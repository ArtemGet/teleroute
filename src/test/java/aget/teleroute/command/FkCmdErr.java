package aget.teleroute.command;

import aget.teleroute.send.FkClient;
import aget.teleroute.send.Send;

import java.util.Optional;

public class FkCmdErr implements Cmd<String, FkClient> {
    @Override
    public Optional<Send<FkClient>> execute(String update) {
        throw new RuntimeException();
    }
}
