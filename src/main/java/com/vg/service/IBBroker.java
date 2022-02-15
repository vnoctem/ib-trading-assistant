package com.vg.service;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.Order;
import com.vg.model.OsAlgoOption;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class IBBroker {

    private final EClientSocket client;

    private static final String TWS_ADDRESS = "127.0.0.1";
    private static final int TWS_PORT = 7497;
    private static final int TWS_PORT_PAPER_TRADING = 7496;
    private static final int CLIENT_ID = 1;


    public IBBroker(EClientSocket client) {
        this.client = client;
    }

    public void connect() {
        client.eConnect(TWS_ADDRESS, TWS_PORT, CLIENT_ID);
    }

    public void connectToPaperTrading() {
        client.eConnect(TWS_ADDRESS, TWS_PORT_PAPER_TRADING, CLIENT_ID);
    }

    public void disconnect() {
        client.eDisconnect();
    }

    public void getOptionChainBySymbol(int reqId, String symbol) {
        Contract contract = new Contract();

        contract.symbol(symbol);
        contract.secType("OPT");
        contract.exchange("SMART");
        contract.currency("USD");
        contract.right("CALL");
        contract.strike(170);

        client.reqContractDetails(reqId, contract);
    }

    public void getOptionChain(int reqId, OsAlgoOption option) {
        Contract contract = new Contract();

        contract.symbol(option.getSymbol());
        contract.secType("OPT");
        contract.exchange("SMART");
        contract.currency("USD");
        contract.right(option.getSide());
        contract.strike(option.getStrike());
        contract.lastTradeDateOrContractMonth(option.getDate());

        client.reqContractDetails(reqId, contract);
    }

    public OsAlgoOption createOsAlgoOption(String alert) {
        String[] info = alert.trim().split("-", 5);

        // TODO change to builder pattern
        OsAlgoOption osAlgoOption = new OsAlgoOption();
        osAlgoOption.setSymbol(info[0].trim());
        osAlgoOption.setStrike(Integer.parseInt(info[1].trim().substring(1)));
        osAlgoOption.setSide(info[2].trim());
        osAlgoOption.setDate(convertDateString(info[3].trim()));

        String cost = info[4].trim().split("@")[1].trim();
        cost = cost.substring(cost.indexOf("$") + 1);
        cost = cost.substring(0, cost.indexOf("A") - 1);
        osAlgoOption.setCost(Double.parseDouble(cost));

        return osAlgoOption;
    }

    public Order createOrder(OsAlgoOption option) {
        Order order = new Order();

        //ContractDetails details

        order.referenceContractId(520650186);
        return null;
    }

    private String convertDateString(String dateString) {
        DateTimeFormatter formatter = null;
        if (dateString.length() == 10) { // MMM d yyyy
            formatter = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
        } else if (dateString.length() == 11) { // MM dd yyyy
            formatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
        } else {
            throw new DateTimeException("Error while converting Date");
        }
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH));
    }


}
