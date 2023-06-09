package graphics;

import java.awt.*;
import java.awt.image.*;

public class ImageUtils {
	public static BufferedImage addBackground(BufferedImage image, int dims) {
		BufferedImage newImage = new BufferedImage(dims, dims, image.getType());
		Graphics2D g2d = newImage.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, dims, dims);
        g2d.drawImage(image,
        		(int) (dims*0.15), (int) (dims*0.15), (int) (dims*0.85), (int) (dims*0.85),
        		0, 0, image.getWidth(), image.getHeight(),
        		null);
        g2d.dispose();
        
        return newImage;
	}
}
