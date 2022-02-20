package com.vg.store;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.vg.model.IBOrder;
import com.vg.model.OsAlgoOption;

public class IBDataStore {

    private int nextValidId = -1;
    private ContractDetails contractDetails;
    private OsAlgoOption option;

    private int currentParentOrderId = -1;
    private int currentTakeProfitOrderId = -1;
    private int currentStopLossOrderId = -1;

    private IBOrder parentOrder;
    private IBOrder takeProfitOrder;
    private IBOrder stopLossOrder;

    private Contract contract;

    public ContractDetails getContractDetails() {return contractDetails;}

    public void setContractDetails(ContractDetails contractDetails) {this.contractDetails = contractDetails;}

    public int getNextValidId() {
        return nextValidId++;
    }

    public void setNextValidId(int orderId) { nextValidId = orderId; }

    public OsAlgoOption getOption() { return option; }

    public void setOption(OsAlgoOption option) { this.option = option; }

    public int getCurrentParentOrderId() {return currentParentOrderId;}

    public void setCurrentParentOrderId(int currentParentOrderId) {this.currentParentOrderId = currentParentOrderId;}

    public int getCurrentTakeProfitOrderId() {return currentTakeProfitOrderId;}

    public void setCurrentTakeProfitOrderId(int currentTakeProfitOrderId) {this.currentTakeProfitOrderId = currentTakeProfitOrderId;}

    public int getCurrentStopLossOrderId() {return currentStopLossOrderId;}

    public void setCurrentStopLossOrderId(int currentStopLossOrderId) {this.currentStopLossOrderId = currentStopLossOrderId;}

    public IBOrder getParentOrder() {return parentOrder;}

    public void setParentOrder(IBOrder parentOrder) {this.parentOrder = parentOrder;}

    public IBOrder getTakeProfitOrder() {return takeProfitOrder;}

    public void setTakeProfitOrder(IBOrder takeProfitOrder) {this.takeProfitOrder = takeProfitOrder;}

    public IBOrder getStopLossOrder() {return stopLossOrder;}

    public void setStopLossOrder(IBOrder stopLossOrder) {this.stopLossOrder = stopLossOrder;}

    public Contract getContract() {return contract;}

    public void setContract(Contract contract) {this.contract = contract;}
}
