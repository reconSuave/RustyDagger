package DCourt.Items.List;

import DCourt.Items.Item;
import DCourt.Items.itList;
import DCourt.Tools.Buffer;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Items/List/itNote.class */
public class itNote extends itList {
  String body;
  String from;
  String date;

  public itNote(String id) {
    super(id);
  }

  public itNote(String id, String from, String msg) {
    super(id);
    setFrom(from);
    setDate(Tools.getToday());
    setBody(msg);
  }

  public itNote(itNote it) {
    super(it);
    fixStrings();
  }

  @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
  public Item copy() {
    return new itNote(this);
  }

  @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
  public String getIcon() {
    return "itNote";
  }

  public static Item factory(Buffer buf) {
    if (!buf.begin() || !buf.match("itNote") || !buf.split()) {
      return null;
    }
    itNote what = new itNote(buf.token());
    what.loadBody(buf);
    what.fixStrings();
    return what;
  }

  void fixStrings() {
    this.from = getValue("from");
    this.date = getValue("date");
    this.body = getValue("body");
  }

  public void setFrom(String val) {
    this.from = val;
    fix("from", val);
  }

  public void setDate(String val) {
    this.date = val;
    fix("date", val);
  }

  public void setBody(String val) {
    this.body = val;
    fix("body", val);
  }

  public String getFrom() {
    return this.from;
  }

  public String getDate() {
    return this.date;
  }

  public String getBody() {
    return this.body;
  }

  @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
  public int getCount() {
    return 1;
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String getValue() {
    return this.body;
  }

  @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
  public String toShow() {
    return String.valueOf(
        String.valueOf(
            new StringBuffer(String.valueOf(String.valueOf(getName())))
                .append(": ")
                .append(getFrom())));
  }

  @Override // DCourt.Items.itList, DCourt.Items.itToken, DCourt.Items.Item
  public String toLoot() {
    return getName();
  }
}
