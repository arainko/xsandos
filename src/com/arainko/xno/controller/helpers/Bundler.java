package com.arainko.xno.controller.helpers;

import com.arainko.xno.controller.game.GameController;
import com.arainko.xno.model.board.ModelBoard;
import com.arainko.xno.view.board.ViewBoard;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.arainko.xno.controller.helpers.MoveKeeper.Move;

/* Bundler is responsible for saving/loading of the current game state to/from file.
* To store the game state it uses a Bundle which is an object
* that consists of a ModelBoard and MoveKeeper's move history and current history index.
* Current game state and history can then be easily rebuilt using only these 3 objects.
*
* Save files are saved and loaded from '.xnosaves' directory in user's home directory. */

public class Bundler {
    public static class Bundle implements Serializable {
        private ModelBoard modelBoard;
        private List<Move> moveHistory;
        private int currentHistoryIndex;

        public Bundle(GameController gameController) {
            this.modelBoard = gameController.getModelBoard();
            this.moveHistory = gameController.getMoveKeeper().getKeptMoves();
            this.currentHistoryIndex = gameController.getMoveKeeper().getCurrentIndex();
        }

        public ModelBoard getBundledModelBoard() {
            return modelBoard;
        }

        public ViewBoard getBundledViewBoard() {
            return Boards.rebuildBoard(modelBoard);
        }

        public MoveKeeper getBundledMoveKeeper(ModelBoard modelBoard, ViewBoard viewBoard) {
            return new MoveKeeper(modelBoard, viewBoard, moveHistory, currentHistoryIndex);
        }
    }
    private GameController gameController;
    private String saveFileDirPath;

    public Bundler(GameController gameController) {
        this.gameController = gameController;
        saveFileDirPath = System.getProperty("user.home")+"/.xnosaves";
        new File(saveFileDirPath).mkdir();
    }

    public void saveBundle() {
        LocalDateTime now = LocalDateTime.now();
        String filename = "/" + DateTimeFormatter.ofPattern("dd-MM-yyyy-HHmmss").format(now) + ".xno";
        Bundle bundle = new Bundle(gameController);
        try {
            FileOutputStream fos = new FileOutputStream(saveFileDirPath + filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bundle);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBundle(Bundle bundle) {
        ModelBoard modelBoard = bundle.getBundledModelBoard();
        ViewBoard viewBoard = bundle.getBundledViewBoard();
        MoveKeeper moveKeeper = bundle.getBundledMoveKeeper(modelBoard, viewBoard);
        gameController.setModelBoard(modelBoard);
        gameController.setViewBoard(viewBoard);
        gameController.setMoveKeeper(moveKeeper);
    }

}
