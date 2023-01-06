package DCourt.Tools;

import DCourt.Components.Portrait;
import DCourt.Items.Item;
import DCourt.Items.List.itHero;
import DCourt.Screens.Wilds.arHills;
import DCourt.Screens.Wilds.arMound;
import DCourt.Static.ArmsTrait;
import DCourt.Static.Constants;
import java.awt.Color;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Tools/StatusPic.class */
public class StatusPic extends Portrait implements Constants {
    public StatusPic() {
        super("Status.gif", 0, 265, Tools.DEFAULT_WIDTH, 35);
        setFont(Tools.textF);
    }

    @Override // DCourt.Components.Portrait
    public void paint(Graphics g) {
        String msg;
        itHero hp = Tools.getHero();
        g.setFont(getFont());
        g.setColor(Color.white);
        if (getIcon() != null) {
            g.drawImage(getIcon(), 0, 0, this);
        }
        if (hp != null) {
            int w = hp.getWounds();
            g.drawString(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(hp.getTitle()))).append(hp.getName()).append("  Guts:").append(hp.getGuts() - w).append(w < 1 ? "" : "/".concat(String.valueOf(String.valueOf(hp.getGuts())))).append(" Wits:").append(hp.getWits()).append(" Charm:").append(hp.getCharm()).append("  Cash: $").append(hp.getMoney()))), 10, 15);
            String msg2 = String.valueOf(String.valueOf(new StringBuffer("   Quests:").append(hp.getQuests()).append("  Level:").append(hp.getLevel()).append("  Exp:").append(hp.getExp()).append("  ")));
            g.drawString(msg2, 10, 30);
            if (Tools.getRegion() instanceof arMound) {
                if (hp.hasTrait(Constants.CATSEYES)) {
                    msg = String.valueOf(String.valueOf(msg2)).concat("Cats Eyes");
                } else {
                    Item it = hp.getGear().findArms(ArmsTrait.GLOWS);
                    msg = it != null ? String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf("glowing ".concat(String.valueOf(String.valueOf(it.getName())))))) : String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("Torch (").append(hp.packCount("Torch")).append(")"))))));
                }
            } else if (Tools.getRegion() instanceof arHills) {
                msg = hp.hasTrait(Constants.HILLFOLK) ? String.valueOf(String.valueOf(msg2)).concat("Hill Folk") : String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("Rope (").append(hp.packCount("Rope")).append(")"))))));
            } else {
                msg = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(hp.getWeapon()))).append(" & ").append(hp.getArmour()))))));
            }
            g.drawString(msg, 10, 30);
        }
    }
}
