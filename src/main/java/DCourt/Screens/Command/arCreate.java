package DCourt.Screens.Command;

import DCourt.Components.Portrait;
import DCourt.Control.Player;
import DCourt.Items.List.itAgent;
import DCourt.Items.List.itHero;
import DCourt.Items.Token.itCount;
import DCourt.Screens.Screen;
import DCourt.Screens.Wilds.arField;
import DCourt.Static.Constants;
import DCourt.Tools.DrawTools;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Event;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

/* loaded from: DCourt.jar:DCourt/Screens/Command/arCreate.class */
public class arCreate extends Screen {
  static final Rectangle[] sink = {
    new Rectangle(40, 80, 65, 40),
    new Rectangle(160, 80, 65, 40),
    new Rectangle(280, 80, 65, 40),
    new Rectangle(245, 160, 100, 40),
    new Rectangle(40, 240, 65, 40)
  };
  static final String[] text = {
    Constants.GUTS, Constants.WITS, Constants.CHARM, "Money(1p=$25)", "Build Points"
  };
  static final String[] traitStr = {"Noble", "Wizard", "Warrior", Constants.TRADER};
  static final int[] traitCost = {12, 9, 8, 10};
  Button[] add;
  Button[] sub;
  Checkbox[] traits;
  itCount guts;
  itCount wits;
  itCount charm;
  itCount money;
  Player who;

  public arCreate(Player who) {
    super("Hero Creation");
    this.who = who;
    setBackground(Color.blue);
    setForeground(Color.white);
    hideStatusBar();
    prepStats();
  }

  void prepStats() {
    this.guts = new itCount("g", 4);
    this.wits = new itCount("w", 4);
    this.charm = new itCount("c", 4);
    this.money = new itCount("m", 1);
  }

  int getBuild() {
    int build =
        (((20 - (this.guts.getCount() - 4)) - (this.wits.getCount() - 4))
                - (this.charm.getCount() - 4))
            - (this.money.getCount() - 1);
    for (int ix = 0; ix < 4; ix++) {
      if (this.traits[ix].getState()) {
        build -= traitCost[ix];
      }
    }
    return build;
  }

  boolean isNoble() {
    return this.traits[0].getState();
  }

  boolean isMagic() {
    return this.traits[1].getState();
  }

  boolean isFight() {
    return this.traits[2].getState();
  }

  boolean isThief() {
    return this.traits[3].getState();
  }

  boolean isGuild() {
    return isMagic() || isThief() || isFight();
  }

  @Override // DCourt.Screens.Screen
  public void update(Graphics g) {
    getPic(1).show(getBuild() == 0);
    super.update(g);
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    g.setFont(Tools.bigF);
    g.setColor(Color.green);
    DrawTools.center(
        g,
        String.valueOf(String.valueOf(isNoble() ? "Knight " : ""))
            .concat(String.valueOf(String.valueOf(this.who.getName()))),
        200,
        35);
    g.setFont(Tools.statusF);
    g.setColor(Color.yellow);
    for (int i = 0; i < sink.length; i++) {
      g.drawString(text[i], sink[i].x, sink[i].y - 10);
    }
    g.drawString("Options", 90, 150);
    drawNumSink(g, "".concat(String.valueOf(String.valueOf(this.guts.getCount()))), sink[0]);
    drawNumSink(g, "".concat(String.valueOf(String.valueOf(this.wits.getCount()))), sink[1]);
    drawNumSink(g, "".concat(String.valueOf(String.valueOf(this.charm.getCount()))), sink[2]);
    drawNumSink(g, "".concat(String.valueOf(String.valueOf(this.money.getCount() * 25))), sink[3]);
    drawNumSink(g, "".concat(String.valueOf(String.valueOf(getBuild()))), sink[4]);
  }

  void drawNumSink(Graphics g, String msg, Rectangle r) {
    FontMetrics fm = g.getFontMetrics(Tools.bigF);
    int dx = r.x + ((r.width - fm.stringWidth(msg)) / 2);
    int dy = r.y + (((r.height + fm.getAscent()) - 6) / 2);
    drawSink(g, r);
    g.setFont(Tools.bigF);
    g.setColor(Color.black);
    g.drawString(msg, dx + 2, dy + 2);
    g.setColor(new Color(255, 128, 0));
    g.drawString(msg, dx, dy);
  }

