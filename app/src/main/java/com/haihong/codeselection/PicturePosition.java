package com.haihong.codeselection;

public class PicturePosition {

    private String mPosition;

    public PicturePosition(String position) {
        mPosition = position;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

    @Override
    public String toString() {
        return mPosition;
    }
}
