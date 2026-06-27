package com.cosium.hal_model;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * @author Réda Housni Alaoui
 */
public class OptionsLinkRepresentation {

  private final String href;
  private final @Nullable String type;
  private final boolean templated;

  @JsonCreator
  public OptionsLinkRepresentation(
      @JsonProperty("href") String href,
      @JsonProperty("type") @Nullable String type,
      @JsonProperty("templated") @Nullable Boolean templated) {
    this.href = requireNonNull(href, "Attribute 'href' is missing");
    this.type = type;
    this.templated = Optional.ofNullable(templated).orElse(false);
  }

  public String href() {
    return href;
  }

  public Optional<String> type() {
    return Optional.ofNullable(type);
  }

  public boolean templated() {
    return templated;
  }
}
