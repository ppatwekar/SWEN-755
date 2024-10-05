package controller;
import AsyncAbstractSystemComponents.DataInSubscriber;
import AsyncConcreteSystemComponents.LightBulb;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PortDataInController {

    //Use Instance of Bulb instead of this. This is only a placeholder

    private ConcurrentHashMap<String, List<DataInSubscriber>> subscriberMap;

    private final String ALL_KEY = "all";

    public PortDataInController()
    {
        subscriberMap = new ConcurrentHashMap<>();
    }


    /**
     *
     * @param input can follow a structure like "Something{data}
     *              the 'Something' helps indicate where to send the data"
     */
    public void processInput(String input){
        System.out.println("PortDataInController: Received"+input);
        //System.out.println("Got here");
        String key = input.substring(0,input.indexOf(" "));

        if(!subscriberMap.containsKey(key)){
            System.out.println("Not Yet Subscribed to "+key);
            return;
        }

        notifySubscribers(key, input);
        notifyAllSubscribers(input);
    }

    public void subscribe(DataInSubscriber dataInSubscriber, String ...keys){
        if(keys.length == 1 && keys[0].equals(ALL_KEY)){
            subscribeToAll(dataInSubscriber);
            return;
        }


        for(String key : keys){
            if(!subscriberMap.containsKey(key)){
                LinkedList<DataInSubscriber> list = new LinkedList<>();
                list.add(dataInSubscriber);
                subscriberMap.put(key,list);
            }
            else{
                subscriberMap.get(key).add(dataInSubscriber);
            }
        }
    }

    private void subscribeToAll(DataInSubscriber dataInSubscriber){
        if(!subscriberMap.containsKey(ALL_KEY)){
            LinkedList<DataInSubscriber> list = new LinkedList<>();
            list.add(dataInSubscriber);
            subscriberMap.put(ALL_KEY,list);
        }
        else{
            subscriberMap.get(ALL_KEY).add(dataInSubscriber);
        }
    }

    public void notifySubscribers(String key, String input){
        System.out.println(subscriberMap.toString());
        subscriberMap.get(key).forEach(dataInSubscriber -> dataInSubscriber.update(input));
    }

    /**
     * notifies subscribers subscribed to all events (for. example the redundancy component needs all updates)
     * @param input
     */
    public void notifyAllSubscribers(String input){
        if(subscriberMap.containsKey(ALL_KEY)) {
            subscriberMap.get(ALL_KEY).forEach(dataInSubscriber -> dataInSubscriber.update(input));
        }
    }
}
