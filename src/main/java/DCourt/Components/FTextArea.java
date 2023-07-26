package DCourt.Components;

import java.awt.Event;
import java.awt.TextArea;

/* loaded from: DCourt.jar:DCourt/Components/FTextArea.class */
public class FTextArea extends TextArea {
  int max;

  public FTextArea() {}

  public FTextArea(int len) {
    this.max = len;
  }

  void setSize(int len) {
    this.max = len;
  }

  public boolean handleEvent(Event e) {
    String msg;
    boolean result = super.handleEvent(e);
    if (e.target == this && (msg = getText()) != null && msg.length() > this.max) {
      setText(msg.substring(0, this.max));
      select(this.max, this.max);
    }
    return result;
  }
}
