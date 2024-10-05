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

    public SocketWrapper(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream());
    }

    public BufferedReader getBufferedReader() {
        return this.bufferedReader;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }
}
