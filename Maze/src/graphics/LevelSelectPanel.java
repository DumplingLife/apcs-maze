package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class LevelSelectPanel extends JPanel {
    public LevelSelectPanel(int numLevels, Consumer<Integer> levelClick, ActionListener backClick) {
        setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel inner = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JButton[] buttons = new JButton[numLevels];
        for(int i = 0; i < numLevels; i++) {
            gbc.gridy = i;
            buttons[i] = new JButton("Level " + (i + 1));
            final int iTemp = i;
            buttons[i].addActionListener((e) -> levelClick.accept(iTemp));
            buttons[i].setPreferredSize(new Dimension(300,50));
            inner.add(buttons[i], gbc);
        }

        gbc.gridy = numLevels;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(backClick);
        inner.add(backButton, gbc);
        
        scrollPane.setViewportView(inner);
        
        setPreferredSize(new Dimension(350, 400));
    }
}
