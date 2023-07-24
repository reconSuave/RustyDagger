package DCourt.Items.Token;

import DCourt.Items.Item;
import DCourt.Tools.Buffer;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Items/Token/itPercent.class */
public class itPercent extends itCount {
    itPercent() {
    }

    itPercent(itCount it) {
        super(it);
    }

    itPercent(itPercent it) {
        super(it);
    }

    @Override // DCourt.Items.Token.itCount, DCourt.Items.itToken, DCourt.Items.Item
    public Item copy() {
        return new itPercent(this);
    }

    @Override // DCourt.Items.Token.itCount
    public int makeCount() {
        return Tools.percent(getCount()) ? 1 : 0;
    }

    @Override // DCourt.Items.Token.itCount, DCourt.Items.itToken, DCourt.Items.Item
    public String getIcon() {
        return "%";
    }

    public static Item factory(Buffer buf) {
        return new itPercent((itCount) itCount.factory(buf));
    }
}
