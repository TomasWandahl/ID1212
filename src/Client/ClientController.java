package eu.tomaswandahl.Client;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;


public class ClientController {
    private final ClientNet net = new ClientNet();

    public void guess(String s) {
        CompletableFuture.runAsync(() -> net.guess(s));
    }

    public void start() {
        CompletableFuture.runAsync(net::start);
    }

    public void quit() throws IOException {
        net.quit();
    }

    public void connect(String host, int port, Observer obs) {
        CompletableFuture.runAsync(() -> {
            try {
                net.connect(host, port, obs);
            } catch (IOException ioe) {
                throw new UncheckedIOException(ioe);
            }
        }).thenRun(() -> obs.printMsg("Connected to " + host + ":" + port +"\nStart a game by using the command start"));
    }
}
