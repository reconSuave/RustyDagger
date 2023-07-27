package DCourt.Items.Token;

import DCourt.Items.Item;
import DCourt.Items.itToken;
import DCourt.Tools.Buffer;

/* loaded from: DCourt.jar:DCourt/Items/Token/itValue.class */
public class itValue extends itToken {
  String value;

  public itValue(String id, String val) {
    super(id);
    this.value = null;
    this.value = val;
  }

  public itValue() {
    this((String) null, (String) null);
  }

  public itValue(String id) {
    this(id, (String) null);
  }

  public itValue(int id, String val) {
    this("".concat(String.valueOf(String.valueOf(id))), val);
  }

  public itValue(itValue it) {
    this(it.getName(), it.getValue());
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public Item copy() {
    return new itValue(this);
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String getIcon() {
    return "=";
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String toString(int depth) {
    return String.valueOf(
        String.valueOf(
            new StringBuffer(String.valueOf(String.valueOf(toStringHead(depth))))
                .append("|")
                .append(getValue())
                .append("}")));
  }

  public static Item factory(Buffer buf) {
    itValue v = new itValue();
    if (!buf.begin() || !buf.match("=")) {
      return null;
    }
    if (buf.split()) {
      v.setName(buf.token());
    }
    if (buf.split()) {
      v.setValue(buf.token());
    }
    buf.end();
    return v;
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String getValue() {
    return this.value;
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public void setValue(String val) {
    this.value = val;
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public long toLong() {
    try {
      return new Long(this.value).longValue();
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public int toInt() {
    try {
      return new Long(this.value).intValue();
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}
