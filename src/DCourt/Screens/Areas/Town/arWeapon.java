package DCourt.Screens.Areas.Town;

import DCourt.Items.Item;
import DCourt.Items.List.itArms;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Smith;
import DCourt.Static.ArmsTrait;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Town/arWeapon.class */
public class arWeapon extends Smith {
    static String[] greeting = {null, "Welcome to my Shop", "Greetings Friend", "Buy something sharp", "You think Aileen's cute?", "Elf Bows are fast", "Gonna sell something?", "See you at the Tavern", "How are you today?", "My joints are aching", "Want to arm wrestle?"};
    static final String[] stock = {"Knife", "Hatchet", "Short Sword", "Long Sword", "Spear", "Broad Sword", "Battle Axe", "Pike", "Sling", "Short Bow", "Long Bow", "Spike Helm", "Main Gauche"};

    public arWeapon(Screen from) {
        super(from, "Bill Smith's Weapon Shoppe");
        setShopValues(60, 10);
    }

    @Override // DCourt.Screens.Template.Indoors
    public String getFace() {
        return "Faces/Bill.jpg";
    }

    @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Template.Indoors
    public String getGreeting() {
        String msg = Tools.select(greeting);
        return msg == null ? String.valueOf(String.valueOf(Tools.getBest())).concat(" is strong...") : msg;
    }

    @Override // DCourt.Screens.Template.Shop
    public String[] getStockList() {
        return stock;
    }

    @Override // DCourt.Screens.Template.Shop
    public String getSpecial() {
        return "Identify";
    }

    @Override // DCourt.Screens.Template.Shop
    public void doSpecial() {
        doIdentify();
    }

    @Override // DCourt.Screens.Template.Shop
    public int costSpecial() {
        itArms a = (itArms) shopFind();
        return (a == null || !a.hasTrait(ArmsTrait.SECRET)) ? 0 : 40;
    }

    @Override // DCourt.Screens.Template.Smith, DCourt.Screens.Template.Shop
    public int stockValue(Item it) {
        if (!(it instanceof itArms)) {
            return 0;
        }
        itArms arm = (itArms) it;
        int val = arm.stockValue();
        if (arm.hasTrait(ArmsTrait.RIGHT)) {
            val = (int) (((float) val) * 1.3f);
        }
        if (val < 2) {
            return 2;
        }
        return val;
    }
}
