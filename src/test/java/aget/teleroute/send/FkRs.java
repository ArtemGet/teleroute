package aget.teleroute.send;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fake tg response.
 */
public class FkRs {
    private final List<String> response;

    /**
     * Constructs empty response.
     */
    public FkRs() {
        this.response = new ArrayList<>();
    }

    /**
     * Main constructor. Constructs response containing content.
     *
     * @param response content
     */
    public FkRs(String response) {
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
    public List<String> containing() {
        return this.response;
    }
}
