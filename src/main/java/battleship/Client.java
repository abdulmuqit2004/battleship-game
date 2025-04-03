package battleship;

import java.io.*;
import java.net.Socket;

public class Client {
    public static ObjectOutputStream out;
    public static ObjectInputStream in;
    public static int playerId;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // ✅ Receive JOINED message
            GameMessage joined = (GameMessage) in.readObject();
            playerId = joined.playerId;
            System.out.println(joined.message);

            UIManager.initializeUI();

            UIManager.setMessage(joined.message);

            ShipPlacementManager.setupShipPlacement();

            GameLogic.setupGameStart();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
