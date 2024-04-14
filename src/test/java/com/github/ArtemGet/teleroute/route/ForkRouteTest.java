package com.github.ArtemGet.teleroute.route;

import com.github.ArtemGet.teleroute.command.FkCmd;
import com.github.ArtemGet.teleroute.match.FkMatch;
import com.github.ArtemGet.teleroute.send.FkSend;
import com.github.ArtemGet.teleroute.update.FkUpdWrap;
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
                )
                .route(new FkUpdWrap())
                .get()
        );
    }

    @Test
    void route_shouldRouteEmpty_whenNotMatchAndNoSpareCmd() {
        Assertions.assertTrue(
                new ForkRoute<>(
                        new FkMatch(false),
                        new FkCmd(new FkSend())
                )
                .route(new FkUpdWrap())
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
                )
                .route(new FkUpdWrap())
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
                )
                .route(new FkUpdWrap())
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
                )
                .route(new FkUpdWrap())
                .get()
        );
    }

    @Test
    void route_shouldRouteEmpty_whenNotMatchAndNoSpareRoute() {
        Assertions.assertTrue(
                new ForkRoute<>(
                        new FkMatch(false),
                        new EndRoute<>(new FkCmd(new FkSend()))
                )
                .route(new FkUpdWrap())
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
                )
                .route(new FkUpdWrap())
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
                )
                .route(new FkUpdWrap())
                .get()
        );
    }
}
