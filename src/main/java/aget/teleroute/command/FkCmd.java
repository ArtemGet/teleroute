package aget.teleroute.command;

import aget.teleroute.send.FkRs;
import aget.teleroute.send.FkSend;
import aget.teleroute.send.Send;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FkCmd implements Cmd<String, FkRs> {
    private final List<String> response;

    public FkCmd() {
        this(Collections.emptyList());
    }

    public FkCmd(String... response) {
        this(Arrays.asList(response));
    }

    public FkCmd(List<String> response) {
        this.response = Collections.unmodifiableList(response);
    }

    @Override
    public Optional<Send<FkRs>> execute(String update) {
        return Optional.of(
                new FkSend(response)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FkCmd fkCmd = (FkCmd) o;
        return Objects.equals(response, fkCmd.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response);
    }
}
