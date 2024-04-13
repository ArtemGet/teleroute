package aget.teleroute.send;

public class FkSendErr implements Send<FkRs> {
    @Override
    public void send(FkRs send) {
        throw new RuntimeException();
    }
}
