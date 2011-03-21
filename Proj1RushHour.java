import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.List;

public class Proj1RushHour {
    JPanel mainpanel;
    GameDisplay solutionDisplay;
    JButton animateButton;
    JComboBox boardCombo;
	Timer animTimer;
    Board currentBoard;

    JLabel movesInSolution;
    JLabel maxTreeDepth;
    JLabel statesGenerated;

    SpinnerNumberModel mspmSpeed;

    JSlider stateSlider;
    SpinnerNumberModel stateSpinner;

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
                
				if (solutionDisplay.isAnimating()) {
                    stateSlider.setValue(solutionDisplay.getState());
                    stateSpinner.setValue(solutionDisplay.getState());
                } else {
					animTimer.stop();
                }
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
                    currentBoard = null;
                } else {
                    currentBoard = GameState.getInitialGame(Integer.parseInt(i));
                    solutionDisplay.setBoard(currentBoard);
                }

                movesInSolution.setText("");
                maxTreeDepth.setText("");
                statesGenerated.setText("");
                animateButton.setEnabled(true);
                solutionDisplay.setAnimation(null);
                stateSlider.setValue(0);
                stateSlider.setMaximum(0);
                stateSpinner.setValue(0);
                stateSpinner.setMaximum(0);

                solutionDisplay.repaint();
            }
        });
        select.add(boardCombo, BorderLayout.NORTH);
        select.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        select.add(new JButton(new AbstractAction("Solve!") {
            public void actionPerformed(ActionEvent e) {
                List<Board> solution = currentBoard.solve();
                animateButton.setEnabled(true);
				solutionDisplay.setAnimation(solution);
                stateSlider.setMaximum(solution.size() - 1);
                stateSpinner.setMaximum(solution.size() - 1);
                movesInSolution.setText(Integer.toString(solution.size()));
                maxTreeDepth.setText("??");
                statesGenerated.setText("??");
            }
        }), BorderLayout.SOUTH);
        select.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel info = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        info.add(new JLabel("Moves in Solution: "), c);
        c.gridx = 2;
        movesInSolution = new JLabel("");
        info.add(movesInSolution, c);

        c.gridx = 0;
        c.gridy = 1;
        info.add(new JLabel("Max Tree Depth: "), c);
        c.gridx = 2;
        maxTreeDepth = new JLabel("");
        info.add(maxTreeDepth, c);

        c.gridx = 0;
        c.gridy = 2;
        info.add(new JLabel("States Generated: "), c);
        c.gridx = 2;
        statesGenerated = new JLabel("");
        info.add(statesGenerated, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = GridBagConstraints.REMAINDER;
        info.add(Box.createVerticalStrut(10), c);

        c.gridy = 4;
        animateButton = new JButton(new AbstractAction("Toggle Animation") {
            public void actionPerformed(ActionEvent e) {
                if (solutionDisplay.isAnimating()) {
                    solutionDisplay.pauseAnimation();
                    animTimer.stop();
                } else {
                    solutionDisplay.startAnimation();
                    animTimer.start();
                }
            }
        });

        animateButton.setEnabled(false);
        info.add(animateButton, c);

        c.gridx = 0;
        c.gridy = 5;
        info.add(Box.createVerticalStrut(10), c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.weightx = 1;
        info.add(new JLabel("milliseconds/move: "), c);

        c.gridx = 1;
        c.weightx = .5; 
        mspmSpeed = new SpinnerNumberModel(2000, 100, 4000, 100);
        info.add(new JSpinner(mspmSpeed), c);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = GridBagConstraints.REMAINDER;
        stateSlider = new JSlider(JSlider.HORIZONTAL, 0, 0, 0);
        stateSlider.setPaintTicks(true);
        stateSlider.setMajorTickSpacing(5);
        stateSlider.setMinorTickSpacing(1);
        stateSlider.setSnapToTicks(true);
        info.add(stateSlider, c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.weightx = 1;
        info.add(new JLabel("Solution State: "), c);
        c.gridx = 1;
        c.weightx = .5; 
        stateSpinner = new SpinnerNumberModel(0, 0, 0, 1);
        info.add(new JSpinner(stateSpinner), c);

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
