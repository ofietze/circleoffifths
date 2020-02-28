package circle.of.fifths;
import java.lang.IllegalArgumentException;

public class Note {
  private byte octave;
  // note from c-h only 0-11 are valid notes
  // sharps/flats are notes 1,3,6,8,10
  private byte note;
  private static final byte NOTES_IN_OCTAVE = 12;

  public Note(byte note) {
    if (note < 0 || note > Byte.MAX_VALUE) throw new IllegalArgumentException("note value out of range");
    this.note = (byte) (note % NOTES_IN_OCTAVE);
    this.octave = (byte) (note / NOTES_IN_OCTAVE);
  }

  public Note (byte note, byte octave) {
    this(note);

    if (octave < 0) throw new IllegalArgumentException("octave value out of range");

    this.octave = octave;
  }

  public byte getNoteVal() { return this.note; }

  public byte getOctave() { return this.octave; }

  public String toString() {
    StringBuilder sb = new StringBuilder("(");

    // write down the 12 cases for now (less error prone)
    switch (this.note){
      case 0: sb.append("C ");      break;
      case 1: sb.append("C#/Db ");  break;
      case 2: sb.append("D ");      break;
      case 3: sb.append("D#/Eb ");  break;
      case 4: sb.append("E ");      break;
      case 5: sb.append("F ");      break;
      case 6: sb.append("F#/Gb ");  break;
      case 7: sb.append("G ");      break;
      case 8: sb.append("G#/Ab ");  break;
      case 9: sb.append("A ");      break;
      case 10: sb.append("A#/Bb "); break;
      case 11: sb.append("B ");     break;
      // Technically not allowed to happen
      default: System.err.println("Illegal note value");
    }
    sb.append(this.octave);
    sb.append(")");
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;

    if (!Note.class.isAssignableFrom(obj.getClass())) return false;

    final Note note = (Note) obj;
    return this.octave == note.getOctave() && this.note == note.getNoteVal();
  }
}
