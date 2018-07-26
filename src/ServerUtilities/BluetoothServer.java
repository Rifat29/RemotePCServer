package ServerUtilities;

import BluetoothUtilities.ConnectionModule;
import RobotDriver.Driver;
import RobotDriver.RobotDecisionMaker;

import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;

/**
* Class that implements an SPP Server which accepts single line of
* message from an SPP client and sends a single line of response to the client.
*/
public class BluetoothServer {

	//start server
	private void startServer() throws IOException{

		//Create a UUID for SPP

		UUID uuid = new UUID("0000110100001000800000805f9b34fb", false);

		//Create the service url
		String connectionString = "btspp://localhost:" + uuid + ";name=Sample SPP Server";

		//open server url
		StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier)Connector.open(connectionString);

		//Wait for client connection
		System.out.println("\nServer Started. Waiting for clients to connect...");
		StreamConnection connection=streamConnNotifier.acceptAndOpen();

		ConnectionModule connectionModule = new ConnectionModule(connection);
		Driver driver = new Driver();

		Thread receiverThread = new Thread(new ServerReceiver(connectionModule, driver));
		receiverThread.start();

		Thread senderThread = new Thread(new ServerSender(connectionModule, driver));
		senderThread.start();

		Thread decisionMakerThread = new Thread(new RobotDecisionMaker(driver));
		decisionMakerThread.start();

		RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
		System.out.println("Remote device address: " + dev.getBluetoothAddress());
		System.out.println("Remote device name: " + dev.getFriendlyName(true));

		try {
			receiverThread.join();
			senderThread.join();
			decisionMakerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		streamConnNotifier.close();
	}

	public static void main(String[] args) throws IOException {

		//display local device address and name
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		System.out.println("Address: " + localDevice.getBluetoothAddress());
		System.out.println("Name: " + localDevice.getFriendlyName());

		BluetoothServer bluetoothServer = new BluetoothServer();
		bluetoothServer.startServer();
	}
}