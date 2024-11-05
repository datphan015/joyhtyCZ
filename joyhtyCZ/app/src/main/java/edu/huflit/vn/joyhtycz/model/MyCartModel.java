package edu.huflit.vn.joyhtycz.model;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    int price;
    String id ;
    int size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String chosename;
    String hinhanh;
    int total;

    public MyCartModel( int price, int size, String chosename, String hinhanh) {

        this.price = price;
        this.size = size;
        this.chosename = chosename;
        this.hinhanh = hinhanh;
        this.total = price*size;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getChosename() {
        return chosename;
    }

    public void setChosename(String chosename) {
        this.chosename = chosename;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
