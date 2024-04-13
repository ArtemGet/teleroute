package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.command.FkCmd;
import aget.teleroute.send.FkRs;
import aget.teleroute.send.FkSend;
import aget.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class RndRouteTest {
    private static final Set<Cmd<String, FkRs>> CMD_SET =
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
