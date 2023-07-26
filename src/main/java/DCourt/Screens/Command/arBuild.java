package DCourt.Screens.Command;

import DCourt.Components.FTextField;
import DCourt.Items.List.itHero;
import DCourt.Items.Token.itValue;
import DCourt.Items.itList;
import DCourt.Screens.Screen;
import DCourt.Static.Constants;
import DCourt.Static.GameStrings;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Rectangle;

/* loaded from: DCourt.jar:DCourt/Screens/Command/arBuild.class */
public class arBuild extends Screen implements GameStrings {
  Button save;
  Button done;
  Button random;
  CheckboxGroup[] cbg;
  Checkbox[] cb;
  FTextField[] text;
  int gender = 0;
  static final int[] tlen = {15, 15, 15, 15, 15, 15, 40, 60, 60};
  static final Rectangle[] trect = {
    new Rectangle(70, 103, 100, 22),
    new Rectangle(70, 133, 100, 22),
    new Rectangle(70, 163, 100, 22),
    new Rectangle(260, 103, 100, 22),
    new Rectangle(260, 133, 100, 22),
    new Rectangle(260, 163, 100, 22),
    new Rectangle(170, 193, 225, 22),
    new Rectangle(170, 219, 225, 22),
    new Rectangle(170, 245, 225, 22)
  };

  public arBuild(Screen from) {
    super(from, "Hero Description");
    setBackground(Color.blue);
    setForeground(Color.white);
    setFont(Tools.courtF);
    hideStatusBar();
  }

