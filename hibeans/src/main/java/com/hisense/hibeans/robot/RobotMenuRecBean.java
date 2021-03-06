package com.hisense.hibeans.robot;

/**
 * Created by liudunjian on 2018/6/22.
 */

public class RobotMenuRecBean {

    private String itemId;
    private String content;
    private int type;

    public RobotMenuRecBean(String itemId, String content, int type) {
        this.itemId = itemId;
        this.content = content;
        this.type = type;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
