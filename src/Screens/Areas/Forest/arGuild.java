package DCourt.Screens.Areas.Forest;

import DCourt.Items.List.itHero;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Indoors;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Event;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Forest/arGuild.class */
public class arGuild extends Indoors {
    Button[] tools;
    int[] cost;
    static String[] text = {"Guild Member", "{5}Fighter Skill", "{5}Magery Skill", "{5}Trader Skill"};
    static String[] someone = {null, "Silas Keep", "Sally Trader", "Bill Smith", "Aileen Suitor", "Elden Bishop", "Fenton Magus", "Gareth Shortlegs"};
    static String[] greeting = {null, "I am Fenton Magus", "Feel fear mortal", "Who violates these halls?", "Beware the Snot", "Elves are aloof", "Boar tastes like chicken", "Orcs are greedy", "Gryphons talk in riddles"};
    static final String joinMsg = String.valueOf(String.valueOf(new StringBuffer("\tYou are ushered into luxurious chambers where the guild membership awaits you, dressed in black robes to maintain anonymity.  Incense wafts past and somewhere above a gong sounds.\n\tYou think you see ").append(someone[Tools.roll(someone.length)]).append(" mingled in the crowd, but you can't be entirely sure.\n").append("\tA carpet is rolled back, revealing a blood soaked ").append("pentagram!  A goat is brought forward!! You are handed ").append("a condom!!!\n").append("\tThen they take your money and give you a guild pass. ").append("While everyone gets drunk, you are sworn to secrecy. ").append("You must never reveal the secret sacred rituals of the ").append("Free Adventurers Guild.\n\t\t\t\t\tBottoms Up!!!\n")));
    static final String fightMsg = fightMsg;
    static final String fightMsg = fightMsg;
    static final String magicMsg = magicMsg;
    static final String magicMsg = magicMsg;
    static final String thiefMsg = thiefMsg;
    static final String thiefMsg = thiefMsg;

    public arGuild(Screen from) {
        super(from, "The Free Adventurers Guild".concat(String.valueOf(String.valueOf(Screen.getQuests() < 5 ? " - Closed For Rituals" : ""))));
    }

    @Override // DCourt.Screens.Template.Indoors
    public String getFace() {
        return "Faces/Fenton.jpg";
    }

    @Override // DCourt.Screens.Template.Indoors
    public String getGreeting() {
        String msg = Tools.select(greeting);
        return msg == null ? "I've met ".concat(String.valueOf(String.valueOf(Tools.getBest()))) : msg;
    }

    @Override // DCourt.Screens.Screen
    public void localPaint(Graphics g) {
        itHero h = Screen.getHero();
        localPaint(g);
        updateTools(h);
        if (h.hasTrait(Constants.GUILD)) {
            g.drawString("Guild Training", 210, 60);
            g.drawString(String.valueOf(String.valueOf(new StringBuffer("Fighter: ").append(h.fight()).append("/").append(h.fightRank()))), 160, 80);
            g.drawString(String.valueOf(String.valueOf(new StringBuffer("Magery: ").append(h.magic()).append("/").append(h.magicRank()))), 160, 100);
            g.drawString(String.valueOf(String.valueOf(new StringBuffer("Trader: ").append(h.thief()).append("/").append(h.thiefRank()))), 280, 80);
            g.drawString(String.valueOf(String.valueOf(new StringBuffer("Total: ").append(h.guildRank()).append("/").append(h.getLevel()))), 280, 100);
        }
    }

    @Override // DCourt.Screens.Template.Indoors, DCourt.Screens.Screen
    public boolean action(Event e, Object o) {
        itHero h = Screen.getHero();
        Screen next = null;
        if (Tools.movedAway(this)) {
            return true;
        }
        if (e.target == getPic(0)) {
            next = getHome();
        }
        int i = 0;
        while (true) {
            if (i >= this.tools.length) {
                break;
            } else if (e.target != this.tools[i]) {
                i++;
            } else if (h.getMoney() >= this.cost[i]) {
                h.subMoney(this.cost[i]);
                switch (i) {
                    case 0:
                        next = joinGuild(h);
                        break;
                    case 1:
                        next = addFight(h);
                        break;
                    case 2:
                        next = addMagic(h);
                        break;
                    case 3:
                        next = addThief(h);
                        break;
                }
            }
        }
        Tools.setRegion(next);
        return action(e, o);
    }

    @Override // DCourt.Screens.Screen
    public void createTools() {
        this.tools = new Button[text.length];
        this.cost = new int[text.length];
        for (int i = 0; i < this.tools.length; i++) {
            this.tools[i] = new Button(text[i]);
            this.tools[i].reshape(170, 110 + (i * 30), 200, 25);
            this.tools[i].setFont(Tools.statusF);
        }
        updateTools(Screen.getHero());
    }

    @Override // DCourt.Screens.Screen
    public void addTools() {
        for (int i = 0; i < this.tools.length; i++) {
            add(this.tools[i]);
        }
    }

    public void updateTools(itHero h) {
        String msg;
        int cash = h.getMoney();
        int guild = h.guildRank();
        boolean closed = h.getQuests() < 5;
        boolean member = h.hasTrait(Constants.GUILD);
        boolean avail = !closed && member && guild < h.getLevel();
        this.cost[0] = 4000;
        for (int i = 1; i < this.tools.length; i++) {
            this.cost[i] = guild * 1000;
        }
        if (h.hasTrait(Constants.ILLUMINATI)) {
            for (int i2 = 0; i2 < 4; i2++) {
                int[] iArr = this.cost;
                iArr[i2] = iArr[i2] / 2;
            }
        }
        for (int i3 = 0; i3 < this.tools.length; i3++) {
            if (i3 == 0) {
                msg = member ? text[i3] : "{5}Join Guild $4000";
            } else {
                msg = String.valueOf(String.valueOf(text[i3])).concat(String.valueOf(String.valueOf(guild == 0 ? " Free" : " $".concat(String.valueOf(String.valueOf(this.cost[i3]))))));
            }
            this.tools[i3].setLabel(msg);
        }
        this.tools[0].enable(!closed && !member && cash >= this.cost[1]);
        for (int i4 = 1; i4 < this.tools.length; i4++) {
            this.tools[i4].enable(avail && cash >= this.cost[i4]);
        }
    }

    Screen joinGuild(itHero h) {
        h.getStatus().fixTrait(Constants.GUILD);
        h.addFatigue(5);
        return new arNotice(this, joinMsg);
    }

    Screen addFight(itHero h) {
        h.addWits(-2);
        h.addCharm(-2);
        h.addRank(Constants.FIGHT, 1);
        h.addTemp(Constants.FIGHT, 1);
        h.addFatigue(5);
        return new arNotice(this, fightMsg);
    }

    Screen addMagic(itHero h) {
        h.addGuts(-2);
        h.addCharm(-2);
        h.addRank(Constants.MAGIC, 1);
        h.addTemp(Constants.MAGIC, 1);
        h.addFatigue(5);
        return new arNotice(this, magicMsg);
    }

    Screen addThief(itHero h) {
        h.addGuts(-2);
        h.addWits(-2);
        h.addRank(Constants.THIEF, 1);
        h.addTemp(Constants.THIEF, 1);
        h.addFatigue(5);
        return new arNotice(this, thiefMsg);
    }
}
