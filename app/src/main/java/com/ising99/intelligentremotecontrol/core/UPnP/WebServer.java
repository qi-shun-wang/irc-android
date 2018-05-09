package com.ising99.intelligentremotecontrol.core.UPnP;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by shun on 2018/5/7.
 * .
 */

public class WebServer extends NanoHTTPD {

    WebServer(String hostname, int port) {
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

        if (uri.startsWith("/music/")) {
            String filepath = uri.replaceFirst("/music","");
            try {
                return  serveFile(session.getHeaders(), new File(filepath), "audio/mpeg");
            } catch (Exception e) {
                return  newFixedLengthResponse(e.getLocalizedMessage());
            }
        }

        if (uri.startsWith("/video/")) {
            String filepath = uri.replaceFirst("/video","");
            try {
                return  serveFile( session.getHeaders(), new File(filepath), "video/mp4");
            } catch (Exception e) {
                return  newFixedLengthResponse(e.getLocalizedMessage());
            }
        }
        return  null;

    }

    private Response createResponse(Response.Status status, String mimeType,
                                    InputStream message) {
        Response res = newChunkedResponse(status, mimeType, message);
        res.addHeader("Accept-Ranges", "bytes");
        return res;
    }

    /**
     * Serves file from homeDir and its' subdirectories (only). Uses only URI,
     * ignores all headers and HTTP parameters.
     */
    private Response serveFile( Map<String, String> header,
                               File file, String mime) {
        Response res;
        try {
            // Calculate etag
            String etag = Integer.toHexString((file.getAbsolutePath()
                    + file.lastModified() + "" + file.length()).hashCode());

            // Support (simple) skipping:
            long startFrom = 0;
            long endAt = -1;
            String range = header.get("range");
            if (range != null) {
                if (range.startsWith("bytes=")) {
                    range = range.substring("bytes=".length());
                    int minus = range.indexOf('-');
                    try {
                        if (minus > 0) {
                            startFrom = Long.parseLong(range
                                    .substring(0, minus));
                            endAt = Long.parseLong(range.substring(minus + 1));
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            // Change return code and add Content-Range header when skipping is
            // requested
            long fileLen = file.length();
            if (range != null && startFrom >= 0) {
                if (startFrom >= fileLen) {
                    InputStream stream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
                    res = createResponse(Response.Status.RANGE_NOT_SATISFIABLE,
                            NanoHTTPD.MIME_PLAINTEXT, stream);
                    res.addHeader("Content-Range", "bytes 0-0/" + fileLen);
                    res.addHeader("ETag", etag);
                } else {
                    if (endAt < 0) {
                        endAt = fileLen - 1;
                    }
                    long newLen = endAt - startFrom + 1;
                    if (newLen < 0) {
                        newLen = 0;
                    }

                    final long dataLen = newLen;
                    FileInputStream fis = new FileInputStream(file) {
                        @Override
                        public int available() throws IOException {
                            return (int) dataLen;
                        }
                    };
                    fis.skip(startFrom);

                    res = createResponse(Response.Status.PARTIAL_CONTENT, mime,
                            fis);
                    res.addHeader("Content-Length", "" + dataLen);
                    res.addHeader("Content-Range", "bytes " + startFrom + "-"
                            + endAt + "/" + fileLen);
                    res.addHeader("ETag", etag);
                }
            } else {
                if (etag.equals(header.get("if-none-match")))
                {
                    InputStream stream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
                    res = createResponse(Response.Status.NOT_MODIFIED, mime, stream);
                }
                else {
                    res = createResponse(Response.Status.OK, mime,
                            new FileInputStream(file));
                    res.addHeader("Content-Length", "" + fileLen);
                    res.addHeader("ETag", etag);
                }
            }
        } catch (IOException ioe) {
            InputStream stream = new ByteArrayInputStream("FORBIDDEN: Reading file failed.".getBytes(StandardCharsets.UTF_8));
            res = createResponse(Response.Status.FORBIDDEN,
                    NanoHTTPD.MIME_PLAINTEXT, stream);
        }

        return res;
    }
}
