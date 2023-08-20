package nl.ilovecoding.wiremocktestcontainers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class FetchSoapResponseIT {

    Logger logger = LoggerFactory.getLogger(FetchUserIT.class);

    private final String path = Paths.get("src", "main", "resources", "wiremock").toFile().getAbsolutePath();
    @Container
    private final GenericContainer<?> wiremockContainer = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock"))
            .withExposedPorts(8080)
            .withFileSystemBind(path, "/home/wiremock", BindMode.READ_ONLY);


    @Test
    void shouldReturnResponse() throws URISyntaxException, IOException, InterruptedException {

        Integer firstMappedPort = wiremockContainer.getFirstMappedPort();
        String host = wiremockContainer.getHost();
        String requestPath = "/api/soap";

        Path bodyPath = Paths.get("src", "test", "resources", "soap.xml");

        List<String> strings = Files.readAllLines(bodyPath);
        StringBuilder bodyAsString = new StringBuilder();
        strings.forEach(bodyAsString::append);
        logger.info("{}", bodyAsString);

        String url = "http://" + host + ":" + firstMappedPort + requestPath;
        logger.info("url : {}", url);

        MakeSoapRequest makeSoapRequest = new MakeSoapRequest(url, bodyAsString.toString());
        HttpResponse<String> response = makeSoapRequest.execute();

        assertEquals(200, response.statusCode());


    }
}
