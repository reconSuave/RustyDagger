package DCourt.Screens.Areas.Mound;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Event;

import DCourt.Items.Item;
import DCourt.Items.List.itArms;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Shop;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Static.GRumors;
import DCourt.Static.GameStrings;
import DCourt.Static.GearTypes;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Mound/arGoblin.class */
public class arGoblin extends Shop implements GRumors {
  Button[] tools;
  Button transact;
  CheckboxGroup bar_shop;
  Checkbox bar;
  Checkbox shop;
  static String[] greeting = {
    null,
    "Sorry, I sneezded on it",
    "Zat's funny! tee-hee-hee!",
    "Need some shirtzez?",
    "I can loanzez money...",
    "You need zomething?",
    "I price thingzez nice",
    "Insuranze for your woezez?",
    "Whatz your problemzez?"
  };
  static int[] cost = {0, 10, 250};
  static String[] text = {"Sleep on Floor", "Buy a Drink", "Rent Smelly Cot"};
  static final String[] stock = {
    "Gobble Inn Postcard",
    "Gobble Inn T-Shirt",
    "Identify Scroll",
    GearTypes.SALVE,
    GearTypes.SELTZER,
    "Map to Warrens",
    GearTypes.INSURANCE,
    "Map to Treasury"
  };
  static final String[] monger = {
    "An old goblin witch tells you:",
    "A frothing berzerker screams:",
    "A sly pickpocket sidles up to you:",
    "Smidgeon Crumb grunts at you:",
    "A beligerent worm herder prods you:",
    "A goblin mage deigns to inform you:",
    "Slouch the barmaid mumbles to you:"
  };

  public arGoblin(Screen from) {
    super(from, "Smidgeon Crumb at the Gobble Inn");
    cost[2] = 75 + (25 * Screen.getHero().getLevel());
    if (Screen.hasTrait(Constants.HOTEL)) {
      int[] iArr = cost;
      iArr[2] = iArr[2] / 10;
    }
  }

