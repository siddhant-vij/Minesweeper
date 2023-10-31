## [Minesweeper Game](https://codegym.cc/projects/games/com.codegym.games.minesweeper)

### Rules:
- The playing field is divided into adjacent cells (squares), some of which contain "mines".
- The objective of the game is to clear the field, i.e. reveal all cells that don't contain mines.
- If a cell with a mine is revealed, the game is lost. The mines are placed randomly.
- If a revealed cell doesn't have a mine, a number appears in it, indicating how many "mined" cells are adjacent to the revealed cell. You can use these numbers to calculate the location of mines.
- If the adjacent cells also do not contain mines, an "unmined" region is revealed up to cells that do have numbers.
- Cells that you believe to contain mines can be flagged so you don't accidentally reveal them. The number of flags is equal to the number of mines on the game board.
- If "unmined" cells are revealed, then the game is won.

<br>

### Future Tasks:
- Limit the number of moves.
- Make it so the first move never hits a mine.
- Make the game easier or harder by changing the number of mines on the field.
- Add visual effects (animation).