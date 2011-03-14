import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.util.*;

public class GameDisplay extends JPanel {
    boolean mBoardIsNull;
    char[][] mBoard;
    public GameDisplay() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
        mBoardIsNull = true;
    }

    public void setBoard(char[][] board) {
        mBoard = board;
        mBoardIsNull = false;
    }
    
    public void setBoardNull() {
        mBoardIsNull = true;
    }
    
    public void setBoardAnimation(char[][][] board) {
        
    }

    public void startAnimation() {
    }

    public void pauseAnimation() {
    }

    public void setFrame(int f) {
    }

    public int getFrame() {
        return 0;
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
            sqy = x + 5;
            HashMap<Character, Color> colorMap = new HashMap<Character, Color>();
            Random rng = new Random();
            for (int i = 0; i < mBoard.length; i++) {
                sqx = x + 5;
                for (int j = 0; j < mBoard[i].length; j++) {
                    if (mBoard[i][j] != '.') {
                        Color c;
                        if (mBoard[i][j] == 'X') {
                            c = Color.RED;
                        } else if (!colorMap.containsKey(mBoard[i][j])) {
                            c = new Color(rng.nextInt(255),rng.nextInt(255),rng.nextInt(255));
                            colorMap.put(mBoard[i][j], c);
                        } else {
                            c = colorMap.get(mBoard[i][j]);
                        }

                        g.setColor(c);
                        g.fillRect(sqx - 5, sqy - 5, sqouter, sqouter);
                    }
                    sqx += sqouter;
                }
                sqy += sqouter;
            }
        }
    }

}

