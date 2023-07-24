package DCourt.Screens.Areas.Hills;

import DCourt.Control.GearTable;
import DCourt.Items.itList;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Trade;
import DCourt.Screens.Utility.arPeer;
import DCourt.Static.GearTypes;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Hills/arGemShop.class */
public class arGemShop extends Trade implements GearTypes {
  Button peer;
  static String[] greeting = {
    null,
    "Unhh...",
    "Hunh...",
    "Hargh..",
    "Eh?",
    "Enh..",
    "Hmm?",
    "Hrmm...",
    "Heh, Heh..",
    "Skrechk! Phtoo!"
  };
  static final String[] stock = {"Quartz", "Opal", "Garnet", "Emerald", "Ruby", "Turquoise"};

  public arGemShop(Screen from) {
    super(from, "Gakthrak Cunning's Priceless Gems");
    setShopValues(70, 30);
  }

  @Override // DCourt.Screens.Template.Indoors
  public String getFace() {
    return "Faces/Gakthrak.jpg";
  }

  @Override // DCourt.Screens.Template.Shop, DCourt.Screens.Template.Indoors
  public String getGreeting() {
    String msg = Tools.select(greeting);
    return msg == null ? String.valueOf(String.valueOf(Tools.getBest())).concat(" heh, heh") : msg;
  }

  @Override // DCourt.Screens.Template.Trade, DCourt.Screens.Template.Shop, DCourt.Screens.Screen
  public void createTools() {
    createTools();
    this.peer = new Button("Peer $250");
    this.peer.reshape(10, 242, 90, 20);
    this.peer.setFont(Tools.textF);
  }

  @Override // DCourt.Screens.Template.Trade, DCourt.Screens.Template.Shop, DCourt.Screens.Screen
  public void addTools() {
    addTools();
    add(this.peer);
  }

  @Override // DCourt.Screens.Template.Shop
  public String[] getStockList() {
    return stock;
  }

  @Override // DCourt.Screens.Template.Shop
  public itList getBuyList() {
    return GearTable.findList("Buy", 4);
  }

  @Override // DCourt.Screens.Template.Shop
  public String getSpecial() {
    return "Peer";
  }

  @Override // DCourt.Screens.Template.Shop
  public void doSpecial() {
    Tools.setRegion(new arPeer(this, 1, null));
  }

  @Override // DCourt.Screens.Template.Shop
  public int costSpecial() {
    return 250;
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    localPaint(g);
    this.peer.enable(Screen.getHero().getMoney() >= 250);
  }
}
