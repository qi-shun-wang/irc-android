package com.ising99.intelligentremotecontrol.core.UPnP;

import org.fourthline.cling.model.meta.Device;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by shun on 2018/5/4.
 */

public interface DLNAMediaManagerProtocol {

    void startServer() throws IOException;
    void stopServer();
    void setupMediaServer(InetAddress address);

    void setCurrentDevice(Device currentDevice);

    void setAVTransportURI(String path, DLNAMediaManagerCallback.Common callback);

    void play(DLNAMediaManagerCallback.Common callback);

    void stop(DLNAMediaManagerCallback.OneWay callback);

    void pause(DLNAMediaManagerCallback.Common callback);

    void seek(String targetPosition,DLNAMediaManagerCallback.Common callback);


    /**
     * Go on playing the video from the position.
     *
     * @param device
     *            The device be controlled.
     * @param pausePosition
     *            The format must be 00:00:00.
     */
    boolean goOn(Device device, String pausePosition);

    /**
     * All the state is "STOPPED" // "PLAYING" // "TRANSITIONING"//
     * "PAUSED_PLAYBACK"// "PAUSED_RECORDING"// "RECORDING" //
     * "NO_MEDIA_PRESENT//
     */
    String getTransportState(Device device);

    /**
     * Get the min volume value,this must be 0.
     *
     * @param device
     * @return
     */
    int getMinVolumeValue(Device device);

    /**
     * Get the max volume value, usually it is 100.
     *
     * @param device
     *            The device be controlled.
     * @return The max volume value.
     */
    int getMaxVolumeValue(Device device);

    /**
     * Get the current playing position of the video.
     *
     * @param device
     *            The device be controlled.
     * @return Current playing position is 00:00:00
     */
    String getPositionInfo(Device device);

    /**
     * Get the duration of the video playing.
     *
     * @param device
     *            The device be controlled.
     * @return The media duration like 00:00:00,if get failed it will return
     *         null.
     */
    String getMediaDuration(Device device);

    /**
     * Mute the device or not.
     *
     * @param device
     *            The device be controlled.
     * @param targetValue
     *            1 is that want mute, 0 if you want make it off of mute.
     * @return If is success to mute the device.
     */
    boolean setMute(Device device, String targetValue);

    /**
     * Get if the device is mute.
     *
     * @param device
     *            The device be controlled.
     * @return 1 is mute, otherwise will return 0.
     */
    String getMute(Device device);

    /**
     * Set the device's voice.
     *
     * @param device
     *            The device be controlled.
     * @param value
     *            Target voice want be set.
     * @return
     */
    boolean setVoice(Device device, int value);

    /**
     * Get the current voice of the device.
     *
     * @param device
     *            The device be controlled.
     * @return Current voice.
     */
    int getVoice(Device device);

    /**
     * get the remote device play state add by bhj
     * @param device
     * @return
     */
    boolean getPauseState(Device device);
}
