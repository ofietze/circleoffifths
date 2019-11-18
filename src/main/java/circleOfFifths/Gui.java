package circle.of.fifths;

// see https://stackoverflow.com/a/22573536
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Gui extends JFrame {

    private static final long serialVersionUID = 1L;
    private int width = 500;
    private int height = 500;
    private int padding = 10;
    private BufferedImage graphicsContext;
    private JPanel contentPanel = new JPanel();
    private JLabel contextRender;
    private Stroke solidStroke = new BasicStroke(3.0f);
    private RenderingHints antialiasing;

    private final int NUM_OF_NOTES = 12;
    private final Point2D centerPoint = new Point2D.Double((double)width / 2 + padding, (double)height / 2 + padding);
    private final double CIRCLE_RADIUS = (double)width / 2;

    public static void main(String[] args) {
        //you should always use the SwingUtilities.invodeLater() method
        //to perform actions on swing elements to make certain everything
        //is happening on the correct swing thread
        Runnable swingStarter = new Runnable()
        {
            @Override
            public void run(){
                new Gui();
            }
        };
        SwingUtilities.invokeLater(swingStarter);
    }

    public Gui(){
        antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphicsContext = new BufferedImage(width + (2 * padding), width + (2 * padding), BufferedImage.TYPE_INT_RGB);
        contextRender = new JLabel(new ImageIcon(graphicsContext));

        contentPanel.add(contextRender);
        contentPanel.setSize(width + padding * 2, height + padding * 2);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(contentPanel);
        //take advantage of auto-sizing the window based on the size of its contents
        this.pack();
        this.setLocationRelativeTo(null);
        this.paint();
        setVisible(true);
    }

    public void paint() {
        Graphics2D g2d = graphicsContext.createGraphics();
        g2d.setRenderingHints(antialiasing);

        //clear the background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, graphicsContext.getWidth(), graphicsContext.getHeight());

        //set up the large circle
        Point2D centerPoint = new Point2D.Double((double)width / 2 + padding, (double)height / 2 + padding);
        Ellipse2D largeCircle = getCircleByCenter(centerPoint, CIRCLE_RADIUS);

        //before we draw any of the circles or lines, set the clip to the large circle
        //to prevent drawing outside our boundaries
        g2d.setClip(largeCircle);

        //chose a random angle for the line through the center of the small circle
        double angle = 15.0d;

        g2d.setColor(Color.BLUE);
        g2d.setStroke(solidStroke);

        // Draw the lines for each key
        Line2D[] centerLines = new Line2D[NUM_OF_NOTES];
        for (Line2D line : centerLines) {
          line = getVector(centerPoint, angle, CIRCLE_RADIUS * 2);
          angle += 30;
          g2d.draw(line);
        }

        g2d.setColor(Color.black);

        drawKeyStrings(g2d, 0);
        //we save the big circle for last, to cover up any stray marks under the stroke
        //of its perimeter. We also set the clip back to null to prevent the large circle
        //itselft from accidentally getting clipped
        g2d.setClip(null);
        g2d.setStroke(solidStroke);
        g2d.setColor(Color.BLACK);
        g2d.draw(largeCircle);

        g2d.dispose();
        //force the container for the context to re-paint itself
        contextRender.repaint();

    }

    // Draws the keys in the circle of fifths starting with "key"
    private void drawKeyStrings(Graphics2D g2d, int key) {
      //Set up the font to print on the circles
      Font font = g2d.getFont();
      font = font.deriveFont(Font.BOLD, 20f);
      g2d.setFont(font);

      FontMetrics fontMetrics = g2d.getFontMetrics();

      // Get circle of fifths from App
      App app = new App();
      String[] circleOfFifths = app.scaleToStringArray(app.getCircleOfFifths(key));

      double textX = 0;
      double textY = 0;
      double angle = 540;
      for (int i = 0; i < circleOfFifths.length; i++) {
        Rectangle2D itemStringBounds = fontMetrics.getStringBounds(circleOfFifths[i], g2d);

        // To go around the circle we draw a vector from the center to our current cell and use the end point to draw the text
        Line2D vector = getVector(centerPoint, angle - 30 * i, CIRCLE_RADIUS-CIRCLE_RADIUS/5);
        textX = vector.getP2().getX() - (itemStringBounds.getWidth() / 2);
        textY = vector.getP2().getY() + (itemStringBounds.getHeight() / 2);

        g2d.drawString(circleOfFifths[i], (float)textX, (float)textY);
      }
    }

    private static Line2D getVector(Point2D start, double degrees, double length){
        //we just multiply the unit vector in the direction we want by the length
        //we want to get a vector of correct direction and magnitute
        double endX = start.getX() + (length * Math.sin(Math.PI * degrees/ 180.0d));
        double endY = start.getY() + (length * Math.cos(Math.PI * degrees/ 180.0d));
        Point2D end = new Point2D.Double(endX, endY);
        Line2D vector = new Line2D.Double(start, end);
        return vector;
    }

    private static Ellipse2D getCircleByCenter(Point2D center, double radius)
    {
        Ellipse2D.Double myCircle = new Ellipse2D.Double(center.getX() - radius, center.getY() - radius, 2 * radius, 2 * radius);
        return myCircle;
    }

}
