package com.hisense.hibeans.main;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hisense.hibeans.base.BaseBean;

/**
 * Created by liudunjian on 2018/4/23.
 */

public class SvcBean extends BaseBean {

    @SerializedName("name")
    private String name;
    @SerializedName("pid")
    private String pid;
    @SerializedName("id")
    private String id;
    @SerializedName("status")
    private int status;
    @SerializedName("iconLink")
    private String iconLink;

    public SvcBean(String name, String pid, String id, int status, String iconLink) {
        this.name = name;
        this.pid = pid;
        this.id = id;
        this.status = status;
        this.iconLink = iconLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
