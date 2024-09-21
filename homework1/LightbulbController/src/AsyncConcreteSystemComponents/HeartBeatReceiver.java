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

    // Detects fault by checking if the heartbeat is null or if the waiting time exceeds the threshold
    private boolean detectFault(String beat, long currentTime) {
        if (beat == null) {
            System.out.println("Null Heartbeat Detected. Sending to Fault Monitor.");
            faultMonitorService.reportFault("null"); // Reporting a null fault
            return true;
        }

        if (currentTime - lastUpdatedHeartBeatTimeInMills > MAX_WAITING_TIME) {
            System.out.println("Time-based Failure Detected. Sending to Fault Monitor.");
            faultMonitorService.reportFault("time"); // Reporting a time-based fault
            return true;
        }

        return false;
    }

    @Override
    protected void processInput(String beat) {
        long currentTime = System.currentTimeMillis();

        // Detect any faults in the heartbeat
        if (detectFault(beat, currentTime)) {
            return; // Exit if fault detected
        }

        // Update the last heartbeat received time
        lastUpdatedHeartBeatTimeInMills = currentTime;

        System.out.println("Received '" + beat + "' from LightBulb System");
    }

}
