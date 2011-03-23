import javax.swing.*;
import javax.swing.event.*;
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

    /**
     * Initialize the board selection combo box
     */
    public String[] initBoardCombo() {
        String[] ret = new String[1 + GameState.initialGameStateCount()];
        ret[0] = "Select a board...";
        for (int i = 1; i <= GameState.initialGameStateCount(); i++) {
            ret[i] = Integer.toString(i);
        }

        return ret;
    }

    /**
     *
     */
    public void updateInfo(int totalstates, int maxdepth) {
        maxTreeDepth.setText(Integer.toString(maxdepth));
        statesGenerated.setText(Integer.toString(totalstates));
        maxTreeDepth.repaint();
        statesGenerated.repaint();
    }

    /**
     * Constructor for the main gui panel.
     */
    public Proj1RushHour() {

        // Create the animation timer
		animTimer = new Timer(17, new ActionListener() { // ~60 fps
			public void actionPerformed(ActionEvent e) {
				solutionDisplay.repaint();
                
				if (solutionDisplay.isAnimating()) {
                    stateSlider.setValue(solutionDisplay.getState());
                    stateSpinner.setValue(solutionDisplay.getState());
                } else {
					animTimer.stop();
                    animateButton.setText("Play Animation");
                }
			}
		}); 
		
        // Create the main panel and layout
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        solutionDisplay = new GameDisplay();
        mainpanel.add(solutionDisplay, BorderLayout.CENTER);

        // Create the sidebar credit box
        Box credits = Box.createVerticalBox();
        credits.add(new JLabel("CSC 540 team:  "));
        credits.add(new JLabel("Brian Hrebec"));
        credits.add(new JLabel("Thomas Shaddox"));
		credits.add(new JLabel("Dave Robinson"));
        credits.add(Box.createVerticalStrut(30));
		credits.add(new JLabel("The protagonist"));
		credits.add(new JLabel("vehicle is always red."));
        credits.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Create the sidebar board selection box
        JPanel select = new JPanel();
        select.setLayout(new BorderLayout());
        String[] comboVals = initBoardCombo();
        boardCombo = new JComboBox(comboVals);

        // ActionListener for board selection combo box
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

                movesInSolution.setText("  ");
                maxTreeDepth.setText("  ");
                statesGenerated.setText("  ");
                animateButton.setEnabled(false);
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

        // ActionListener for "Solve!" button
        final Proj1RushHour prh = this;
        select.add(new JButton(new AbstractAction("Solve!") {
            public void actionPerformed(ActionEvent e) {
                if (currentBoard == null) {
                    JOptionPane.showMessageDialog(mainpanel, 
                        "The imaginary board MUST NOT BE SOLVED!");
                    return;
                }

                List<Board> solution = currentBoard.solve(prh);
                animateButton.setEnabled(true);
				solutionDisplay.setAnimation(solution);

                if (solution.size() > 0) {
                    stateSlider.setMaximum(solution.size() - 1);
                    stateSpinner.setMaximum(solution.size() - 1);
                } else {
                    stateSlider.setMaximum(0);
                    stateSpinner.setMaximum(0);
                }

                movesInSolution.setText(Integer.toString(solution.size() - 1));

                solutionDisplay.startAnimation();
                animTimer.start();
                animateButton.setText("Pause Animation");
            }
        }), BorderLayout.SOUTH);
        select.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Create the solution information and animation control sidebar area
        JPanel info = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Info boxes
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        info.add(new JLabel("Moves in Solution: "), c);
        c.gridx = 2;
        movesInSolution = new JLabel("  ");
        info.add(movesInSolution, c);

        c.gridx = 0;
        c.gridy = 1;
        info.add(new JLabel("Max Tree Depth: "), c);
        c.gridx = 2;
        maxTreeDepth = new JLabel("  ");
        info.add(maxTreeDepth, c);

        c.gridx = 0;
        c.gridy = 2;
        info.add(new JLabel("States Generated: "), c);
        c.gridx = 2;
        statesGenerated = new JLabel("  ");
        info.add(statesGenerated, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = GridBagConstraints.REMAINDER;
        info.add(Box.createVerticalStrut(10), c);

        // Animation controls
        c.gridy = 4;
        animateButton = new JButton(new AbstractAction("Play Animation") {
            public void actionPerformed(ActionEvent e) {
                if (solutionDisplay.isAnimating()) {
                    solutionDisplay.pauseAnimation();
                    animTimer.stop();
                    animateButton.setText("Play Animation");
                } else {
                    solutionDisplay.startAnimation();
                    animTimer.start();
                    animateButton.setText("Pause Animation");
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

        // Speed Spinner
        c.gridx = 1;
        c.weightx = .5; 
        mspmSpeed = new SpinnerNumberModel(2000, 100, 4000, 100);
        mspmSpeed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                solutionDisplay.setAnimationSpeed(
                    mspmSpeed.getNumber().intValue());
            }
        });
        info.add(new JSpinner(mspmSpeed), c);

        // State Spinner
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        c.weightx = 1;
        info.add(new JLabel("Current Move: "), c);
        c.gridx = 1;
        c.weightx = .5; 
        stateSpinner = new SpinnerNumberModel(0, 0, 0, 1);
        stateSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int val = stateSpinner.getNumber().intValue();
                if (solutionDisplay.getState() != val) {
                    solutionDisplay.setState(val);
                    stateSlider.setValue(val);
                    solutionDisplay.repaint();
                }
            }
        });
        info.add(new JSpinner(stateSpinner), c);

        // State Slider
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = GridBagConstraints.REMAINDER;
        stateSlider = new JSlider(JSlider.HORIZONTAL, 0, 0, 0);
        stateSlider.setPaintTicks(true);
        stateSlider.setMajorTickSpacing(5);
        stateSlider.setMinorTickSpacing(1);
        stateSlider.setSnapToTicks(true);
        stateSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int val = stateSlider.getValue();
                if (solutionDisplay.getState() != val) {
                    solutionDisplay.setState(val);
                    stateSpinner.setValue(val);
                    solutionDisplay.repaint();
                }
            }
        });
        info.add(stateSlider, c);

        info.setBorder(BorderFactory.createEmptyBorder(10,15,20,10));

        // Put the gui together
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

    /** 
     * Create and display the frame.
     */
    public static void createGui() {
        Proj1RushHour proj = new Proj1RushHour(); // create the main panel

        // Application Frame
        JFrame frame = new JFrame("Rush Hour - CSC540 Project 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setContentPane(proj.mainpanel); // Add the panel to the frame
        
        frame.pack();
        frame.setVisible(true);
    }

    /** 
     * Entry Point. Basic swing loop.
     */
    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGui();
            }
        });
    }


}
