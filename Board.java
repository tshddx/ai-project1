import java.util.*;

public class Board {
	private int boardSize;
	private char board[][];
    private Board parent;
    private int protagRow;
    private int depth;


	// For now
	private static char testBoard[][] =
	    { { '.', '.', '.', '.', 'O', 'O', 'O'},
          { '.', '.', '.', '.', 'A', 'B', 'C'},
          { 'G', '.', 'X', 'X', 'A', 'B', 'C'},
          { 'G', '.', '.', '.', 'P', 'P', 'P'},
          { '.', 'K', 'K', '.', '.', '.', '.'},
          { '.', 'K', 'K', '.', '.', '.', '.'},
          { '.', '.', '.', '.', '.', '.', '.'} };
	private static char testBoard2[][] =
	    { { '.', '.', '.', '.', '.', '.', '.'},
          { '.', '.', '.', '.', '.', '.', '.'},
          { '.', '.', '.', '.', '.', '.', '.'},
          { '.', '.', '.', '.', '.', '.', '.'},
          { '.', 'K', 'K', '.', '.', '.', '.'},
          { '.', 'K', 'K', '.', '.', '.', '.'},
          { '.', '.', '.', '.', '.', '.', '.'} };
	
	
	public Board(int boardSize, char[][] board) {
		this.boardSize = boardSize;
		this.board = board;
        this.parent = null;
        this.depth = 0;

        // Find the row that the protagonist vehicle is on
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 'X') {
                    this.protagRow = i;
                    return;
                }
            }
        }
		// call validMoves
	}

	public Board(Board other, boolean parent) {
        if (parent) {
            this.boardSize = other.size();
            this.board = other.getBoardCopy();
            this.parent = other;
            this.depth = other.depth + 1;
            this.protagRow = other.protagRow;
        } else {
            this.boardSize = other.size();
            this.board = other.getBoardCopy();
            this.parent = other.parent;
            this.depth = other.depth;
            this.protagRow = other.protagRow;
        }
	}

	// returns a new copy of this game board as an array of character arrays
	private char[][] getBoardCopy(){
		char[][] boardCopy = new char[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				boardCopy[i][j] = this.board[i][j];
			}
		}
		return boardCopy;
	}

    /**
     * Return the size of this board
     */
    public int size() {
        return boardSize;
    }

    private boolean isBoardBlank() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] != '.')
                    return false;
            }
        }

        return true;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Board))
            return false;

        Board other = (Board)o;
        if (other.toString().equals(this.toString()))
            return true;
        else
            return false;
    }

    /**
     * Solves this board, and returns a list of the solution boards
     * in order
     */
    public List<Board> solve(Proj1RushHour prh) {
        int maxdepth = 0;
        int totalstates = 0;

        // Find the special case blank board.
        if (isBoardBlank())
            return new ArrayList<Board>();
        
        // Run BFS
        Queue<Board> ready = new LinkedList<Board>();
        HashSet<Board> markedBoards = new HashSet<Board>();
        ready.offer(this);
        markedBoards.add(this);

        Board currentBoard;
        for(;;) {
            currentBoard = ready.poll();
            //System.out.println(currentBoard);
            
            if (currentBoard.depth > maxdepth)
                maxdepth = currentBoard.depth;

            if (currentBoard.solved())
                break;

            // TODO: push moves into queue directly?
            currentBoard.validMoves(ready, markedBoards);

        }

        //TODO: set tree depth
        totalstates = markedBoards.size();
        prh.updateInfo(totalstates, maxdepth);
        
        // Trace back up through tree and generate the solution
        List<Board> solution = new ArrayList<Board>();
        while (currentBoard.parent != null) {
            solution.add(currentBoard);
            currentBoard = currentBoard.parent;
        }

        solution.add(currentBoard);

        Collections.reverse(solution);
        System.out.println("#####################");
        for (Board b : solution) {
            System.out.println(b);
        }
        return solution;
    }

    /**
     * Converts to a string.
     *
     * @return String representation of this board
     */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				s.append(board[i][j]);
			}
			s.append('\n');
		}
		return s.toString();
	}

    /**
     * Returns true if this baord represents a 'solved' state.
     *
     * @return true if solved, false otherwise
     */
    public boolean solved() {
        if (board[protagRow][boardSize - 1] == 'X')
            return true;
        else 
            return false;
    }

	/**
     * Return a character array representing a certain column, zero indexed.
     */
	public char[] getColumn(int col) {
	if (col > boardSize - 1) {
		// exception
	}
	return this.board[col];
	}
	
	/**
     * Return a character array representing a certain row, zero indexed.
     *
     * @param row row to return
     */
	public char[] getRow(int row) {
	if (row > boardSize - 1) {
		// exception
	}
	char[] r = new char[boardSize];
	for (int i = 0; i < boardSize; i++) {
		r[i] = board[i][row];
	}
	return r;
	}

    /**
     * Return the piece at position x, y
     */
	public char get(int x, int y) {
        return board[y][x];
    }
	
    /**
     * Given a row (or column) as a character array, return a character array of the pieces in it.
     */
	public char[] getPieces(char[] row) {
		HashSet<Character> pieces = new HashSet<Character>();
		for (int i = 0; i < boardSize; i++) {
			if (row[i] != '.' && !pieces.contains(row[i])) {
				pieces.add(new Character(row[i]));
			}
		}
		char[] p = new char[pieces.size()];
		Iterator iter = pieces.iterator();
		int i = 0;
		while (iter.hasNext()) {
			// p[i] = (char) iter.next();
			System.out.println(iter.next());
		}
		return p;
	}	


    /**
     * Adds to q Board objects reachable from the current Board in a single move.
     * If the board is 'invalid', it will not be added. When a board is added
     * to q, it will be added to invalid.
     */
	public void validMoves(Queue<Board> q, HashSet<Board> invalid) {
		validMoves(q, invalid, false);
		validMoves(q, invalid, true);
	}
	 
    /**
     * Adds all Board objects reachable from the 
     * current Board in a single move to q, for rows or columns only.
     * If the board is 'invalid', it will not be added. When a board is added
     * to q, it will be added to invalid.
     */
	public void validMoves(Queue<Board> q, 
            HashSet<Board> invalid, boolean column) {
	    // Iterate through rows
	    for (int i = 0; i < boardSize; i++) {
            int emptyBefore = 0, emptyAfter = 0;
	        int pieceLength = 0, pieceStart = 0;
            char piece = ' ', lastPiece = ' ';

	        for (int j = 0; j < boardSize; j++) {
                if (column)
                    piece = board[j][i];
                else
                    piece = board[i][j];

                if (piece == '.') {
                    emptyAfter++;
                } else if (lastPiece != piece) {
                    // We switched pieces, write out the possibilities
                    if (lastPiece != ' ' && pieceLength > 1) { 
                        addGameStates(q, invalid, lastPiece, 
                                pieceStart, pieceLength, 
                                emptyBefore, emptyAfter, i, column);
                    }

                    lastPiece = piece;
                    pieceStart = j;
                    pieceLength = 1;

                    // Start a new empty run
                    emptyBefore = emptyAfter;
                    emptyAfter = 0;
                } else { // repeated piece
                    pieceLength++;
                }
            }

            // Add the final piece in the row
            if (lastPiece != ' ' && pieceLength > 1) { 
                addGameStates(q, invalid, lastPiece, 
                        pieceStart, pieceLength, 
                        emptyBefore, emptyAfter, i, column);
            }
        }
    }
	
    private boolean isSquareStart(char piece, int k, int rowcol, boolean column) {
        if (rowcol == boardSize - 1)
            return false;

        if ((column && board[k][rowcol] == board[k][rowcol + 1]) || 
            (!column && board[rowcol][k] == board[rowcol + 1][k])) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSquareEnd(char piece, int k, int rowcol, boolean column) {
        if (rowcol == 0)
            return false;

        if ((column && board[k][rowcol] == board[k][rowcol - 1]) || 
            (!column && board[rowcol][k] == board[rowcol - 1][k])) {
            return true;
        } else {
            return false;
        }
    }

	private void addGameState(Queue<Board> q, 
            HashSet<Board> invalid, char piece, 
            int offset, int length, int rowcol, boolean column,
            boolean square) {

        for (int j = 0; j < length; j++) {
            int w;
            if (square)
                w = 2;
            else
                w = 1;

            for (int k = 0; k < w; k++) {
                if (column)
                    board[offset + j][rowcol + k] = piece;
                else
                    board[rowcol + k][offset + j] = piece;
            }
        }

        if (!invalid.contains(this)) {
            q.offer(this);
            invalid.add(this);
        }
    }

	private void addGameStates(Queue<Board> q, 
            HashSet<Board> invalid, char piece, 
            int pieceStart, int pieceLength, int emptyBefore, 
            int emptyAfter, int rowcol, boolean column) {

        // If this is the second half of a square, we've already processed it
        if (isSquareEnd(piece, pieceStart, rowcol, column))
            return;

        /*
        System.out.print("Row/Col " + rowcol + 
                ": movable piece '" + piece + 
                "' with " + emptyBefore + 
                " blanks before and " + 
                emptyAfter + " blanks after.");
                */

        boolean square = isSquareStart(piece, pieceStart, rowcol, column);

        Board baseboard = new Board(this, true);
		// remove the piece from the row
		for (int i = 0; i < pieceLength; i++) {
            int w;
            if (square)
                w = 2;
            else
                w = 1;

            for (int k = 0; k < w; k++) {
                if (column)
                    baseboard.board[pieceStart + i][rowcol + k] = '.';
                else
                    baseboard.board[rowcol + k][pieceStart + i] = '.';
            }
		}

		// generate the moves from moving the piece to the left(up)
        for (int i = 0; i < emptyBefore; i++) {
			// put the piece back in
            int offset = pieceStart - i - 1;

            // if the other side of the square is blocked,
            // disallow this move and all future ones in this 
            // direction
            if (square) {
                char blockPiece;
                if (column)
                    blockPiece = board[offset][rowcol + 1];
                else
                    blockPiece = board[rowcol + 1][offset];

                if (blockPiece != '.' && blockPiece != piece)
                    break;
            }

            // Create the new board
            Board b = new Board(baseboard, false);
            b.addGameState(q, invalid, piece, offset, 
                    pieceLength, rowcol, column, square);
		}

		// generate the moves from moving the piece to the right(down)
        for (int i = 0; i < emptyAfter; i++) {
			// put the piece back in
            int offset = pieceStart + i + 1;

            // if the other side of the square is blocked,
            // disallow this move and all future ones in this 
            // direction
            if (square) {
                if (offset + pieceLength > boardSize) // off the edge
                    break;

                char blockPiece;
                if (column)
                    blockPiece = board[offset + pieceLength - 1][rowcol + 1];
                else
                    blockPiece = board[rowcol + 1][offset + pieceLength - 1];

                if (blockPiece != '.' && blockPiece != piece) // blocked
                    break;
            }

            // Create the new board
            Board b = new Board(baseboard, false);
            b.addGameState(q, invalid, piece, offset, 
                    pieceLength, rowcol, column, square);
        }
    }
	
	public static void main(String args[]) {
		Board b = new Board(7, testBoard);
		System.out.println(b);
		Queue<Board> tonsOfMoves = new LinkedList<Board>();
        HashSet<Board> invalid = new HashSet<Board>();
        b.validMoves(tonsOfMoves, invalid);
		System.out.println("\n\nSTARTING BOARD\n");
		System.out.println(b);
		System.out.println("\nPOSSIBLE MOVES\n");
		for (Board m : tonsOfMoves) {
			System.out.println();
			System.out.println(m);
		}
	}
}
