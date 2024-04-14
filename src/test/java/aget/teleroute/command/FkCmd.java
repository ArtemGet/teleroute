package aget.teleroute.command;

import aget.teleroute.send.FkClient;
import aget.teleroute.send.Send;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FkCmd implements Cmd<String, FkClient> {
    private final List<Send<FkClient>> send;

    public FkCmd() {
        send = Collections.emptyList();
    }

    public FkCmd(Send<FkClient> send) {
        this.send = Collections.singletonList(send);
    }

    @Override
    public Optional<Send<FkClient>> execute(String update) {
        if (send.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(send.get(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FkCmd fkCmd = (FkCmd) o;
        return Objects.equals(send, fkCmd.send);
    }

    @Override
    public int hashCode() {
        return Objects.hash(send);
    }

    @Override
    public String toString() {
        return "FkCmd{" +
                "send=" + send +
                '}';
    }
}
