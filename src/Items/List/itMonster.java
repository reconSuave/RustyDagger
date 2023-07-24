package DCourt.Items.List;

import DCourt.Components.Portrait;
import DCourt.Control.GearTable;
import DCourt.Items.Item;
import DCourt.Items.Token.itCount;
import DCourt.Items.itList;
import DCourt.Static.ArmsTrait;
import DCourt.Static.Constants;
import DCourt.Static.GearTypes;
import DCourt.Tools.Buffer;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Items/List/itMonster.class */
public class itMonster extends itAgent {
    private Portrait picture;
    private itText text;
    private itList opts;
    private int baseA;
    private int baseD;
    private int baseS;
    private int stance;
    static final String PASSION = "PASSION";
   // static final String PASSION = PASSION;
    public static final String PASSIVE = "PASSIVE";
    //public static final String PASSIVE = PASSIVE;
    public static final String DEFENSIVE = "DEFENSIVE";
    //public static final String DEFENSIVE = DEFENSIVE;
    public static final String HOSTILE = "HOSTILE";
    //public static final String HOSTILE = HOSTILE;
    public static final String AGGRESIVE = "AGGRESIVE";
    //public static final String AGGRESIVE = AGGRESIVE;
    static final String TEXT = "TEXT";
    //static final String TEXT = TEXT;

    public itMonster(String name) {
        super(name);
    }

    public itMonster(itMonster it) {
        super(it);
        this.baseA = it.baseA;
        this.baseD = it.baseD;
        this.baseS = it.baseS;
    }

    @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public Item copy() {
        return new itMonster(this);
    }

