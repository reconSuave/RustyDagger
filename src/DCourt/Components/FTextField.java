package DCourt.Components;

import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Event;
import java.awt.TextField;

/* loaded from: DCourt.jar:DCourt/Components/FTextField.class */
public class FTextField extends TextField {
  int max;

  public FTextField() {}

  public FTextField(int len) {
    this.max = len;
    setForeground(Color.black);
    setFont(Tools.textF);
  }

  public FTextField(String s, int len) {
    super(s);
    this.max = len;
    setForeground(Color.black);
    setFont(Tools.textF);
  }

  public void setSize(int len) {
    this.max = len;
  }

  public boolean isMatch(String test) {
    if (test == null) {
      return getText() == null;
    }
    return test.equalsIgnoreCase(getText());
  }

  public boolean postEvent(Event e) {
    String msg;
    boolean result = super.postEvent(e);
    if (e.target == this && (msg = getText()) != null && msg.length() > this.max) {
      setText(msg.substring(0, this.max));
    }
    return result;
  }
}
