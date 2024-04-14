package aget.teleroute.send;

public class FkSendErr implements Send<FkClient> {
    @Override
    public void send(FkClient send) {
        throw new RuntimeException();
    }
}
