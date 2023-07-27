package DCourt.Screens.Areas.Forest;

import DCourt.Items.Item;
import DCourt.Items.List.itArms;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Smith;
import DCourt.Static.ArmsTrait;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Forest/arDwfSmith.class */
public class arDwfSmith extends Smith {
  static String[] greeting = {
    null,
    "What the hell you want?",
    "Think as you're tough?",
    "Sod off ye bugger!",
    "What now!",
    "Well, piss on me",
    "Fargin' hell!",
    "Acch! What is it?",
    "Shades! Go away!",
    "This is me finest work",
    "A dandy bit this is",
    "Here's a pretty piece",
    "This is art, laddy",
    "Mithril is crap!",
    "I spit on Mithril",
    "Elf Bows? Bah!",
    "Gak! I hate Elf Bows!",
    "REPAIR!? Smeg off!",
    "FIX IT!? Yure Nuts!",
    "POLISH!? I'm no serf!"
  };
  static final String[] stock = {
    "Steel Sword",
    "Bill Hook",
    "Sword Breaker",
    "Shakrum",
    "Recurve Bow",
    "Half Plate",
    "Full Plate",
    "Steel Buckler",
    "Roman Helm",
    "Doc Martins",
    "Mercury Sandals"
  };

  public arDwfSmith(Screen from) {
    super(from, "Gareth Shortleg's Forest Smithy");
    setShopValues(50, 20);
  }

  @Override // DCourt.Screens.Template.Indoors
  public String getFace() {
    return "Faces/Gareth.jpg";
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Template.Indoors
  public String getGreeting() {
    String msg = Tools.select(greeting);
    return msg == null ? "You're no ".concat(String.valueOf(String.valueOf(Tools.getBest()))) : msg;
  }

  @Override // DCourt.Screens.Template.Shop
  public String[] getStockList() {
    return stock;
  }

  @Override // DCourt.Screens.Template.Smith, DCourt.Screens.Template.Shop
  public int stockValue(Item it) {
    if (!(it instanceof itArms)) {
      return 0;
    }
    itArms arm = (itArms) it;
    int val = arm.stockValue();
    if (arm.hasTrait(ArmsTrait.LEFT)) {
      val = (int) (((float) val) * 1.3f);
    }
    if (val < 2) {
      return 2;
    }
    return val;
  }

  @Override // DCourt.Screens.Template.Shop
  public void doSpecial() {
    doIdentify();
  }

  @Override // DCourt.Screens.Template.Shop
  public String getSpecial() {
    return "Identify";
  }

  @Override // DCourt.Screens.Template.Shop
  public int costSpecial() {
    itArms arm = (itArms) shopFind();
    return (arm == null || !arm.hasTrait(ArmsTrait.SECRET)) ? 0 : 60;
  }
}
