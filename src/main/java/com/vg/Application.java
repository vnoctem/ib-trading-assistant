package com.vg;

import com.ib.client.*;
import com.vg.model.OsAlgoOption;
import com.vg.service.IBBroker;
import com.vg.service.IBReceiver;
import com.vg.store.IBDataStore;

import java.util.List;
import java.util.Scanner;

public class Application {


    public static void main(String[] args) throws InterruptedException {

        // Connect to TWS instance
        IBDataStore dataStore = new IBDataStore();
        IBReceiver receiver = new IBReceiver(dataStore);

        final EClientSocket client = receiver.getClient();
        final EReaderSignal readerSignal = receiver.getSignal();

        IBBroker broker = new IBBroker(client);

        // Connect to socket
        broker.connectToPaperTrading();

        // Wait for validNextId callback to create EReader
        try {
            while (!client.isConnected()) {
                // nothing
            }
        } catch (Exception e) {
            System.out.println("Exception happened during connection: " + e.getMessage());
        }

        System.out.println("INFO - Connection to socket done");

        final EReader reader = new EReader(client, readerSignal);

        // Start reading incoming messages
        reader.start();
        new Thread(() -> {
            while (client.isConnected()) {
                readerSignal.waitForSignal();
                try {
                    reader.processMsgs();
                } catch (Exception e) {
                    System.out.println("Thread exception: " + e.getMessage());
                    //client.cancelOrder(dataStore.getCurrentOrderId());
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

            dataStore.setOption(option);

            // Get Option details
            broker.getContractDetails(dataStore.getNextValidId(), option);

            if (scanner.nextLine().equalsIgnoreCase("exit")) {
                isRunning = false;
            }
        }

        broker.disconnect();
        System.out.println("Exiting");
        System.exit(0);
    }

//    private void processMessages(EReader reader) {
//        reader.processMsgs();
//    }

}
