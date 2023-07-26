package DCourt.Screens.Areas.Hills;

import DCourt.Control.GearTable;
import DCourt.Items.Item;
import DCourt.Items.itList;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Trade;
import DCourt.Static.GearTypes;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Hills/arMagicShop.class */
public class arMagicShop extends Trade implements GearTypes {
  static String[] greeting = {
    null,
    "Greetings Master",
    "You Wish is My Command",
    "How May I Serve Thee?",
    "Do Not Anger Me",
    "Seek and Ye Shall Find",
    "Try Blinding Trolls",
    "Seltzer Cleans Dust",
    "Never Ask A Girls Age"
  };
  static final String[] stock = {
    "Identify Scroll",
    "Glow Scroll",
    GearTypes.SALVE,
    GearTypes.SELTZER,
    GearTypes.PANIC_DUST,
    "Gold Apple",
    GearTypes.BLIND_DUST,
    "Bless Scroll",
    "Luck Scroll",
    "Enchant Scroll",
    "Flame Scroll",
    "Faceless Potion"
  };

  public arMagicShop(Screen from) {
    super(from, "Djinni's Ethereal Magic Shop");
    setShopValues(55, 22);
  }

  @Override // DCourt.Screens.Template.Indoors
  public String getFace() {
    return "Faces/Djinni.jpg";
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Template.Indoors
  public String getGreeting() {
    String msg = Tools.select(greeting);
    return msg == null
        ? String.valueOf(String.valueOf(Tools.getBest())).concat(" Is Dreeeamy*")
        : msg;
  }

  @Override // DCourt.Screens.Template.Shop
  public String[] getStockList() {
    return stock;
  }

  @Override // DCourt.Screens.Template.Shop
  public itList getBuyList() {
    itList list = GearTable.findList("Buy", 6);
    list.merge(GearTable.findList("", 7));
    return list;
  }

  @Override // DCourt.Screens.Template.Shop
  public String getSpecial() {
    return null;
  }

  @Override // DCourt.Screens.Template.Shop
  public void doSpecial() {}

  @Override // DCourt.Screens.Template.Shop
  public int costSpecial() {
    return 0;
  }

  @Override // DCourt.Screens.Template.Shop
  public int stockValue(Item it) {
    return stockValue(it) * 2;
  }
}
