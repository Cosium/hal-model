package com.cosium.hal_model2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import tools.jackson.databind.json.JsonMapper;

/**
 * @author Réda Housni Alaoui
 */
class TestJsonMapper {

  public static final JsonMapper INSTANCE =
      JsonMapper.builder()
          .addModule(new HalModelJacksonModule())
          .changeDefaultVisibility(
              visibilityChecker ->
                  visibilityChecker
                      .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                      .withFieldVisibility(JsonAutoDetect.Visibility.NONE)
                      .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                      .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                      .withSetterVisibility(JsonAutoDetect.Visibility.NONE))
          .build();
}
