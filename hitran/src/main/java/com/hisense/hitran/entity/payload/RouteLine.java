package com.hisense.hitran.entity.payload;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2018/10/26.
 */

public class RouteLine {

    private int tag;
    private ArrayList<Step> steps;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

}
