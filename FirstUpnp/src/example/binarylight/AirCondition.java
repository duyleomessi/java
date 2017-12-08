package example.binarylight;

import org.fourthline.cling.binding.annotations.*;

@UpnpService(
        serviceId = @UpnpServiceId("AirCondition"),
        serviceType = @UpnpServiceType(value = "AirCondition", version = 1)
)
public class AirCondition {
	@UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private boolean target = false;

    @UpnpStateVariable(defaultValue = "0")
    private boolean status = false;
    
    @UpnpStateVariable(defaultValue = "0.0")
    private double temperature = 0.0;

    @UpnpAction
    public void setTarget(@UpnpInputArgument(name = "NewTargetValue")boolean newTargetValue, 
    					  @UpnpInputArgument(name = "newStatusValue") boolean newStatusValue
    					  //@UpnpInputArgument(name = "newTemperature") double newTemperature
                          ) {
        target = newTargetValue;
        status = newStatusValue;
        //temperature = newTemperature;
        System.out.println("Aircondition is: " + status);
    }
    
    public void setTemperature(@UpnpInputArgument(name = "newTemperature") double newTemperature) {
    	this.temperature = newTemperature;
    	System.out.println("The temperature is: " + this.temperature);
    }

    public void increaseTemperature(double tem) {
    	this.temperature += tem;
    }
    
    public void decreaseTemperature(double tem) {
    	this.temperature -= tem;
    }
    
    @UpnpAction(out = @UpnpOutputArgument(name = "RetTargetValue"))
    public boolean getTarget() {
        return target;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "ResultStatus"))
    public boolean getStatus() {
        // If you want to pass extra UPnP information on error:
        // throw new ActionException(ErrorCode.ACTION_NOT_AUTHORIZED);
        return status;
    }
    
    public double getTemperature() {
    	return temperature;
    }
}
