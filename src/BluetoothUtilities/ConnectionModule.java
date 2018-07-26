package BluetoothUtilities;

import javax.microedition.io.StreamConnection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ConnectionModule {

    private StreamConnection streamConnection;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean alive;

    public ConnectionModule(StreamConnection streamConnection)
    {
        this.streamConnection = streamConnection;
        try {
            this.objectOutputStream = new ObjectOutputStream(streamConnection.openOutputStream());
            this.objectInputStream = new ObjectInputStream(streamConnection.openInputStream());
            alive = true;
            //this.objectInputStream = new ObjectInputStream(streamConnection.openDataInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            alive = false;
        }

    }

    public void send(Object object)
    {
        try {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
            alive = false;
        }
    }

    public Object receive()
    {
        try {
            Object object = objectInputStream.readObject();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
            alive = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isAlive()
    {
        return alive;
    }
}
