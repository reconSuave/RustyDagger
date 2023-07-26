package DCourt.Screens.Utility;

import DCourt.Screens.Screen;
import DCourt.Tools.Breaker;
import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Utility/arNotice.class */
public class arNotice extends Screen {
  String text = null;

  public arNotice(Screen from) {
    super(from);
    hideStatusBar();
  }

  public arNotice(Screen from, String msg) {
    super(from);
    hideStatusBar();
    setMessage(msg);
  }

  /* access modifiers changed from: protected */
  public void setMessage(String msg) {
    this.text = msg;
    repaint();
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, (int) Tools.DEFAULT_WIDTH, (int) Tools.DEFAULT_HEIGHT);
    g.setColor(Color.white);
    drawText(g, 10, 5);
  }

  /* access modifiers changed from: package-private */
  public void drawText(Graphics g, int dx, int dy) {
    if (this.text != null) {
      g.setFont(Tools.courtF);
      System.out.println(Tools.courtF + " " + this.text);
      Breaker snap = new Breaker(this.text, g.getFontMetrics(g.getFont()), 380, false);
      for (int ix = 0; ix < snap.lineCount(); ix++) {
        g.drawString(snap.getLine(ix), dx, dy + snap.getAscent() + (ix * snap.getHeight()));
      }
    }
  }

  @Override // DCourt.Screens.Screen
  public Screen down(int x, int y) {
    if (Tools.movedAway(this)) {
      return null;
    }
    Tools.setRegion(getHome());
    return null;
  }
}