  @Override // DCourt.Screens.Screen
  public void localPaint(Graphics g) {
    itHero h = Screen.getHero();
    g.setColor(getForeground());
    g.setFont(Tools.statusF);
    g.drawString(
        "Description of "
            .concat(
                String.valueOf(
                    String.valueOf(
                        String.valueOf(
                            String.valueOf(
                                new StringBuffer(
                                        String.valueOf(
                                            String.valueOf(
                                                Constants.rankName[this.gender][h.getSocial()])))
                                    .append(" ")
                                    .append(h.getName())))))),
        5,
        15);
    g.setFont(Tools.courtF);
    g.drawString("Nervous Habit", 50, 210);
    g.drawString("Distinguishing Marks", 2, 236);
    g.drawString("Clever Catch-phrase", 10, 262);
    g.drawString(Constants.GENDER, 5, 35);
    g.drawString(Constants.DRESS, 5, 55);
    g.drawString("Behavior", 5, 75);
    g.drawString(Constants.TITLE, 5, 95);
    g.drawString(Constants.SKIN, 210, 120);
    g.drawString(Constants.EYES, 210, 150);
    g.drawString(Constants.HAIR, 210, 180);
    g.drawString(Constants.RACE, 20, 120);
    g.drawString(Constants.BUILD, 20, 150);
    g.drawString(Constants.SIGN, 20, 180);
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == this.random) {
      Randomize();
    }
    if (e.target == this.done) {
      Tools.setRegion(getHome());
    }
    if (e.target == this.save) {
      CreateHeroLooks();
      Tools.setRegion(getHome());
    }
    if (e.target == this.cb[12]) {
      this.gender = 0;
    }
    if (e.target == this.cb[13]) {
      this.gender = 1;
    }
    repaint();
    return super.action(e, o);
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    setFont(Tools.textF);
    setForeground(Color.black);
    this.save = new Button("Fix These Settings Permanently");
    this.save.setFont(Tools.textF);
    this.done = new Button("I'll get to this later");
    this.done.setFont(Tools.textF);
    this.random = new Button("Random");
    this.random.setFont(Tools.textF);
    this.cbg = new CheckboxGroup[4];
    for (int i = 0; i < this.cbg.length; i++) {
      this.cbg[i] = new CheckboxGroup();
    }
    this.cb = new Checkbox[14];
    for (int i2 = 0; i2 < this.cb.length; i2++) {
      this.cb[i2] = new Checkbox(Constants.sexs[i2 % 4], this.cbg[i2 / 4], i2 % 4 == 0);
      this.cb[i2].setBackground(Color.blue);
      this.cb[i2].setFont(Tools.textF);
      this.cb[i2].reshape(80 + ((i2 % 4) * 70), 20 + ((i2 / 4) * 20), 60, 20);
    }
    this.save.reshape(5, 275, 200, 20);
    this.done.reshape(245, 275, 150, 20);
    this.random.reshape(328, 2, 70, 20);
    this.text = new FTextField[tlen.length];
    for (int i3 = 0; i3 < tlen.length; i3++) {
      this.text[i3] = new FTextField(tlen[i3]);
      this.text[i3].reshape(trect[i3].x, trect[i3].y, trect[i3].width, trect[i3].height);
      this.text[i3].setFont(Tools.statusF);
    }
    for (int i4 = 0; i4 < 3; i4++) {
      this.text[i4 + 0].reshape(70, 103 + (i4 * 30), 100, 22);
      this.text[i4 + 3].reshape(260, 103 + (i4 * 30), 100, 22);
      this.text[i4 + 6].reshape(170, 193 + (i4 * 25), 225, 22);
    }
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    for (int i = 0; i < this.cb.length; i++) {
      add(this.cb[i]);
    }
    for (int i2 = 0; i2 < this.text.length; i2++) {
      add(this.text[i2]);
    }
    add(this.save);
    add(this.done);
    add(this.random);
    Randomize();
  }

  void Randomize() {
    this.text[0].setText(GameStrings.races[Tools.roll(GameStrings.races.length)]);
    this.text[1].setText(Tools.select(GameStrings.builds));
    this.text[2].setText(Tools.select(GameStrings.signs));
    this.text[3].setText(Tools.select(GameStrings.colors));
    this.text[4].setText(Tools.select(GameStrings.colors));
    this.text[5].setText(Tools.select(GameStrings.colors));
    this.text[6].setText(Tools.select(GameStrings.habits));
    this.text[7].setText(Tools.select(GameStrings.features));
    this.text[8].setText(Tools.select(GameStrings.phrases));
    this.gender = Tools.roll(2);
    this.cbg[0].setCurrent(this.cb[this.gender]);
    this.cbg[1].setCurrent(this.cb[4 + this.gender]);
    this.cbg[2].setCurrent(this.cb[8 + this.gender]);
    this.cbg[3].setCurrent(this.cb[12 + this.gender]);
  }

  void CreateHeroLooks() {
    itList lp = Screen.getHero().getLooks();
    for (int i = 0; i < 4; i++) {
      if (this.cb[0 + i].getState()) {
        lp.append(Constants.GENDER, Constants.sexs[i]);
      }
      if (this.cb[4 + i].getState()) {
        lp.append(Constants.DRESS, Constants.sexs[i]);
      }
      if (this.cb[8 + i].getState()) {
        lp.append(Constants.BEHAVE, Constants.sexs[i]);
      }
      if (i < 2 && this.cb[12 + i].getState()) {
        lp.append(Constants.TITLE, Constants.sexs[i]);
      }
    }
    lp.append(new itValue(Constants.RACE, Tools.detokenize(this.text[0].getText())));
    lp.append(new itValue(Constants.BUILD, Tools.detokenize(this.text[1].getText())));
    lp.append(new itValue(Constants.SIGN, Tools.detokenize(this.text[2].getText())));
    lp.append(new itValue(Constants.SKIN, Tools.detokenize(this.text[3].getText())));
    lp.append(new itValue(Constants.EYES, Tools.detokenize(this.text[4].getText())));
    lp.append(new itValue(Constants.HAIR, Tools.detokenize(this.text[5].getText())));
    lp.append(new itValue(Constants.HABIT, Tools.detokenize(this.text[6].getText())));
    lp.append(new itValue("Marks", Tools.detokenize(this.text[7].getText())));
    lp.append(new itValue(Constants.PHRASE, Tools.detokenize(this.text[8].getText())));
  }
}
