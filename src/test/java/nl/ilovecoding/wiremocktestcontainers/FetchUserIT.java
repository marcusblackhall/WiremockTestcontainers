package nl.ilovecoding.wiremocktestcontainers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class FetchUserIT {

    private final String path = Paths.get("src", "main", "resources", "wiremock").toFile().getAbsolutePath();
    @Container
    private final GenericContainer<?> wiremockContainer = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock"))
            .withExposedPorts(8080)
            .withFileSystemBind(path, "/home/wiremock", BindMode.READ_ONLY);


    @Test
    void shouldReturnResponse() throws URISyntaxException, IOException, InterruptedException {

        Integer firstMappedPort = wiremockContainer.getFirstMappedPort();
        String host = wiremockContainer.getHost();
        String requestPath = "/api/users/1";
        FetchUser fetchUser = new FetchUser();
        String url = "http://" + host + ":" + firstMappedPort + requestPath;
        fetchUser.setUrl(url);
        HttpResponse<String> response = fetchUser.execute();

        assertEquals(200, response.statusCode());
        assertThat(response.body(), containsString("george.bluth@reqres.in"));


    }
}