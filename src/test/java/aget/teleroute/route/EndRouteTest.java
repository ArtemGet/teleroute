package aget.teleroute.route;

import aget.teleroute.command.FkCmd;
import aget.teleroute.send.FkClient;
import aget.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EndRouteTest {

    @Test
    public void route_shouldBeEmpty_whenNoCommandSpecified() {
        Assertions.assertTrue(
                new EndRoute<String, FkClient>().route(new FkUpdWrap()).isEmpty()
        );
    }

    @Test
    public void route_shouldReturnFkCmd_whenFkCmdSpecified() {
        Assertions.assertTrue(
                new EndRoute<>(new FkCmd()).route(new FkUpdWrap()).isPresent()
        );
    }
}
