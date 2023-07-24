package DCourt.Screens.Utility;

import DCourt.Components.FTextArea;
import DCourt.Items.List.itNote;
import DCourt.Screens.Screen;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;

/* loaded from: DCourt.jar:DCourt/Screens/Utility/arScribe.class */
public class arScribe extends Screen {
  Button cancel;
  Button done;
  FTextArea text;
  String spend;

  public arScribe() {}

  public arScribe(Screen from, String use) {
    super(from, "Compose A Note");
    setBackground(Color.red);
    setForeground(Color.black);
    setFont(Tools.statusF);
    this.spend = use;
  }

  @Override // DCourt.Screens.Screen
  public boolean action(Event e, Object o) {
    if (Tools.movedAway(this)) {
      return true;
    }
    if (e.target == this.cancel) {
      Tools.setRegion(getHome());
    }
    if (e.target == this.done) {
      addNoteToPack();
      Tools.setRegion(getHome());
    }
    return action(e, o);
  }

  @Override // DCourt.Screens.Screen
  public void createTools() {
    setFont(Tools.textF);
    setForeground(Color.black);
    this.cancel = new Button("Cancel");
    this.done = new Button("Done");
    this.text = new FTextArea(Tools.DEFAULT_HEIGHT);
    this.text.reshape(20, 30, 360, 230);
    this.text.setFont(Tools.statusF);
    this.cancel.reshape(280, 5, 50, 20);
    this.cancel.setFont(Tools.textF);
    this.done.reshape(340, 5, 50, 20);
    this.done.setFont(Tools.textF);
  }

  @Override // DCourt.Screens.Screen
  public void addTools() {
    add(this.cancel);
    add(this.done);
    add(this.text);
  }

  void addNoteToPack() {
    String msg = Tools.detokenize(this.text.getText());
    Screen.subPack(this.spend, 1);
    Screen.putPack(
        new itNote(
            this.spend.equals("Pen & Paper") ? "Letter" : "Postcard",
            Screen.getHero().getName(),
            msg));
  }
}
