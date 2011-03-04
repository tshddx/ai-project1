
public class GameState
{
    /**
     * Game piece positions for the given RushHour game.
     * 
     * @param  g   the given RushHour game.
     */
    public static char[][] getInitialGame(int g)
    {
        
        // Known range of valid initial game states {
        if (g > 0 && g <= initialGameStateCount())  {
            return (char [][]) tempboard[g-1];  // Return initial game state
        }
		else
		{
			throw new IllegalArgumentException("Game number out of range");
		}

    } // end initializeGame

	
    /**
     * Return the number of possible initial states of RushHour game.
     */
    public static int initialGameStateCount()
    {
        return tempboard.length;
    } // end initialGameStates

    // This declaration is put at the bottom because it's so big and would 
    // obscure the methods
    final static private char tempboard[][][] = {
             
			// RushHour Railroad card 0, Blank, 0 move solution
            { { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'} },

			// RushHour Railroad card 1, Junior, 5 move solution
            { { '.', '.', '.', '.', 'O', 'O', 'O'},
              { '.', '.', '.', '.', 'A', 'B', 'C'},
              { '.', '.', 'X', 'X', 'A', 'B', 'C'},
              { '.', '.', '.', '.', 'P', 'P', 'P'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'} },

			// RushHour Railroad card 2, Junior, 6 move solution
			{ { '.', '.', '.', '.', '.', '.', '.'},
			  { '.', '.', '.', '.', '.', '.', '.'},
			  { '.', '.', 'A', 'A', 'B', 'C', 'C'},
			  { 'X', 'X', 'D', '.', 'B', '.', 'F'},
			  { '.', '.', 'D', '.', 'E', '.', 'F'},
			  { '.', '.', 'G', 'G', 'E', 'H', 'H'},
			  { '.', '.', '.', '.', '.', '.', '.'} },

			// RushHour Railroad card 4, Junior, 9 move solution
            { { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', 'A', 'A', 'B', 'B', 'C'},
              { '.', '.', 'D', '.', '.', '.', 'C'},
              { 'X', 'X', 'D', '.', '.', '.', 'E'},
              { 'F', '.', '.', '.', '.', '.', 'E'},
              { 'F', 'G', 'G', 'H', 'H', 'I', 'I'},
              { '.', '.', '.', '.', '.', '.', '.'} },

			// RushHour Railroad card 6, Junior, 7 move solution
            { { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', 'A', 'A', 'B'},
              { 'X', 'X', 'O', '.', 'C', '.', 'B'},
              { 'D', '.', 'O', '.', 'C', '.', 'P'},
              { 'D', '.', 'O', '.', 'E', '.', 'P'},
              { 'F', '.', 'G', 'G', 'E', '.', 'P'},
              { 'F', '.', '.', '.', '.', '.', '.'} },
	    
            // RushHour Railroad card 9, Junior, 22 move solution
            { { 'A', 'O', 'O', 'O', '.', 'B', 'B'},
              { 'A', '.', '.', 'P', '.', '.', 'Q'},
              { 'C', '.', '.', 'P', '.', '.', 'Q'},
              { 'C', 'X', 'X', 'P', '.', '.', 'Q'},
              { 'D', '.', '.', 'E', '.', '.', 'R'},
              { 'D', '.', '.', 'E', '.', '.', 'R'},
              { 'F', 'F', 'G', 'G', 'H', 'H', 'R'} },

            // RushHour Railroad card 10, Junior, 10 move solution
            { { 'A', 'U', 'U', '.', '.', '.', '.'},
              { 'A', 'U', 'U', '.', 'O', 'O', 'O'},
              { '.', 'X', 'X', 'B', 'P', '.', 'Q'},
              { '.', 'C', '.', 'B', 'P', '.', 'Q'},
              { '.', 'C', 'D', 'D', 'P', '.', 'Q'},
              { 'E', 'V', 'V', '.', 'R', 'R', 'R'},
              { 'E', 'V', 'V', '.', '.', '.', '.'} },

			// RushHour Railroad card 3, Beginner, 7 move solution
            { { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', 'A', 'O', 'O', 'O', 'B'},
              { 'X', 'X', 'A', '.', 'P', '.', 'B'},
              { '.', '.', 'C', 'C', 'P', 'D', 'D'},
              { '.', '.', 'E', '.', 'P', '.', 'F'},
              { '.', '.', 'E', 'Q', 'Q', 'Q', 'F'},
              { '.', '.', '.', '.', '.', '.', '.'} },
	 
			// RushHour Railroad card 4, Beginner, 19 move solution
            { { '.', 'A', 'A', 'O', 'O', 'O', '.'},
              { 'B', 'B', 'C', 'D', 'E', 'F', 'F'},
              { 'X', 'X', 'C', 'D', 'E', 'P', 'G'},
              { 'Q', 'H', 'I', 'J', 'J', 'P', 'G'},
              { 'Q', 'H', 'I', 'K', 'K', 'P', 'R'},
              { 'Q', 'U', 'U', '.', 'L', 'L', 'R'},
              { '.', 'U', 'U', 'S', 'S', 'S', 'R'} },

            // RushHour Railroad card 6, Beginner, 12 move solution
            { { '.', 'A', '.', '.', '.', '.', '.'},
              { '.', 'A', '.', 'O', 'O', 'O', 'Q'},
              { 'X', 'X', '.', 'R', 'U', 'U', 'Q'},
              { '.', '.', '.', 'R', 'U', 'U', 'Q'},
              { '.', '.', '.', 'R', 'P', 'P', 'P'},
              { '.', '.', '.', 'B', 'B', 'C', 'C'},
              { '.', '.', '.', '.', '.', '.', '.'} },

			// RushHour Railroad card 7, Beginner, 13 move solution
			{ { '.', '.', 'O', 'O', 'O', '.', '.'},
			  { '.', '.', 'P', 'P', 'P', '.', '.'},
			  { 'X', 'X', 'A', 'B', 'C', 'Q', 'R'},
			  { 'D', 'D', 'A', 'B', 'C', 'Q', 'R'},
			  { 'E', 'E', 'S', 'S', 'S', 'Q', 'R'},
			  { '.', '.', 'F', 'G', 'H', '.', '.'},
			  { '.', '.', 'F', 'G', 'H', '.', '.'} },

			// RushHour Railroad card 8, Beginner, 10 move solution
            { { 'A', 'A', 'B', 'B', 'C', 'C', 'D'},
              { 'O', '.', '.', '.', '.', '.', 'D'},
              { 'O', '.', 'X', 'X', 'E', '.', 'P'},
              { 'O', '.', '.', '.', 'E', '.', 'P'},
              { 'Q', '.', 'F', 'G', 'G', '.', 'P'},
              { 'Q', '.', 'F', 'H', '.', 'I', 'I'},
              { 'Q', 'J', 'J', 'H', '.', '.', '.'} },

			// RushHour Railroad card 2, Intermediate, 15 move solution
			{ { 'O', 'A', 'A', 'B', 'B', 'C', 'C'},
			  { 'O', '.', '.', '.', '.', '.', 'D'},
			  { 'O', '.', 'X', 'X', 'E', '.', 'D'},
			  { 'P', '.', 'F', '.', 'E', '.', 'G'},
			  { 'P', '.', 'F', '.', 'Q', '.', 'G'},
			  { 'P', '.', '.', '.', 'Q', 'H', 'H'},
			  { 'I', 'I', 'J', 'J', 'Q', '.', '.'} },

		// RushHour Railroad card 5, Intermediate, 12 move solution
			{ { 'A', 'A', 'B', 'B', 'C', '.', '.'},
			  { 'D', '.', '.', '.', 'C', '.', '.'},
			  { 'D', '.', 'E', 'X', 'X', '.', 'O'},
			  { 'P', '.', 'E', '.', '.', '.', 'O'},
			  { 'P', '.', '.', '.', '.', '.', 'O'},
			  { 'P', 'F', 'F', 'G', 'G', 'H', 'H'},
			  { '.', '.', '.', '.', '.', '.', '.'} },

			// RushHour Railroad card 11, Intermediate, 20 move solution
            { { '.', '.', 'O', 'P', 'Q', 'R', 'S'},
              { '.', '.', 'O', 'P', 'Q', 'R', 'S'},
              { 'X', 'X', 'O', 'P', 'Q', 'R', 'S'},
              { 'A', 'A', '.', '.', '.', '.', '.'},
              { 'B', 'B', '.', '.', '.', '.', '.'},
              { 'C', 'C', '.', '.', '.', 'U', 'U'},
              { 'D', 'D', '.', '.', '.', 'U', 'U'} },

            // RushHour Railroad card 12, Intermediate, 18 move solution
            { { '.', '.', '.', '.', 'A', 'O', '.'},
              { '.', '.', 'B', 'B', 'A', 'O', '.'},
              { '.', 'X', 'X', 'U', 'U', 'O', '.'},
              { '.', '.', 'P', 'U', 'U', 'C', 'C'},
              { '.', '.', 'P', 'D', 'Q', 'Q', 'Q'},
              { '.', '.', 'P', 'D', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'} },

            // RushHour Railroad card 13, Intermediate, 16 move solution
            { { 'A', '.', 'O', 'U', 'U', 'P', '.'},
              { 'A', '.', 'O', 'U', 'U', 'P', '.'},
              { 'X', 'X', 'O', 'B', 'C', 'P', '.'},
              { '.', 'D', 'D', 'B', 'C', 'E', 'E'},
              { '.', 'F', 'F', 'V', 'V', 'G', 'G'},
              { '.', 'H', '.', 'V', 'V', '.', 'I'},
              { '.', 'H', '.', 'J', 'J', '.', 'I'} },
				  
			// RushHour Railroad card 14, Intermediate, 21 move solution
            { { '.', '.', '.', '.', 'A', 'B', 'B'},
              { '.', 'C', 'D', 'D', 'A', 'E', '.'},
              { '.', 'C', 'F', 'G', 'G', 'E', '.'},
              { 'X', 'X', 'F', 'H', 'O', '.', '.'},
              { 'P', 'I', 'I', 'H', 'O', '.', '.'},
              { 'P', 'J', 'K', 'K', 'O', 'L', 'L'},
              { 'P', 'J', 'Q', 'Q', 'Q', '.', '.'} },			  
			  
            // RushHour Railroad card 15, Intermediate, 24 move solution
            { { 'A', '.', 'U', 'U', 'B', 'C', 'C'},
              { 'A', '.', 'U', 'U', 'B', '.', '.'},
              { 'D', 'D', 'E', 'F', 'F', '.', '.'},
              { 'X', 'X', 'E', '.', 'G', '.', '.'},
              { '.', '.', 'H', 'H', 'G', 'I', 'I'},
              { '.', '.', 'J', 'V', 'V', '.', 'K'},
              { 'L', 'L', 'J', 'V', 'V', '.', 'K'} },

			// RushHour Railroad card 16, Intermediate, 23 move solution
			{ { 'A', '.', '.', 'B', 'B', 'C', 'C'},
  			  { 'A', '.', '.', 'O', 'U', 'U', 'D'},
  			  { 'X', 'X', '.', 'O', 'U', 'U', 'D'},
  			  { 'E', '.', '.', 'O', 'P', 'P', 'P'},
  			  { 'E', 'Q', 'Q', 'Q', 'R', '.', '.'},
  			  { 'F', 'G', 'V', 'V', 'R', 'H', '.'},
  			  { 'F', 'G', 'V', 'V', 'R', 'H', '.'} },

			// RushHour Railroad card 17, Intermediate, 25 move solution
            { { 'O', 'O', 'O', '.', '.', 'A', 'P'},
              { 'Q', 'B', '.', '.', '.', 'A', 'P'},
              { 'Q', 'B', 'C', 'C', 'D', 'D', 'P'},
              { 'Q', 'X', 'X', 'R', 'E', '.', 'S'},
              { '.', '.', 'F', 'R', 'E', '.', 'S'},
              { '.', '.', 'F', 'R', 'G', 'G', 'S'},
              { '.', 'H', 'H', 'I', 'I', 'J', 'J'} },

			// RushHour Railroad card 18, Intermediate, 19 move solution
            { { '.', '.', 'F', '.', '.', '.', '.'},
              { 'E', 'E', 'F', 'K', 'R', 'R', 'R'},
              { '.', 'X', 'X', 'K', 'J', '.', '.'},
              { 'U', 'U', 'B', 'B', 'J', 'A', 'O'},
              { 'U', 'U', 'P', 'D', 'D', 'A', 'O'},
              { '.', '.', 'P', '.', 'H', 'H', 'O'},
              { '.', '.', 'P', '.', '.', 'I', 'I'} },

			// RushHour Railroad card 19, Intermediate, 15 move solution
            { { '.', 'A', 'A', '.', 'B', 'C', 'O'},
              { '.', '.', 'D', '.', 'B', 'C', 'O'},
              { 'E', 'E', 'D', '.', 'X', 'X', 'O'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { 'P', 'F', 'F', '.', 'G', 'H', 'H'},
              { 'P', 'I', 'J', '.', 'G', 'K', 'K'},
              { 'P', 'I', 'J', '.', 'Q', 'Q', 'Q'} },

			// RushHour Railroad card 20, Intermediate, 21 move solution
			{ { '.', '.', 'A', 'A', 'O', 'O', 'O'},
			  { '.', '.', 'B', 'U', 'U', 'C', 'C'},
			  { '.', '.', 'B', 'U', 'U', 'V', 'V'},
			  { 'X', 'X', 'P', 'D', 'E', 'V', 'V'},
			  { '.', '.', 'P', 'D', 'E', 'Q', 'R'},
			  { '.', '.', 'P', 'F', 'F', 'Q', 'R'},
			  { '.', '.', 'S', 'S', 'S', 'Q', 'R'} },

			// RushHour Railroad card 25, Intermediate, 31 move solution
			{ { '.', 'F', 'F', 'C', 'L', 'L', 'P'},
			  { '.', 'A', '.', 'C', 'Q', '.', 'P'},
			  { '.', 'A', 'X', 'X', 'Q', '.', 'P'},
			  { 'B', 'B', 'O', '.', 'Q', 'E', 'E'},
			  { 'J', '.', 'O', 'D', 'D', 'G', '.'},
			  { 'J', '.', 'O', 'H', '.', 'G', '.'},
			  { '.', 'K', 'K', 'H', 'I', 'I', '.'} },

			// RushHour Railroad card 31, Intermediate, 32 move solution
			{ { 'A', '.', '.', '.', 'B', '.', '.'},
			  { 'A', '.', '.', '.', 'B', 'C', 'C'},
			  { 'D', '.', '.', '.', 'O', 'O', 'O'},
			  { 'D', 'X', 'X', '.', 'E', 'F', '.'},
			  { 'G', 'G', 'H', 'H', 'E', 'F', '.'},
			  { '.', 'I', 'J', 'K', 'K', 'L', '.'},
			  { '.', 'I', 'J', '.', '.', 'L', '.'} },
			  
			// RushHour Railroad card 33, Intermediate, 32 move solution
			{ { '.', 'A', 'B', 'B', 'C', 'C', 'O'},
			  { '.', 'A', 'D', 'D', 'P', '.', 'O'},
			  { '.', 'X', 'X', 'E', 'P', '.', 'O'},
			  { 'Q', 'Q', 'Q', 'E', 'P', '.', '.'},
			  { '.', '.', 'F', 'G', 'G', 'H', 'H'},
			  { '.', '.', 'F', 'I', 'I', 'J', '.'},
			  { '.', 'K', 'K', 'L', 'L', 'J', '.'} },
			  
			// RushHour Railroad card 37, Intermediate, 44 move solution
			{ { '.', '.', 'A', '.', 'B', 'C', 'C'},
			  { '.', '.', 'A', '.', 'B', '.', '.'},
			  { 'D', 'D', 'E', '.', 'F', 'G', 'G'},
			  { 'X', 'X', 'E', '.', 'F', '.', 'H'},
			  { 'U', 'U', 'V', 'V', 'I', 'I', 'H'},
			  { 'U', 'U', 'V', 'V', '.', '.', 'J'},
			  { '.', 'K', 'K', 'L', 'L', '.', 'J'} },
  
			// RushHour Railroad card 21, Advanced, 22 move solution
            { { '.', '.', '.', '.', '.', '.', '.'},
              { '.', 'A', 'A', 'O', 'O', 'O', 'P'},
              { 'X', 'X', 'U', 'U', 'V', 'V', 'P'},
              { '.', 'B', 'U', 'U', 'V', 'V', 'P'},
              { '.', 'B', 'C', 'C', 'Q', 'Q', 'Q'},
              { '.', '.', 'D', 'R', 'R', 'R', '.'},
              { '.', '.', 'D', '.', '.', '.', '.'} },
			  
			// RushHour Railroad card 22, Advanced, 27 move solution  
			{ { '.', '.', 'A', 'A', 'B', 'U', 'U'},
  			  { '.', '.', '.', '.', 'B', 'U', 'U'},
  			  { '.', '.', 'C', 'O', 'O', 'O', '.'},
  			  { 'X', 'X', 'C', 'V', 'V', 'D', '.'},
  			  { '.', '.', 'E', 'V', 'V', 'D', '.'},
  			  { '.', '.', 'E', 'P', 'P', 'P', '.'},
  			  { '.', '.', '.', '.', '.', '.', '.'} },

			  // RushHour Railroad card 23, Advanced, 26 move solution
            { { 'A', '.', 'B', '.', 'O', 'O', 'O'},
              { 'A', '.', 'B', 'U', 'U', 'C', 'C'},
              { 'D', 'E', 'E', 'U', 'U', 'F', 'G'},
              { 'D', '.', '.', 'X', 'X', 'F', 'G'},
              { 'J', 'H', 'H', 'V', 'V', 'I', 'I'},
              { 'J', '.', 'K', 'V', 'V', '.', '.'},
              { '.', '.', 'K', '.', 'P', 'P', 'P'} },

			// RushHour Railroad card 26, Advanced, 34 move solution
            { { 'A', '.', '.', 'U', 'U', 'O', '.'},
              { 'A', 'B', 'B', 'U', 'U', 'O', '.'},
              { 'X', 'X', 'C', 'D', 'E', 'O', '.'},
              { 'F', 'P', 'C', 'D', 'E', 'G', 'G'},
              { 'F', 'P', 'V', 'V', 'H', 'H', 'I'},
              { 'J', 'P', 'V', 'V', '.', '.', 'I'},
              { 'J', 'Q', 'Q', '.', '.', '.', '.'} },

			// RushHour Railroad card 27, Advanced, 31 move solution
			{ { '.', 'A', 'A', 'O', 'O', 'O', 'P'},
  			  { '.', 'Q', 'Q', 'Q', 'U', 'U', 'P'},
  			  { 'X', 'X', 'V', 'V', 'U', 'U', 'P'},
  			  { '.', 'B', 'V', 'V', 'R', '.', '.'},
  			  { '.', 'B', '.', '.', 'R', 'C', 'C'},
  			  { '.', 'S', 'S', 'S', 'R', '.', '.'},
  			  { '.', '.', '.', '.', '.', '.', '.'} },

            // RushHour Railroad card 28, Advanced, 27 move solution
            { { '.', '.', '.', '.', '.', '.', '.'},
              { '.', 'E', 'P', 'Q', 'Q', 'Q', '.'},
              { '.', 'E', 'P', 'V', 'V', 'C', '.'},
              { 'X', 'X', 'P', 'V', 'V', 'C', '.'},
              { 'B', 'U', 'U', 'R', 'F', 'F', '.'},
              { 'B', 'U', 'U', 'R', 'A', '.', '.'},
              { 'O', 'O', 'O', 'R', 'A', '.', '.'} },

			// RushHour Railroad card 29, Advanced, 34 move solution
            { { 'O', 'A', '.', 'P', 'B', '.', '.'},
              { 'O', 'A', '.', 'P', 'B', 'C', 'C'},
              { 'O', 'X', 'X', 'P', 'D', '.', '.'},
              { '.', 'E', '.', '.', 'D', '.', '.'},
              { '.', 'E', 'Q', 'Q', 'Q', '.', 'R'},
              { 'F', 'F', 'G', 'H', 'H', '.', 'R'},
              { '.', '.', 'G', '.', '.', '.', 'R'} },

			// RushHour Railroad card 32, Expert, 36 move solution
            { { 'A', 'B', 'O', 'O', 'O', '.', 'C'},
              { 'A', 'B', 'P', 'P', 'P', '.', 'C'},
              { '.', '.', 'Q', '.', '.', 'U', 'U'},
              { 'X', 'X', 'Q', '.', '.', 'U', 'U'},
              { '.', '.', 'Q', '.', '.', 'G', 'G'},
              { '.', '.', 'H', 'E', 'F', 'I', 'J'},
              { '.', '.', 'H', 'E', 'F', 'I', 'J'} },

			// RushHour Railroad card 34, Expert, 34 move solution
            { { '.', '.', '.', '.', '.', '.', 'P'},
              { '.', 'B', 'B', '.', '.', '.', 'P'},
              { '.', '.', 'O', '.', 'X', 'X', 'P'},
              { '.', '.', 'O', '.', '.', 'U', 'U'},
              { 'C', 'C', 'O', '.', 'Q', 'U', 'U'},
              { 'A', 'D', 'E', 'E', 'Q', 'F', '.'},
              { 'A', 'D', '.', '.', 'Q', 'F', '.'} },

			// RushHour Railroad card 35, Expert, 41 move solution
            { { 'A', 'B', 'B', 'C', '.', '.', 'O'},
              { 'A', '.', '.', 'C', '.', '.', 'O'},
              { 'X', 'X', 'D', '.', '.', '.', 'O'},
              { 'E', 'F', 'D', 'G', 'G', 'U', 'U'},
              { 'E', 'F', 'H', 'H', 'P', 'U', 'U'},
              { 'I', 'I', 'J', 'J', 'P', 'K', '.'},
              { '.', '.', '.', '.', 'P', 'K', '.'} },

            // RushHour Railroad card 36, Expert, 44 move solution
            { { '.', '.', '.', 'O', 'O', 'O', 'C'},
              { '.', '.', '.', 'P', 'P', 'P', 'C'},
              { '.', 'Q', 'Q', 'Q', 'U', 'U', 'G'},
              { 'X', 'X', 'B', '.', 'U', 'U', 'G'},
              { '.', 'E', 'B', 'R', 'A', 'D', 'D'},
              { '.', 'E', 'F', 'R', 'A', '.', '.'},
              { '.', '.', 'F', 'R', '.', '.', '.'} },

			// RushHour Railroad card 38, Expert, 47 move solution
            { { '.', '.', '.', '.', '.', 'U', 'U'},
              { 'A', 'A', 'O', 'B', 'B', 'U', 'U'},
              { 'X', 'X', 'O', 'V', 'V', '.', '.'},
              { '.', 'D', 'O', 'V', 'V', 'C', '.'},
              { '.', 'D', 'E', 'E', 'P', 'C', '.'},
              { '.', '.', '.', '.', 'P', 'F', 'F'},
              { '.', '.', '.', '.', 'P', '.', '.'} },

            // RushHour Railroad card 39, Expert, 52 move solution
            { { 'C', 'C', 'F', 'F', 'P', '.', 'Q'},
              { 'A', 'B', '.', '.', 'P', '.', 'Q'},
              { 'A', 'B', 'X', 'X', 'P', '.', 'Q'},
              { '.', '.', 'E', 'D', 'O', 'O', 'O'},
              { '.', '.', 'E', 'D', '.', '.', '.'},
              { 'G', 'G', 'H', 'H', '.', 'U', 'U'},
              { '.', '.', '.', '.', '.', 'U', 'U'} },
			  
            // RushHour Railroad card 40, Expert, 93 move solution
            { { 'A', 'U', 'U', '.', '.', '.', '.'},
              { 'A', 'U', 'U', '.', 'O', 'O', 'O'},
              { '.', 'C', 'X', 'X', 'P', '.', 'Q'},
              { '.', 'C', '.', 'B', 'P', '.', 'Q'},
              { '.', 'D', 'D', 'B', 'P', '.', 'Q'},
              { 'E', 'V', 'V', '.', 'R', 'R', 'R'},
              { 'E', 'V', 'V', '.', '.', '.', '.'} }
			  
		};  // end of declaration and initialization of tempboard[][][]
    
} // end GameState	   

 



				  
