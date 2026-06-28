package com.cosium.hal_model2;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

/**
 * @author Réda Housni Alaoui
 */
public class HalFormsBodies {

  private static final JsonMapper JSON_MAPPER =
      JsonMapper.builder().addModule(new HalModelJacksonModule()).build();

  private HalFormsBodies() {}

  public static <T> HalFormsBody<T> parseJson(String json) {
    return JSON_MAPPER.readValue(json, new TypeReference<>() {});
  }
}
