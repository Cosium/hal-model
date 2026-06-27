package com.cosium.hal_model;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.Nullable;

/**
 * @author Réda Housni Alaoui
 */
public record EmbeddedContainer<T>(@JsonProperty("_embedded") @Nullable T embedded) {

  public T requireEmbedded() {
    return requireNonNull(embedded);
  }
}
