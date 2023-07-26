package DCourt.Screens.Areas.Fields;

import DCourt.Components.Portrait;
import DCourt.Items.List.itHero;
import DCourt.Screens.Screen;
import DCourt.Static.Constants;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Fields/arHealer.class */
public class arHealer extends Screen {
  Button[] tools;
  int[] cost;
  static final String[] text = {
    "Minor Healing", "Half Healing", "Full Healing", "Tithe", "Cure Disease"
  };
  static final String[] greeting = {
    null,
    "How may I aid thee?",
    "Please let me help",
    "Care for a massage?",
    "Let me heal thy aches",
    "Thou art distressed",
    "Thou art disturbed",
    "Share with the poor",
    "Can you spare some marks?",
    "Tithe for thy soul",
    "Alms for the poor?"
  };

  public arHealer(Screen from) {
    super(from, "Elden Bishop's Temple of Brotherly Sharing");
    setBackground(new Color(128, 255, 128));
    setForeground(Color.black);
    setFont(Tools.textF);
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    super.localPaint(g);
    updateTools();
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    itHero h = Tools.getHero();
    int wounds = h.getWounds();
    int level = h.getLevel();
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == getPic(0)) {
      Tools.setRegion(getHome());
    }
    int i = 0;
    while (true) {
      if (i < this.tools.length) {
        if (e.target == this.tools[i]) {
          h.subMoney(this.cost[i]);
          switch (i) {
            case 0:
              h.subWounds(wounds / 4);
              break;
            case 1:
              h.subWounds(wounds / 2);
              break;
            case 2:
              h.subWounds(wounds);
              break;
            case 3:
              if (level + 14 <= h.getAge()) {
                h.learn(this.cost[3] / (level * level));
                int num = h.getRaise();
                if (h.getExp() > num) {
                  h.getStatus().fix(Constants.EXP, num);
                  break;
                }
              }
              break;
            case 4:
              h.getTemp().zero("Disease");
              h.getTemp().clrTrait("Blind");
              h.getTemp().clrTrait("Panic");
              break;
          }
        } else {
          i++;
        }
      } else {
        break;
      }
    }
    repaint();
    return super.action(e, o);
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    greeting[0] =
        "Be brave like ".concat(String.valueOf(String.valueOf(Tools.getPlayer().getBest())));
    addPic(new Portrait("Exit.jpg", 320, 230, 64, 32));
    addPic(new Portrait("Faces/Elden.jpg", Tools.select(greeting), 10, 30, 144, 192));
    int i = text.length;
    this.tools = new Button[i];
    this.cost = new int[i];
    for (int i2 = 0; i2 < this.tools.length; i2++) {
      this.tools[i2] = new Button();
      this.tools[i2].reshape(180, 40 + (i2 * 30), 180, 25);
      this.tools[i2].setFont(Tools.statusF);
    }
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    for (int i = 0; i < this.tools.length; i++) {
      add(this.tools[i]);
    }
  }

  public void updateTools() {
    itHero h = Tools.getHero();
    int cash = h.getMoney();
    int wounds = h.getWounds();
    int disease = h.disease();
    int level = (h.getLevel() - h.getSocial()) - 1;
    boolean mercy = h.getLevel() == 1;
    if (level < 1) {
      level = 1;
    }
    this.cost[0] = mercy ? 0 : (wounds / 4) * level;
    this.cost[1] = mercy ? 0 : (wounds / 2) * level;
    this.cost[2] = wounds < 1 ? 0 : mercy ? 1 : wounds * level;
    this.cost[3] = (cash + 9) / 10;
    this.cost[4] =
        (disease > 0 || h.hasTrait("Blind") || h.hasTrait("Panic")) ? mercy ? 1 : 10 * level : 0;
    for (int i = 0; i < this.tools.length; i++) {
      this.tools[i].enable(this.cost[i] != 0 && cash >= this.cost[i]);
      this.tools[i].setLabel(
          String.valueOf(
              String.valueOf(
                  new StringBuffer(String.valueOf(String.valueOf(text[i])))
                      .append(" $")
                      .append(this.cost[i]))));
    }
  }
}
