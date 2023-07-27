package DCourt.Items.List;

import DCourt.Items.Item;
import DCourt.Items.itList;
import DCourt.Tools.Buffer;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Items/List/itText.class */
public class itText extends itList {
  MadLib text;

  public itText(String id) {
    super(id);
    this.text = null;
  }

  public itText(String id, String msg) {
    super(id);
    this.text = null;
    setText(msg);
  }

  public itText(itText it) {
    super(it);
    this.text = null;
    this.text = (MadLib) it.text.clone();
  }

  @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
  public Item copy() {
    return new itText(this);
  }

  @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
  public String getIcon() {
    return "itText";
  }

  @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
  public String toString(int depth) {
    return String.valueOf(
        String.valueOf(
            new StringBuffer(String.valueOf(String.valueOf(toStringHead(depth))))
                .append("|")
                .append(getText())
                .append("}")));
  }

  public void setText(String msg) {
    this.text = new MadLib(msg);
  }

  public String getText() {
    return this.text.getText();
  }

  public String getIdentity() {
    return this.text.getReplace("$NAME$");
  }

  @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
  public String toString() {
    return String.valueOf(
        String.valueOf(
            new StringBuffer("{itText|").append(getName()).append("|").append(getText())));
  }

  public static Item factory(Buffer buf) {
    if (!buf.begin() || !buf.match("itText") || !buf.split()) {
      return null;
    }
    itText it = new itText(buf.token());
    if (buf.split()) {
      it.text = new MadLib(buf.token());
    }
    it.loadBody(buf);
    it.parseText();
    return it;
  }

  void parseText() {
    Item pick;
    for (int ix = 0; ix < getCount(); ix++) {
      Item list = select(ix);
      if ((list instanceof itList)
          && (pick = ((itList) list).select(Tools.roll(list.getCount()))) != null) {
        this.text.replace(list.getName(), pick.getName());
      }
    }
  }
}
