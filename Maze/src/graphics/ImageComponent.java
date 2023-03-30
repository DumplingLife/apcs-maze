package graphics;

import javax.swing.*;
import java.awt.*;

public class ImageComponent extends JPanel {
    private Image image;
    public ImageComponent(Image image) {
        setImage(image);
    }
    public void setImage(Image image) {
        if(this.image == image) return; //most images don't change every repaint, so do this for performance

        this.image = image;
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.revalidate();
        this.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
