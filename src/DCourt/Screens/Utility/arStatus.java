package DCourt.Screens.Utility;

import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;

import DCourt.Components.FTextList;
import DCourt.Control.GearTable;
import DCourt.Items.Item;
import DCourt.Items.itList;
import DCourt.Items.List.itAgent;
import DCourt.Items.List.itArms;
import DCourt.Items.List.itHero;
import DCourt.Items.List.itNote;
import DCourt.Screens.Screen;
import DCourt.Static.ArmsTrait;
import DCourt.Static.GearTypes;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Utility/arStatus.class */
public class arStatus extends Screen implements GearTypes, ArmsTrait {
  Item useItem;
  Item pick;
  itArms over;
  int state;
  int effect;
  boolean fight;
  boolean attack;
  FTextList table;
  Button[] action;
  static final int STATE_WAIT = 0;
  static final int STATE_TARGET = 1;
  static final String[] actionSTR = {"Use", "Info", "Peer", "Dump Slot", "Oops", "Exit"};
  static final String[] slot = {
    ArmsTrait.HEAD, ArmsTrait.BODY, ArmsTrait.FEET, ArmsTrait.RIGHT, ArmsTrait.LEFT
  };
  static final String[] loc = {"H:", "B:", "F:", "R:", "L:"};
  static final Rectangle gearRect = new Rectangle(200, 145, 200, 100);
  static final Rectangle expRect = new Rectangle(175, 98, 90, 10);
  static final Color[] gclr = {Color.white, Color.cyan, Color.lightGray, new Color(96, 192, 192)};

  public arStatus(Screen from) {
    this(from, false);
  }

  public arStatus(Screen from, boolean battle) {
    super(from, "Hero Status Screen");
    this.useItem = null;
    this.pick = null;
    this.over = null;
    this.state = 0;
    this.effect = 0;
    this.fight = false;
    this.attack = false;
    this.fight = battle;
    this.attack = this.fight && !Tools.getHero().hasTrait("Panic");
    hideStatusBar();
    setBackground(new Color(192, 64, 0));
    setForeground(Color.white);
    setFont(Tools.statusF);
    Screen.getHero().calcCombat();
  }

  itList getDump() {
    return Screen.getHero().getDump();
  }

  @Override // DCourt.Screens.Screen
  public void init() {
    super.init();
    updateTools();
    fixTable();
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    addPic(Screen.getHero().getPortrait());
    getPic(0).reshape(275, 5, 120, 120);
    this.action = new Button[6];
    int ix = 0;
    while (ix < 6) {
      int off = ix % 3 == 0 ? 20 : 0;
      this.action[ix] = new Button(actionSTR[ix]);
      this.action[ix].setFont(Tools.textF);
      this.action[ix].reshape((205 + ((ix % 3) * 65)) - off, ix < 3 ? 250 : 275, 60 + off, 20);
      this.action[ix].setForeground(Color.black);
      ix++;
    }
    this.table = new FTextList();
    this.table.reshape(5, 140, 170, 140);
    this.table.setFont(Tools.textF);
    this.table.setForeground(Color.black);
    updateTools();
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    for (int i = 0; i < 6; i++) {
      add(this.action[i]);
    }
    add(this.table);
  }

  void updateTools() {
    itHero h = Screen.getHero();
    this.action[0].setLabel(
        String.valueOf(String.valueOf(useString()))
            .concat(
                String.valueOf(
                    String.valueOf(
                        this.fight
                            ? String.valueOf(
                                String.valueOf(
                                    new StringBuffer(" (").append(h.actions()).append(")")))
                            : ""))));
    this.action[4].enable(!h.getDump().isEmpty());
    h.picture();
  }

  String useString() {
    return this.state == 0 ? "Use" : GearTable.effectLabel(this.useItem);
  }

  void fixTable() {
    Enumeration e = Screen.getPack().elements();
    int choice = -1;
    int ix = 0;
    this.table.clear();
    while (e.hasMoreElements()) {
      Item it = (Item) e.nextElement();
      this.table.addItem(nameItem(it));
      if (it == this.pick) {
        choice = ix;
      }
      ix++;
    }
    this.table.setSelect(choice);
  }

  Item findPack(itList p) {
    Enumeration e = p.elements();
    int which = this.table.getSelect();
    if (which < 0) {
      return null;
    }
    while (e.hasMoreElements()) {
      Item it = (Item) e.nextElement();
      which--;
      if (which < 0) {
        return it;
      }
    }
    return null;
  }

