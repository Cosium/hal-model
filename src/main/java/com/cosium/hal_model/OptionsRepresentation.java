package com.cosium.hal_model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * @author Réda Housni Alaoui
 */
public class OptionsRepresentation {

  private final @Nullable List<InlineElementRepresentation> inline;
  private final @Nullable OptionsLinkRepresentation link;
  private final @Nullable Long maxItems;
  private final long minItems;
  private final @Nullable String promptField;
  private final List<String> selectedValues;
  private final @Nullable String valueField;

  @JsonCreator
  public OptionsRepresentation(
      @JsonProperty("inline") @Nullable List<InlineElementRepresentation> inline,
      @JsonProperty("link") @Nullable OptionsLinkRepresentation link,
      @JsonProperty("maxItems") @Nullable Long maxItems,
      @JsonProperty("minItems") @Nullable Long minItems,
      @JsonProperty("promptField") @Nullable String promptField,
      @JsonProperty("selectedValues") @Nullable List<String> selectedValues,
      @JsonProperty("valueField") @Nullable String valueField) {
    this.inline = Optional.ofNullable(inline).map(List::copyOf).orElse(null);
    this.link = link;
    this.maxItems = maxItems;
    this.minItems = Optional.ofNullable(minItems).orElse(0L);
    this.promptField = promptField;
    this.selectedValues = Optional.ofNullable(selectedValues).map(List::copyOf).orElseGet(List::of);
    this.valueField = valueField;
  }

  public Optional<List<InlineElementRepresentation>> inline() {
    return Optional.ofNullable(inline);
  }

  public Optional<OptionsLinkRepresentation> link() {
    return Optional.ofNullable(link);
  }

  public Optional<Long> maxItems() {
    return Optional.ofNullable(maxItems);
  }

  public long minItems() {
    return minItems;
  }

  public Optional<String> promptField() {
    return Optional.ofNullable(promptField);
  }

  public List<String> selectedValues() {
    return List.copyOf(selectedValues);
  }

  public Optional<String> valueField() {
    return Optional.ofNullable(valueField);
  }
}
