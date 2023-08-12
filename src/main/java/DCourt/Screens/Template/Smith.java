package DCourt.Screens.Template;

import DCourt.Items.Item;
import DCourt.Items.List.itArms;
import DCourt.Items.List.itHero;
import DCourt.Screens.Screen;
import DCourt.Static.ArmsTrait;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Event;

/* loaded from: DCourt.jar:DCourt/Screens/Template/Smith.class */
public abstract class Smith extends Shop {
  Button transact;

  @Override // DCourt.Screens.Template.Shop
  public abstract int stockValue(Item item);

  public Smith(Screen from, String name) {
    super(from, name);
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Screen
  public void init() {
    super.init();
    updateTools();
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Template.Indoors, DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == this.transact) {
      transact();
    }
    if (e.target == this.special) {
      doSpecial();
    }
    return super.action(e, o);
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Screen
  public void createTools() {
    super.createTools();
    this.transact = new Button("Buy");
    this.transact.reshape(180, 50, 100, 20);
    this.transact.setFont(Tools.textF);
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Screen
  public void addTools() {
    super.addTools();
    add(this.transact);
  }

  @Override // DCourt.Screens.Template.Shop
  public void updateTools() {
    super.updateTools();
    Item it = shopFind();
    if (isPack()) {
      this.transact.setLabel("Sell $".concat(String.valueOf(String.valueOf(packValue(it)))));
      this.transact.enable(it != null);
      return;
    }
    int cost = stockValue(it);
    this.transact.setLabel("Buy $".concat(String.valueOf(String.valueOf(cost))));
    this.transact.enable(it != null && Screen.getHero().getMoney() >= cost);
  }

  @Override // DCourt.Screens.Template.Shop
  protected boolean discardStock(Item it) {
    return !(it instanceof itArms);
  }

  @Override // DCourt.Screens.Template.Shop
  protected boolean discardPack(Item it) {
    return !(it instanceof itArms);
  }

  void transact() {
    Item it = shopFind();
    if (it != null && (it instanceof itArms)) {
      if (isPack()) {
        sellWeapon((itArms) it);
      } else {
        buyWeapon((itArms) it);
      }
    }
  }

  void buyWeapon(itArms it) {
    int cost = stockValue(it);
    if (cost <= Screen.getMoney()) {
      Screen.subMoney(cost);
      Screen.getPack().insert(it.copy());
      /* don't switch to Sell list after buy
      setMode(1);
      */
      shopList(it);
    }
  }

  void sellWeapon(itArms it) {
    int cost = packValue(it);
    Screen.subPack(it);
    Screen.addMoney(cost);
    this.table.delItem(this.table.getSelect());
  }

  public void doIdentify() {
    int cost;
    itHero h = Screen.getHero();
    itArms a = (itArms) shopFind();
    if (a != null && a.hasTrait(ArmsTrait.SECRET) && h.getMoney() >= (cost = costSpecial())) {
      h.subMoney(cost);
      a.clrTrait(ArmsTrait.SECRET);
      getTable().setItem(shopName(a), getTable().getSelect());
    }
  }
}
