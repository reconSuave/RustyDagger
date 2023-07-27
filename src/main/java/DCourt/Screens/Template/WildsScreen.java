package DCourt.Screens.Template;

import DCourt.Items.List.itMonster;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.ArmsTrait;
import DCourt.Static.Constants;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Template/WildsScreen.class */
public abstract class WildsScreen extends Screen {
  public static final String TOO_TIRED =
      "\tYou find yourself far too exhausted to continue adventuring.  Please return tommorow for further exploration.\n";
  public static final String NEED_ROPE =
      "\tYou cannot advance any further up these cliffs and crags without an additional supply of ROPE.\n";
  public static final String NEED_LIGHT =
      "\tYou can advance no further through these dark and dingy caverns without TORCHES or some other source of light.\n";

  public abstract Screen pickQuest(int i);

  public abstract int getPower(int i);

  public abstract String getWhere(int i);

  public WildsScreen() {}

  public WildsScreen(Screen from) {
    super(from, "WildsScreen");
  }

  public WildsScreen(String name) {
    super(name);
  }

  public WildsScreen(Screen from, String name) {
    super(from, name);
  }

  public boolean needsLight(int loc) {
    return false;
  }

  public boolean needsRope(int loc) {
    return false;
  }

  public int getHideBits() {
    return 0;
  }

  public String markFound(int find) {
    return "";
  }

  public void goQuesting() {
    goQuesting(0);
  }

  public void goQuesting(int loc) {
    if (testAdvance(loc) && !doSearch(loc)) {
      Tools.setRegion(pickQuest(loc));
    }
  }

  public boolean testAdvance(int loc) {
    String test = canAdvance(loc);
    if (test == null) {
      return true;
    }
    Tools.setRegion(new arNotice(this, test));
    return false;
  }

  String canAdvance(int loc) {
    if (Tools.getHero().getQuests() < 1) {
      return TOO_TIRED;
    }
    if (needsRope(loc) && !findClimb()) {
      return NEED_ROPE;
    }
    if (!needsLight(loc) || findLight()) {
      return null;
    }
    return NEED_LIGHT;
  }

  boolean doSearch(int loc) {
    int count = 0;
    int bits = getHideBits();
    if (bits == 0 || !Tools.contest(Screen.getWits(), getPower(loc) * 20)) {
      return false;
    }
    for (int ix = bits; ix != 0; ix >>= 1) {
      if ((ix & 1) != 0) {
        count++;
      }
    }
    int num = Tools.roll(count);
    int ix2 = 1;
    int pick = -1;
    while (num >= 0) {
      if ((bits & ix2) != 0) {
        num--;
      }
      pick++;
      ix2 <<= 1;
    }
    Screen.getHero().searchWork(1);
    Tools.setRegion(
        new arNotice(
            this,
            String.valueOf(String.valueOf(markFound(pick)))
                .concat(
                    String.valueOf(String.valueOf(Screen.getHero().gainWits(getPower(loc) + 2))))));
    return true;
  }

  public itMonster selectQuest(String[] names, int[] weight) {
    return selectQuest(0, names, weight);
  }

  public itMonster selectQuest(int loc, String[][] names, int[][] weight) {
    return selectQuest(loc, names[loc], weight[loc]);
  }

  itMonster selectQuest(int loc, String[] names, int[] weight) {
    int total = 0;
    if (Tools.percent(1)) {
      return Screen.findBeast("Faery");
    }
    for (int i : weight) {
      total += i;
    }
    int total2 = Tools.roll(total);
    for (int ix = 0; ix < weight.length; ix++) {
      total2 -= weight[ix];
      if (total2 < 0) {
        return Screen.findBeast(
            String.valueOf(
                String.valueOf(
                    new StringBuffer(String.valueOf(String.valueOf(getWhere(loc))))
                        .append(":")
                        .append(names[ix]))));
      }
    }
    return Screen.findBeast(
        String.valueOf(
            String.valueOf(
                new StringBuffer(String.valueOf(String.valueOf(getWhere(loc))))
                    .append(":")
                    .append(names[0]))));
  }

  public boolean findClimb() {
    return Screen.hasTrait(Constants.HILLFOLK) || Screen.subPack("Rope", 1) == 1;
  }

  protected boolean findLight() {
    return Screen.hasTrait(Constants.CATSEYES)
        || Screen.findGearTrait(ArmsTrait.GLOWS) != null
        || Screen.findGearTrait(ArmsTrait.FLAME) != null
        || Screen.subPack("Torch", 1) > 0;
  }
}
