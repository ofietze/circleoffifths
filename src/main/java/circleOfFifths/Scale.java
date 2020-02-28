package circle.of.fifths;

public class Scale {
  protected Note[] notes;
  private Note key;

  public Scale(byte key) {
    this.key = new Note(key);
  }

  public Scale(Note[] notes) {
    this.notes = notes;
    this.key = notes[0];
  }

  public Scale(byte[] noteVals) {
    notes = new Note[noteVals.length];
    this.key = new Note(noteVals[0]);

    for (int i = 0; i < noteVals.length; i++) {
      notes[i] = new Note(noteVals[i]);
    }
  }

  public Note getKey() { return this.key; }
  public Note[] getNotes() { return this.notes; }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Note n: notes) {
      sb.append(n.toString());
      sb.append(" ");
    }
    return sb.toString();
  }

  public String[] toStringArray() {
    String[] arr = new String[notes.length];
    for (int i = 0; i < notes.length; i++) {
      arr[i] = notes[i].toString();
    }
    return arr;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;

    if (!Scale.class.isAssignableFrom(obj.getClass())) return false;

    final Scale scale = (Scale) obj;
    return this.key.equals(scale.getKey()) && this.notes.equals(scale.getNotes());
  }
}
