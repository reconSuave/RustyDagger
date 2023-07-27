package DCourt.Screens.Utility;

import DCourt.Items.List.itHero;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Transfer;
import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Utility/arStorage.class */
public class arStorage extends Transfer {
  public arStorage(Screen from) {
    super(from, "Storage at ".concat(String.valueOf(String.valueOf(from.getTitle()))));
    setBackground(new Color(0, 0, 128));
    setForeground(Color.white);
    itHero h = Tools.getHero();
    setValues(h.storeMax(), Screen.getPack(), h.getStore());
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    super.localPaint(g);
    updateTools();
    g.setFont(Tools.statusF);
    g.setColor(getForeground());
    g.drawString(
        String.valueOf(
            String.valueOf(
                new StringBuffer("Backpack ")
                    .append(Screen.getPack().getCount())
                    .append("/")
                    .append(Screen.getHero().packMax()))),
        30,
        65);
    g.drawString(
        String.valueOf(
            String.valueOf(
                new StringBuffer("Storage ").append(stashCount()).append("/").append(getLimit()))),
        230,
        65);
  }
}
