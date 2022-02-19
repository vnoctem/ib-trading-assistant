package com.vg;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.EReader;
import com.ib.client.EReaderSignal;
import com.vg.model.OsAlgoOption;
import com.vg.service.IBBroker;
import com.vg.service.IBReceiver;
import com.vg.store.IBDataStore;

import java.util.Scanner;

public class Application {


    public static void main(String[] args) throws InterruptedException {

        // Create necessary objects to connect to TWS instance
        final IBDataStore dataStore = new IBDataStore();
        final IBReceiver receiver = new IBReceiver(dataStore);
        final EClientSocket client = receiver.getClient();
        final EReaderSignal readerSignal = receiver.getSignal();
        final EReader reader = new EReader(client, readerSignal);
        final IBBroker broker = new IBBroker(client, dataStore);

        // Select live or paper trading
        broker.connectToPaperTrading();

        // Start reading incoming messages
        reader.start();
        new Thread(() -> {
            while (client.isConnected()) {
                readerSignal.waitForSignal();
                try {
                    reader.processMsgs();
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        }).start();
        Thread.sleep(1000);

        // Create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            // Read alert
            System.out.print("Enter alert: ");
            String alert = scanner.nextLine();

            // Create Option
            OsAlgoOption option = broker.createOsAlgoOption(alert);
            System.out.println(option.toString()); // TODO debugging

            // Get Option details
            broker.getOptionDetails(dataStore.getNextOrderId(), option);

            // Create buying order
            //dataStore.getContractDetails().contract()

            // Place order
            //client.placeOrder(dataStore.getNextOrderId(), );

            if (scanner.nextLine().equalsIgnoreCase("exit")) {
                isRunning = false;
            }
        }

        System.out.println("Exiting");
        System.exit(0);
    }

}
