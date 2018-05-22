package com.ising99.intelligentremotecontrol.core.CoapClient;

/**
 * Created by shun on 2018/3/27.
 *
 */
public interface RemoteControlCoAPServiceCallback {
    interface Common {
        void didSuccessWith(String payload);
        void didFailure();
    }

}
