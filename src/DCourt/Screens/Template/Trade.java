package DCourt.Screens.Template;

import DCourt.Items.Item;
import DCourt.Items.List.itArms;
import DCourt.Screens.Screen;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Event;

/* loaded from: DCourt.jar:DCourt/Screens/Template/Trade.class */
public abstract class Trade extends Shop {
  Button one;
  Button ten;
  Button hundred;
  Button kilo;

  public Trade(Screen from, String title) {
    super(from, title);
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Template.Indoors, DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == this.one) {
      transact(1);
    }
    if (e.target == this.ten) {
      transact(10);
    }
    if (e.target == this.hundred) {
      transact(100);
    }
    if (e.target == this.kilo) {
      transact(1000);
    }
    repaint();
    return action(e, o);
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Screen
  public void createTools() {
    createTools();
    this.one = new Button("1");
    this.one.reshape(295, 50, 40, 20);
    this.one.setFont(Tools.textF);
    this.ten = new Button("10");
    this.ten.reshape(250, 50, 40, 20);
    this.ten.setFont(Tools.textF);
    this.hundred = new Button("100");
    this.hundred.reshape(205, 50, 40, 20);
    this.hundred.setFont(Tools.textF);
    this.kilo = new Button("1K");
    this.kilo.reshape(160, 50, 40, 20);
    this.kilo.setFont(Tools.textF);
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Screen
  public void addTools() {
    addTools();
    add(this.one);
    add(this.ten);
    add(this.hundred);
    add(this.kilo);
  }

  @Override // DCourt.Screens.Template.Shop
  public boolean discardStock(Item it) {
    return it instanceof itArms;
  }

  @Override // DCourt.Screens.Template.Shop
  public boolean discardPack(Item it) {
    return it instanceof itArms;
  }

  public void transact(int num) {
    Screen.getHero().clearDump();
    if (isPack()) {
      sellItem(num);
    } else {
      buyItem(num);
    }
  }
}