  String nameItem(Item it) {
    return !GearTable.canMageUse(it)
        ? it.toShow()
        : String.valueOf(
            String.valueOf(
                new StringBuffer(String.valueOf(String.valueOf(it.toShow())))
                    .append("{")
                    .append(GearTable.effectLabel(it))
                    .append("}")));
  }

  void insertPack(Item it) {
    Screen.putPack(it);
    this.table.addItem(nameItem(it), 0);
  }

  boolean removePack(Item it) {
    int ix = Screen.getPack().indexOf(it);
    if (ix < 0) {
      return false;
    }
    this.table.delItem(ix);
    Screen.subPack(it);
    this.table.setSelect(-1);
    return true;
  }

  void updatePack(Item it) {
    int ix = Screen.getPack().indexOf(it);
    if (ix >= 0) {
      this.table.setItem(nameItem(it), ix);
      this.table.setSelect(ix);
      this.pick = it;
    }
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    g.setColor(getBackground());
    g.fillRect(0, 0, bounds().width, bounds().height);
    drawStats(g);
    drawGear(g, 180, 140);
  }

  void drawStats(Graphics g) {
    itHero h = Screen.getHero();
    int wounds = h.getWounds();
    int fatigue = h.getFatigue() + h.getOverload();
    int disease = h.disease();
    g.setColor(getForeground());
    g.setFont(Tools.statusF);
    g.drawString(
        String.valueOf(String.valueOf(h.getTitle()))
            .concat(String.valueOf(String.valueOf(h.getName()))),
        5,
        18);
    g.drawString(
        String.valueOf(
            String.valueOf(
                new StringBuffer("Level: ")
                    .append(h.getLevel())
                    .append("   Rank: ")
                    .append(h.getRankTitle())
                    .append("   Age: ")
                    .append(h.getAge()))),
        5,
        36);
    g.drawString(
        String.valueOf(
            String.valueOf(
                new StringBuffer("Guts: ")
                    .append(h.getGuts())
                    .append(
                        wounds > 0
                            ? String.valueOf(
                                String.valueOf(new StringBuffer("[-").append(wounds).append("]")))
                            : ""))),
        5,
        54);
    g.drawString("Wits: ".concat(String.valueOf(String.valueOf(h.getWits()))), 5, 72);
    g.drawString("Charm: ".concat(String.valueOf(String.valueOf(h.getCharm()))), 5, 90);
    g.drawString(
        String.valueOf(
            String.valueOf(
                new StringBuffer("Quests: ")
                    .append(h.getBaseQuests())
                    .append(
                        fatigue > 0
                            ? String.valueOf(
                                String.valueOf(new StringBuffer("[-").append(fatigue).append("]")))
                            : ""))),
        5,
        108);
    g.drawString("Attack: ".concat(String.valueOf(String.valueOf(h.getAttack()))), 140, 54);
    g.drawString("Defend: ".concat(String.valueOf(String.valueOf(h.getDefend()))), 140, 72);
    g.drawString(
        String.valueOf(
            String.valueOf(
                new StringBuffer("Skill: ")
                    .append(h.getSkill())
                    .append(
                        disease > 0
                            ? String.valueOf(
                                String.valueOf(
                                    new StringBuffer("[-").append(disease).append("]sick")))
                            : ""))),
        140,
        90);
    g.drawString("Exp:", 140, 108);
    Rectangle r = new Rectangle(175, 98, 90, 10);
    g.setColor(Color.white);
    g.fillRect(r.x, r.y, r.width, r.height);
    g.setColor(Color.blue);
    g.fillRect(r.x, r.y, (r.width * h.getExp()) / h.getRaise(), r.height);
    g.setColor(Color.black);
    g.drawRect(r.x, r.y, r.width, r.height);
    if (this.fight) {
      g.setFont(Tools.courtF);
      g.drawString(actionLine(h), 5, 130);
    } else {
      g.setFont(Tools.statusF);
      g.drawString(guildLine(h), 5, 130);
    }
    String msg =
        String.valueOf(
            String.valueOf(
                new StringBuffer("Load: ")
                    .append(Screen.getPack().getCount())
                    .append(" (")
                    .append(h.packMax())
                    .append(")")));
    g.setFont(Tools.boldF);
    if (h.getOverload() > 0) {
      g.setColor(Color.cyan);
      msg = "OVER ".concat(String.valueOf(String.valueOf(msg)));
    }
    g.drawString(msg, 5, 295);
  }

