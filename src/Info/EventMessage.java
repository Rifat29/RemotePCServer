package Info;

import java.io.Serializable;

public class EventMessage implements Serializable {

    private String device;
    private int keycode;
    private int keyStatus;
    public int x;
    public int y;

    public EventMessage(String device, int keycode, int keyStatus)
    {
        this.device = device;
        this.keycode = keycode;
        this.keyStatus = keyStatus;
    }

    public void setCoordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public String getDevice()
    {
        return device;
    }

    public int getKeycode()
    {
        return keycode;
    }

    public int getKeyStatus()
    {
        return keyStatus;
    }
}