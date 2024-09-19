package PortInAndOut;

import controller.PortDataInController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SinglePortDataIn implements Runnable{

    private Socket socket;

    private BufferedReader bufferedReader;

    private PortDataInController portDataInController;


    public SinglePortDataIn(Socket socket, PortDataInController portDataInController) throws IOException {
        this.socket = socket;
        this.portDataInController = portDataInController;
        initializeSocketInput();
    }

    private void initializeSocketInput() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        while(true){
            try {
                String dataIn = bufferedReader.readLine();
                if(dataIn != null) {
                    portDataInController.processInput(dataIn);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
