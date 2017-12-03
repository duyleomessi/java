package example.binarylight;

import java.io.IOException;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.*;
import org.fourthline.cling.binding.annotations.*;
import org.fourthline.cling.model.*;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.*;
import org.fourthline.cling.registry.RegistrationException;

public class BinaryLightServer implements Runnable {
	public static void main(String[] args) throws Exception {
		// Start a user thread that runs the UPnP stack
		Thread serverThread = new Thread(new BinaryLightServer());
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
		DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier("Demo Binary Light"));

		DeviceType type = new UDADeviceType("BinaryLight", 1);

		DeviceDetails details = new DeviceDetails("Friendly Binary Light", new ManufacturerDetails("ACME"),
				new ModelDetails("BinLight2000", "A demo light with on/off switch.", "v1"));

		Icon icon = null;
		try {
			icon = new Icon("image/png", 48, 48, 8, getClass().getResource("icon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LocalService<SwitchPower> switchPowerService = new AnnotationLocalServiceBinder().read(SwitchPower.class);

		switchPowerService.setManager(new DefaultServiceManager(switchPowerService, SwitchPower.class));

		return new LocalDevice(identity, type, details, icon, switchPowerService);

		/*
		 * Several services can be bound to the same device: return new LocalDevice(
		 * identity, type, details, icon, new LocalService[] {switchPowerService,
		 * myOtherService} );
		 */
	}
}
