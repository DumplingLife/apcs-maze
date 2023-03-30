package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InstructionsPanel extends JPanel {
    public InstructionsPanel(ActionListener backClick) {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 30, 0, 30);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel text = new JLabel("<html>" +
                "Your goal is to help the robot navigate out of the maze. Enter a series of space-separated commands with the following syntax:" +
                "<br />F: forward 1" +
                "<br />L: turn left (i.e. turn counterclockwise)" +
                "<br />R: turn right (i.e. turn clockwise)" +
                "<br />F2: forward 2" +
                "<br />There may be boulders in the way. Every move has an attached stamina value (0 by default). 50 stamina is needed to push 1 boulder. If 2 boulders are adjacent, the robot cna push both with 100 stamina. You are in charge of calculating when stamina is needed and how much should be used." +
                "<br />The syntax for moving with stamina is:" +
                "<br />F2_50: forward 2 with stamina 50 on both movements" +
                "</html>");
        add(text, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;

        JButton backButton = new JButton("Back");
        backButton.addActionListener(backClick);
        add(backButton, gbc);
        setPreferredSize(new Dimension(640, 480 ));
    }
}