    @Override // DCourt.Items.List.itAgent, DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public String getIcon() {
        return "itMonster";
    }

    @Override // DCourt.Items.List.itAgent, DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public String toString(int depth) {
        return String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(toStringHead(depth))).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("|").append(getGuts()).append("|").append(getWits()).append("|").append(getCharm())))))))).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("|").append(getAttack()).append("|").append(getDefend()).append("|").append(getSkill()).append("\n\t")))))))).concat(String.valueOf(String.valueOf(listBody(depth))));
    }

    public static Item factory(Buffer buf) {
        if (!buf.begin() || !buf.match("itMonster") || !buf.split()) {
            return null;
        }
        itMonster who = new itMonster(buf.token());
        who.loadAttributes(buf);
        who.loadSecondary(buf);
        who.loadBody(buf);
        who.fixLists();
        return who;
    }

    void loadSecondary(Buffer buf) {
        if (buf.split()) {
            this.baseA = buf.num();
        }
        if (buf.split()) {
            this.baseD = buf.num();
        }
        if (buf.split()) {
            this.baseS = buf.num();
        }
    }

    @Override // DCourt.Items.List.itAgent
    public void fixLists() {
        fixLists();
        this.text = (itText) find(TEXT);
        if (this.text == null) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("ERR: [text=null] for [").append(this).append("]"))));
        }
        Item it = find("pic");
        if (it == null) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("ERR: [pic=null] for [").append(this).append("]"))));
        } else {
            this.picture = new Portrait(it.getValue(), 0, 0, 80, 80);
            this.picture.setType(2);
        }
        this.opts = findList("opts");
        if (this.text == null) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("ERR: [opts=null] for [").append(this).append("]"))));
        }
    }

    public void balance(int weight) {
        if (isMatch(Constants.DRAGON) && Tools.getHero().hasTrait(Constants.DRAGON)) {
            getOptions().append("trade");
            this.stance--;
        }
        calcPrimary(weight);
        calcCombat();
        calcSecondary(weight);
        buildPack();
        buildGear(getGear().select(0));
        buildGear(getGear().select(1));
    }

    void alterGuts(float val) {
        setGuts((int) (((float) getGuts()) * val));
    }

    void alterWits(float val) {
        setWits((int) (((float) getWits()) * val));
    }

    void alterCharm(float val) {
        setCharm((int) (((float) getCharm()) * val));
    }

    void calcPrimary(int weight) {
        String id = this.text.getIdentity();
        if (id != null) {
            setName(id);
        }
        float ratio = 0.9f + (((float) Tools.getHero().getLevel()) * 0.1f);
        alterGuts(ratio);
        alterWits(ratio);
        alterCharm(ratio);
        setGuts(Tools.spread(getGuts()));
        setWits(Tools.spread(getWits()));
        setCharm(Tools.spread(getCharm()));
        if (hasTrait("adjust")) {
            float ratio2 = (1.0f + (((float) weight) * 0.1f)) / ((float) (getPower() / Tools.getHero().getPower()));
            if (ratio2 > 1.0f) {
                alterGuts(ratio2);
                alterWits(ratio2);
                alterCharm(ratio2);
                this.baseA = (int) (((float) this.baseA) * ratio2);
                this.baseD = (int) (((float) this.baseD) * ratio2);
                this.baseS = (int) (((float) this.baseS) * ratio2);
            }
        }
    }

    void calcSecondary(int weight) {
        int num = tempCount(Constants.ACTIONS);
        if (num == 0) {
            num = 1;
            fixTemp(Constants.ACTIONS, 1);
        }
        fixStatus(Constants.ACTIONS, num);
        fixStatus(Constants.FAME, (((getGuts() + getWits()) + getCharm()) / 30) + ((((thief() + magic()) + fight()) + weight) / 4));
        fixStatus(Constants.EXP, (((1 + getAttack()) + getDefend()) * (100 + getSkill())) / 100);
        String passion = getValues().getValue(PASSION);
        if ("aggressive".equals(passion)) {
            this.stance = 4;
        } else if (HOSTILE.equals(passion)) {
            this.stance = 3;
        } else if (DEFENSIVE.equals(passion)) {
            this.stance = 2;
        } else if ("timid".equals(passion)) {
            this.stance = 1;
        } else if (PASSIVE.equals(passion)) {
            this.stance = 0;
        } else {
            this.stance = 2;
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("Unknown [passion=").append(passion).append("] for [").append(this).append("]"))));
        }
    }

    void buildPack() {
        itList pack = getPack();
        itList from = (itList) pack.copy();
        pack.clrQueue();
        for (int ix = 0; ix < from.getCount(); ix++) {
            Item it = from.select(ix);
            if (GearTable.find(it)) {
                if (it instanceof itNote) {
                    pack.append(it);
                } else {
                    Item make = GearTable.shopItem(it);
                    if (!(make instanceof itArms)) {
                        int num = ((itCount) it).makeCount();
                        if (num >= 1) {
                            make.setCount(num);
                            pack.append(make);
                        }
                    } else if (Tools.percent(it.getCount()) && (!make.getName().startsWith("Silver") || Tools.percent(10))) {
                        ((itArms) make).tweak();
                        pack.append(make);
                    }
                }
            }
        }
    }

    void buildGear(Item it) {
        itArms make;
        if (((itCount) it).makeCount() >= 1 && (make = (itArms) GearTable.shopItem(it)) != null) {
            make.tweak();
            if (getGear().hasTrait(ArmsTrait.CURSE) && Tools.percent(25)) {
                make.fixTrait(ArmsTrait.CURSED);
            }
            if (getGear().hasTrait(ArmsTrait.BLESS)) {
                make.fixTrait(ArmsTrait.BLESS);
            }
            getPack().append(make);
        }
    }

    public void testGear() {
        for (int ix = 0; ix < getPack().getCount(); ix++) {
            GearTable.find(getPack().select(ix));
        }
        testItem(getGear().select(0), "weapon");
        testItem(getGear().select(1), "armour");
    }

    void testItem(Item it, String type) {
        if (it == null) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("ERR: No ").append(type).append(" for ").append(getName()))));
        } else if (!(it instanceof itCount)) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("ERR: Bad ").append(type).append(" for ").append(getName()))));
        } else if (it.getCount() > 0) {
            GearTable.find(it);
        }
    }

    public void resetActions() {
        itList acts = getActions();
        itList temp = getTemp();
        if (hasTrait("Blind")) {
            temp.zero(Constants.ACTIONS);
        } else {
            temp.fix(Constants.ACTIONS, statusCount(Constants.ACTIONS));
        }
        acts.clrQueue();
        acts.setName(Constants.ATTACK);
        setState(itAgent.ALIVE);
    }

    public String getText() {
        return this.text.getText();
    }

    @Override // DCourt.Items.List.itAgent
    public Portrait getPicture() {
        return this.picture;
    }

    public itList getOptions() {
        return this.opts;
    }

    public int baseExp() {
        return statusCount("exp");
    }

    public int baseFame() {
        return statusCount(Constants.FAME);
    }

    @Override // DCourt.Items.List.itAgent
    public String getWeapon() {
        return getGear().select(0).getName();
    }

    @Override // DCourt.Items.List.itAgent
    public String getArmour() {
        return getGear().select(1).getName();
    }

    @Override // DCourt.Items.List.itAgent
    protected int gearAttack() {
        return this.baseA;
    }

    @Override // DCourt.Items.List.itAgent
    protected int gearDefend() {
        return this.baseD;
    }

    @Override // DCourt.Items.List.itAgent
    protected int gearSkill() {
        return this.baseS;
    }

    public int getStance() {
        return this.stance;
    }

    public void incStance() {
        this.stance++;
    }

    public void setPassive() {
        this.stance = 0;
    }

    public boolean isAggresive() {
        return this.stance >= 4;
    }

    public boolean isHostile() {
        return this.stance == 3;
    }

    public boolean isDefensive() {
        return this.stance == 2;
    }

    public boolean isPassive() {
        return this.stance <= 1;
    }

    public void chooseActions(boolean first) {
        itHero enemy = Tools.getHero();
        int pm = packMagic();
        int ph = packHeal();
        int sk = guildSkill();
        itList acts = getActions();
        itList temp = getTemp();
        acts.setName(Constants.ATTACK);
        if (actions() < 1) {
            useSkills(first);
            return;
        }
        if ((hasTrait("Blind") || hasTrait("Panic")) && subPack(GearTypes.SELTZER, 1) == 1) {
            acts.add(GearTypes.SELTZER, 1);
            temp.clrTrait("Blind");
            temp.clrTrait("Panic");
            useAction();
        }
        int num = getWounds();
        int val = actions();
        if (num > val * 20 && ph > val && subPack(GearTypes.GINSENG, 1) == 1) {
            acts.add(GearTypes.GINSENG, 1);
            temp.add(Constants.ACTIONS, 2);
        }
        int num2 = num - actionHeal(GearTypes.TROLL, num, 30);
        int num3 = num2 - actionHeal(GearTypes.APPLE, num2, 30);
        int actionHeal = num3 - actionHeal(GearTypes.SALVE, num3, 15);
        if (temp.getCount(Constants.GOAT) > 0) {
            acts.setName(Constants.GOAT);
            temp.sub(Constants.GOAT, 1);
        } else if (temp.getCount(Constants.WORM) > 0) {
            acts.setName(Constants.WORM);
            temp.sub(Constants.WORM, 1);
        } else {
            int danger = getPower();
            int danger2 = ((enemy.getPower() - danger) * 4) / danger;
            int num4 = actions() + (packCount(GearTypes.GINSENG) * 2);
            if (pm > num4) {
                pm = num4;
            }
            if (sk > 0) {
                sk += danger2;
            }
            if (Tools.contest(pm, first ? sk - fight() : sk - thief())) {
                useMagic();
            } else {
                useSkills(first);
            }
        }
    }

    void useMagic() {
        int bd = packCount(GearTypes.BLIND_DUST);
        int pn = packCount(GearTypes.PANIC_DUST);
        int bt = packCount(GearTypes.BLAST_DUST);
        itList acts = getActions();
        itList temp = getTemp();
        acts.setName(Constants.SPELLS);
        while (bd + pn > actions() && subPack(GearTypes.GINSENG, 1) == 1) {
            acts.add(GearTypes.GINSENG, 1);
            temp.add(Constants.ACTIONS, 2);
        }
        while (bd + pn + bt > 0 && actions() > 0) {
            if (Tools.contest(bd, pn + bt)) {
                subPack(GearTypes.BLIND_DUST, 1);
                acts.add("Blind", 1);
                bd--;
            } else if (Tools.contest(pn, bt)) {
                subPack(GearTypes.PANIC_DUST, 1);
                acts.add("Panic", 1);
                pn--;
            } else {
                subPack(GearTypes.BLAST_DUST, 1);
                acts.add(ArmsTrait.BLAST, 1);
                bt--;
            }
            useAction();
        }
    }

    void useSkills(boolean first) {
        int wr = fight();
        int mg = magic();
        int tf = thief();
        int sm = ieatsu();
        itList acts = getActions();
        if (Tools.roll(3) >= this.stance) {
            acts.setName(Constants.RUNAWAY);
        } else if (first) {
            if (tf + mg + sm >= 1) {
                if (Tools.contest(mg, tf + sm)) {
                    acts.setName("Control");
                } else if (Tools.contest(sm, tf)) {
                    acts.setName(Constants.IEATSU);
                } else {
                    acts.setName(Tools.roll(2) == 0 ? "Swindle" : Constants.BACKSTAB);
                }
            }
        } else if (mg + wr >= 1) {
            if (Tools.contest(mg, wr)) {
                acts.setName("Control");
            } else {
                acts.setName(Constants.BERZERK);
            }
        }
    }

    int actionHeal(String id, int wounds, int val) {
        int num = (wounds + (val / 2)) / val;
        int has = packCount(id);
        if (num > has) {
            num = has;
        }
        int has2 = actions();
        if (num > has2) {
            num = has2;
        }
        useAction(num);
        getActions().add(id, num);
        return num * val;
    }

    public String goatSkill() {
        int val;
        itHero h = Tools.getHero();
        getActions().setName(Constants.ATTACK);
        if (!Tools.contest(2 * getGuts(), h.getGuts()) || (val = h.packCount("Rope")) == 0) {
            return "";
        }
        int num = 1 + Tools.roll(4) + Tools.roll(4);
        if (num > val) {
            num = val;
        }
        h.subPack("Rope", num);
        return String.valueOf(String.valueOf(new StringBuffer("\tThe ").append(getName()).append(" steals and devours ").append(num).append(" pieces of rope!  Baa-a-a-a!\n")));
    }

    public String wormSkill() {
        itHero h = Tools.getHero();
        getActions().setName(Constants.ATTACK);
        if (!Tools.contest(2 * getGuts(), h.getGuts())) {
            return "";
        }
        Item it = h.findGearTrait(ArmsTrait.GLOWS);
        if (it == null) {
            it = h.findGearTrait(ArmsTrait.FLAME);
        }
        if (it == null) {
            return "";
        }
        h.dropGear(it);
        it.decay(3);
        getPack().insert(it);
        return String.valueOf(String.valueOf(new StringBuffer("\tThe ").append(getName()).append(" rips the ").append(it.getName()).append(" from your body and swallows it whole!\n")));
    }
}
