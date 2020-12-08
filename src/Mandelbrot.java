import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mandelbrot extends JPanel implements MouseListener {
    private final double RESOLUTION = 1, RESOLUTION_IMAGE = 10;
    private final float COLOR_INTENSITY = 0.7f;
    private final int MAX_ITERATIONS = 100;
    private Offset offset, offsetUnscaled;
    private double zoom;

    public Mandelbrot(){
        offset = new Offset();
        offsetUnscaled = new Offset();
        zoom = 100;
        this.addMouseListener(this);
        calculateColorAtPoint(1,1);
    }

    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        BufferedImage frame = renderFrame(RESOLUTION);
        g.drawImage(frame, 0, 0, null);
    }

    public BufferedImage renderFrame(double resolution){
        int renderedWidth = (int)(getWidth() * resolution), renderedHeight = (int)(getHeight() * resolution);
        BufferedImage buffer = new BufferedImage(renderedWidth, renderedHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < renderedWidth; x++) {
            for (int y = 0; y < renderedHeight; y++) {
                double coordX = (x - renderedWidth / 2f + offset.getX()) / (zoom*resolution) + offsetUnscaled.getX();
                double coordY = (y - renderedHeight / 2f - offset.getY()) / (zoom*resolution) + offsetUnscaled.getY();
                int color = calculateColorAtPoint(coordX, coordY);
                buffer.setRGB(x, y, color);
            }
        }
        return buffer;
    }

    private int calculateColorAtPoint(double x, double i) {
        ImmutableVector2 startingPoint = new ImmutableVector2(x, i);
        Vector2 point = new Vector2(x, i);
        int iterations = 0;
        while(iterations < MAX_ITERATIONS){
            point = point.imaginarySquare();
            point = point.add(startingPoint);
            if(point.getY() + point.getX() > 16) {
                break;
            }
            iterations++;
        }
        if(iterations == MAX_ITERATIONS){
            return 0x222222;
        }
        return Color.HSBtoRGB((float) iterations / MAX_ITERATIONS, COLOR_INTENSITY, 1f);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        int x = event.getX()-getWidth()/2, y = event.getY()-getHeight()/2;
        offsetUnscaled.setX(offsetUnscaled.getX() + x/zoom);
        offsetUnscaled.setY(offsetUnscaled.getY() + y/zoom);
        zoom *= 2;
        System.out.println(zoom);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    public void safeImage() {
        String path  =chooseOutputImage();
        if(path == null){
            System.out.println("Canceled!");
            return;
        }
        File outputImageFile = new File(path);
        String extension = path.substring(path.length()-3, path.length()).toUpperCase();
        System.out.println("Saving "+extension + " at "+path);
        try {
            ImageIO.write(renderFrame(RESOLUTION_IMAGE), extension, outputImageFile);
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

    public Offset getOffset() {
        return offset;
    }

    public void setOffset(Offset offset) {
        this.offset = offset;
    }

    public Offset getOffsetUnscaled() {
        return offsetUnscaled;
    }

    public void setOffsetUnscaled(Offset offsetUnscaled) {
        this.offsetUnscaled = offsetUnscaled;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
