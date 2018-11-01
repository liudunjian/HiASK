package com.hisense.hiask.map;

/**
 * Created by liudunjian on 2018/10/29.
 */

public class DataTransaction {

    private Object info;
    private int type; //0: means info is route way; 1:indicate info is poi object;

    public static DataTransaction getInstance() {
        return INSTANCE_HOLDER.instance;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getInfo() {
        return this.info;
    }

    public int getType() {
        return this.type;
    }

    public void clear() {
        this.info = null;
        this.type = -1;
    }

    private static class INSTANCE_HOLDER {
        private  static DataTransaction instance = new DataTransaction();
    }
}
