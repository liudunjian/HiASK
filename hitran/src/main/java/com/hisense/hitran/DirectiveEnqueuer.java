package com.hisense.hitran;

import android.util.Log;

import com.google.gson.JsonParseException;
import com.hisense.hitran.dispatcher.DirectiveDispatcher;
import com.hisense.hitran.lisener.IHttpRequestListener;
import com.hisense.hitran.manager.SessionManager;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by liudunjian on 2018/10/25.
 */

public class DirectiveEnqueuer extends Thread implements Callback {

    private BlockingDeque<TranResponse> queue = new LinkedBlockingDeque<>();

    private boolean isStopped = true;

    private IHttpRequestListener httpRequestListener;

    private DirectiveDispatcher directiveDispatcher;

    public DirectiveEnqueuer(DirectiveDispatcher directiveDispatcher) {
        this.directiveDispatcher = directiveDispatcher;
    }

    public void init() {
        isStopped = false;
        this.start();
    }

    public void release() {
        this.isStopped = true;
        this.interrupt();
    }

    public void setHttpRequestListener(IHttpRequestListener listener) {
        this.httpRequestListener = listener;
    }

    @Override
    public void run() {
        try {
            while (!isStopped) {
                final TranResponse response = this.queue.take();
                directiveDispatcher.parseDirective(response);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            isStopped = true;
        }
    }

    /**********http response callback***************/
    @Override
    public void onFailure(Call call, IOException e) {
        Log.d("DirectiveEnqueuer", "onFailure");
        SessionManager.getInstance().setSessionId("");
        if (httpRequestListener != null) {
            if (call.isCanceled()) {
                httpRequestListener.canceled();
            } else {
                httpRequestListener.failure();
            }
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            if (!response.isSuccessful())
                throw new IOException("request failed");
            parseResponse(response);

        } catch (Exception e) {
            SessionManager.getInstance().setSessionId("");
            if (httpRequestListener != null) {
                if (call.isCanceled()) {
                    httpRequestListener.canceled();
                } else {
                    httpRequestListener.failure();
                }
            }
        } finally {
            if (response.body() != null)
                response.body().close();
        }
    }

    private void parseResponse(Response response) throws IOException {
        String content = IOUtils.toString(response.body().byteStream(), "utf-8");
        TranResponse tranResponse = this.directiveDispatcher.gson().fromJson(content, TranResponse.class);
        SessionManager.getInstance().setSessionId(tranResponse.getSessionid());
        Log.d("sesstion ID:",SessionManager.getInstance().getSessionId());
        if (tranResponse.getErr_no() == 0) {
            tranResponse.setContent(content);
            this.queue.add(tranResponse);
        } else if (httpRequestListener != null) {
            httpRequestListener.failure();
        }
    }

}
