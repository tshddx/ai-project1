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
          { '.', '.', '.', '.', '.', '.', '.'},
          { '.', '.', '.', '.', '.', '.', '.'},
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

	public Board(Board parent, char[][] board) {
		this.boardSize = parent.size();
		this.board = board;
        this.parent = parent;
        this.depth = parent.depth + 1;
        this.protagRow = parent.protagRow;
		// call validMoves
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

    /**
     * Solves this board, and returns a list of the solution boards
     * in order
     */
    public List<Board> solve() {
        int maxdepth = 0;
        int totalstates = 0;

        // Find the special case blank board.
        if (isBoardBlank())
            return new ArrayList<Board>();
        
        // Run BFS
        Queue<Board> ready = new LinkedList<Board>();
        HashSet<String> markedBoards = new HashSet<String>();
        ready.offer(this);
        markedBoards.add(this.toString());
        Board currentBoard;
        for(;;) {
            currentBoard = ready.poll();
            System.out.println(currentBoard);

            // TODO: pre-find row to check?
            if (currentBoard.solved())
                break;

            // TODO: push moves into queue directly?
            List<Board> moves = currentBoard.validMoves();
            for (Board m : moves) {
                if (!markedBoards.contains(m.toString())) {
                    markedBoards.add(m.toString());
                    ready.offer(m);
                } else {
                }
            }

            //if (markedBoards.size() > totalstates + 1000) {
            //    totalstates = markedBoards.size();
            //    Proj1RushHour.updateInfo(totalstates, maxdepth);
            //}
            
        }

        //TODO: set tree depth
        totalstates = markedBoards.size();
        
        System.out.println("#####################");
        // Trace back up through tree and generate the solution
        List<Board> solution = new ArrayList<Board>();
        while (currentBoard.parent != null) {
            solution.add(currentBoard);
            currentBoard = currentBoard.parent;
        }

        Collections.reverse(solution);
        System.out.println(solution.size());
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
     * Return an array of all Board objects reachable from the current Board in a single move.
     */
	public List<Board> validMoves() {
		ArrayList<Board> m = validMoves(false);
		m.addAll(validMoves(true));
		return m;
	}
	 
    /**
     * Return an array of all Board objects reachable from the 
     * current Board in a single move, for rows or columns only.
     */
	public ArrayList<Board> validMoves(boolean column) {
        ArrayList<Board> moves = new ArrayList<Board>();
	    // Iterate through rows
		if (column) {
			this.rotateLeft();
		}
	    for (int i = 0; i < boardSize; i++) {
            int emptyBefore = 0;
			int emptyAfter = 0;
	        int pieceLength = 0;
			int pieceStart = 0;
            char piece = ' ';
            // char lastChar = ' ';
            boolean hadPiece = false;
	        for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == '.') {
                    if (piece != ' ') {
                        // Done with a piece
                        if (hadPiece) {
                            // Start after spacing
                            emptyAfter++;
                            if (j == boardSize - 1 && pieceLength > 1) {
								//System.out.println("Row " + i + ": movable piece '" + piece + "' with " + emptyBefore + " blanks before and " + emptyAfter + " blanks after.");
                                addGameStates(moves, piece, pieceStart, pieceLength, emptyBefore, emptyAfter, i, column);
                            }
                        }
                        else {
                            // Never had a peice, only seen vertical pieces
                            emptyBefore++;
                        }
                    }
                    else {
                        if (hadPiece) {
                            // between pieces
                            emptyAfter++;
                        }
                        else {
                            // Still blank from beginning
                            emptyBefore++;
                        }
                    }
                }
                else {
                    // on piece square
                    if (board[i][j] == piece) {
                        // is a continued piece
                        // TODO: check for square or regular
                        hadPiece = true;
                        pieceLength++;
                        if (j == boardSize - 1 && pieceLength > 1) {
							//System.out.println("Row " + i + ": movable piece '" + piece + "' with " + emptyBefore + " blanks before and " + emptyAfter + " blanks after.");
                            addGameStates(moves, piece, pieceStart, pieceLength, emptyBefore, emptyAfter, i, column);
                        }
                    }
                    else {
                        // found new piece
                        if (hadPiece && pieceLength > 1) {
                            // add moves for last piece (using empty after spaces)
							//System.out.println("Row " + i + ": movable piece '" + piece + "' with " + emptyBefore + " blanks before and " + emptyAfter + " blanks after.");
                            addGameStates(moves, piece, pieceStart, pieceLength, emptyBefore, emptyAfter, i, column);
                            emptyBefore = emptyAfter;
							emptyAfter = 0;
                        }
                        else {
                            // Never had a piece, found first piece, doesn't mean anything
                        }
                        piece = board[i][j];
                        pieceLength = 1;
                        pieceStart = j;
                    }
                }
            }
            // if (hadPiece)
        }
		if (column) {
			this.rotateRight();
		}
		return moves;
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
	
	private void rotateLeft(){
		Board b = new Board(boardSize, this.getBoardCopy());
		
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				b.board[boardSize - j - 1][i] = this.board[i][j];
			}
		}
		this.board = b.board;
	}
	
	private void rotateRight(){
		Board b = new Board(boardSize, this.getBoardCopy());
		
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				b.board[i][j] = this.board[boardSize - j - 1][i];
			}
		}
		this.board = b.board;
	}
	
	private void addGameStates(List<Board> moves, char piece, int pieceStart, int pieceLength, int emptyBefore, int emptyAfter, int row, boolean rotate) {
		// char[][] newRow = this.getRow(row);
        Board newBoard = new Board(this, this.getBoardCopy());
		// remove the piece from the row
		for (int i = 0; i < pieceLength; i++) {
			newBoard.board[row][pieceStart + i] = '.';
		}
		// System.out.println(new String(newRow));
		// generate the moves from moving the piece to the left
        for (int i = 0; i < emptyBefore; i++) {
			Board b = new Board(this, newBoard.getBoardCopy());
			// put the piece back in
			for (int j = 0; j < pieceLength; j++) {
				b.board[row][pieceStart - emptyBefore + i + j] = piece;
			}
			if (rotate) {
				b.rotateRight();
			}
			moves.add(b);
		}
		// generate the moves from moving the piece to the right
        for (int i = 0; i < emptyAfter; i++) {
			Board b = new Board(this, newBoard.getBoardCopy());
			// put the piece back in
			for (int j = 0; j < pieceLength; j++) {
				b.board[row][pieceStart + 1 + i + j] = piece;
			}
			if (rotate) {
				b.rotateRight();
			}
			moves.add(b);
        }
    }
	
	public static void main(String args[]) {
		Board b = new Board(7, testBoard);
		System.out.println(b);
		List<Board> tonsOfMoves = b.validMoves();
		System.out.println("\n\nSTARTING BOARD\n");
		System.out.println(b);
		System.out.println("\nPOSSIBLE X-AXIS MOVES\n");
		for (Board m : tonsOfMoves) {
			System.out.println();
			System.out.println(m);
		}
	}
}
