package DCourt.Screens.Command;

import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Enumeration;

import DCourt.Components.FScrollbar;
import DCourt.Items.Item;
import DCourt.Items.itList;
import DCourt.Items.List.itHero;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Tools.Buffer;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Command/arRanking.class */
public class arRanking extends Screen {
  Button done;
  Button[] ranks;
  itList[] lists;
  Item heroItem = null;
  int start = 0;
  int top = 0;
  int which = 0;
  FScrollbar scroll;
  static final int SHOWROWS = 12;
  static final int SHOWLEFT = 220;
  static final int SHOWWIDE = 55;
  static final int CLAN_DISPLAY = 4;
  static final String[] rankStr = {
    Constants.FAME, Constants.SKILL, Constants.RANK, Constants.GUILD, Constants.CLAN
  };
  static final String[] office = {
    "Queens Champion", "Captain of the Guard", "Chief Counselor", "Guild Master", "Clan Rankings"
  };
  static final String[][] stats = {
    new String[] {Constants.FAME, "Place", Constants.CLAN},
    new String[] {Constants.SKILL, "At/Df", "Stats"},
    new String[] {Constants.RANK, Constants.AGE, ""},
    new String[] {"Cash", Constants.GUILD, ""},
    new String[] {"Men", "Power", "Leader"}
  };
  static final Color[] clear = {
    new Color(255, 128, 128),
    new Color(0, 224, 224),
    new Color(255, 0, 255),
    new Color(0, 255, 0),
    new Color(255, 128, 128)
  };
  static final Color[] bars = {
    new Color(255, 160, 160),
    new Color(128, 255, 255),
    new Color(255, 128, 255),
    new Color(160, 255, 160),
    new Color(255, 160, 160)
  };

  public arRanking(Screen from) {
    super(from);
    setBackground(Color.green);
    setForeground(Color.black);
    setFont(Tools.statusF);
    hideStatusBar();
  }

  @Override // DCourt.Screens.Screen
  public void init() {
    super.init();
    Buffer buf = Tools.getRankings();
    if (buf == null || buf.isError()) {
      Tools.setRegion(
          new arNotice(
              getHome(),
              "Error Reading Rankfile:\n".concat(String.valueOf(String.valueOf(buf.line())))));
    } else {
      digest(buf);
    }
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    String target;
    itHero hero = Tools.getHero();
    if (this.which == 4) {
      target = hero == null ? "none" : hero.getClan();
    } else {
      target = hero == null ? "not" : hero.getName();
    }
    this.scroll.setMax(this.lists[this.which].getCount() - 12);
    this.scroll.setJump(12);
    this.scroll.setStep(1);
    g.setColor(bars[this.which]);
    g.fillRect(217, 0, (int) SHOWWIDE, 265);
    g.fillRect(327, 0, (int) SHOWWIDE, 265);
    g.setFont(Tools.courtF);
    g.setColor(new Color(0, 0, 128));
    g.drawString(office[this.which], 30, 20);
    g.setFont(getFont());
    for (int i = 0; i < 3; i++) {
      g.drawString(stats[this.which][i], SHOWLEFT + (i * SHOWWIDE), 20);
    }
    g.setFont(getFont());
    g.setColor(getForeground());
    int base = this.scroll.getVal();
    if (this.lists[this.which].getCount() < 1) {
      g.drawString("No Records Found", 28, 44);
      return;
    }
    Enumeration e = this.lists[this.which].elements();
    int i2 = 0;
    while (e.hasMoreElements() && i2 < base) {
      Item item = (Item) e.nextElement();
      i2++;
    }
    int i3 = 1;
    while (e.hasMoreElements() && i3 <= 12) {
      Item it = (Item) e.nextElement();
      if (it instanceof itList) {
        itList list = (itList) it;
        int v = 24 + (i3 * 20);
        g.setColor(Color.white);
        g.drawLine(0, v, (int) Tools.DEFAULT_WIDTH, v);
        if (it.isMatch(target)) {
          g.setColor(Color.blue);
        } else {
          g.setColor(Color.black);
        }
        g.setFont(Tools.textF);
        FontMetrics fm = getFontMetrics(g.getFont());
        int next = 0;
        String rank = list.select(0).getName();
        int h = fm.stringWidth(rank);
        g.drawString(rank, 3, v);
        g.setFont(Tools.statusF);
        if (this.which == 4) {
          g.drawString(list.getName(), h + 6, v);
        } else {
          next = 0 + 1;
          g.drawString(
              String.valueOf(String.valueOf(Constants.rankTitle[list.select(next).toInteger()]))
                  .concat(String.valueOf(String.valueOf(list.getName()))),
              h + 6,
              v);
        }
        g.setFont(Tools.textF);
        int h2 = SHOWLEFT;
        while (true) {
          next++;
          Item it2 = list.select(next);
          if (it2 == null) {
            break;
          }
          g.drawString(it2.getName(), h2, v);
          h2 += SHOWWIDE;
        }
      }
      i3++;
    }
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == this.done) {
      Tools.setRegion(getHome());
    }
    int i = 0;
    while (true) {
      if (i < 5) {
        if (this.ranks[i] == e.target) {
          this.which = i;
          break;
        }
        i++;
      } else {
        break;
      }
    }
    repaint();
    return super.action(e, o);
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    setForeground(Color.black);
    setFont(Tools.textF);
    this.done = new Button("Done");
    this.ranks = new Button[5];
    for (int i = 0; i < this.ranks.length; i++) {
      this.ranks[i] = new Button(rankStr[i]);
    }
    this.scroll = new FScrollbar();
    this.scroll.reshape(384, 0, 16, Tools.DEFAULT_HEIGHT);
    this.scroll.setAll(0, this.start, 1, 12);
    this.done.reshape(5, 277, 50, 20);
    for (int i2 = 0; i2 < this.ranks.length; i2++) {
      this.ranks[i2].reshape(75 + (60 * i2), 277, 50, 20);
    }
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    add(this.done);
    add(this.scroll);
    for (int i = 0; i < this.ranks.length; i++) {
      add(this.ranks[i]);
    }
  }

  void digest(Buffer buf) {
    this.lists = new itList[5];
    for (int i = 0; i < this.lists.length; i++) {
      Item it = Item.factory(buf);
      if (it instanceof itList) {
        this.lists[i] = (itList) it;
      } else {
        this.lists[i] = new itList("");
      }
    }
  }
}
