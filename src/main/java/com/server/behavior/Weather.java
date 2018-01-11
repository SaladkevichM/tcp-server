package com.server.behavior;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.ext.Fetcher;

import java.io.IOException;
import java.util.HashMap;

public class Weather implements Behavior {

    private ObjectMapper mapper = new ObjectMapper();
    private Fetcher fetch = new Fetcher();

    @Override
    public String apply(String input) {
        return represent(fetch.fetchResource(input));
    }

    @Override
    public String represent(String input) {
        try {
            HashMap<String, Object> map = mapper.readValue(input, HashMap.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map.get("main"));
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
