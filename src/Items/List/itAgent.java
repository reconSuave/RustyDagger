package DCourt.Items.List;

import DCourt.Components.Portrait;
import DCourt.Items.Item;
import DCourt.Items.Token.itCount;
import DCourt.Items.itList;
import DCourt.Static.ArmsTrait;
import DCourt.Static.Constants;
import DCourt.Static.GearTypes;
import DCourt.Tools.Buffer;

/* loaded from: DCourt.jar:DCourt/Items/List/itAgent.class */
public abstract class itAgent extends itList implements Constants, GearTypes, ArmsTrait {
    private String picfile;
    private itCount gval;
    private itCount wval;
    private itCount cval;
    private itCount aval;
    private itCount dval;
    private itCount sval;
    private itList pack;
    private itList gear;
    private itList stat;
    private itList temp;
    private itList rank;
    private itList vals;
    private itList acts;
    static final String STATE = STATE;
    //static final String STATE = STATE;
    //public static final String ALIVE = ALIVE;
    public static final String ALIVE = ALIVE;
    //public static final String DEAD = DEAD;
    public static final String DEAD = DEAD;
   // public static final String CREATE = CREATE;
    public static final String CREATE = CREATE;
    public static final String CONTROL = "Control";
    public static final String SWINDLE = "Swindle";
  //  static final String PACK = PACK;
    static final String PACK = PACK;
   // static final String GEAR = GEAR;
    static final String GEAR = GEAR;
  //  static final String STAT = STAT;
    static final String STAT = STAT;
   // static final String TEMP = TEMP;
    static final String TEMP = TEMP;
  //  static final String ACTS = ACTS;
    static final String ACTS = ACTS;
 //   static final String RANK = RANK;
    static final String RANK = RANK;
  //  static final String OPTS = OPTS;
    static final String OPTS = OPTS;
 //   static final String PIC = PIC;
    static final String PIC = PIC;
 //   static final String VALUES = VALUES;
    static final String VALUES = VALUES;

    public abstract String getWeapon();

    public abstract String getArmour();

    protected abstract int gearAttack();

    protected abstract int gearDefend();

    protected abstract int gearSkill();

    public itAgent(String id) {
        super(id);
        setVals(0, 0, 0, 0, 0, 0);
    }

    public itAgent(itAgent it) {
        super(it);
        setVals(it.getGuts(), it.getWits(), it.getCharm(), it.getAttack(), it.getDefend(), it.getSkill());
        fixLists();
    }

    public void setVals(int g, int w, int c, int a, int d, int s) {
        this.gval = new itCount("g", g);
        this.wval = new itCount("w", w);
        this.cval = new itCount("c", c);
        this.aval = new itCount("a", a);
        this.dval = new itCount("d", d);
        this.sval = new itCount("s", s);
    }

    public void setGuts(int num) {
        this.gval.setCount(num);
    }

    public void setWits(int num) {
        this.wval.setCount(num);
    }

    public void setCharm(int num) {
        this.cval.setCount(num);
    }

    public void setAttack(int num) {
        this.aval.setCount(num);
    }

    public void setDefend(int num) {
        this.dval.setCount(num);
    }

    public void setSkill(int num) {
        this.sval.setCount(num);
    }

    public void addGuts(int num) {
        this.gval.adds(num);
    }

    public void addWits(int num) {
        this.wval.adds(num);
    }

    public void addCharm(int num) {
        this.cval.adds(num);
    }

    public void addAttack(int num) {
        this.aval.adds(num);
    }

    public void addDefend(int num) {
        this.dval.adds(num);
    }

    public void addSkill(int num) {
        this.sval.adds(num);
    }

    public int getGuts() {
        return this.gval.getCount();
    }

    public int getWits() {
        return this.wval.getCount();
    }

    public int getCharm() {
        return this.cval.getCount();
    }

    public int getAttack() {
        return this.aval.getCount();
    }

    public int getDefend() {
        return this.dval.getCount();
    }

    public int getSkill() {
        return this.sval.getCount();
    }

    @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public String getIcon() {
        return "itAgent";
    }

