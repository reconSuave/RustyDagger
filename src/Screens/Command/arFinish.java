package DCourt.Screens.Command;

import DCourt.Components.Portrait;
import DCourt.Items.List.itHero;
import DCourt.Items.itList;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Static.GameStrings;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Command/arFinish.class */
public class arFinish extends Screen implements Constants {
    Button lists;
    Button credits;
    itHero hero = Screen.getHero();
    itList start = Screen.getPlayer().getStart();
    static final String[] path = {"Final/Bed.jpg", "Final/Floor.jpg", "Final/Camp.jpg", "Final/Dead.jpg"};
    static final int[] which = {0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2};
    static final String EQL = "EQL";
    // static final String EQL = EQL;

    public arFinish() {
        super("Time to Finally Rest");
        setBackground(new Color(255, 128, 128));
        setForeground(new Color(64, 32, 32));
        setFont(Tools.statusF);
        hideStatusBar();
    }

    @Override // DCourt.Screens.Screen
    public void localPaint(Graphics g) {
        g.setFont(Tools.courtF);
        g.setColor(getForeground());
        g.drawString(getTitle(), 10, 15);
        g.drawString("Today's Gains:", 260, 55);
        g.drawString(String.valueOf(String.valueOf(new StringBuffer("Statistics for ").append(this.hero.getTitle()).append(this.hero.getName()))), 10, 195);
        g.drawString("Reload/Refresh to Play Again", 10, 295);
        g.setFont(Tools.statusF);
        int v = 62;
        if (guts() != 0) {
            v = 62 + 20;
            g.drawString(value(Constants.GUTS, guts()), 275, v);
        }
        if (wits() != 0) {
            v += 20;
            g.drawString(value(Constants.WITS, wits()), 275, v);
        }
        if (charm() != 0) {
            v += 20;
            g.drawString(value(Constants.CHARM, charm()), 275, v);
        }
        if (attack() != 0) {
            v += 20;
            g.drawString(value(Constants.ATTACK, attack()), 275, v);
        }
        if (defend() != 0) {
            v += 20;
            g.drawString(value(Constants.DEFEND, defend()), 275, v);
        }
        if (skill() != 0) {
            v += 20;
            g.drawString(value(Constants.SKILL, skill()), 275, v);
        }
        if (quests() != 0) {
            v += 20;
            g.drawString(value("Quests", quests()), 275, v);
        }
        if (v == 62) {
            g.drawString("Spirit!", 275, v + 20);
        }
        if (level() > 0) {
            g.drawString("Power burns within you: ".concat(String.valueOf(String.valueOf(value(Constants.LEVEL, level())))), 20, 220);
        } else {
            g.drawString("Difficult lessons learned: ".concat(String.valueOf(String.valueOf(value(Constants.EXP, exp())))), 20, 220);
        }
        g.drawString(String.valueOf(String.valueOf(new StringBuffer("Your legend has ").append(fame() < 0 ? "declined" : "grown").append(": ").append(value(Constants.FAME, fame())))), 20, 245);
        g.drawString(String.valueOf(String.valueOf(new StringBuffer("Wealth accumulates ").append(money() < 0 ? "slowly" : "rapidly").append(": ").append(value("Marks", money())))), 20, 270);
    }

    @Override // DCourt.Screens.Screen
    public boolean action(Event e, Object o) {
        if (Tools.movedAway(this)) {
            return true;
        }
        if (e.target == this.credits) {
            Tools.setRegion(new arNotice(this, GameStrings.creditText));
        } else if (e.target == this.lists) {
            Tools.setRegion(new arRanking(this));
        } else {
            repaint();
        }
        return action(e, o);
    }

    @Override // DCourt.Screens.Screen
    public void createTools() {
        int val;
        if (Screen.getPlayer().isDead()) {
            val = 3;
        } else {
            int i = Constants.PLACE_LIST.firstOf(Screen.getPlace());
            val = (i < 0 || i >= 11) ? 3 : which[i];
        }
        addPic(new Portrait(path[val], "", 5, 20, 240, 160));
        this.lists = new Button("Lists");
        this.lists.reshape(260, 10, 60, 20);
        this.lists.setFont(Tools.textF);
        this.credits = new Button("Credits");
        this.credits.reshape(330, 10, 60, 20);
        this.credits.setFont(Tools.textF);
    }

    @Override // DCourt.Screens.Screen
    public void addTools() {
        add(this.lists);
        add(this.credits);
    }

    String value(String what, int val) {
        return val < 0 ? String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(val))).append(" ").append(what))) : String.valueOf(String.valueOf(new StringBuffer("+").append(val).append(" ").append(what)));
    }

    int startCount(String id) {
        return this.start.getCount(id);
    }

    int guts() {
        return this.hero.getGuts() - startCount(Constants.GUTS);
    }

    int wits() {
        return this.hero.getWits() - startCount(Constants.WITS);
    }

    int charm() {
        return this.hero.getCharm() - startCount(Constants.CHARM);
    }

    int attack() {
        return this.hero.getAttack() - startCount(Constants.ATTACK);
    }

    int defend() {
        return this.hero.getDefend() - startCount(Constants.DEFEND);
    }

    int skill() {
        return this.hero.getSkill() - startCount(Constants.SKILL);
    }

    int quests() {
        return 3 * (this.hero.getLevel() - startCount(Constants.LEVEL));
    }

    int level() {
        return this.hero.getLevel() - startCount(Constants.LEVEL);
    }

    int exp() {
        return this.hero.getExp() - startCount(Constants.EXP);
    }

    int fame() {
        return this.hero.getFame() - startCount(Constants.FAME);
    }

    int money() {
        return this.hero.getMoney() - startCount("Marks");
    }
}
