package DCourt.Screens.Command;

import DCourt.Components.FTextField;
import DCourt.Components.Portrait;
import DCourt.Control.PlaceTable;
import DCourt.Control.Player;
import DCourt.Items.List.itHero;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Static.GameStrings;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/* loaded from: DCourt.jar:DCourt/Screens/Command/arEntry.class */
public class arEntry extends Screen
    implements GameStrings, KeyListener, ActionListener, MouseListener {
  FTextField nameTXF;
  FTextField passTXF;
  Button lists;
  Button credits;
  Image splash;
  static final String welcome = "Welcome To";
  static final String guard = "Home of the Dragon Guard";
  static final String copyright = "Copyright 1998 Fred\'s Friends, Inc.";
  static final String homepage = "www.FFIends.com";
  static final String dragon = "Dragon";
  static final String court = "Court";
  static final String CAMP_BAG = "Sleeping Bag";
  static final String CAMP_COOK = "Cooking Gear";
  static final String CAMP_TENT = "Camp Tent";
  public static final String[] sick = {
    null,
    "You are deathly ill.",
    "You have a touch of plague.",
    "You are coughing and wheezing.",
    "You are sniffling and sneezing.",
    "You feel hungover."
  };
  public static final String[] injure = {
    null,
    "Something bit off your arm last night.",
    "Your nicks and scratches fester horribly.",
    "You have a deep bruise where you lay on a rock.",
    "You have a painful crick in your back.",
    "Bedbugs have been gnawing on you."
  };
  public static final String[] tired = {
    null,
    "You awake late in the afternoon.",
    "You've slept through lunch.",
    "Aw heck, it's nearly noon.",
    "You wake when the sun is two hands high.",
    "You awaken after breakfast."
  };
  static final String sniffle = "\tYou have tiny sniffle";
  static final String warriorSniffle = " But warrior training dispels suffering.\n";
  static final String cramp = "\tYou have a slight cramp.";
  static final String warriorCramp = " But warrior training ignores pain.\n";
  static final String sleepLate = "\tYou sleep a little late.";
  static final String warriorRise = " But warrior training wakes you early.\n";
  static final String gearRust = "\tSome of your gear has rusted.\n";
  static final String gearRot = "\tSome camping gear has rotted.\n";
  static final String stipendArrives =
      "\tThe annual stipend from your family lands has just arrived: +$";

  public arEntry() {
    super("Title Screen");
    this.splash = null;
    setBackground(new Color(0, 128, 0));
    setForeground(Color.white);
    setFont(Tools.textF);
    hideStatusBar();
    this.splash = Tools.loadImage("Splash.jpg");
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    g.setColor(new Color(255, 64, 0));
    g.setFont(Tools.giantF);
    g.drawString("Dragon", 80, 100);
    g.drawString(court, 100, 180);
    if (this.splash != null) {
      g.drawImage(this.splash, 0, 0, (int) Tools.DEFAULT_WIDTH, (int) Tools.DEFAULT_HEIGHT, this);
    }
    g.setColor(Color.white);
    g.setFont(Tools.textF);
    FontMetrics fm = getFontMetrics(g.getFont());
    int val = 5 + fm.getAscent();
    g.drawString(copyright, 5, val);
    g.drawString(homepage, (Tools.DEFAULT_WIDTH - fm.stringWidth(homepage)) - 5, val);
    g.setColor(new Color(50, 200, 200));
    g.setFont(Tools.statusF);
    FontMetrics fm2 = getFontMetrics(g.getFont());
    g.drawString(welcome, (Tools.DEFAULT_WIDTH - fm2.stringWidth(welcome)) / 2, 45);
    g.drawString(guard, (Tools.DEFAULT_WIDTH - fm2.stringWidth(guard)) / 2, 205);
    g.setColor(getForeground());
    g.setFont(getFont());
    int val2 = getFontMetrics(getFont()).getAscent();
    g.drawString("Hero Name", 25, 232 + val2);
    g.drawString("Password", 28, 262 + val2);
  }

  /*
  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
      System.out.println("action");
      if (Tools.movedAway(this)) {
          return true;
      }
      if (e.target == this.lists) {
          Tools.setRegion(new arRanking(this));
      }
      if (e.target == getPic(0)) {
          Tools.setRegion(EnterGame());
      }
      if (e.target != this.credits) {
          return false;
      }
      Tools.setRegion(new arNotice(this, GameStrings.creditText));
      return false;
  }*/

  @Override // Dcourt.Screens.Screen
  public void actionPerformed(ActionEvent e) {
    if (Tools.movedAway(this)) {
      return;
    }
    if (e.getSource() == this.lists) {
      Tools.setRegion(new arRanking(this));
    }
    /* Canvas can't trigger ActionEvent
    if (e.getSource() == getPic(0)) {
        Tools.setRegion(EnterGame());
    }
    */
    if (e.getSource() != this.credits) {
      return;
    }
    Tools.setRegion(new arNotice(this, GameStrings.creditText));
  }

  /* to manage click on Portrait */
  public void mouseClicked(MouseEvent e) {
    if (e.getSource() == getPic(0)) {
      Tools.setRegion(EnterGame());
    }
  }

  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  /*
  public boolean handleEvent(Event e) {
      boolean val = this.handleEvent(e);
      if (e.target == this.nameTXF || e.target == this.passTXF) {
          getPic(0).show(testNames());
      }
      return val;
  }
  */

  /* for login/password */
  public void keyPressed(KeyEvent e) {
    getPic(0).show(testNames());
  }

  public void keyReleased(KeyEvent e) {}

  public void keyTyped(KeyEvent e) {}

  Screen EnterGame() {
    System.out.println("EnterGame");
    int a = 0;
    a += 1;
    String name = scoreString(this.nameTXF.getText());
    String pass = scoreString(this.passTXF.getText());
    a += 1;
    Player player = Tools.getPlayer();
    a += 1;
    if (!player.loadHero(name, pass)) {
      System.out.println("error loadHero EnterGame: " + a);
      a += 1;
      return player.errorScreen(this);
    }
    a += 1;
    if (player.getHero() == null) {
      System.out.println("error getHero EnterGame: " + a);
      a += 1;
      return new arCreate(player);
    }
    a += 1;
    if (player.isDead()) {
      System.out.println("Dead EnterGame: " + a);
      a += 1;
      return new arNotice(this, GameStrings.heroHasDied);
    }
    a += 1;
    PlaceTable lot = Tools.getPlaceTable();
    lot.select(player.getPlace());
    Screen next = lot.getLaunch();
    if (next == null) {
      next = this;
    }
    Screen next2 = heroAwakens(lot.getDecay(), lot.getUse(), lot.getAwake(), next);
    return player.needsBuild() ? new arBuild(next2) : next2;
  }

  Screen heroAwakens(int dcy, String use, String msg, Screen after) {
    itHero hero = Tools.getHero();
    String result = msg;
    if (use.indexOf(98) >= 0 && hero.packCount(CAMP_BAG) > 0) {
      dcy++;
    }
    if (use.indexOf(99) >= 0 && hero.packCount(CAMP_COOK) > 0) {
      dcy++;
    }
    if (use.indexOf(116) >= 0 && hero.packCount(CAMP_TENT) > 0) {
      dcy++;
    }
    if (hero.isNewday()) {
      if (use.indexOf(115) >= 0) {
        result =
            String.valueOf(String.valueOf(result))
                .concat(String.valueOf(String.valueOf(heroStipend(hero))));
      }
      result =
          String.valueOf(
                  String.valueOf(
                      String.valueOf(
                              String.valueOf(
                                  String.valueOf(
                                          String.valueOf(
                                              String.valueOf(String.valueOf(result))
                                                  .concat(
                                                      String.valueOf(
                                                          String.valueOf(diseaseHero(hero, dcy))))))
                                      .concat(
                                          String.valueOf(String.valueOf(injureHero(hero, dcy))))))
                          .concat(String.valueOf(String.valueOf(exhaustHero(hero, dcy))))))
              .concat(String.valueOf(String.valueOf(heroDecay(hero, dcy))));
    }
    if (hero.getOverload() > 0) {
      result = String.valueOf(String.valueOf(result)).concat("\n*** YOUR PACK IS OVERLOADED ***\n");
    }
    return new arNotice(after, result);
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    Color entryC = new Color(64, 128, 64);
    addPic(new Portrait("fldQuest.jpg", "Enter Here", 235, 215, 96, 64));
    getPic(0).setForeground(Color.white);
    getPic(0).show(false);
    getPic(0).addMouseListener(this);

    this.nameTXF = new FTextField(15);
    this.nameTXF.setBackground(entryC);
    this.nameTXF.setForeground(Color.black);
    this.nameTXF.reshape(100, 230, 120, 22);
    this.nameTXF.addKeyListener(this);

    this.passTXF = new FTextField(15);
    this.passTXF.setEchoCharacter('*');
    this.passTXF.setBackground(entryC);
    this.passTXF.setForeground(Color.black);
    this.passTXF.reshape(100, 260, 120, 22);
    this.passTXF.addKeyListener(this);

    this.lists = new Button("Lists");
    this.lists.reshape(340, 232, 55, 20);
    this.lists.addActionListener(this);
    this.credits = new Button("Credits");
    this.credits.reshape(340, 262, 55, 20);
    this.credits.addActionListener(this);
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    add(this.nameTXF);
    add(this.passTXF);
    add(this.lists);
    add(this.credits);
  }

  public boolean testNames() {
    return this.nameTXF.getText().length() >= 4;
    /* no password with local storage
    && this.passTXF.getText().length() >= 4;
    */
  }

  public String scoreString(String msg) {
    String buf = "";
    String msg2 = Tools.detokenize(msg);
    int len = msg2.length();
    int tlen = msg2.trim().length();
    int ix = 0;
    char c;
    while (ix < len && (c = msg2.charAt(ix)) >= 0 && c <= ' ') {
      buf = String.valueOf(String.valueOf(buf)).concat("_");
      ix++;
    }
    int tlen2 = tlen + ix;
    while (ix < tlen2) {
      buf =
          String.valueOf(String.valueOf(buf))
              .concat(String.valueOf(String.valueOf(msg2.charAt(ix))));
      ix++;
    }
    while (ix < len) {
      buf = String.valueOf(String.valueOf(buf)).concat("_");
      ix++;
    }
    return buf;
  }

  String diseaseHero(itHero h, int rate) {
    String msg;
    if (Tools.roll(rate) > 0) {
      return "";
    }
    if (rate >= sick.length) {
      msg = String.valueOf(String.valueOf("")).concat(sniffle);
    } else {
      msg = "\t".concat(String.valueOf(String.valueOf(sick[rate])));
    }
    if (h.fight() >= 1) {
      return String.valueOf(String.valueOf(msg)).concat(warriorSniffle);
    }
    h.ail(h.getSkill() / rate);
    return String.valueOf(String.valueOf(msg)).concat("\n");
  }

  String injureHero(itHero h, int rate) {
    String msg;
    if (Tools.roll(rate) > 0) {
      return "";
    }
    if (rate >= injure.length) {
      msg = String.valueOf(String.valueOf("")).concat(cramp);
    } else {
      msg = "\t".concat(String.valueOf(String.valueOf(injure[rate])));
    }
    if (h.fight() >= 2) {
      return String.valueOf(String.valueOf(msg)).concat(warriorCramp);
    }
    h.addWounds((h.getGuts() / rate) - 1);
    return String.valueOf(String.valueOf(msg)).concat("\n");
  }

  String exhaustHero(itHero h, int rate) {
    String msg;
    if (Tools.roll(rate) > 0) {
      return "";
    }
    if (rate >= tired.length) {
      msg = String.valueOf(String.valueOf("")).concat(sleepLate);
    } else {
      msg = "\t".concat(String.valueOf(String.valueOf(tired[rate])));
    }
    if (h.fight() >= 3) {
      return String.valueOf(String.valueOf(msg)).concat(warriorRise);
    }
    h.addFatigue((h.getBaseQuests() / (rate + 1)) - 1);
    return String.valueOf(String.valueOf(msg)).concat("\n");
  }

  String heroDecay(itHero h, int rate) {
    boolean decay = false;
    String msg = "";
    int rate2 = (rate + h.fight()) * 5;
    if (Screen.getGear().decay(rate2)) {
      decay = true;
    }
    if (Screen.getPack().decay(rate2)) {
      decay = true;
    }
    if (decay) {
      msg = String.valueOf(String.valueOf(msg)).concat(gearRust);
    }
    boolean decay2 = false;
    if (Tools.roll(rate2) == 0 && Screen.subPack(CAMP_BAG, 1) == 1) {
      decay2 = true;
    }
    if (Tools.roll(rate2) == 0 && Screen.subPack(CAMP_COOK, 1) == 1) {
      decay2 = true;
    }
    if (Tools.roll(rate2) == 0 && Screen.subPack(CAMP_TENT, 1) == 1) {
      decay2 = true;
    }
    if (decay2) {
      msg = String.valueOf(String.valueOf(msg)).concat(gearRot);
    }
    return msg.length() > 0 ? "\n".concat(String.valueOf(String.valueOf(msg))) : msg;
  }

  String heroStipend(itHero h) {
    int num = h.getStatus().getCount(Constants.STIPEND);
    h.getStatus().zero(Constants.STIPEND);
    if (num < 1) {
      return "";
    }
    h.addMoney(num);
    return String.valueOf(
        String.valueOf(new StringBuffer(stipendArrives).append(num).append("\n")));
  }
}
