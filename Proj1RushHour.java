import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

public class Proj1RushHour {

    JPanel mainpanel;
    GameDisplay solutionDisplay;
    JButton animateButton;
    JComboBox boardCombo;

    public void initBoardCombo() {
    }

    public Proj1RushHour() {
        mainpanel = new JPanel();
        mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.LINE_AXIS));
        mainpanel.add(new GameDisplay());
        mainpanel.add(Box.createRigidArea(new Dimension(5,0)));

        Box rightPane = Box.createVerticalBox();
        rightPane.add(new JLabel("Welcome to the CSC 540 team of:  "));
        rightPane.add(new JLabel("Brian Hrebec"));
        rightPane.add(new JLabel("Thomas Shaddox"));
        rightPane.add(Box.createRigidArea(new Dimension(0, 15)));

        String[] cb = {"Select a board"};
        boardCombo = new JComboBox(cb);
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.add(boardCombo, BorderLayout.CENTER);
        wrapper.setPreferredSize(new Dimension(200, 20));

        rightPane.add(wrapper);
        rightPane.add(new JButton(new AbstractAction("Solve!") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainpanel, 
                "This will eventually start the solving process");
            }
        }));
        rightPane.add(new JLabel("Max Tree Depth: "));
        rightPane.add(new JLabel("States Generated: "));
        rightPane.add(new JLabel("Other Fun info: "));
        animateButton = new JButton(new AbstractAction("Replay Animation") {
            public void actionPerformed(ActionEvent e) {
            }
        });
        animateButton.setEnabled(false);

        rightPane.add(animateButton);

        rightPane.add(Box.createVerticalGlue());

        mainpanel.add(rightPane);
        mainpanel.add(Box.createRigidArea(new Dimension(5,0)));
    }
    public static void createGui() {
        Proj1RushHour proj = new Proj1RushHour();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
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
