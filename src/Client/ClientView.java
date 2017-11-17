package eu.tomaswandahl.Client;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;


public class ClientView implements Runnable{
    private final Scanner console = new Scanner(System.in);
    private boolean running = false;
    private ClientController contr;
    private final SyncPrint SynchronizedPrinter = new SyncPrint();

    public void start() {
        if (running) return;
        running = true;
        SynchronizedPrinter.println("Please connect to a server with the command: connect ip port");
        contr = new ClientController();
        new Thread(this).start();
    }


    @Override
    public void run() {
        while (running) {
            try {
                String readLine = console.nextLine().toLowerCase();
                String[] nextCmd = readLine.split(" ");
                switch (nextCmd[0]) {
                    case "connect":
                        contr.connect(nextCmd[1], Integer.parseInt(nextCmd[2]), new ConsoleOutput());
                        break;
                    case "guess":
                        contr.guess(nextCmd[1]);
                        break;
                    case "start":
                        contr.start();
                        break;
                    case "quit":
                        running = false;
                        contr.quit();
                        break;
                    default:
                        SynchronizedPrinter.println("Unknown command");
                        SynchronizedPrinter.println("Please use one of the following commands");
                        SynchronizedPrinter.println("start (Starts a new game)");
                        SynchronizedPrinter.println("guess (Guesses a letter or a word. Empty guesses are not allowed)");
                        SynchronizedPrinter.println("quit (Quits from the server)");
                }
            } catch (Exception e) {
                SynchronizedPrinter.println("Operation Failed");
            }
        }
    }

    private class ConsoleOutput implements Observer {
        @Override
        public void printMsg(String msg) {
            SynchronizedPrinter.println(msg);
        }
    }


}