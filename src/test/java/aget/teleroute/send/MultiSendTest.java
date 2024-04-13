package aget.teleroute.send;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MultiSendTest {

    @Test
    public void send_shouldSingle_whenSubmittedSingle() {
        FkRs fkRs = new FkRs();
        new MultiSend<>(new FkSend("resp")).send(fkRs);

        Assertions.assertEquals(
                List.of("resp"),
                fkRs.containing()
        );
    }

    @Test
    public void send_shouldMulti_whenSubmittedMulti() {
        FkRs fkRs = new FkRs();
        new MultiSend<>(
                new FkSend("resp1"),
                new FkSend("resp2"),
                new FkSend("resp3")
        ).send(fkRs);

        Assertions.assertEquals(
                List.of("resp1", "resp2", "resp3"),
                fkRs.containing()
        );
    }

    @Test
    public void send_shouldNotSend_whenError() {
        FkRs fkRs = new FkRs();
        new MultiSend<>(new FkSendErr()).send(fkRs);

        Assertions.assertEquals(
                List.of(),
                fkRs.containing()
        );
    }

    @Test
    public void send_shouldSendOnlyOne_whenManyError() {
        FkRs fkRs = new FkRs();
        new MultiSend<>(
                new FkSendErr(),
                new FkSendErr(),
                new FkSend("resp")
        ).send(fkRs);

        Assertions.assertEquals(
                List.of("resp"),
                fkRs.containing()
        );
    }
}
