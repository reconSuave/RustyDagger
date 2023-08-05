package DCourt.Control;

import DCourt.Items.Item;
import DCourt.Items.List.itAgent;
import DCourt.Items.List.itHero;
import DCourt.Items.itList;
import DCourt.Screens.Command.arError;
import DCourt.Screens.Command.arExit;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Tools.Buffer;
import DCourt.Tools.FileLoader;
import DCourt.Tools.Loader;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Control/Player.class */
public class Player implements Constants {
  static final int NOERR = 0;
  static final int SERVER_NOT_RESPONDING = 1;
  static final int SERVER_SIDE_ERROR = 2;
  static final int BAD_RECORD = 3;
  static int err;
  static String errMessage;
  private String powers = null;
  private String leader = null;
  private String best = null;
  private String pass = null;
  private String name = null;
  private int sessionID = 0;
  private itHero hero = null;
  private itList start = new itList("start");

  public itHero getHero() {
    return this.hero;
  }

  public itHero createHero() {
    this.hero = new itHero(this.name);
    this.hero.fixLists();
    return this.hero;
  }

  public String getName() {
    return this.name;
  }

  public String getPass() {
    return this.pass;
  }

  public String getBest() {
    return this.best;
  }

  public String getLeader() {
    return this.leader;
  }

  public int getSessionID() {
    return this.sessionID;
  }

  int alterSessionID(int sid) {
    return ((sid >> 11) & 2097151) | ((sid << 24) & -2097152);
  }

  public boolean isDead() {
    return itAgent.DEAD.equals(this.hero.getState());
  }

  public boolean isAlive() {
    return itAgent.ALIVE.equals(this.hero.getState());
  }

  public boolean isCreate() {
    return itAgent.CREATE.equals(this.hero.getState());
  }

  public void setState(String val) {
    this.hero.setState(val);
  }

  public String getPlace() {
    return this.hero.getPlace();
  }

  public void setPlace(String val) {
    this.hero.setPlace(val);
  }

  public boolean needsBuild() {
    if (this.hero.getLevel() <= 5) {
      return false;
    }
    return this.hero.getLooks() == null || this.hero.getLooks().getCount() < 1;
  }

  public itList getStart() {
    return this.start;
  }

  public void startValues() {
    this.start.add(Constants.GUTS, this.hero.getGuts());
    this.start.add(Constants.WITS, this.hero.getWits());
    this.start.add(Constants.CHARM, this.hero.getCharm());
    this.start.add(Constants.ATTACK, this.hero.getAttack());
    this.start.add(Constants.DEFEND, this.hero.getDefend());
    this.start.add(Constants.SKILL, this.hero.getSkill());
    this.start.add(Constants.LEVEL, this.hero.getLevel());
    this.start.add(Constants.EXP, this.hero.getExp());
    this.start.add(Constants.FAME, this.hero.getFame());
    this.start.add(Constants.MONEY, this.hero.getMoney());
  }

  public boolean loadHero(String tname, String tpass) {
    this.name = tname;
    this.pass = tpass;
    this.sessionID = Tools.nextInt();
    String msg = tname + "|" + tpass + "|" + this.sessionID;
    this.sessionID = alterSessionID(this.sessionID);
    if (!readFindValues(FileLoader.cgiBuffer(Loader.FINDHERO, msg))) {
      return false;
    }
    this.hero = null;
    return readHeroValues(FileLoader.loadHero(this.name));
  }

  boolean readHeroValues(Buffer buf) {
    System.out.println("== readHeroValues == 1/");
    err = 1;
    if (buf == null || buf.isError()) {
      return false;
    }
    err = 0;
    System.out.println("== readHeroValues == 2/ > " + buf.toString());
    this.hero = (itHero) Item.factory(buf);
    if (this.hero == null) {
      return true;
    }
    System.out.println("== readHeroValues == 3/");
    this.hero.update(this.name, this.powers);
    startValues();
    System.out.println("== readHeroValues == 4/");
    return true;
  }

  boolean readFindValues(Buffer buf) {
    err = 1;
    if (buf == null || buf.isEmpty()) {
      return false;
    }
    err = 2;
    if (buf.isError()) {
      errMessage = buf.line();
      return false;
    }
    err = 3;
    Tools.setToday(buf.token());
    if (Tools.getToday() == null || !buf.split()) {
      return false;
    }
    this.best = buf.token();
    if (!buf.split()) {
      return false;
    }
    this.leader = buf.token();
    if (!buf.split()) {
      return false;
    }
    this.powers = buf.token();
    return true;
  }

  public boolean saveHero() {
    this.hero.fix("Date", Tools.getToday());
    /*
     * name|sessionID|...
     */
    Buffer buf = FileLoader.saveHero(this.name, this.hero.toString());
    System.out.println("saveHero " + buf);
    if (!buf.isError()) {
      return true;
    }
    err = 2;
    errMessage = buf.line();
    return false;
  }

  public void saveScore() {
    FileLoader.cgi(
        Loader.SAVESCORE,
        String.valueOf(
            String.valueOf(
                new StringBuffer(String.valueOf(String.valueOf(this.name)))
                    .append("|")
                    .append(this.sessionID)
                    .append("\n")
                    .append(this.hero.rankString())
                    .append("\n"))));
  }

  public Screen tryToExit(Screen from, String loc, int cost) {
    return new arExit(from, loc);
  }

  public Screen errorScreen(Screen from) {
    String msg =
        String.valueOf(
            String.valueOf(new StringBuffer("Error #").append(err).append(" Has Occured\n")));
    switch (err) {
      case 0:
        return null;
      case 1:
        return new arNotice(
            from,
            String.valueOf(String.valueOf(msg))
                .concat("\nServer Not Responding.\nPlease Try Again Later."));
      case 2:
        return new arNotice(
            from,
            String.valueOf(
                String.valueOf(
                    new StringBuffer(String.valueOf(String.valueOf(msg)))
                        .append("\nServer Reports This Problem:\n")
                        .append(errMessage))));
      case 3:
        return new arNotice(
            from,
            String.valueOf(
                String.valueOf(
                    new StringBuffer(String.valueOf(String.valueOf(msg)))
                        .append("Bad Hero Record for: ")
                        .append(this.name)
                        .append("\n")
                        .append("If this persists, contact the game manager."))));
      default:
        return new arError(
            String.valueOf(
                String.valueOf(
                    new StringBuffer("Unknown Error=")
                        .append(err)
                        .append("\nOkay, I'm scared..."))));
    }
  }
}
