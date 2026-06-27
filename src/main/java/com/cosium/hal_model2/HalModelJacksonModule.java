package com.cosium.hal_model2;

import tools.jackson.core.Version;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.module.SimpleDeserializers;

/**
 * @author Réda Housni Alaoui
 */
public class HalModelJacksonModule extends JacksonModule {

  @Override
  public String getModuleName() {
    return "com.cosium.hal_model";
  }

  @Override
  public Version version() {
    return Version.unknownVersion();
  }

  @Override
  public void setupModule(SetupContext context) {
    context.addDeserializers(
        new SimpleDeserializers()
            .addDeserializer(HalFormsBody.class, new HalFormsBodyDeserializer())
            .addDeserializer(InlineElement.class, new InlineElementDeserializer()));
  }
}
