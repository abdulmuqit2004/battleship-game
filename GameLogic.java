package finalassignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

public class GameLogic {

    public static int[][] attacked = new int[10][10];

    public static void setupGameStart() {
        UIManager.start.addActionListener(e -> {
            if (!allShipsPlaced()) {
                UIManager.announce.setForeground(Color.RED);
                UIManager.announce.setText("You must place all ships first!");
                return;
            }

            setComputerShips();
            hidePlacementUI();
            setupAttackListeners();
        });

        UIManager.quit.addActionListener(e -> System.exit(0));
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
        JButton[][] attack = UIManager.attack;
        JButton[][] fleet = UIManager.fleet;

        ActionListener playerMove = e -> {
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    if (e.getSource() == attack[i][j]) {
                        if (Utils.checkHit(attacked[i - 1][j - 1])) {
                            attack[i][j].setBackground(Color.RED);
                        } else {
                            attack[i][j].setBackground(Color.CYAN);
                        }
                        attack[i][j].setEnabled(false);
                    }
                }
            }

            if (Utils.checkWin('a', UIManager.fleet, UIManager.attack) == 1) {
                endGame(true);
                return;
            }

            computerTurn();

            if (Utils.checkWin('f', UIManager.fleet, UIManager.attack) == 1) {
                endGame(false);
            }
        };

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                attack[i][j].addActionListener(playerMove);
            }
        }
    }

    private static void computerTurn() {
        JButton[][] fleet = UIManager.fleet;
        Random rand = new Random();

        while (true) {
            int i = rand.nextInt(10) + 1;
            int j = rand.nextInt(10) + 1;

            if (fleet[i][j].isEnabled()) {
                if (fleet[i][j].getBackground().equals(Color.DARK_GRAY)) {
                    fleet[i][j].setBackground(Color.RED);
                } else {
                    fleet[i][j].setBackground(Color.CYAN);
                }
                fleet[i][j].setEnabled(false);
                break;
            }
        }
    }

    private static void endGame(boolean playerWon) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                UIManager.attack[i][j].setEnabled(false);
                UIManager.fleet[i][j].setEnabled(false);
            }
        }

        UIManager.label.setText(playerWon ? "YOU WON!" : "Better luck next time!");
        UIManager.panel.setBackground(playerWon ? Color.GREEN : Color.RED);

        UIManager.restart.setBounds(1200, 150, 150, 150);
        UIManager.restart.setText("Replay");
        UIManager.restart.setBackground(Color.MAGENTA);
        UIManager.restart.setForeground(Color.BLACK);
        UIManager.restart.setFont(new Font("Ariel", Font.PLAIN, 20));
        UIManager.panel.add(UIManager.restart);

        UIManager.restart.addActionListener(e -> restartGame());
    }

    private static void restartGame() {
        String javaPath = System.getProperty("java.home") + "/bin/java";
        String classPath = System.getProperty("java.class.path");
        String className = BattleShip.class.getCanonicalName();
        ProcessBuilder pb = new ProcessBuilder(javaPath, "-cp", classPath, className);

        try {
            pb.start();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Restart failed");
        }
    }

    private static void setComputerShips() {
        Random rand = new Random();
        int k = 0;

        while (true) {
            k = 0;
            resetAttackedGrid();

            if (placeShip(rand, 2)) k++; // destroyer
            if (placeShip(rand, 3)) k++; // cruiser
            if (placeShip(rand, 3)) k++; // submarine
            if (placeShip(rand, 4)) k++; // battleship
            if (placeShip(rand, 5)) k++; // aircraft

            if (k == 5) break;
        }
    }

    private static boolean placeShip(Random rand, int length) {
        int x, y, o;
        try {
            o = rand.nextInt(2); // 0 = horizontal, 1 = vertical
            x = rand.nextInt(10) + 1;
            y = rand.nextInt(10) + 1;

            if (o == 0) {
                for (int i = 0; i < length; i++) {
                    if (attacked[y - 1][x - 1 - i] == 1) return false;
                }
                if (!Utils.compCheckPossible(attacked, x, y, length, 1)) return false;
                for (int i = 0; i < length; i++) {
                    attacked[y - 1][x - 1 - i] = 1;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    if (attacked[y - 1 - i][x - 1] == 1) return false;
                }
                if (!Utils.compCheckPossible(attacked, x, y, 1, length)) return false;
                for (int i = 0; i < length; i++) {
                    attacked[y - 1 - i][x - 1] = 1;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static void resetAttackedGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                attacked[i][j] = 0;
                UIManager.attack[i + 1][j + 1].setBackground(Color.BLUE);
            }
        }
    }
}
