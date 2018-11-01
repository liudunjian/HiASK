package com.hisense.hiask.robot.adapter;

import com.hisense.hitools.utils.EmptyUtils;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2018/5/8.
 */

public class RobotRichMsgHolder {

    private static final int HOLDER_MAX_PAGE_SIZE = 420;

    private String content;
    private ArrayList<String> parts;

    private int currentPos = 0;
    private int totalPart = 1;

    public RobotRichMsgHolder(String content) {
        this.content = content;
    }

    public boolean isNeededSplit() {
        if (EmptyUtils.isEmpty(content))
            return false;
        totalPart = (int) Math.ceil(content.length() * 1.0 / HOLDER_MAX_PAGE_SIZE);
        return totalPart > 1;
    }

    public void splitContentToParts() {
        if (parts == null)
            parts = new ArrayList<>();
        int pos = 0;
        while (pos < totalPart) {
            String part = content.substring(pos * HOLDER_MAX_PAGE_SIZE, (pos + 1) * HOLDER_MAX_PAGE_SIZE >= content.length() ? content.length() : (pos + 1) * HOLDER_MAX_PAGE_SIZE);
            parts.add(part);
            pos++;
        }
    }

    public String nextContent() {
        if (currentPos >= totalPart)
            return null;
        String part = parts.get(currentPos);
        currentPos++;
        return part;
    }
}
