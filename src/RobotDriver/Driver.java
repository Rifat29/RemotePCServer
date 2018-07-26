package RobotDriver;

import com.pcremotecontroller.Info.EventMessage;

import java.awt.*;
import java.awt.event.InputEvent;

public class Driver{

    private Robot robot;
    private Object receivedMessage;
    private Object sentMessage;
    private boolean receivedIsWritable;
    private boolean receivedIsReadable;
    private boolean sentIsWritable;
    private boolean sentIsReadable;
    private final int KEY_PRESSED = 1;
    private final int KEY_RELEASED = 2;
    private final int MOVE_CURSOR = 3;

    public Driver()
    {
        try {
            robot = new Robot();
            receivedIsWritable = true;
            receivedIsReadable = false;
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    synchronized public void setReceivedMessage(Object message)
    {
        if (!receivedIsWritable)
        {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        receivedIsWritable = false;
        this.receivedMessage = message;
        receivedIsReadable = true;
        notifyAll();
    }

    synchronized public Object getReceivedMessage()
    {
        if (!receivedIsReadable)
        {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        receivedIsReadable = false;
        Object message = this.receivedMessage;
        receivedIsWritable = true;
        notifyAll();
        return message;
    }

    synchronized public void setSentMessage(Object message)
    {
        if (!sentIsWritable)
        {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        sentIsWritable = false;
        this.sentMessage = message;
        sentIsReadable = true;
        notifyAll();
    }

    synchronized public Object getSentMessage()
    {
        if (!sentIsReadable)
        {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        sentIsReadable = false;
        Object message = this.sentMessage;
        sentIsWritable = true;
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

    public void mouseMover(float dx, float dy)
    {
        PointerInfo mouseInfo = MouseInfo.getPointerInfo();
        Point mouseCoordinate = mouseInfo.getLocation();

        double currentX = mouseCoordinate.x;
        double currentY = mouseCoordinate.y;
        robot.mouseMove((int)(currentX + dx), (int)(currentY + dy));
    }

    public void mouseCommand(EventMessage eventMessage)
    {
        if (eventMessage.getKeyStatus() == KEY_PRESSED)
        {
            robot.mousePress(InputEvent.getMaskForButton(eventMessage.getKeycode()));
        }

        else if (eventMessage.getKeyStatus() == KEY_RELEASED)
        {
            robot.mouseRelease(InputEvent.getMaskForButton(eventMessage.getKeycode()));
        }

        else if (eventMessage.getKeyStatus() == MOVE_CURSOR)
        {
            mouseMover(eventMessage.getDx(), eventMessage.getDy());
        }
    }
}
