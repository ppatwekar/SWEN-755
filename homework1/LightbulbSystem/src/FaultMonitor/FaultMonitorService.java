package FaultMonitor;

public class FaultMonitorService {

    //    public void reportFault(){
//        System.out.println("Fault detected in the Lightbulbsystem");
//
//    }
//
//}
    public static void reportFault(String faultType) {
        switch (faultType) {
            case "time":
                handleTimeFault();
                break;
            case "socket":
                handleSocketFault();
                break;
            case "null":
                handleNullFault();
                break;
            default:
                System.out.println("Unknown fault detected");
                break;
        }
        System.exit(0);
    }

    // Fault detection for time-based issues
    private static void handleTimeFault() {
        System.out.println("Time-based fault detected: System timeout or delay");
        // Add specific logic to handle time-related faults, such as retrying operations or triggering alerts.
    }

    // Fault detection for socket-related issues
    private static void handleSocketFault() {
        System.out.println("Socket fault detected: Connection issues");
        // Add logic to handle socket-related faults, such as retrying the connection or logging the error.
    }

    // Fault detection for null value issues
    private static void handleNullFault() {
        System.out.println("Null fault detected: Null reference encountered");
        // Add logic to handle null pointer exceptions or log relevant info.
    }

    // Example main function for testing
    public static void main(String[] args) {

        // Test different fault cases
        FaultMonitorService.reportFault("time");
        FaultMonitorService.reportFault("socket");
        FaultMonitorService.reportFault("null");
        FaultMonitorService.reportFault("unknown");
    }
}

