package view;

import app.TemperatureSensorApp;
import device.Device;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class TemperatureSensorView {
    @FXML
    private TextField currentTemp;
    
    @FXML
    private RadioButton availableBut;
    @FXML
    private RadioButton unavailableBut;
    @FXML
    private ComboBox comboBox;

    private ObservableList<String> tempSensorDeviceIds = FXCollections.observableArrayList();
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private TemperatureSensorApp app;

    private static final String STATE_AVAILABLE = "available";
    private static final String STATE_UNAVAILABLE = "unavailable";

    @FXML
    private void initialize() {
        // ComboBox
        comboBox.setItems(tempSensorDeviceIds);
        
        // Radio Button
        availableBut.setUserData(STATE_AVAILABLE);
        unavailableBut.setUserData(STATE_UNAVAILABLE);
        availableBut.setToggleGroup(toggleGroup);
        unavailableBut.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                boolean status = false;
                // Parse status value
                String statusStr = (String) toggleGroup.getSelectedToggle().getUserData();
                if (statusStr.equals(STATE_AVAILABLE)) {
                    status = true;
                } else if (statusStr.equals(STATE_UNAVAILABLE)) {
                    status = false;
                }

                app.setTempSensorState(status);
            }
        });
        
        currentTemp.textProperty().addListener((observable, oldValue, newValue) -> {
            app.setTempSensorTempAction(newValue);
        });
    }

    @FXML
    private void onSelectSensor() {
        String selectedStr = comboBox.getValue().toString();
        selectedStr = selectedStr.replace("Temp", "");
        int selectedIndex = Integer.valueOf(selectedStr);
        app.setCurrentDevice(selectedIndex);
        System.out.println("Select sensor " + selectedIndex);
    }
    
    public void populateTempSensorList(Device[] devices)
    {
        for (Device device : devices)
        {
            tempSensorDeviceIds.add(device.getId());
        }
    }

    public void updateSensorStatusUI(boolean status)
    {
        if(!status)
        {
            unavailableBut.fire();
        }
        else
        {
            availableBut.fire();
        }
    }
    
    public void updateTempUI(String temp)
    {
        currentTemp.setText(temp);
    }

    public void setApp(TemperatureSensorApp app) {
        this.app = app;
    }
}