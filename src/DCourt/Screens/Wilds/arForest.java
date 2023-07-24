package DCourt.Screens.Wilds;

import java.awt.Color;
import java.awt.Event;

import DCourt.Components.Portrait;
import DCourt.Items.List.itHero;
import DCourt.Screens.Screen;
import DCourt.Screens.Areas.Forest.arDwfSmith;
import DCourt.Screens.Areas.Forest.arGuild;
import DCourt.Screens.Quest.arQuest;
import DCourt.Screens.Template.WildsScreen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Wilds/arForest.class */
public class arForest extends WildsScreen {
  int hidden = 7;
  String[] fields = {
    "You spy an old sign that reads: 'Town Ahead'",
    "You find a strand of flowers just coming into bloom.",
    "You pass a pond that is fresh and sweet.",
    "You see horse droppings and wagon tracks.",
    "You pass a herd of wild horses feeding quietly.",
    "Songbirds circle above you...",
    "You hear distant laughter, or is it applause?",
    "You pass a homestead that has been newly built..."
  };
  String[] hills = {
    "You spy an old sign that reads: 'Djini Crossing'",
    "You find a strand of scrubby flowers clinging to a crevice.",
    "You pass a trickling mountain stream.",
    "You see the paw prints of some large cat.",
    "You spy a herd of sheep in the distance.",
    "Flys circle around you...",
    "You hear distant water, or is it wind?",
    "You pass a cave that smells of bear..."
  };
  static final int FINDHILLS = 80;
  static final int SEARCH = 40;
  static final int FINDFIELDS = 20;
  static final int[] weights = {10, 9, 8, 6, 4, 3};
  static final String[] beasts = {"Boar", "Orc", "Elf", "Gryphon", "Snot", "Unicorn"};
  static final String[] found = {
    "The Forest Smithy, hidden in an enchanted grove!\n",
    "The Free Adventurers Guild in a maze of shrubbery!\n",
    "The secret path to the Fenris Mountains!\n"
  };

  public arForest() {
    super("The Depths of the Arcane Forest");
    setBackground(new Color(0, 128, 0));
    setForeground(new Color(128, 255, 128));
    setFont(Tools.textF);
    addPic(new Portrait("Weapon.jpg", "Smithy", 20, 170, 96, 64));
    addPic(new Portrait("Tower.jpg", "The Guild", 320, 150, 64, 96));
    addPic(new Portrait("fstHills.jpg", "{1}Mountain Trail", 10, 30, 96, 64));
    addPic(new Portrait("toFields.jpg", "{1}To Fields", Tools.DEFAULT_HEIGHT, 10, 96, 64));
    addPic(new Portrait("fstQuest.jpg", "{1}Quest!", 160, 60, 96, 64));
    addPic(new Portrait("fstCamp.jpg", "Exit Game", 180, 180, 96, 64));
    for (int i = 0; i < 3; i++) {
      getPic(i).hide();
    }
    Screen.setPlace(Constants.FOREST);
  }

  @Override // DCourt.Screens.Screen
  public void init() {
    super.init();
    questInit();
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    switch (getPic(e.target)) {
      case 0:
        Tools.setRegion(new arDwfSmith(this));
        break;
      case 1:
        Tools.setRegion(new arGuild(this));
        break;
      case 2:
        Tools.setRegion(hills());
        break;
      case 3:
        Tools.setRegion(fields());
        break;
      case 4:
        goQuesting();
        break;
      case 5:
        Tools.setRegion(Screen.tryToExit(this, Constants.FOREST, 0));
        break;
    }
    return super.action(e, o);
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public int getHideBits() {
    return this.hidden;
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public String markFound(int pick) {
    this.hidden &= 65535 ^ (1 << pick);
    if (pick < 0 || pick >= found.length) {
      return "???";
    }
    getPic(pick).show();
    return "While trudging through the woods you discover...\n\n"
        .concat(String.valueOf(String.valueOf(found[pick])));
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public int getPower(int loc) {
    return 2;
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public String getWhere(int loc) {
    return "Forest";
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public Screen pickQuest(int loc) {
    return new arQuest(this, 2, "Forest Quest", selectQuest(beasts, weights));
  }

  Screen fields() {
    itHero h = Screen.getHero();
    if (Screen.getQuests() < 1) {
      return new arNotice(this, WildsScreen.TOO_TIRED);
    }
    if (!Tools.contest(h.getWits(), 20)) {
      return pickQuest(0);
    }
    String msg =
        String.valueOf(
                String.valueOf(
                    String.valueOf(
                        String.valueOf(
                            new StringBuffer(
                                    "\tYou trudge along the dusty trail and occasion to wonder why you haven't seen any other travellers.\n\n\t")
                                .append(this.fields[Tools.roll(this.fields.length)])
                                .append("\n\n\tYou Enter the Fields...\n")))))
            .concat(String.valueOf(String.valueOf(h.gainWits(1))));
    h.travelWork(1);
    return new arNotice(new arField(), msg);
  }

  Screen hills() {
    itHero h = Screen.getHero();
    if (h.getQuests() < 1) {
      return new arNotice(this, WildsScreen.TOO_TIRED);
    }
    if (!Tools.contest(h.getWits(), FINDHILLS)) {
      return pickQuest(0);
    }
    String msg =
        String.valueOf(
                String.valueOf(
                    String.valueOf(
                        String.valueOf(
                            new StringBuffer(
                                    "\tYou march along a rising trail, admiring the spreading vista where mountain meets forest.\n\n\t")
                                .append(this.hills[Tools.roll(this.fields.length)])
                                .append("\n\n\tYou Enter the Mountains...\n")))))
            .concat(String.valueOf(String.valueOf(h.gainWits(3))));
    h.travelWork(1);
    return new arNotice(new arHills(), msg);
  }
}
