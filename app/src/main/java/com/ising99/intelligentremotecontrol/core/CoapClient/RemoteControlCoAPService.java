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

    public void ping(RemoteControlCoAPServiceCallback.Common callback){
        client.setURI("coap://"+address+":"+port+"/ping");
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

    public void detectGameEventNumber(RemoteControlCoAPServiceCallback.Common callback) {
        client.setURI("coap://"+address+":"+port+"/detectEvent");
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

    public void sendGameEvent(String eventNum, GameCode code) {
        client.setURI("coap://"+address+":"+port+"/gameEvent");
        String payload = eventNum + ";" + String.valueOf(code.getCode()) + ";";
        client.useNONs().post(handler, payload, MediaTypeRegistry.TEXT_PLAIN);
    }

    public void sendGameEventBegan(String eventNum, GameCode code) {
        client.setURI("coap://"+address+":"+port+"/gameEventBegan");
        String payload = eventNum + ";" + String.valueOf(code.getCode()) + ";";
        client.useNONs().post(handler, payload, MediaTypeRegistry.TEXT_PLAIN);
    }

    public void sendGameEventEnd(String eventNum, GameCode code) {
        client.setURI("coap://"+address+":"+port+"/gameEventEnd");
        String payload = eventNum + ";" + String.valueOf(code.getCode()) + ";";
        client.useNONs().post(handler, payload, MediaTypeRegistry.TEXT_PLAIN);
    }

    public void sendGameDPadEvent(String eventNum, GameCode code) {
        client.setURI("coap://"+address+":"+port+"/gameDPadEvent");
        String payload = eventNum
                + ";"
                + String.valueOf(Math.abs(code.getCode()))
                + ";"
                + (code.getCode()>0? "1":"-1")
                + ";";
        client.useNONs().post(handler, payload, MediaTypeRegistry.TEXT_PLAIN);
    }


    public void sendGameDPadEventBegan(String eventNum, GameCode code) {
        client.setURI("coap://"+address+":"+port+"/gameDPadBegan");
        String payload = eventNum
                + ";"
                + String.valueOf(Math.abs(code.getCode()))
                + ";"
                + (code.getCode()>0? "1":"-1")
                + ";";
        client.useNONs().post(handler, payload, MediaTypeRegistry.TEXT_PLAIN);
    }

    public void sendGameDPadEventEnd(String eventNum, GameCode code) {
        client.setURI("coap://"+address+":"+port+"/gameDPadEnd");
        String payload = eventNum
                + ";"
                + String.valueOf(Math.abs(code.getCode()))
                + ";";
        client.useNONs().post(handler, payload, MediaTypeRegistry.TEXT_PLAIN);
    }

    public void sendGameAxisEvent(String eventNum, GameCode code, String value) {
        client.setURI("coap://" + address + ":" + port + "/gameAxisEvent");
        String payload = eventNum
                + ";"
                + String.valueOf(Math.abs(code.getCode()))
                + ";";
        client.useNONs().post(handler, payload, MediaTypeRegistry.TEXT_PLAIN);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
