package DCourt.Screens.Command;

import DCourt.Screens.Screen;
import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Command/arError.class */
public class arError extends Screen {
  String problem;

  arError() {
    this.problem = "Unknown Error";
  }

  public arError(String err) {
    this.problem = err;
    setBackground(Color.red);
    setForeground(Color.white);
    setFont(Tools.textF);
    hideStatusBar();
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    String msg = this.problem;
    super.localPaint(g);
    g.setColor(getForeground());
    g.setFont(getFont());
    int v = 10 + 15;
    g.drawString("ERROR - Dragon Court Error has occurred", 10, v);
    int i = 0;
    while (true) {
      int ix = msg.indexOf(10);
      if (ix == -1) {
        g.drawString(msg, 30, v + 15);
        return;
      }
      v += 15;
      g.drawString(msg.substring(0, ix), 30, v);
      msg = msg.substring(ix + 1);
      i++;
    }
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this) || e.target != this) {
      return true;
    }
    Tools.setRegion(getHome());
    return true;
  }
}
