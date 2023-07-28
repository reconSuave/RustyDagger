package DCourt.Screens.Utility;

import DCourt.Components.FTextField;
import DCourt.Control.GearTable;
import DCourt.Items.Item;
import DCourt.Items.List.itArms;
import DCourt.Items.List.itHero;
import DCourt.Items.itList;
import DCourt.Screens.Screen;
import DCourt.Static.ArmsTrait;
import DCourt.Static.Constants;
import DCourt.Tools.Buffer;
import DCourt.Tools.FileLoader;
import DCourt.Tools.Loader;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Utility/arPeer.class */
public class arPeer extends arNotice {
  FTextField heroName;
  Button seek;
  Button done;
  String pname;
  itHero target;
  int spend;
  static final int SEEKMONEY = 250;
  ;
  static final String SEEKGEM = "Opal";
  public static final int DISABLED = 0;
  public static final int USEMONEY = 1;
  public static final int USEMAGIC = 2;
  public static final int USEPALANTIR = 3;
  public static final int CLANPEER = 4;
  static final String[] sex_Act = {"masculine", "feminine", "ambiguous", "neutral"};
  static final String[] sex_Dress = {
    "trousers and dark colors",
    "long skirts and bright colors",
    "fluffy pants and a puffy shirt",
    "dark overcoat concealing all"
  };
  static final String[] situation = {
    " battling a centaur, bellowing forth ",
    " fleeing a wyvern, shrieking in terror ",
    " riding a gryphon above the clouds, exhorting ",
    " searching the corpse of an elf, mumbling ",
    " tracking a forest boar, muttering ",
    " seducing a castle servant, whispering ",
    " gambling amongst nobles, chuckling ",
    " training at the guild, while crying ",
    " sharing a beer in the tavern, while boasting ",
    " tricking a goblin mage, then saying ",
    " stroking a goblin queen, then moaning "
  };
  static final String[] strongAdj = {
    "stern", "brave", "headstrong", "alert", "valiant", "cunning", "powerful"
  };
  static final String noDescription =
      "$title$ $name$ = No Description\n\n\tGuts: $guts$\tWits: $wits$\tCharm: $charm$\nWeapon: $weapon$\nArmor: $armor$";
  static final String describe0 =
      "\t$title$ $name$ $clanmsg$ is a $build$ $race$ with $hair$ hair, $eyes$ eyes, and $skin$ skin. $dress$ $behave$ $intro$ the odd trait of $marks$. Rumor has it that $HE$ is a $sign$. \n\tAs you peer into the gem you espy";
  static final String describe1 = "$him$ $situation$ \"$phrase$\"";
  static final String describe2 = "that $habit$.";
  static final String describe3 =
      "\n\tGuts: $guts$\tWits: $wits$\tCharm: $charm$\nWeapon: $weapon$\nArmor: $armor$";
  static final String clanmsg = "of the $clan$ Clan";
  static final String dressmsg = "$He$ is dressed in a $sexact$ fashion; $sexdress$";
  static final String behave1 = "$and$ $his$ behaviour is $also$ suspiciously $sexact$.";
  static final String behave2 = "$His$ behaviour is suspiciously $sexact$.";
  static final String intro1 = "This $adject$ $rank$ is overall undistinguished, save for";
  static final String intro2 = "In addition, this $adject$ $rank$ has";
  static final String STOP = ".";
  static final String AND = "and";
  static final String WHILE = "while";
  static final String ALSO = "also";

  public arPeer(Screen from, int source, String who) {
    super(from, "Examine Hero");
    setBackground(Color.blue);
    setForeground(Color.white);
    setFont(Tools.courtF);
    hideStatusBar();
    this.pname = who == null ? Screen.getHero().getName() : who;
    this.spend = source;
    if (this.spend == 2 && GearTable.canMageUse(SEEKGEM)) {
      this.spend = 0;
    }
    setMessage("Working...");
  }

  @Override // DCourt.Screens.Screen
  public void init() {
    super.init();
    LoadVision(this.pname);
  }

