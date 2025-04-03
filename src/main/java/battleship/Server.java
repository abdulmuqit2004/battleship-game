package battleship;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static final int PORT = 12345;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Battleship Server started on port " + PORT);

        while (clients.size() < 2) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket, clients.size() + 1);
            clients.add(handler);
            handler.start();
            System.out.println("Player " + handler.getPlayerId() + " connected.");
        }

        ClientHandler c1 = clients.get(0);
        ClientHandler c2 = clients.get(1);

        // Wait until both clients have initialized their streams
        while (c1.getOut() == null || c2.getOut() == null) {
            try {
                Thread.sleep(50); // short delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Safe to assign opponents now
        c1.setOpponent(c2);
        c2.setOpponent(c1);

        System.out.println("Both players connected. Game ready to begin.");
    }
}
