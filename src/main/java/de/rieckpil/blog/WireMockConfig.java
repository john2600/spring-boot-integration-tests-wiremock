package de.rieckpil.blog;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@Service
public class WireMockConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  static final String xmlStr = "<employees>" +
    "   <employee id=\"101\">" +
    "        <name>Lokesh Gupta</name>" +
    "       <title>Author</title>" +
    "   </employee>" +
    "   <employee id=\"102\">" +
    "        <name>Brian Lara</name>" +
    "       <title>Cricketer</title>" +
    "   </employee>" +
    "</employees>";
  @Override
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().port(8081));
    wireMockServer.start();
    configurableApplicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);
    configurableApplicationContext.addApplicationListener(applicationEvent -> {
      if (applicationEvent instanceof ContextClosedEvent) {
        wireMockServer.stop();
      }
    });

    wireMockServer.stubFor(
      WireMock.get("/json")
        .willReturn(aResponse()
          .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
          .withBody("[{\"userId\": 1,\"id\": 1,\"title\": \"Learn Spring Boot 3.0\", \"completed\": false}," +
            "{\"userId\": 1,\"id\": 2,\"title\": \"Learn WireMock\", \"completed\": true}]"))
    );

    wireMockServer.stubFor(
      WireMock.get("/xml")
        .willReturn(aResponse()
          .withHeader("Content-Type", MediaType.APPLICATION_XML_VALUE)
          .withBody(xmlStr)))
    ;


  }
}
