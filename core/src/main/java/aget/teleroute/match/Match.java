package aget.teleroute.match;


import aget.teleroute.update.Update;

public interface Match<SrcUpdate> {
    boolean match(Update<SrcUpdate> update);
}
