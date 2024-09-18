package controller;

import AsyncAbstractSystemComponents.AbstractAsyncBidirectionalComponent;

public class PortDataInController {

    //Instance of Bulb
    private AbstractAsyncBidirectionalComponent component;

    /**
     *
     * @param input can follow a structure like "Something{data}
     *              the 'Something' helps indicate where to send the data"
     */
    public void processInput(String input){
        if(input.startsWith("Something")){
            component.addInputDataToQueue(input);
        }
    }
}
