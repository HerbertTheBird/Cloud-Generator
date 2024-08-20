import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;
public class Main {
	static ArrayList<Ellipse2D> ovals = new ArrayList();
	static int cloudOvals = 500;
	static int ovalWidth = 50;
	static int ovalHeight = 30;
	static int centerX = 225, centerY = 150;
	public static void main(String[] args) {
		//setup window
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setSize(450, 300);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Display display = new Display();
		f.add(display);
		f.addKeyListener(display);
		createCloud();
	}
	public static void createCloud() {
		ovals.clear();
		//create the first oval in the middle
		ovals.add(new Ellipse2D.Double(centerX-ovalWidth/2, centerY-ovalHeight/2, ovalWidth, ovalHeight));
		for(int i = 0; i < cloudOvals; i++) {
			int randomOval = (int)(Math.random()*ovals.size());
			Point2D center = randomPoint(ovals.get(randomOval));
			ovals.add(new Ellipse2D.Double(center.getX()-ovalWidth/2, center.getY()-ovalHeight/2, ovalWidth, ovalHeight));
		}
	}
	public static Point2D randomPoint(Ellipse2D ellipse) {
		//generate random point in circle
		double theta = Math.random()*Math.PI*2;
		double radius = Math.sqrt(Math.random());
		
		double x = Math.cos(theta)*radius;
		double y = Math.sin(theta)*radius;
		
		//map to ellipse
		x *= ellipse.getWidth();
		y *= ellipse.getHeight();
		x += ellipse.getCenterX();
		y += ellipse.getCenterY();
		return new Point2D.Double(x, y);
	}
	public static BufferedImage blur(BufferedImage image, int radius) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage blurredImage = new BufferedImage(width, height, image.getType());

        int sumA, sumR, sumG, sumB, count;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sumA = sumR = sumG = sumB = count = 0;

                for (int ky = -radius; ky <= radius; ky++) {
                    for (int kx = -radius; kx <= radius; kx++) {
                    	if(y+ky < 0 || x+kx < 0 || y+ky >= image.getHeight() || x+kx >= image.getWidth())
                    		continue;
                        int pixel = image.getRGB(x + kx, y + ky);
                        int a = (pixel >> 24) & 0xff;
                        int r = (pixel >> 16) & 0xff;
                        int g = (pixel >> 8) & 0xff;
                        int b = pixel & 0xff;
                        
                        sumA += a;
                        sumR += r;
                        sumG += g;
                        sumB += b;
                        count++;
                    }
                }
                
                sumA /= count;
                sumR /= count;
                sumG /= count;
                sumB /= count;
                
                int avgPixel = (sumA << 24) | (sumR << 16) | (sumG << 8) | sumB;
                blurredImage.setRGB(x, y, avgPixel);
            }
        }

        return blurredImage;
    }
	public static BufferedImage drawCloud() {
		BufferedImage img = new BufferedImage(450, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		
		g2d.setColor(new Color(255, 255, 255, 10));
		for(Ellipse2D oval:ovals) {
			g2d.fillOval((int)oval.getX(), (int)oval.getY(), (int)oval.getWidth(), (int)oval.getHeight());
		}
		g2d.dispose();
		return blur(img, 5);
	}
	public static void paint(Graphics2D g2d) {
		//set black background
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, 800, 800);
		
		g2d.drawImage(drawCloud(), 0, 0, 450, 300, null);
	}
}
