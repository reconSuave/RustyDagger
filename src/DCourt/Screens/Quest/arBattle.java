package DCourt.Screens.Quest;

import DCourt.Items.Item;
import DCourt.Items.List.itAgent;
import DCourt.Items.List.itArms;
import DCourt.Items.List.itHero;
import DCourt.Items.List.itMonster;
import DCourt.Items.itList;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.ArmsTrait;
import DCourt.Static.Constants;
import DCourt.Static.GearTypes;
import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.util.Enumeration;

/* loaded from: DCourt.jar:DCourt/Screens/Quest/arBattle.class */
public class arBattle extends Screen implements GearTypes {
    String events;
    String text;
    String[] power = {"Fly Swat", "Weak Blow", "Good Hit", "Potent Hit", "POWER HIT!"};
    String[] effect = {"DODGED!", "Unharmed", "Scratched", "Injured!", "Wounded!!", "KILLED!!!"};
    boolean killStop = false;
    itHero hero = Screen.getHero();
    itMonster mob;
    arQuest quest;
    static final String ABUF = "    ---";
    static final String[] berzerks = {"With a scream of fury, you tear into it!", "Foaming at the mouth you leap onto it!", "You attack like a whirlwind, screaming!", "KILL!  KILL!  KILL!  KILL!  KILL!  KILL!"};
    static final String[] backstabs = {"You pat it on the back with a knife", "You point down \"Hey your shoes are untied\"", "You pretend to leave, but circle back!", "I'm your best friend - DIE! DIE! DIE!"};

    public arBattle(arQuest from, String msg) {
        super(from, "Battle Screen");
        hideStatusBar();
        setBackground(new Color(192, 0, 0));
        setForeground(Color.white);
        setFont(Tools.courtF);
        this.quest = from;
        this.mob = from.getMob();
        addPic(this.mob.getPicture());
        getPic(0).reshape(10, 10, 160, 160);
        addPic(this.hero.getPicture());
        getPic(1).reshape(230, 10, 160, 160);
        this.text = battle(this.hero, this.mob);
        this.events = combatEvents(msg);
    }

    @Override // DCourt.Screens.Screen
    public void init() {
        init();
        if (this.events.length() > 0) {
            Tools.setRegion(new arNotice(this, this.events));
            this.events = "";
        }
    }

    @Override // DCourt.Screens.Screen
    public void localPaint(Graphics g) {
        int v = 170;
        String msg = this.text;
        g.setColor(getForeground());
        g.setFont(getFont());
        while (true) {
            int ix = msg.indexOf(10);
            if (ix == -1) {
                g.drawString(msg, 5, v + 20);
                return;
            }
            v += 20;
            g.drawString(msg.substring(0, ix), 5, v);
            msg = msg.substring(ix + 1);
        }
    }

    @Override // DCourt.Screens.Screen
    public boolean action(Event e, Object o) {
        if (Tools.movedAway(this)) {
            return true;
        }
        this.hero.clearDump();
        this.quest.battleActionResult();
        return true;
    }

