package app;

import action.GetDeviceIdAction;
import action.SetAirConditionerTempAction;
import view.ControlPointView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.UDAServiceId;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import org.fourthline.cling.model.meta.Action;

public class ControlPointApp extends Application {
//    private ParkingGraph parkingGraph = new ParkingGraph();
    private HashMap<String, RemoteDevice> controlledDevices;
    private final UpnpService upnpService = new UpnpServiceImpl();
    private Stage primaryStage;
    private ControlPointView controlPointView;
    private int upperThreshold = 28, lowerThreshold = 10;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
//        primaryStage.setTitle("Smart Parking Control Panel");

        try {
            controlledDevices = new HashMap<>();
            initializeRootLayout();
            // Add a listener for device registration events
            upnpService.getRegistry().addListener(
                    createRegistryListener(upnpService)
            );

            // Broadcast a search message for all devices
            upnpService.getControlPoint().search(
                    new STAllHeader()
            );

            
        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            System.exit(1);
        }
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    private void initializeRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/ControlPoint.fxml"));
            AnchorPane rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Set app reference for controller
            controlPointView = loader.getController();
            controlPointView.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializePropertyChangeCallback(UpnpService upnpService, Service service) {
        SubscriptionCallback callback = new SubscriptionCallback(service, 600) {

            @Override
            public void established(GENASubscription sub) {
//                System.out.println("Established: " + sub.getSubscriptionId());
            }

            @Override
            protected void failed(GENASubscription subscription, UpnpResponse responseStatus, Exception exception, String defaultMsg) {
                System.err.println(defaultMsg);
            }

            @Override
            public void ended(GENASubscription sub, CancelReason reason, UpnpResponse response) {
                System.out.println(reason.toString());
            }

            @Override
            public void eventReceived(GENASubscription sub) {
                Map<String, StateVariableValue> values = new HashMap<>();
                values = sub.getCurrentValues();
                System.out.println(sub.getCurrentValues());
//                System.out.println(sub.getService().getAction("GetId"));
//                StateVariableValue idVar = values.get("Id");
                
//                Action getIdAction = sub.getService().getAction("GetId");
//                ActionInvocation getIdInvocation = new ActionInvocation(getIdAction);
//                new ActionCallback.Default(getIdInvocation, upnpService.getControlPoint()).run();
                System.out.println(sub.getService().getDevice().getDetails().getFriendlyName());
//                System.out.println(Arrays.toString(getIdInvocation.getOutput()));
                String device = sub.getService().getDevice().getDetails().getFriendlyName();
                if (device != null && values != null) {
                    if (device.contains("Temp") || device.contains("AirC")) {
                        System.out.println(values);
                        
                        boolean status = (boolean) values.get("Status").getValue();
                        double temp = (double)values.get("Temperature").getValue();

                        onDataChange(device, status, temp);   
                    }
                    
                    if (device.contains("Temp")) {
                        boolean status = (boolean) values.get("Status").getValue();
                        double temp = (double)values.get("Temperature").getValue();
                        
                        if(status && (temp > upperThreshold || temp < lowerThreshold)) {
                            onReachThresHold(device);
                        }
                    }
                }
            }

            @Override
            public void eventsMissed(GENASubscription sub, int numberOfMissedEvents) {
                System.out.println("Missed events: " + numberOfMissedEvents);
                System.out.println(sub.getCurrentValues());
            }

            @Override
            protected void invalidMessage(RemoteGENASubscription sub, UnsupportedDataException ex) {
                // Log/send an error report?
            }
        };

        upnpService.getControlPoint().execute(callback);
    }
    

