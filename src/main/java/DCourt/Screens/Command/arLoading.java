package DCourt.Screens.Command;

import DCourt.DCourtApplet;
import DCourt.Screens.Screen;
import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Command/arLoading.class */
public class arLoading extends Screen {
  int stage = 0;
  DCourtApplet papa;
  Tools tools;

  public arLoading(DCourtApplet papa, Tools tools) {
    this.papa = papa;
    this.tools = tools;
    hideStatusBar();
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    g.setColor(Color.white);
    g.fillRect(0, 0, (int) Tools.DEFAULT_WIDTH, (int) Tools.DEFAULT_HEIGHT);
    g.setColor(Color.black);
    g.setFont(Tools.bigF);
    String msg = "Loading";
    for (int ix = 0; ix < this.stage; ix++) {
      msg = String.valueOf(String.valueOf(msg)).concat(" .");
    }
    g.drawString(msg, 20, 120);
    int i = this.stage;
    this.stage = i + 1;
    if (!Tools.isLoading(i)) {
      Tools.setRegion(new arEntry());
    } else {
      repaint();
    }
  }
}
