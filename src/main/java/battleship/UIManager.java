package battleship;

import javax.swing.*;
import java.awt.*;

public class UIManager {

    public static JFrame frame = new JFrame("BattleShip");
    public static JPanel panel = new JPanel(), fleetBoard = new JPanel(), attackBoard = new JPanel();
    public static JLabel label = new JLabel(), fleetLabel = new JLabel("Fleet Grid"),
            attackLabel = new JLabel("Attack Grid"), announce = new JLabel(), announce2 = new JLabel();
    public static JButton[][] fleet = new JButton[11][11], attack = new JButton[11][11];
    public static JButton quit = new JButton("Quit"), destroyer = new JButton(), cruiser = new JButton(),
            submarine = new JButton(), battleship = new JButton(), aircraft = new JButton(),
            shipRemove = new JButton("Reset"), rotate = new JButton("Rotate"), start = new JButton("Play"),
            restart = new JButton("Restart");

    public static void initializeUI() {
        frame.setBounds(50, 50, 1500, 800);
        frame.setLayout(null);
        panel.setBounds(0, 0, 1500, 800);
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);

        label.setBounds(720, 550, 800, 30);
        label.setForeground(Color.white);
        label.setFont(new Font("Ariel", Font.BOLD, 30));
        label.setText("Place your ships, then PLAY!");

        announce.setBounds(720, 600, 800, 20);
        announce.setForeground(Color.white);
        announce.setFont(new Font("Ariel", Font.PLAIN, 20));
        announce.setText("Click a ship, then click the square where you want the ship to start.");

        announce2.setBounds(720, 630, 800, 20);
        announce2.setForeground(Color.white);
        announce2.setFont(new Font("Ariel", Font.PLAIN, 10));
        announce2.setText("*the clicked square will be the leftmost or upmost part of the ship*");

        fleetLabel.setBounds(720, 20, 200, 20);
        fleetLabel.setForeground(Color.white);
        fleetLabel.setFont(new Font("Ariel", Font.BOLD, 15));

        attackLabel.setBounds(50, 20, 200, 20);
        attackLabel.setForeground(Color.white);
        attackLabel.setFont(new Font("Ariel", Font.BOLD, 15));

        fleetBoard.setBounds(720, 40, 465, 465);
        fleetBoard.setLayout(new GridLayout(11, 11));
        fleetBoard.setBackground(Color.GREEN);

        attackBoard.setBounds(50, 40, 650, 650);
        attackBoard.setLayout(new GridLayout(11, 11));
        attackBoard.setBackground(Color.RED);

        quit.setBounds(1400, 725, 80, 30);
        quit.setBackground(Color.RED);
        quit.setForeground(Color.BLACK);

        shipRemove.setBounds(720, 510, 80, 30);
        shipRemove.setBackground(Color.RED);
        shipRemove.setForeground(Color.BLACK);

        rotate.setBounds(810, 510, 80, 30);
        rotate.setBackground(Color.YELLOW);
        rotate.setForeground(Color.BLACK);

        start.setBounds(900, 510, 80, 30);
        start.setBackground(Color.GREEN);
        start.setForeground(Color.BLACK);

        destroyer.setBounds(1200, 40, 84, 42);
        destroyer.setBackground(Color.DARK_GRAY);
        destroyer.setText("D");

        cruiser.setBounds(1340, 140, 126, 42);
        cruiser.setBackground(Color.DARK_GRAY);
        cruiser.setText("C");

        submarine.setBounds(1200, 140, 126, 42);
        submarine.setBackground(Color.DARK_GRAY);
        submarine.setText("S");

        battleship.setBounds(1200, 285, 168, 42);
        battleship.setBackground(Color.DARK_GRAY);
        battleship.setText("B");

        aircraft.setBounds(1200, 472, 210, 42);
        aircraft.setBackground(Color.DARK_GRAY);
        aircraft.setText("A");

        // Grid Buttons
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                fleet[i][j] = new JButton();
                fleet[i][j].setBackground(Color.BLUE);
                fleetBoard.add(fleet[i][j]);

                attack[i][j] = new JButton();
                attack[i][j].setBackground(Color.BLUE);
                attackBoard.add(attack[i][j]);
            }
        }

        fleet[0][0].setBackground(Color.BLACK);
        fleet[0][0].setEnabled(false);
        attack[0][0].setBackground(Color.BLACK);
        attack[0][0].setEnabled(false);

        for (int i = 1; i < 11; i++) {
            fleet[i][0].setBackground(Color.BLACK);
            fleet[i][0].setText(String.valueOf(i));
            fleet[i][0].setEnabled(false);

            attack[i][0].setBackground(Color.BLACK);
            attack[i][0].setText(String.valueOf(i));
            attack[i][0].setEnabled(false);

            fleet[0][i].setBackground(Color.BLACK);
            fleet[0][i].setEnabled(false);

            attack[0][i].setBackground(Color.BLACK);
            attack[0][i].setEnabled(false);
        }

        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int i = 1; i <= 10; i++) {
            fleet[0][i].setText(letters[i - 1]);
            attack[0][i].setText(letters[i - 1]);
        }

        frame.setVisible(true);
        frame.add(panel);
        panel.add(fleetBoard);
        panel.add(attackBoard);
        panel.add(quit);
        panel.add(start);
        panel.add(label);
        panel.add(fleetLabel);
        panel.add(attackLabel);
        panel.add(announce);
        panel.add(announce2);
    }
}
