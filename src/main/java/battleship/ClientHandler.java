package battleship;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ClientHandler opponent;
    private int playerId;
    private boolean ready = false;

    public ClientHandler(Socket socket, int playerId) {
        this.socket = socket;
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setOpponent(ClientHandler opponent) {
        this.opponent = opponent;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void sendMessage(GameMessage msg) throws IOException {
        if (out != null) {
            out.writeObject(msg);
            out.flush();
        } else {
            System.out.println("sendMessage(): Output stream is null for Player " + playerId);
        }
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            GameMessage joinMsg = new GameMessage();
            joinMsg.type = "JOINED";
            joinMsg.playerId = playerId;
            joinMsg.message = "You are Player " + playerId;
            sendMessage(joinMsg);
            System.out.println("Player " + playerId + " connected and JOINED message sent.");

            while (true) {
                try {
                    GameMessage incoming = (GameMessage) in.readObject();
                    System.out.println("Player " + playerId + " received: " + incoming.type);

                    switch (incoming.type) {
                        case "READY":
                            ready = true;
                            System.out.println("Player " + playerId + " is ready.");
                            if (opponent != null) {
                                GameMessage readyMsg = new GameMessage();
                                readyMsg.type = "OPPONENT_READY";
                                readyMsg.message = "Opponent is ready!";
                                opponent.sendMessage(readyMsg);
                            }
                            checkIfBothReady();
                            break;

                        case "ATTACK":
                            if (opponent != null) opponent.sendMessage(incoming);
                            break;

                        case "RESULT":
                            if (opponent != null) opponent.sendMessage(incoming);
                            break;

                        case "WIN":
                            if (opponent != null) {
                                GameMessage loseMsg = new GameMessage();
                                loseMsg.type = "LOSE";
                                loseMsg.message = "You lost!";
                                opponent.sendMessage(loseMsg);
                            }
                            break;

                        default:
                            System.out.println("Unknown message type: " + incoming.type);
                            break;
                    }

                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Player " + playerId + " readObject() error: " + e.getMessage());
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Player " + playerId + " failed to initialize streams.");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Failed to close socket for Player " + playerId);
            }
            System.out.println("Player " + playerId + " disconnected.");
        }
    }

    private void checkIfBothReady() {
        if (this.ready && opponent != null && opponent.ready) {
            try {
                GameMessage turnMsg = new GameMessage();
                turnMsg.type = "TURN";
                turnMsg.message = "Your turn!";
                turnMsg.playerId = 1;

                if (playerId == 1) {
                    sendMessage(turnMsg);
                } else {
                    opponent.sendMessage(turnMsg);
                }

                System.out.println("Both players ready. Game started for Player 1.");
            } catch (IOException e) {
                System.out.println("Error starting turn for Player " + playerId);
                e.printStackTrace();
            }
        } else {
            System.out.println("checkIfBothReady(): Waiting on opponent or readiness. Player " + playerId);
        }
    }
}
