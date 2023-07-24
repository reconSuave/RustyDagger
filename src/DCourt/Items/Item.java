package DCourt.Items;

import DCourt.Items.List.itArms;
import DCourt.Items.List.itHero;
import DCourt.Items.List.itMonster;
import DCourt.Items.List.itNote;
import DCourt.Items.List.itText;
import DCourt.Items.Token.itCount;
import DCourt.Items.Token.itPercent;
import DCourt.Items.Token.itRandom;
import DCourt.Items.Token.itValue;
import DCourt.Tools.Buffer;

/* loaded from: DCourt.jar:DCourt/Items/Item.class */
public abstract class Item {
    public abstract Item copy();

    public abstract String toString(int i);

    public abstract String getIcon();

    public abstract String getName();

    public abstract void setName(String str);

    public abstract boolean isValid();

    public abstract String toShow();

    public abstract String toLoot();

    public abstract boolean isMatch(Item item);

    public abstract boolean isMatch(String str);

    public abstract String getValue();

    public abstract void setValue(String str);

    public abstract int getCount();

    public abstract void setCount(int i);

    public abstract int add(int i);

    public abstract int sub(int i);

    public abstract boolean decay(int i);

    public abstract long toLong();

    public abstract int toInteger();

    public String toString() {
        return toString(0);
    }

    /* access modifiers changed from: protected */
    public String toStringHead(int depth) {
        String msg = "";
        for (int ix = 0; ix < depth; ix++) {
            msg = String.valueOf(String.valueOf(msg)).concat("\t");
        }
        return String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(msg))).append("{").append(getIcon()).append("|").append(getName())));
    }

    public static Item factory(String val) {
        return factory(new Buffer(val));
    }

    public static Item factory(Buffer buf) {
        if (buf.startsWith("{#|")) {
            return itCount.factory(buf);
        }
        if (buf.startsWith("{@|")) {
            return itRandom.factory(buf);
        }
        if (buf.startsWith("{%|")) {
            return itPercent.factory(buf);
        }
        if (buf.startsWith("{=|")) {
            return itValue.factory(buf);
        }
        if (buf.startsWith("{~|") || buf.startsWith("{itList|")) {
            return itList.factory(buf);
        }
        if (buf.startsWith("{itArms|")) {
            return itArms.factory(buf);
        }
        if (buf.startsWith("{itText|")) {
            return itText.factory(buf);
        }
        if (buf.startsWith("{itNote|")) {
            return itNote.factory(buf);
        }
        if (buf.startsWith("{itAgent|") || buf.startsWith("{itHero|")) {
            return itHero.factory(buf);
        }
        return buf.startsWith("{itMonster|") ? itMonster.factory(buf) : itToken.factory(buf);
    }
}
