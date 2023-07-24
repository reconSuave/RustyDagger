package DCourt.Items.List;

import DCourt.Items.Item;
import DCourt.Items.Token.itCount;
import DCourt.Items.itList;
import DCourt.Static.ArmsTrait;
import DCourt.Tools.Buffer;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Items/List/itArms.class */
public class itArms extends itList implements ArmsTrait {
    private itCount aval;
    private itCount dval;
    private itCount sval;
    static final int MEGATWEAK = 2048;
    //static final int MEGATWEAK = MEGATWEAK;
    static final int DECAY_FACTOR = 12;

    public itArms() {
        this(null, 0, 0, 0);
    }

    public itArms(String id) {
        this(id, 0, 0, 0);
    }

    public itArms(String _id, int atk, int def, int skl) {
        super(_id);
        /*
         */
        setVals(atk, def, skl);
    }

    public itArms(itArms it) {
        super(it);
        setVals(it.getAttack(), it.getDefend(), it.getSkill());
    }

    @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public Item copy() {
        return new itArms(this);
    }

    @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public String getIcon() {
        return "itArms";
    }

    @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public String toString() {
        return toString(0);
    }

    @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public String toString(int depth) {
        return String.valueOf(String.valueOf(String.valueOf(String.valueOf(toStringHead(depth))).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("|").append(getAttack()).append("|").append(getDefend()).append("|").append(getSkill())))))))).concat(String.valueOf(String.valueOf(listBody(depth))));
    }

    private void setVals(int a, int d, int s) {
        this.aval = new itCount("a", a);
        this.dval = new itCount("d", d);
        this.sval = new itCount("s", s);
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

    public int getAttack() {
        return this.aval.getCount();
    }

    public int getDefend() {
        return this.dval.getCount();
    }

    public int getSkill() {
        return this.sval.getCount();
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

    public void subAttack(int num) {
        this.aval.adds(-num);
    }

    public void subDefend(int num) {
        this.dval.adds(-num);
    }

    public void subSkill(int num) {
        this.sval.adds(-num);
    }

    public static Item factory(Buffer buf) {
        if (!buf.begin() || !buf.match("itArms") || !buf.split()) {
            return null;
        }
        itArms what = new itArms(buf.token());
        if (buf.split()) {
            what.setAttack(buf.num());
        }
        if (buf.split()) {
            what.setDefend(buf.num());
        }
        if (buf.split()) {
            what.setSkill(buf.num());
        }
        what.loadBody(buf);
        return what;
    }

    @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public String toLoot() {
        return toShow();
    }

    @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public String toShow() {
        String msg = getName();
        if (hasTrait(ArmsTrait.SECRET)) {
            return String.valueOf(String.valueOf(msg)).concat("[?]");
        }
        String msg2 = String.valueOf(String.valueOf(msg)).concat("[");
        if (fullAttack() > 0) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat("+");
        }
        if (fullAttack() != 0) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(fullAttack())).concat("a"))));
        }
        if (fullDefend() > 0) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat("+");
        }
        if (fullDefend() != 0) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(fullDefend())).concat("d"))));
        }
        if (fullSkill() > 0) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat("+");
        }
        if (fullSkill() != 0) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(fullSkill())).concat("s"))));
        }
        if (hasTrait(ArmsTrait.DECAY)) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat("@");
        }
        if (hasTrait(ArmsTrait.CURSE)) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat("*");
        }
        return String.valueOf(String.valueOf(msg2)).concat("]");
    }

    @Override // DCourt.Items.itList
    public int fullAttack() {
        int num = getAttack();
        if (hasTrait(ArmsTrait.RIGHT) && hasTrait(ArmsTrait.FLAME)) {
            num += 8;
        }
        return num + ((getEnchant() + 9) / 10);
    }

    @Override // DCourt.Items.itList
    public int fullDefend() {
        int num = getDefend();
        if (hasTrait(ArmsTrait.BLESS)) {
            num++;
        }
        return num + ((getEnchant() + 4) / 10);
    }

    @Override // DCourt.Items.itList
    public int fullSkill() {
        int num = getSkill();
        if (hasTrait(ArmsTrait.RIGHT) && hasTrait(ArmsTrait.LUCKY)) {
            num += 12;
        }
        if (hasTrait(ArmsTrait.GLOWS)) {
            num += 2;
        }
        return num + getEnchant();
    }

    public int getPower() {
        int power = (getAttack() * 3) + (getDefend() * 2) + getSkill();
        if (power < 1) {
            return 1;
        }
        return power;
    }

    public int getEnchant() {
        return getCount(ArmsTrait.ENCHANT);
    }

    public void incEnchant() {
        add(ArmsTrait.ENCHANT, 1);
    }

    void setEnchant(int val) {
        fix(ArmsTrait.ENCHANT, val);
    }

    public boolean isBright() {
        return hasTrait(ArmsTrait.GLOWS) || hasTrait(ArmsTrait.FLAME);
    }

    public boolean isCursed() {
        return hasTrait(ArmsTrait.CURSE) || hasTrait(ArmsTrait.CURSED);
    }

    public void revealCurse() {
        if (hasTrait(ArmsTrait.CURSED)) {
            clrTrait(ArmsTrait.CURSED);
            fixTrait(ArmsTrait.CURSE);
        }
    }

    public boolean wearable() {
        for (int ix = 0; ix < ArmsTrait.END_WEAR_TRAIT; ix++) {
            if (hasTrait(ix)) {
                return true;
            }
        }
        return false;
    }

    boolean hasTrait(int num) {
        return hasTrait(ArmsTrait.traitLabel[num]);
    }

    void fixTrait(int num) {
        fixTrait(ArmsTrait.traitLabel[num]);
    }

    void clrTrait(int num) {
        clrTrait(ArmsTrait.traitLabel[num]);
    }

    @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
    public boolean decay(int rate) {
        clrTrait(ArmsTrait.DECAY);
        if (rate < 2) {
            rate = 2;
        }
        if (Tools.roll(rate) > 0) {
            return false;
        }
        fixTrait(ArmsTrait.DECAY);
        int num = getAttack();
        subAttack(num <= 1 ? 1 - (num / 12) : 1 + (num / 12));
        int num2 = getDefend();
        subDefend(num2 <= 1 ? 1 - (num2 / 12) : 1 + (num2 / 12));
        int num3 = getSkill();
        subSkill(num3 <= 1 ? 1 - (num3 / 12) : 1 + (num3 / 12));
        if (Tools.roll(12) != 0) {
            return true;
        }
        clrTrait(ArmsTrait.VISIBLE_TRAIT + Tools.roll(ArmsTrait.ENCHANT_TRAIT - ArmsTrait.VISIBLE_TRAIT));
        sub(ArmsTrait.ENCHANT, (getEnchant() + 4) / 5);
        return true;
    }

    public void tweak() {
        int sum = getPower();
        int value = 7 + Tools.twice(4) + Tools.skew(50);
        int num = getAttack();
        setAttack(num < 0 ? (num * 10) / value : (num * value) / 10);
        int num2 = getDefend();
        setDefend(num2 < 0 ? (num2 * 10) / value : (num2 * value) / 10);
        int num3 = getSkill();
        setSkill(num3 < 0 ? (num3 * 10) / value : (num3 * value) / 10);
        while (true) {
            int value2 = Tools.roll(MEGATWEAK);
            if (value2 >= sum) {
                break;
            }
            sum -= value2;
            int trait = ArmsTrait.VISIBLE_TRAIT + Tools.roll(ArmsTrait.ENCHANT_TRAIT - ArmsTrait.VISIBLE_TRAIT);
            if ((value2 & 1) == 0) {
                clrTrait(trait);
            } else {
                fixTrait(trait);
            }
        }
        if (isCursed()) {
            clrTrait(ArmsTrait.CURSE);
            fixTrait(ArmsTrait.CURSED);
        }
        if (isCursed() || stockValue() >= 70) {
            fixTrait(ArmsTrait.SECRET);
        }
    }

    public int stockValue() {
        if (hasTrait(ArmsTrait.SECRET) || hasTrait(ArmsTrait.CURSE)) {
            return 2;
        }
        int num = getAttack() + getDefend();
        int value = 0 + ((num > 0 ? 1 : -1) * num * num * 5);
        int num2 = getSkill();
        int value2 = (value + ((((num2 > 0 ? 1 : -1) * num2) * num2) * 2)) / 2;
        for (int ix = ArmsTrait.VALUED_TRAIT; ix < ArmsTrait.traitLabel.length; ix++) {
            Item it = find(ArmsTrait.traitLabel[ix]);
            if (it != null) {
                value2 += it.getCount() * ArmsTrait.traitValue[ix];
            }
        }
        return value2;
    }
}
