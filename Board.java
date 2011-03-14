import java.util.HashSet;
import java.util.Iterator;

public class Board {
	private int boardSize;
	private char board[][];
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
		System.out.println("whatever");
		return new Board[1];
	}
	
	public static void main(String args[]) {
		Board b = new Board(7, testBoard);
		System.out.println(b);
		System.out.println(b.getColumn(1));
		System.out.println(b.getRow(6));
		System.out.println(b.getPieces(b.getRow(6)));
	}
}