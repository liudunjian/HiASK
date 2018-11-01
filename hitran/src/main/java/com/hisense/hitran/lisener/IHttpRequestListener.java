package com.hisense.hitran.lisener;

/**
 * Created by liudunjian on 2018/10/25.
 */

public interface IHttpRequestListener {
    void successful();
    void failure();
    void canceled();
}
