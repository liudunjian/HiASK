package com.hisense.hibeans.robot;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by liudunjian on 2018/4/28.
 */

public class RobotMenuMnuList implements IRobotBeanDateTime{

    private ArrayList<RobotMenuMnuBean> robotMenus;
    private long sendTime;

    public RobotMenuMnuList(long sendTime, ArrayList<RobotMenuMnuBean> robotMenus) {
        this.robotMenus = robotMenus;
        this.sendTime = sendTime;
    }

    public ArrayList<RobotMenuMnuBean> getRobotMenus() {
        return robotMenus;
    }

    public void setRobotMenus(ArrayList<RobotMenuMnuBean> robotMenus) {
        this.robotMenus = robotMenus;
    }

    @Override
    public long getTime() {
        return sendTime;
    }

    @Override
    public void sendTime(long time) {
        this.sendTime = time;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public static class RobotMenuMnuBean  {

        protected String itemId;
        protected String content;
        private int type;


        public RobotMenuMnuBean( String itemId, String content, int type) {
            this.itemId = itemId;
            this.content = content;
            this.type = type;

        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }


    }
}
