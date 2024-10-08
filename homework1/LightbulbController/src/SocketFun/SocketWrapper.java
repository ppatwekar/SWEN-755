package SocketFun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketWrapper {
    private Socket socket;

    private BufferedReader bufferedReader;

    private PrintWriter printWriter;

    private boolean isReadyToUse;

    public SocketWrapper(Socket socket) throws IOException {
        this.socket = socket;
        init();
    }

    private void init() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream());
        setReadyToUse(true);
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void updateSocket(Socket socket) throws IOException {
        this.socket = socket;
        init();
        setReadyToUse(true);
    }

    public synchronized void setReadyToUse(boolean readyToUse) {
        isReadyToUse = readyToUse;
    }

    public synchronized boolean isReadyToUse() {
        return isReadyToUse;
    }
}
