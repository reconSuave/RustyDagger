package DCourt.Screens.Template;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Event;

import DCourt.Components.FTextList;
import DCourt.Components.Portrait;
import DCourt.Control.GearTable;
import DCourt.Items.Item;
import DCourt.Items.itList;
import DCourt.Items.List.itArms;
import DCourt.Items.List.itHero;
import DCourt.Items.Token.itCount;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arDetail;
import DCourt.Static.Constants;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Template/Shop.class */
public abstract class Shop extends Indoors {
  static final int STOCK = 0;
  static final int PACK = 1;
  public static final Color TABLE_COLOR = new Color(64, 255, 192);
  int RESALE;
  int BASE;
  int mode;
  itList sellList;
  itList buyList;
  itList packList;
  Checkbox[] box;
  FTextList table;
  CheckboxGroup group;
  Button info;
  Button special;
  int heroCharm = Screen.getHero().getCharm();
  Item lastSelect = null;

  @Override // DCourt.Screens.Template.Indoors
  public abstract String getGreeting();

  protected abstract String[] getStockList();

  protected abstract boolean discardStock(Item item);

  protected abstract boolean discardPack(Item item);

  public Shop(Screen from, String name) {
    super(from, name);
    this.mode = 0;
    this.mode = 0;
  }

  public void setShopValues(int rs, int bs) {
    this.RESALE = rs;
    this.BASE = bs;
  }

  @Override // DCourt.Screens.Screen
  public void init() {
    super.init();
    shopList(this.lastSelect);
  }

  public void fixPicture(String face) {
    addPic(new Portrait("Exit.jpg", 320, 10, 64, 32));
    addPic(new Portrait(face, getGreeting(), 10, 30, 144, 192));
  }

