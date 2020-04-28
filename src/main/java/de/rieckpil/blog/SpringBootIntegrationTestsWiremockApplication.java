  package de.rieckpil.blog;

  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.boot.builder.SpringApplicationBuilder;

  @SpringBootApplication
  public class SpringBootIntegrationTestsWiremockApplication {
    public static void main(String[] args) {

      new SpringApplicationBuilder(SpringBootIntegrationTestsWiremockApplication.class)
        .initializers(new WireMockConfig())
        .run(args);
    }
  }
