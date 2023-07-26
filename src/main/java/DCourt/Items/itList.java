package DCourt.Items;

import DCourt.Items.List.itArms;
import DCourt.Items.Token.itCount;
import DCourt.Items.Token.itValue;
import DCourt.Tools.Buffer;
import DCourt.Tools.Tools;
import java.util.Enumeration;
import java.util.Vector;

/* loaded from: DCourt.jar:DCourt/Items/itList.class */
public class itList extends itToken {
  private Vector queue;

  public itList(String id) {
    super(id);
    this.queue = new Vector();
  }

  public itList(String id, String[] list) {
    this(id);
    merge(list);
  }

  public itList(itList it) {
    this(it.getName());
    for (int ix = 0; ix < it.getCount(); ix++) {
      append(it.select(ix).copy());
    }
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public Item copy() {
    return new itList(this);
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String getIcon() {
    return "~";
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String toString() {
    return toString(0);
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String toString(int depth) {
    return String.valueOf(String.valueOf(toStringHead(depth)))
        .concat(String.valueOf(String.valueOf(listBody(depth))));
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String toShow() {
    return String.valueOf(String.valueOf(getName())).concat("(1)");
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public String toLoot() {
    return "1 ".concat(String.valueOf(String.valueOf(getName())));
  }

  public String listBody(int depth) {
    String result = "";
    int count = 0;
    for (int ix = 0; ix < this.queue.size(); ix++) {
      Item it = (Item) this.queue.elementAt(ix);
      String result2 = String.valueOf(String.valueOf(result)).concat("|");
      count = it.getIcon().length() < 1 ? count + 1 : count + 2;
      if ((it instanceof itArms) || (it instanceof itList) || count >= 6) {
        result =
            String.valueOf(String.valueOf(result2))
                .concat(
                    String.valueOf(
                        String.valueOf(
                            "\n".concat(String.valueOf(String.valueOf(it.toString(depth + 1)))))));
        count = count >= 6 ? 0 : 5;
      } else {
        result =
            String.valueOf(String.valueOf(result2))
                .concat(String.valueOf(String.valueOf(it.toString())));
      }
    }
    return String.valueOf(String.valueOf(result)).concat("}");
  }

  public static Item factory(Buffer buf) {
    if (!buf.begin()) {
      return null;
    }
    if ((!buf.match("itList") && !buf.match("~")) || !buf.split()) {
      return null;
    }
    itList what = new itList(buf.token());
    what.loadBody(buf);
    return what;
  }

  public void loadBody(Buffer buf) {
    while (buf.split()) {
      append(Item.factory(buf));
    }
    buf.end();
  }

  public Enumeration elements() {
    return this.queue.elements();
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public boolean isValid() {
    if (!super.isValid()) {
      return false;
    }
    for (int ix = 0; ix < this.queue.size(); ix++) {
      if (!select(ix).isValid()) {
        return false;
      }
    }
    return true;
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public int getCount() {
    return this.queue.size();
  }

  public boolean isEmpty() {
    return getCount() < 1;
  }

  public Vector getQueue() {
    return this.queue;
  }

  public void clrQueue() {
    this.queue.removeAllElements();
  }

  public void merge(itList itl) {
    Enumeration e = itl.elements();
    while (e.hasMoreElements()) {
      insert((Item) e.nextElement());
    }
  }

  public void merge(String[] str) {
    for (String str2 : str) {
      append(new itToken(str2));
    }
  }

  @Override // DCourt.Items.itToken, DCourt.Items.Item
  public boolean decay(int rate) {
    boolean result = false;
    for (int ix = 0; ix < getCount(); ix++) {
      if (select(ix).decay(rate)) {
        result = true;
      }
    }
    return result;
  }

  public void insert(String id) {
    if (id != null) {
      insert(new itToken(id));
    }
  }

  public void insert(String id, String val) {
    if (id != null) {
      insert(new itValue(id, val));
    }
  }

  public void insert(Item it) {
    if (it != null) {
      if (!(it instanceof itCount) || find(it.getName()) == null) {
        this.queue.insertElementAt(it, 0);
      } else {
        add((itCount) it);
      }
    }
  }

  public void append(String id) {
    if (id != null) {
      append(new itToken(id));
    }
  }

  public void append(String id, String val) {
    if (id != null) {
      append(new itValue(id, val));
    }
  }

  public void append(Item it) {
    if (it != null) {
      if (!(it instanceof itCount) || find(it.getName()) == null) {
        this.queue.addElement(it);
      } else {
        add((itCount) it);
      }
    }
  }

  public int add(String id, int num) {
    return add(new itCount(id, num));
  }

  public int add(itCount itc) {
    if (itc.getCount() < 1) {
      return getCount(itc);
    }
    Item it = find(itc.getName());
    if (it != null) {
      return it.add(itc.getCount());
    }
    insert(itc);
    return itc.getCount();
  }

  public boolean hasTrait(String id) {
    return find(id) instanceof itToken;
  }

  public void fixTrait(String id) {
    if (id != null) {
      drop(id);
      append(new itToken(id));
    }
  }

  public void clrTrait(String id) {
    Item it;
    if (id != null && (it = find(id)) != null && (it instanceof itToken)) {
      drop(it);
    }
  }

  public void dropAll() {
    this.queue.removeAllElements();
  }

  public Item drop(String id) {
    return drop(find(id));
  }

  public Item drop(Item it) {
    if (it != null && this.queue.removeElement(it)) {
      return it;
    }
    return null;
  }

  public int sub(itCount it) {
    return sub(it.getName(), it.getCount());
  }

  public int sub(String id, int num) {
    if (num < 1) {
      return getCount(id);
    }
    Item it = find(id);
    if (it == null || !(it instanceof itCount)) {
      return 0;
    }
    int sum = it.getCount();
    if (num < sum) {
      return it.sub(num);
    }
    drop(it);
    return sum;
  }

  public void zero(String id) {
    Item it = find(id);
    if (it != null && (it instanceof itCount)) {
      drop(it);
    }
  }

  public void dropItem(String id) {
    Item it = find(id);
    if (it != null && (it instanceof Item)) {
      drop(it);
    }
  }

  public void fix(String id, int num) {
    fix(new itCount(id, num));
  }

  public void fix(String id, String val) {
    fix(new itValue(id, val));
  }

  public void fix(Item it) {
    drop(it.getName());
    append(it);
  }

  public void update(itList list) {
    Enumeration e = elements();
    while (e.hasMoreElements()) {
      list.fix((Item) e.nextElement());
    }
  }

  public itList fixList(String id) {
    Item it = find(id);
    if (it == null || !(it instanceof itList)) {
      drop(it);
      Item itlist = new itList(id);
      it = itlist;
      append(itlist);
    }
    return (itList) it;
  }

  public Item select(int ix) {
    if (ix < 0 || ix >= this.queue.size()) {
      return null;
    }
    return (Item) this.queue.elementAt(ix);
  }

  public Item select(String id, int num) {
    Enumeration e = elements();
    int count = 0;
    while (e.hasMoreElements()) {
      Item it = (Item) e.nextElement();
      if (it.isMatch(id)) {
        count++;
        if (count > num) {
          return it;
        }
      }
    }
    return null;
  }

  public int indexOf(Item what) {
    return this.queue.indexOf(what);
  }

  public int firstOf(String id) {
    for (int ix = 0; ix < getCount(); ix++) {
      if (select(ix).isMatch(id)) {
        return ix;
      }
    }
    return -1;
  }

  public Item find(String id) {
    for (int ix = 0; ix < this.queue.size(); ix++) {
      Item it = (Item) this.queue.elementAt(ix);
      if (it.isMatch(id)) {
        return it;
      }
    }
    return null;
  }

  public int getCount(Item it) {
    return getCount(it.getName());
  }

  public int getCount(String id) {
    Item it = find(id);
    if (it == null) {
      return 0;
    }
    return it.getCount();
  }

  public boolean contains(String id) {
    return find(id) != null;
  }

  public String getValue(String id) {
    Item it = find(id);
    if (it == null || !(it instanceof itValue)) {
      return null;
    }
    return it.getValue();
  }

  public void loseHalf() {
    int ix = 0;
    while (ix < getCount()) {
      Item it = select(ix);
      if (it instanceof itCount) {
        it.sub(Tools.roll(1 + it.getCount()));
        if (it.getCount() > 0) {
          ix++;
        }
        drop(it);
        ix--;
        ix++;
      } else {
        if (Tools.chance(2)) {
          ix++;
        }
        drop(it);
        ix--;
        ix++;
      }
    }
  }

  public int fullSkill() {
    int skill = 0;
    for (int ix = 0; ix < getCount(); ix++) {
      Item it = select(ix);
      if (it instanceof itArms) {
        skill += ((itArms) it).fullSkill();
      }
    }
    return skill;
  }

  public int fullAttack() {
    int attack = 0;
    for (int ix = 0; ix < getCount(); ix++) {
      Item it = select(ix);
      if (it instanceof itArms) {
        attack += ((itArms) it).fullAttack();
      }
    }
    return attack;
  }

  public int fullDefend() {
    int defend = 0;
    for (int ix = 0; ix < getCount(); ix++) {
      Item it = select(ix);
      if (it instanceof itArms) {
        defend += ((itArms) it).fullDefend();
      }
    }
    return defend;
  }

  public itArms findArms(String id) {
    for (int ix = 0; ix < getCount(); ix++) {
      Item what = select(ix);
      if (what instanceof itArms) {
        itArms it = (itArms) what;
        if (it.hasTrait(id)) {
          return it;
        }
      }
    }
    return null;
  }
}
