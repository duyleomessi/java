package service;

import org.fourthline.cling.binding.annotations.*;
import org.fourthline.cling.model.ModelUtil;

import java.beans.PropertyChangeSupport;

@UpnpService(
        serviceId = @UpnpServiceId("TemperatureSensor"),
        serviceType = @UpnpServiceType(value = "TemperatureSensor", version = 1)
)

public class TemperatureSensor {
    private final PropertyChangeSupport propertyChangeSupport;

    public TemperatureSensor() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(defaultValue = "0")
    private String id;

    @UpnpStateVariable(defaultValue = "0")
    private boolean status = false; 
    
    @UpnpStateVariable(defaultValue = "18")
    private double temperature; 

    @UpnpAction
    public void setId(@UpnpInputArgument(name = "NewId") String newId) {
        id = newId;
    }

    @UpnpAction
    public void setStatus(@UpnpInputArgument(name = "NewStatus") boolean newStatus) {
        status = newStatus;
        getPropertyChangeSupport().firePropertyChange("Status", null, null);
    }
    
    @UpnpAction
    public void setTemperature(@UpnpInputArgument(name = "NewTemp") double newTemp) {
        temperature = newTemp;
        getPropertyChangeSupport().firePropertyChange("Temperature", null, null);
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "ResultTempStatus"))
    public boolean getStatus() {
        return status;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "ResultTempId"))
    public String getId() {
        return id;
    }
}