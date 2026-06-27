package com.cosium.hal_model2;

import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * @author Réda Housni Alaoui
 */
public record MapInlineElement(Map<String, String> map) implements InlineElement {

  public MapInlineElement(@Nullable Map<String, String> map) {
    this.map = Optional.ofNullable(map).map(Map::copyOf).orElseGet(Map::of);
  }
}
