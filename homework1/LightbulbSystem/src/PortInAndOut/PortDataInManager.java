package PortInAndOut;

import AsyncAbstractSystemComponents.DataInSubscriber;
import controller.PortDataInController;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class PortDataInManager {
    private ConcurrentHashMap<Integer, PortDataInController> portMap;

    private static PortDataInManager portDataInManagerInstance;

    private PortDataInManager(){
        portMap = new ConcurrentHashMap<>();
    }

    public static PortDataInManager getInstance(){
        if(portDataInManagerInstance == null){
            portDataInManagerInstance = new PortDataInManager();
        }
        return portDataInManagerInstance;
    }


    public void createNewPortDataIn(int port) throws IOException {
        PortDataInController portDataInController = new PortDataInController();
        SinglePortDataIn singlePortDataIn = new SinglePortDataIn(port, portDataInController);

        portMap.put(port,portDataInController);

        new Thread(singlePortDataIn).start();
    }

    public void subscribe(int port, DataInSubscriber dataInSubscriber, String ...keys){
        portMap.get(port).subscribe(dataInSubscriber,keys);
    }
}
