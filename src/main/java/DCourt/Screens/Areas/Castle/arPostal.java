package DCourt.Screens.Areas.Castle;

import DCourt.Components.FTextList;
import DCourt.Items.Item;
import DCourt.Items.List.itAgent;
import DCourt.Items.List.itHero;
import DCourt.Items.itList;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Indoors;
import DCourt.Screens.Utility.arNotice;
import DCourt.Screens.Utility.arPackage;
import DCourt.Tools.Buffer;
import DCourt.Tools.Loader;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.util.Enumeration;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Castle/arPostal.class */
public class arPostal extends Indoors {
  Button take;
  Button send;
  FTextList postbox;
  itList mail = null;
  static String[] greeting = {
    null,
    "In a minute..",
    "Okay, alright already.",
    "Fill in this form.",
    "This form is wrong",
    "I'm on my break",
    "Geez, again?",
    "*Sigh* Oh I suppose.",
    "Right now? Yeah, yeah."
  };

  public arPostal(Screen from) {
    super(from, "Sloeth Dreyfus Postal Express");
    setBackground(new Color(128, 255, 128));
    setForeground(new Color(0, 128, 0));
  }

  @Override // DCourt.Screens.Template.Indoors
  public String getFace() {
    return "Faces/Sloeth.jpg";
  }

  @Override // DCourt.Screens.Template.Indoors
  public String getGreeting() {
    String msg = Tools.select(greeting);
    return msg == null ? String.valueOf(String.valueOf(Tools.getBest())).concat("? Oh yeah.") : msg;
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    super.localPaint(g);
    updateTools(Tools.getHero());
  }

  @Override // DCourt.Screens.Template.Indoors, DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == this.take) {
      Tools.setRegion(takePackage());
    }
    if (e.target == this.send) {
      Tools.setRegion(new arPackage(this));
    }
    if (e.target == getPic(0)) {
      Tools.setRegion(getHome());
    }
    repaint();
    return super.action(e, o);
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    itHero h = Screen.getHero();
    this.take = new Button("Take Mail $100");
    this.take.reshape(160, 40, 150, 20);
    this.take.setFont(Tools.textF);
    this.send = new Button("Send Mail <x$100>");
    this.send.reshape(10, 240, 140, 20);
    this.send.setFont(Tools.textF);
    this.postbox = new FTextList();
    this.postbox.reshape(160, 70, 230, 180);
    this.postbox.setFont(Tools.textF);
    if (this.mail == null) {
      loadMailList();
    }
    if (this.mail != null) {
      Enumeration e = this.mail.elements();
      while (e.hasMoreElements()) {
        this.postbox.addItem(((Item) e.nextElement()).getName());
      }
    }
    this.postbox.setSelect(-1);
    updateTools(h);
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    add(this.take);
    add(this.send);
    add(this.postbox);
  }

  public void updateTools(itAgent h) {
    this.take.enable(this.postbox.getSelect() >= 0 && h.getMoney() >= 100);
  }

  void loadMailList() {
    this.mail =
        (itList)
            Loader.cgiItem(
                Loader.LISTMAIL,
                String.valueOf(
                    String.valueOf(
                        new StringBuffer(String.valueOf(String.valueOf(Screen.getHero().getName())))
                            .append("|")
                            .append(Screen.getPlayer().getSessionID()))));
  }

  Screen takePackage() {
    itHero h = Screen.getHero();
    int index = this.postbox.getSelect();
    if (index < 0) {
      return null;
    }
    Buffer buf =
        Loader.cgiBuffer(
            Loader.TAKEMAIL,
            String.valueOf(
                String.valueOf(
                    new StringBuffer(String.valueOf(String.valueOf(h.getName())))
                        .append("|")
                        .append(Screen.getSessionID())
                        .append("|")
                        .append(index))));
    String msg = this.postbox.getItem(index);
    this.postbox.delItem(index);
    if (buf == null || buf.isEmpty() || buf.isError()) {
      return new arNotice(
          this,
          String.valueOf(
              String.valueOf(
                  new StringBuffer("A transmission error has occurred:\n")
                      .append(buf == null ? "" : buf.peek())
                      .append("\n")
                      .append("Sorry About That."))));
    }
    String msg2 =
        String.valueOf(
            String.valueOf(
                new StringBuffer(
                        "\tA mail daemon pushes out a dilapidated package, makes a futile attempt to polish it up, then scurries into the depths of the mail room.\n\nThe label reads:\n\t")
                    .append(msg)
                    .append("\n")
                    .append("The package contains:\n")));
    itList list = (itList) Item.factory(buf);
    for (int ix = 0; ix < list.getCount(); ix++) {
      Item it = list.select(ix);
      if (it.getName() != null && it.getName().length() >= 1) {
        msg2 =
            String.valueOf(String.valueOf(msg2))
                .concat(
                    String.valueOf(
                        String.valueOf(
                            String.valueOf(
                                String.valueOf(
                                    new StringBuffer("\t").append(it.toShow()).append("\n"))))));
        Screen.addPack(it);
      }
    }
    h.subMoney(100);
    Screen.saveHero();
    return new arNotice(this, msg2);
  }
}
