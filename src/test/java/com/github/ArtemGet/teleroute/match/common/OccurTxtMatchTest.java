package com.github.ArtemGet.teleroute.match.common;

import com.github.ArtemGet.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OccurTxtMatchTest {

    @Test
    void match_shouldMatch_whenFullTextMatch() {
        Assertions.assertTrue(
                new OccurTxtMatch<String>("text").match(new FkUpdWrap())
        );
    }

    @Test
    void match_shouldMatch_whenTextOccurs() {
        Assertions.assertTrue(
                new OccurTxtMatch<String>("te").match(new FkUpdWrap())
        );
    }

    @Test
    void match_shouldNotMatch_whenTextNotOccurs() {
        Assertions.assertFalse(
                new OccurTxtMatch<String>("not").match(new FkUpdWrap())
        );
    }
}