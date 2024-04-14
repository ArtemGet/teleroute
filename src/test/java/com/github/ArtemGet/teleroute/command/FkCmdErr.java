package com.github.ArtemGet.teleroute.command;

import com.github.ArtemGet.teleroute.send.FkClient;
import com.github.ArtemGet.teleroute.send.Send;

import java.util.Optional;

public class FkCmdErr implements Cmd<String, FkClient> {
    @Override
    public Optional<Send<FkClient>> execute(String update) {
        throw new RuntimeException();
    }
}
