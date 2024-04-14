package aget.teleroute.send;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FkSend implements Send<FkClient> {
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
    public void send(FkClient client) {
        response.forEach(client::submit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FkSend fkSend = (FkSend) o;
        return Objects.equals(response, fkSend.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response);
    }

    @Override
    public String toString() {
        return "FkSend{" +
                "response=" + response +
                '}';
    }
}