  @Override // DCourt.Screens.Screen
  public synchronized boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    for (int ix = 0; ix < this.add.length; ix++) {
      if (e.target == this.add[ix]) {
        raise(ix);
      }
      if (e.target == this.sub[ix]) {
        lower(ix);
      }
    }
    int ix2 = 0;
    while (true) {
      if (ix2 >= this.traits.length) {
        break;
      } else if (e.target != this.traits[ix2]) {
        ix2++;
      } else {
        this.traits[ix2].setState(this.traits[ix2].getState());
        if (this.traits[ix2].getState() && getBuild() < 0) {
          this.traits[ix2].setState(false);
        }
        break;
      }
    }
    if (e.target == getPic(0)) {
      Tools.setRegion(new arEntry());
    }
    if (e.target == getPic(1)) {
      Tools.setRegion(beginPlay());
    }
    repaint();
    return super.action(e, o);
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    setFont(Tools.textF);
    addPic(new Portrait("Exit.jpg", 320, 240, 64, 32));
    addPic(new Portrait("fldQuest.jpg", "Enter Here", 180, 215, 96, 64));
    getPic(1).show(false);
    getPic(1).setForeground(Color.white);
    this.add = new Button[sink.length - 1];
    this.sub = new Button[sink.length - 1];
    for (int i = 0; i < sink.length - 1; i++) {
      this.add[i] = new Button("+");
      this.sub[i] = new Button("-");
      Rectangle r = sink[i];
      this.add[i].reshape(r.x - 25, r.y, 20, 20);
      this.sub[i].reshape(r.x - 25, r.y + 20, 20, 20);
    }
    int i2 = traitStr.length;
    if (traitCost.length < i2) {
      i2 = traitCost.length;
    }
    this.traits = new Checkbox[i2];
    for (int i3 = 0; i3 < this.traits.length; i3++) {
      this.traits[i3] =
          new Checkbox(
              String.valueOf(
                  String.valueOf(
                      new StringBuffer(String.valueOf(String.valueOf(traitStr[i3])))
                          .append(" ")
                          .append(traitCost[i3])
                          .append("p"))));
      this.traits[i3].setFont(Tools.textF);
      this.traits[i3].setForeground(Color.white);
      this.traits[i3].setBackground(Color.blue);
      this.traits[i3].reshape(20 + ((i3 / 2) * 100), 160 + (25 * (i3 % 2)), 100, 20);
    }
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    for (int i = 0; i < this.add.length; i++) {
      add(this.add[i]);
      add(this.sub[i]);
    }
    for (int i2 = 0; i2 < this.traits.length; i2++) {
      add(this.traits[i2]);
    }
  }

  synchronized void raise(int what) {
    switch (what) {
      case 0:
        if (getBuild() > 0) {
          this.guts.adds(1);
          return;
        }
        return;
      case 1:
        if (getBuild() > 0) {
          this.wits.adds(1);
          return;
        }
        return;
      case 2:
        if (getBuild() > 0) {
          this.charm.adds(1);
          return;
        }
        return;
      case 3:
        if (getBuild() > 0) {
          this.money.adds(1);
          return;
        }
        return;
      default:
        return;
    }
  }

  synchronized void lower(int what) {
    switch (what) {
      case 0:
        if (this.guts.getCount() > 4) {
          this.guts.adds(-1);
          return;
        }
        return;
      case 1:
        if (this.wits.getCount() > 4) {
          this.wits.adds(-1);
          return;
        }
        return;
      case 2:
        if (this.charm.getCount() > 4) {
          this.charm.adds(-1);
          return;
        }
        return;
      case 3:
        if (this.money.getCount() > 1) {
          this.money.adds(-1);
          return;
        }
        return;
      default:
        return;
    }
  }

  void drawSink(Graphics g, Rectangle r) {
    g.setColor(new Color(0, 255, 255));
    g.fillRect(r.x, r.y, r.width, r.height);
    g.setColor(new Color(0, 255, 128));
    g.drawRect(r.x - 1, r.y - 1, r.width + 1, r.height + 1);
    g.setColor(new Color(0, 192, 64));
    g.drawRect(r.x - 2, r.y - 2, r.width + 3, r.height + 3);
    g.setColor(new Color(0, 128, 64));
    g.drawRect(r.x - 3, r.y - 3, r.width + 5, r.height + 5);
  }

  synchronized Screen beginPlay() {
    if (getBuild() != 0) {
      return null;
    }
    createHero();
    return this.who.saveHero() ? new arField() : this.who.errorScreen(this);
  }

  void createHero() {
    itHero hero = this.who.createHero();
    hero.setGuts(this.guts.getCount());
    hero.setWits(this.wits.getCount());
    hero.setCharm(this.charm.getCount());
    hero.getPack().clrQueue();
    hero.getPack().fix("Marks", this.money.getCount() * 25);
    hero.getRank().clrQueue();
    hero.getRank().fix(Constants.LEVEL, 1);
    hero.getRank().fix(Constants.SOCIAL, isNoble() ? 1 : 0);
    hero.getStatus().clrQueue();
    hero.getStatus().fix(Constants.AGE, 16);
    hero.calcCombat();
    hero.calcRaise();
    hero.setState(itAgent.CREATE);
    hero.setPlace(Constants.FIELDS);
    if (isThief()) {
      hero.addRank(Constants.THIEF, 1);
      hero.fixTemp(Constants.THIEF, hero.thiefRank());
    }
    if (isMagic()) {
      hero.addRank(Constants.MAGIC, 1);
      hero.fixTemp(Constants.MAGIC, hero.magicRank());
    }
    if (isFight()) {
      hero.addRank(Constants.FIGHT, 1);
      hero.fixTemp(Constants.FIGHT, hero.fightRank());
    }
    if (isGuild()) {
      hero.fixStatTrait(Constants.GUILD);
    }
    System.out.println("createHero: " + hero.toString());
  }
}
