package DCourt.Screens.Quest;

import java.awt.Color;
import java.awt.Event;
import java.awt.FontMetrics;
import java.awt.Graphics;

import DCourt.Items.itList;
import DCourt.Items.List.itHero;
import DCourt.Items.List.itMonster;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Screens.Utility.arStatus;
import DCourt.Static.Constants;
import DCourt.Static.GearTypes;
import DCourt.Static.QuestStrings;
import DCourt.Tools.Breaker;
import DCourt.Tools.DrawTools;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Quest/arQuest.class */
public class arQuest extends Screen implements QuestStrings, GearTypes {
  private itMonster mob;
  private itHero hero;
  private Options opt;
  private int weight;
  private Screen gate;
  static final String trains =
      "\tYou are taken to a remote location in the forest where you undergo a bizzare training regimen.  You shower naked beneath a freezing waterfall.  You eat nothing but rice and fish.  You must sit for hours in a lotus position while  contemplating the sound of a single hand clapping.\n\tWhen the time comes to draw your blade, you find that an unheralded clarity of vision guides your stroke.\n\t\t\tYour Training is Complete\n\n<<< You Have Gained in Samurai Skill >>>\n\n\t*** The Cost to Your Body is Severe ***\n\t*** -3 Guts  -3 Wits  -3 Charm ***\n";

  public arQuest(Screen a, Screen n, int w, String m, itMonster b) {
    this(a, w, m, b);
    this.gate = n;
  }

  public arQuest(Screen from, int wgt, String msg, itMonster beast) {
    super(from, msg);
    this.opt = null;
    this.weight = 0;
    this.gate = null;
    this.gate = getHome();
    setBackground(new Color(255, 255, 0));
    setForeground(Color.black);
    setFont(Tools.courtF);
    this.weight = wgt;
    this.hero = Screen.getHero();
    this.mob = (itMonster) beast.copy();
    this.mob.balance(this.weight);
    this.opt = new Options(this.mob.getOptions());
    this.opt.reshape(180, 20, 200, 110);
    this.hero.addFatigue(1);
    this.hero.resetActions();
    this.mob.resetActions();
    this.mob.chooseActions(true);
  }

