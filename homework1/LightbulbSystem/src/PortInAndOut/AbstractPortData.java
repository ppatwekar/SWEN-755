package PortInAndOut;

public abstract class AbstractPortData implements Runnable{
    protected int port;

    public AbstractPortData(int port) {
        this.port = port;
    }
}
