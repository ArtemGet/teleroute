package aget.teleroute.command;

import aget.teleroute.send.FkRs;
import aget.teleroute.send.Send;

import java.util.Optional;

public class FkCmdErr implements Cmd<String, FkRs> {
    @Override
    public Optional<Send<FkRs>> execute(String update) {
        throw new RuntimeException();
    }
}
