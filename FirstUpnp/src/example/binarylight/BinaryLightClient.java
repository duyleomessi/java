package example.binarylight;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.controlpoint.*;
import org.fourthline.cling.model.action.*;
import org.fourthline.cling.model.message.*;
import org.fourthline.cling.model.message.header.*;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.*;
import org.fourthline.cling.registry.*;

public class BinaryLightClient implements Runnable {
	public static void main(String[] args) throws Exception {
		// start user thread that runs the upnp stack
		Thread clientThread = new Thread(new BinaryLightClient());
		clientThread.setDaemon(false);
		clientThread.start();
	}

	public void run() {
		try {
			UpnpService upnpService = new UpnpServiceImpl();
			upnpService.getRegistry().addListener(createRegistryListener(upnpService));
			// broadcast a search message for all device
			upnpService.getControlPoint().search(new STAllHeader());
		} catch (Exception ex) {
			System.err.println("Exception occured: " + ex);
			System.exit(1);
		}
	}

	RegistryListener createRegistryListener(final UpnpService upnpService) {
		return new DefaultRegistryListener() {
			ServiceId serviceId = new UDAServiceId("SwitchPower");

			@Override
			public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
				Service switchPower;
				if ((switchPower = device.findService(serviceId)) != null) {
					System.out.println("service discover: " + switchPower);
					executeAction(upnpService, switchPower);
				}
			}

			@Override
			public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
				Service switchPower;
				if ((switchPower = device.findService(serviceId)) != null) {
					System.out.println("Service disappeared: " + switchPower);
				}
			}
		};
	}

	void executeAction(UpnpService upnpService, Service switchPowerService) {
		ActionInvocation setTargetInvocation = new SetTargetActionInvocation(switchPowerService);

		// Executes asynchronous in the background
		upnpService.getControlPoint().execute(new ActionCallback(setTargetInvocation) {
			@Override
			public void success(ActionInvocation invocation) {
				assert invocation.getOutput().length == 0;
				System.out.println("Successfully called action!!");
			}

			@Override
			public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMessage) {
				// TODO Auto-generated method stub
				System.err.println(defaultMessage);
			}
		});
	}

	class SetTargetActionInvocation extends ActionInvocation {
		SetTargetActionInvocation(Service service) {
			super(service.getAction("SetTarget"));
			try {
				// Throws InvalidValueException if the value is of wrong type
				setInput("NewTargetValue", true);
			} catch (InvalidValueException ex) {
				System.err.println(ex.getMessage());
				System.exit(1);
			}
		}
	}
}