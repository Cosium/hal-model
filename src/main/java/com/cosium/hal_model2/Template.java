package com.cosium.hal_model2;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;

/**
 * @author Réda Housni Alaoui
 */
public class Template {

  private final @Nullable String title;
  private final String method;
  private final String contentType;
  private final Map<String, TemplateProperty> propertyByName;
  private final @Nullable String target;

  @JsonCreator
  Template(
      @JsonProperty("title") @Nullable String title,
      @JsonProperty("method") String method,
      @JsonProperty("contentType") @Nullable String contentType,
      @JsonProperty("properties") @Nullable List<TemplateProperty> properties,
      @JsonProperty("target") @Nullable String target) {
    this.title = title;
    this.method = requireNonNull(method, "Attribute 'method' is missing");
    this.contentType = Optional.ofNullable(contentType).orElse("application/json");
    Map<String, TemplateProperty> mutablePropertyByName =
        Optional.ofNullable(properties).orElseGet(List::of).stream()
            .collect(
                Collectors.toMap(
                    TemplateProperty::name,
                    Function.identity(),
                    (e1, e2) -> {
                      throw new IllegalStateException(
                          "Properties " + e1 + " and " + e2 + " have the same name");
                    },
                    LinkedHashMap::new));
    this.propertyByName = Collections.unmodifiableMap(mutablePropertyByName);
    this.target = target;
  }

  public Optional<String> title() {
    return Optional.ofNullable(title);
  }

  public String method() {
    return method;
  }

  public String contentType() {
    return contentType;
  }

  public Map<String, TemplateProperty> propertyByName() {
    return propertyByName;
  }

  public Optional<TemplateProperty> findProperty(String name) {
    return Optional.ofNullable(propertyByName.get(name));
  }

  public TemplateProperty requireProperty(String name) {
    return findProperty(name).orElseThrow();
  }

  public Optional<String> target() {
    return Optional.ofNullable(target);
  }
}
