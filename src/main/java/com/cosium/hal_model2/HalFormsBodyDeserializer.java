package com.cosium.hal_model2;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.core.ObjectReadContext;
import tools.jackson.core.TreeNode;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.exc.MismatchedInputException;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.type.MapType;

/**
 * @author Réda Housni Alaoui
 */
class HalFormsBodyDeserializer extends ValueDeserializer<HalFormsBody<?>> {

  private final @Nullable JavaType representationType;

  public HalFormsBodyDeserializer() {
    this.representationType = null;
  }

  private HalFormsBodyDeserializer(JavaType representationType) {
    this.representationType = requireNonNull(representationType);
  }

  @Override
  public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
    JavaType halFormsBodyType = ctxt.getContextualType();
    return new HalFormsBodyDeserializer(halFormsBodyType.containedType(0));
  }

  @Override
  public HalFormsBody<?> deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) {

    requireNonNull(representationType);

    TreeNode treeNode = deserializationContext.readTree(jsonParser);
    if (!(treeNode instanceof ObjectNode objectNode)) {
      throw MismatchedInputException.from(
          jsonParser, HalFormsBody.class, "Expected object node, got " + treeNode);
    }

    Object representation =
        deserializationContext.readValue(
            createJsonParser(deserializationContext, objectNode), representationType);

    JsonNode links = objectNode.get("_links");
    Map<String, Link> linkByName;
    if (links == null) {
      linkByName = Map.of();
    } else {
      MapType mapType =
          deserializationContext
              .getTypeFactory()
              .constructMapType(Map.class, String.class, Link.class);
      linkByName =
          deserializationContext.readValue(
              createJsonParser(deserializationContext, links), mapType);
    }

    JsonNode templates = objectNode.get("_templates");
    Map<String, Template> templateByKey;
    if (templates == null) {
      templateByKey = Map.of();
    } else {
      MapType mapType =
          deserializationContext
              .getTypeFactory()
              .constructMapType(Map.class, String.class, Template.class);
      templateByKey =
          deserializationContext.readValue(
              createJsonParser(deserializationContext, templates), mapType);
    }

    return new HalFormsBody<>(representation, linkByName, templateByKey);
  }

  private JsonParser createJsonParser(ObjectReadContext readCtxt, TreeNode objectNode) {
    JsonParser parser = objectNode.traverse(readCtxt);
    parser.nextToken();
    return parser;
  }
}
