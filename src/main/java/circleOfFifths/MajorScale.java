package circle.of.fifths;

public class MajorScale extends Scale {

  public MajorScale(byte key) {
    super(key);
    Note[] scale = new Note[7];
    byte note = key;

    for (int i = 0; i < scale.length; i++) {
      scale[i] = new Note(note);

      // One Half step all other steps are whole steps
      if (i == 2) {
        note++;
      } else{
        note +=2;
      }
    }
    super.notes = scale;
  }
}
