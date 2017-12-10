package app;

import action.SetTempSensorStatusAction;
import device.Device;
import device.DeviceApp;
import service.TemperatureSensor;
import view.TemperatureSensorView;
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

public class TemperatureSensorApp extends DeviceApp {
    private final int SLOT_NUMBER = 5;
    private Device currentDevice;
    private TemperatureSensorView temperatureSensorViewController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        super.start(primaryStage);
        primaryStage.setTitle("Temperature Sensors");
        initializeDevices(SLOT_NUMBER, "Temp", "TemperatureSensor", "Using for sense Temperature", TemperatureSensor.class);
        initializeRootLayout();

        // Set id for services
        setServiceIds("TemperatureSensor");

        // Populate combobox slot id options
        temperatureSensorViewController.populateSlotSensorList(devices);
    }

    @Override
    public void onPropertyChangeCallbackReceived(GENASubscription subscription) {
        Map<String, StateVariableValue> values = subscription.getCurrentValues();
        StateVariableValue idVar = values.get("Id");

        if (idVar != null) {
            String id = (String) idVar.getValue();
            // Only update current selected device
            if (id.equals(currentDevice.getId())) {
                StateVariableValue status = values.get("Status");
                System.out.println("SLot App Status change " + (boolean)status.getValue());
                temperatureSensorViewController.updateSlotStatusUI((boolean) status.getValue());
            }
        }
    }

    private void initializeRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/TemperatureSensorView.fxml"));
            AnchorPane rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 320, 240);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Set app reference for controller
            temperatureSensorViewController = loader.getController();
            temperatureSensorViewController.setApp(this);
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
        Service service = getService(currentDevice.getDevice(), "TemperatureSensor");

        if (service != null) {
            initializePropertyChangeCallback(upnpService, service);
        }
    }

    /**
     * Set status value of current selected sensor
     *
     * @param state
     */
    public void setSlotSensorState(boolean state) {
        Service service = getService(currentDevice.getDevice(), "TemperatureSensor");

        if (service != null) {
            executeAction(upnpService, new SetTempSensorStatusAction(service, state));
        }
    }

    /**
     * Get status value from current selected sensor
     */
    public void getSlotSensorStatus() {
        Service service = getService(currentDevice.getDevice(), "TemperatureSensor");

        if (service != null) {
            Action getStatusAction = service.getAction("GetStatus");
            ActionInvocation actionInvocation = new ActionInvocation(getStatusAction);
            ActionCallback getStatusCallback = new ActionCallback(actionInvocation) {
                @Override
                public void success(ActionInvocation invocation) {
                    ActionArgumentValue status = invocation.getOutput("ResultStatus");
                    temperatureSensorViewController.updateSlotStatusUI((boolean) status.getValue());
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