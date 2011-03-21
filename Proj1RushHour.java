import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

public class Proj1RushHour {
final static private char testAnim[][][] = {
			// RushHour Railroad card 1, Junior, 5 move solution
            { { '.', '.', '.', '.', 'O', 'O', 'O'},
              { '.', '.', '.', '.', 'B', 'B', 'C'},
              { '.', '.', 'X', 'X', 'B', 'B', 'C'},
              { '.', '.', '.', '.', 'P', 'P', 'P'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'} },
			{ { 'O', 'O', 'O', '.', '.', '.', '.'},
              { '.', '.', '.', '.', 'B', 'B', 'C'},
              { '.', '.', 'X', 'X', 'B', 'B', 'C'},
              { '.', '.', '.', '.', 'P', 'P', 'P'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'} },
			{ { 'O', 'O', 'O', '.', '.', '.', '.'},
              { '.', '.', '.', '.', 'B', 'B', 'C'},
              { '.', '.', 'X', 'X', 'B', 'B', 'C'},
              { '.', 'P', 'P', 'P', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'} },
			{ { 'O', 'O', 'O', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', 'C'},
              { '.', '.', 'X', 'X', '.', '.', 'C'},
              { '.', 'P', 'P', 'P', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', 'B', 'B', '.'},
              { '.', '.', '.', '.', 'B', 'B', '.'} },
			{ { 'O', 'O', 'O', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', 'X', 'X', '.', '.', 'C'},
              { '.', 'P', 'P', 'P', '.', '.', 'C'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', 'B', 'B', '.'},
              { '.', '.', '.', '.', 'B', 'B', '.'} },
			{ { 'O', 'O', 'O', '.', '.', '.', '.'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { 'X', 'X', '.', '.', '.', '.', 'C'},
              { '.', 'P', 'P', 'P', '.', '.', 'C'},
              { '.', '.', '.', '.', '.', '.', '.'},
              { '.', '.', '.', '.', 'B', 'B', '.'},
              { '.', '.', '.', '.', 'B', 'B', '.'} }
			  };
    JPanel mainpanel;
    GameDisplay solutionDisplay;
    JButton animateButton;
    JComboBox boardCombo;
	Timer animTimer;

    public String[] initBoardCombo() {
        String[] ret = new String[1 + GameState.initialGameStateCount()];
        ret[0] = "Select a board...";
        for (int i = 1; i <= GameState.initialGameStateCount(); i++) {
            ret[i] = Integer.toString(i);
        }

        return ret;
    }

    public Proj1RushHour() {
		animTimer = new Timer(17, new ActionListener() { // ~60 fps
			public void actionPerformed(ActionEvent e) {
				solutionDisplay.repaint();
				if (!solutionDisplay.isAnimating())
					animTimer.stop();
			}
		}); 
		
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        solutionDisplay = new GameDisplay();
        mainpanel.add(solutionDisplay, BorderLayout.CENTER);

        Box credits = Box.createVerticalBox();
        credits.add(new JLabel("CSC 540 team:  "));
        credits.add(new JLabel("Brian Hrebec"));
        credits.add(new JLabel("Thomas Shaddox"));
		credits.add(new JLabel("Dave Robinson"));
        credits.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel select = new JPanel();
        select.setLayout(new BorderLayout());
        String[] comboVals = initBoardCombo();
        boardCombo = new JComboBox(comboVals);
        boardCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String i = boardCombo.getSelectedItem().toString();
                if (i == "Select a board...") {
                    solutionDisplay.setBoardNull();
                } else {
                    solutionDisplay.setBoard(GameState.getInitialGame(Integer.parseInt(i)));
                }
                solutionDisplay.repaint();
            }
        });
        select.add(boardCombo, BorderLayout.NORTH);
        select.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        select.add(new JButton(new AbstractAction("Solve!") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainpanel, 
                "This will eventually start the solving process");
            }
        }), BorderLayout.SOUTH);
        select.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        Box info = Box.createVerticalBox();
        info.add(new JLabel("Moves in Solution: "));
        info.add(new JLabel("Max Tree Depth: "));
        info.add(new JLabel("States Generated: "));
        info.add(new JLabel("Other Fun info: "));
        info.add(Box.createVerticalStrut(10));
        animateButton = new JButton(new AbstractAction("Test Animation") {
            public void actionPerformed(ActionEvent e) {
				solutionDisplay.setAnimation(testAnim);
                solutionDisplay.startAnimation();
				animTimer.start();
            }
        });
        //animateButton.setEnabled(false);
		
        info.add(animateButton);
        info.setBorder(BorderFactory.createEmptyBorder(10,15,20,10));

        JPanel options = new JPanel();
        options.setLayout(new BorderLayout());
        options.add(select, BorderLayout.NORTH);
        options.add(info, BorderLayout.SOUTH);

        JPanel sidepanel = new JPanel();
        sidepanel.setLayout(new BorderLayout());
        sidepanel.add(credits, BorderLayout.NORTH);
        sidepanel.add(Box.createHorizontalStrut(200), BorderLayout.CENTER);
        sidepanel.add(options, BorderLayout.SOUTH);

        mainpanel.add(sidepanel, BorderLayout.EAST);
    }
    public static void createGui() {

		
        Proj1RushHour proj = new Proj1RushHour();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setContentPane(proj.mainpanel);
        
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGui();
            }
        });
    }


}
