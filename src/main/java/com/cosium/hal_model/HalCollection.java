package com.cosium.hal_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * @author Réda Housni Alaoui
 */
public record HalCollection<T>(@JsonProperty("_embedded") @Nullable Embedded<T> embedded) {

  public List<T> content() {
    return Optional.ofNullable(embedded)
        .map(Embedded::content)
        .map(List::copyOf)
        .orElseGet(List::of);
  }

  public record Embedded<T>(@JsonProperty("content") @Nullable List<T> content) {}
}
