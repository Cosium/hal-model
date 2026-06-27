package com.cosium.hal_model;

import java.util.Map;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.exc.MismatchedInputException;

/**
 * @author Réda Housni Alaoui
 */
class InlineElementRepresentationDeserializer
    extends ValueDeserializer<InlineElementRepresentation> {

  @Override
  public @Nullable InlineElementRepresentation deserialize(
      JsonParser p, DeserializationContext ctxt) {

    JsonToken currentToken = p.currentToken();
    if (currentToken == JsonToken.START_OBJECT) {
      Map<String, String> map =
          ctxt.readValue(
              p, ctxt.getTypeFactory().constructMapType(Map.class, String.class, String.class));
      if (map == null) {
        return null;
      }
      return new MapInlineElementRepresentation(map);
    }
    if (currentToken == JsonToken.VALUE_STRING) {
      String value = ctxt.readValue(p, String.class);
      if (value == null) {
        return null;
      }
      return new StringInlineElementRepresentation(value);
    }

    throw MismatchedInputException.from(
        p,
        InlineElementRepresentation.class,
        "%s should have been either %s or %s. But it is not."
            .formatted(currentToken, JsonToken.START_OBJECT, JsonToken.VALUE_STRING));
  }
}
