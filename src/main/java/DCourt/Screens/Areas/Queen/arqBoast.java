package DCourt.Screens.Areas.Queen;

import DCourt.Items.List.itHero;
import DCourt.Screens.Areas.arQueen;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Screens.Wilds.arCastle;
import DCourt.Static.Constants;
import DCourt.Static.GameStrings;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Queen/arqBoast.class */
public class arqBoast extends arNotice {
  static int BOASTRISK = 10;
  public static final String boastMsg = "boastMsg";
  // public static final String boastMsg = boastMsg;
  public static final String[] boastText = {
    "$TB$Your narrative is going well, when quite by accident you $accident$$CR$$TB$You apologize profusely, but the guardsmen grab you by the collar, take half your purse for damages and eject you from the premises.$CR$",
    "$TB$$lordname$'s frown grows deeper and deeper as you blather continuously about the inummerable goblins, rodents, and boars you have slain in your day.$CR$$TB$Finally, $HE$ interrupts you, declares you a bore and a liar and stomps away indignantly for having wasted $his$ time.$CR$",
    "$TB$The $lordrank$ bears an amused smile throughout your discourse.  When your tale is concluded, $HE$ thanks you for the opportunity to wax nostalgic and dream about $HIS$ own halcyon days of youth.$CR$",
    "$TB$The $lordrank$ listens with fascination for the better part of an hour.  $HE$ is favorably impressed by your courage and heroism and agrees to take up your case with the queen.$CR$",
    "$TB$The $lordrank$ becomes the core of an admiring knot of listeners.  When you finish they all applaud.$CR$$TB$The $lordrank$ shakes your hand and thanks you heartily for such a terrifically inspiring tale of braggadocio and derring-do.  $HE$ further declares you to be a credit and inspiration to the entire court.$CR$"
  };
  public static final String[] accidents = {
    "smash an antique burial urn.",
    "step on the dinner platter.",
    "slip and fall into the fireplace.",
    "hurl a drumstick into the Queen's lap.",
    "kick the Queen's favorite wolf-hound.",
    "skid and rip your trews wide open.",
    "throw wine on the Queen's portrait.",
    "refer to the Queen as 'a bit of rumpy-pumpy'.",
    "swing on the chandelier, carrying it and you into a drunken group of visiting dignitaries who proceed to pummel you senseless with their oddly shaped walking sticks."
  };

  public arqBoast(Screen from) {
    super(from);
    prepareText(from);
  }

  void prepareText(Screen from) {
    itHero h = Screen.getHero();
    int rank = 1 + Tools.roll(h.getSocial() + Tools.roll(3));
    if (rank > 10) {
      rank = 10;
    }
    int level = (rank * 2) + Tools.roll(rank * 2);
    int skill = h.getCharm() + (h.fight() * 5);
    int favor = h.getFavor();
    h.addFatigue(1);
    int index = Tools.fourTest(skill, level * BOASTRISK);
    MadLib msg = new MadLib(boastMsg.concat(String.valueOf(String.valueOf(boastText[index]))));
    switch (index) {
      case 0:
        h.subMoney(h.getMoney() / 2);
        h.subFavor(favor / (12 - rank));
        break;
      case 1:
        h.subFavor(favor / (14 - rank));
        break;
      case 2:
        msg.append(h.gainExp(rank + level));
        msg.append(h.gainCharm(4));
        break;
      case 3:
        msg.append(h.gainExp((rank * 2) + level));
        msg.append(h.gainCharm(7));
        h.addFavor(favor / (14 - rank));
        break;
      case 4:
        msg.append(h.gainExp((rank * 5) + level));
        msg.append(h.gainCharm(10));
        h.addFavor(favor / (12 - rank));
        break;
    }
    msg.append(arQueen.Recommend());
    msg.replace(
        "$lordname$",
        String.valueOf(String.valueOf(Constants.rankTitle[rank]))
            .concat(String.valueOf(String.valueOf(Tools.select(GameStrings.Names)))));
    String[][] strArr = Constants.rankName;
    int sex = Tools.roll(2);
    msg.replace("$lordrank$", strArr[sex][rank]);
    msg.replace("$accident$", Tools.select(accidents));
    msg.genderize(sex == 0);
    if (index == 0) {
      setHome(new arCastle());
    } else {
      setHome(from);
    }
    setMessage(msg.getText());
  }
}
