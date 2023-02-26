package nl.ilovecoding.wiremocktestcontainers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FetchUser {

    public static final String GET_USER_URL = "https://reqres.in/api/users/1";

    private String url;

    public String getUrl() {
        return url == null ? GET_USER_URL : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpResponse<String> execute() throws URISyntaxException, IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(
                        new URI(getUrl()))
                .GET()
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    }

}
