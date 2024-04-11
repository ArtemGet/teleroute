package aget.teleroute.send;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FkSend implements Send<FkRs> {
    private final List<String> response;

    public FkSend() {
        this(Collections.emptyList());
    }

    public FkSend(String... response) {
        this(Arrays.asList(response));
    }

    public FkSend(List<String> response) {
        this.response = Collections.unmodifiableList(response);
    }

    @Override
    public void send(FkRs send) {
        response.forEach(send::submit);
    }
}
