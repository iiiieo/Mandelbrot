import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MandelbrotPanel extends JPanel implements MouseListener {
    private int maxIterations = 100;
    private double zoom = 100;
    private Point offset = new Point(0,0), offsetUnscaled = new Point(0,0);
    public MandelbrotPanel(){
        addMouseListener(this);
    }

    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.drawImage(renderImage(1), 0, 0, null);
    }

    public BufferedImage renderImage(double resolution){
        int width = (int)(getWidth() * resolution), height = (int)(getHeight() * resolution);
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double calcX = (x - width / 2f + offset.getX()) / zoom + offsetUnscaled.getX();
                double calcY = (y - height / 2f - offset.getY()) / zoom + offsetUnscaled.getY();
                int color = calculateColor(calcX, calcY);
                buffer.setRGB(x, y, color);
            }
        }
        return buffer;
    }

    public void safeImage() {
        String path  =chooseOutputImage();
        if(path == null){
            System.out.println("Canceled!");
            return;
        }
        File outputfile = new File(path);
        try {
            ImageIO.write(renderImage(5), "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Exported!");
    }
    public static String chooseOutputImage(){
        FileDialog fd = new FileDialog(new JFrame(), "Save", FileDialog.SAVE);
        fd.setMultipleMode(false);
        fd.setFile("mandelbrot.png");
        fd.setVisible(true);
        return fd.getFiles()[0].getAbsolutePath();
    }


    private int calculateColor(double x, double y) {
        double cx = x;
        double cy = y;
        int i = 0;
        for (; i < maxIterations; i++) {
            double xx = x * x - y * y;
            double yy = 2 * x * y;
            x = xx+cx;
            y = yy+cy;
            if (x + y > 16) {
                break;
            }
        }
        if (i == maxIterations) {
            return 0x000000;
        }
        return Color.HSBtoRGB((float) i / maxIterations, 0.7f, 1f);
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public double getOffsetX() {
        return offset.getX();
    }

    public void setOffsetX(double offsetX) {
        this.offset.setX(offsetX);
    }

    public double getOffsetY() {
        return offset.getY();
    }

    public void setOffsetY(double offsetY) {
        this.offset.setY(offsetY);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX()-getWidth()/2, y = e.getY()-getHeight()/2;
        offsetUnscaled.setX(offsetUnscaled.getX() + x/zoom);
        offsetUnscaled.setY(offsetUnscaled.getY() + y/zoom);
        zoom *= 2;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
