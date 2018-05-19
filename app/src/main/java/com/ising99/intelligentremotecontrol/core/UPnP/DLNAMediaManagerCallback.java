package com.ising99.intelligentremotecontrol.core.UPnP;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;

/**
 * Created by shun on 2018/5/4.
 * .
 */
public interface DLNAMediaManagerCallback {

    interface Common {
        void success(ActionInvocation invocation);
        void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2);
    }

    interface OneWay {
        void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg);
    }

}
