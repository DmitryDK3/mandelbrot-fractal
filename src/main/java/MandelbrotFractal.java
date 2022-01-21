import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotFractal extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final String TITLE = "Mandelbrot fractal";

    Canvas canvas;
    BufferedImage bufferedImage;

    {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        addCanvas();
        setVisible(true);
    }

    private void addCanvas() {
        canvas = new Canvas();
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        canvas.setBackground(Color.BLACK);
        canvas.setVisible(true);
        add(canvas, BorderLayout.CENTER);
    }

    public static void main(String[]args){
        new MandelbrotFractal();
    }

    class Canvas extends JPanel{
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH, HEIGHT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(bufferedImage, 0, 0, null);
        }
    }
}
