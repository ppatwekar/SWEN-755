package AsyncConcreteSystemComponents;

import AsyncAbstractSystemComponents.AbstractAsyncInputOnlyComponent;
import FaultMonitor.FaultMonitorService;
import PortInAndOut.SinglePortDataIn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HeartBeatReceiver extends AbstractAsyncInputOnlyComponent {


    private static int MAX_WAITING_TIME = 2000;

    private long lastUpdatedHeartBeatTimeInMills;

    private FaultMonitorService faultMonitorService;

    public HeartBeatReceiver(FaultMonitorService faultMonitorService, SinglePortDataIn singlePortDataIn) throws IOException {
        lastUpdatedHeartBeatTimeInMills = System.currentTimeMillis();
        this.faultMonitorService = faultMonitorService;
    }

    private boolean detectFault(String beat, long currentTime){
        if(beat == null || (currentTime - lastUpdatedHeartBeatTimeInMills > MAX_WAITING_TIME)){
            System.out.println("Failure Detected. Sending to Fault Monitor");
            faultMonitorService.reportFault();
            return true;
        }
        return false;
    }

    @Override
    protected void processInput(String beat) {
        long currentTime = System.currentTimeMillis();

        detectFault(beat,currentTime);

        lastUpdatedHeartBeatTimeInMills = currentTime;

        System.out.println("Received "+beat+" from LightBulb System");

    }

}
