package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShipPlacementManager {

    private static char ship;
    private static int orientation = 0;

    public static void setupShipPlacement() {
        JPanel panel = UIManager.panel;
        panel.add(UIManager.shipRemove);
        panel.add(UIManager.rotate);
        panel.add(UIManager.destroyer);
        panel.add(UIManager.cruiser);
        panel.add(UIManager.submarine);
        panel.add(UIManager.battleship);
        panel.add(UIManager.aircraft);

        ActionListener shipChoice = e -> {
            orientation = 0;
            if (e.getSource() == UIManager.destroyer) ship = 'd';
            else if (e.getSource() == UIManager.cruiser) ship = 'c';
            else if (e.getSource() == UIManager.submarine) ship = 's';
            else if (e.getSource() == UIManager.battleship) ship = 'b';
            else if (e.getSource() == UIManager.aircraft) ship = 'a';
        };

        UIManager.destroyer.addActionListener(shipChoice);
        UIManager.cruiser.addActionListener(shipChoice);
        UIManager.submarine.addActionListener(shipChoice);
        UIManager.battleship.addActionListener(shipChoice);
        UIManager.aircraft.addActionListener(shipChoice);

        UIManager.rotate.addActionListener(e -> {
            orientation = (orientation + 1) % 2;
            adjustShipButtonOrientation();
        });

        UIManager.shipRemove.addActionListener(e -> {
            orientation = 0;
            resetShipButtons();
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    UIManager.fleet[i][j].setBackground(Color.BLUE);
                }
            }
        });

        ActionListener placeShip = e -> {
            UIManager.announce2.setText(null);
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    if (e.getSource() == UIManager.fleet[i][j]) {
                        tryPlaceShipAt(j, i);
                    }
                }
            }
        };

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                UIManager.fleet[i][j].addActionListener(placeShip);
            }
        }
    }

    private static void adjustShipButtonOrientation() {
        if (ship == 'd') UIManager.destroyer.setBounds(1200, 40, orientation == 0 ? 84 : 42, orientation == 0 ? 42 : 84);
        if (ship == 'c') UIManager.cruiser.setBounds(1340, 140, orientation == 0 ? 126 : 42, orientation == 0 ? 42 : 126);
        if (ship == 's') UIManager.submarine.setBounds(1200, 140, orientation == 0 ? 126 : 42, orientation == 0 ? 42 : 126);
        if (ship == 'b') UIManager.battleship.setBounds(1200, 285, orientation == 0 ? 168 : 42, orientation == 0 ? 42 : 168);
        if (ship == 'a') UIManager.aircraft.setBounds(1200, 472, orientation == 0 ? 210 : 42, orientation == 0 ? 42 : 210);
    }

    private static void resetShipButtons() {
        UIManager.destroyer.setVisible(true);
        UIManager.cruiser.setVisible(true);
        UIManager.submarine.setVisible(true);
        UIManager.battleship.setVisible(true);
        UIManager.aircraft.setVisible(true);

        UIManager.destroyer.setBounds(1200, 40, 84, 42);
        UIManager.cruiser.setBounds(1340, 140, 126, 42);
        UIManager.submarine.setBounds(1200, 140, 126, 42);
        UIManager.battleship.setBounds(1200, 285, 168, 42);
        UIManager.aircraft.setBounds(1200, 472, 210, 42);
    }

    private static void tryPlaceShipAt(int x, int y) {
        JButton[][] fleet = UIManager.fleet;
        JLabel announce = UIManager.announce;

        int len = 0;
        JButton shipButton = null;

        switch (ship) {
            case 'd': len = 2; shipButton = UIManager.destroyer; break;
            case 'c': case 's': len = 3; shipButton = ship == 'c' ? UIManager.cruiser : UIManager.submarine; break;
            case 'b': len = 4; shipButton = UIManager.battleship; break;
            case 'a': len = 5; shipButton = UIManager.aircraft; break;
            default: return;
        }

        try {
            if (orientation == 0) {
                // Horizontal
                for (int i = 0; i < len; i++) {
                    if (!fleet[y][x + i].getBackground().equals(Color.BLUE)) return;
                }
                if (!Utils.checkPossible(fleet, x, y, len, 1)) return;

                for (int i = 0; i < len; i++) fleet[y][x + i].setBackground(Color.DARK_GRAY);
            } else {
                // Vertical
                for (int i = 0; i < len; i++) {
                    if (!fleet[y + i][x].getBackground().equals(Color.BLUE)) return;
                }
                if (!Utils.checkPossible(fleet, x, y, 1, len)) return;

                for (int i = 0; i < len; i++) fleet[y + i][x].setBackground(Color.DARK_GRAY);
            }

            if (shipButton != null) shipButton.setVisible(false);
            announce.setText(null);
            announce.setForeground(Color.GREEN);
            ship = 'p'; // Mark as placed

        } catch (Exception e) {
            announce.setForeground(Color.RED);
            announce.setText("Out of bounds!");
        }
    }
}
