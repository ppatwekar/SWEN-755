package controller;

import AsyncAbstractSystemComponents.AbstractAsyncBidirectionalComponent;
import AsyncAbstractSystemComponents.AbstractAsyncInputOnlyComponent;
import AsyncConcreteSystemComponents.Sensor;

public class PortDataInController {

    //Use Instance of Bulb instead of this. This is only a placeholder
    private AbstractAsyncBidirectionalComponent component;


    public PortDataInController(){
    }

    /**
     *
     * @param input can follow a structure like "Something{data}
     *              the 'Something' helps indicate where to send the data"
     */
    public void processInput(String input){
        System.out.println("PortDataInController: Received"+input);
        if(input.startsWith("Something")){
            component.addInputDataToQueue(input);
        }
    }
}
