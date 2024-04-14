package com.github.ArtemGet.teleroute.send;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MultiSendTest {

    @Test
    public void send_shouldSingle_whenSubmittedSingle() {
        FkClient fkClient = new FkClient();
        new MultiSend<>(new FkSend("resp")).send(fkClient);

        Assertions.assertEquals(
                List.of("resp"),
                fkClient.sent()
        );
    }

    @Test
    public void send_shouldMulti_whenSubmittedMulti() {
        FkClient fkClient = new FkClient();
        new MultiSend<>(
                new FkSend("resp1"),
                new FkSend("resp2"),
                new FkSend("resp3")
        ).send(fkClient);

        Assertions.assertEquals(
                List.of("resp1", "resp2", "resp3"),
                fkClient.sent()
        );
    }

    @Test
    public void send_shouldNotSend_whenError() {
        FkClient fkClient = new FkClient();
        new MultiSend<>(new FkSendErr()).send(fkClient);

        Assertions.assertEquals(
                List.of(),
                fkClient.sent()
        );
    }

    @Test
    public void send_shouldSendOnlyOne_whenManyError() {
        FkClient fkClient = new FkClient();
        new MultiSend<>(
                new FkSendErr(),
                new FkSendErr(),
                new FkSend("resp")
        ).send(fkClient);

        Assertions.assertEquals(
                List.of("resp"),
                fkClient.sent()
        );
    }
}
