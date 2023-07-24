package DCourt.Screens.Quest;

import DCourt.Items.Item;
import DCourt.Items.List.itHero;
import DCourt.Items.List.itMonster;
import DCourt.Items.Token.itValue;
import DCourt.Items.itList;
import DCourt.Static.GearTypes;
import DCourt.Tools.Tools;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Quest/Options.class */
public class Options extends Canvas {
  static final int BRIBE = 0;
  static final int FEED = 1;
  static final int RIDDLE = 2;
  static final int TRADE = 3;
  static final int HELP = 4;
  static final int SEDUCE = 5;
  static final int CONTROL = 6;
  static final int BACKSTAB = 7;
  static final int BERZERK = 8;
  static final int SWINDLE = 9;
  static final int IEATSU = 10;
  static final int ATTACK = 11;
  static final int RUNAWAY = 12;
  static final int CARP = 13;
  static final int BUSHIDO = 14;
  static final int CAPTURE = 15;
  static final int SPELLS = 16;
  static final String[] OPT_ARRAY = {
    "bribe",
    "feed",
    "riddle",
    "trade",
    "help",
    "seduce",
    "control",
    "backstab",
    "berzerk",
    "swindle",
    "ieatsu",
    "attack",
    "flee",
    "fish",
    "bushido",
    "capture"
  };
  static final itList OPT_LIST = new itList("Options", OPT_ARRAY);
  String[][] OPT_STRING;
  itList list;
  itList goal;
  Font font;
  int select;
  int fontH;
  int fontA;
  int fontB;
  boolean firstRound;

  Options() {
    this.OPT_STRING =
        new String[][] {
          new String[] {"Bribe with Marks", "Pay for Passage", "Give it Money"},
          new String[] {"Feed This Creature", "Tempt With Food", "Throw Some Grub"},
          new String[] {"Try to Answer", "Hazard a Guess", "Riddle Me This"},
          new String[] {"Bargain", "Barter", "Swap Goods", "Trade"},
          new String[] {"Aid the Poor Booger", "Help Out", "Lend a Hand"},
          new String[] {"Seduce the Beast", "Use Sex Appeal", "Flirt Lewdly"},
          new String[] {"  M:Hypnotize"},
          new String[] {"  T:Backstab"},
          new String[] {"  F:Berzerk"},
          new String[] {"  T:Swindle"},
          new String[] {"  S:Ieatsu"},
          new String[] {
            "Slay This Brute",
            "Attack Yon Beastie",
            "Assault The Monster",
            "Kill The Critter",
            "Smash The Devil"
          },
          new String[] {
            "Flee For Safety", "Run For Your Life", "Evade With Haste", "Run Away! Run Away!"
          },
          new String[] {"Feed This Creature", "Tempt With Food", "Throw Some Grub"},
          new String[] {"Present Your Token", "Display A Token", "Hand Over Token"},
          new String[] {"Grab The Sucker", "Bottle This Thing", "Take it Captive"}
        };
    this.select = -1;
  }

  public Options(itList ml) {
    this.OPT_STRING =
        new String[][] {
          new String[] {"Bribe with Marks", "Pay for Passage", "Give it Money"},
          new String[] {"Feed This Creature", "Tempt With Food", "Throw Some Grub"},
          new String[] {"Try to Answer", "Hazard a Guess", "Riddle Me This"},
          new String[] {"Bargain", "Barter", "Swap Goods", "Trade"},
          new String[] {"Aid the Poor Booger", "Help Out", "Lend a Hand"},
          new String[] {"Seduce the Beast", "Use Sex Appeal", "Flirt Lewdly"},
          new String[] {"  M:Hypnotize"},
          new String[] {"  T:Backstab"},
          new String[] {"  F:Berzerk"},
          new String[] {"  T:Swindle"},
          new String[] {"  S:Ieatsu"},
          new String[] {
            "Slay This Brute",
            "Attack Yon Beastie",
            "Assault The Monster",
            "Kill The Critter",
            "Smash The Devil"
          },
          new String[] {
            "Flee For Safety", "Run For Your Life", "Evade With Haste", "Run Away! Run Away!"
          },
          new String[] {"Feed This Creature", "Tempt With Food", "Throw Some Grub"},
          new String[] {"Present Your Token", "Display A Token", "Hand Over Token"},
          new String[] {"Grab The Sucker", "Bottle This Thing", "Take it Captive"}
        };
    this.select = -1;
    this.goal = ml;
    this.firstRound = true;
    this.list = new itList("Options");
    fixList();
    redraw();
  }

  public void reshape(int x, int y, int w, int h) {
    reshape(x, y, w, h);
    setdraw();
  }

  public int select() {
    return get(this.select);
  }

