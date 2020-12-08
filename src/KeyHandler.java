import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements ActionListener, KeyListener {
    private MandelbrotPanel panel;
    private Timer timer;
    private double zoom = 0, zoomStep = 80, speed = 50;
    private double speedY, speedX;

    public KeyHandler(MandelbrotPanel panel) {
        this.panel = panel;
        this.timer = new Timer(3, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double offsetX = panel.getOffsetX(), offsetY = panel.getOffsetY();
        if (zoom != 0) {
            panel.setZoom(panel.getZoom() + zoom);
            if (offsetX > 0) {
                offsetX += zoomStep / 3;
            } else if (offsetX < 0) {
                offsetX -= zoomStep / 3;
            }
            if (offsetY > 0) {
                offsetY += zoomStep / 3;
            } else if (offsetY < 0) {
                offsetY -= zoomStep / 3;
            }
        }
        offsetY += speedY;
        offsetX += speedX;

        if (speedX != 0 || speedY != 0 || zoom != 0) {
            panel.setOffsetY(offsetY);
            panel.setOffsetX(offsetX);
            panel.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            zoom = zoomStep;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            zoom = -zoomStep;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            speedY = speed;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            speedX = -speed;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if((e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
                panel.safeImage();
            }else{
                speedY = -speed;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            speedX = speed;
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
