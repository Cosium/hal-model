package com.cosium.hal_model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.jr.ob.JSON;

/**
 * @author Réda Housni Alaoui
 */
class OptionsTest {

  private static final JsonMapper JSON_MAPPER =
      JsonMapper.builder().addModule(new HalModelJacksonModule()).build();

  @Test
  void inlineOfStringArray() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "https://api.example.org/")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("default")
            .put("method", "POST")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "shipping")
            .startObjectProperty("options")
            .startArrayProperty("selectedValues")
            .add("FedEx")
            .end()
            .startArrayProperty("inline")
            .add("FedEx")
            .add("UPS")
            .add("DHL")
            .end()
            .end()
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    OptionsRepresentation options =
        JSON_MAPPER
            .<HalFormsBody<Void>>readValue(json, new TypeReference<>() {})
            .requireTemplate("default")
            .propertyByName()
            .get("shipping")
            .options()
            .orElseThrow();

    assertThat(options.link()).isEmpty();
    assertThat(options.minItems()).isZero();
    assertThat(options.maxItems()).isEmpty();
    assertThat(options.promptField()).isEmpty();
    assertThat(options.valueField()).isEmpty();

    assertThat(options.selectedValues()).containsExactly("FedEx");
    assertThat(options.inline().orElseThrow())
        .allMatch(StringInlineElementRepresentation.class::isInstance)
        .map(StringInlineElementRepresentation.class::cast)
        .map(StringInlineElementRepresentation::value)
        .containsExactly("FedEx", "UPS", "DHL");
  }

  @Test
  void referenceProperties() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "https://api.example.org/")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("default")
            .put("method", "POST")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "shipping")
            .startObjectProperty("options")
            .startArrayProperty("selectedValues")
            .add("FedEx")
            .end()
            .startArrayProperty("inline")
            .startObject()
            .put("shipName", "Federal Express")
            .put("shipCode", "FedEx")
            .end()
            .startObject()
            .put("shipName", "United Parcel Service")
            .put("shipCode", "UPS")
            .end()
            .startObject()
            .put("shipName", "DHL Express")
            .put("shipCode", "DHL")
            .end()
            .end()
            .put("promptField", "shipName")
            .put("valueField", "shipCode")
            .end()
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    OptionsRepresentation options =
        JSON_MAPPER
            .<HalFormsBody<Void>>readValue(json, new TypeReference<>() {})
            .requireTemplate("default")
            .propertyByName()
            .get("shipping")
            .options()
            .orElseThrow();

    assertThat(options.link()).isEmpty();
    assertThat(options.minItems()).isZero();
    assertThat(options.maxItems()).isEmpty();
    assertThat(options.promptField()).contains("shipName");
    assertThat(options.valueField()).contains("shipCode");

    assertThat(options.selectedValues()).containsExactly("FedEx");
    assertThat(options.inline().orElseThrow())
        .allMatch(MapInlineElementRepresentation.class::isInstance)
        .map(MapInlineElementRepresentation.class::cast)
        .map(MapInlineElementRepresentation::map)
        .contains(
            Map.of("shipName", "Federal Express", "shipCode", "FedEx"),
            Map.of("shipName", "United Parcel Service", "shipCode", "UPS"),
            Map.of("shipName", "DHL Express", "shipCode", "DHL"));
  }

  @Test
  void multipleReturnValues() {
    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "https://api.example.org/")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("default")
            .put("method", "POST")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "shipping")
            .startObjectProperty("options")
            .startArrayProperty("selectedValues")
            .add("FedEx")
            .end()
            .startArrayProperty("inline")
            .startObject()
            .put("shipName", "Federal Express")
            .put("shipCode", "FedEx")
            .end()
            .startObject()
            .put("shipName", "United Parcel Service")
            .put("shipCode", "UPS")
            .end()
            .startObject()
            .put("shipName", "DHL Express")
            .put("shipCode", "DHL")
            .end()
            .end()
            .put("minItems", 1)
            .put("maxItems", 2)
            .put("promptField", "shipName")
            .put("valueField", "shipCode")
            .end()
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    OptionsRepresentation options =
        JSON_MAPPER
            .<HalFormsBody<Void>>readValue(json, new TypeReference<>() {})
            .requireTemplate("default")
            .propertyByName()
            .get("shipping")
            .options()
            .orElseThrow();

    assertThat(options.link()).isEmpty();
    assertThat(options.minItems()).isOne();
    assertThat(options.maxItems()).contains(2L);
    assertThat(options.promptField()).contains("shipName");
    assertThat(options.valueField()).contains("shipCode");

    assertThat(options.selectedValues()).containsExactly("FedEx");
    assertThat(options.inline().orElseThrow())
        .allMatch(MapInlineElementRepresentation.class::isInstance)
        .map(MapInlineElementRepresentation.class::cast)
        .map(MapInlineElementRepresentation::map)
        .contains(
            Map.of("shipName", "Federal Express", "shipCode", "FedEx"),
            Map.of("shipName", "United Parcel Service", "shipCode", "UPS"),
            Map.of("shipName", "DHL Express", "shipCode", "DHL"));
  }

  @Test
  void externalArrayOfValues() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "https://api.example.org/")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("default")
            .put("method", "POST")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "shipping")
            .startObjectProperty("options")
            .startArrayProperty("selectedValues")
            .add("FedEx")
            .end()
            .startObjectProperty("link")
            .put("href", "http://api.examples.org/shipping-options")
            .put("templated", false)
            .put("type", "application/json")
            .end()
            .end()
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    OptionsRepresentation options =
        JSON_MAPPER
            .<HalFormsBody<Void>>readValue(json, new TypeReference<>() {})
            .requireTemplate("default")
            .propertyByName()
            .get("shipping")
            .options()
            .orElseThrow();

    assertThat(options.minItems()).isZero();
    assertThat(options.maxItems()).isEmpty();
    assertThat(options.promptField()).isEmpty();
    assertThat(options.valueField()).isEmpty();

    assertThat(options.selectedValues()).containsExactly("FedEx");
    OptionsLinkRepresentation link = options.link().orElseThrow();
    assertThat(link.href()).isEqualTo("http://api.examples.org/shipping-options");
    assertThat(link.templated()).isFalse();
    assertThat(link.type()).contains("application/json");
  }
}