  @Override // DCourt.Screens.Utility.arNotice, DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    itHero h = Screen.getHero();
    if (this.spend != 4) {
      this.seek.enable(
          this.heroName.isMatch(h.getName())
              || (this.spend == 1 && h.getMoney() > SEEKMONEY)
              || (this.spend == 2 && h.packCount(SEEKGEM) > 3));
    }
    g.setColor(getBackground());
    g.fillRect(0, 0, (int) Tools.DEFAULT_WIDTH, (int) Tools.DEFAULT_HEIGHT);
    g.setColor(getForeground());
    g.setFont(Tools.statusF);
    if (this.heroName.isMatch(h.getName())) {
      g.drawString("Cost: Free", 195, 23);
    } else if (this.spend == 1) {
      g.drawString("Cost: $250", 195, 23);
    } else if (this.spend == 2) {
      g.drawString(
          String.valueOf(
              String.valueOf(
                  new StringBuffer("Cost: 3/")
                      .append(h.packCount(SEEKGEM))
                      .append(" ")
                      .append(SEEKGEM))),
          195,
          23);
    } else if (this.spend == 4) {
      g.drawString(String.valueOf(String.valueOf(h.getClan())).concat(" Clan Palantir"), 10, 20);
    } else {
      g.drawString("Cost: ---", 200, 20);
    }
    g.setColor(getForeground());
    drawText(g, 10, 40);
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == this.done) {
      Tools.setRegion(getHome());
    }
    if (e.target == this.seek) {
      LoadVision(this.heroName.getText());
    }
    repaint();
    return true;
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    setFont(Tools.textF);
    setForeground(Color.black);
    this.done = new Button("Done");
    this.done.reshape(335, 7, 60, 20);
    this.heroName = new FTextField(15);
    this.heroName.reshape(70, 5, 120, 22);
    if (this.spend != 4) {
      this.heroName.setText(Screen.getHero().getName());
    }
    this.seek = new Button("Seek");
    this.seek.reshape(5, 7, 60, 20);
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    add(this.done);
    if (this.spend != 4) {
      add(this.seek);
      add(this.heroName);
    }
  }

  void LoadVision(String who) {
    itHero h = Screen.getHero();
    this.pname = who;
    if (who == null || who.length() < 4) {
      setMessage(
          String.valueOf(
              String.valueOf(new StringBuffer("Illegal Name: <").append(who).append(">"))));
    } else if (h.isMatch(this.pname)) {
      this.target = h;
      BuildDescription();
    } else {
      if (this.spend == 1) {
        h.subMoney(SEEKMONEY);
      }
      if (this.spend == 2) {
        h.subPack(SEEKGEM, 3);
      }
      Buffer buf = FileLoader.cgiBuffer(Loader.READHERO, this.pname);
      if (buf == null || buf.isEmpty() || buf.isError()) {
        setMessage(
            String.valueOf(
                String.valueOf(
                    new StringBuffer("Unable to Load <").append(this.pname).append(">"))));
        return;
      }
      this.target = (itHero) Item.factory(buf);
      BuildDescription();
    }
  }

  void BuildDescription() {
    MadLib mad;
    itList lp = this.target.getLooks();
    if (lp == null || lp.getCount() < 1) {
      mad = new MadLib(noDescription);
    } else {
      if (lp.getValue(Constants.HABIT) == null || !Tools.chance(3)) {
        mad =
            new MadLib(
                "\t$title$ $name$ $clanmsg$ is a $build$ $race$ with $hair$ hair, $eyes$ eyes, and $skin$ skin. $dress$ $behave$ $intro$ the odd trait of $marks$. Rumor has it that $HE$ is a $sign$. \n\tAs you peer into the gem you espy $him$ $situation$ \"$phrase$\"\n\tGuts: $guts$\tWits: $wits$\tCharm: $charm$\nWeapon: $weapon$\nArmor: $armor$");
      } else {
        mad =
            new MadLib(
                "\t$title$ $name$ $clanmsg$ is a $build$ $race$ with $hair$ hair, $eyes$ eyes, and $skin$ skin. $dress$ $behave$ $intro$ the odd trait of $marks$. Rumor has it that $HE$ is a $sign$. \n\tAs you peer into the gem you espy that $habit$.\n\tGuts: $guts$\tWits: $wits$\tCharm: $charm$\nWeapon: $weapon$\nArmor: $armor$");
      }
      if (this.target.getClan() == null) {
        mad.replace("$clanmsg$", "");
      } else {
        mad.replace("$clanmsg$", clanmsg);
        mad.replace("$clan$", this.target.getClan());
      }
      mad.replace("$build$", lp.getValue(Constants.BUILD));
      mad.replace("$race$", lp.getValue(Constants.RACE));
      mad.replace("$hair$", lp.getValue(Constants.HAIR));
      mad.replace("$eyes$", lp.getValue(Constants.EYES));
      mad.replace("$skin$", lp.getValue(Constants.SKIN));
      String val = lp.getValue(Constants.TITLE);
      int gender = 0;
      while (gender < 2 && val != null && !val.equals(Constants.sexs[gender])) {
        gender++;
      }
      String val2 = lp.getValue(Constants.DRESS);
      int dress = 0;
      while (dress < 4 && val2 != null && !val2.equals(Constants.sexs[dress])) {
        dress++;
      }
      String val3 = lp.getValue(Constants.BEHAVE);
      int behave = 0;
      while (behave < 4 && val3 != null && !val3.equals(Constants.sexs[behave])) {
        behave++;
      }
      if (gender != dress) {
        mad.replace("$dress$", dressmsg);
        mad.replace("$sexact$", sex_Act[dress]);
        mad.replace("$sexdress$", sex_Dress[dress]);
        if (gender == behave) {
          mad.replace("$behave$", STOP);
        } else {
          mad.replace("$behave$", behave1);
          mad.replace("$and$", dress == behave ? AND : WHILE);
          mad.replace("$also$", dress == behave ? ALSO : "");
          mad.replace("$sexact$", sex_Act[behave]);
        }
      } else if (gender != behave) {
        mad.replace("$dress$", "");
        mad.replace("$behave$", behave2);
        mad.replace("$sexact$", sex_Act[behave]);
      } else {
        mad.replace("$dress$", "");
        mad.replace("$behave$", "");
      }
      mad.replace("$intro$", gender == dress ? intro1 : intro2);
      mad.replace("$marks$", lp.getValue("Marks"));
      mad.replace("$sign$", lp.getValue(Constants.SIGN));
      mad.replace("$situation$", Tools.select(situation));
      mad.replace("$phrase$", lp.getValue(Constants.PHRASE));
      mad.replace("$habit$", lp.getValue(Constants.HABIT));
      mad.replace("$rank$", this.target.getRankTitle());
      mad.replace("$adject$", Tools.select(strongAdj));
      mad.genderize(gender == 0);
    }
    mad.replace("$title$", this.target.getTitle());
    mad.replace("$name$", this.target.getName());
    mad.replace("$guts$", this.target.getGuts());
    mad.replace("$wits$", this.target.getWits());
    mad.replace("$charm$", this.target.getCharm());
    itArms ap = this.target.findGearTrait(ArmsTrait.RIGHT);
    mad.replace("$weapon$", ap == null ? Constants.NONE : ap.getName());
    itArms ap2 = this.target.findGearTrait(ArmsTrait.BODY);
    mad.replace("$armor$", ap2 == null ? Constants.NONE : ap2.getName());
    String msg = String.valueOf(String.valueOf(mad.getText())).concat("\nTraits: ");
    int row = 1;
    for (int i = 0; i < Constants.TraitList.length; i++) {
      if (this.target.hasTrait(Constants.TraitList[i])) {
        msg =
            String.valueOf(String.valueOf(msg))
                .concat(
                    String.valueOf(
                        String.valueOf(
                            String.valueOf(String.valueOf(Constants.TraitList[i])).concat(" "))));
        row++;
        if (row % 6 == 0) {
          msg = String.valueOf(String.valueOf(msg)).concat("\n\t");
        }
      }
    }
    if (row == 1) {
      msg = String.valueOf(String.valueOf(msg)).concat(Constants.NONE);
    }
    setMessage(msg);
  }
}
