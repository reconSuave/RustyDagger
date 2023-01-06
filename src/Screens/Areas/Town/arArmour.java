package DCourt.Screens.Areas.Town;

import DCourt.Control.ArmsTable;
import DCourt.Items.Item;
import DCourt.Items.List.itArms;
import DCourt.Items.List.itHero;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Smith;
import DCourt.Static.ArmsTrait;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Town/arArmour.class */
public class arArmour extends Smith {
    static final int MAXFIX_POWER = MAXFIX_POWER;
    static final int MAXFIX_POWER = MAXFIX_POWER;
    static String[] greeting = {null, "What's your sign?", "Hiya Sonny!", "Watcha Got?", "Hey there, sexy", "Rub me feet, willya?", "Armour is good...", "C'mon sexy, smile", "Cover ever'thing", "Back fer more?", "Need some shoes?"};
    public static final String[] stock = {"Clothes", "Leather Jacket", "Brigandine", "Chain Suit", "Scale Suit", "Buckler", "Targe", "Shield", "Spike Shield", "Sandals", "Shoes", "Boots", "Leather Cap", "Pot Helm", "Chain Coif"};

    public arArmour(Screen from) {
        super(from, "Aileen Suitor's Armour Shoppe");
        setShopValues(50, 15);
    }

    @Override // DCourt.Screens.Template.Indoors
    public String getFace() {
        return "Faces/Aileen.jpg";
    }

    @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Template.Indoors
    public String getGreeting() {
        String val = Tools.select(greeting);
        return val == null ? String.valueOf(String.valueOf(Tools.getBest())).concat(" is my hero.") : val;
    }

    @Override // DCourt.Screens.Template.Shop
    public String[] getStockList() {
        return stock;
    }

    @Override // DCourt.Screens.Template.Shop
    public String getSpecial() {
        return "Polish";
    }

    @Override // DCourt.Screens.Template.Shop
    public void doSpecial() {
        int cost;
        itHero h = Screen.getHero();
        itArms arm = (itArms) shopFind();
        if (arm != null && !arm.hasTrait(ArmsTrait.SECRET) && h.getMoney() >= (cost = costSpecial())) {
            h.subMoney(cost);
            arm.clrTrait(ArmsTrait.DECAY);
            if (cost >= 2) {
                itArms base = ArmsTable.get(arm);
                if (base != null) {
                    if (base.getAttack() > arm.getAttack()) {
                        arm.setAttack(base.getAttack());
                    }
                    if (base.getDefend() > arm.getDefend()) {
                        arm.setDefend(base.getDefend());
                    }
                    if (base.getSkill() > arm.getSkill()) {
                        arm.setSkill(base.getSkill());
                    }
                }
                getTable().setItem(shopName(arm), getTable().getSelect());
            }
        }
    }

    @Override // DCourt.Screens.Template.Shop
    public int costSpecial() {
        itArms arm = (itArms) shopFind();
        if (arm == null || arm.hasTrait(ArmsTrait.SECRET)) {
            return 0;
        }
        itArms base = ArmsTable.get(arm);
        int cost = arm.hasTrait(ArmsTrait.DECAY) ? 1 : 0;
        if (base == null || base.getPower() >= MAXFIX_POWER) {
            return cost;
        }
        int num = base.getAttack() - arm.getAttack();
        if (num > 0) {
            cost += num * num * 5;
        }
        int num2 = base.getDefend() - arm.getDefend();
        if (num2 > 0) {
            cost += num2 * num2 * 4;
        }
        int num3 = base.getSkill() - arm.getSkill();
        if (num3 > 0) {
            cost += num3 * num3 * 2;
        }
        return cost;
    }

    @Override // DCourt.Screens.Template.Smith, DCourt.Screens.Template.Shop
    public int stockValue(Item it) {
        if (!(it instanceof itArms)) {
            return 0;
        }
        itArms arm = (itArms) it;
        int val = arm.stockValue();
        if (arm.hasTrait(ArmsTrait.BODY)) {
            val = (int) (((float) val) * 1.3f);
        }
        if (val < 2) {
            return 2;
        }
        return val;
    }
}
