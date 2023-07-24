package DCourt.Components;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

/* loaded from: DCourt.jar:DCourt/Components/FTextList.class */
public class FTextList extends FTools {
    FScrollbar scroll;
    int width;
    int height;
    String[] text = null;
    int selectVal = -1;
    int base = 0;
    boolean canSelect = true;

    public FTextList() {
        this.scroll = null;
        FScrollbar fScrollbar = new FScrollbar();
        this.scroll = fScrollbar;
        add(fScrollbar);
        reshape(0, 0, 50, 50);
    }

    public FTextList(Font f) {
        super(f);
        this.scroll = null;
        FScrollbar fScrollbar = new FScrollbar();
        this.scroll = fScrollbar;
        add(fScrollbar);
        reshape(0, 0, 50, 50);
    }

    public void reshape(Rectangle r) {
        reshape(r.x, r.y, r.width, r.height);
    }

    public void reshape(int x, int y, int w, int h) {
        reshape(x, y, w, h);
        this.width = w - 6;
        this.height = h;
        this.scroll.reshape(w - 12, 0, 12, this.height);
        FixScroller();
    }

    public void setCanSelect(boolean val) {
        this.canSelect = val;
    }

    @Override // DCourt.Components.FTools
    public void setFill(Color fc) {
        setFill(fc);
        this.scroll.setFill(fc);
    }

    @Override // DCourt.Components.FTools
    public void update(Graphics g) {
        paintAll(g);
    }

    public void paint(Graphics g) {
        g.setColor(this.fill);
        g.fillRect(0, 0, bounds().width, bounds().height);
        this.base = this.scroll.getVal();
        if (this.text != null) {
            g.setColor(getForeground());
            g.setFont(getFont());
            int show = showLines() + 1;
            int textH = this.fmet.getHeight();
            int v = 3 + this.fmet.getAscent();
            int ix = 0;
            while (ix < show && this.base + ix < this.text.length) {
                g.drawString(this.text[this.base + ix], 3, v + (ix * textH));
                ix++;
            }
            int pick = this.selectVal - this.base;
            if (pick >= 0 && pick < show) {
                g.setColor(this.dark);
                g.fillRect(3, 3 + (pick * textH), this.width, textH);
                g.setColor(this.glow);
                g.drawString(this.text[this.selectVal], 3, v + (pick * textH));
            }
        }
        drawSinkBorder(g);
    }

    @Override // DCourt.Components.FTools
    public boolean handleEvent(Event e) {
        if (e.target != this.scroll || this.scroll.getVal() == this.base) {
            return handleEvent(e);
        }
        repaint();
        return true;
    }

    public boolean mouseDown(Event e, int x, int y) {
        if (!setSelect(this.base + ((y - 3) / this.fmet.getHeight()))) {
            return true;
        }
        postEvent(new Event(this, 1001, (Object) null));
        return true;
    }

    public void addItem(String str) {
        if (this.text == null) {
            addItem(str, 0);
        } else {
            addItem(str, this.text.length);
        }
        repaint();
    }

    public void addItem(String str, int index) {
        if (str != null) {
            if (this.text == null) {
                this.text = new String[1];
                this.text[0] = str;
                FixScroller();
                return;
            }
            if (index < 0 || index >= this.text.length) {
                index = this.text.length;
            }
            String[] temp = new String[this.text.length + 1];
            for (int i = 0; i < index; i++) {
                temp[i] = this.text[i];
            }
            temp[index] = str;
            for (int i2 = index; i2 < this.text.length; i2++) {
                temp[i2 + 1] = this.text[i2];
            }
            this.text = temp;
            FixScroller();
            repaint();
        }
    }

    public void clear() {
        this.text = null;
        FixScroller();
        repaint();
    }

    public void delItem(int index) {
        if (this.text != null && index >= 0 && index < this.text.length) {
            if (this.text.length == 1) {
                this.text = null;
                return;
            }
            String[] temp = new String[this.text.length - 1];
            for (int i = 0; i < index; i++) {
                temp[i] = this.text[i];
            }
            for (int i2 = index + 1; i2 < this.text.length; i2++) {
                temp[i2 - 1] = this.text[i2];
            }
            this.text = temp;
            if (this.selectVal >= this.text.length) {
                this.selectVal = -1;
            }
            FixScroller();
            repaint();
        }
    }

    public void setItem(String str, int index) {
        if (this.text != null && index >= 0 && index < this.text.length) {
            this.text[index] = str;
            repaint();
        }
    }

    public boolean setSelect(int index) {
        if (!this.canSelect || this.selectVal == index) {
            return false;
        }
        if (this.text == null || index < 0 || index >= this.text.length) {
            this.selectVal = -1;
        } else if (this.selectVal == -1 || index != this.selectVal) {
            this.selectVal = index;
        } else {
            this.selectVal = -1;
        }
        repaint();
        return true;
    }

    public String getItem(int index) {
        if (this.text == null || this.selectVal < 0 || this.selectVal >= this.text.length) {
            return null;
        }
        return this.text[this.selectVal];
    }

    public int getSelect() {
        return this.selectVal;
    }

    public void FixScroller() {
        if (this.text == null) {
            this.scroll.show(false);
            return;
        }
        int count = this.text.length;
        int shown = showLines();
        if (shown >= count) {
            this.scroll.show(false);
            return;
        }
        this.scroll.setMax(count - shown);
        this.scroll.show(true);
        repaint();
    }

    public int showLines() {
        return this.height / this.fmet.getHeight();
    }
}
