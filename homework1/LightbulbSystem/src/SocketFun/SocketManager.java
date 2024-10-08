package SocketFun;

import PortInAndOut.PortDataInManager;
import PortInAndOut.PortDataOutManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SocketManager {
    private static Map<Integer, SocketWrapper> socketWrapperMap;

    private static Map<Integer, ServerSocket> serverSocketMap;

    private static final String HOST = "localhost";

    private static PortDataInManager portDataInManager;

    private static PortDataOutManager portDataOutManager;


    static {
        socketWrapperMap = new HashMap<>();
        portDataInManager = PortDataInManager.getInstance();
        portDataOutManager = PortDataOutManager.getInstance();
        serverSocketMap = new HashMap<>();
    }

    private SocketManager(){

    }

    public static void createServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        System.out.println("SocketManager: Accepted Connection on: "+port);

        socketWrapperMap.put(port, new SocketWrapper(clientSocket, true));
        serverSocketMap.put(port,serverSocket);

        createNewPortsOnPortManagers(port);
    }

    public static Socket createSocket(int port) throws IOException {
        Socket socket = new Socket(HOST,port);
        socketWrapperMap.put(port,new SocketWrapper(socket, false));

        createNewPortsOnPortManagers(port);
        return socket;
    }

    private static void reconnect(int port){

        if(socketWrapperMap.get(port).isConnectionFromServerSocketAccept()){

            CompletableFuture<Socket> future = CompletableFuture.supplyAsync(() ->{
                System.out.println("Waiting for service to connect to "+port);
                Socket socket;
                try {
                    socket = serverSocketMap.get(port).accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return socket;
            });

            future.thenAccept(socket->{
                if(socket == null){
                    return;
                }

                try {
                    socketWrapperMap.get(port).updateWithNewConnection(socket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else{
            CompletableFuture<Socket> future = CompletableFuture.supplyAsync(()-> {

                final int retryAmount = 10;
                int currentTry = 0;
                Socket socket = null;

                while(currentTry < retryAmount) {
                    try {
                        socket = new Socket(HOST, port);
                    } catch (IOException e) {
                        System.out.println("Retrying connection to port" + port + " in 1 second...");
                        try {
                            Thread.sleep(1000);
                            currentTry++;
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

                if(socket == null){
                    System.out.println("Could not connect to port "+port);
                }
                return socket;
            });

            future.thenAccept(socket->{
                
                if(socket == null){
                    return;
                }

                try {
                    socketWrapperMap.get(port).updateWithNewConnection(socket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


        }
    }

    public static void unableToConnectToPort(int port){
        if(socketWrapperMap.get(port).isReadyToUse()){
            socketWrapperMap.get(port).setReadyToUse(false);
        }
        reconnect(port);
    }

    public static boolean isReadyToUse(int port){
        return socketWrapperMap.get(port).isReadyToUse();
    }

    public static BufferedReader getBufferedReader(int port){
        return socketWrapperMap.get(port).getBufferedReader();
    }

    public static PrintWriter getPrintWriter(int port){
        return socketWrapperMap.get(port).getPrintWriter();
    }

    private static void createNewPortsOnPortManagers(int port) throws IOException {
        portDataInManager.createNewPortDataIn(port);
        portDataOutManager.createNewPortDataOut(port);
    }
}
