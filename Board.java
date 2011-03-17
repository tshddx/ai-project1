import java.util.*;

public class Board {
	private int boardSize;
	private char board[][];
	ArrayList<Board> moves = new ArrayList<Board>();
	// For now
	private static char testBoard[][] =
	    { { '.', '.', '.', '.', 'O', 'O', 'O'},
          { '.', '.', '.', '.', 'A', 'B', 'C'},
          { '.', '.', 'X', 'X', 'A', 'B', 'C'},
          { '.', '.', '.', '.', 'P', 'P', 'P'},
          { '.', '.', '.', '.', '.', '.', '.'},
          { '.', '.', '.', '.', '.', '.', '.'},
          { '.', '.', '.', '.', '.', '.', '.'} };
	
	public Board(int boardSize, char[][] board) {
		this.boardSize = boardSize;
		this.board = board;
		// call validMoves
	}

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
	public Board[] validMoves() {
	    // Iterate through rows
	    for (int i = 0; i < boardSize; i++) {
            int emptyBefore, emptyAfter = 0;
	        int pieceLength, pieceStart = 0;
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
                            if (j = boardSize - 1) {
                                addGameStates(piece, pieceStart, pieceLength, emptyBefore, emptyAfter, i);
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
                        if (j = boardSize - 1) {
                            addGameStates(piece, pieceStart, pieceLength, emptyBefore, emptyAfter, i);
                        }
                    }
                    else {
                        // found new piece
                        if (hadPiece) {
                            // add moves for last piece (using empty after spaces)
                            addGameStates(piece, pieceStart, pieceLength, emptyBefore, emptyAfter, i);
                            emptyBefore = emptyAfter;
                        }
                        else {
                            // Never had a peice, found first piece, doesn't mean anything
                        }
                        piece = board[i][j];
                        pieceLength = 1;
                        pieceStart = j;
                    }
                }
            }
            if (hadPiece)
        }
		System.out.println("whatever");
		return new ArrayList <Board>;
	}
	
	private void addGameStates(char piece, int pieceStart, int pieceLength, int emptyBefore, int emptyAfter, int row) {
        // add game states to array list
        for (int i = 0; i < (emptyAfter + emptyBefore); i++) {
            Board b = new Board(boardSize, board);
        }
    }
	
	public static void main(String args[]) {
		Board b = new Board(7, testBoard);
		System.out.println(b);
		System.out.println(b.getColumn(1));
		System.out.println(b.getRow(6));
		System.out.println(b.getPieces(b.getRow(6)));
	}
}