package DCourt.Screens.Wilds;

import DCourt.Components.Portrait;
import DCourt.Items.List.itHero;
import DCourt.Screens.Areas.Mound.arGoblin;
import DCourt.Screens.Quest.arQuest;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.WildsScreen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Tools.Buffer;
import DCourt.Tools.Loader;
import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Wilds/arMound.class */
public class arMound extends WildsScreen {
    static final int FINDFIELDS = 1; // FINDFIELDS;
    static final int FINDVORTEX = 2; // FINDVORTEX;
    static final int[][] weight = {new int[]{5, 7, 3, 8, 4}, new int[]{5, 5, 5, 7, 3}, new int[]{5, 5, 5, 4, 2}};
    static final String[][] beasts = {new String[]{"Worm", "Thief", "Mage", "Gang", "Rager"}, new String[]{"Worm", "Thief", "Mage", "Guard", "Vault"}, new String[]{"Worm", "Thief", "Mage", "Queen", "Champ"}};
    static String[] fields = {"You spy an old sign that reads: 'Town Ahead'", "You find a strand of flowers just coming into bloom.", "You pass a pond that is fresh and sweet.", "You see horse droppings and wagon tracks.", "You pass a herd of wild horses feeding quietly.", "Songbirds circle above you...", "You hear distant laughter, or is it applause?", "You pass a homestead that has been newly built..."};

    public arMound() {
        super("The Bowels of the Goblin Mound");
        setBackground(new Color(192, 96, 48));
        setForeground(Color.white);
        setFont(Tools.textF);
        addPic(new Portrait("mndWarrens.jpg", "{1}Warrens", 145, 130, 96, 64));
        addPic(new Portrait("mndTreasury.jpg", "{1}Treasury", 30, 180, 96, 64));
        addPic(new Portrait("mndThrone.jpg", "{1}Throne Room", 280, 160, 96, 64));
        addPic(new Portrait("mndVortex.jpg", ">>Dark Vortex<<", 165, 25, 96, 64));
        addPic(new Portrait("mndFields.jpg", "{1}To Fields", 20, 40, 96, 64));
        addPic(new Portrait("Tavern.jpg", "Gobble Inn", Tools.DEFAULT_HEIGHT, 35, 96, 64));
        Screen.setPlace(Constants.MOUND);
    }

    @Override // DCourt.Screens.Screen
    public void init() {
        init();
        questInit();
        getPic(0).show(Screen.packCount("Map to Warrens") > 0);
        getPic(1).show(Screen.packCount("Map to Treasury") > 0);
        getPic(2).show(Screen.packCount("Map to Throne Room") > 0);
        getPic(3).show(Screen.packCount("Map to Vortex") > 0);
    }

    @Override // DCourt.Screens.Screen
    public void localPaint(Graphics g) {
        localPaint(g);
        g.setFont(Tools.courtF);
        g.setColor(new Color(96, 48, 24));
        g.drawString(getTitle(), 10, 20);
    }

    @Override // DCourt.Screens.Screen
    public boolean action(Event e, Object o) {
        if (Tools.movedAway(this)) {
            return true;
        }
        switch (getPic(e.target)) {
            case 0:
                goQuesting(0);
                break;
            case 1:
                goQuesting(1);
                break;
            case 2:
                goQuesting(2);
                break;
            case 3:
                Tools.setRegion(enterVortex());
                break;
            case 4:
                Tools.setRegion(enterFields());
                break;
            case 5:
                Tools.setRegion(new arGoblin(this));
                break;
        }
        return action(e, o);
    }

    @Override // DCourt.Screens.Template.WildsScreen
    public boolean needsLight(int loc) {
        return true;
    }

    @Override // DCourt.Screens.Template.WildsScreen
    public int getPower(int loc) {
        return 3;
    }

    @Override // DCourt.Screens.Template.WildsScreen
    public String getWhere(int pick) {
        return "Mound";
    }

    @Override // DCourt.Screens.Template.WildsScreen
    public Screen pickQuest(int loc) {
        return new arQuest(this, 3, "Goblin Mound Quest", selectQuest(loc, beasts, weight));
    }

    Screen enterFields() {
        itHero h = Screen.getHero();
        if (Screen.getQuests() < 1) {
            return new arNotice(this, WildsScreen.TOO_TIRED);
        }
        if (!Tools.contest(h.getWits(), FINDFIELDS)) {
            return pickQuest(0);
        }
        String msg = String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("\tYou trudge along the dusty trail and occasion to wonder why you haven't seen any other travellers.\n\n\t").append(fields[Tools.roll(fields.length)]).append("\n\n\tYou Enter the Fields...\n"))))).concat(String.valueOf(String.valueOf(h.gainWits(1))));
        h.addFatigue(1);
        return new arNotice(new arField(), msg);
    }

    Screen enterVortex() {
        if (Screen.getQuests() < 1) {
            return new arNotice(this, WildsScreen.TOO_TIRED);
        }
        Buffer buf = Loader.cgiBuffer(Loader.MESSAGE, null);
        if (buf == null || buf.isEmpty() || buf.isError()) {
            return null;
        }
        return new arQuest(this, new arNotice(this, buf.toString()), 4, "Vortex Mouth", Screen.findBeast("Vortex:Guard"));
    }
}
