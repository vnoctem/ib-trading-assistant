package com.vg;

import com.ib.client.EClientSocket;
import com.ib.client.EReader;
import com.ib.client.EReaderSignal;
import com.vg.model.OsAlgoOption;
import com.vg.service.IBBroker;
import com.vg.service.IBReceiver;

import java.util.Scanner;

public class Application {


    public static void main(String[] args) throws InterruptedException {

        // Connect to TWS instance
        IBReceiver receiver = new IBReceiver();

        final EClientSocket client = receiver.getClient();
        final EReaderSignal readerSignal = receiver.getSignal();

        IBBroker broker = new IBBroker(client);

        broker.connectToPaperTrading();

        final EReader reader = new EReader(client, readerSignal);

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



        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter alert: ");
        String alert = scanner.nextLine();

        OsAlgoOption option = broker.createOsAlgoOption(alert);
        System.out.println(option.toString());

        broker.getOptionChain(receiver.getNextReqId(), option);

//        System.out.print("Enter your age ? ");
//        int age = scanner.nextInt();

        //Print all name, age etc
    }

}
