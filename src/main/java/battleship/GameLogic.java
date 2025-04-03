package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameLogic {

    private static boolean isMyTurn = false;

    public static void setupGameStart() {
        UIManager.start.addActionListener(e -> {
            if (!allShipsPlaced()) {
                UIManager.announce.setForeground(Color.RED);
                UIManager.announce.setText("You must place all ships first!");
                return;
            }

            // Build player's ship grid to send to server
            int[][] shipGrid = new int[10][10];
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    if (UIManager.fleet[i][j].getBackground().equals(Color.DARK_GRAY)) {
                        shipGrid[i - 1][j - 1] = 1;
                    }
                }
            }

            // Send READY message with ship grid
            GameMessage msg = new GameMessage();
            msg.type = "READY";
            msg.shipGrid = shipGrid;
            msg.playerId = Client.playerId;
            msg.message = "Player is ready";

            try {
                Client.out.writeObject(msg);
                UIManager.announce.setText("Waiting for opponent...");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            hidePlacementUI();
        });

        UIManager.quit.addActionListener(e -> System.exit(0));
        setupAttackListeners();
        listenForMessages();
    }

    private static boolean allShipsPlaced() {
        int count = 0;
        JButton[][] fleet = UIManager.fleet;
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (fleet[i][j].getBackground().equals(Color.DARK_GRAY)) count++;
            }
        }
        return count == 17;
    }

    private static void hidePlacementUI() {
        UIManager.shipRemove.setVisible(false);
        UIManager.rotate.setVisible(false);
        UIManager.start.setVisible(false);
    }

    private static void setupAttackListeners() {
        ActionListener attackAction = e -> {
            if (!isMyTurn) return;

            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    if (e.getSource() == UIManager.attack[i][j]) {
                        try {
                            GameMessage msg = new GameMessage();
                            msg.type = "ATTACK";
                            msg.x = j - 1;
                            msg.y = i - 1;
                            msg.playerId = Client.playerId;
                            Client.out.writeObject(msg);

                            UIManager.attack[i][j].setEnabled(false);
                            isMyTurn = false;
                            UIManager.label.setText("Opponent's Turn");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        };

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                UIManager.attack[i][j].addActionListener(attackAction);
                UIManager.attack[i][j].setEnabled(false); // disable until game begins
            }
        }
    }

    private static void listenForMessages() {
        Thread listener = new Thread(() -> {
            try {
                while (true) {
                    GameMessage msg = (GameMessage) Client.in.readObject();

                    switch (msg.type) {
                        case "TURN":
                            isMyTurn = true;
                            UIManager.label.setText("Your Turn");
                            enableAttackBoard();
                            break;

                        case "ATTACK":
                            int x = msg.x;
                            int y = msg.y;
                            JButton target = UIManager.fleet[y + 1][x + 1];
                            boolean hit = target.getBackground().equals(Color.DARK_GRAY);
                            target.setBackground(hit ? Color.RED : Color.CYAN);
                            target.setEnabled(false);

                            GameMessage result = new GameMessage();
                            result.type = "RESULT";
                            result.hit = hit;
                            result.x = x;
                            result.y = y;
                            result.playerId = Client.playerId;

                            Client.out.writeObject(result);
                            break;

                        case "RESULT":
                            int resX = msg.x;
                            int resY = msg.y;
                            JButton resBtn = UIManager.attack[resY + 1][resX + 1];
                            resBtn.setBackground(msg.hit ? Color.RED : Color.CYAN);
                            break;

                        case "OPPONENT_READY":
                            UIManager.announce.setText("Opponent is ready");
                            break;

                        case "WIN":
                            endGame(true);
                            break;

                        case "LOSE":
                            endGame(false);
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Disconnected from server.");
            }
        });

        listener.start();
    }

    private static void enableAttackBoard() {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (UIManager.attack[i][j].getBackground().equals(Color.BLUE)) {
                    UIManager.attack[i][j].setEnabled(true);
                }
            }
        }
    }

    private static void endGame(boolean playerWon) {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                UIManager.attack[i][j].setEnabled(false);
                UIManager.fleet[i][j].setEnabled(false);
            }
        }

        UIManager.label.setText(playerWon ? "YOU WON!" : "You lost!");
        UIManager.panel.setBackground(playerWon ? Color.GREEN : Color.RED);
    }
}
