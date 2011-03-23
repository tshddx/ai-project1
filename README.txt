CSC 540
Project 1 "Rush Hour"

Authors:
Brian Hrebec
David Robinson
Thomas Shaddox

INSTRUCTIONS
============

From the directory containing our .java files, run the command

    javac Proj1RushHour.java

Then run the command

    java Proj1RushHour

Our application should appear, with an empty game board that says "There is no board!" Click on the "Select a board..." drop-down box and choose one of the 42 puzzles to solve. When you find one you like, click the "Solve!" button. Depending on the puzzle, the application might discover a solution immediately, or it might have to "think" for a while.

When it find a solution, the pieces will begin moving. You can change the milliseconds/move option (descreasing the value will increase the animation speed). You can also pause and resume the animation, or jump to a specific move.

Notice that once a solution has been found, information will be displayed such as the number of moves in the solution, the max tree depth, and the number of states that were generated when searching for a solution.

HOW IT WORKS
============

We created a Board class that represents a state of the game board. Starting from the initial Board (which you can choose), we generate every valid move that can be made. Each of these is a new Board object, and they represent the "neighbors" of the initial Board. Starting from this initial Board, and subsequently generating neighbors, we create the graph of the problem space one Board at a time.

Note: Technically, every time you can move a piece, you can immediately move the piece back to its previous position. Clearly, this results in no progress being made. To make sure we don't return to a previously-visited game state, we hash each new Board in a Java HashSet. HashSets provide constant time `add` and `contains` operations, so they're quite fast.

We chose to use a A* search to discover our solution. The estimate is
made based on the number of pieces blocking the X. This guarantees that we find an optimal solution, which means we will solve each puzzle in the fewest moves possible. In some cases, there are multiple "optimal solutions." For example, there might be two different ways to solve a puzzle in eight moves. In these cases, our application will find one of these based on the order in which neighbors are visited.

OUR RESULTS
===========

We match or beat the number of moves for every solution provided. We beat the provided move number in cases in board 27 and 42 (as numbered in our application).
