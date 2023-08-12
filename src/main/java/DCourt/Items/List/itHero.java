package DCourt.Items.List;

import DCourt.Components.Portrait;
import DCourt.Items.Item;
import DCourt.Items.itList;
import DCourt.Screens.Areas.Fields.arHealer;
import DCourt.Screens.Command.arExit;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Screens.Wilds.arField;
import DCourt.Static.ArmsTrait;
import DCourt.Static.Constants;
import DCourt.Static.GearTypes;
import DCourt.Tools.Buffer;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Items/List/itHero.class */
public class itHero extends itAgent {
  private String pass;
  private String lastPlay;
  private String best;
  private String leader;
  private int raise;
  static final String PLACE = "place";

  static final int VERSION = 10;
  static final String STORE = "store";
  static final String DUMP = "dump";
  static final String LOOKS = "looks";
  private static Portrait picture = new Portrait("Faces/Hero.jpg", 0, 0, 80, 80);
  static final String clanMsg =
      "\tWith the unsealing of the grant, you become a staunch member of the [ $clan$ ], able to call upon them for power and assistance.  Mystical energies course through your being, making betrayal an impossible concept.\n";
  static final String flameMsg =
      "\tThis grant would be a betrayal of your clan. It bursts into flames, singeing your hands and blinding you.\n";
  private int sessionID = 0;
  private itList store = new itList(STORE);
  private itList looks = new itList(LOOKS);
  private itList dump = new itList(DUMP);

  static {
    picture.setType(2);
  }

  public itHero(String id) {
    super(id);
  }

  public itHero(itHero it) {
    super(it);
  }

  @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
  public Item copy() {
    return new itHero(this);
  }

  @Override // DCourt.Items.List.itAgent, DCourt.Items.itList, DCourt.Items.itToken,
  // DCourt.Items.Item
  public String getIcon() {
    return "itHero";
  }

  public static Item factory(Buffer buf) {
    if (!buf.begin()) {
      return null;
    }
    if ((!buf.match("itHero") && !buf.match("itAgent")) || !buf.split()) {
      return null;
    }
    System.out.println("itHero.factory");
    itHero who = new itHero(buf.token());
    who.loadAttributes(buf);
    who.loadBody(buf);
    who.fixLists();
    return who;
  }

  @Override // DCourt.Items.List.itAgent
  public void fixLists() {
    super.fixLists();
    this.lastPlay = findValue("Date");
    this.store = findList(STORE);
    this.looks = findList(LOOKS);
    this.dump = new itList(this.dump);
  }

  public itList getStore() {
    return this.store;
  }

  public itList getDump() {
    return this.dump;
  }

  public itList getLooks() {
    return this.looks;
  }

  public int getVersion() {
    return getStatus().getCount(Constants.VERSION);
  }

  public void setVersion(int val) {
    getStatus().fix(Constants.VERSION, val);
  }

  public int storeCount(String id) {
    return this.store.getCount(id);
  }

  public int storeCount(Item it) {
    return this.store.getCount(it);
  }

  public void fixStore(String id, int num) {
    this.store.fix(id, num);
  }

  public int addStore(String id, int num) {
    return this.store.add(id, num);
  }

  public int subStore(String id, int num) {
    return this.store.sub(id, num);
  }

  @Override // DCourt.Items.List.itAgent
  public Portrait getPicture() {
    return picture;
  }

  public void calcRaise() {
    this.raise = (int) (((double) 50) * Math.pow(1.5d, (double) (getLevel() - 1)));
  }

  public String getPlace() {
    return getValues().getValue(PLACE);
  }

  public void setPlace(String val) {
    getValues().fix(PLACE, val);
  }

  public void clearDump() {
    this.dump.clrQueue();
  }

  public void doFaceless() {
    this.looks.clrQueue();
  }

  public void doAging() {
    getStatus().add(Constants.AGE, 1);
  }

  public void doYouth() {
    if (getAge() >= 9) {
      getStatus().sub(Constants.AGE, 1);
    }
  }

  public void doExhaust() {
    getTemp().fix(Constants.FATIGUE, getBaseQuests());
  }

  public boolean update(String tname, String powers) {
    Tools.setSeed(getLevel() + getExp() + getMoney() + getAge() + getFame() + guildRank());
    calcCombat();
    calcRaise();
    if (getVersion() != 10) {
      getPack().fix("Cookie", 3);
      getPack().fix("Bottled Faery", 1);
      getPack()
          .append(
              new itNote(
                  "Letter",
                  "Fred",
                  "Hi There,\nThe changes to DC1 are now complete except for fixing bugs.  In order to celebrate, everyone gets a onetime gift of cookies and a bottled faery.  Thanks for helping out.\n-Fred-"));
      drop("end");
    }
    setVersion(10);
    if (!Tools.isPlaytest() && (!isNewday() || itAgent.CREATE.equals(getState()))) {
      return true;
    }
    advance(powers);
    return true;
  }

