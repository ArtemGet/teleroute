package com.github.ArtemGet.teleroute.command;

import com.github.ArtemGet.teleroute.send.FkSend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MultiCmdTest {

    @Test
    public void execute_shouldSendOne_whenSubmitOne() {
        Assertions.assertTrue(
                new MultiCmd<>(
                        new FkCmd(
                                new FkSend()
                        )
                )
                .execute("resp")
                .isPresent()
        );
    }

    @Test
    public void execute_shouldNotSend_whenError() {
        Assertions.assertTrue(
                new MultiCmd<>(new FkCmdErr())
                        .execute("resp")
                        .isEmpty()
        );
    }
}
