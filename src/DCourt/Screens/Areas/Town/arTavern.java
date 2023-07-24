package DCourt.Screens.Areas.Town;

import java.awt.Button;
import java.awt.Event;
import java.awt.Graphics;

import DCourt.Items.List.itHero;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Indoors;
import DCourt.Screens.Utility.arNotice;
import DCourt.Screens.Utility.arStorage;
import DCourt.Static.Constants;
import DCourt.Static.GameStrings;
import DCourt.Static.Rumors;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Town/arTavern.class */
public class arTavern extends Indoors implements Rumors {
  Button[] tools;
  String[] monger = {
    "One old woman tells you:",
    "A spirited forester tells you:",
    "An old drunken soldier tells you:",
    "Silas Keeper whispers to you:",
    "A beligerent fish monger prods you:",
    "A sly bard sing to you:",
    "Kara the barmaid sidles up to you:"
  };
  static String[] greeting = {
    null,
    "This job is great",
    "I love to drink",
    "You're my best friend",
    "I'm so happy",
    "Hixxup! Excuse me",
    "Burrrp - Ahhhhh",
    "*Sniff* *Sniff* <Gulp>",
    "Beer is my friend",
    "Huh? You say something?",
    "I'm kinda sleepy"
  };
  static int[] cost = {1, 5, 25, 100, 1};
  static String[] text = {
    "Buy a Drink", "Sleep on Floor", "Rent a Room", "Rent a Suite", "Storage"
  };
  static final int TAVERN_ID = 1;

  public arTavern(Screen from) {
    super(from, "Silas Keepers Bed & Breakfast");
    if (Tools.getHero().hasTrait(Constants.HOTEL)) {
      for (int i = 1; i <= 4; i++) {
        cost[i] = (cost[i] + 9) / 10;
      }
    }
  }

  @Override // DCourt.Screens.Template.Indoors
  public String getFace() {
    return "Faces/Silas.jpg";
  }

  @Override // DCourt.Screens.Template.Indoors
  public String getGreeting() {
    String msg = Tools.select(greeting);
    return msg == null ? String.valueOf(String.valueOf(Tools.getBest())).concat(" who?") : msg;
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    super.localPaint(g);
    updateTools();
  }

  @Override // DCourt.Screens.Template.Indoors, DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    itHero hero = Screen.getHero();
    Screen next = null;
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == getPic(0)) {
      next = getHome();
    }
    int i = 0;
    while (true) {
      if (i < this.tools.length) {
        if (e.target == this.tools[i]) {
          if (i < 4) {
            hero.subMoney(cost[i]);
          }
          switch (i) {
            case 0:
              next = rumors();
              break;
            case 1:
              next = Screen.tryToExit(this, Constants.FLOOR, cost[i]);
              break;
            case 2:
              next = Screen.tryToExit(this, Constants.ROOM, cost[i]);
              break;
            case 3:
              next = Screen.tryToExit(this, Constants.SUITE, cost[i]);
              break;
            case 4:
              int spend = hero.subMoney(cost[i]);
              if (spend < cost[i]) {
                hero.subStore("Marks", cost[i] - spend);
              }
              next = new arStorage(this);
              break;
          }
        } else {
          i++;
        }
      } else {
        break;
      }
    }
    repaint();
    Tools.setRegion(next);
    return super.action(e, o);
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    int level = Screen.getHero().getLevel();
    int i = text.length;
    if (i > cost.length) {
      i = cost.length;
    }
    this.tools = new Button[i];
    cost[1] = 4 + level;
    cost[2] = 20 + (5 * level);
    cost[3] = 75 + (25 * level);
    cost[4] = level * 50;
    for (int i2 = 0; i2 < this.tools.length; i2++) {
      String msg = text[i2];
      if (cost[i2] > 0) {
        msg =
            String.valueOf(
                String.valueOf(new StringBuffer("$").append(cost[i2]).append(" ").append(msg)));
      }
      this.tools[i2] = new Button(msg);
      this.tools[i2].reshape(180, 55 + (i2 * 30), 180, 25);
      this.tools[i2].setFont(Tools.statusF);
    }
    updateTools();
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    for (int i = 0; i < this.tools.length; i++) {
      add(this.tools[i]);
    }
  }

  void updateTools() {
    itHero h = Screen.getHero();
    int cash = h.getMoney();
    int store = h.storeCount("Marks");
    int i = 0;
    while (i < this.tools.length - 1) {
      this.tools[i].enable(cash >= cost[i]);
      i++;
    }
    this.tools[i].enable(cash >= cost[i] || store >= cost[i] || cash + store > cost[i]);
  }

  Screen rumors() {
    String msg;
    itHero h = Screen.getHero();
    if (h.getQuests() < 1) {
      return new arNotice(
          this,
          String.valueOf(String.valueOf(GameStrings.GOSSIP))
              .concat(
                  "\nYou are so tired that you nearly pass out trying to swallow your drink.\n"));
    }
    int val = Tools.roll(h.getCharm());
    if (h.getCharm() <= 10) {
      val += 2;
    } else if (h.getCharm() <= 20) {
      val++;
    }
    switch (val) {
      case 0:
        msg =
            String.valueOf(String.valueOf(GameStrings.GOSSIP))
                .concat(
                    String.valueOf(
                        String.valueOf(
                            String.valueOf(
                                String.valueOf(
                                    new StringBuffer(
                                            "\n\tSomeone slips you a mickey. You awake with a headache in the dark and stinking alley.\n\n*** You Have Missed One Quest ***\n\n*** ")
                                        .append(h.getMoney())
                                        .append(" Marks Lost ***\n"))))));
        h.subMoney(h.getMoney());
        h.addFatigue(1);
        break;
      case 1:
        msg =
            String.valueOf(String.valueOf(GameStrings.GOSSIP))
                .concat(
                    "\n\tYou drink heavily and pass outfor a couple hours.\n\n*** You Have Missed One Quest ***\n");
        h.addFatigue(1);
        break;
      case 2:
      case 3:
      case 4:
        msg =
            String.valueOf(String.valueOf(GameStrings.GOSSIP))
                .concat("\nNoone seems interested in you...\n");
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
                                                                .append(Tools.select(this.monger))
                                                                .append("\n"))))))))
                            .concat(
                                String.valueOf(
                                    String.valueOf(
                                        String.valueOf(
                                            String.valueOf(
                                                new StringBuffer("\n\t")
                                                    .append(Tools.select(Rumors.rumors))
                                                    .append("\n"))))))))
                .concat(String.valueOf(String.valueOf(h.gainCharm(1))));
        break;
    }
    return new arNotice(this, msg);
  }
}
