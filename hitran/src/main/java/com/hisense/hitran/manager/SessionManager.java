package com.hisense.hitran.manager;

/**
 * Created by liudunjian on 2018/10/24.
 */

public class SessionManager {

    private String sessionId = "";

    public static SessionManager getInstance() {
        return InstanceHolder.sessionManager;
    }

    private SessionManager() {

    }

    public void releaseSessionId() {
        if(null != this.sessionId) {
            sessionId = null;
        }
    }

    /*************getter and setter**************/

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /*************嵌套类**************/
    private static class InstanceHolder {
        private static SessionManager sessionManager = new SessionManager();
    }
}
