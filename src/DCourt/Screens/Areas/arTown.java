package DCourt.Screens.Areas;

import DCourt.Components.Portrait;
import DCourt.Items.List.itHero;
import DCourt.Screens.Areas.Town.arArmour;
import DCourt.Screens.Areas.Town.arTavern;
import DCourt.Screens.Areas.Town.arTrader;
import DCourt.Screens.Areas.Town.arWeapon;
import DCourt.Screens.Quest.arQuest;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.WildsScreen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Screens.Wilds.arCastle;
import DCourt.Screens.Wilds.arField;
import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Event;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/arTown.class */
public class arTown extends Screen {
  public arTown() {
    super("Welcome to Salamander Township");
    setBackground(new Color(0, 255, 255));
    setForeground(Color.red);
    addPic(new Portrait("Tavern.jpg", "Tavern", 10, 60, 96, 64));
    addPic(new Portrait("Weapon.jpg", "Weapons", 160, 40, 96, 64));
    addPic(new Portrait("twnArmour.jpg", "Armour", 30, 170, 96, 64));
    addPic(new Portrait("toCastle.jpg", "Castle Gate", Tools.DEFAULT_HEIGHT, 20, 96, 64));
    addPic(new Portrait("twnTrader.jpg", "Trade Shop", 150, 180, 96, 64));
    addPic(new Portrait("toFields.jpg", "Leave Town", 280, 150, 96, 64));
  }

  @Override // DCourt.Screens.Screen
  public void init() {
    init();
    getPic(3).show(Screen.getHero().getLevel() >= 6);
    Screen.getHero().tryToLevel(this);
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    Screen next = null;
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == getPic(0)) {
      next = new arTavern(this);
    } else if (e.target == getPic(1)) {
      next = new arWeapon(this);
    } else if (e.target == getPic(2)) {
      next = new arArmour(this);
    } else if (e.target == getPic(3)) {
      next = enterCastle();
    } else if (e.target == getPic(4)) {
      next = new arTrader(this);
    } else if (e.target == getPic(5)) {
      next = new arField();
    }
    Tools.setRegion(next);
    return action(e, o);
  }

  Screen enterCastle() {
    itHero h = Screen.getHero();
    return (h.getSocial() > 0 || h.packCount("Castle Permit") > 0)
        ? new arCastle()
        : h.getQuests() < 1
            ? new arNotice(this, WildsScreen.TOO_TIRED)
            : new arQuest(this, new arCastle(), 3, "Castle Gate", Screen.findBeast("Town:Guard"));
  }
}
