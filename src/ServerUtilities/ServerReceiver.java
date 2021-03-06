package ServerUtilities;

import BluetoothUtilities.ConnectionModule;
import RobotDriver.Driver;

public class ServerReceiver implements Runnable{

    private ConnectionModule connectionModule;
    private Driver driver;

    public ServerReceiver(ConnectionModule connectionModule, Driver driver)
    {
        this.connectionModule = connectionModule;
        this.driver = driver;
    }

    @Override
    public void run() {

        Object object;

        while (connectionModule.isAlive())
        {
            object = connectionModule.receive();
            driver.setReceivedMessage(object);
        }
    }
}
