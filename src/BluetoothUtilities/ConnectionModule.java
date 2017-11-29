package BluetoothUtilities;

import javax.microedition.io.StreamConnection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ConnectionModule {

    private StreamConnection streamConnection;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ConnectionModule(StreamConnection streamConnection)
    {
        this.streamConnection = streamConnection;
        try {
            this.objectOutputStream = new ObjectOutputStream(streamConnection.openOutputStream());
            this.objectInputStream = new ObjectInputStream(streamConnection.openInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void send(Object object)
    {
        try {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object receive()
    {
        try {
            Object object = objectInputStream.readObject();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
