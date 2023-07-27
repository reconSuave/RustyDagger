package DCourt.Screens.Command;

import DCourt.Control.PlaceTable;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Command/arExit.class */
public class arExit extends arNotice {
  boolean saveSuccess = false;
  static final String deadMsg =
      "$TB$Whoops! you have been killed!!!$CR$$TB$The creature steals half your gear...$CR$$CR$You fall to the ground in the $place$. You will lie there unmourned until tomorrow when the spirits of the $place$ will awaken you.$CR$$CR$$TB$$TB$Please Return Tomorrow$CR$$TB$$TB$For Further Adventures$CR$";

  public arExit(Screen from, String loc) {
    super(from, "arExit");
    Screen.setPlace(loc);
    if (Screen.getPlayer().isDead()) {
      setMessage(dead());
      return;
    }
    PlaceTable lot = Tools.getPlaceTable();
    lot.select(loc);
    String msg = lot.getSleep();
    String use = lot.getUse();
    if (use.indexOf(99) >= 0 && Screen.packCount("Cooking Gear") > 0) {
      msg = String.valueOf(String.valueOf(msg)).concat("\tYou cook up a hearty dinner.\n");
    }
    if (use.indexOf(116) >= 0 && Screen.packCount("Camp Tent") > 0) {
      msg = String.valueOf(String.valueOf(msg)).concat("\tYou prepare a tent for shelter.\n");
    }
    if (use.indexOf(98) >= 0 && Screen.packCount("Sleeping Bag") > 0) {
      msg = String.valueOf(String.valueOf(msg)).concat("\tYou roll up in a sleeping bag.\n");
    }
    setMessage(
        String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(questsRemain()))));
  }

  @Override // DCourt.Screens.Utility.arNotice, DCourt.Screens.Screen
  public Screen down(int x, int y) {
    if (Tools.movedAway(this)) {
      return null;
    }
    saveAdvance();
    return null;
  }

  void saveAdvance() {
    if (Screen.getPlayer().saveHero()) {
      Screen.getPlayer().saveScore();
      Tools.setRegion(new arFinish());
      return;
    }
    Tools.setRegion(Screen.getPlayer().errorScreen(getHome()));
  }

  String dead() {
    MadLib mad = new MadLib(deadMsg);
    mad.replace("$place$", Screen.getHero().getPlace());
    Screen.getHero().doExhaust();
    return mad.getText();
  }

  String questsRemain() {
    int qnum = Screen.getQuests();
    return qnum > 0
        ? String.valueOf(
            String.valueOf(
                new StringBuffer("\n\n\t").append(qnum).append(" Quests Remain for Today...\n")))
        : "\n\n\tReturn Tomorrow for Further Quests...\n";
  }
}
