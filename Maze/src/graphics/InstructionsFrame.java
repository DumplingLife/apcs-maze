package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InstructionsFrame extends JFrame {
    public InstructionsFrame() {
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
                "<br />F3: forward 3" +
                "<br />..." +
                "<br />The robot should end outside the maze (navigate it to the exit, then move forward one more time to exit the maze)" +
                "<br />" +
                "<br />Boulders and stamina:" +
                "<br />There may be boulders in the way. Every move has an attached stamina value (0 by default if stamina is not specified). 50 stamina is needed to push 1 boulder. If 2 boulders are in a row, the robot can push both with 100 stamina. The syntax for moving with stamina is:" +
                "<br />F_50: forward with stamina 50" +
                "<br />F2_200: forward 2 with stamina 200 on each movement (this would be enough to push up to 4 boulders in a row)" +
                "<br />The robot starts each level has a different amount of stamina; make sure you don't run out." +
                "<br />" +
                "<br />Macros:" +
                "<br />You can set macros with the following syntax: \"M1 = L F2 R\", then use macros like a regular instruction: \"F M1 M1 R\"" +
                "<br />" +
                "<br />Additional instructions:" +
                "<br />Speed: enter \"set speed 3\". Speed goes from 1-10, 1 being slow and 10 being fast." +
                "<br />Reset: enter \"reset\" to restart the level. You can do this during a level or after you finish." +
                "</html>");
        text.setFont(new Font("Arial", Font.PLAIN, 16));
        add(text, gbc);
        
        setSize(800, 1000);
        setAlwaysOnTop(true);
        setVisible(true);
    }
}
