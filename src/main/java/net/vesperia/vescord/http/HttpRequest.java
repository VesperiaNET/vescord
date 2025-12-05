package net.vesperia.vescord.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public class HttpRequest {
    private final Method method;
    private final URI uri;
    private final HttpURLConnection connection;

    public HttpRequest(String url) throws IOException {
        this.method = Method.GET;
        this.uri = URI.create(url);
        this.connection = (HttpURLConnection) uri.toURL().openConnection();
        this.connection.setRequestMethod(method.name());
    }

    public HttpRequest(Method method, String url) throws IOException {
        this.method = method;
        this.uri = URI.create(url);
        this.connection = (HttpURLConnection) uri.toURL().openConnection();
        this.connection.setRequestMethod(method.name());
    }

    public HttpRequest setHeader(String name, String value) {
        this.connection.setRequestProperty(name, value);
        return this;
    }

    public HttpRequest setHeaders(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            this.connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public HttpRequest setBody(String body) {
        this.connection.setDoOutput(true);
        try {
            this.connection.getOutputStream().write(body.getBytes());
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        return this;
    }

    public HttpResponse send() throws IOException {
        int statusCode = this.connection.getResponseCode();
        InputStream inputStream = statusCode < 400 ? this.connection.getInputStream() : this.connection.getErrorStream();
        StringBuilder responseBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line).append("\n");
            }
        }
        return new HttpResponse(statusCode, responseBody.toString().trim(), this.connection.getHeaderFields());
    }
}