  void advance(String powers) {
    itList temp = getTemp();
    itList stat = getStatus();
    temp.clrQueue();
    setState(itAgent.ALIVE);
    if (powers != null) {
      for (int i = 0; i < Constants.TraitList.length; i++) {
        if (powers.indexOf(Constants.TraitList[i]) >= 0
            || powers.indexOf(Constants.TraitStub[i]) >= 0) {
          temp.fixTrait(Constants.TraitList[i]);
        }
      }
    }
    temp.fix(Constants.FIGHT, fightRank());
    if (hasTrait(Constants.BERZERK)) {
      temp.add(Constants.FIGHT, (getLevel() + 7) / 8);
    }
    temp.fix(Constants.MAGIC, magicRank());
    if (hasTrait(Constants.MYSTIC)) {
      temp.add(Constants.MAGIC, (getLevel() + 7) / 8);
    }
    temp.fix(Constants.THIEF, thiefRank());
    if (hasTrait(Constants.TRADER)) {
      temp.add(Constants.THIEF, (getLevel() + 7) / 8);
    }
    temp.fix(Constants.IEATSU, ieatsuRank());
    if (getStatus().getCount(Constants.AGE) < 15) {
      getStatus().fix(Constants.AGE, 15);
    }
    if (!hasTrait(Constants.UNAGING)) {
      getStatus().add(Constants.AGE, 1);
    } else {
      if (getStatus().getCount(Constants.AGE) > 33) {
        getStatus().sub(Constants.AGE, 1);
      }
      if (getStatus().getCount(Constants.AGE) < 33) {
        getStatus().add(Constants.AGE, 1);
      }
    }
    int fame = getFame();
    int rank = getSocial();
    stat.fix(Constants.FAME, (fame - (fame / 10)) + (rank * 10));
    stat.add(Constants.STIPEND, rank * rank * 50);
  }

  public String rankString() {
    getPack();
    calcCombat();
    return String.valueOf(
        String.valueOf(
            new StringBuffer("{itList|")
                .append(getName())
                .append("|")
                .append(getSocial())
                .append("|")
                .append(getAge())
                .append("|")
                .append(getFame())
                .append("|")
                .append(getSkill())
                .append("|")
                .append(getAttack() + getDefend())
                .append("|")
                .append(getGuts() + getWits() + getCharm())
                .append("|")
                .append(getMoney() + getStore().getCount("Marks"))
                .append("|")
                .append(guildRank())
                .append("|")
                .append(itAgent.DEAD.equals(getState()) ? getState() : getPlace())
                .append("|")
                .append(getClan() == null ? Constants.NONE : getClan())
                .append("}")));
  }

  @Override // DCourt.Items.List.itAgent
  protected int gearAttack() {
    return getGear().fullAttack();
  }

  @Override // DCourt.Items.List.itAgent
  protected int gearDefend() {
    return getGear().fullDefend();
  }

  @Override // DCourt.Items.List.itAgent
  protected int gearSkill() {
    return getGear().fullSkill();
  }

  @Override // DCourt.Items.List.itAgent
  public String getWeapon() {
    Item it = getGear().findArms(ArmsTrait.RIGHT);
    return hasTrait("Blind") ? Constants.BLIND_STR : it == null ? "Fists" : it.getName();
  }

  @Override // DCourt.Items.List.itAgent
  public String getArmour() {
    Item it = getGear().findArms(ArmsTrait.BODY);
    return hasTrait("Panic") ? Constants.PANIC_STR : it == null ? Constants.SKIN : it.getName();
  }

  public String gainExp(int exp) {
    if (exp < 1) {
      return "";
    }
    learn(exp);
    return String.valueOf(
        String.valueOf(
            new StringBuffer("\nThis encounter has left you wiser +").append(exp).append("xp\n")));
  }

  public String gainGuts(int weight) {
    if (Tools.roll(getGuts()) >= weight) {
      return "";
    }
    addGuts(1);
    return "\n*** You grow Stronger  +1 Guts! ***\n";
  }

  public String gainWits(int weight) {
    if (Tools.roll(getWits()) >= weight) {
      return "";
    }
    addWits(1);
    return "\n*** You grow Smarter  +1 Wits! ***\n";
  }