  public void paint(Graphics g) {
    g.setColor(getBackground());
    g.fillRect(0, 0, bounds().width, bounds().height);
    g.setFont(this.font);
    int ix = 0;
    while (ix < this.list.getCount()) {
      g.setColor(this.select == ix ? Color.red : Color.black);
      g.drawString(text(ix), 2, this.fontB + this.fontA + (ix * this.fontH));
      ix++;
    }
  }

  void setdraw() {
    int count = this.list.getCount();
    int height = bounds().height;
    for (int size = 18; size >= 8; size--) {
      this.font = new Font(Tools.primeFont, 3, size);
      FontMetrics fmet = getFontMetrics(this.font);
      this.fontA = fmet.getAscent();
      this.fontH = this.fontA + fmet.getDescent();
      if (this.fontH * count < height) {
        break;
      }
    }
    this.fontB = (bounds().height - (this.list.getCount() * this.fontH)) / 2;
    if (this.fontB < 0) {
      this.fontB = 0;
    }
    repaint();
  }

  public boolean handleEvent(Event e) {
    int choice = this.select;
    if (e.id == 503) {
      this.select = (e.y - this.fontB) / this.fontH;
      if (this.select != choice) {
        repaint();
      }
    }
    if (e.id == 501 && this.select >= 0 && this.select < this.list.getCount()) {
      postEvent(new Event(this, 1001, (Object) null));
    }
    return handleEvent(e);
  }

  void append(int what) {
    int which = Tools.roll(this.OPT_STRING[what].length);
    String tag = "".concat(String.valueOf(String.valueOf(what)));
    this.list.drop(tag);
    this.list.append(new itValue(tag, this.OPT_STRING[what][which]));
  }

  public void remove(int what) {
    this.goal.drop(OPT_ARRAY[what]);
  }

  public void redraw() {
    itHero h = Tools.getHero();
    for (int px = 0; px < this.list.getCount(); px++) {
      Item it = this.list.select(px);
      int ix = it.toInteger();
      switch (ix) {
        case 6:
          it.setValue(
              String.valueOf(
                  String.valueOf(
                      new StringBuffer(String.valueOf(String.valueOf(this.OPT_STRING[ix][0])))
                          .append("[")
                          .append(h.magic())
                          .append("]"))));
          break;
        case 7:
        case 9:
          it.setValue(
              String.valueOf(
                  String.valueOf(
                      new StringBuffer(String.valueOf(String.valueOf(this.OPT_STRING[ix][0])))
                          .append("[")
                          .append(h.thief())
                          .append("]"))));
          break;
        case 8:
          it.setValue(
              String.valueOf(
                  String.valueOf(
                      new StringBuffer(String.valueOf(String.valueOf(this.OPT_STRING[ix][0])))
                          .append("[")
                          .append(h.fight())
                          .append("]"))));
          break;
        case 10:
          it.setValue(
              String.valueOf(
                  String.valueOf(
                      new StringBuffer(String.valueOf(String.valueOf(this.OPT_STRING[ix][0])))
                          .append("[")
                          .append(h.ieatsu())
                          .append("]"))));
          break;
      }
    }
    setdraw();
  }

  public void fixList() {
    itHero h = Tools.getHero();
    this.list.clrQueue();
    if (h.hasTrait("Panic")) {
      append(12);
    } else if (!this.firstRound) {
      append(11);
      append(12);
      if (this.goal.contains("control") && h.magic() > 0) {
        append(6);
      }
      if (h.fight() > 0) {
        append(8);
      }
    } else {
      append(11);
      append(12);
      for (int ix = 0; ix < this.goal.getCount(); ix++) {
        int choice = OPT_LIST.firstOf(this.goal.select(ix).getName());
        if (choice >= 0
            && ((choice != 0 || h.getMoney() >= 1)
                && ((choice != 3 || h.getMoney() >= 1)
                    && ((choice != 1 || h.packCount(GearTypes.FOOD) >= 1)
                        && ((choice != 13 || h.packCount(GearTypes.FISH) >= 1)
                            && (choice != 14
                                || (h.packCount(GearTypes.TOKEN) >= 1
                                    && h.getQuests() >= 10
                                    && h.guildRank() < h.getLevel()))))))) {
          if (choice == 7 || choice == 9) {
            if (h.thief() > 0) {
              append(choice);
            }
          } else if (choice != 6) {
            append(choice);
          } else if (h.magic() > 0) {
            append(choice);
          }
        }
      }
      if (h.ieatsu() > 0) {
        append(10);
      }
    }
  }

  public void nextRound(itHero h, itMonster m) {
    this.firstRound = false;
    if (m.isHostile() || m.isDefensive()) {
      m.incStance();
    }
  }

  int count() {
    if (this.list == null) {
      return 0;
    }
    return this.list.getCount();
  }

  String text(int which) {
    return ((itValue) this.list.select(which)).getValue();
  }

  int get(int which) {
    return ((itValue) this.list.select(which)).toInteger();
  }
}
