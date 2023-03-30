package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    public StartPanel(ActionListener startClick, ActionListener instructionsClick) {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton startButton = new JButton("Start");
        startButton.addActionListener(startClick);
        startButton.setPreferredSize(new Dimension(150, 50));
        add(startButton, gbc);

        gbc.gridy = 1;

        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(instructionsClick);
        instructionsButton.setPreferredSize(new Dimension(150, 50));
        add(instructionsButton, gbc);

        setPreferredSize(new Dimension(320, 160 ));
    }
}
