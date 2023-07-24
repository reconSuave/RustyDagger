package DCourt.Screens.Areas.Town;

import DCourt.Screens.Screen;
import DCourt.Screens.Template.Trade;
import DCourt.Static.GearTypes;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Town/arTrader.class */
public class arTrader extends Trade implements GearTypes {
    static String[] greeting = {null, "Hello Again", "How are you?", "Nice weather today", "How's the family?", "You look healthy", "Need something special?", "Aileen scares me", "The tavern is noisy", "Need some help?", "Want a kiss?"};
    static final String[] stock = {GearTypes.FOOD, GearTypes.FISH, "Torch", "Rope", "Pen & Paper", "Sleeping Bag", "Cooking Gear", "Camp Tent", "Identify Scroll", GearTypes.SALVE, GearTypes.SELTZER, GearTypes.PANIC_DUST, GearTypes.BLIND_DUST, GearTypes.PANIC_DUST, GearTypes.BLAST_DUST, "Castle Permit"};

    public arTrader(Screen from) {
        super(from, "Sally Trader's Curious Goods");
        setShopValues(80, 15);
    }

    @Override // DCourt.Screens.Template.Indoors
    public String getFace() {
        return "Faces/Sally.jpg";
    }

    @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Template.Indoors
    public String getGreeting() {
        String msg = Tools.select(greeting);
        return msg == null ? "I like ".concat(String.valueOf(String.valueOf(Tools.getBest()))) : msg;
    }

    @Override // DCourt.Screens.Template.Shop
    public String[] getStockList() {
        return stock;
    }
}
