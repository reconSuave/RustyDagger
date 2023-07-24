package DCourt.Screens.Wilds;

import java.awt.Color;
import java.awt.Event;

import DCourt.Components.Portrait;
import DCourt.Items.List.itHero;
import DCourt.Screens.Screen;
import DCourt.Screens.Areas.arTown;
import DCourt.Screens.Areas.Fields.arHealer;
import DCourt.Screens.Quest.arQuest;
import DCourt.Screens.Template.WildsScreen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Wilds/arField.class */
public class arField extends WildsScreen {
  String[] forests = {
    "You spy an old sign that reads: 'Danger!'",
    "You find a human skull with an arrow embedded in it...",
    "You pass a pond that is obviously poisonous.",
    "You find animal droppings. There are chainmail links in it...",
    "You find a horse skeleton. Something big was eating it...",
    "Vultures circle above you...",
    "You hear distance howling, or is it screaming?",
    "You pass a homestead that has been burned to the ground..."
  };
  static final int FINDFOREST = 40;
  ;
  static final int[] hiweight = {8, 6, 4, 5, 2, 5, 2};
  static final int[] loweight = {12, 10, 6, 10, 2, 1, 0};
  static final String[] beasts = {
    "Rodent", "Goblin", "Centaur", Constants.MERCHANT, "Wizard", Constants.GYPSY, "Soldier"
  };

  public arField() {
    super("The Fields near Salamander Township");
    setBackground(new Color(255, 128, 128));
    setForeground(new Color(192, 64, 64));
    Tools.setHeroPlace(Constants.FIELDS);
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    addPic(new Portrait("fldTown.jpg", "Town Road", 30, FINDFOREST, 96, 64));
    addPic(new Portrait("Tower.jpg", "Healers Tower", 320, 55, 64, 96));
    addPic(new Portrait("fldQuest.jpg", "{1}Quest!", 145, 135, 96, 64));
    addPic(new Portrait("fldCamp.jpg", "Exit Game", 265, 185, 96, 64));
    addPic(new Portrait("fldForest.jpg", "{1}Forest Road", 10, 180, 96, 64));
    addPic(new Portrait("fldMound.jpg", "{1}Goblin Mound", 190, 30, 96, 64));
  }

  @Override // DCourt.Screens.Screen
  public void init() {
    super.init();
    questInit();
    getPic(4).show(Tools.getHero().getLevel() >= 4);
    getPic(5).show(Tools.getHero().getLevel() >= 8);
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
        Tools.setRegion(new arHealer(this));
        break;
      case 2:
        goQuesting();
        break;
      case 3:
        Tools.setRegion(Screen.tryToExit(this, Constants.FIELDS, 0));
        break;
      case 4:
        Tools.setRegion(enterForest());
        break;
      case 5:
        Tools.setRegion(enterMound());
        break;
    }
    return super.action(e, o);
  }

  Screen enterForest() {
    itHero h = Tools.getHero();
    if (h.getQuests() < 1) {
      return new arNotice(this, WildsScreen.TOO_TIRED);
    }
    if (!Tools.contest(h.getWits(), FINDFOREST)) {
      return h.getLevel() >= 6
          ? pickQuest(0)
          : new arNotice(
              pickQuest(0),
              "\tYou start hiking towards the distant woods. You are making good time, when suddenly...");
    }
    String msg =
        String.valueOf(
                String.valueOf(
                    String.valueOf(
                        String.valueOf(
                            new StringBuffer(
                                    "\tYou trudge along the dusty trail and occasion to wonder why you haven't seen any other travellers.\n\n\t")
                                .append(this.forests[Tools.roll(this.forests.length)])
                                .append("\n\n\tYou Enter the Forest...\n")))))
            .concat(String.valueOf(String.valueOf(h.gainWits(2))));
    h.travelWork(1);
    return new arNotice(new arForest(), msg);
  }

  Screen enterMound() {
    return Screen.getHero().getQuests() < 1
        ? new arNotice(this, WildsScreen.TOO_TIRED)
        : new arQuest(this, new arMound(), 2, "Goblin Mound Quest", Screen.findBeast("Mound:Gate"));
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public int getPower(int loc) {
    return 1;
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public String getWhere(int loc) {
    return "Fields";
  }

  @Override // DCourt.Screens.Template.WildsScreen
  public Screen pickQuest(int loc) {
    return new arQuest(
        this,
        1,
        "Fields Quest",
        selectQuest(beasts, Screen.getHero().getLevel() < 3 ? loweight : hiweight));
  }
}
