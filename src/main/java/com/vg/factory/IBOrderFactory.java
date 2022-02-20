package com.vg.factory;

import com.ib.client.Order;
import com.vg.model.IBOrder;

import java.util.ArrayList;
import java.util.List;

public class IBOrderFactory {


    public static List<IBOrder> createBracketOrder(int parentOrderId, String action, double quantity, double limitPrice, double takeProfitLimitPrice, double stopLossPrice) {
        //This will be our main or "parent" order
        IBOrder parent = new IBOrder();
        parent.orderId(parentOrderId);
        parent.action(action);
        parent.orderType("LMT");
        parent.totalQuantity(quantity);
        parent.lmtPrice(limitPrice);
        //The parent and children orders will need this attribute set to false to prevent accidental executions.
        //The LAST CHILD will have it set to true.
        parent.transmit(false);

        IBOrder takeProfit = new IBOrder();
        takeProfit.orderId(parent.orderId() + 1);
        takeProfit.action(action.equals("BUY") ? "SELL" : "BUY");
        takeProfit.orderType("LMT");
        takeProfit.totalQuantity(quantity);
        takeProfit.lmtPrice(takeProfitLimitPrice);
        takeProfit.parentId(parentOrderId);
        takeProfit.transmit(false);

        IBOrder stopLoss = new IBOrder();
        stopLoss.orderId(parent.orderId() + 2);
        stopLoss.action(action.equals("BUY") ? "SELL" : "BUY");
        stopLoss.orderType("STP");
        //Stop trigger price
        stopLoss.auxPrice(stopLossPrice);
        stopLoss.totalQuantity(quantity);
        stopLoss.parentId(parentOrderId);
        //In this case, the low side order will be the last child being sent. Therefore, it needs to set this attribute to true
        //to activate all its predecessors
        stopLoss.transmit(true);

        List<IBOrder> bracketOrder = new ArrayList<>();
        bracketOrder.add(parent);
        bracketOrder.add(takeProfit);
        bracketOrder.add(stopLoss);

        return bracketOrder;
    }

    public static Order createLimitOrder(String action, double quantity, double limitPrice) {
        Order order = new Order();
        order.action(action);
        order.orderType("LMT");
        order.totalQuantity(quantity);
        order.lmtPrice(limitPrice);
        return order;
    }

    public static Order createMarketOrder(String action, double quantity) {
        Order order = new Order();
        order.action(action);
        order.orderType("MKT");
        order.totalQuantity(quantity);
        return order;
    }

    public static Order createMidpriceOrder(String action, double quantity, double priceCap) {
        Order order = new Order();
        order.action(action);
        order.orderType("MIDPRICE");
        order.totalQuantity(quantity);
        order.lmtPrice(priceCap); // optional
        return order;
    }




}
