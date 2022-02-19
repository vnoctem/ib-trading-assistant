package com.vg.store;

import com.ib.client.ContractDetails;

public class IBDataStore {

    private int currentOrderId = -1;
    private ContractDetails contractDetails;

    public ContractDetails getContractDetails() {return contractDetails;}

    public void setContractDetails(ContractDetails contractDetails) {this.contractDetails = contractDetails;}

    public int getCurrentOrderId() {
        return currentOrderId;
    }

    public void setCurrentOrderId(int orderId) {
        currentOrderId = orderId;
    }

    public int getNextOrderId() {
        return currentOrderId++;
    }
}
