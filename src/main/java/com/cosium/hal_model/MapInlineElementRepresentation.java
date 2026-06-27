package com.cosium.hal_model;

import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * @author Réda Housni Alaoui
 */
public record MapInlineElementRepresentation(Map<String, String> map)
    implements InlineElementRepresentation {

  public MapInlineElementRepresentation(@Nullable Map<String, String> map) {
    this.map = Optional.ofNullable(map).map(Map::copyOf).orElseGet(Map::of);
  }
}
