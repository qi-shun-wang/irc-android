package com.ising99.intelligentremotecontrol.core.CoapClient;

import android.util.Log;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import java.io.UnsupportedEncodingException;

public final class RemoteControlCoAPService {

    private CoapClient client ;
    private String address;
    private int port;

    private CoapHandler handler = new CoapHandler() {
        @Override
        public void onLoad(CoapResponse response) { }

        @Override
        public void onError() {}
    };

    public RemoteControlCoAPService(String address, int port){
        this.address = address;
        this.port = port;
        client = new CoapClient();
    }

    public RemoteControlCoAPService() {
        this("192.168.34.1",5683);
    }

    public void checkWireConnection(RemoteControlCoAPServiceCallback.Common callback){
        client.setURI("coap://"+address+":"+port+"/wireCheck");
        client.useCONs().get(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                Log.d("CoapResponse","====>"+response.getResponseText());
                callback.didSuccessWith(response.getResponseText());
            }

            @Override
            public void onError() {
                callback.didFailure();
                Log.d("CoapResponse","====>error????");
            }
        });
    }

    public void send(SendCode code){
        client.setURI("coap://"+address+":"+port+"/sendEvent");
        client.useNONs().post(handler,String.valueOf(code.getCode()), MediaTypeRegistry.TEXT_PLAIN);
    }

    public void sendBegan(SendCode code){
        client.setURI("coap://"+address+":"+port+"/sendEventBegan");
        client.useNONs().post(handler,String.valueOf(code.getCode()), MediaTypeRegistry.TEXT_PLAIN);
    }

    public void sendEnd(SendCode code){
        client.setURI("coap://"+address+":"+port+"/sendEventEnd");
        client.useNONs().post(handler,String.valueOf(code.getCode()), MediaTypeRegistry.TEXT_PLAIN);
    }


    public void sendL(SendCode code){
        client.setURI("coap://"+address+":"+port+"/sendLongPressedEvent");
        client.useNONs().post(handler,String.valueOf(code.getCode()), MediaTypeRegistry.TEXT_PLAIN);
    }

    public void send(String text){
        try {
            client.setURI("coap://"+address+":"+port+"/textInput");
            client.useNONs().post(handler,text.getBytes("UTF-8"),MediaTypeRegistry.TEXT_PLAIN);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
