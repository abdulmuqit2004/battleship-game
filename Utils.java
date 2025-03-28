package finalassignment;

import java.awt.Color;
import javax.swing.JButton;

public class Utils {

    public static boolean checkHit(int coordinate) {
        return coordinate == 1;
    }

    public static int checkWin(char board, JButton[][] fleet, JButton[][] attack) {
        int hits = 0;

        if (board == 'f') {
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    if (fleet[i][j].getBackground().equals(Color.RED)) {
                        hits++;
                    }
                }
            }
        } else if (board == 'a') {
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    if (attack[i][j].getBackground().equals(Color.RED)) {
                        hits++;
                    }
                }
            }
        }

        return hits == 17 ? 1 : 0;
    }

    public static boolean checkPossible(JButton[][] fleet, int x, int y, int length, int width) {
        for (int i = y - 1; i < y + width + 1; i++) {
            for (int j = x - 1; j < x + length + 1; j++) {
                try {
                    if (fleet[i][j].getBackground().equals(Color.DARK_GRAY)) {
                        return false;
                    }
                } catch (Exception e) {
                    // Ignore out-of-bounds
                }
            }
        }
        return true;
    }

    public static boolean compCheckPossible(int[][] attacked, int x, int y, int length, int width) {
        for (int i = y - width - 1; i < y + 1; i++) {
            for (int j = x - length - 1; j < x + 1; j++) {
                try {
                    if (attacked[i][j] == 1) {
                        return false;
                    }
                } catch (Exception e) {
                    // Ignore out-of-bounds
                }
            }
        }
        return true;
    }
}
