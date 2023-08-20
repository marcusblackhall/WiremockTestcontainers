package nl.ilovecoding.wiremocktestcontainers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MakeSoapRequest {

    private final String url;
    private final String body;

    public MakeSoapRequest(String url, String body) {
        this.url = url;
        this.body = body;
    }
    public HttpResponse<String> execute() throws URISyntaxException, IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(
                        new URI(url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    }

}
