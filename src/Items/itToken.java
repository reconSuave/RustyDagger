package DCourt.Items;

import DCourt.Tools.Buffer;

/* loaded from: DCourt.jar:DCourt/Items/itToken.class */
public class itToken extends Item {
    private String name;
    private int hash;

    public itToken(String id) {
        setName(id);
    }

    public itToken(itToken id) {
        setName(id.name);
    }

    @Override // DCourt.Items.Item
    public Item copy() {
        return new itToken(this);
    }

    @Override // DCourt.Items.Item
    public String getIcon() {
        return "";
    }

    @Override // DCourt.Items.Item
    public String toString() {
        return toString(0);
    }

    @Override // DCourt.Items.Item
    public String toString(int depth) {
        String msg = "";
        for (int ix = 0; ix < depth; ix++) {
            msg = String.valueOf(String.valueOf(msg)).concat("\t");
        }
        return String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(getName())));
    }

    public static Item factory(Buffer buf) {
        Item it = new itToken(buf.token());
        if (it.getName() == null || it.getName().length() < 1 || it.getName().charAt(0) == '{' || it.getName().charAt(0) == '}') {
            return null;
        }
        return it;
    }

    @Override // DCourt.Items.Item
    public String getName() {
        if (!isValid()) {
            System.exit(-1);
        }
        return this.name;
    }

    @Override // DCourt.Items.Item
    public void setName(String val) {
        this.name = val;
        this.hash = this.name == null ? 0 : this.name.hashCode();
    }

    @Override // DCourt.Items.Item
    public boolean isValid() {
        return (this.name == null && this.hash == 0) || this.hash == this.name.hashCode();
    }

    @Override // DCourt.Items.Item
    public String toShow() {
        return String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(getName()))).append("(").append(getCount()).append(")")));
    }

    @Override // DCourt.Items.Item
    public String toLoot() {
        return String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(getCount()))).append(" ").append(getName())));
    }

    @Override // DCourt.Items.Item
    public String getValue() {
        return null;
    }

    @Override // DCourt.Items.Item
    public void setValue(String val) {
    }

    @Override // DCourt.Items.Item
    public boolean isMatch(Item it) {
        if (it == null || this.name == null) {
            return false;
        }
        return this.name.equalsIgnoreCase(it.getName());
    }

    @Override // DCourt.Items.Item
    public boolean isMatch(String id) {
        if (this.name == null) {
            return false;
        }
        return this.name.equalsIgnoreCase(id);
    }

    @Override // DCourt.Items.Item
    public int getCount() {
        return 1;
    }

    @Override // DCourt.Items.Item
    public void setCount(int num) {
    }

    @Override // DCourt.Items.Item
    public int add(int val) {
        return 0;
    }

    @Override // DCourt.Items.Item
    public int sub(int val) {
        return 0;
    }

    @Override // DCourt.Items.Item
    public boolean decay(int val) {
        return false;
    }

    @Override // DCourt.Items.Item
    public int toInteger() {
        try {
            return Integer.parseInt(this.name);
        } catch (NumberFormatException e) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("itToken.toInteger() failed for [").append(this.name).append("]"))));
            return 0;
        }
    }

    @Override // DCourt.Items.Item
    public long toLong() {
        try {
            return Long.parseLong(this.name);
        } catch (NumberFormatException e) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("itToken.toLong() failed for [").append(this.name).append("]"))));
            return 0;
        }
    }
}
