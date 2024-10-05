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

public class SocketManager {
    private static Map<Integer, SocketWrapper> socketWrapperMap;

    private static final String HOST = "localhost";

    private static PortDataInManager portDataInManager;

    private static PortDataOutManager portDataOutManager;

    static {
        socketWrapperMap = new HashMap<>();
        portDataInManager = PortDataInManager.getInstance();
        portDataOutManager = PortDataOutManager.getInstance();
    }

    private SocketManager(){

    }

    public static void createServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        System.out.println("SocketManager: Accepted Connection on: "+port);
        socketWrapperMap.put(port, new SocketWrapper(clientSocket));

        createNewPortsOnPortManagers(port);
    }

    public static Socket createSocket(int port) throws IOException {
        Socket socket = new Socket(HOST,port);
        socketWrapperMap.put(port,new SocketWrapper(socket));

        createNewPortsOnPortManagers(port);
        return socket;
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
