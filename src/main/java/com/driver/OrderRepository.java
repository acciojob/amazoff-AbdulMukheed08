package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> orderMap = new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerMap = new HashMap<>();
    HashMap<String,List<String>> assignedOrderMap = new HashMap<>();
    HashMap<String,Order> unassignedOrderMap = new HashMap<>();


    public void addOrder(String orderId,Order order) {
        orderMap.put(orderId,order);
        unassignedOrderMap.put(orderId,order);
    }

    public void addPartner(String partnerId) {
        deliveryPartnerMap.put(partnerId,new DeliveryPartner(partnerId));
        List<String> list = new ArrayList<>();
        assignedOrderMap.put(partnerId,list);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        //System.out.println(orderMap);
        //System.out.println(deliveryPartnerMap);
        List<String> list = assignedOrderMap.get(partnerId);
        list.add(orderId);
        assignedOrderMap.put(partnerId,list);
        unassignedOrderMap.remove(orderId);
    }

    public Order getOrderById(String orderId) {
        return orderMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerMap.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        System.out.println(assignedOrderMap);
        return assignedOrderMap.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return assignedOrderMap.get(partnerId);
    }

    public List<String> getAllOrders() {
        List<String> ordersList = new ArrayList<>();
        for(String orderId : orderMap.keySet()){
            ordersList.add(orderId);
        }
        return ordersList;
    }

    public Integer getCountOfUnassignedOrders() {
        return unassignedOrderMap.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int hh = Integer.valueOf(time.substring(0,2));
        int mm = Integer.valueOf(time.substring(3));
        int currTime =hh*60+mm;

        int undelivered = 0;

        List<String> assignedOrders = assignedOrderMap.get(partnerId);

        for(String orderId : assignedOrders){
            Order order = orderMap.get(orderId);
            if(currTime<order.getDeliveryTime()){
                undelivered++;
            }
        }

        return undelivered;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int lastDeliveryTime = 0;
        List<String> assignedOrders = assignedOrderMap.get(partnerId);
        for(String orderId : assignedOrders){
            Order order = orderMap.get(orderId);
            lastDeliveryTime = Math.max(lastDeliveryTime,order.getDeliveryTime());
        }
        int hh = lastDeliveryTime/60;
        int mm = lastDeliveryTime%60;

        String hhs = String.valueOf(hh);
        if(hh<10){
            hhs = "0"+hhs;
        }
        String mms = String.valueOf(mm);
        if(mm<10){
            mms = "0"+mms;
        }

        return hhs+":"+mms;
    }

    public void deletePartnerById(String partnerId) {
        List<String> ordersAssigned = assignedOrderMap.get(partnerId);
        for(String orderId : ordersAssigned){
            Order order = orderMap.get(orderId);
            unassignedOrderMap.put(orderId,order);
        }
        assignedOrderMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderMap.remove(orderId);
        for(List<String> list : assignedOrderMap.values()){
            for(int i=0;i<list.size();i++){
                if(list.get(i) == orderId){
                    list.remove(i);
                    break;
                }
            }
        }
    }
}
