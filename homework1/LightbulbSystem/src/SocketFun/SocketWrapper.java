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

    private boolean readyToUse;

    private boolean connectionFromServerSocketAccept;

    public SocketWrapper(Socket socket, boolean connectionIsFromServerSocketAccept) throws IOException {
        this.connectionFromServerSocketAccept = connectionIsFromServerSocketAccept;
        this.socket = socket;
        init(socket);
    }

    public BufferedReader getBufferedReader() {
        return this.bufferedReader;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setReadyToUse(boolean readyToUse) {
        this.readyToUse = readyToUse;
    }

    public boolean isReadyToUse() {
        return readyToUse;
    }

    private void init(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream());
        readyToUse = true;
    }

    public void updateWithNewConnection(Socket socket) throws IOException {
        this.socket = socket;
        init(socket);
    }

    public boolean isConnectionFromServerSocketAccept() {
        return connectionFromServerSocketAccept;
    }
}
