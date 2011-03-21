import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Font;
import java.util.*;

public class GameDisplay extends JPanel {
    boolean mBoardIsNull;
    char[][] mBoard;
	char[][] mBoardNext;
	char[][][] mAnimation;
	Point mAnimNewLoc;
	Point mAnimOldLoc;
	char mAnimPiece;
	HashMap<Character, Color> mColorMap;
	
	double mAnimPercentComplete;
	double mAnimSpeed; // milliseconds per move
	int stage;
	boolean animating;
	Date lastTime;
	
    public GameDisplay() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
        mBoardIsNull = true;
		mAnimPercentComplete = 0.0;
		mAnimSpeed = 2000.0;
		animating = false;
		mColorMap = new HashMap<Character, Color>();
    }

    public void setBoard(char[][] board) {
        mBoard = board;
        mBoardIsNull = false;
		animating = false;
		setColors();
    }
    
    public void setBoardNull() {
        mBoardIsNull = true;
    }
    
    public void setAnimation(char[][][] boards) {
		mAnimation = boards;
		resetAnimation();
		mBoardIsNull = false;
		animating = false;
		setColors();
    }

	public void resetAnimation() {
		mAnimPercentComplete = 0.0;
		setStage(0);
		animating = false;
	}
	
    public void startAnimation() {
		lastTime = new Date();
		animating = true;
    }
	
	/*
	 * speed is in ms per move
	 *
	 */
	public void setAnimationSpeed(double speed) {
		mAnimSpeed = speed;
	}

    public void pauseAnimation() {
		animating = false;
    }
	
	public boolean isAnimating() {
		return animating;
	}
	
	public void stepAnimation() {
		if (mBoardNext == null)
			animating = false;
			
		if (!animating)
			return;
				
		Date now = new Date();
		long delta = now.getTime() - lastTime.getTime();
		
		if (mAnimPercentComplete >= 1.0) {
			setStage(stage + 1);
		} else {
			mAnimPercentComplete += delta / mAnimSpeed;
		}
		
		lastTime = now;
	}
	
	private void setColors() {
		Random rng = new Random();
		for (int i = 0; i < mBoard.length; i++) {
			for (int j = 0; j < mBoard[i].length; j++) {
				if (mBoard[i][j] == '.') 
					continue;
					
				if (mBoard[i][j] == 'X') {
					mColorMap.put(mBoard[i][j], Color.RED);
				} else if (!mColorMap.containsKey(mBoard[i][j])) {
					Color c;
					c = new Color(rng.nextInt(255),rng.nextInt(255),rng.nextInt(255));
					mColorMap.put(mBoard[i][j], c);
				} 
			}
		}
	}

	private Point getULCornerFor(char[][] board, char piece) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[j][i] == piece)
					return new Point(i, j);
			}
		}
		
		return null;
	}
	
    public void setStage(int s) {
		stage = s;
		mBoard = mAnimation[stage];
		mAnimPercentComplete = 0.0;
		
		if (mAnimation.length > stage + 1) {
			mBoardNext = mAnimation[stage + 1];
			
			for (int i = 0; i < mBoard.length; i++) {
				for (int j = 0; j < mBoard[i].length; j++) {
					char p = mBoard[j][i];
					if (p != '.' && p != mBoardNext[j][i]) {
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

    public int getStage() {
        return stage;
    }

	private void drawPieces(Graphics g, int x, int y, int sqouter) {
		int sqx, sqy = x + 5;
		for (int i = 0; i < mBoard.length; i++) {
			sqx = x + 5;
			for (int j = 0; j < mBoard[i].length; j++) {
				if (mBoard[i][j] != '.') {
					g.setColor(mColorMap.get(mBoard[i][j]));
					int px, py;
					px = sqx - 5;
					py = sqy - 5;
					
					if (mBoard[i][j] == mAnimPiece) {
						int offsetX = j - mAnimOldLoc.x;
						int offsetY = i - mAnimOldLoc.y;
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

