package com.cosium.hal_model2;

import static java.util.Objects.requireNonNull;

import com.damnhandy.uri.template.UriTemplate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * @author Réda Housni Alaoui
 */
public class Link {

  private final String href;
  private final @Nullable String type;
  private final boolean templated;

  @JsonCreator
  public Link(
      @JsonProperty("href") String href,
      @JsonProperty("type") @Nullable String type,
      @JsonProperty("templated") @Nullable Boolean templated) {
    this.href = requireNonNull(href, "Attribute 'href' is missing");
    this.type = type;
    this.templated = Optional.ofNullable(templated).orElse(false);
  }

  public UriTemplate href() {
    return UriTemplate.fromTemplate(href);
  }

  public Optional<String> type() {
    return Optional.ofNullable(type);
  }

  public boolean templated() {
    return templated;
  }
}