    private RegistryListener createRegistryListener(final UpnpService upnpService) {
        return new DefaultRegistryListener() {
            @Override
            public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
                String deviceId = device.getDetails().getFriendlyName();
                if (deviceId.contains("Temp")) {
                    // Add device to hashmap
                    System.out.println(deviceId);
                    // Set data change callback
                    controlledDevices.put(deviceId, device);
                    Service tempSensorService = device.findService(new UDAServiceId("TemperatureSensor"));
//                    executeGetIdAction(upnpService, new GetDeviceIdAction(tempSensorService) );
//                    System.out.println("updatedId:" +updatedId);

//                    Action getIdAction = tempSensorService.getAction("GetId");
//                    ActionInvocation getIdInvocation = new ActionInvocation(getIdAction);
//                    new ActionCallback.Default(getIdInvocation, upnpService.getControlPoint()).run();
//                    
//                    System.out.println(Arrays.toString(getIdInvocation.getOutput()));
                    if (tempSensorService != null) {
                        initializePropertyChangeCallback(upnpService, tempSensorService);
                    }
                }

                if (deviceId.contains("Air")) {
                    // Add device to hashmap
                    System.out.println(deviceId);
                    controlledDevices.put(deviceId, device);

                    // Set data change callback
                    Service airConditionerService = device.findService(new UDAServiceId("AirConditioner"));
//                    Action getIdAction = airConditionerService.getAction("GetId");
//                    ActionInvocation getIdInvocation = new ActionInvocation(getIdAction);
//                    new ActionCallback.Default(getIdInvocation, upnpService.getControlPoint()).run();
//                    
//                    System.out.println(getIdInvocation.getOutput("ResultAirId"));
                    if (airConditionerService != null) {
                        initializePropertyChangeCallback(upnpService, airConditionerService);
                    }
                }

                System.out.println("Device discovered: " + deviceId);
            }
            
//            @Override
//            public void remoteDeviceUpdated(Registry registry, RemoteDevice device) { 
//                String deviceId = device.getDetails().getFriendlyName();
//                if (deviceId.contains("Temp")) {
//                    // Add device to hashmap
//                    System.out.println(deviceId);
//                    // Set data change callback
//                    controlledDevices.put(deviceId, device);
//                    Service tempSensorService = device.findService(new UDAServiceId("TemperatureSensor"));
////                    executeGetIdAction(upnpService, new GetDeviceIdAction(tempSensorService) );
////                    System.out.println("updatedId:" +updatedId);
//
//                    
//                    Action getIdAction = tempSensorService.getAction("GetId");
//                    ActionInvocation getIdInvocation = new ActionInvocation(getIdAction);
//                    new ActionCallback.Default(getIdInvocation, upnpService.getControlPoint()).run();
//                    
//                    System.out.println(Arrays.toString(getIdInvocation.getOutput()));
//                    if (tempSensorService != null) {
//                        initializePropertyChangeCallback(upnpService, tempSensorService);
//                    }
//                }
//
//                if (deviceId.contains("Air")) {
//                    // Add device to hashmap
//                    System.out.println(deviceId);
//                    
//
//                    // Set data change callback
//                    Service airConditionerService = device.findService(new UDAServiceId("AirConditioner"));
//                    Action getIdAction = airConditionerService.getAction("GetId");
//                    ActionInvocation getIdInvocation = new ActionInvocation(getIdAction);
//                    new ActionCallback.Default(getIdInvocation, upnpService.getControlPoint()).run();
//                    
//                    System.out.println(getIdInvocation.getOutput("ResultAirId").toString());
//                    if (airConditionerService != null && !getIdInvocation.getOutput("ResultAirId").toString().equals("0")) {
//                        controlledDevices.put(deviceId, device);
//                        initializePropertyChangeCallback(upnpService, airConditionerService);
//                    }
//                }
//
//                System.out.println("Device discovered: " + deviceId);
//            }

            @Override
            public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
                String deviceId = device.getDetails().getFriendlyName();

                // Remove device from hashmap
                controlledDevices.remove(deviceId);
                System.out.println("Device disappeared: " + deviceId);
            }
        };
    }

    private void executeAction(UpnpService upnpService, ActionInvocation action) {
        // Executes asynchronous in the background
        upnpService.getControlPoint().execute(
                new ActionCallback(action) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called action " + invocation.getClass().getSimpleName());
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    private void onDataChange(String device,boolean status,double temp) {
//        System.out.println(id);
          
        controlPointView.updateRoom(device, status, temp);  
    }
    
    private void onReachThresHold(String device) {
        String selectedStr = device.replace("Temp", "");
        Service service = controlledDevices.get("AirC"+selectedStr).findService(new UDAServiceId("AirConditioner"));
        executeAction(upnpService, new SetAirConditionerTempAction(service, (upperThreshold+lowerThreshold)/2 ));
    }
    
    public void setNewAirTemp(String roomId, double newtemp) {
        Service service = controlledDevices.get("AirC"+roomId).findService(new UDAServiceId("AirConditioner"));
        if(service != null)
        {
            executeAction(upnpService, new SetAirConditionerTempAction(service, newtemp));
        }
    }
}