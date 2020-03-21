package com.hutcwp.game.wuziqi;

/**
 * Created by hutcwp on 2020-03-21 14:36
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class Point {

    private float x;
    private float y;
    private float ridus;

    /*
     ** 白色球 ： 0  黑色球 ：1
     */
    private int type;


    public float getRidus() {
        return ridus;
    }

    public int getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Point(float x, float y, float ridus, int type) {

        this.ridus = ridus;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public boolean Compare(float x, float y) {

        if (this.x == x && this.y == y) {

            return true;
        }

        return false;
    }

    public boolean isHaving(float x, float y, int t) {

        if (this.x == x && this.y == y) {

            if (type==t)
                return true;

        }

        return false;
    }


}
