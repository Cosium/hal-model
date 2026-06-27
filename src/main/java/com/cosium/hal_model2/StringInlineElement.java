package com.cosium.hal_model2;

import static java.util.Objects.requireNonNull;

/**
 * @author Réda Housni Alaoui
 */
public record StringInlineElement(String value) implements InlineElement {

  public StringInlineElement {
    requireNonNull(value);
  }
}
