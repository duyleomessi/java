package action;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.InvalidValueException;

public class SetAirConditionerTempAction extends ActionInvocation {
    public SetAirConditionerTempAction(Service service, double temp) {
        super(service.getAction("SetTemperature"));

        try {
            setInput("NewTemp", temp);
        } catch (InvalidValueException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}