  @Override // DCourt.Screens.Screen
  public void init() {
    super.init();
    if (Screen.getActions().isMatch(Constants.SPELLS)) {
      Tools.setRegion(applyChoice(16));
    }
    this.opt.fixList();
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    addPic(this.mob.getPicture());
    getPic(0).reshape(10, 10, 160, 160);
    add(this.opt);
    super.addTools();
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    g.setFont(Tools.courtF);
    g.setColor(Color.blue);
    DrawTools.center(g, getTitle(), 280, 15);
    g.setColor(new Color(0, 128, 0));
    int hurt = this.mob.getWounds();
    g.drawString(
        String.valueOf(
            String.valueOf(
                new StringBuffer("Guts: ")
                    .append(this.mob.getGuts() - hurt)
                    .append(
                        hurt > 0
                            ? "/".concat(String.valueOf(String.valueOf(this.mob.getGuts())))
                            : ""))),
        180,
        145);
    DrawTools.right(g, this.mob.getWeapon(), 390, 145);
    g.drawString("Wits: ".concat(String.valueOf(String.valueOf(this.mob.getWits()))), 180, 165);
    DrawTools.right(g, this.mob.getArmour(), 390, 165);
    g.setColor(Color.black);
    g.setFont(Tools.questF);
    FontMetrics fm = g.getFontMetrics(g.getFont());
    int jump = fm.getAscent() + fm.getDescent();
    Breaker lines = new Breaker(this.mob.getText(), fm, 380, false);
    int ix = 0;
    while (ix < 5 && ix < lines.lineCount()) {
      g.drawString(lines.getLine(ix), 10, 175 + fm.getAscent() + (ix * jump));
      ix++;
    }
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == Tools.statusPic) {
      Tools.setRegion(new arStatus(this, true));
    }
    if (e.target != this.opt) {
      return true;
    }
    Tools.setRegion(applyChoice(this.opt.select()));
    return true;
  }

  public void battleActionResult() {
    Screen next;
    if (this.hero.isDead()) {
      next = this.hero.killedScreen(getHome(), null, true);
    } else if (this.hero.isControl()) {
      next = heroControls("");
    } else if (this.hero.isSwindle()) {
      next = heroSwindles();
    } else if (this.mob.isDead()) {
      next = heroWins();
    } else if (this.mob.isControl()) {
      next = mobControls("");
    } else if (this.mob.isSwindle()) {
      next = mobSwindles();
    } else {
      this.hero.resetActions();
      this.opt.nextRound(this.hero, this.mob);
      this.mob.resetActions();
      this.mob.chooseActions(false);
      next = this;
    }
    Tools.setRegion(next);
  }

  Screen applyChoice(int choice) {
    itList ma = this.mob.getActions();
    itList ha = this.hero.getActions();
    switch (choice) {
      case 0:
        return tryBribe();
      case 1:
        return trySupply(GearTypes.FOOD, 1);
      case 2:
        return tryRiddle();
      case 3:
        return tryTrade();
      case 4:
        return tryAssist();
      case 5:
        return trySeduce();
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      default:
        switch (choice) {
          case 6:
            ha.setName("Control");
            break;
          case 7:
            ha.setName(Constants.BACKSTAB);
            break;
          case 8:
            ha.setName(Constants.BERZERK);
            break;
          case 9:
            ha.setName("Swindle");
            break;
          case 10:
            ha.setName(Constants.IEATSU);
            break;
          case 11:
            ha.setName(Constants.ATTACK);
            break;
          case GearTypes.EFF_BLESS:
            ha.setName(Constants.SPELLS);
            break;
        }
        if (ma.isMatch(Constants.RUNAWAY)) {
          return mobFlees();
        }
        if (ha.isMatch(ma.getName())) {
          switch (choice) {
            case 6:
              return stareDown();
            case 9:
              return swapGoods();
          }
        }
        return new arBattle(this, null);
      case GearTypes.EFF_AGING:
        return tryFlee();
      case GearTypes.EFF_FACELESS:
        return trySupply(GearTypes.FISH, 13);
      case GearTypes.EFF_SCRIBE:
        return tryToken();
      case GearTypes.EFF_GLOW:
        return tryCapture();
    }
  }

  public itMonster getMob() {
    return this.mob;
  }

  Screen heroWins() {
    String msg;
    int exp =
        this.mob.baseExp()
            + (((((2 * this.mob.getGuts()) + this.mob.getWits()) + this.mob.getCharm())
                    * this.weight)
                / 4);
    this.hero.setState(Constants.VICTORY);
    String msg2 =
        String.valueOf(
                String.valueOf(
                    String.valueOf(
                        String.valueOf(
                            new StringBuffer("You have slain the ")
                                .append(this.mob.getName())
                                .append(" with tremendous valor.\n")))))
            .concat(
                String.valueOf(
                    String.valueOf(Screen.packString("\nYou Find: ", this.mob.getPack()))));
    this.hero.getPack().merge(this.mob.getPack());
    if (this.hero.getOverload() > 0) {
      msg2 = String.valueOf(String.valueOf(msg2)).concat("*** YOUR PACK IS OVERLOADED ***\n");
    }
    String msg3 =
        String.valueOf(String.valueOf(msg2))
            .concat(String.valueOf(String.valueOf(this.hero.gainExp(exp))));
    this.hero.addStatus(Constants.FAME, this.mob.baseFame());
    if (this.hero.getActions().isMatch(Constants.BACKSTAB)) {
      msg =
          String.valueOf(
                  String.valueOf(
                      String.valueOf(String.valueOf(msg3))
                          .concat(
                              String.valueOf(
                                  String.valueOf(this.hero.gainCharm(this.weight * 3))))))
              .concat(String.valueOf(String.valueOf(this.hero.gainGuts(this.weight * 2))));
    } else if (this.hero.getActions().isMatch(Constants.BERZERK)) {
      msg =
          String.valueOf(String.valueOf(msg3))
              .concat(String.valueOf(String.valueOf(this.hero.gainGuts(this.weight * 5))));
    } else {
      msg =
          String.valueOf(String.valueOf(msg3))
              .concat(String.valueOf(String.valueOf(this.hero.gainGuts(this.weight))));
    }
    return new arNotice(this.gate, msg);
  }

  Screen tryBribe() {
    String msg;
    int exp = this.mob.baseExp();
    int num = (this.weight * (this.mob.getGuts() + this.mob.getWits())) / 2;
    int cost = this.hero.subMoney(num);
    if (cost >= num && Tools.contest(this.hero.bribeCharm(), this.mob.bribeCharm())) {
      String msg2 =
          String.valueOf(
                  String.valueOf(
                      String.valueOf(
                              String.valueOf(
                                  String.valueOf(
                                      String.valueOf(
                                          new StringBuffer(
                                                  "\tYou have averted conflict by paying the ")
                                              .append(this.mob.getName())
                                              .append(" ")
                                              .append(cost)
                                              .append(" marks...\n")))))
                          .concat(String.valueOf(String.valueOf(this.hero.gainExp(exp))))))
              .concat(String.valueOf(String.valueOf(this.hero.gainCharm(this.weight))));
      this.hero.subFatigue(1);
      return new arNotice(this.gate, msg2);
    } else if (this.mob.isAggresive()) {
      this.mob.addMoney(cost);
      return new arBattle(
          this,
          String.valueOf(
              String.valueOf(
                  new StringBuffer("The ")
                      .append(this.mob.getName())
                      .append(" takes ")
                      .append(cost)
                      .append(" marks, then attacks!\n"))));
    } else {
      if (this.mob.isHostile()) {
        this.mob.addMoney(cost);
        msg =
            String.valueOf(
                String.valueOf(
                    new StringBuffer("\tThe ")
                        .append(this.mob.getName())
                        .append(" takes ")
                        .append(cost)
                        .append(" marks, then ")
                        .append("laughs at you!")));
      } else {
        this.hero.addMoney(cost);
        msg =
            String.valueOf(
                String.valueOf(
                    new StringBuffer("The ")
                        .append(this.mob.getName())
                        .append(" refuses your money...")));
      }
      this.opt.remove(0);
      return new arNotice(this, msg);
    }
  }

  Screen trySupply(String id, int choice) {
    String msg;
    int exp = this.mob.baseExp();
    int num = (this.mob.getGuts() + 4) / 5;
    int cost = this.hero.subPack(id, num);
    if (cost >= num && Tools.contest(this.hero.feedCharm(), this.mob.feedCharm())) {
      String msg2 =
          String.valueOf(
                  String.valueOf(
                      String.valueOf(
                              String.valueOf(
                                  String.valueOf(
                                      String.valueOf(
                                          new StringBuffer("The ")
                                              .append(this.mob.getName())
                                              .append(" chows down on ")
                                              .append(cost)
                                              .append(" ")
                                              .append(id)
                                              .append(
                                                  ", the waddles away with satisfaction...\n")))))
                          .concat(String.valueOf(String.valueOf(this.hero.gainExp(exp))))))
              .concat(String.valueOf(String.valueOf(this.hero.gainCharm(this.weight))));
      this.hero.subFatigue(1);
      return new arNotice(this.gate, msg2);
    } else if (this.mob.isAggresive()) {
      return new arBattle(
          this,
          String.valueOf(
              String.valueOf(
                  new StringBuffer("The ")
                      .append(this.mob.getName())
                      .append(" eats your ")
                      .append(id)
                      .append("...")
                      .append("then it attacks!\n"))));
    } else {
      if (this.mob.isHostile()) {
        msg =
            String.valueOf(
                String.valueOf(
                    new StringBuffer("The ")
                        .append(this.mob.getName())
                        .append(" eats ")
                        .append(cost)
                        .append(" ")
                        .append(id)
                        .append("...")
                        .append("then blocks your path!\n")));
      } else {
        this.hero.addPack("food", cost);
        msg =
            String.valueOf(
                String.valueOf(
                    new StringBuffer("The ")
                        .append(this.mob.getName())
                        .append(" turns up it's ")
                        .append("nose up at your ")
                        .append(id)
                        .append("...\n")));
      }
      this.opt.remove(choice);
      return new arNotice(this, msg);
    }
  }

  Screen tryTrade() {
    int exp = this.mob.baseExp();
    int num = this.weight * (this.mob.getCharm() + this.mob.getWits());
    int cost = this.hero.subMoney(num);
    itList gear = new itList(this.mob.getPack());
    gear.zero("marks");
    if (!gear.isEmpty()
        && cost >= num
        && Tools.contest(this.hero.tradeCharm(), this.mob.tradeCharm())) {
      String msg =
          String.valueOf(
                  String.valueOf(
                      "You flash your marks at it until an agreeable price is reached...\n"))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("\nYou spend ")
                                      .append(cost)
                                      .append(" marks.\n"))))));
      this.mob.getPack().zero("marks");
      String msg2 =
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(Screen.packString("\nYou Recieve: ", this.mob.getPack()))));
      this.hero.getPack().merge(this.mob.getPack());
      return new arNotice(
          this.gate,
          String.valueOf(
                  String.valueOf(
                      String.valueOf(String.valueOf(msg2))
                          .concat(String.valueOf(String.valueOf(this.hero.gainExp(exp))))))
              .concat(String.valueOf(String.valueOf(this.hero.gainCharm(this.weight)))));
    } else if (this.mob.isAggresive()) {
      this.mob.addMoney(cost);
      return new arBattle(
          this,
          String.valueOf(
              String.valueOf(
                  new StringBuffer("\tThe ")
                      .append(this.mob.getName())
                      .append(" takes ")
                      .append(cost)
                      .append(" marks, then attacks!\n"))));
    } else if (this.mob.isHostile()) {
      this.mob.addMoney(cost);
      this.mob.setPassive();
      this.mob.getActions().setName(Constants.ATTACK);
      return mobFlees(
          String.valueOf(
              String.valueOf(new StringBuffer("\tIt steals ").append(cost).append(" marks!\n"))));
    } else {
      this.hero.addMoney(cost);
      String msg3 =
          String.valueOf(
              String.valueOf(
                  new StringBuffer("The ")
                      .append(this.mob.getName())
                      .append(" shows no interest in trading...\n")));
      this.opt.remove(3);
      return new arNotice(this, msg3);
    }
  }

  Screen tryAssist() {
    String msg;
    int exp = this.mob.baseExp();
    if (Tools.contest(this.hero.getWits(), this.mob.getWits())) {
      String msg2 =
          String.valueOf(
              String.valueOf(
                  new StringBuffer("\tYou manage to fix the problem!\n\n\tThe ")
                      .append(this.mob.getName())
                      .append(" is indebted to you for ")
                      .append("your kind assistance...\n")));
      this.mob.getPack().loseHalf();
      String msg3 =
          String.valueOf(String.valueOf(msg2))
              .concat(
                  String.valueOf(
                      String.valueOf(Screen.packString("\nYou Recieve: ", this.mob.getPack()))));
      this.hero.getPack().merge(this.mob.getPack());
      this.hero.addStatus(Constants.FAME, this.weight);
      return new arNotice(
          this.gate,
          String.valueOf(
                  String.valueOf(
                      String.valueOf(String.valueOf(msg3))
                          .concat(String.valueOf(String.valueOf(this.hero.gainExp(exp))))))
              .concat(String.valueOf(String.valueOf(this.hero.gainWits(this.weight)))));
    } else if (this.mob.isAggresive()) {
      return new arBattle(
          this,
          String.valueOf(
              String.valueOf(
                  new StringBuffer("\tThe ")
                      .append(this.mob.getName())
                      .append(" attacks while you busy!\n"))));
    } else {
      if (this.mob.isHostile()) {
        msg =
            String.valueOf(String.valueOf("\tYou can't seem to solve the problem...\n"))
                .concat(
                    String.valueOf(
                        String.valueOf(
                            String.valueOf(
                                String.valueOf(
                                    new StringBuffer("\n\tThe ")
                                        .append(this.mob.getName())
                                        .append(" calls you a worthless loser!\n"))))));
      } else {
        msg =
            String.valueOf(String.valueOf("\tYou can't seem to solve the problem...\n"))
                .concat(
                    String.valueOf(
                        String.valueOf(
                            String.valueOf(
                                String.valueOf(
                                    new StringBuffer("\n\tThe ")
                                        .append(this.mob.getName())
                                        .append(" thanks you for your efforts.\n"))))));
      }
      this.opt.remove(4);
      return new arNotice(this, msg);
    }
  }

  Screen tryRiddle() {
    int exp = this.mob.baseExp();
    int which = Tools.roll(QuestStrings.riddle.length);
    String msg =
        String.valueOf(
            String.valueOf(
                new StringBuffer("\t").append(QuestStrings.riddle[which]).append("\n\n\t")));
    if (!Tools.contest(this.hero.getWits(), this.mob.getWits())) {
      String msg2 =
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                                  String.valueOf(
                                      QuestStrings.guess[Tools.roll(QuestStrings.guess.length)]))
                              .concat("\n"))));
      this.opt.remove(2);
      if (this.mob.isAggresive() || this.mob.isHostile()) {
        return new arBattle(
            this, String.valueOf(String.valueOf(msg2)).concat("\n\tWRONG!! SCREEEEECH!!!\n"));
      }
      return new arNotice(
          this,
          String.valueOf(String.valueOf(msg2))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("\n\tThe ")
                                      .append(this.mob.getName())
                                      .append(" shakes its head.\n")))))));
    }
    String msg3 =
        String.valueOf(String.valueOf(msg))
            .concat(
                String.valueOf(
                    String.valueOf(
                        String.valueOf(String.valueOf(QuestStrings.answer[which]))
                            .concat("\n\n\tGARRGH! THAT'S RIGHT!!\n"))));
    this.mob.getPack().loseHalf();
    String msg4 =
        String.valueOf(String.valueOf(msg3))
            .concat(
                String.valueOf(
                    String.valueOf(Screen.packString("\nYou Recieve: ", this.mob.getPack()))));
    this.hero.getPack().merge(this.mob.getPack());
    String msg5 =
        String.valueOf(String.valueOf(msg4))
            .concat(String.valueOf(String.valueOf(this.hero.gainExp(exp))));
    this.hero.addStatus(Constants.FAME, this.weight);
    return new arNotice(
        this.gate,
        String.valueOf(String.valueOf(msg5))
            .concat(String.valueOf(String.valueOf(this.hero.gainWits(this.weight)))));
  }

  Screen trySeduce() {
    int exp = this.mob.baseExp();
    String msg =
        String.valueOf(
            String.valueOf(
                new StringBuffer("\tYou waggle your eyebrows and make kissing noises towards ")
                    .append(this.mob.getName())
                    .append(".\n")));
    if (Tools.contest(this.hero.seduceCharm(), this.mob.seduceCharm())) {
      String msg2 =
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("\tIt smiles and slinks on over. ")
                                      .append(Tools.select(QuestStrings.seduces))
                                      .append("\n\tAfterwards, ")
                                      .append(this.mob.getName())
                                      .append(" gives you a small token of ")
                                      .append("affection\n"))))));
      this.mob.getPack().loseHalf();
      String msg3 =
          String.valueOf(String.valueOf(msg2))
              .concat(
                  String.valueOf(
                      String.valueOf(Screen.packString("\nYou Recieve: ", this.mob.getPack()))));
      this.hero.getPack().merge(this.mob.getPack());
      this.hero.addStatus(Constants.FAME, this.weight);
      return new arNotice(
          this.gate,
          String.valueOf(
                  String.valueOf(
                      String.valueOf(String.valueOf(msg3))
                          .concat(String.valueOf(String.valueOf(this.hero.gainExp(exp))))))
              .concat(String.valueOf(String.valueOf(this.hero.gainCharm(this.weight)))));
    } else if (!this.mob.isPassive()) {
      return new arBattle(
          this,
          String.valueOf(String.valueOf(msg))
              .concat("\tIt shrieks with fury at your shallow lies and attacks!\n"));
    } else {
      return new arNotice(
          getHome(),
          String.valueOf(String.valueOf(msg)).concat("\tIt shrieks and runs away, giggling.\n"));
    }
  }

  Screen tryFlee() {
    int exp = this.mob.baseExp() + (this.mob.getWits() / 5);
    int tf = this.hero.thiefRank();
    if (this.mob.isPassive() || this.mob.isDefensive()) {
      this.hero.subFatigue(1);
      return new arNotice(
          getHome(),
          String.valueOf(
                  String.valueOf("\tYou run like the wind.  Fear lending flight to thy heels.\n"))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("\tThe ")
                                      .append(this.mob.getName())
                                      .append(" makes no effort to pursue.\n")))))));
    } else if (this.mob.isHostile()) {
      String msg =
          String.valueOf(
                  String.valueOf("\tYou run like the wind.  Fear lending flight to thy heels.\n"))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("\tThe ")
                                      .append(this.mob.getName())
                                      .append(" chases you for a while ")
                                      .append("just to make sure you aren't coming back\n"))))));
      if (tf >= 2) {
        msg =
            String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(thiefRun())));
      }
      return new arNotice(getHome(), msg);
    } else {
      int hs = this.hero.runWits();
      if (this.mob.hasTrait(Constants.BANDIT)) {
        hs /= 2;
      }
      if (!Tools.contest(hs, this.mob.runWits())) {
        return new arBattle(
            this,
            String.valueOf(
                    String.valueOf("\tYou run like the wind.  Fear lending flight to thy heels.\n"))
                .concat(
                    String.valueOf(
                        String.valueOf(
                            String.valueOf(
                                String.valueOf(
                                    new StringBuffer("\tBut the ")
                                        .append(this.mob.getName())
                                        .append(" proves to be swifter!\n")))))));
      }
      String msg2 =
          String.valueOf(
              String.valueOf(
                  new StringBuffer("\tThe ")
                      .append(this.mob.getName())
                      .append(" is left behind, panting. ")
                      .append("Your fleet feet have just saved your skin.\n")));
      if (tf >= 3) {
        msg2 =
            String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(thiefRun())));
      }
      return new arNotice(
          getHome(),
          String.valueOf(
                  String.valueOf(
                      String.valueOf(String.valueOf(msg2))
                          .concat(String.valueOf(String.valueOf(this.hero.gainExp(exp))))))
              .concat(String.valueOf(String.valueOf(this.hero.gainWits(this.weight)))));
    }
  }

  Screen stareDown() {
    this.mob.magic(1);
    this.hero.magic(1);
    this.opt.redraw();
    String msg =
        String.valueOf(
            String.valueOf(
                new StringBuffer("\tYou and the ")
                    .append(this.mob.getName())
                    .append(" lock stares ")
                    .append("in a ferocious contest of wills...")));
    if (!Tools.contest(this.hero.getWits(), this.mob.getWits())) {
      return mobControls(String.valueOf(String.valueOf(msg)).concat("You blink first!\n"));
    }
    return heroControls(String.valueOf(String.valueOf(msg)).concat("It blinks first!\n"));
  }

  Screen heroControls(String msg) {
    int exp = this.mob.baseExp() + this.mob.getWits();
    String msg2 =
        String.valueOf(
                String.valueOf(
                    String.valueOf(String.valueOf(msg))
                        .concat(
                            String.valueOf(
                                String.valueOf(
                                    String.valueOf(
                                        String.valueOf(
                                            new StringBuffer(
                                                    "\tFirst you take all its treasures. Then you ")
                                                .append(
                                                    QuestStrings.controls[
                                                        Tools.roll(QuestStrings.controls.length)])
                                                .append("\n"))))))))
            .concat(
                String.valueOf(
                    String.valueOf(Screen.packString("\nYou Recieve: ", this.mob.getPack()))));
    Screen.getPack().merge(this.mob.getPack());
    return new arNotice(
        this.gate,
        String.valueOf(
                String.valueOf(
                    String.valueOf(String.valueOf(msg2))
                        .concat(String.valueOf(String.valueOf(this.hero.gainExp(exp))))))
            .concat(String.valueOf(String.valueOf(this.hero.gainWits(this.weight * 5)))));
  }

  Screen mobControls(String msg) {
    if (msg == null) {
      msg = "";
    }
    if (this.mob.isAggresive()) {
      return new arNotice(
          this.hero.killedScreen(getHome(), null, true),
          String.valueOf(String.valueOf(msg))
              .concat(
                  "\tOut of sheer maliciousness, the creature sends you on a long hike over a short cliff.\n"));
    }
    QuestStrings.controlled[0] =
        String.valueOf(
            String.valueOf(
                new StringBuffer("convinces you that you are ")
                    .append(Tools.getBest())
                    .append(".")));
    if (this.mob.isPassive()) {
      return new arNotice(
          getHome(),
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("\nThe ")
                                      .append(this.mob.getName())
                                      .append(" expresses its anger when it ")
                                      .append(Tools.select(QuestStrings.controlled))
                                      .append("\n")))))));
    }
    this.hero.getPack().loseHalf();
    return new arNotice(
        getHome(),
        String.valueOf(String.valueOf(msg))
            .concat(
                String.valueOf(
                    String.valueOf(
                        String.valueOf(
                            String.valueOf(
                                new StringBuffer("\n\tFirst the ")
                                    .append(this.mob.getName())
                                    .append(" takes half your gear, then it ")
                                    .append(Tools.select(QuestStrings.controlled))
                                    .append("\n")))))));
  }

  Screen swapGoods() {
    this.mob.thief(1);
    this.hero.thief(1);
    this.opt.nextRound(this.hero, this.mob);
    return new arNotice(
        this,
        String.valueOf(
            String.valueOf(
                new StringBuffer("\tYou and the ")
                    .append(this.mob.getName())
                    .append(" start trading gear, ")
                    .append("and before you know it... You are right back where ")
                    .append("you started???\n"))));
  }

  Screen heroSwindles() {
    int exp = this.mob.baseExp() + this.mob.getCharm();
    if (this.mob.subPack(GearTypes.INSURANCE, 1) == 1) {
      String msg =
          String.valueOf(
              String.valueOf(
                  new StringBuffer("\tYou start 'trading' in earnest with the ")
                      .append(this.mob.getName())
                      .append(", rooting through its back pack as it nods ")
                      .append("enthusiastically, when you find a magic coupon: ")
                      .append("Thief Insurance.  Grumbling, you take the receipt ")
                      .append("and walk away.\n")));
      Screen.addPack(GearTypes.INSURANCE, 1);
      return new arNotice(getHome(), msg);
    }
    String msg2 =
        String.valueOf(
                String.valueOf(
                    String.valueOf(
                        String.valueOf(
                            new StringBuffer("\tYou lay out a complicated deal that the ")
                                .append(this.mob.getName())
                                .append(" is unable to follow.  By the time you are finished, ")
                                .append("it is paying you to take all its equipment.\n")))))
            .concat(
                String.valueOf(
                    String.valueOf(Screen.packString("\nYou Recieve: ", this.mob.getPack()))));
    Screen.getPack().merge(this.mob.getPack());
    return new arNotice(
        this.gate,
        String.valueOf(
                String.valueOf(
                    String.valueOf(String.valueOf(msg2))
                        .concat(String.valueOf(String.valueOf(this.hero.gainExp(exp))))))
            .concat(String.valueOf(String.valueOf(this.hero.gainCharm(this.weight * 5)))));
  }

  Screen mobSwindles() {
    if (this.hero.subPack(GearTypes.INSURANCE, 1) == 1) {
      return new arNotice(
          getHome(),
          String.valueOf(
              String.valueOf(
                  new StringBuffer("\tThe ")
                      .append(this.mob.getName())
                      .append(" starts to tell you about this ")
                      .append("terrific bridge for sale in Brook Land, then it ")
                      .append("spies your Thief Insurance.  Grumbling, it snags ")
                      .append("the coupon and makes an escape!\n"))));
    } else if (this.mob.isAggresive()) {
      String msg =
          String.valueOf(
              String.valueOf(
                  new StringBuffer("\tThe ")
                      .append(this.mob.getName())
                      .append(" starts to show you the ")
                      .append("benefits of trading goods.  Pretty soon ")
                      .append("you walk away completely satisfied with ")
                      .append("an empty backpack.\n")));
      this.hero.getPack().clrQueue();
      return new arNotice(getHome(), msg);
    } else if (this.mob.isPassive()) {
      String msg2 =
          String.valueOf(
              String.valueOf(
                  new StringBuffer("\tThe ")
                      .append(this.mob.getName())
                      .append(" sells you a 'magic' rock ")
                      .append("for ")
                      .append(this.hero.getMoney())
                      .append(" marks, and leaves you a ")
                      .append("satisfied customer.\n")));
      this.hero.getPack().zero("Marks");
      Screen.addPack("Rock", 1);
      return new arNotice(getHome(), msg2);
    } else {
      int cash = this.hero.getMoney();
      this.hero.getPack().loseHalf();
      this.hero.fixPack("Marks", cash / 2);
      return new arNotice(
          getHome(),
          String.valueOf(
              String.valueOf(
                  new StringBuffer("\tThe ")
                      .append(this.mob.getName())
                      .append(" makes an irresistable sales pitch.  ")
                      .append("It only costs you ")
                      .append(cash - (cash / 2))
                      .append(" marks for him to haul away ")
                      .append("half your gear.\n"))));
    }
  }

  Screen mobFlees() {
    return mobFlees(null);
  }

  Screen mobFlees(String msg) {
    if (msg == null) {
      msg = "";
    }
    String msg2 =
        String.valueOf(String.valueOf(msg))
            .concat(
                String.valueOf(
                    String.valueOf(
                        String.valueOf(
                            String.valueOf(
                                new StringBuffer("\tThe ")
                                    .append(this.mob.getName())
                                    .append(" turns and makes tracks rapidly ")
                                    .append("away from you.\n"))))));
    int ms = this.mob.runWits();
    if (this.hero.hasTrait(Constants.BANDIT)) {
      ms /= 2;
    }
    if (!Tools.contest(ms, this.hero.runWits())) {
      return new arBattle(
          this,
          String.valueOf(String.valueOf(msg2)).concat("\tBut you catch it before it escapes!\n"));
    }
    return new arNotice(
        getHome(),
        String.valueOf(String.valueOf(msg2))
            .concat("\tIt manages to stay ahead of you long enough to escape..."));
  }

  String thiefRun() {
    if (!Tools.contest(this.hero.getCharm(), this.mob.getCharm())) {
      return "";
    }
    this.hero.subFatigue(1);
    return String.valueOf(
        String.valueOf(
            new StringBuffer(
                    "\nYour 'merchant' training comes in handy. You duck into cover and the ")
                .append(this.mob.getName())
                .append(" passes by.\n")));
  }

  Screen tryToken() {
    String msg =
        String.valueOf(
            String.valueOf(
                new StringBuffer("\tThe ")
                    .append(this.mob.getName())
                    .append(" fixes you with an intent stare, ")
                    .append("studying you to the core of your soul.  Finally, it ")
                    .append("makes a decision.\n")));
    if (Tools.contest(this.hero.getPower(), this.mob.getPower())) {
      String msg2 =
          String.valueOf(
                  String.valueOf(
                      String.valueOf(
                              String.valueOf(
                                  String.valueOf(
                                          String.valueOf(
                                              String.valueOf(
                                                      String.valueOf(
                                                          String.valueOf(String.valueOf(msg))
                                                              .concat(
                                                                  String.valueOf(
                                                                      String.valueOf(
                                                                          String.valueOf(
                                                                              String.valueOf(
                                                                                  new StringBuffer(
                                                                                          "\tBy some invisible procedure, the ")
                                                                                      .append(
                                                                                          this.mob
                                                                                              .getName())
                                                                                      .append(
                                                                                          " has judged you ")
                                                                                      .append(
                                                                                          "worthy to recieve special training.  You are taken to a remote ")
                                                                                      .append(
                                                                                          "location where you will learn the secrets of Ieatsu.\n"))))))))
                                                  .concat(
                                                      String.valueOf(
                                                          String.valueOf(
                                                              this.hero.gainExp(
                                                                  2 * this.mob.baseExp()))))))
                                      .concat(
                                          String.valueOf(
                                              String.valueOf(
                                                  this.hero.gainGuts(2 * this.weight))))))
                          .concat(
                              String.valueOf(String.valueOf(this.hero.gainWits(2 * this.weight))))))
              .concat(String.valueOf(String.valueOf(this.hero.gainCharm(2 * this.weight))));
      this.hero.addGuts(-3);
      this.hero.addWits(-3);
      this.hero.addCharm(-3);
      this.hero.addRank(Constants.IEATSU, 1);
      this.hero.addTemp(Constants.IEATSU, 1);
      this.hero.subFatigue(10);
      return new arNotice(new arNotice(getHome(), trains), msg2);
    } else if (this.mob.isAggresive()) {
      return new arBattle(this, "\tIt launches itself into battle, determined to destroy you!\n");
    } else {
      String msg3 =
          String.valueOf(
              String.valueOf(
                  new StringBuffer("\tThe ")
                      .append(this.mob.getName())
                      .append(" slaps the token from your hand, then smashes it. ")
                      .append("Apparently you have failed some unspoken test.\n")));
      this.opt.remove(14);
      return new arNotice(this, msg3);
    }
  }

  Screen tryCapture() {
    String msg =
        String.valueOf(
            String.valueOf(
                new StringBuffer(
                        "\tYou scurry around the water, hopping over logs, rocks and roots.  The ")
                    .append(this.mob.getName())
                    .append("  spins away from you ")
                    .append("squeeking in fear as it leaves behind a trail ")
                    .append("of sparkling dust.\n")));
    if (Tools.contest(Screen.getWits(), this.mob.getWits())) {
      String msg2 =
          String.valueOf(String.valueOf(msg))
              .concat("\tYou Capture It!\n\n*** Bottled Faery found ***");
      Screen.addPack("Bottled Faery", 1);
      return new arNotice(
          getHome(),
          String.valueOf(
                  String.valueOf(
                      String.valueOf(String.valueOf(msg2))
                          .concat(String.valueOf(String.valueOf(this.hero.gainWits(10))))))
              .concat(String.valueOf(String.valueOf(this.hero.gainExp(this.mob.baseExp())))));
    } else if (Tools.roll(3) >= this.mob.getStance()) {
      return new arNotice(
          getHome(),
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("\tThe ")
                                      .append(this.mob.getName())
                                      .append(" escapes over a small cliff and ")
                                      .append("quickly vanishes into some hedges.")))))));
    } else {
      return new arNotice(
          new arBattle(this, null),
          String.valueOf(String.valueOf(msg)).concat("\tOkay! Now its mad!"));
    }
  }
}