  public String gainCharm(int weight) {
    if (Tools.roll(getCharm()) >= weight) {
      return "";
    }
    addCharm(1);
    return "\n*** You grow Happier  +1 Charm! ***\n";
  }

  public int learn(int num) {
    return getStatus().add(Constants.EXP, num);
  }

  public int getExp() {
    return getStatus().getCount(Constants.EXP);
  }

  public int getRaise() {
    return this.raise;
  }

  public int getBaseQuests() {
    return hasTrait(Constants.QUICK) ? 27 + (4 * getLevel()) : 27 + (3 * getLevel());
  }

  public int getQuests() {
    return (getBaseQuests() - getFatigue()) - getOverload();
  }

  public int getFatigue() {
    return getTemp().getCount(Constants.FATIGUE);
  }

  public int getOverload() {
    int max = packMax();
    int size = getPack().getCount();
    if (size > max) {
      return size - max;
    }
    return 0;
  }

  public int addFatigue(int num) {
    return getTemp().add(Constants.FATIGUE, num);
  }

  public int subFatigue(int num) {
    return getTemp().sub(Constants.FATIGUE, num);
  }

  public int getSocial() {
    return getRank().getCount(Constants.SOCIAL);
  }

  public String getTitle() {
    return Constants.rankTitle[getSocial()];
  }

  public String getRankTitle() {
    return Constants.rankName[getGender()][getSocial()];
  }

  public String getFullTitle() {
    return String.valueOf(
        String.valueOf(
            new StringBuffer(String.valueOf(String.valueOf(getRankTitle())))
                .append(" ")
                .append(getName())));
  }

  public int getGender() {
    return Constants.FEMALE.equals(this.looks.getValue(Constants.TITLE)) ? 1 : 0;
  }

  public int getFame() {
    return getStatus().getCount(Constants.FAME);
  }

  public int getAge() {
    return getStatus().getCount(Constants.AGE);
  }

  public boolean isNewday() {
    return this.lastPlay != null && !this.lastPlay.equals(Tools.getToday());
  }

  public int getFavor() {
    return getStatus().getCount(Constants.FAVOR);
  }

  public void addFavor(int add) {
    if (getFavor() + add >= 0) {
      getStatus().add(Constants.FAVOR, add);
    }
  }

  public void subFavor(int sub) {
    if (sub > getFavor()) {
      getStatus().zero(Constants.FAVOR);
    } else {
      getStatus().sub(Constants.FAVOR, sub);
    }
  }

  public void searchWork(int val) {
    if (hasTrait(Constants.RANGER)) {
      addFatigue(Tools.roll(1 + val));
    } else {
      addFatigue(val);
    }
  }

  public void travelWork(int val) {
    if (hasTrait(Constants.GYPSY)) {
      addFatigue(Tools.roll(1 + val));
    } else {
      addFatigue(val);
    }
  }

  public int actCount() {
    return getTemp().getCount(Constants.ACTIONS);
  }

  public boolean act() {
    return getTemp().sub(Constants.ACTIONS, 1) == 1;
  }

  public boolean act(int num) {
    return getTemp().sub(Constants.ACTIONS, num) == num;
  }

  public void resetActions() {
    if (hasTrait("Blind")) {
      getTemp().zero(Constants.ACTIONS);
    } else {
      getTemp()
          .fix(Constants.ACTIONS, 1 + (fightRank() / 4) + (thiefRank() / 5) + (magicRank() / 6));
    }
    getActions().clrQueue();
    getActions().setName(Constants.ATTACK);
    setState(itAgent.ALIVE);
  }

  public int packMax() {
    return 60 + (hasTrait(Constants.TRADER) ? 20 : 0) + (hasTrait(Constants.MERCHANT) ? 20 : 0);
  }

  public int storeMax() {
    return 100 + (hasTrait(Constants.HOTEL) ? 50 : 0);
  }

  public int holdMax() {
    return 100 + (hasTrait(Constants.TRADER) ? 50 : 0) + (hasTrait(Constants.MERCHANT) ? 100 : 0);
  }

  public int heroHas(Item it) {
    return packCount(it) + getStore().getCount(it);
  }

  @Override // DCourt.Items.List.itAgent
  public int packHeal() {
    return packCount(GearTypes.APPLE) + packCount(GearTypes.TROLL) + packCount(GearTypes.SALVE);
  }

  @Override // DCourt.Items.List.itAgent
  public int packMagic() {
    return packCount(GearTypes.BLIND_DUST)
        + packCount(GearTypes.PANIC_DUST)
        + packCount(GearTypes.BLAST_DUST);
  }

