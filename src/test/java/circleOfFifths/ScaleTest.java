/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package circle.of.fifths;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScaleTest {

    @Test void testNoteConstructor() {
      Note[] notes = new Note[]{new Note((byte)0), new Note((byte)2), new Note((byte)4)};
      Scale scale = new Scale(notes);

      assertEquals("(C 0) (D 0) (E 0) ", scale.toString());
      assertEquals(new Note((byte)0), scale.getKey());
      assertEquals(notes, scale.getNotes());
    }

    @Test void testScaleToString() {
        Scale scale = new Scale(new byte[]{0, 2, 4, 5, 7, 9, 11});
        assertEquals("(C 0) (D 0) (E 0) (F 0) (G 0) (A 0) (B 0) ", scale.toString());

        Scale scale2 = new Scale(new byte[]{12, 14, 16, 17, 19, 21, 23});
        assertEquals("(C 1) (D 1) (E 1) (F 1) (G 1) (A 1) (B 1) ", scale2.toString());
    }

    @Test void testScaleToStringArray() {
        Scale scale = new Scale(new byte[]{0, 2, 4, 5, 7, 9, 11});
        assertArrayEquals(new String[]{"(C 0)", "(D 0)", "(E 0)", "(F 0)", "(G 0)", "(A 0)", "(B 0)"}, scale.toStringArray());

        Scale scale2 = new Scale(new byte[]{12, 14, 16, 17, 19, 21, 23});
        assertArrayEquals(new String[]{"(C 1)", "(D 1)", "(E 1)", "(F 1)", "(G 1)", "(A 1)", "(B 1)"}, scale2.toStringArray());
    }

    @Test void testMajorScale() {
        Scale mScale = new MajorScale((byte)0);
        Scale expected = new Scale(new byte[]{0, 2, 4, 5, 7, 9, 11});
        assertTrue(expected.equals(mScale));

        Scale expected2 = new Scale(new byte[]{18, 20, 22, 23, 25, 27, 29});
        MajorScale mScale2 = new MajorScale((byte)18);
        assertTrue(expected2.equals(mScale2));
    }


    @Test void testGetScaleAndString() {
        MajorScale majorScale = new MajorScale((byte)18);

        assertEquals("(F#/Gb 1) (G#/Ab 1) (A#/Bb 1) (B 1) (C#/Db 2) (D#/Eb 2) (F 2) ", majorScale.toString());
    }

    @Test void testGetScaleAndStringArray() {
        MajorScale majorScale = new MajorScale((byte)18);

        assertArrayEquals(new String[]{"(F#/Gb 1)", "(G#/Ab 1)", "(A#/Bb 1)", "(B 1)", "(C#/Db 2)", "(D#/Eb 2)", "(F 2)"}, majorScale.toStringArray());
    }
}