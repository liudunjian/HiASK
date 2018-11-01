package com.hisense.hitran.http.factory;

/**
 * Created by liudunjian on 2018/10/25.
 */

public enum RequestBodyType {

    REQUEST_BODY_TYPE_JSON(0),
    REQUEST_BODY_TYPE_FORM(1);

    private int index = 0;

    RequestBodyType(int index) {
        this.index = index;
    }

    public RequestBodyType valueOf(int index) {
        if(index<0||index>1)
            return null;
        return RequestBodyType.values()[index];
    }

}
