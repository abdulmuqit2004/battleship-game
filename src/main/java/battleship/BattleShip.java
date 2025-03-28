package battleship;

public class BattleShip {
    public static void main(String[] args) {
        UIManager.initializeUI();
        ShipPlacementManager.setupShipPlacement();
        GameLogic.setupGameStart();
    }
}
