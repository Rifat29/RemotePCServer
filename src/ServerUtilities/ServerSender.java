package ServerUtilities;

import BluetoothUtilities.ConnectionModule;
import RobotDriver.Driver;

public class ServerSender implements Runnable {

    private ConnectionModule connectionModule;
    private Driver driver;

    public ServerSender(ConnectionModule connectionModule, Driver driver)
    {
        this.connectionModule = connectionModule;
        this.driver = driver;
    }

    @Override
    public void run() {

        Object object;

        while (connectionModule.isAlive())
        {
            object = driver.getSentMessage();
            connectionModule.send(object);
        }
    }
}
