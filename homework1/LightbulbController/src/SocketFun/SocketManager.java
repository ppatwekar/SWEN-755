package SocketFun;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class SocketManager {
    private static Socket clientSocket;

    private static ServerSocket serverSocket;

    private static SocketWrapper socketWrapper;

    private static int port;

    public static SocketWrapper createServer(int port) throws IOException {
        SocketManager.port = port;
        serverSocket = new ServerSocket(port);
        System.out.println("Waiting to accept connection...");
        clientSocket = serverSocket.accept();
        socketWrapper = new SocketWrapper(clientSocket);
        return socketWrapper;
    }

    public static void retryConnection(){
        socketWrapper.setReadyToUse(false);

        CompletableFuture<Socket> future = CompletableFuture.supplyAsync(()->{
            Socket socket;
            try {
                System.out.println("Waiting to accept connection...");
                socket = serverSocket.accept();
                System.out.println("Connected!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return socket;
        });

        future.thenAccept(socket -> {
            try {
                socketWrapper.updateSocket(socket);
                System.out.println("Updated Socket!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