  void drawGear(Graphics g, int dx, int dy) {
    g.setFont(Tools.statusF);
    g.setColor(Color.black);
    g.drawString("Armament", dx + 70, dy);
    for (int ix = 0; ix < 5; ix++) {
      itArms it = Screen.getHero().findGearTrait(slot[ix]);
      g.setColor(gclr[it == null ? 0 : (it == this.pick ? 1 : 0) + (it == this.over ? 2 : 0)]);
      dy += 20;
      g.drawString(
          String.valueOf(String.valueOf(loc[ix]))
              .concat(String.valueOf(String.valueOf(it == null ? "" : it.toShow()))),
          dx,
          dy);
    }
  }

  String actionLine(itAgent h) {
    Enumeration e = h.getActions().elements();
    String msg = "";
    while (e.hasMoreElements()) {
      msg =
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          " "
                              .concat(
                                  String.valueOf(
                                      String.valueOf(((Item) e.nextElement()).toShow()))))));
    }
    return msg.length() < 1 ? msg : "Use: ".concat(String.valueOf(String.valueOf(msg)));
  }

  String guildLine(itAgent h) {
    String msg = "Guild = ";
    if (h.guildRank() < 1) {
      return "";
    }
    int num = h.fightRank();
    if (num > 0) {
      msg =
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("F:")
                                      .append(h.fight())
                                      .append("/")
                                      .append(num)
                                      .append("  "))))));
    }
    int num2 = h.magicRank();
    if (num2 > 0) {
      msg =
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("M:")
                                      .append(h.magic())
                                      .append("/")
                                      .append(num2)
                                      .append("  "))))));
    }
    int num3 = h.thiefRank();
    if (num3 > 0) {
      msg =
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("T:")
                                      .append(h.thief())
                                      .append("/")
                                      .append(num3)
                                      .append("  "))))));
    }
    int num4 = h.ieatsuRank();
    if (num4 > 0) {
      msg =
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("S:")
                                      .append(h.ieatsu())
                                      .append("/")
                                      .append(num4)
                                      .append("  "))))));
    }
    return msg;
  }

  public boolean mouseMove(Event e, int x, int y) {
    Item what =
        !gearRect.inside(x, y) ? null : Screen.getHero().findGearTrait(slot[(y - gearRect.y) / 20]);
    if (what == this.over) {
      return false;
    }
    this.over = (itArms) what;
    repaint();
    return false;
  }

  @Override // DCourt.Screens.Screen
  public boolean mouseDown(Event e, int x, int y) {
    if (!gearRect.inside(x, y)) {
      return false;
    }
    if (this.table.getSelect() >= 0) {
      this.table.setSelect(-1);
      this.pick = null;
    }
    Item what = Screen.getHero().findGearTrait(slot[(y - gearRect.y) / 20]);
    if (!(what instanceof itArms)) {
      return false;
    }
    this.over = (itArms) what;
    if (this.over == this.pick) {
      this.pick = null;
    } else {
      this.pick = this.over;
    }
    repaint();
    return true;
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == this.table) {
      this.pick = Screen.getPack().select(this.table.getSelect());
    } else if (e.target == this.action[0]) {
      usePick();
    } else if (e.target == this.action[1]) {
      detailItem(this.pick);
    } else if (e.target == this.action[2]) {
      peerHero();
    } else if (e.target == this.action[3]) {
      dumpItem();
    } else if (e.target == this.action[4]) {
      backDump();
    } else if (e.target == this.action[5] || e.target == getPic(0)) {
      Tools.setRegion(getHome());
    }
    updateTools();
    repaint();
    return true;
  }

  void detailItem(Item what) {
    if (what != null) {
      Tools.setRegion(new arDetail(this, what));
    }
  }

  void peerHero() {
    Tools.setRegion(new arPeer(this, 2, Screen.getHero().getName()));
  }

  void usePick() {
    if ((this.fight && Screen.getHero().actCount() < 1) || !GearTable.find(this.pick)) {
      return;
    }
    if (this.state == 1) {
      if (this.pick instanceof itArms) {
        performEffect(this.useItem);
      }
      setStateWait();
    } else if (this.state != 0) {
    } else {
      if (this.pick instanceof itArms) {
        enactGear();
      } else if (GearTable.isScroll(this.pick)) {
        setStateTarget();
      } else if (GearTable.canHeroUse(this.pick)) {
        this.useItem = this.pick;
        performEffect(this.pick);
      }
    }
  }

  void dumpItem() {
    if (this.pick != null) {
      if (this.table.getSelect() >= 0) {
        int old = this.table.getSelect();
        if (removePack(this.pick)) {
          getDump().insert(this.pick);
          this.table.setSelect(old);
          this.pick = Screen.selectPack(this.table.getSelect());
        }
      } else if (Screen.getGear().indexOf(this.pick) >= 0
          && (this.pick instanceof itArms)
          && !((itArms) this.pick).hasTrait(ArmsTrait.CURSE)
          && Screen.getGear().drop(this.pick) != null) {
        getDump().insert(this.pick);
        this.pick = null;
      }
    }
  }

  void backDump() {
    Item it = getDump().select(0);
    if (it != null) {
      getDump().drop(it);
      insertPack(it);
    }
  }

  void enactGear() {
    if (Screen.getPack().indexOf(this.pick) >= 0) {
      wearGear();
    } else if (Screen.getGear().indexOf(this.pick) >= 0) {
      removeGear((itArms) this.pick);
    }
    if (this.fight) {
      Screen.getHero().useAction();
    }
    Screen.getHero().calcCombat();
  }

  void wearGear() {
    itArms what = (itArms) this.pick;
    int slots = 0;
    for (int ix = 0; ix < 5; ix++) {
      if (what.hasTrait(slot[ix])) {
        if (removeGear(Screen.getGear().findArms(slot[ix]))) {
          slots++;
        } else {
          return;
        }
      }
    }
    if (slots >= 1) {
      what.revealCurse();
      removePack(what);
      itList gear = Screen.getGear();
      this.pick = what;
      gear.append(what);
    }
  }

  boolean removeGear(itArms what) {
    if (what == null) {
      return true;
    }
    if (what.isCursed()) {
      Tools.setRegion(
          new arNotice(
              this,
              String.valueOf(
                  String.valueOf(
                      new StringBuffer("\tYou can't remove the ")
                          .append(what.getName())
                          .append("!  The @&$#~ thing is Cursed %#$@!\n")))));
      return false;
    }
    Screen.getGear().drop(what);
    insertPack(what);
    return true;
  }

  void setStateWait() {
    if (this.state != 0) {
      this.state = 0;
      this.useItem = null;
      this.effect = 0;
    }
  }

  void setStateTarget() {
    if (this.state != 1) {
      this.state = 1;
      this.useItem = this.pick;
      this.effect = GearTable.getEffect(this.pick);
      this.pick = null;
    }
  }

  void performEffect(Item source) {
    if (tryEffect(source)) {
      this.attack = this.fight && !Screen.getHero().hasTrait("Panic");
      if (this.fight) {
        Screen.getHero().act();
      }
      if (this.useItem.getCount() == 1) {
        removePack(this.useItem);
        this.pick = null;
      } else {
        Screen.subPack(this.useItem.getName(), 1);
        updatePack(this.useItem);
      }
      repaint();
    }
  }

  boolean tryEffect(Item source) {
    switch (GearTable.getEffect(source)) {
      case 1:
        effectIdentify((itArms) this.pick);
        return true;
      case 2:
        Screen.getHero().doHeal();
        return true;
      case 3:
        Screen.getHero().doCure();
        return true;
      case 4:
        Screen.getHero().doBlind();
        return true;
      case 5:
        Screen.getHero().doPanic();
        return true;
      case 6:
        Screen.getHero().doBlast();
        return true;
      case 7:
        Screen.getHero().doRevive();
        return true;
      case 8:
        Screen.getHero().doHaste();
        return true;
      case 9:
        Screen.getHero().doRefresh();
        return true;
      case 10:
        Screen.getHero().doCookie();
        return true;
      case 11:
        Screen.getHero().doYouth();
        return true;
      case GearTypes.EFF_AGING:
        Screen.getHero().doAging();
        return true;
      case GearTypes.EFF_FACELESS:
        effectFaceless();
        return true;
      case GearTypes.EFF_SCRIBE:
        effectScribe(this.pick);
        return true;
      case GearTypes.EFF_GLOW:
        effectGlow((itArms) this.pick);
        return true;
      case GearTypes.EFF_BLESS:
        effectBless((itArms) this.pick);
        return true;
      case GearTypes.EFF_LUCK:
        effectLuck((itArms) this.pick);
        return true;
      case GearTypes.EFF_FLAME:
        effectFlame((itArms) this.pick);
        return true;
      case GearTypes.EFF_ENCHANT:
        effectEnchant((itArms) this.pick);
        return true;
      case GearTypes.EFF_GRANT:
        effectGrant(this.pick);
        return true;
      case GearTypes.EFF_FOOD:
        Screen.getHero().doFood();
        return true;
      default:
        return false;
    }
  }

  void effectFaceless() {
    Screen.getHero().doFaceless();
    Tools.setRegion(
        new arNotice(
            new arPeer(this, 2, Screen.getHero().getName()),
            "\tYou feel your features dissolve into an indistinct and shapeless form."));
  }

  void effectScribe(Item what) {
    Tools.setRegion(new arScribe(this, what.getName()));
  }

  void effectGrant(Item what) {
    Tools.setRegion(new arNotice(this, Screen.getHero().doGrant((itNote) what)));
  }

  boolean tryScroll(itArms what) {
    if (Tools.contest(Screen.getHero().getWits(), what.getPower())) {
      return true;
    }
    Tools.setRegion(
        new arNotice(
            this,
            String.valueOf(
                String.valueOf(
                    new StringBuffer("\tThe mass and material of the ")
                        .append(what.getName())
                        .append(" resists the power of your spell.  This scroll has been ")
                        .append("inneffective.\n")))));
    return false;
  }

  void effectGlow(itArms what) {
    addArmsTrait(what, ArmsTrait.GLOWS);
  }

  void effectLuck(itArms what) {
    addArmsTrait(what, ArmsTrait.LUCKY);
  }

  void effectFlame(itArms what) {
    addArmsTrait(what, ArmsTrait.FLAME);
  }

  void addArmsTrait(itArms what, String trait) {
    if (tryScroll(what)) {
      what.fixTrait(trait);
      detailItem(what);
    }
  }

  void effectIdentify(itArms what) {
    if (tryScroll(what)) {
      if (what.hasTrait(ArmsTrait.SECRET)) {
        what.clrTrait(ArmsTrait.SECRET);
      } else {
        what.revealCurse();
      }
      detailItem(what);
    }
  }

  void effectBless(itArms what) {
    if (tryScroll(what)) {
      if (what.isCursed()) {
        what.clrTrait(ArmsTrait.CURSED);
        what.clrTrait(ArmsTrait.CURSE);
        Tools.setRegion(
            new arNotice(
                this,
                String.valueOf(
                    String.valueOf(
                        new StringBuffer("\tThe ")
                            .append(what.getName())
                            .append(" flashes and sparkles first red ")
                            .append("then blue as a terrible curse is lifted...\n")))));
        return;
      }
      what.fixTrait(ArmsTrait.BLESS);
      detailItem(what);
    }
  }

  void effectEnchant(itArms what) {
    if (tryScroll(what)) {
      what.incEnchant();
      int power = what.getPower();
      int dif = what.getEnchant() - what.getPower();
      if (dif < 0) {
        detailItem(what);
      } else if (!Tools.contest(dif, power)) {
        Tools.setRegion(
            new arNotice(
                new arDetail(this, what),
                String.valueOf(
                    String.valueOf(
                        new StringBuffer("\tThe ")
                            .append(what.getName())
                            .append(" pulses with a dangerous purple light.")))));
      } else {
        if (Screen.getGear().drop(what) == null) {
          removePack(what);
        }
        String msg =
            String.valueOf(
                String.valueOf(
                    new StringBuffer("\tThere is a hot, steamy explosion as your ")
                        .append(what.getName())
                        .append(" suddenly disintegrates into whirling, melting, whining ")
                        .append("and floating fragments!  The magical energies penetrate ")
                        .append("your body causing -")
                        .append(dif)
                        .append(" wounds!\n")));
        Screen.getHero().addWounds(dif);
        if (Screen.getHero().isDead()) {
          Tools.setRegion(
              Screen.getHero()
                  .killedScreen(
                      getHome(),
                      String.valueOf(String.valueOf(msg)).concat("\n\tYou have been killed!\n"),
                      false));
        } else {
          Tools.setRegion(new arNotice(this, msg));
        }
      }
    }
  }
}