    @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public String toString(int depth) {
        return String.valueOf(String.valueOf(String.valueOf(String.valueOf(toStringHead(depth))).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("|").append(getGuts()).append("|").append(getWits()).append("|").append(getCharm()).append("\n\t")))))))).concat(String.valueOf(String.valueOf(listBody(depth))));
    }

    public void loadAttributes(Buffer buf) {
        if (buf.split()) {
            setGuts(buf.num());
        }
        if (buf.split()) {
            setWits(buf.num());
        }
        if (buf.split()) {
            setCharm(buf.num());
        }
    }

    public void fixLists() {
        this.picfile = findValue(PIC);
        this.pack = findList(PACK);
        this.gear = findList(GEAR);
        this.stat = findList(STAT);
        this.temp = findList(TEMP);
        this.rank = findList(RANK);
        this.vals = findList(VALUES);
        this.acts = new itList(ACTS);
    }

    public String findValue(String name) {
        Item it = find(name);
        if (it == null) {
            return null;
        }
        return it.getValue();
    }

    public itList findList(String name) {
        Item it = find(name);
        if (it == null) {
            Item itlist = new itList(name);
            it = itlist;
            append(itlist);
        }
        return (itList) it;
    }

    public Portrait getPicture() {
        return null;
    }

    public String getState() {
        return getValues().getValue(STATE);
    }

    public void setState(String val) {
        getValues().fix(STATE, val);
    }

    public boolean isAlive() {
        return ALIVE.equals(getState());
    }

    public boolean isDead() {
        return DEAD.equals(getState());
    }

    public boolean isCreate() {
        return CREATE.equals(getState());
    }

    public boolean isControl() {
        return "Control".equals(getState());
    }

    public boolean isSwindle() {
        return "Swindle".equals(getState());
    }

    public itList getPack() {
        return this.pack;
    }

    public itList getGear() {
        return this.gear;
    }

    public itList getStatus() {
        return this.stat;
    }

    public itList getTemp() {
        return this.temp;
    }

    public itList getActions() {
        return this.acts;
    }

    public itList getRank() {
        return this.rank;
    }

    public itList getValues() {
        return this.vals;
    }

    public int rankCount(Item it) {
        return this.rank.getCount(it);
    }

    public int rankCount(String id) {
        return this.rank.getCount(id);
    }

    public void fixRank(String id, int num) {
        this.rank.fix(id, num);
    }

    public int addRank(String id, int num) {
        return this.rank.add(id, num);
    }

    public int subRank(String id, int num) {
        return this.rank.sub(id, num);
    }

    public int tempCount(Item it) {
        return this.temp.getCount(it);
    }

    public int tempCount(String id) {
        return this.temp.getCount(id);
    }

    public void fixTemp(String id, int num) {
        this.temp.fix(id, num);
    }

    public int addTemp(String id, int num) {
        return this.temp.add(id, num);
    }

    public int subTemp(String id, int num) {
        return this.temp.sub(id, num);
    }

    public void fixTempTrait(String id) {
        this.temp.fixTrait(id);
    }

    public void clrTempTrait(String id) {
        this.temp.clrTrait(id);
    }

    public int statusCount(Item it) {
        return this.stat.getCount(it);
    }

    public int statusCount(String id) {
        return this.stat.getCount(id);
    }

    public void fixStatus(String id, int num) {
        this.stat.fix(id, num);
    }

    public int addStatus(String id, int num) {
        return this.stat.add(id, num);
    }

    public int subStatus(String id, int num) {
        return this.stat.sub(id, num);
    }

    public void fixStatTrait(String id) {
        this.stat.fixTrait(id);
    }

    public void clrStatTrait(String id) {
        this.stat.clrTrait(id);
    }

    public itArms findGearTrait(String id) {
        return this.gear.findArms(id);
    }

    public void dropGear(Item it) {
        this.gear.drop(it);
    }

    public int packCount() {
        return this.pack.getCount();
    }

    public int packCount(Item it) {
        return this.pack.getCount(it);
    }

    public int packCount(String id) {
        return this.pack.getCount(id);
    }

    public void fixPack(String id, int num) {
        this.pack.fix(id, num);
    }

    public int addPack(String id, int num) {
        return this.pack.add(id, num);
    }

    public int subPack(String id, int num) {
        return this.pack.sub(id, num);
    }

    public void addPack(Item it) {
        this.pack.append(it);
    }

    public void putPack(Item it) {
        this.pack.insert(it);
    }

    public void subPack(Item it) {
        this.pack.drop(it);
    }

    public Item selectPack(int ix) {
        return this.pack.select(ix);
    }

    public int firstPack(String id) {
        return this.pack.firstOf(id);
    }

    public int indexPack(Item it) {
        return this.pack.indexOf(it);
    }

    @Override // DCourt.Items.itList
    public boolean hasTrait(String attribute) {
        return this.temp.hasTrait(attribute) || this.stat.hasTrait(attribute);
    }

    public int getPower() {
        return 0 + (getAttack() * 4) + (getDefend() * 4) + getSkill() + (getGuts() * 2) + getWits() + getCharm() + scale(fight(), 12) + scale(magic(), 16) + scale(thief(), 8);
    }

    public int scale(int guild, int base) {
        int num = 0;
        for (int i = guild; i > 0; i--) {
            num += base / 2;
        }
        return num;
    }

    public void calcCombat() {
        int num = ((((getWits() * 2) + getCharm()) + 2) / 3) + gearSkill() + magicRank();
        if (num < 1) {
            num = 1;
        }
        if (hasTrait(Constants.AGILE)) {
            num += (num + 9) / 10;
        }
        setSkill(num);
        int num2 = gearAttack() + fightRank();
        if (hasTrait(Constants.STRONG)) {
            num2 += (num2 + 9) / 10;
        }
        setAttack(num2);
        int num3 = gearDefend() + thiefRank();
        if (hasTrait(Constants.STURDY)) {
            num3 += (num3 + 9) / 10;
        }
        setDefend(num3);
    }

    public int runWits() {
        int val = (getWits() * (10 + thiefRank())) / 10;
        return hasTrait(Constants.SWIFT) ? val + 30 : val;
    }

    public int bribeCharm() {
        return hasTrait(Constants.SINCERE) ? getCharm() + 30 : getCharm();
    }

    public int tradeCharm() {
        return hasTrait(Constants.TRICKY) ? getCharm() + 30 : getCharm();
    }

    public int feedCharm() {
        return hasTrait(Constants.EMPATHIC) ? getCharm() + 50 : getCharm();
    }

    public int seduceCharm() {
        return hasTrait(Constants.SEXY) ? getCharm() + 50 : getCharm();
    }

    public int getMoney() {
        return this.pack.getCount("Marks");
    }

    public int addMoney(int num) {
        return this.pack.add("Marks", num);
    }

    public int subMoney(int num) {
        return this.pack.sub("Marks", num);
    }

    public int getWounds() {
        return this.temp.getCount(Constants.WOUNDS);
    }

    public int subWounds(int num) {
        return this.temp.sub(Constants.WOUNDS, num);
    }

    public int addWounds(int num) {
        return this.temp.add(Constants.WOUNDS, num);
    }

    public int disease() {
        return this.temp.getCount("Disease");
    }

    public int ail(int num) {
        return this.temp.add("Disease", num);
    }

    public int skill() {
        int num = getSkill() - disease();
        if (num < 1) {
            return 1;
        }
        return num;
    }

    public int getLevel() {
        return this.rank.getCount(Constants.LEVEL);
    }

    public void picfile(String path) {
        if (path.length() < 1) {
            this.picfile = null;
        } else {
            this.picfile = path;
        }
    }

    public int fight() {
        return this.temp.getCount(Constants.FIGHT);
    }

    public void fight(int num) {
        this.temp.sub(Constants.FIGHT, num);
    }

    public int magic() {
        return this.temp.getCount(Constants.MAGIC);
    }

    public void magic(int num) {
        this.temp.sub(Constants.MAGIC, num);
    }

    public int thief() {
        return this.temp.getCount(Constants.THIEF);
    }

    public void thief(int num) {
        this.temp.sub(Constants.THIEF, num);
    }

    public int ieatsu() {
        return this.temp.getCount(Constants.IEATSU);
    }

    public void ieatsu(int num) {
        this.temp.sub(Constants.IEATSU, num);
    }

    public int fightRank() {
        return this.rank.getCount(Constants.FIGHT);
    }

    public int magicRank() {
        return this.rank.getCount(Constants.MAGIC);
    }

    public int thiefRank() {
        return this.rank.getCount(Constants.THIEF);
    }

    public int ieatsuRank() {
        return this.rank.getCount(Constants.IEATSU);
    }

    public int guildRank() {
        return fightRank() + magicRank() + thiefRank() + ieatsuRank();
    }

    public int guildSkill() {
        return fight() + magic() + thief() + ieatsu();
    }

    public int actions() {
        return this.temp.getCount(Constants.ACTIONS);
    }

    public boolean useAction(int num) {
        return this.temp.sub(Constants.ACTIONS, num) == num;
    }

    public boolean useAction() {
        return useAction(1);
    }

    public int packHeal() {
        return this.pack.getCount(GearTypes.APPLE) + this.pack.getCount(GearTypes.TROLL) + this.pack.getCount(GearTypes.SALVE);
    }

    public int packMagic() {
        return this.pack.getCount(GearTypes.BLIND_DUST) + this.pack.getCount(GearTypes.PANIC_DUST) + this.pack.getCount(GearTypes.BLAST_DUST);
    }

    public Portrait getPortrait() {
        String msg = "";
        if (hasTrait("Blind")) {
            msg = String.valueOf(String.valueOf(msg)).concat("*BLIND*\n");
        }
        if (hasTrait("Panic")) {
            msg = String.valueOf(String.valueOf(msg)).concat("+PANIC+\n");
        }
        getPicture().setText(msg);
        return getPicture();
    }

    public void doRefresh() {
        getTemp().sub(Constants.FATIGUE, 1);
    }

    public void doHaste() {
        getTemp().add(Constants.ACTIONS, 2);
    }

    public void doCookie() {
        getTemp().zero(Constants.FATIGUE);
    }

    public void doCure() {
        getTemp().zero("Disease");
        getTemp().clrTrait("Blind");
        getTemp().clrTrait("Panic");
    }

    public void doFood() {
        if (hasTrait(Constants.MEDIC)) {
            subWounds(3);
        } else {
            subWounds(2);
        }
    }

    public void doHeal() {
        if (hasTrait(Constants.MEDIC)) {
            subWounds(25);
        } else {
            subWounds(15);
        }
    }

    public void doRevive() {
        if (hasTrait(Constants.MEDIC)) {
            subWounds(50);
        } else {
            subWounds(30);
        }
        doCure();
    }

    public void doPanic() {
        getActions().add("Panic", 1);
        getActions().setName(Constants.SPELLS);
    }

    public void doBlind() {
        getActions().add("Blind", 1);
        getActions().setName(Constants.SPELLS);
    }

    public void doBlast() {
        getActions().add(ArmsTrait.BLAST, 1);
        getActions().setName(Constants.SPELLS);
    }
}
