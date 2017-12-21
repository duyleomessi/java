package view;

import app.ControlPointApp;
import device.DeviceApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ControlPointView {
    private ControlPointApp app;
    private final int NUMBER_ROOM = 2;
    private ObservableList<String> roomList = FXCollections.observableArrayList();
    
    private Map<String, List> sub = new HashMap<String, List>();
    private List sensor,airC = new ArrayList();
    private int selectedRoom = -1;
    
    @FXML
    private TextField sensorTemp;
    @FXML
    private TextField airTemp;
    @FXML
    private TextField newAirTemp;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private ComboBox comboBox;
    
    @FXML
    private void initialize() {
        
        System.out.println();
        for (int i = 0; i < NUMBER_ROOM; i++)
        {
            roomList.add("Room"+ (i));
        }
        
        comboBox.setItems(roomList);
    };

    @FXML
    private void onSelectRoom() {
        String selectedStr = comboBox.getValue().toString();
        selectedStr = selectedStr.replace("Room", "");
        selectedRoom = Integer.valueOf(selectedStr);
        
        updateUI();
    }

    @FXML
    private void onUpdateField() {
        String selectedStr = comboBox.getValue().toString();
        selectedStr = selectedStr.replace("Room", "");
        
        app.setNewAirTemp(selectedStr, Double.parseDouble(newAirTemp.getText()));
    }
    
    private void updateUI() {
        if(sub != null) {
            sensor = sub.get("Temp"+selectedRoom);
            airC = sub.get("AirC"+selectedRoom);
        }
//        System.out.println(sensor.get(0).toString());
        if(sensor != null) {
            if(sensor.get(0).toString().contains("true")) {
                sensorTemp.setText(sensor.get(1).toString());
            }
            else {
                sensorTemp.setText("Unavailable");
            }
        }
        
        if(airC != null) {
            if(airC.get(0).toString().contains("true")) {
                airTemp.setText(airC.get(1).toString());
            }
            else {
                airTemp.setText("Unavailable");
            }
        }
    }
    
    public void updateRoom(String id, boolean status, double temp) {
        List content = new ArrayList();
        
        if(id != null) {
            content.add(Boolean.toString(status));
            content.add(Double.toString(temp));
            sub.put(id, content);
            System.out.println(sub);

            updateUI();
        }
    }    
    
    public void updateSensorTempUI(String id, String temp)
    {
        sensorTemp.setText(temp);
    }
    
    public void updateAirConditionerTempUI(String temp)
    {
        airTemp.setText(temp);
    }
    
    public void setApp(ControlPointApp app) {
        this.app = app;
    }
}