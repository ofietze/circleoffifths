/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package circle.of.fifths;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CircleTest {
    @Test void testCircleOfFifths() {
        Circle c = new Circle((byte)0);
        Scale expectedScale = new Scale (new byte[]{0, 7, 2, 9, 4, 11, 6, 1, 8, 3, 10, 5});
        assertEquals(expectedScale, c.getCircleOfFifths());

        Circle c2 = new Circle((byte)6);
        Scale expectedScale2 = new Scale(new byte[]{6, 1, 8, 3, 10, 5, 0, 7, 2, 9, 4, 11});
        assertEquals(expectedScale2, c2.getCircleOfFifths());
    }
}
