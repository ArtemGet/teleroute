package aget.teleroute.route;

import aget.teleroute.command.FkCmd;
import aget.teleroute.match.FkMatch;
import aget.teleroute.send.FkSend;
import aget.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ForkRouteTest {

    @Test
    void route_shouldRouteCmd_whenMatchAndNoSpareCmd() {
        Assertions.assertEquals(
                new FkCmd(new FkSend()),
                new ForkRoute<>(
                        new FkMatch(),
                        new FkCmd(new FkSend())
                ).route(new FkUpdWrap())
                        .get()
        );
    }

    @Test
    void route_shouldRouteEmpty_whenNotMatchAndNoSpareCmd() {
        Assertions.assertTrue(
                new ForkRoute<>(
                        new FkMatch(false),
                        new FkCmd(new FkSend())
                ).route(new FkUpdWrap())
                        .isEmpty()
        );
    }

    @Test
    void route_shouldRouteCmd_whenMatchAndSpareCmd() {
        Assertions.assertEquals(
                new FkCmd(new FkSend()),
                new ForkRoute<>(
                        new FkMatch(),
                        new FkCmd(new FkSend()),
                        new FkCmd()
                ).route(new FkUpdWrap())
                        .get()
        );
    }

    @Test
    void route_shouldRouteSpareCmd_whenNotMatchAndSpareCmd() {
        Assertions.assertEquals(
                new FkCmd(),
                new ForkRoute<>(
                        new FkMatch(false),
                        new FkCmd(new FkSend()),
                        new FkCmd()
                ).route(new FkUpdWrap())
                        .get()
        );
    }

    //routes

    @Test
    void route_shouldRoute_whenMatchAndNoSpareRoute() {
        Assertions.assertEquals(
                new FkCmd(new FkSend()),
                new ForkRoute<>(
                        new FkMatch(),
                        new EndRoute<>(new FkCmd(new FkSend()))
                ).route(new FkUpdWrap())
                        .get()
        );
    }

    @Test
    void route_shouldRouteEmpty_whenNotMatchAndNoSpareRoute() {
        Assertions.assertTrue(
                new ForkRoute<>(
                        new FkMatch(false),
                        new EndRoute<>(new FkCmd(new FkSend()))
                ).route(new FkUpdWrap())
                        .isEmpty()
        );
    }

    @Test
    void route_shouldRoute_whenMatchAndSpareRoute() {
        Assertions.assertEquals(
                new FkCmd(new FkSend()),
                new ForkRoute<>(
                        new FkMatch(),
                        new EndRoute<>(new FkCmd(new FkSend())),
                        new EndRoute<>(new FkCmd())
                ).route(new FkUpdWrap())
                        .get()
        );
    }

    @Test
    void route_shouldRouteSpareRoute_whenNotMatchAndSpareRoute() {
        Assertions.assertEquals(
                new FkCmd(),
                new ForkRoute<>(
                        new FkMatch(false),
                        new EndRoute<>(new FkCmd(new FkSend())),
                        new EndRoute<>(new FkCmd())
                ).route(new FkUpdWrap())
                        .get()
        );
    }
}
