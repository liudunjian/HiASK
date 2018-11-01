package com.hisense.hitran.http.factory;

/**
 * Created by liudunjian on 2018/10/25.
 */

public class RequestBodyProducer {

    public static IReqBodyFactory factory(RequestBodyType type) {
        switch (type) {
            case REQUEST_BODY_TYPE_FORM:
                return new FormBodyFactoryFactory();
            case REQUEST_BODY_TYPE_JSON:
                return new JsonBodyFactoryFactory();
        }
        throw new UnsupportedOperationException("无法创建该工程方法,请确认你的type参数");
    }
}
