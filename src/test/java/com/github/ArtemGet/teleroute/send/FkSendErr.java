package com.github.ArtemGet.teleroute.send;

public class FkSendErr implements Send<FkClient> {
    @Override
    public void send(FkClient send) {
        throw new RuntimeException();
    }
}
