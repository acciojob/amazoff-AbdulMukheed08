package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        String[] del = deliveryTime.split(":");
        int hh = Integer.valueOf(del[0]);
        int mm = Integer.valueOf(del[1]);

        this.deliveryTime = hh*60+mm;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
