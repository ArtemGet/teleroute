package com.github.ArtemGet.teleroute.send;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fake tg client.
 */
public class FkClient {
    private final List<String> response;

    /**
     * Constructs empty response.
     */
    public FkClient() {
        this.response = new ArrayList<>();
    }

    /**
     * Main constructor. Constructs response containing content.
     *
     * @param response content
     */
    public FkClient(String response) {
        this.response = Collections.singletonList(response);
    }

    /**
     * Submit content to tg response.
     *
     * @param response content
     */
    public void submit(String response) {
        this.response.add(response);
    }

    /**
     * Retrieve tg response contents.
     *
     * @return response
     */
    public List<String> sent() {
        return this.response;
    }
}
