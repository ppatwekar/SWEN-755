public class FaultMonitorService {

    //    public void reportFault(){
//        System.out.println("Fault detected in the Lightbulbsystem");
//
//    }
//
//}
    public void reportFault(String faultType, String details) {
        System.out.println("Fault detected in the Lightbulb system.");
        switch (faultType) {
            case "NULL_FAULT":
                System.out.println("Fault Type: Null Pointer Exception.");
                break;
            case "TIME_FAULT":
                System.out.println("Fault Type: Time-based failure.");
                break;
            case "SOCKET_FAULT":
                System.out.println("Fault Type: Socket Exception.");
                break;
            default:
                System.out.println("Fault Type: Unknown.");
                break;
        }
        System.out.println("Details: " + details);
    }

    // Example methods to simulate different types of faults
    public void reportNullFault() {
        reportFault("NULL_FAULT", "A null object was encountered in the system.");
    }

    public void reportTimeFault() {
        reportFault("TIME_FAULT", "The system timed out waiting for a response.");
    }

    public void reportSocketFault() {
        reportFault("SOCKET_FAULT", "Socket connection reset or closed unexpectedly.");
    }

    // Simulating different fault scenarios
    public static void main(String[] args) {
        FaultMonitorService monitor = new FaultMonitorService();
        monitor.reportNullFault();
        monitor.reportTimeFault();
        monitor.reportSocketFault();
    }
}

