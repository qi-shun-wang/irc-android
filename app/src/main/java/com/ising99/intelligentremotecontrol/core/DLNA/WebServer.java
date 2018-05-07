package com.ising99.intelligentremotecontrol.core.DLNA;

import java.io.File;
import java.io.FileInputStream;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by shun on 2018/5/7.
 * .
 */

public class WebServer extends NanoHTTPD {

    public WebServer(int port) {
        super(port);
    }

    public WebServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        if (uri.startsWith("/image/")) {
            String filepath = uri.replaceFirst("/image","");
            try {
                FileInputStream fis = new FileInputStream(new File(filepath));
                return newChunkedResponse(Response.Status.OK,"image/jpeg", fis);
            } catch (Exception e) {
                return  newFixedLengthResponse(e.getLocalizedMessage());
            }
        }
        return  null;

    }
}
