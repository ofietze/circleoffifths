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
    private int padding = 50;
    private BufferedImage graphicsContext;
    private JPanel contentPanel = new JPanel();
    private JLabel contextRender;
    private Stroke dashedStroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 2f, new float[] {3f, 3f}, 0f);
    private Stroke solidStroke = new BasicStroke(3.0f);
    private RenderingHints antialiasing;
    private Random random = new Random();

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

        //Set up the font to print on the circles
        Font font = g2d.getFont();
        font = font.deriveFont(Font.BOLD, 20f);
        g2d.setFont(font);

        FontMetrics fontMetrics = g2d.getFontMetrics();

        //clear the background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, graphicsContext.getWidth(), graphicsContext.getHeight());

        //set up the large circle
        Point2D largeCircleCenter = new Point2D.Double((double)width / 2 + padding, (double)height / 2 + padding);
        double largeCircleRadius = (double)width / 2;
        Ellipse2D largeCircle = getCircleByCenter(largeCircleCenter, largeCircleRadius);

        //here we build the small circle
        Point2D smallCircleCenter = new Point2D.Double();
        double smallCircleRadius = 15;
        //we need to make certain it is confined inside the larger circle
        //so we choose the following values carefully

        //we want to go a random direction from the circle, so chose an
        //angle randomly in any direction
        double smallCenterVectorAngle = 30.0d;
        //and we want to be a random distance from the center of the large circle, but
        //we limit the distance based on the radius of the small circle to prevent it
        //from appearing outside the large circle
        double smallCenterVectorLength = random.nextDouble() * (largeCircleRadius - smallCircleRadius);
        Line2D vectorToSmallCenter = getVector(largeCircleCenter, smallCenterVectorAngle, smallCenterVectorLength);
        //the resulting end point of the vector is a random distance from the center of the large circle
        //in a random direction, and guaranteed to not place the small circle outside the large
        smallCircleCenter.setLocation(vectorToSmallCenter.getP2());
        Ellipse2D smallCircle = getCircleByCenter(largeCircleCenter, smallCircleRadius);

        //before we draw any of the circles or lines, set the clip to the large circle
        //to prevent drawing outside our boundaries
        g2d.setClip(largeCircle);



        //chose a random angle for the line through the center of the small circle
        double angle = 30.0d;
        //we create two lines that start at the center and go out at the angle in
        //opposite directions. We use 2*largeCircleRadius to make certain they
        //will be large enough to fill the circle, and the clip we set prevent stray
        //marks outside the big circle
        Line2D centerLine1 = getVector(largeCircleCenter, angle, largeCircleRadius * 2);
        Line2D centerLine2 = getVector(largeCircleCenter, angle, -largeCircleRadius * 2);

        //fill the small circle with blue
        g2d.setColor(Color.BLUE);
        // g2d.fill(smallCircle);

        //draw the two center lines lines
        g2d.setStroke(dashedStroke);
        g2d.draw(centerLine1);
        g2d.draw(centerLine2);

        //create and draw the black offset vector
        Line2D normalVector = getVector(smallCircleCenter, angle + 90, largeCircleRadius * 2);
        g2d.setColor(Color.black);
        // g2d.draw(normalVector);

        //draw the offset vectors
        g2d.setColor(new Color(0, 200, 0));
        // g2d.draw(sightVector1);
        // g2d.draw(sightVector2);


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
