package com.cosium.hal_model2;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;
import tools.jackson.jr.ob.JSON;

/**
 * @author Réda Housni Alaoui
 */
class HalFormsBodyTest {

  @Test
  @DisplayName("Parse atomic value")
  void test1() {
    String json =
        JSON.std
            .composeString()
            .startObject()
            .put("username", "jdoe")
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/form-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("default")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<User> halFormsBody =
        TestJsonMapper.INSTANCE.readValue(json, new TypeReference<>() {});
    assertThat(halFormsBody.representation().username()).isEqualTo("jdoe");
    assertThat(halFormsBody.linkByName()).containsOnlyKeys("self");
    assertThat(halFormsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/form-test:put");
    assertThat(halFormsBody.requireSelfUri()).isEqualTo("http://localhost/form-test:put");

    assertThat(halFormsBody.templateByKey()).containsOnlyKeys("default");

    Template template = halFormsBody.requireTemplate("default");
    assertThat(template.method()).isEqualTo("PUT");
    assertThat(template.propertyByName()).containsOnlyKeys("foo");

    TemplateProperty property = template.propertyByName().get("foo");
    assertThat(property.type()).isEqualTo("text");
  }

  @Test
  @DisplayName("Parse collection")
  void test2() {
    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_embedded")
            .startArrayProperty("content")
            .add("element1")
            .add("element2")
            .end()
            .end()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/form-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("default")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<EmbeddedContainer<ContentList<String>>> halFormsBody =
        TestJsonMapper.INSTANCE.readValue(json, new TypeReference<>() {});
    assertThat(halFormsBody.representation().requireEmbedded().content())
        .containsExactly("element1", "element2");
    assertThat(halFormsBody.linkByName()).containsOnlyKeys("self");
    assertThat(halFormsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/form-test:put");

    assertThat(halFormsBody.templateByKey()).containsOnlyKeys("default");

    Template template = halFormsBody.requireTemplate("default");
    assertThat(template.method()).isEqualTo("PUT");
    assertThat(template.propertyByName()).containsOnlyKeys("foo");

    TemplateProperty property = template.propertyByName().get("foo");
    assertThat(property.type()).isEqualTo("text");
  }

  private record User(@JsonProperty("username") String username) {}

  private record ContentList<T>(@JsonProperty("content") @Nullable List<T> content) {}
}
