/**
 * Created by Peter on 8/18/2016.
 */
///// Add to the gameboard a list of the redUnits and blueUnits

abstract class MilitaryUnit {
    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public int getYTile() {
        return yTile;
    }

    public void setYTile(int yTile) {
        this.yTile = yTile;
    }

    public int getXTile() {
        return xTile;
    }

    public void setXTile(int xTile) {
        this.xTile = xTile;
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isMovedThisTurn() {
        return movedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.movedThisTurn = movedThisTurn;
    }

    public boolean isAttackedThisTurn() {
        return attackedThisTurn;
    }

    public void setAttackedThisTurn(boolean attackedThisTurn) {
        this.attackedThisTurn = attackedThisTurn;
    }

    public GameBoard.MilitaryUnitType getMilitaryUnitType() {

        switch(this.getClass().getName()) {
            case "Infantry":
                return GameBoard.MilitaryUnitType.INFANTRY;
            case "Mech":
                return GameBoard.MilitaryUnitType.MECH;
            case "Artillery":
                return GameBoard.MilitaryUnitType.ARTILLERY;
            case "Tank":
                return GameBoard.MilitaryUnitType.TANK;
            default:
                return null;

        }

    } // END OF getMilitaryUnitType GETTER

    private char color;
    private int xTile;
    private int yTile;
    private boolean selected;
    private boolean movedThisTurn = false;
    private boolean attackedThisTurn = false;

    MilitaryUnit(char color, int xTile, int yTile, boolean selected) {
        this.color = color;
        this.xTile = xTile;
        this.yTile = yTile;
        this.selected = selected;
    }

}

class Infantry extends MilitaryUnit {

    Infantry(char color, int xTile, int yTile, boolean selected) {
        super(color, xTile, yTile, selected);
    }

}

class Mech extends MilitaryUnit {

    Mech(char color, int xTile, int yTile, boolean selected) {
        super(color, xTile, yTile, selected);
    }

}

class Artillery extends MilitaryUnit {

    Artillery(char color, int xTile, int yTile, boolean selected) {
        super(color, xTile, yTile, selected);
    }

}

class Tank extends MilitaryUnit {
    private char color;

    Tank(char color, int xTile, int yTile, boolean selected) {
        super(color, xTile, yTile, selected);
    }

}