  @Override // DCourt.Screens.Template.Indoors
  public String getFace() {
    return "Faces/Smidgeon.jpg";
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Template.Indoors
  public String getGreeting() {
    String msg = Tools.select(greeting);
    return msg == null
        ? String.valueOf(String.valueOf(Tools.getBest())).concat(" aint nuzzin")
        : msg;
  }

  @Override // DCourt.Screens.Template.Shop
  public String[] getStockList() {
    return stock;
  }

  @Override // DCourt.Screens.Template.Shop
  public void doSpecial() {}

  @Override // DCourt.Screens.Template.Shop
  public String getSpecial() {
    return null;
  }

  @Override // DCourt.Screens.Template.Shop
  public int costSpecial() {
    return 0;
  }

  @Override // DCourt.Screens.Template.Shop
  public boolean discardStock(Item it) {
    return false;
  }

  @Override // DCourt.Screens.Template.Shop
  public boolean discardPack(Item it) {
    return it instanceof itArms;
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Screen
  public void createTools() {
    super.createTools();
    getTable().reshape(160, 80, 230, 175);
    int i = text.length;
    if (i > cost.length) {
      i = cost.length;
    }
    this.tools = new Button[i];
    for (int i2 = 0; i2 < this.tools.length; i2++) {
      String msg = text[i2];
      if (cost[i2] > 0) {
        msg =
            String.valueOf(String.valueOf(msg))
                .concat(
                    String.valueOf(
                        String.valueOf(" $".concat(String.valueOf(String.valueOf(cost[i2]))))));
      }
      this.tools[i2] = new Button(msg);
      this.tools[i2].reshape(180, 75 + (i2 * 30), 200, 25);
      this.tools[i2].setFont(Tools.statusF);
    }
    this.transact = new Button("Buy");
    this.transact.reshape(200, 50, 100, 20);
    this.transact.setFont(Tools.statusF);
    this.bar_shop = new CheckboxGroup();
    this.bar = new Checkbox("Inn", this.bar_shop, true);
    this.bar.reshape(5, 240, 45, 20);
    this.bar.setFont(Tools.statusF);
    this.bar.setBackground(getBackground());
    this.shop = new Checkbox("Shop", this.bar_shop, false);
    this.shop.reshape(50, 240, 65, 20);
    this.shop.setFont(Tools.statusF);
    this.shop.setBackground(getBackground());
    updateTools();
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Screen
  public void addTools() {
    super.addTools();
    for (int i = 0; i < this.tools.length; i++) {
      add(this.tools[i]);
    }
    add(this.transact);
    add(this.bar);
    add(this.shop);
  }

  @Override // DCourt.Screens.Template.Shop
  public void updateTools() {
    boolean inBar = this.bar_shop.getCurrent() == this.bar;
    int cash = Screen.getHero().getMoney();
    updateTools();
    hideTools(inBar ? 2 : 1);
    for (int i = 0; i < this.tools.length; i++) {
      this.tools[i].enable(cash >= cost[i]);
      this.tools[i].show(inBar);
    }
    Item it = shopFind();
    if (it == null) {
      this.transact.setLabel("Buy");
      this.transact.enable(false);
    } else {
      int price = stockValue(it);
      this.transact.setLabel("Buy $".concat(String.valueOf(String.valueOf(price))));
      this.transact.enable(cash >= price);
    }
    this.transact.show(!inBar);
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Template.Indoors, DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == this.transact) {
      buyItem(1);
    } else if (e.target == getPic(0)) {
      Tools.setRegion(getHome());
    } else {
      int ix = 0;
      while (true) {
        if (ix >= this.tools.length) {
          break;
        } else if (e.target != this.tools[ix]) {
          ix++;
        } else if (Screen.getMoney() >= cost[ix]) {
          Screen.subMoney(cost[ix]);
          switch (ix) {
            case 0:
              Tools.setRegion(Screen.tryToExit(this, Constants.MOUND, cost[ix]));
              break;
            case 1:
              Tools.setRegion(rumors());
              break;
            case 2:
              Tools.setRegion(Screen.tryToExit(this, Constants.COT, cost[ix]));
              break;
          }
        }
      }
    }
    return super.action(e, o);
  }

  Screen rumors() {
    String msg;
    if (Screen.getQuests() < 1) {
      return new arNotice(
          this,
          String.valueOf(String.valueOf(GameStrings.GOSSIP))
              .concat(
                  "\nYou are so tired that you nearly pass out trying to swallow your drink.\n"));
    }
    switch (Tools.roll(Screen.getCharm())) {
      case 0:
        msg =
            String.valueOf(String.valueOf(GameStrings.GOSSIP))
                .concat(
                    "\n\tSomeone slips you a mickey. You awake with a headache in the dark and stinking alley.\n\n*** You Have Missed One Quest ***\n\n*** Your Backpack is Empty! ***\n");
        Screen.getPack().clrQueue();
        Screen.addFatigue(1);
        break;
      case 1:
      case 2:
        msg =
            String.valueOf(String.valueOf(GameStrings.GOSSIP))
                .concat(
                    "\n\tYou drink heavily and pass outfor a couple hours.\n\n*** You Have Missed One Quest ***\n");
        Screen.addFatigue(1);
        break;
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
        msg =
            String.valueOf(String.valueOf(GameStrings.GOSSIP))
                .concat("\tNoone seems interested in you...\n");
        break;
      default:
        msg =
            String.valueOf(
                    String.valueOf(
                        String.valueOf(
                                String.valueOf(
                                    String.valueOf(String.valueOf(GameStrings.GOSSIP))
                                        .concat(
                                            String.valueOf(
                                                String.valueOf(
                                                    String.valueOf(
                                                        String.valueOf(
                                                            new StringBuffer("\n\t")
                                                                .append(Tools.select(monger))
                                                                .append("\n"))))))))
                            .concat(
                                String.valueOf(
                                    String.valueOf(
                                        String.valueOf(
                                            String.valueOf(
                                                new StringBuffer("\n\t")
                                                    .append(Tools.select(GRumors.grumors))
                                                    .append("\n"))))))))
                .concat(String.valueOf(String.valueOf(Screen.getHero().gainCharm(2))));
        break;
    }
    return new arNotice(this, msg);
  }
}
