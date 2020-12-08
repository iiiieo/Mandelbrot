import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements ActionListener, KeyListener {
    private Mandelbrot panel;
    private Timer timer;
    private double zoom = 0;
    private double speedY, speedX;
    private final double SPEED = 50, ZOOM_SPEED = 80;

    public KeyHandler(Mandelbrot panel) {
        this.panel = panel;
        this.timer = new Timer(3, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double offsetX = panel.getOffset().getX(), offsetY = panel.getOffset().getY();
        if (zoom != 0) {
            panel.setZoom(panel.getZoom() + zoom);
            if (offsetX > 0) {
                offsetX += ZOOM_SPEED / 3;
            } else if (offsetX < 0) {
                offsetX -= ZOOM_SPEED / 3;
            }
            if (offsetY > 0) {
                offsetY += ZOOM_SPEED / 3;
            } else if (offsetY < 0) {
                offsetY -= ZOOM_SPEED / 3;
            }
        }
        offsetY += speedY;
        offsetX += speedX;

        if (speedX != 0 || speedY != 0 || zoom != 0) {
            panel.setOffset(new Offset(offsetX, offsetY));
            panel.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            zoom = ZOOM_SPEED;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            zoom = -ZOOM_SPEED;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            speedY = SPEED;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            speedX = -SPEED;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if((e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
                panel.safeImage();
            }else{
                speedY = -SPEED;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            speedX = SPEED;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            zoom = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            zoom = 0;
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
            speedX = 0;
        }
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
            speedY = 0;
        }
    }

}
