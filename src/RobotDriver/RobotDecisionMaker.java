package RobotDriver;

import Info.EventMessage;

public class RobotDecisionMaker implements Runnable {

    public Driver driver;

    public RobotDecisionMaker(Driver driver){
        this.driver = driver;
    }
    @Override
    public void run() {

        while(true)
        {
            Object message = driver.getMessage();

            if (message instanceof EventMessage)
            {
                EventMessage eventMessage = (EventMessage) message;

                if (eventMessage.getDevice().equals("KEY_BOARD"))
                    driver.keyCommand(eventMessage);

                else if (eventMessage.getDevice().equals("MOUSE"))
                    driver.mouseCommand(eventMessage);
            }
        }
    }
}