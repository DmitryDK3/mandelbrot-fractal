import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import static java.awt.Color.*;

public class MandelbrotFractal extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final String TITLE = "Mandelbrot fractal";
    public static final int ITERATION_LIMIT = 200;
    private static final double DEFAULT_TOP_LEFT_X = -3.0;
    private static final double DEFAULT_TOP_LEFT_Y = 3.0;
    private static final double DEFAULT_ZOOM = 100.0;

    double currentTopX = DEFAULT_TOP_LEFT_X;
    double currentTopY = DEFAULT_TOP_LEFT_Y;
    double currentZoom = DEFAULT_ZOOM;

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
        
        updateFractal();
    }

    private void addCanvas() {
        canvas = new Canvas();
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        canvas.setBackground(BLACK);
        canvas.setVisible(true);
        addMouseListener(canvas);
        add(canvas, BorderLayout.CENTER);
    }

    public static void main(String[]args){
        new MandelbrotFractal();
    }

    void updateFractal() {
        for (int i = 0; i < MandelbrotFractal.WIDTH; i++) {
            for (int j = 0; j < MandelbrotFractal.HEIGHT; j++){
                double xForComplexSet = getComplexX(i);
                double yForComplexSet = getComplexY(j);
                int iterationCount = getIterationCount(xForComplexSet, yForComplexSet);
                bufferedImage.setRGB(i, j, gerRGBValue(iterationCount));
            }
        }
        canvas.repaint();
    }

    private int gerRGBValue(int iterationCount) {
        int color = PINK.getRGB();
        int mask = 0b00000000000001011001010;
        int shiftOfMask = iterationCount / 13;

        return iterationCount == ITERATION_LIMIT ? BLACK.getRGB() : color | (mask << shiftOfMask);


    }

    private double getComplexY(double y) {
        return y / currentZoom - currentTopY;
    }

    private double getComplexX(double x) {
        return x / currentZoom + currentTopX;
    }

    public int getIterationCount( double constant_real, double constant_imaginary) {
        int iterationCount = 0;
        double z_real = 0.0;
        double z_imaginary = 0.0;

        while ( Math.pow(z_real, 2) + Math.pow(z_imaginary, 2) <= 4) {
            if (iterationCount > ITERATION_LIMIT) {
                return ITERATION_LIMIT;
            }
            double z_real_temp = z_real;
            z_real = Math.pow(z_real, 2) - Math.pow(z_imaginary, 2) + constant_real;
            z_imaginary = 2 * z_real_temp * z_imaginary + constant_imaginary;
            iterationCount++;
        }
        return iterationCount;
    }

    class Canvas extends JPanel implements MouseListener {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(MandelbrotFractal.WIDTH, MandelbrotFractal.HEIGHT);
        }

        @Override
        protected void paintComponent(Graphics g) {

            g.drawImage(bufferedImage, 0, 0, null);
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            double x = (double) e.getX();
            double y = (double) e.getY();
            int button = e.getButton();

            switch (button) {
                case MouseEvent.BUTTON1:
                    System.out.println("pressed left button" + button);
                    adjustZoom(x,y, currentZoom*2);
                    break;
                 case MouseEvent.BUTTON3:

                     System.out.println("pressed right button" + button);
                     adjustZoom(x,y, currentZoom/2);
                     break;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private void adjustZoom(double newX, double newY, double newZoomFactror) {
        currentTopX += newX/currentZoom;
        currentTopY -= newY/currentZoom;
        currentZoom = newZoomFactror;
        currentTopX -= (WIDTH/2)/currentZoom;
        currentTopY += (HEIGHT/2)/currentZoom;
        updateFractal();
    }
}
