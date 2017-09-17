package com.watchcoin.Data;

/**
 * Class define the event of error message to display in user interface
 * (Used by EventBus process : http://greenrobot.org/eventbus/)
 */
public class ErrorMessageEvent {

    private String errorMessageType;


    public ErrorMessageEvent(String errorMessageType) {

        this.errorMessageType = errorMessageType;
    }

    public String getErrorMessageType() {

        return errorMessageType;
    }
}
