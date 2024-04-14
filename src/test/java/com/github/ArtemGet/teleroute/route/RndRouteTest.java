package com.github.ArtemGet.teleroute.route;

import com.github.ArtemGet.teleroute.command.Cmd;
import com.github.ArtemGet.teleroute.command.FkCmd;
import com.github.ArtemGet.teleroute.send.FkClient;
import com.github.ArtemGet.teleroute.send.FkSend;
import com.github.ArtemGet.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class RndRouteTest {
    private static final Set<Cmd<String, FkClient>> CMD_SET =
            Set.of(new FkCmd(), new FkCmd(new FkSend("resp")));

    @Test
    void route_shouldRouteAny_whenManyCmdSpecified() {
        Assertions.assertTrue(
                CMD_SET.contains(
                        new RndRoute<>(
                                new FkCmd(),
                                new FkCmd(new FkSend("resp"))
                        ).route(new FkUpdWrap()).get()
                )
        );
    }

    @Test
    void route_shouldRouteAny_whenManyRouteSpecified() {
        Assertions.assertTrue(
                CMD_SET.contains(
                        new RndRoute<>(
                                new EndRoute<>(new FkCmd()),
                                new EndRoute<>(new FkCmd(new FkSend("resp")))
                        ).route(new FkUpdWrap()).get()
                )
        );
    }

    @Test
    void route_shouldRoute_whenOneCmdSpecified() {
        Assertions.assertEquals(
                new FkCmd(new FkSend("resp")),
                new RndRoute<>(
                        new FkCmd(new FkSend("resp"))
                ).route(new FkUpdWrap()).get()
        );
    }

    @Test
    void route_shouldRouteOne_whenOneRouteSpecified() {
        Assertions.assertEquals(
                new FkCmd(new FkSend("resp")),
                new RndRoute<>(
                        new EndRoute<>(new FkCmd(new FkSend("resp")))
                ).route(new FkUpdWrap()).get()
        );
    }
}
