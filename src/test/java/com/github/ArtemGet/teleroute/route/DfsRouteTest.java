package com.github.ArtemGet.teleroute.route;

import com.github.ArtemGet.teleroute.command.FkCmd;
import com.github.ArtemGet.teleroute.send.FkClient;
import com.github.ArtemGet.teleroute.send.FkSend;
import com.github.ArtemGet.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DfsRouteTest {

    @Test
    void route_shouldRouteEmpty_whenEmpty() {
        Assertions.assertTrue(
                new DfsRoute<String, FkClient>(new EndRoute<>())
                        .route(new FkUpdWrap())
                        .isEmpty()
        );
    }

    @Test
    void route_shouldRouteEmpty_whenNullUpdate() {
        Assertions.assertTrue(
                new DfsRoute<>(new EndRoute<>(new FkCmd(new FkSend("resp"))))
                        .route(null)
                        .isEmpty()
        );
    }

    @Test
    void route_shouldRouteFirst_whenOneSubmitted() {
        Assertions.assertEquals(
                new FkCmd(new FkSend("resp")),
                new DfsRoute<>(new EndRoute<>(new FkCmd(new FkSend("resp"))))
                        .route(new FkUpdWrap())
                        .get()
        );
    }

    @Test
    void route_shouldRouteFirstSuitable_whenManySubmitted() {
        Assertions.assertEquals(
                new FkCmd(new FkSend("resp1")),
                new DfsRoute<>(
                        new EndRoute<>(),
                        new EndRoute<>(new FkCmd(new FkSend("resp1"))),
                        new EndRoute<>(new FkCmd(new FkSend("resp2")))
                )
                .route(new FkUpdWrap())
                .get()
        );
    }
}
