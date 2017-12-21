package action;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.InvalidValueException;

public class GetDeviceIdAction extends ActionInvocation {
    public GetDeviceIdAction(Service service) {
        super(service.getAction("GetId"));
        try {
            getOutput("ResultId");
        } catch (InvalidValueException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}
