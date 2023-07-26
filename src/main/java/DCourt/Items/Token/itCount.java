package DCourt.Items.Token;

import DCourt.Items.Item;
import DCourt.Items.itToken;
import DCourt.Tools.Buffer;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Items/Token/itCount.class */
public class itCount extends itToken {
  private int value;
  private int offset;

  public itCount() {
    this(null, 0);
  }

  public itCount(String id) {
    this(id, 0);
  }

  public itCount(itCount it) {
    this(it.getName(), it.getCount());
  }

  public itCount(String id, int num) {
    super(id);
    setCount(num);
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public Item copy() {
    return new itCount(this);
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String getIcon() {
    return "#";
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String toString(int depth) {
    return String.valueOf(
        String.valueOf(
            new StringBuffer(String.valueOf(String.valueOf(toStringHead(depth))))
                .append("|")
                .append(getCount())
                .append("}")));
  }

  public static Item factory(Buffer buf) {
    String msg;
    itCount it = new itCount();
    if (!buf.begin() || (msg = buf.token()) == null || msg.length() != 1) {
      return null;
    }
    if (buf.split()) {
      it.setName(buf.token());
    }
    if (buf.split()) {
      it.setCount(buf.num());
    }
    if (!buf.end()) {
      return null;
    }
    return it;
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public void setCount(int num) {
    this.offset = Tools.roll(1024) + 1;
    this.value = num - this.offset;
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public int getCount() {
    return this.value + this.offset;
  }

  public int makeCount() {
    return getCount();
  }

  public String display() {
    return String.valueOf(
        String.valueOf(
            new StringBuffer(String.valueOf(String.valueOf(getName())))
                .append("[")
                .append(getCount())
                .append("]")));
  }

  public int add(itCount itc) {
    return add(itc.getCount());
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public int add(int num) {
    if (num > 0) {
      setCount(getCount() + num);
    }
    return getCount();
  }

  public int adds(itCount itc) {
    return adds(itc.getCount());
  }

  public int adds(int num) {
    setCount(getCount() + num);
    return getCount();
  }

  public int sub(itCount itc) {
    return sub(itc.getCount());
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public int sub(int num) {
    int sum = getCount();
    if (num < 0) {
      return 0;
    }
    if (num > sum) {
      num = sum;
    }
    setCount(sum - num);
    return num;
  }
}
