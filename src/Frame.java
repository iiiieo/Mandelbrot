import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame {
    final int width = 800, height = 600;
    public Frame(){
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Mandelbrot mandelbrotPanel = new Mandelbrot();
        add(mandelbrotPanel);
        setTitle("Mandelbrot Set");
        addKeyListener(new KeyHandler(mandelbrotPanel));
        setVisible(true);
    }

}
