package view;

import app.AirConditionerApp;
import device.Device;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class AirConditionerView {
    @FXML
    private TextField currentTemp;
    
    @FXML
    private RadioButton availableBut;
    @FXML
    private RadioButton unavailableBut;
    @FXML
    private ComboBox comboBox;

    private ObservableList<String> AirConditionerDeviceIds = FXCollections.observableArrayList();
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private AirConditionerApp app;

    private static final String STATE_AVAILABLE = "available";
    private static final String STATE_UNAVAILABLE = "unavailable";

    @FXML
    private void initialize() {
//        currentTemp.setText("18");
        
        // ComboBox
        comboBox.setItems(AirConditionerDeviceIds);

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

                app.setAirCState(status);
            }
        });
        
        currentTemp.textProperty().addListener((observable, oldValue, newValue) -> {
            app.setAirConditionTemperature(newValue);
        });
        
//        currentTemp.textProperty().bind(observable);
        
//        app.setAirConditionTemperature(currentTemp.getText());
    }

    @FXML
    private void onSelectAirConditioner() {
        String selectedStr = comboBox.getValue().toString();
        selectedStr = selectedStr.replace("AirC", "");
        int selectedIndex = Integer.valueOf(selectedStr);
        app.setCurrentDevice(selectedIndex);
        System.out.println("Select sensor " + selectedIndex);
    }

    public void populateAirCList(Device[] devices)
    {
        for (Device device : devices)
        {
            AirConditionerDeviceIds.add(device.getId());
        }
    }

    public void updateStatusUI(boolean status)
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

    public void setApp(AirConditionerApp app) {
        this.app = app;
    }
}