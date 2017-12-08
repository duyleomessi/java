package example.binarylight;

import java.io.IOException;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.DeviceDetails;
import org.fourthline.cling.model.meta.DeviceIdentity;
import org.fourthline.cling.model.meta.Icon;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.ManufacturerDetails;
import org.fourthline.cling.model.meta.ModelDetails;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.registry.RegistrationException;

public class AirConditionServer implements Runnable {
	public static void main(String[] args) throws Exception {
		// Start a user thread that runs the UPnP stack
		Thread serverThread = new Thread(new AirConditionServer());
		serverThread.setDaemon(false);
		serverThread.start();
	}

	public void run() {
		final UpnpService upnpService = new UpnpServiceImpl();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				upnpService.shutdown();
			}
		});

		// add the bound local device
		try {
			upnpService.getRegistry().addDevice(createDevice());
		} catch (RegistrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LocalServiceBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	LocalDevice createDevice() throws ValidationException, LocalServiceBindingException {
		DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier("Demo Aircondition"));

		DeviceType type = new UDADeviceType("AirCondition", 1);

		DeviceDetails details = new DeviceDetails("Friendly Air Condition", new ManufacturerDetails("Sam sung"),
				new ModelDetails("Samsung inverter 9000 BTU", "A demo air-condition with on/off switch and increase/decrease temperature", "v1"));

		Icon icon = null;
		try {
			icon = new Icon("image/png", 48, 48, 8, getClass().getResource("icon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LocalService<AirCondition> airConditionService = new AnnotationLocalServiceBinder().read(AirCondition.class);

		airConditionService.setManager(new DefaultServiceManager(airConditionService, AirCondition.class));

		return new LocalDevice(identity, type, details, icon, airConditionService);

		/*
		 * Several services can be bound to the same device: return new LocalDevice(
		 * identity, type, details, icon, new LocalService[] {switchPowerService,
		 * myOtherService} );
		 */
	}
}
