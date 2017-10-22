package com.example.apichaya.addrealmsudent.Model;

import io.realm.RealmObject;

/**
 * Created by apichaya on 10/19/2017 AD.
 */

public class RgbColor extends RealmObject {

    int redValue , greenValue , blueValue;

    public int getRedValue() {
        return redValue;
    }

    public void setRedValue(int redValue) {
        this.redValue = redValue;
    }

    public int getGreenValue() {
        return greenValue;
    }

    public void setGreenValue(int greenValue) {
        this.greenValue = greenValue;
    }

    public int getBlueValue() {
        return blueValue;
    }

    public void setBlueValue(int blueValue) {
        this.blueValue = blueValue;
    }

    public RgbColor(int redValue , int greenValue , int blueValue) {
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;

    }
    public RgbColor() {

    }
}
