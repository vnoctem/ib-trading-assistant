package com.vg.service;

import com.ib.client.*;
import com.vg.model.IBOrder;
import com.vg.model.IBOption;
import com.vg.store.IBDataStore;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class IBReceiver implements EWrapper {

    private final EReaderSignal readerSignal;
    private final EClientSocket client;
    private final IBDataStore dataStore;

    private static final int MAX_COST_BASIS = 250;

    public IBReceiver(IBDataStore dataStore) {
        this.readerSignal = new EJavaSignal();
        this.client = new EClientSocket(this, readerSignal);
        this.dataStore = dataStore;
    }

    public EClientSocket getClient() {
        return client;
    }

    public EReaderSignal getSignal() {
        return readerSignal;
    }

    @Override
    public void tickPrice(int i, int i1, double v, TickAttrib tickAttrib) {

    }

    @Override
    public void tickSize(int i, int i1, int i2) {

    }

    @Override
    public void tickOptionComputation(int i,
            int i1,
            double v,
            double v1,
            double v2,
            double v3,
            double v4,
            double v5,
            double v6,
            double v7) {

    }

    @Override
    public void tickGeneric(int i, int i1, double v) {

    }

    @Override
    public void tickString(int i, int i1, String s) {

    }

    @Override
    public void tickEFP(int i, int i1, double v, String s, double v1, int i2, String s1, double v2, double v3) {

    }

    @Override
    public void orderStatus(int i,
            String s,
            double v,
            double v1,
            double v2,
            int i1,
            int i2,
            double v3,
            int i3,
            String s1,
            double v4) {

        // If parent order is Submitted, then ready for next alert
        if (i == dataStore.getCurrentParentOrderId() && s.equalsIgnoreCase("Submitted")) {
            System.out.println("INFO - Parent order was submitted");

            dataStore.setReadyForAlert(true);
        }
//
//        // If take profit order is PreSubmitted or Submitted, then place StopLoss order
//        if (i == dataStore.getCurrentTakeProfitOrderId() && s.equalsIgnoreCase("PreSubmitted")) {
//            IBOrder stopLossOrder = dataStore.getStopLossOrder();
//            stopLossOrder.transmit(true);
//
//            client.placeOrder(stopLossOrder.orderId(), dataStore.getContract(), stopLossOrder);
//            System.out.println("INFO - Stop Loss Order placed " + stopLossOrder);
//        }
    }

    @Override
    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
        //System.out.println(EWrapperMsgGenerator.openOrder(orderId, contract, order, orderState));
    }

    @Override
    public void openOrderEnd() {

    }

    @Override
    public void updateAccountValue(String s, String s1, String s2, String s3) {

    }

    @Override
    public void updatePortfolio(Contract contract,
            double v,
            double v1,
            double v2,
            double v3,
            double v4,
            double v5,
            String s) {

    }

    @Override
    public void updateAccountTime(String s) {

    }

    @Override
    public void accountDownloadEnd(String s) {

    }

    @Override
    public void nextValidId(int orderId) {
        System.out.println(EWrapperMsgGenerator.nextValidId(orderId));
        dataStore.setNextValidId(orderId);
    }

    /**
     * This method is called when we receive a response from EClientSocket.reqContractDetails()
     * We want to create a bracket order and pla
     *
     * @param orderId
     * @param contractDetails
     */
    @Override
    public void contractDetails(int orderId, ContractDetails contractDetails) {
        System.out.println(EWrapperMsgGenerator.contractDetails(orderId, contractDetails));

        // Get Contract
        Contract contract = contractDetails.contract();
        dataStore.setContract(contract);



        // Calculate quantity for a maximum cost basis if contract cost is less than that amount
        IBOption option = dataStore.getOption();
        int quantity = 1;

        if ((option.getCost() * 100) < MAX_COST_BASIS) {
            quantity = (int) (MAX_COST_BASIS / (option.getCost() * 100));
        }

        // Get Orders
        List<IBOrder> orders = IBBroker.createOrders(dataStore.getNextValidId(), quantity, dataStore.getOption());
        dataStore.setCurrentParentOrderId(orders.get(0).orderId());

        // Place orders
        for (IBOrder order : orders) {
            client.placeOrder(order.orderId(), contract, order);
            System.out.println("INFO - Order placed: " + order);
        }
    }

    @Override
    public void bondContractDetails(int i, ContractDetails contractDetails) {

    }

    @Override
    public void contractDetailsEnd(int i) {

    }

    @Override
    public void execDetails(int i, Contract contract, Execution execution) {

    }

    @Override
    public void execDetailsEnd(int i) {

    }

    @Override
    public void updateMktDepth(int i, int i1, int i2, int i3, double v, int i4) {

    }

    @Override
    public void updateMktDepthL2(int i, int i1, String s, int i2, int i3, double v, int i4, boolean b) {

    }

    @Override
    public void updateNewsBulletin(int i, int i1, String s, String s1) {

    }

    @Override
    public void managedAccounts(String s) {

    }

    @Override
    public void receiveFA(int i, String s) {

    }

    @Override
    public void historicalData(int i, Bar bar) {

    }

    @Override
    public void scannerParameters(String s) {

    }

    @Override
    public void scannerData(int i, int i1, ContractDetails contractDetails, String s, String s1, String s2, String s3) {

    }

    @Override
    public void scannerDataEnd(int i) {

    }

    @Override
    public void realtimeBar(int i, long l, double v, double v1, double v2, double v3, long l1, double v4, int i1) {

    }

    @Override
    public void currentTime(long l) {

    }

    @Override
    public void fundamentalData(int i, String s) {

    }

    @Override
    public void deltaNeutralValidation(int i, DeltaNeutralContract deltaNeutralContract) {

    }

    @Override
    public void tickSnapshotEnd(int i) {

    }

    @Override
    public void marketDataType(int i, int i1) {

    }

    @Override
    public void commissionReport(CommissionReport commissionReport) {

    }

    @Override
    public void position(String s, Contract contract, double v, double v1) {

    }

    @Override
    public void positionEnd() {

    }

    @Override
    public void accountSummary(int i, String s, String s1, String s2, String s3) {

    }

    @Override
    public void accountSummaryEnd(int i) {

    }

    @Override
    public void verifyMessageAPI(String s) {

    }

    @Override
    public void verifyCompleted(boolean b, String s) {

    }

    @Override
    public void verifyAndAuthMessageAPI(String s, String s1) {

    }

    @Override
    public void verifyAndAuthCompleted(boolean b, String s) {

    }

    @Override
    public void displayGroupList(int i, String s) {

    }

    @Override
    public void displayGroupUpdated(int i, String s) {

    }

    @Override
    public void error(Exception e) {
        System.out.println("API exception: " + e.getMessage());
    }

    @Override
    public void error(String errorMsg) {
        System.out.println("Error: " + errorMsg);
    }

    @Override
    public void error(int id, int errorCode, String errorMsg) {
        System.out.println(EWrapperMsgGenerator.error(id, errorCode, errorMsg));
        //System.out.println("TWS Error: Id: " + id + ", Code: " + errorCode + ", Msg: " + errorMsg + "\n");
    }

    @Override
    public void connectionClosed() {

    }

    @Override
    public void connectAck() {

    }

    @Override
    public void positionMulti(int i, String s, String s1, Contract contract, double v, double v1) {

    }

    @Override
    public void positionMultiEnd(int i) {

    }

    @Override
    public void accountUpdateMulti(int i, String s, String s1, String s2, String s3, String s4) {

    }

    @Override
    public void accountUpdateMultiEnd(int i) {

    }

    @Override
    public void securityDefinitionOptionalParameter(int i,
            String s,
            int i1,
            String s1,
            String s2,
            Set<String> set,
            Set<Double> set1) {

    }

    @Override
    public void securityDefinitionOptionalParameterEnd(int i) {

    }

    @Override
    public void softDollarTiers(int i, SoftDollarTier[] softDollarTiers) {

    }

    @Override
    public void familyCodes(FamilyCode[] familyCodes) {

    }

    @Override
    public void symbolSamples(int i, ContractDescription[] contractDescriptions) {

    }

    @Override
    public void historicalDataEnd(int i, String s, String s1) {

    }

    @Override
    public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {

    }

    @Override
    public void tickNews(int i, long l, String s, String s1, String s2, String s3) {

    }

    @Override
    public void smartComponents(int i, Map<Integer, Map.Entry<String, Character>> map) {

    }

    @Override
    public void tickReqParams(int i, double v, String s, int i1) {

    }

    @Override
    public void newsProviders(NewsProvider[] newsProviders) {

    }

    @Override
    public void newsArticle(int i, int i1, String s) {

    }

    @Override
    public void historicalNews(int i, String s, String s1, String s2, String s3) {

    }

    @Override
    public void historicalNewsEnd(int i, boolean b) {

    }

    @Override
    public void headTimestamp(int i, String s) {

    }

    @Override
    public void histogramData(int i, List<HistogramEntry> list) {

    }

    @Override
    public void historicalDataUpdate(int i, Bar bar) {

    }

    @Override
    public void rerouteMktDataReq(int i, int i1, String s) {

    }

    @Override
    public void rerouteMktDepthReq(int i, int i1, String s) {

    }

    @Override
    public void marketRule(int i, PriceIncrement[] priceIncrements) {

    }

    @Override
    public void pnl(int i, double v, double v1, double v2) {

    }

    @Override
    public void pnlSingle(int i, int i1, double v, double v1, double v2, double v3) {

    }

    @Override
    public void historicalTicks(int i, List<HistoricalTick> list, boolean b) {

    }

    @Override
    public void historicalTicksBidAsk(int i, List<HistoricalTickBidAsk> list, boolean b) {

    }

    @Override
    public void historicalTicksLast(int i, List<HistoricalTickLast> list, boolean b) {

    }

    @Override
    public void tickByTickAllLast(int i,
            int i1,
            long l,
            double v,
            int i2,
            TickAttribLast tickAttribLast,
            String s,
            String s1) {

    }

    @Override
    public void tickByTickBidAsk(int i,
            long l,
            double v,
            double v1,
            int i1,
            int i2,
            TickAttribBidAsk tickAttribBidAsk) {

    }

    @Override
    public void tickByTickMidPoint(int i, long l, double v) {

    }

    @Override
    public void orderBound(long l, int i, int i1) {

    }

    @Override
    public void completedOrder(Contract contract, Order order, OrderState orderState) {
        System.out.println(EWrapperMsgGenerator.completedOrder(contract, order, orderState));
    }

    @Override
    public void completedOrdersEnd() {

    }
}
