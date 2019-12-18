package com.arainko.xno.controller.gamestates.gamerunningstate;

import com.arainko.xno.controller.gamestates.interfaces.InternalGameState;
import com.arainko.xno.helpers.Cords;
import com.arainko.xno.model.board.ModelBoard;
import com.arainko.xno.model.elements.Cell;
import com.arainko.xno.model.elements.Connection;
import com.arainko.xno.view.ViewBoard;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

import static com.arainko.xno.model.predicates.ConnectionPredicates.empty;

public class ConnectionBuilder implements InternalGameState {

    private GameRunningState parentGameState;
    private ModelBoard modelBoard;
    private ViewBoard viewBoard;

    List<Cords> lastClickedNeighbors;
    Connection connection;

    public ConnectionBuilder(GameRunningState parentGameState) {
        this.parentGameState = parentGameState;
        this.modelBoard = parentGameState.getGameController().getModelBoard();
        this.viewBoard = parentGameState.getGameController().getViewBoard();
        connection = new Connection();
        lastClickedNeighbors = new ArrayList<>();
    }

    @Override
    public void onInternalGameStateClickHandler(Button button) {
        Cords clickedCords = viewBoard.getButtonCords(button);
        Cell clickedCell = modelBoard.getCellAt(clickedCords);

        // Handle initial state entry
        if (connection.isConnection(empty())) {
            onStateInitialEntryHandler(button, clickedCell, clickedCords);
        } else {
            onStateContinuousEntryHandler(button, clickedCell, clickedCords);
        }
    }

    private void onStateInitialEntryHandler(Button clickedButton, Cell clickedCell, Cords clickedCords) {
        connection.addConnectionUnit(clickedCell);
        clickedButton.setId("clicked-button");
        lastClickedNeighbors = modelBoard.getFreeNeighborsAt(clickedCords);
        litUpNeighborButtonColors(lastClickedNeighbors, "neighbor-button");
    }

    private void onStateContinuousEntryHandler(Button button, Cell clickedCell, Cords clickedCords) {
        if (lastClickedNeighbors.contains(clickedCords)) {
            connection.addConnectionUnit(clickedCell);
            litUpNeighborButtonColors(lastClickedNeighbors, "custom-button");
            lastClickedNeighbors = modelBoard.getFreeNeighborsAt(clickedCords);
            button.setId("clicked-button");
            litUpNeighborButtonColors(lastClickedNeighbors, "neighbor-button");
        }
    }

    private void litUpNeighborButtonColors(List<Cords> neighborCords, String buttonStyleId) {
        neighborCords.forEach( cord -> viewBoard.getButtonAt(cord).setId(buttonStyleId));
    }

}