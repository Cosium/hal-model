package com.cosium.hal_model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.jr.ob.JSON;

/**
 * @author Réda Housni Alaoui
 */
class TemplateTest {

  private static final JsonMapper JSON_MAPPER =
      JsonMapper.builder().addModule(new HalModelJacksonModule()).build();

  @Test
  @DisplayName("Parse template title")
  void test1() {
    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .put("title", "Create")
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");
    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");
    assertThat(create.title()).contains("Create");
  }

  @Test
  @DisplayName("Parse property prompt")
  void test2() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("prompt", "Foo")
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").prompt()).isEqualTo("Foo");
  }

  @Test
  @DisplayName("Parse required property")
  void test3() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("required", true)
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").required()).isTrue();
  }

  @Test
  @DisplayName("Parse property value")
  void test4() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("value", "my-value")
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").rawValue()).contains("my-value");
  }

  @Test
  @DisplayName("Parse readOnly property")
  void test5() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("readOnly", true)
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").readOnly()).isTrue();
  }

  @Test
  @DisplayName("Parse property type")
  void test6() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("type", "color")
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").type()).contains("color");
  }

  @Test
  @DisplayName("Parse property regex")
  void test7() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("regex", "abc")
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").regex()).contains("abc");
  }

  @Test
  @DisplayName("Parse templated property")
  void test8() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("templated", true)
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").templated()).isTrue();
  }

  @Test
  @DisplayName("Parse property max")
  void test10() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("max", 5)
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").max()).contains(5D);
  }

  @Test
  @DisplayName("Parse property min")
  void test11() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("min", 5)
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").min()).contains(5D);
  }

  @Test
  @DisplayName("Parse property maxLength")
  void test12() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("maxLength", 5)
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").maxLength()).contains(5L);
  }

  @Test
  @DisplayName("Parse property minLength")
  void test13() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("minLength", 5)
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").minLength()).contains(5L);
  }

  @Test
  @DisplayName("Parse property step")
  void test14() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("step", 5)
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").step()).contains(5D);
  }

  @Test
  @DisplayName("Parse non string property value")
  void test15() {

    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .startArrayProperty("properties")
            .startObject()
            .put("name", "foo")
            .put("value", true)
            .end()
            .end()
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");

    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");

    assertThat(create.propertyByName()).containsOnlyKeys("foo");
    assertThat(create.requireProperty("foo").rawValue()).contains(true);
  }

  @Test
  @DisplayName("Parse template contentType")
  void test16() {
    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .put("contentType", "text/plain")
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");
    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");
    assertThat(create.contentType()).contains("text/plain");
  }

  @Test
  @DisplayName("Parse template target")
  void test17() {
    String json =
        JSON.std
            .composeString()
            .startObject()
            .startObjectProperty("_links")
            .startObjectProperty("self")
            .put("href", "http://localhost/template-assertion-test:put")
            .end()
            .end()
            .startObjectProperty("_templates")
            .startObjectProperty("create")
            .put("method", "PUT")
            .put("target", "http://localhost/template-assertion-test:put2")
            .end()
            .end()
            .end()
            .finish();

    HalFormsBody<Void> formsBody = JSON_MAPPER.readValue(json, new TypeReference<>() {});

    assertThat(formsBody.linkByName()).containsOnlyKeys("self");
    assertThat(formsBody.requireLink("self").href().expand())
        .isEqualTo("http://localhost/template-assertion-test:put");
    assertThat(formsBody.templateByKey()).containsOnlyKeys("create");
    TemplateRepresentation create = formsBody.requireTemplate("create");
    assertThat(create.method()).isEqualTo("PUT");
    assertThat(create.target()).contains("http://localhost/template-assertion-test:put2");
  }
}
