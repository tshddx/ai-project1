import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Font;
import java.util.*;

/**
 * GUI component to display a rushhour board or rushhour board animation.
 */
public class GameDisplay extends JPanel {
    boolean mBoardIsNull;
    Board mBoard;
	Board mBoardNext;
	List<Board> mAnimation;
	Point mAnimNewLoc;
	Point mAnimOldLoc;
	char mAnimPiece;
	HashMap<Character, Color> mColorMap;
	
	double mAnimPercentComplete;
	int mAnimSpeed; // milliseconds per move
	int state;
	boolean animating;
	Date lastTime;
	
    /**
     * Constructs a new GameDisplay object, displaying a null board
     */
    public GameDisplay() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
        mBoardIsNull = true;
		mAnimPercentComplete = 0.0;
		mAnimSpeed = 2000;
		animating = false;
		mColorMap = new HashMap<Character, Color>();
    }

    /**
     * Sets the displayed board to 'board'. If playing, stops the currently
     * animating animation.
     *
     * @param board the board to display
     */
    public void setBoard(Board board) {
        mBoard = board;
        mBoardIsNull = false;
		animating = false;
		setColors();
    }
    
    /**
     * Set the current board to null
     */
    public void setBoardNull() {
        mBoardIsNull = true;
    }
    
    /**
     * Sets the current animation and displays the first frame, with the
     * animation paused.
     *
     * @param boards list of boards in the animation
     */ 
    public void setAnimation(List<Board> boards) {
        mAnimation = boards;
        resetAnimation();
        animating = false;
        setColors();
    }

    /**
     * Resets the animation back to the first frame and pauses it.
     */
	public void resetAnimation() {
		mAnimPercentComplete = 0.0;
		setState(0);
		animating = false;
	}
	
    /**
     * Starts playing the animation from the current frame. It will 
     * be updated each time the component is repainted. The 
     * animation will be played back at the
     * speed set by setAnimationSpeed (default 2000).
     *
     * When the animation is finished, it will stop on the last frame. After
     * this happens, isAnimating() will return false.
     */
    public void startAnimation() {
		lastTime = new Date();
        if (state == mAnimation.size() - 1)
            resetAnimation();
		animating = true;
    }
	
	/**
	 * Set the speed at which to play the animation.
     * Note that this is in a constant time per move. (For clarity.)
     *
     * @param speed speed in milliseconds per move
	 */
	public void setAnimationSpeed(int speed) {
		mAnimSpeed = speed;
	}

    /**
     * Pauses the animation on the current frame
     */
    public void pauseAnimation() {
		animating = false;
    }
	
    /**
     * Determine whether the animation is currently playing.
     *
     * @return true if the animation is currently playing.
     */
	public boolean isAnimating() {
		return animating;
	}
	
    /**
     * Sets the current state (or move) in the animation. If the provided
     * state number is out of range, the board will be set to null.
     *
     * @param s state number to make current.
     */
    public void setState(int s) {
		state = s;

        // Reset the animation
		mAnimPercentComplete = 0.0;

        if (mAnimation == null)
            return;

		mBoard = mAnimation.get(state);
        mAnimPiece = 0;

		if (state < mAnimation.size() - 1 && state >= 0) {
			mBoardNext = mAnimation.get(state + 1);
			
            // Compare the boards and look for a mismatch
			for (int i = 0; i < mBoard.size(); i++) {
				for (int j = 0; j < mBoard.size(); j++) {
					char p = mBoard.get(i, j);
					if (p != '.' && p != mBoardNext.get(i, j)) {
                        // This piece doesn't match, so it must be animated.
                        
                        // Find its new location.
						mAnimNewLoc = getULCornerFor(mBoardNext, p);
						mAnimOldLoc = new Point(i, j);
						mAnimPiece = p;
						return;
					}
				}
			}
		} else {
			mBoardNext = null;
        }
    }

    /**
     * Returns the current state (move) of the animation.
     *
     * @return the current state number
     */
    public int getState() {
        return state;
    }

    /**
     * Advance the animation the proper amount for the time passed.
     *
     * Side effects: updates lastTime with the current time.
     */
	private void stepAnimation() {
		if (mBoardNext == null)
			animating = false;
			
		if (!animating)
			return;

		Date now = new Date();
		long delta = now.getTime() - lastTime.getTime();
		
		if (mAnimPercentComplete >= 1.0) {
			setState(state + 1);
		} else {
			mAnimPercentComplete += (double)delta / mAnimSpeed;
		}
		
		lastTime = now;
	}
	
    /**
     * Assigns a random color for each piece in the current board, if a color
     * was not assigned already.
     *
     * Side effects: updates mColorMap.
     */
	private void setColors() {
        if (mBoard == null) {
            return;
        }

		Random rng = new Random();
		for (int i = 0; i < mBoard.size(); i++) {
			for (int j = 0; j < mBoard.size(); j++) {
				if (mBoard.get(i, j) == '.') 
					continue;
					
				if (mBoard.get(i, j) == 'X') {
					mColorMap.put(mBoard.get(i, j), Color.RED);
				} else if (!mColorMap.containsKey(mBoard.get(i, j))) {
					Color c;
					c = new Color(rng.nextInt(255),rng.nextInt(255),rng.nextInt(255));
					mColorMap.put(mBoard.get(i, j), c);
				} 
			}
		}
	}

    /**
     * Finds the upper-left-most occurence of a particular piece in the given
     * board.
     *
     * @param board the board to search
     * @param piece the piece to search for
     */
	private Point getULCornerFor(Board board, char piece) {
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.size(); j++) {
				if (board.get(i, j) == piece)
					return new Point(i, j);
			}
		}
		
		return null;
	}
	
    /**
     * Draws the pieces on the board
     */
	private void drawPieces(Graphics g, int x, int y, int sqouter) {
		int sqx, sqy = x + 5;
        for (int j = 0; j < mBoard.size(); j++) {
			sqx = x + 5;
            for (int i = 0; i < mBoard.size(); i++) {
                char piece = mBoard.get(i, j);
				if (piece != '.') { // Empty squares are not drawn
					g.setColor(mColorMap.get(piece));
					int px, py;
					px = sqx - 5;
					py = sqy - 5;
					
					if (piece == mAnimPiece) {
                        // This is the animated piece, move it a percentage of
                        // the way toward its destination.
						int offsetX = i - mAnimOldLoc.x;
						int offsetY = j - mAnimOldLoc.y;
						int nx = x + (mAnimNewLoc.x + offsetX) * sqouter;
						int ny = y + (mAnimNewLoc.y + offsetY) * sqouter;

						px += mAnimPercentComplete * (nx - px);
						py += mAnimPercentComplete * (ny - py);
					}
					g.fillRect(px, py, sqouter, sqouter);
				}
				sqx += sqouter;
			}
			sqy += sqouter;
		}
	}

    /**
     * Overridden paint method. Draws the rushhour board and advances the
     * animation as needed.
     *
     * Do not call directly. Call repaint() as needed instead.
     *
     * @param g
     */
    public void paint(Graphics g) {
        int x = 25;
        int y = 25;

        Dimension size = getSize();
        int w = 0;
        if (size.getWidth() > size.getHeight()) {
            w = (int)size.getHeight() - 50;
        } else {
            w = (int)size.getWidth() - 50;
        }

        // White bg
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (int)size.getWidth(), (int)size.getHeight());

        // Draw board
        g.setColor(new Color(150, 150, 150));
        g.fillRoundRect(x, y, w, w, 5, 5);
        g.setColor(new Color(20, 20, 20));
        g.drawRoundRect(x, y, w, w, 10, 10);

        int sqinner = (int)((double)w / 7 * .8);
        int sqblock = (int)((double)w / 7 * .9);
        int sqouter = (int)((double)w / 7);

        g.setColor(new Color(70, 70, 70));
        int sqx, sqy = x + 5;
        for (int i = 0; i < 7; i++) {
            sqx = x + 5;
            for (int j = 0; j < 7; j++) {
                g.fillRoundRect(sqx, sqy, sqinner, sqinner, 10, 10);
                sqx += sqouter;
            }
            sqy += sqouter;
        }

        if (mBoardIsNull) {
            g.setColor(new Color(200, 0, 0));
            g.setFont(new Font("Sans", Font.ITALIC, 30));
            g.drawString("There is no board!", w/2 - 100, w/2);
        } else {
			drawPieces(g, x, y, sqouter);
			stepAnimation();
        }
    }

}

