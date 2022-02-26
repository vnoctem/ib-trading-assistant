package com.vg;

import com.ib.client.*;
import com.vg.model.IBOption;
import com.vg.service.IBBroker;
import com.vg.service.IBReceiver;
import com.vg.store.IBDataStore;

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

        // Wait for successful connection before continuing
        try {
            while (!client.isConnected()) {
                // nothing
            }
        } catch (Exception e) {
            System.out.println("Exception happened during connection: " + e.getMessage());
        }

        System.out.println("INFO - Connection to TWS instance is successful");

        final EReader reader = new EReader(client, readerSignal);

        Scanner scanner = new Scanner(System.in);

        // Start reading incoming messages
        reader.start();
        new Thread(() -> {
            while (client.isConnected()) {
                if (dataStore.isReadyForAlert()) {
                    readAlert(dataStore, broker, scanner);
                }

                readerSignal.waitForSignal();
                try {
                    reader.processMsgs();
                } catch (Exception e) {
                    System.out.println("Thread exception: " + e.getMessage());
                }
            }
        }).start();
        Thread.sleep(1000);

        readAlert(dataStore, broker, scanner);
    }

    private static void readAlert(IBDataStore dataStore, IBBroker broker, Scanner scanner) {
        // Create a scanner so we can read the command-line input
        //Scanner scanner = new Scanner(System.in);

        // Read alert
        System.out.print("Enter alert: ");
        String alert = scanner.nextLine();

        // Create Option
        IBOption option = broker.createIBOption(alert);
        System.out.println(option.toString()); // TODO debugging

        dataStore.setOption(option);

        // Get Option details (start trading flow)
        broker.getContractDetails(dataStore.getNextValidId(), option);

        if (scanner.nextLine().equalsIgnoreCase("exit")) {
            broker.disconnect();
            System.out.print("INFO - Client disconnected");
            System.out.print("INFO - Exiting application");
            System.exit(0);
        }

    }

}
