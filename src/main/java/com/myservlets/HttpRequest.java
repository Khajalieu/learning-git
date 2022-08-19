package com.myservlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private BufferedReader reader;
    private String method;
    private String path;
    private Map<String, String> requestParameters;
    private Map<String, String> headers;

    public HttpRequest(BufferedReader reader) {
        this.reader = reader;
        requestParameters = new HashMap<>();
        headers = new HashMap<>();
    }


    public boolean parse() throws IOException {
        //GET /hello?user=khaja&pwd=1234 Http/1.1
        //GET /hello Http/1.1
        String line = reader.readLine();
        String[] firstLine = line.split(" ");
        if (firstLine.length != 3) return false;
        method = firstLine[0];
        String url = firstLine[1];
        int queryStringIndex = url.indexOf("?");
        if (queryStringIndex > -1) {
            path = url.substring(0, queryStringIndex);
            parseRequestParams(url.substring(queryStringIndex + 1));
        } else {
            path = url;
        }

        while (!line.isEmpty()) {
            line = reader.readLine(); //request headers
            if (!line.isBlank()) {
                String[] headerPair = line.split(":");
                headers.put(headerPair[0], headerPair[1]);
            }
        }


        return true;
    }

    private void parseRequestParams(String queryString) { //user=khaja&pwd=1234*city=freetown
        for (String pair : queryString.split("&")) {
            String[] queryPair = pair.split("=");
            requestParameters.put(queryPair[0], queryPair[1]);
        }

    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
