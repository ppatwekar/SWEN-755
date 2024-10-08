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


    private static int MAX_WAITING_TIME = 5000;

    private long lastUpdatedHeartBeatTimeInMills;

    private FaultMonitorService faultMonitorService;

    private boolean faultDetected;

    private boolean processedSingleInputData;

    public HeartBeatReceiver(FaultMonitorService faultMonitorService, SinglePortDataIn singlePortDataIn) throws IOException {
        lastUpdatedHeartBeatTimeInMills = System.currentTimeMillis();
        this.faultMonitorService = faultMonitorService;
        this.processedSingleInputData = false;
        this.faultDetected = false;
    }

    // Detects fault by checking if the heartbeat is null or if the waiting time exceeds the threshold
    private boolean detectFault(String beat) {
        if (beat == null) {
            System.out.println("Null Heartbeat Detected. Sending to Fault Monitor.");
            faultMonitorService.reportFault("null"); // Reporting a null fault
            return true;
        }
        return false;
    }

    private boolean detectFault(long currentTime){
        if (currentTime - lastUpdatedHeartBeatTimeInMills > MAX_WAITING_TIME) {
            System.out.println("Time-based Failure Detected. Sending to Fault Monitor.");
            faultMonitorService.reportFault("time"); // Reporting a time-based fault
            return true;
        }
        return false;
    }

    @Override
    protected void processInput(String beat) {
        System.out.println("222222 "+beat);
        if (detectFault(beat)) {
            return; // Exit if fault detected
        }
        processedSingleInputData = true;
        lastUpdatedHeartBeatTimeInMills = System.currentTimeMillis();

        System.out.println("Received '" + beat + "' from LightBulb System");
    }

    @Override
    protected void processSingleInputData() {
        super.processSingleInputData();
        long currentTime = System.currentTimeMillis();

        if(!processedSingleInputData && !this.faultDetected) {
            if(detectFault(currentTime)){
                this.faultDetected = true;
            }
        }
        else{
            processedSingleInputData = false;
        }

    }
}
