package DCourt.Screens.Template;

import DCourt.Components.FScrollbar;
import DCourt.Components.FTextList;
import DCourt.Items.Item;
import DCourt.Items.Token.itCount;
import DCourt.Items.Token.itValue;
import DCourt.Items.itList;
import DCourt.Screens.Screen;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Event;

/* loaded from: DCourt.jar:DCourt/Screens/Template/Transfer.class */
public abstract class Transfer extends Screen {
    private Button exit;
    private Button transfer;
    private FTextList plist;
    private FTextList slist;
    private FScrollbar scroll;
    private int limit;
    private itList purse;
    private itList stash;
    static final String TRANSFER = TRANSFER;
    static final String TRANSFER = TRANSFER;

    public Transfer(Screen from, String name) {
        super(from, name);
    }

    public void setValues(int limit, itList purse, itList stash) {
        this.limit = limit;
        this.purse = purse;
        this.stash = stash;
    }

    public int getLimit() {
        return this.limit;
    }

    public int stashCount() {
        if (this.stash == null) {
            return 0;
        }
        return this.stash.getCount();
    }

    public itList getStash() {
        return this.stash;
    }

    public void clrStash() {
        this.stash.clrQueue();
    }

    @Override // DCourt.Screens.Screen
    public boolean action(Event e, Object o) {
        if (Tools.movedAway(this)) {
            return true;
        }
        if (e.target == this.exit) {
            goHome();
        } else if (e.target == this.transfer) {
            transfer();
        } else if (e.target == this.plist) {
            purseSelect();
        } else if (e.target == this.slist) {
            stashSelect();
        }
        repaint();
        return action(e, o);
    }

    public void goHome() {
        Tools.setRegion(getHome());
    }

    @Override // DCourt.Screens.Screen
    public void createTools() {
        this.exit = new Button("Exit");
        this.exit.reshape(340, 5, 50, 20);
        this.exit.setFont(Tools.textF);
        this.transfer = new Button("Transfer 0");
        this.transfer.reshape(5, 28, 115, 20);
        this.transfer.setFont(Tools.textF);
        this.transfer.enable(false);
        this.plist = new FTextList();
        this.plist.reshape(5, 70, 190, 190);
        this.slist = new FTextList();
        this.slist.reshape(205, 70, 190, 190);
        this.scroll = new FScrollbar();
        this.scroll.reshape(125, 30, 270, 16);
    }

    @Override // DCourt.Screens.Screen
    public void addTools() {
        add(this.exit);
        add(this.transfer);
        add(this.plist);
        add(this.slist);
        add(this.scroll);
        purseList((Item) null);
        stashList((Item) null);
    }

    public void updateTools() {
        this.transfer.enable(this.plist.getSelect() >= 0 || this.slist.getSelect() >= 0);
        this.transfer.setLabel(TRANSFER.concat(String.valueOf(String.valueOf(this.scroll.getVal()))));
    }

    void purseList(String find) {
        purseList(this.purse.find(Tools.truncate(find)));
    }

    void purseList(Item find) {
        int select = -1;
        this.plist.clear();
        for (int ix = 0; ix < this.purse.getCount(); ix++) {
            Item it = this.purse.select(ix);
            this.plist.addItem(it.toShow());
            if (it == find) {
                select = ix;
            }
        }
        this.plist.setSelect(select);
    }

    void stashList(String find) {
        purseList(this.stash.find(Tools.truncate(find)));
    }

    void stashList(Item find) {
        int select = -1;
        this.slist.clear();
        for (int ix = 0; ix < this.stash.getCount(); ix++) {
            Item it = this.stash.select(ix);
            this.slist.addItem(it.toShow());
            if (it == find) {
                select = ix;
            }
        }
        this.slist.setSelect(select);
    }

    void purseSelect() {
        Item it;
        this.slist.setSelect(-1);
        this.transfer.enable(false);
        int ix = this.plist.getSelect();
        if (ix < 0 || (it = this.purse.select(ix)) == null) {
            return;
        }
        if (it.getCount() > 1) {
            prepareTransfer(it);
            return;
        }
        PackToStash(it, ix, 1);
        this.scroll.setMax(0);
    }

    void stashSelect() {
        Item it;
        this.plist.setSelect(-1);
        this.transfer.enable(false);
        int ix = this.slist.getSelect();
        if (ix < 0 || (it = this.stash.select(ix)) == null) {
            return;
        }
        if (it.getCount() > 1) {
            prepareTransfer(it);
            return;
        }
        StashToPack(it, ix, 1);
        this.scroll.setMax(0);
    }

    void prepareTransfer(Item it) {
        if (it != null) {
            int val = it.getCount();
            this.scroll.setMax(val);
            this.scroll.setVal(val);
            this.transfer.setLabel(TRANSFER.concat(String.valueOf(String.valueOf(val))));
            this.transfer.enable(true);
        }
    }

    void transfer() {
        int count = this.scroll.getVal();
        if (count >= 1) {
            int ix = this.plist.getSelect();
            if (ix >= 0) {
                PackToStash(ix, count);
            }
            int ix2 = this.slist.getSelect();
            if (ix2 >= 0) {
                StashToPack(ix2, count);
            }
        }
    }

    void PackToStash(int pix, int count) {
        Item it = this.purse.select(pix);
        if (it != null) {
            PackToStash(it, pix, count);
        }
    }

    void PackToStash(Item it, int pix, int count) {
        boolean delSlot = true;
        boolean newSlot = true;
        String id = null;
        int six = 0;
        if (!(it instanceof itValue)) {
            if (it instanceof itCount) {
                id = it.getName();
                delSlot = count >= it.getCount();
                six = this.stash.firstOf(id);
                newSlot = six < 0;
                if (!newSlot || this.limit <= 0 || this.stash.getCount() < this.limit) {
                    this.stash.add(id, this.purse.sub(id, count));
                } else {
                    return;
                }
            } else if (this.limit <= 0 || this.stash.getCount() < this.limit) {
                this.purse.drop(it);
                this.stash.insert(it);
            } else {
                return;
            }
            if (delSlot) {
                this.plist.delItem(pix);
            } else {
                this.plist.setItem(this.purse.find(id).toShow(), pix);
            }
            this.plist.setSelect(-1);
            if (newSlot) {
                this.slist.addItem(this.stash.select(0).toShow(), 0);
            } else {
                this.slist.setItem(this.stash.find(id).toShow(), six);
            }
            this.transfer.enable(false);
            this.scroll.setMax(0);
        }
    }

    void StashToPack(int six, int count) {
        Item it = this.stash.select(six);
        if (it != null) {
            StashToPack(it, six, count);
        }
    }

    void StashToPack(Item it, int six, int count) {
        boolean delSlot = true;
        boolean newSlot = true;
        String id = null;
        int pix = 0;
        if (!(it instanceof itValue)) {
            if (!(it instanceof itCount)) {
                this.stash.drop(it);
                this.purse.insert(it);
            } else {
                id = it.getName();
                delSlot = count >= it.getCount();
                pix = this.purse.firstOf(id);
                newSlot = pix < 0;
                this.purse.add(id, this.stash.sub(id, count));
            }
            if (delSlot) {
                this.slist.delItem(six);
            } else {
                this.slist.setItem(this.stash.find(id).toShow(), six);
            }
            this.slist.setSelect(-1);
            if (newSlot) {
                this.plist.addItem(this.purse.select(0).toShow(), 0);
            } else {
                this.plist.setItem(this.purse.find(id).toShow(), pix);
            }
            this.transfer.enable(false);
            this.scroll.setMax(0);
        }
    }
}