    String battle(itAgent hero, itAgent mob) {
        boolean heroFirst;
        String msg;
        int hguts = hero.getGuts();
        int hspeed = hero.skill();
        int hhit = Tools.twice(3);
        int mguts = mob.getGuts();
        int mspeed = mob.skill();
        int mhit = Tools.twice(3);
        itList ha = hero.getActions();
        itList ma = mob.getActions();
        if (ha.isMatch(Constants.BACKSTAB)) {
            hguts *= 2;
            hspeed *= 2;
            mhit = 1;
        } else if (ha.isMatch(Constants.BERZERK) || ha.isMatch(Constants.IEATSU)) {
            hguts *= 2;
            hspeed *= 2;
            hhit = 4;
        } else if (ha.isMatch("Control")) {
            hspeed = hero.getWits();
        } else if (ha.isMatch("Swindle")) {
            hspeed = hero.getCharm();
        }
        if (hero.hasTrait(Constants.REFLEX)) {
            hspeed += 30;
        }
        if (hero.hasTrait("Blind")) {
            hspeed /= 2;
            hhit /= 2;
        }
        if (ma.isMatch(Constants.BACKSTAB)) {
            mguts *= 2;
            mspeed *= 2;
            hhit = 1;
        } else if (ma.isMatch(Constants.BERZERK) || ma.isMatch(Constants.IEATSU)) {
            mguts *= 2;
            mspeed *= 2;
            mhit = 4;
        } else if (ma.isMatch("Control")) {
            mspeed = mob.getWits();
        } else if (ma.isMatch("Swindle")) {
            mspeed = mob.getCharm();
        }
        if (mob.hasTrait(Constants.REFLEX)) {
            mspeed += 30;
        }
        if (mob.hasTrait("Blind")) {
            mspeed /= 2;
            mhit /= 2;
        }
        if (ma.isMatch(Constants.RUNAWAY) && !ha.isMatch(Constants.RUNAWAY)) {
            heroFirst = true;
        } else if (!ha.isMatch(Constants.RUNAWAY) || ma.isMatch(Constants.RUNAWAY)) {
            heroFirst = Tools.contest(hspeed, mspeed);
        } else {
            heroFirst = false;
        }
        if (heroFirst) {
            msg = String.valueOf(String.valueOf("")).concat(String.valueOf(String.valueOf(agentAct(hero, mob, hguts, hhit, hspeed, mspeed))));
            if (!this.killStop) {
                msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(agentAct(mob, hero, mguts, mhit, mspeed, hspeed))));
            }
        } else {
            msg = String.valueOf(String.valueOf("")).concat(String.valueOf(String.valueOf(agentAct(mob, hero, mguts, mhit, mspeed, hspeed))));
            if (!this.killStop) {
                msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(agentAct(hero, mob, hguts, hhit, hspeed, mspeed))));
            }
        }
        return msg;
    }

    String agentAct(itAgent at, itAgent df, int guts, int hit, int as, int ds) {
        int stk;
        int dmg;
        itList act = at.getActions();
        boolean useBlast = false;
        if (act.isMatch("Control")) {
            return actorControls(at, df, 2 * at.getWits());
        }
        if (act.isMatch("Swindle")) {
            return actorSwindles(at, df, 2 * at.getCharm());
        }
        if (act.isMatch(Constants.BACKSTAB)) {
            at.thief(1);
            if (df.hasTrait(Constants.ALERT)) {
                ds += 30;
            }
        }
        if (act.isMatch(Constants.BERZERK)) {
            at.fight(1);
            if (df.hasTrait(Constants.FENCER)) {
                ds += 30;
            }
        }
        if (act.isMatch(Constants.IEATSU)) {
            at.ieatsu(1);
            if (df.hasTrait(Constants.FENCER)) {
                ds += 30;
            }
        }
        itArms weapon = at.getGear().findArms(ArmsTrait.RIGHT);
        if (weapon != null && weapon.hasTrait(ArmsTrait.BLAST)) {
            at.getActions().add(ArmsTrait.BLAST, 1);
        }
        if (Tools.roll(ds) > as) {
            dmg = 0;
            stk = 0;
        } else {
            dmg = (((guts * (2 + hit)) / 10) + at.getAttack()) - df.getDefend();
            int val = 25 * at.getActions().getCount(ArmsTrait.BLAST);
            useBlast = val > dmg;
            if (useBlast) {
                dmg = val;
            } else {
                at.getActions().drop(ArmsTrait.BLAST);
            }
            int val2 = df.getGuts() - df.getWounds();
            if (dmg < 1) {
                stk = 1;
            } else {
                stk = dmg >= val2 ? 5 : 2 + ((3 * dmg) / val2);
            }
        }
        if (stk > 1) {
            df.addWounds(dmg);
        }
        if (stk == 5) {
            this.killStop = true;
            df.setState(itAgent.DEAD);
        }
        if (weapon != null) {
            if (weapon.hasTrait("Blind")) {
                at.getActions().add("Blind", 1);
            }
            if (weapon.hasTrait("Panic")) {
                at.getActions().add("Panic", 1);
            }
            if (!useBlast && weapon.hasTrait("Disease")) {
                at.getActions().add("Disease", (dmg + 3) / 5);
            }
        }
        String msg = String.valueOf(String.valueOf(at.getName())).concat(":");
        return String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(act.isMatch(Constants.ATTACK) ? String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(this.power[hit]))) : String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(act.getName()))))).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("\n    ---").append(df.getName()).append(" ").append(this.effect[stk])))))))).concat(String.valueOf(String.valueOf(spellEffects(at, df)))))).concat("\n");
    }

    String actorControls(itAgent at, itAgent df, int as) {
        String msg;
        at.magic(1);
        String msg2 = String.valueOf(String.valueOf(at.getName())).concat(" tries Hypnosis!\n");
        int ds = df.getWits();
        if (df.hasTrait(Constants.STUBBORN)) {
            ds += 30;
        }
        if (Tools.contest(as, ds)) {
            msg = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(ABUF).append(df.getName()).append(" is Mesmerized!\n"))))));
            at.setState("Control");
            this.killStop = true;
        } else {
            msg = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("    ---But the ").append(df.getName()).append(" Resists!\n"))))));
        }
        return msg;
    }

    String actorSwindles(itAgent at, itAgent df, int as) {
        String msg;
        at.thief(1);
        String msg2 = String.valueOf(String.valueOf(at.getName())).concat(" starts 'Trading'!\n");
        int ds = df.getCharm();
        if (df.hasTrait(Constants.CLEVER)) {
            ds += 30;
        }
        if (Tools.contest(as, ds)) {
            msg = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(ABUF).append(df.getName()).append(" falls for It!\n"))))));
            at.setState("Swindle");
            this.killStop = true;
        } else {
            msg = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("    ---But the ").append(df.getName()).append(" is too Cunning!\n"))))));
        }
        return msg;
    }

    String spellEffects(itAgent at, itAgent df) {
        String msg = "";
        Enumeration e = at.getActions().elements();
        while (e.hasMoreElements()) {
            Item it = (Item) e.nextElement();
            if (it.isMatch("Blind") && Tools.contest(at.getWits() * it.getCount(), df.getWits())) {
                msg = String.valueOf(String.valueOf(msg)).concat(" *BLIND*");
                df.getTemp().fixTrait("Blind");
            }
            if (it.isMatch("Panic") && Tools.contest(at.getWits() * it.getCount(), df.getWits())) {
                msg = String.valueOf(String.valueOf(msg)).concat(" +PANIC+");
                if (df instanceof itMonster) {
                    ((itMonster) df).setPassive();
                }
                df.getTemp().fixTrait("Panic");
            }
            if (it.isMatch("Disease") && it.getCount() > 0) {
                msg = String.valueOf(String.valueOf(msg)).concat(" ^Sick^");
                if (df.hasTrait(Constants.HARDY)) {
                    df.ail(it.getCount() / 2);
                } else {
                    df.ail(it.getCount());
                }
            }
            if (it.isMatch(ArmsTrait.BLAST)) {
                msg = String.valueOf(String.valueOf(msg)).concat(" >KABOOM<");
            }
        }
        return msg;
    }

    String combatEvents(String msg) {
        itList ma = this.mob.getActions();
        if (msg == null) {
            msg = "";
        }
        int num = ma.getCount(GearTypes.GINSENG);
        if (num > 0) {
            msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("\tThe ").append(this.mob.getName()).append(" gains energy by eating ").append(num).append(" ").append(GearTypes.GINSENG).append("\n"))))));
        }
        if (ma.getCount(GearTypes.SELTZER) > 0) {
            msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("\tThe ").append(this.mob.getName()).append(" washes dust from its ").append("eyes by using ").append(GearTypes.SELTZER).append("\n"))))));
        }
        int val = ma.getCount(GearTypes.APPLE);
        int num2 = ma.getCount(GearTypes.SALVE);
        if (val > 0 || num2 > 0) {
            String msg2 = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("\tThe ").append(this.mob.getName()).append(" swallows"))))));
            if (val > 0) {
                msg2 = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(" ").append(val).append(" ").append(GearTypes.APPLE))))));
            }
            if (val > 0 && num2 > 0) {
                msg2 = String.valueOf(String.valueOf(msg2)).concat(" and");
            }
            if (num2 > 0) {
                msg2 = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(" ").append(num2).append(" ").append(GearTypes.SALVE))))));
            }
            msg = String.valueOf(String.valueOf(msg2)).concat(" healing its wounds.\n");
            while (true) {
                val--;
                if (val >= 0) {
                    this.mob.doRevive();
                    this.mob.subPack(GearTypes.APPLE, 1);
                } else {
                    break;
                }
            }
            while (true) {
                num2--;
                if (num2 < 0) {
                    break;
                }
                this.mob.doHeal();
                this.mob.subPack(GearTypes.SALVE, 1);
            }
        }
        int val2 = ma.getCount(GearTypes.TROLL);
        if (val2 > 0) {
            msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("\tThe ").append(this.mob.getName()).append(" regenerates!\n"))))));
            while (true) {
                val2--;
                if (val2 < 0) {
                    break;
                }
                this.mob.doRevive();
                this.mob.subPack(GearTypes.TROLL, 1);
            }
        }
        if (ma.isMatch(Constants.GOAT)) {
            msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(this.mob.goatSkill())));
        }
        if (ma.isMatch(Constants.WORM)) {
            msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(this.mob.wormSkill())));
        }
        return msg;
    }
}
