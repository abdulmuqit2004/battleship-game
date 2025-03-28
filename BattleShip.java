package w25-csci2020u-finalproject-w25-team11;

public class BattleShip {
    public static void main(String[] args) {
        UIManager.initializeUI();
        ShipPlacementManager.setupShipPlacement();
        GameLogic.setupGameStart();
    }
}
