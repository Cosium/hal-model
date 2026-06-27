[![Build Status](https://github.com/Cosium/hal-model/actions/workflows/ci.yml/badge.svg)](https://github.com/Cosium/hal-model/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.cosium.hal_model/hal-model.svg)](https://central.sonatype.com/artifact/com.cosium.hal_model/hal-model)

# HAL Model
A java modelisation of HAL (Hypertext Application Language)

# Prerequisites
- Java 17+

# Quick start

1. Add the `hal-model` dependency:
    ```xml
    <dependency>
      <groupId>com.cosium.hal_model</groupId>
      <artifactId>hal-model2</artifactId>
      <version>${hal-model.version}</version>
    </dependency>
    ```
2. Add `HalModelJacksonModule` to your [Jackson ObjectMapper](https://github.com/fasterxml/jackson):
   ```java
   JsonMapper jsonMapper = JsonMapper.builder().addModule(new HalModelJacksonModule()).build();
   ```
3. Use [Jackson ObjectMapper](https://github.com/fasterxml/jackson) to parse any HAL(-FORMS) JSON body

# Atomic representation parsing

```json
{
  "username" : "jdoe",
  "_links" : {
    "self" : {
      "href" : "http://localhost/self"
    }
  },
  "_templates" : {
    "default" : {
      "method" : "PUT",
      "properties" : [ {
        "name" : "foo"
      } ]
    }
  }
}
```

```java
record User(@JsonProperty("username") String username) {}

void main() {
  JsonMapper jsonMapper = JsonMapper.builder().addModule(new HalModelJacksonModule()).build();
  HalFormsBody<User> halFormsBody = jsonMapper.readValue(json, new TypeReference<>() {});
  assertThat(halFormsBody.representation().username()).isEqualTo("jdoe");
  assertThat(halFormsBody.linkByName()).containsOnlyKeys("self");
  assertThat(halFormsBody.requireLink("self").href().expand())
      .isEqualTo("http://localhost/self");
  assertThat(halFormsBody.requireSelfUri()).isEqualTo("http://localhost/self");

  assertThat(halFormsBody.templateByKey()).containsOnlyKeys("default");

  TemplateRepresentation template = halFormsBody.requireTemplate("default");
  assertThat(template.method()).isEqualTo("PUT");
  assertThat(template.propertyByName()).containsOnlyKeys("foo");

  TemplatePropertyRepresentation property = template.propertyByName().get("foo");
  assertThat(property.type()).isEqualTo("text");
}
```

# Collection representation parsing

```json
{
  "_embedded" : {
    "content" : [ "element1", "element2" ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost/self"
    }
  },
  "_templates" : {
    "default" : {
      "method" : "PUT",
      "properties" : [ {
        "name" : "foo"
      } ]
    }
  }
}
```

```java
record ContentList<T>(@JsonProperty("content") @Nullable List<T> content) {}

void main() {
  JsonMapper jsonMapper = JsonMapper.builder().addModule(new HalModelJacksonModule()).build();
  HalFormsBody<EmbeddedContainer<ContentList<String>>> halFormsBody =
      JSON_MAPPER.readValue(json, new TypeReference<>() {});
  assertThat(halFormsBody.representation().requireEmbedded().content())
      .containsExactly("element1", "element2");
  assertThat(halFormsBody.linkByName()).containsOnlyKeys("self");
  assertThat(halFormsBody.requireLink("self").href().expand())
      .isEqualTo("http://localhost/self");

  assertThat(halFormsBody.templateByKey()).containsOnlyKeys("default");

  TemplateRepresentation template = halFormsBody.requireTemplate("default");
  assertThat(template.method()).isEqualTo("PUT");
  assertThat(template.propertyByName()).containsOnlyKeys("foo");

  TemplatePropertyRepresentation property = template.propertyByName().get("foo");
  assertThat(property.type()).isEqualTo("text");
}

```