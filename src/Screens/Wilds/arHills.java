package DCourt.Screens.Wilds;

import DCourt.Components.Portrait;
import DCourt.Items.List.itHero;
import DCourt.Screens.Areas.Hills.arGemShop;
import DCourt.Screens.Areas.Hills.arMagicShop;
import DCourt.Screens.Quest.arQuest;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.WildsScreen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Event;

/* loaded from: DCourt.jar:DCourt/Screens/Wilds/arHills.class */
public class arHills extends WildsScreen {
    int hidden = 7;
    String[] forests = {"You spy an old sign that reads: 'Danger!'", "You find a human skull with an arrow embedded in it...", "You pass a pond that is obviously poisonous.", "You find animal droppings. There are chainmail links in it...", "You find a horse skeleton. Something big was eating it...", "Vultures circle above you...", "You hear distance howling, or is it screaming?", "You pass a homestead that has been burned to the ground..."};
    static final int FINDFOREST = FINDFOREST;
    static final int FINDFOREST = FINDFOREST;
    static final int SEARCH = SEARCH;
    static final int SEARCH = SEARCH;
    static final int[] weights = {7, 5, 5, 4, 3, 3};
    static final String[] beasts = {"Goat", "Basilisk", "Troll", "Wyvern", "Giant", "Sphinx"};
    static final String[] found = {"The Jewel Exchange atop a misty peak!\n", "Djinni's Magic Shop floating on a cloud!\n", "A dangerous shaft leading to the Abandoned Mines!\n"};

    public arHills() {
        super("High Crags of the Fenris Mountains");
        setBackground(new Color(160, 160, 160));
        setForeground(Color.white);
        setFont(Tools.textF);
        addPic(new Portrait("hllJewels.jpg", "Jewel Store", Tools.DEFAULT_HEIGHT, 20, 96, 64));
        addPic(new Portrait("hllMagics.jpg", "Magic Shop", 20, FINDFOREST, 96, 64));
        addPic(new Portrait("hllMines.jpg", "{1}Abandoned Mines", 150, 180, 96, 64));
        addPic(new Portrait("hllQuest.jpg", "{1}Quest", 170, SEARCH, 96, 64));
        addPic(new Portrait("hllCamp.jpg", "Exit Game", 10, 175, 96, 64));
        addPic(new Portrait("hllForest.jpg", "{1}Forest Trail", Tools.DEFAULT_HEIGHT, 180, 96, 64));
        Screen.setPlace(Constants.HILLS);
        for (int ix = 0; ix < 3; ix++) {
            getPic(ix).hide();
        }
    }

    @Override // DCourt.Screens.Screen
    public void init() {
        init();
        questInit();
    }

    @Override // DCourt.Screens.Screen
    public boolean action(Event e, Object o) {
        if (Tools.movedAway(this)) {
            return true;
        }
        switch (getPic(e.target)) {
            case 0:
                Tools.setRegion(new arGemShop(this));
                break;
            case 1:
                Tools.setRegion(new arMagicShop(this));
                break;
            case 2:
                Tools.setRegion(cavern());
                break;
            case 3:
                goQuesting();
                break;
            case 4:
                Tools.setRegion(Screen.tryToExit(this, Constants.HILLS, 0));
                break;
            case 5:
                Tools.setRegion(forest());
                break;
        }
        return action(e, o);
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
        return "While hiking over rocky ridges you discover...\n\n".concat(String.valueOf(String.valueOf(found[pick])));
    }

    @Override // DCourt.Screens.Template.WildsScreen
    public boolean needsRope(int loc) {
        return true;
    }

    @Override // DCourt.Screens.Template.WildsScreen
    public int getPower(int loc) {
        return 3;
    }

    @Override // DCourt.Screens.Template.WildsScreen
    public String getWhere(int loc) {
        return "Hills";
    }

    @Override // DCourt.Screens.Template.WildsScreen
    public Screen pickQuest(int loc) {
        return new arQuest(this, 3, "Mountain Quest", selectQuest(beasts, weights));
    }

    Screen forest() {
        itHero h = Screen.getHero();
        if (h.getQuests() < 1) {
            return new arNotice(this, WildsScreen.TOO_TIRED);
        }
        if (!Tools.contest(h.getWits(), FINDFOREST)) {
            return pickQuest(0);
        }
        String msg = String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("\tYou trudge along the dusty trail and occasion to wonder why you haven't seen any other travellers.\n\n\t").append(this.forests[Tools.roll(this.forests.length)]).append("\n\n\tYou Enter the Forest...\n"))))).concat(String.valueOf(String.valueOf(h.gainWits(2))));
        h.travelWork(1);
        return new arNotice(new arForest(), msg);
    }

    Screen cavern() {
        return Screen.getQuests() < 1 ? new arNotice(this, WildsScreen.TOO_TIRED) : !findClimb() ? new arNotice(this, WildsScreen.NEED_ROPE) : new arQuest(this, this, 5, "Deep Mines Quest", Screen.findBeast("Hills:Dragon"));
    }
}
