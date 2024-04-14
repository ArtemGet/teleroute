package aget.teleroute.match.common;

import aget.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CmdMatchTest {

    @Test
    void match_shouldMatch_whenCmd() {
        Assertions.assertTrue(
                new CmdMatch<String>().match(new FkUpdWrap())
        );
    }

    @Test
    void match_shouldNotMatch_whenNotCmd() {
        Assertions.assertFalse(
                new CmdMatch<String>()
                        .match(
                                new FkUpdWrap(
                                        123,
                                        false,
                                        "text",
                                        "src"
                                )
                        )
        );
    }
}
