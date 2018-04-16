package com.ising99.intelligentremotecontrol.modules.IRC.mode;

/**
 * Created by shun on 2018/4/15.
 *
 */

public interface IRCSelectModeDelegate {
    void didSelectedDefaultMode();
    void didSelectedNormalMode();
    void didSelectedTouchMode();
    void didSelectedTextingMode();
    void didSelectedGameMode();
    void didCanceledSelection();
}
