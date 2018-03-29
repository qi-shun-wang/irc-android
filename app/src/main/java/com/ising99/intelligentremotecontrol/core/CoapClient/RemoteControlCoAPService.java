package com.ising99.intelligentremotecontrol.core.CoapClient;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

/**
 * Created by shun on 2018/3/27.
 *
 */

public final class RemoteControlCoAPService {

    private CoapClient client ;
    private String address;
    private int port;

    RemoteControlCoAPService(String address,int port){
        this.address = address;
        this.port = port;
        client = new CoapClient();

    }

    public void send(KeyCode code){
        client.setURI("coap://"+address+":"+port+"/keyEvent");
        client.post(String.valueOf(code.getCode()), MediaTypeRegistry.TEXT_PLAIN);
    }

}
