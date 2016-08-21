import java.util.Iterator;

public class FindRightClickGameStateChanges {

    GameBoard gameBoard;
    int xClicked;
    int yClicked;
    int cursorXTile;
    int cursorYTile;
    int clickedXTile;
    int clickedYTile;
    boolean isAMeleeMilitaryUnitSelected;
    boolean isARangedMilitaryUnitSelected;
    MilitaryUnit selectedMilitaryUnit = null;
    MilitaryUnit clickedMilitaryUnit = null;


    FindRightClickGameStateChanges(GameBoard gameBoard, int xClicked, int yClicked) {

        this.gameBoard = gameBoard;

        // pull snapshots of variables

        this.xClicked = xClicked;
        this.yClicked = yClicked;
        clickedXTile = gameBoard.findXTileClickedOn();
        clickedYTile = gameBoard.findYTileClickedOn();
        cursorXTile = gameBoard.getCursorMapTileX();
        cursorYTile = gameBoard.getCursorMapTileY();
        this.isAMeleeMilitaryUnitSelected = gameBoard.isAMeleeMilitaryUnitSelected();
        this.isARangedMilitaryUnitSelected = gameBoard.isARangedMilitaryUnitSelected();
        this.selectedMilitaryUnit = gameBoard.getSelectedMilitaryUnit();  // this can be null
        this.clickedMilitaryUnit = gameBoard.getMilitaryUnitAtXYTile(clickedXTile, clickedYTile);  // This can be null

        // assign a value to clickedMilitaryUnit:


        //gameBoard.unit



        //this.clickedMilitaryUnit =






        // leave early if no units are selected

        if (selectedMilitaryUnit == null) { return; }

        // if the unit can still move this turn:
        // check if the player clicked on a tile that is true in the movable grid while a unit is selected (black tiles)

        if (!selectedMilitaryUnit.isMovedThisTurn()) {
            militaryUnitMovementCommand();
        }

        // if the unit can still attack this turn:
        // check if the player clicked on an enemy unit inside the attack grid and make sure a unti is currently under the cursor

        if (!selectedMilitaryUnit.isAttackedThisTurn()) {
            militaryUnitAttackCommand();
        }

    } // END OF CONSTRUCTOR

    /*
    check if the player clicked on a tile that is true in the movable grid while a unit is selected (black tiles)
     */

    void militaryUnitMovementCommand() {

        boolean[][] tempCurrentMoveableChoices = gameBoard.cloneCurrentMoveableChoicesGrid();
        int defenseStars = 0;

        // if the selected tile is currently movable

        if (tempCurrentMoveableChoices[clickedYTile][clickedXTile]) {

            selectedMilitaryUnit.setXTile(clickedXTile);
            selectedMilitaryUnit.setYTile(clickedYTile);
            defenseStars = gameBoard.defenseStarsAtXYTile(clickedXTile, clickedYTile);
            selectedMilitaryUnit.setDefenseStars(defenseStars);
            selectedMilitaryUnit.setMovedThisTurn(true);
            gameBoard.setCursorMapTileX(clickedXTile);
            gameBoard.setCursorMapTileY(clickedYTile);
            gameBoard.setBuyingFromBasePossible(false);

            // If artillery moves then it can't attack the same turn

            if (selectedMilitaryUnit.getMilitaryUnitType() == GameBoard.MilitaryUnitType.ARTILLERY) {
                selectedMilitaryUnit.setAttackedThisTurn(true);
            }

            // These don't need to be updated because paint is turned off and move is turned off.  The attack squares
            // already seem updated
              //gameBoard.updateCurrentMoveableChoicesGrid();
              //gameBoard.updateAttackSquares();

        }

    } // END OF militaryUnitMovementCommand METHOD

    /*
    check if the player clicked on an enemy unit inside the attack grid and make sure a unti is currently under the cursor
     */

    void militaryUnitAttackCommand() {

        if (clickedMilitaryUnit == null) { return; } // we return if no unit was clicked on
        if (clickedMilitaryUnit.getColor() == gameBoard.getTurnColor()) { return; } // we return if the unit isn't another team color

        boolean[][] tempCurrentAttackChoices = gameBoard.cloneCurrentAttackChoicesGrid();

        if (tempCurrentAttackChoices[clickedYTile][clickedXTile]) {

            selectedMilitaryUnit.setMovedThisTurn(true);
            selectedMilitaryUnit.setAttackedThisTurn(true);
            selectedMilitaryUnit.attack(clickedMilitaryUnit);

            checkForDestroyedUnits();

        }

    }

    /*
    Since units can't access GameBoard methods, the health of all units is checked here so they can be removed from the gameboard
     */

    void checkForDestroyedUnits() {

        MilitaryUnit currentMilitaryUnit = null;

        Iterator<MilitaryUnit> tempMilitaryUnitsIterator = gameBoard.militaryUnitsIterator();
        while (tempMilitaryUnitsIterator.hasNext()) {
            currentMilitaryUnit = tempMilitaryUnitsIterator.next();
            if (currentMilitaryUnit.getHealth() < 0) {
                gameBoard.removeMilitaryUnits(currentMilitaryUnit);
            }

        }

    } // END OF checkForDestroyedUnits METHOD


} // END OF FindRightClickGameStateChanges CLASS