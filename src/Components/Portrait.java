package DCourt.Components;

import DCourt.Tools.Breaker;
import DCourt.Tools.Tools;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Event;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/* loaded from: DCourt.jar:DCourt/Components/Portrait.class */
public class Portrait extends Canvas {
    public static final int NOTEXT = 0;
    public static final int SUBTEXT = 1;
    public static final int SUPERTEXT = 2;
    Image icon;
    String text;
    boolean loading;
    int type;

    public Portrait() {
        this.icon = null;
        this.text = null;
        this.loading = false;
        setForeground(Color.black);
        this.type = 1;
    }

    public Portrait(String where, int x, int y, int w, int h) {
        this();
        reshape(x, y, w, h);
        this.icon = Tools.loadImage(where);
    }

    public Portrait(String where, String msg, int x, int y, int w, int h) {
        this();
        this.text = msg;
        reshape(x, y, w, h);
        this.icon = Tools.loadImage(where);
    }

    public String toString() {
        return "Portrait : ".concat(String.valueOf(String.valueOf(this.text)));
    }

    public void setType(int val) {
        this.type = val;
    }

    public void setText(String msg) {
        this.text = msg;
    }

    public String getText() {
        return this.text;
    }

    public Image getIcon() {
        return this.icon;
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        Rectangle r = bounds();
        g.setColor(getForeground());
        if (!this.loading) {
            g.drawRect(0, 0, r.width - 1, r.height - 1);
            prepareImage(this.icon, this);
            this.loading = true;
        }
        boolean widthGood = this.icon.getWidth(this) == r.width;
        boolean heightGood = this.icon.getHeight(this) == r.height;
        boolean doneLoading = (checkImage(this.icon, this) & 32) != 0;
        if (this.icon == null || (!doneLoading && (!widthGood || !heightGood))) {
            g.drawRect(0, 0, r.width - 1, r.height - 1);
        } else {
            g.drawImage(this.icon, 0, 0, r.width, r.height, (ImageObserver) null);
        }
        if (this.type == 1 && this.text != null) {
            Graphics pg = getParent().getGraphics();
            pg.setColor(getForeground());
            pg.setFont(Tools.textF);
            FontMetrics fm = getFontMetrics(Tools.textF);
            pg.drawString(this.text, r.x + ((r.width - fm.stringWidth(this.text)) / 2), r.y + r.height + fm.getAscent());
        }
        if (this.type == 2 && this.text != null) {
            FontMetrics fm2 = getFontMetrics(Tools.statusF);
            Breaker lines = new Breaker(this.text, fm2, r.width, false);
            int count = lines.lineCount();
            g.setFont(Tools.statusF);
            g.setColor(Color.white);
            for (int i = 0; i < count; i++) {
                String msg = lines.getLine(i);
                g.drawString(msg, (r.width - fm2.stringWidth(msg)) / 2, 5 + ((1 + i) * fm2.getAscent()));
            }
        }
    }

    public boolean mouseDown(Event e, int x, int y) {
        postEvent(new Event(this, 1001, (Object) null));
        return true;
    }

    public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {
        repaint();
        return imageUpdate(img, flags, x, y, w, h);
    }
}
