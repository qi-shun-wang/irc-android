package com.ising99.intelligentremotecontrol.core.CoapClient;

import android.util.Log;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

/**
 * Created by shun on 2018/3/27.
 *
 */

public final class RemoteControlCoAPService {

    private CoapClient client ;
    private String address;
    private int port;
    CoapHandler handler = new CoapHandler() {
        @Override
        public void onLoad(CoapResponse response) {

            Log.d("CoapResponse","====>" + response.toString());

        }

        @Override
        public void onError() {

        }
    };

    public RemoteControlCoAPService(String address, int port){
        this.address = address;
        this.port = port;
        client = new CoapClient();


    }

    public void send(KeyCode code){
        client.setURI("coap://"+address+":"+port+"/keyEvent");
        client.useNONs().post(handler,String.valueOf(code.getCode()), MediaTypeRegistry.TEXT_PLAIN);
    }

    public void send(SendCode code){
        client.setURI("coap://"+address+":"+port+"/sendEvent");
        client.useNONs().post(handler,String.valueOf(code.getCode()), MediaTypeRegistry.TEXT_PLAIN);
    }

    public void send(String text){
        client.setURI("coap://"+address+":"+port+"/textInput");
        client.useNONs().post(handler,text, MediaTypeRegistry.TEXT_PLAIN);
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
