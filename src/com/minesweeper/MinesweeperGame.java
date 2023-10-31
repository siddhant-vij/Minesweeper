package com.minesweeper;

import java.util.ArrayList;
import java.util.List;

import com.codegym.engine.cell.*;

public class MinesweeperGame extends Game {
  private static final int SIDE = 9;
  private GameObject[][] gameField = new GameObject[SIDE][SIDE];
  private int countMinesOnField;
  private static final String MINE = "\uD83D\uDCA3";
  private static final String FLAG = "\uD83D\uDD25";
  private int countFlags;
  private boolean isGameStopped;
  private int countClosedTiles = SIDE * SIDE;
  private int score;

  public void initialize() {
    setScreenSize(SIDE, SIDE);
    createGame();
  }

  private void createGame() {
    for (int row = 0; row < SIDE; row++) {
      for (int col = 0; col < SIDE; col++) {
        int randNumber = getRandomNumber(10);
        boolean isMine = randNumber < 1;
        if (isMine) {
          countMinesOnField++;
        }
        gameField[row][col] = new GameObject(row, col, isMine);
        setCellColor(row, col, Color.ORANGE);
        setCellValue(row, col, "");
      }
    }
    countMineNeighbors();
    countFlags = countMinesOnField;
  }

  private List<GameObject> getNeighbors(GameObject gameObject) {
    List<GameObject> neighbors = new ArrayList<>();
    for (int row = gameObject.x - 1; row <= gameObject.x + 1; row++) {
      for (int col = gameObject.y - 1; col <= gameObject.y + 1; col++) {
        if (col < 0 || col >= SIDE || row < 0 || row >= SIDE) {
          continue;
        }
        if (row == gameObject.x && col == gameObject.y) {
          continue;
        }
        neighbors.add(gameField[row][col]);
      }
    }
    return neighbors;
  }

  private void countMineNeighbors() {
    for (int row = 0; row < SIDE; row++) {
      for (int col = 0; col < SIDE; col++) {
        GameObject gameObject = gameField[row][col];
        if (!gameObject.isMine) {
          int countMineNeighbors = 0;
          List<GameObject> neighbors = getNeighbors(gameObject);
          for (GameObject neighbor : neighbors) {
            if (neighbor.isMine) {
              countMineNeighbors++;
            }
          }
          gameObject.countMineNeighbors = countMineNeighbors;
        }
      }
    }
  }

  private void openTile(int row, int col) {
    GameObject gameObject = gameField[row][col];
    if (gameObject.isOpen || isGameStopped || gameObject.isFlag) {
      return;
    }
    gameObject.isOpen = true;
    setCellColor(row, col, Color.GREEN);
    countClosedTiles--;
    if (gameObject.isMine) {
      setCellValueEx(gameObject.x, gameObject.y, Color.RED, MINE);
      gameOver();
      return;
    } else if (gameObject.countMineNeighbors == 0) {
      setCellValue(gameObject.x, gameObject.y, "");
      List<GameObject> neighbors = getNeighbors(gameObject);
      for (GameObject neighbor : neighbors) {
        if (!neighbor.isOpen) {
          openTile(neighbor.x, neighbor.y);
        }
      }
    } else {
      setCellNumber(row, col, gameObject.countMineNeighbors);
    }
    score += 5;
    setScore(score);
    if (countClosedTiles == countMinesOnField) {
      win();
    }
  }

  @Override
  public void onMouseLeftClick(int row, int col) {
    if (isGameStopped) {
      restart();
      return;
    }
    openTile(row, col);
  }

  private void markTile(int row, int col) {
    GameObject gameObject = gameField[row][col];
    if (gameObject.isOpen || (countFlags == 0 && !gameObject.isFlag)) {
      return;
    }
    if (gameObject.isFlag) {
      setCellValue(row, col, "");
      setCellColor(row, col, Color.ORANGE);
      countFlags++;
      gameObject.isFlag = false;
    } else {
      setCellValue(row, col, FLAG);
      setCellColor(row, col, Color.YELLOW);
      countFlags--;
      gameObject.isFlag = true;
    }
  }

  @Override
  public void onMouseRightClick(int row, int col) {
    markTile(row, col);
  }

  private void gameOver() {
    isGameStopped = true;
    showMessageDialog(Color.RED, "Game Over", Color.BLACK, 50);
  }

  private void win() {
    isGameStopped = true;
    showMessageDialog(Color.GREEN, "You Win", Color.BLACK, 50);
  }

  private void restart() {
    isGameStopped = false;
    countMinesOnField = 0;
    countClosedTiles = SIDE * SIDE;
    score = 0;
    createGame();
    setScore(score);
  }
}
