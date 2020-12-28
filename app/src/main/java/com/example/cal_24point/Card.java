package com.example.cal_24point;

public class Card {
    private String info;//卡牌信息
    private int image;//卡牌图片

    public Card(String info, int image) {
        this.info = info;
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
