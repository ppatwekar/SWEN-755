package controller;
import AsyncAbstractSystemComponents.AbstractAsyncInputOnlyComponent;
import AsyncAbstractSystemComponents.LightBulb;

import java.io.IOException;

public class PortDataInController {

    //Use Instance of Bulb instead of this. This is only a placeholder
    private LightBulb lightBulbComponent;


    public PortDataInController() throws IOException
    {

    }

    public void setComponent(LightBulb component)
    {
        lightBulbComponent = component;
    }

    /**
     *
     * @param input can follow a structure like "Something{data}
     *              the 'Something' helps indicate where to send the data"
     */
    public void processInput(String input){
        System.out.println("PortDataInController: Received"+input);
        //System.out.println("Got here");
        if(input.contains("LightBulb")){
            System.out.println("IF"+input);
            lightBulbComponent.addInputDataToQueue(input);
        }
    }
}
