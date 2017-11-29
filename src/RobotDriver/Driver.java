package RobotDriver;

import Info.EventMessage;

import java.awt.*;

public class Driver{

    public Robot robot;
    private String device;
    private static Object message;
    private boolean writable;
    private boolean readable;
    private final int KEY_PRESSED = 1;
    private final int KEY_RELEASED = 2;
    private final int MOVE_CURSOR = 3;

    public Driver()
    {
        try {
            robot = new Robot();
            device = null;
            writable = true;
            readable = false;
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void setDevice(String device)
    {
        this.device = device;
    }

    synchronized public void setMessage(Object message)
    {
        if (!writable)
        {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        
        writable = false;
        this.message = message;
        readable = true;
        notifyAll();
    }

    synchronized public Object getMessage()
    {
        if (!readable)
        {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        readable = false;
        Object message = this.message;
        writable = true;
        notifyAll();
        return message;
    }

    public void keyCommand(EventMessage eventMessage)
    {
        if (eventMessage.getKeyStatus() == KEY_PRESSED)
        {
            robot.keyPress(eventMessage.getKeycode());
        }

        else if (eventMessage.getKeyStatus() == KEY_RELEASED)
        {
            robot.keyRelease(eventMessage.getKeycode());
        }
    }

    public void mouseMover(int x, int y)
    {
        PointerInfo mouseInfo = MouseInfo.getPointerInfo();
        Point mouseCoordinate = mouseInfo.getLocation();

        double currentX = mouseCoordinate.x;
        double currentY = mouseCoordinate.y;

        double diffX = x - mouseCoordinate.x + 1;
        double diffY = y - mouseCoordinate.y + 1;

        double dx = diffX / 1000;
        double dy = diffY / 1000;

        for(int i = 1; i <= 1000; i++)
        {
            currentX += dx;
            currentY += dy;
            robot.mouseMove((int)currentX, (int)currentY);
        }
    }

    public void mouseCommand(EventMessage eventMessage)
    {
        if (eventMessage.getKeyStatus() == KEY_PRESSED)
        {
            robot.mousePress(eventMessage.getKeycode());
        }

        else if (eventMessage.getKeyStatus() == KEY_RELEASED)
        {
            robot.mouseRelease(eventMessage.getKeycode());
        }

        else if (eventMessage.getKeyStatus() == MOVE_CURSOR)
        {
            robot.mouseMove(eventMessage.x, eventMessage.y);
        }
    }
}
