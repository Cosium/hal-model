package com.cosium.hal_model2;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * @author Réda Housni Alaoui
 */
public class TemplateProperty {
  private final String name;
  private final boolean required;
  private final @Nullable Object value;
  private final String prompt;
  private final @Nullable String regex;
  private final boolean templated;
  private final @Nullable Options options;
  private final boolean readOnly;
  private final String type;
  private final @Nullable Double max;
  private final @Nullable Long maxLength;
  private final @Nullable Double min;
  private final @Nullable Long minLength;
  private final @Nullable Double step;

  @JsonCreator
  TemplateProperty(
      @JsonProperty("name") String name,
      @JsonProperty("required") @Nullable Boolean required,
      @JsonProperty("value") @Nullable Object value,
      @JsonProperty("prompt") @Nullable String prompt,
      @JsonProperty("regex") @Nullable String regex,
      @JsonProperty("templated") @Nullable Boolean templated,
      @JsonProperty("options") @Nullable Options options,
      @JsonProperty("readOnly") @Nullable Boolean readOnly,
      @JsonProperty("type") @Nullable String type,
      @JsonProperty("max") @Nullable Double max,
      @JsonProperty("maxLength") @Nullable Long maxLength,
      @JsonProperty("min") @Nullable Double min,
      @JsonProperty("minLength") @Nullable Long minLength,
      @JsonProperty("step") @Nullable Double step) {
    this.name = requireNonNull(name, "Attribute 'name' is missing");
    this.required = Optional.ofNullable(required).orElse(false);
    this.value = value;
    this.prompt = Optional.ofNullable(prompt).orElse(name);
    this.regex = regex;
    this.templated = Optional.ofNullable(templated).orElse(false);
    this.options = options;
    this.readOnly = Optional.ofNullable(readOnly).orElse(false);
    this.type = Optional.ofNullable(type).orElse("text");
    this.max = max;
    this.maxLength = maxLength;
    this.min = min;
    this.minLength = minLength;
    this.step = step;
  }

  public String name() {
    return name;
  }

  public String type() {
    return type;
  }

  public boolean readOnly() {
    return readOnly;
  }

  public boolean required() {
    return required;
  }

  public Optional<Object> rawValue() {
    return Optional.ofNullable(value);
  }

  public Optional<Double> max() {
    return Optional.ofNullable(max);
  }

  public Optional<Long> maxLength() {
    return Optional.ofNullable(maxLength);
  }

  public Optional<Double> min() {
    return Optional.ofNullable(min);
  }

  public Optional<Long> minLength() {
    return Optional.ofNullable(minLength);
  }

  public Optional<Double> step() {
    return Optional.ofNullable(step);
  }

  public String prompt() {
    return prompt;
  }

  public Optional<String> regex() {
    return Optional.ofNullable(regex);
  }

  public boolean templated() {
    return templated;
  }

  public Optional<Options> options() {
    return Optional.ofNullable(options);
  }
}
