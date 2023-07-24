package DCourt.Screens.Wilds;

import DCourt.Components.Portrait;
import DCourt.Items.List.itMonster;
import DCourt.Screens.Areas.Castle.arClanHall;
import DCourt.Screens.Areas.Castle.arPostal;
import DCourt.Screens.Areas.arQueen;
import DCourt.Screens.Areas.arTown;
import DCourt.Screens.Quest.arQuest;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.WildsScreen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Event;

/* loaded from: DCourt.jar:DCourt/Screens/Wilds/arCastle.class */
public class arCastle extends WildsScreen {
  String[] place = {"Castle", "Dunjeon", "Ocean", "Brasil", "Shang"};
  int[] power = {4, 2, 3, 4, 5};
  static final int FIND_OCEAN = 100;
  static final int[][] weight = {
    new int[] {1},
    new int[] {7, 6, 5, 4, 3, 2},
    new int[] {5, 3, 2},
    new int[] {6, 5, 4, 3, 2},
    new int[] {6, 7, 2, 6, 5, 3, 2}
  };
  static final String[][] beasts = {
    new String[] {"Guard"},
    new String[] {"Rodent", "Snot", "Rager", "Gang", "Troll", "Mage"},
    new String[] {"Traders", "Serpent", "Mermaid"},
    new String[] {"Harpy", "Fighter", "Golem", "Medusa", "Hero"},
    new String[] {"Gunner", "Peasant", "Ninja", "Plague", "Shogun", "Panda", "Samurai"}
  };
  static final String DOCKS_FAILURE =
      "\tYou plot a course with confidence.  But after days of fruitless searching you must return for additional provisions.\n\n\t";
  static final String DOCKS_SUCCESS =
      "\tYou plot a course with confidence.  After hours of searching you encounter oceanic inhabitants.\n\n\t";
  static final String DOCKS_BRASIL =
      " \tYou plot a course with confidence.  After days of travel you arrive on the shores of Hie Brasil.\n\n\t";
  static final String DOCKS_SHANG =
      "\tYou plot a course with confidence.  After a week of travel you arrive on the shores of Shangala.\n\n\t";
  static final String[] oceans = {
    "You spy an bouy marking low waters...",
    "You find a barrel floating on the waves...",
    "You pass a stretch of choking seaweed.",
    "You catch an odd fish with bulging eyeballs...",
    "A dolphin swims circles around your ship...",
    "Seagulls circle above you...",
    "You hear distance groans from some sea beast...",
    "You find planks from a ship that broke apart..."
  };

  public arCastle() {
    super("The Central Courtyard of Dragon Keep");
    setBackground(new Color(255, 128, 255));
    setForeground(new Color(128, 0, 128));
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    addPic(new Portrait("cstTown.jpg", "Town Gate", 20, 175, 96, 64));
    addPic(new Portrait("toCastle.jpg", "Royal Court", 155, 45, 96, 64));
    addPic(new Portrait("cstDunjeon.jpg", "{1}Dunjeons", 15, 70, 96, 64));
    addPic(new Portrait("Tower.jpg", "Clan Hall", 295, 40, 64, 96));
    addPic(new Portrait("cstPostal.jpg", "Post Office", 140, 170, 96, 64));
    addPic(new Portrait("cstDocks.jpg", "{1}Docks", 280, 180, 96, 64));
  }

  @Override // DCourt.Screens.Screen
  public void init() {
    init();
    getPic(2).show(Screen.getLevel() >= 8);
    getPic(5).show(Screen.getLevel() >= 10);
    Screen.getHero().tryToLevel(this);
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    switch (getPic(e.target)) {
      case 0:
        Tools.setRegion(new arTown());
        break;
      case 1:
        goQueen();
        break;
      case 2:
        goQuesting(1);
        break;
      case 3:
        Tools.setRegion(new arClanHall(this));
        break;
      case 4:
        Tools.setRegion(new arPostal(this));
        break;
      case 5:
        goQuesting(2);
        break;
    }
    return action(e, o);
  }

  void goQueen() {
    if (Screen.getSocial() > 0) {
      Tools.setRegion(new arQueen(this));
    } else {
      goQuesting(0);
    }
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public int getPower(int loc) {
    return this.power[loc];
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public String getWhere(int loc) {
    return this.place[loc];
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public boolean needsLight(int loc) {
    return loc == 1;
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public void goQuesting(int loc) {
    if (loc < 2) {
      goQuesting(loc);
    } else if (testAdvance(loc)) {
      if (!Tools.contest(Screen.getWits(), FIND_OCEAN)) {
        Tools.setRegion(
            new arNotice(
                this, DOCKS_FAILURE.concat(String.valueOf(String.valueOf(Tools.select(oceans))))));
        Screen.getHero().addFatigue(1);
      } else if (Screen.packCount("Rutter for Shangala") > 0 && Tools.percent(70)) {
        goQuesting(4);
      } else if (Screen.packCount("Rutter for Hie Brasil") <= 0 || !Tools.percent(70)) {
        goQuesting(2);
      } else {
        goQuesting(3);
      }
    }
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public Screen pickQuest(int loc) {
    itMonster beast = selectQuest(loc, beasts, weight);
    Screen next = this;
    if (loc == 0) {
      next = new arQueen(this);
    }
    return new arQuest(
        this,
        next,
        getPower(loc),
        String.valueOf(String.valueOf(getWhere(loc))).concat(" Quest"),
        beast);
  }
}
