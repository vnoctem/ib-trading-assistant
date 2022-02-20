package com.vg.model;

import com.ib.client.Order;

public class IBOrder extends Order {


    @Override
    public String toString() {

        return "IBOrder{" +
                "orderId='" + this.orderId() + '\'' +
                ", parentId='" + this.parentId() + '\'' +
                ", orderType='" + this.getOrderType() + '\'' +
                ", action='" + this.getAction() + '\'' +
                ", limitPrice='" + this.lmtPrice() + '\'' +
                ", auxPrice='" + this.auxPrice() + '\'' +
                ", quantity='" + this.totalQuantity() + '\'' +
                "}";
    }

}