  public String getClan() {
    String clan = getRank().getValue(Constants.CLAN);
    if (clan != null && clan.length() >= 1) {
      return clan;
    }
    return null;
  }

  public void setClan(String clan) {
    getRank().drop(Constants.CLAN);
    if (clan != null) {
      getRank().append(Constants.CLAN, clan);
    }
  }

  public Portrait picture() {
    String msg = "";
    if (hasTrait("Blind")) {
      msg = String.valueOf(String.valueOf(msg)).concat("*BLIND*\n");
    }
    if (hasTrait("Panic")) {
      msg = String.valueOf(String.valueOf(msg)).concat("+PANIC+\n");
    }
    getPicture().setText(msg);
    return getPicture();
  }

  public String doGrant(itNote it) {
    String newClan = it.getFrom();
    String oldClan = getClan();
    getPack().drop(it);
    if (oldClan == null || oldClan.length() <= 0 || Constants.NONE.equalsIgnoreCase(oldClan)) {
      MadLib msg = new MadLib(clanMsg);
      msg.replace("$clan$", newClan);
      msg.append(gainGuts(5));
      msg.append(gainWits(5));
      msg.append(gainCharm(5));
      setClan(newClan);
      return msg.getText();
    }
    getTemp().fixTrait("Blind");
    addWounds((getGuts() - getWounds()) / 2);
    MadLib msg2 = new MadLib(flameMsg);
    msg2.append(gainExp(getLevel()));
    return msg2.getText();
  }

  public boolean tryToLevel(Screen from) {
    if (!isDead()) {
      setState(itAgent.ALIVE);
    }
    if (getExp() < getRaise()) {
      return false;
    }
    getRank().add(Constants.LEVEL, 1);
    getStatus().sub(Constants.EXP, getRaise());
    calcRaise();
    addGuts(2);
    addWits(2);
    addCharm(2);
    getStatus().add(Constants.FAME, getLevel());
    Tools.setRegion(
        new arNotice(
            from,
            String.valueOf(
                String.valueOf(
                    new StringBuffer(
                            "CONGRATUALATIONS!!!!\nYour hours and minutes of sweat and suffering have finally been rewarded by an epiphany of understanding.\n\n+++ You Have Gained A Level - Level ")
                        .append(getLevel())
                        .append("+++\n")
                        .append("*** You Have Grown Stronger  +2 Guts ***\n")
                        .append("*** You Have Grown Smarter  +2 Wits ***\n")
                        .append("*** You Have Grown Happier  +2 Charm ***\n")
                        .append("+++ You Have Grown Tougher  +3 Quests +++\n")))));
    return true;
  }

  public Screen killedScreen(Screen from, String tale, boolean losePack) {
    String msg;
    int cost = getBaseQuests() / 4;
    String msg2 = tale;
    if (getPack().getCount("Bottled Faery") > 0) {
      msg =
          msg2 == null
              ? "\tYou are wounded mortally and fall to the ground.  Before the creature can act, a Bottled Faery breaks free from your pack and transports you the monastery.  It clucks at you briefly in admonishment, then flies away leaving a trail of sparkly dust.\n"
              : String.valueOf(String.valueOf(msg2))
                  .concat(
                      "\n\tA Bottled Faery breaks free from your pack and transports you to the healers!");
      subPack("Bottled Faery", 1);
    } else {
      if (msg2 == null) {
        msg2 =
            "\tYou are wounded mortally and fall to the ground.  The creature roots through your pack and leaves you for dead.  A friendly woodsman finds you before the last of your blood has fallen.  He drags you to the healers where your life is sustained by a thread.\n";
      }
      setState(itAgent.DEAD);
      addFatigue(cost);
      getStatus().fix(Constants.FAME, (getStatus().getCount(Constants.FAME) * 9) / 10);
      if (losePack) {
        msg2 =
            String.valueOf(String.valueOf(msg2))
                .concat("\n\t*** Half your equipment is lost. ***\n");
        getPack().loseHalf();
      }
      if (getQuests() < 1) {
        return new arExit(from, getPlace());
      }
      msg =
          String.valueOf(String.valueOf(msg2))
              .concat(
                  String.valueOf(
                      String.valueOf(
                          String.valueOf(
                              String.valueOf(
                                  new StringBuffer("\n\t\t*** You lose ")
                                      .append(cost)
                                      .append(" quests. ***\n"))))));
    }
    getTemp().fix(Constants.WOUNDS, getGuts() - (getGuts() / getLevel()));
    doCure();
    setState(itAgent.ALIVE);
    setPlace(Constants.FIELDS);
    Screen.saveHero();
    return new arNotice(new arHealer(new arField()), msg);
  }
}
