package edu.huflit.vn.joyhtycz.model;

import java.io.Serializable;
import java.util.Comparator;

public class MainModel implements Serializable, Comparable<MainModel>{

    String chose;
    String hinhanh;

    int price;
    String id;


    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinh_anh) {
        this.hinhanh = hinh_anh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public MainModel(String id, String chose, int price, String hinh_anh) {
        this.id = id;
        this.chose = chose;
        this.price = price;
        this.hinhanh = hinh_anh;


    }

    public String getChose() {
        return chose;
    }

    public void setChose(String chose) {
        this.chose = chose;
    }



//    public String getSize() {
//        return size;
//    }
//
//    public void setSize(String size) {
//        this.size = size;
//    }


//public static Comparator<MainModel> chosename = new Comparator<MainModel>() {
//    @Override
//    public int compare(MainModel t1, MainModel t2) {
//        return t1.getChose().compareTo(t2.getChose());
//    }
//};
    @Override
    public int compareTo(MainModel mainModel){
        return this.price - mainModel.getPrice();
    }

}
