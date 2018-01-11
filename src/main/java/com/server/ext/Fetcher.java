package com.server.ext;

import com.server.util.Util;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Fetcher {

    private static Logger log = Logger.getLogger(Fetcher.class.getName());

    public String fetchResource(String query) {

        HttpUriRequest request = new HttpGet(Util.getProperty("api_url") + "?appid="
                + Util.getProperty("api_key") + "&q=" + query);
        try {

            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                return new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity().getContent())).lines()
                                .collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        }

        return "{}";

    }

}
