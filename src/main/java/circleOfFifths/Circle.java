package circle.of.fifths;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.IOException;


public class Circle {

  private static final byte NUM_OF_NOTES = 12;
  private Scale circleOfFifths;

  public Circle(byte key) {
    Note[] scale = new Note[NUM_OF_NOTES];
    byte note = key;
    byte fifth = 5;

    for (int i = 0; i < scale.length; i++) {
      scale[i] = new Note(note);

      note -= fifth;
      if (note < 0) {
        note = (byte) (NUM_OF_NOTES + note);
      }
    }
    circleOfFifths = new Scale(scale);
  }

  public Scale getCircleOfFifths() {
    return circleOfFifths;
  }

  public String toString() {
    return circleOfFifths.toString();
  }

  public String[] toStringArray() {
    return circleOfFifths.toStringArray();
  }

  public String askForInput(String message) throws NullPointerException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(message);

        String s = null;
        try{
          s = br.readLine();
        }
        catch (IOException e) {
            System.err.println("Error reading console input " + e);
        }

        if (s != null) return s;
        else throw new NullPointerException("Console string is null");
  }

  public int parseNote(String noteString) {
    return 0;
  }
}