  @Override // DCourt.Screens.Template.Indoors, DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == getPic(0)) {
      Tools.setRegion(getHome());
    }
    if (e.target == this.box[0]) {
      setMode(0);
    }
    if (e.target == this.box[1]) {
      setMode(1);
    }
    if (e.target == this.info) {
      shopInfo();
    }
    if (e.target == this.table) {
      this.lastSelect = shopFind();
    }
    updateTools();
    repaint();
    return super.action(e, o);
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    this.table = new FTextList();
    this.table.reshape(162, 75, 230, 186);
    this.table.setFont(Tools.boldF);
    this.info = new Button("Info");
    this.info.reshape(110, 242, 40, 20);
    this.info.setFont(Tools.textF);
    this.group = new CheckboxGroup();
    this.box = new Checkbox[2];
    this.sellList = getSellList();
    this.buyList = getBuyList();
    this.packList = Screen.getPack();
    this.box[0] = new Checkbox("Buy", this.group, isStock());
    this.box[0].reshape(165, 28, 60, 20);
    this.box[0].setBackground(getBackground());
    this.box[1] = new Checkbox("Sell", this.group, isPack());
    this.box[1].reshape(240, 28, 60, 20);
    this.box[1].setBackground(getBackground());
    this.special = null;
    if (getSpecial() != null) {
      this.special =
          new Button(
              String.valueOf(String.valueOf(getSpecial()))
                  .concat(String.valueOf(String.valueOf(0))));
      this.special.setFont(Tools.textF);
      this.special.reshape(10, 242, 90, 20);
    }
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    add(this.table);
    add(this.box[0]);
    add(this.box[1]);
    add(this.info);
    if (this.special != null) {
      add(this.special);
    }
  }

  public void hideTools(int which) {
    this.table.show(which != 2);
    this.box[0].show(which == 0);
    this.box[1].show(which == 0);
    this.info.show(which != 2);
    if (this.special != null) {
      this.special.show(which != 2);
    }
  }

  public void updateTools() {
    if (this.special != null) {
      int cost = costSpecial();
      this.special.setLabel(
          String.valueOf(
              String.valueOf(
                  new StringBuffer(String.valueOf(String.valueOf(getSpecial())))
                      .append(" $")
                      .append(cost))));
      this.special.enable(cost != 0 && Screen.getHero().getMoney() >= cost);
    }
  }

  public void setMode(int val) {
    this.mode = val;
    this.group.setCurrent(this.box[this.mode]);
    shopList(this.lastSelect);
  }

  public int getMode() {
    return this.mode;
  }

  public boolean isStock() {
    return this.mode == 0;
  }

  public boolean isPack() {
    return this.mode == 1;
  }

  public itList getModeList() {
    return isStock() ? this.sellList : this.packList;
  }

  public String getSpecial() {
    return null;
  }

  public void doSpecial() {}

  public int costSpecial() {
    return 0;
  }

  public void shopList(String id) {
    itList list = getModeList();
    if (list != null) {
      shopList(list.find(Tools.truncate(id)));
    }
  }

  public void shopList(Item find) {
    itList list = getModeList();
    if (list != null) {
      this.table.clear();
      int px = 0;
      int pick = -1;
      for (int ix = 0; ix < list.getCount(); ix++) {
        Item it = list.select(ix);
        if (!discardItem(it)) {
          this.table.addItem(shopName(it));
          if (it == find) {
            pick = px;
          }
          px++;
        }
      }
      this.table.setSelect(pick);
      this.lastSelect = find;
    }
  }

  public Item shopFind() {
    int pick = this.table.getSelect();
    itList list = getModeList();
    if (pick < 0) {
      return null;
    }
    for (int ix = 0; ix < list.getCount(); ix++) {
      Item it = list.select(ix);
      if (!discardItem(it)) {
        pick--;
        if (pick < 0) {
          return it;
        }
      }
    }
    return null;
  }

  void shopInfo() {
    Item it = shopFind();
    if (it != null) {
      Tools.setRegion(new arDetail(this, it));
    }
  }

  public String shopName(Item it) {
    String msg =
        it instanceof itArms
            ? it.toShow()
            : String.valueOf(
                String.valueOf(
                    new StringBuffer(String.valueOf(String.valueOf(it.getName())))
                        .append("(")
                        .append(Screen.packCount(it))
                        .append(")")));
    return isPack()
        ? String.valueOf(String.valueOf(msg))
            .concat(
                String.valueOf(
                    String.valueOf(" $".concat(String.valueOf(String.valueOf(packValue(it)))))))
        : String.valueOf(String.valueOf(msg))
            .concat(
                String.valueOf(
                    String.valueOf(" $".concat(String.valueOf(String.valueOf(stockValue(it)))))));
  }

  public int packValue(Item it) {
    int cost = stockValue(it);
    int cost2 =
        Screen.hasTrait(Constants.MERCHANT)
            ? (cost * this.RESALE) / 95
            : (cost * this.RESALE) / 100;
    return cost2 - ((cost2 * this.BASE) / ((2 * this.BASE) + this.heroCharm));
  }

  public int stockValue(Item it) {
    return GearTable.getCost(it);
  }

  protected itList getSellList() {
    return createSellList(getStockList());
  }

  protected itList getBuyList() {
    return null;
  }

  public FTextList getTable() {
    return this.table;
  }

  boolean discardItem(Item it) {
    if (isStock()) {
      return discardStock(it);
    }
    if (!it.isMatch("Marks") && stockValue(it) >= 1 && !discardPack(it)) {
      return this.sellList.find(it.getName()) == null
          && this.buyList != null
          && this.buyList.find(it.getName()) == null;
    }
    return true;
  }

  protected itList createSellList(String[] stock) {
    if (stock == null) {
      return null;
    }
    itList result = new itList("Sell");
    for (int ix = 0; ix < stock.length; ix++) {
      if (GearTable.find(stock[ix])) {
        result.append(GearTable.shopItem(stock[ix]));
      }
    }
    return result;
  }

  public void buyItem(int num) {
    itHero h = Screen.getHero();
    Item it = shopFind();
    if (it != null) {
      int cost = stockValue(it);
      int val = h.getMoney() / cost;
      if (num > val) {
        num = val;
      }
      int val2 = h.holdMax() - h.heroHas(it);
      if (num > val2) {
        num = val2;
      }
      if (num != 0) {
        h.subMoney(cost * num);
        h.addPack(it.getName(), num);
        int ix = this.table.getSelect();
        if (num == val2) {
          this.table.delItem(ix);
          this.table.setSelect(-1);
          return;
        }
        this.table.setItem(shopName(it), ix);
      }
    }
  }

  public void sellItem(int num) {
    int num2;
    itHero h = Screen.getHero();
    Item it = shopFind();
    if (it != null) {
      int cost = packValue(it);
      int max = it.getCount();
      if (it instanceof itCount) {
        num2 = this.packList.sub(it.getName(), num);
      } else {
        num2 = 1;
        this.packList.drop(it);
      }
      h.addMoney(cost * num2);
      int ix = this.table.getSelect();
      if (num2 == max) {
        this.table.delItem(ix);
      } else {
        this.table.setItem(shopName(it), ix);
      }
    }
  }
}
