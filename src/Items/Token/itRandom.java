package DCourt.Items.Token;

import DCourt.Items.Item;
import DCourt.Tools.Buffer;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Items/Token/itRandom.class */
public class itRandom extends itCount {
    itRandom() {
    }

    itRandom(itCount it) {
        super(it);
    }

    itRandom(itRandom it) {
        super(it);
    }

    @Override // DCourt.Items.Token.itCount, DCourt.Items.itToken, DCourt.Items.Item
    public Item copy() {
        return new itRandom(this);
    }

    @Override // DCourt.Items.Token.itCount
    public int makeCount() {
        return Tools.roll(1 + getCount());
    }

    @Override // DCourt.Items.Token.itCount, DCourt.Items.itToken, DCourt.Items.Item
    public String getIcon() {
        return "*";
    }

    public static Item factory(Buffer buf) {
        return new itRandom((itCount) itCount.factory(buf));
    }
}
