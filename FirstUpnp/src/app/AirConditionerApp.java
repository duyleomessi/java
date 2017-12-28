package app;

import action.SetAirConditionerStatusAction;
import action.SetAirConditionerTempAction;
import device.Device;
import device.DeviceApp;
import service.AirConditioner;
import view.AirConditionerView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.state.StateVariableValue;

import java.io.IOException;
import java.util.Map;
import javafx.application.Platform;
import org.fourthline.cling.model.message.header.STAllHeader;

public class AirConditionerApp extends DeviceApp {
    private final int NUMBER_DEVICES = 2;
    private Device currentDevice;
    private AirConditionerView airConditionerViewController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        super.start(primaryStage);
        primaryStage.setTitle("Air Conditioner");
        initializeDevices(NUMBER_DEVICES, "AirC", "AirConditioner", "Using control AirConditioner", AirConditioner.class);
        initializeRootLayout();
        // Set id for services
        setServiceIds("AirConditioner");

        // Populate combobox id options
        airConditionerViewController.populateAirCList(devices);
    }

    @Override
    public void onPropertyChangeCallbackReceived(GENASubscription subscription) {
        Map<String, StateVariableValue> values = subscription.getCurrentValues();
//        StateVariableValue idVar = values.get("Id");
        String device = subscription.getService().getDevice().getDetails().getFriendlyName();
        
        
        if (device != null) {
//            String id = (String) idVar.getValue();
            // Only update current selected device
            if (device.contains("AirC")) {
                StateVariableValue status = values.get("Status");
                StateVariableValue temp = values.get("Temperature");
                if(status != null) {
                    System.out.println("AirC Status change " + (boolean)status.getValue());
                    airConditionerViewController.updateStatusUI((boolean) status.getValue());
                }
//                if(temp != null) {
                    airConditionerViewController.updateTempUI(temp.getValue().toString());
//                }
                
            }
        }
    }

    private void initializeRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/AirConditionerView.fxml"));
            AnchorPane rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Set app reference for controller
            airConditionerViewController = loader.getController();
            airConditionerViewController.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set device that is currently inspected and initialize data callback
     *
     * @param index
     */
    public void setCurrentDevice(int index) {
        currentDevice = devices[index];
        Service service = getService(currentDevice.getDevice(), "AirConditioner");

        if (service != null) {
            initializePropertyChangeCallback(upnpService, service);
        }
    }

    /**
     * Set status value of current selected air conditioner
     *
     * @param state
     */
    public void setAirCState(boolean state) {
        Service service = getService(currentDevice.getDevice(), "AirConditioner");

        if (service != null) {
            executeAction(upnpService, new SetAirConditionerStatusAction(service, state));
        }
    }
    
    public void setAirConditionTemperature(String tempS) {
        Service service = getService(currentDevice.getDevice(), "AirConditioner");
        
        if (service != null && tempS != null && tempS.matches("[-+]?\\d*\\.?\\d+")) {
            double temp = Double.parseDouble(tempS);
            executeAction(upnpService, new SetAirConditionerTempAction(service, temp));
        }
    }

    /**
     * Get status value from current selected air conditioner
     */
    public void getAirCStatus() {
        Service service = getService(currentDevice.getDevice(), "AirConditioner");

        if (service != null) {
            Action getStatusAction = service.getAction("GetStatus");
            ActionInvocation actionInvocation = new ActionInvocation(getStatusAction);
            ActionCallback getStatusCallback = new ActionCallback(actionInvocation) {
                @Override
                public void success(ActionInvocation invocation) {
                    ActionArgumentValue status = invocation.getOutput("ResultStatus");
                    airConditionerViewController.updateStatusUI((boolean) status.getValue());
                }

                @Override
                public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                    System.err.println(defaultMsg);
                }
            };
            upnpService.getControlPoint().execute(getStatusCallback);
        }
    }
}