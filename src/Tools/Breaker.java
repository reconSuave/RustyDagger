package DCourt.Tools;

import java.awt.FontMetrics;

/* loaded from: DCourt.jar:DCourt/Tools/Breaker.class */
public class Breaker {
    Buffer buf;
    int wide;
    int tabWidth;
    boolean indent;
    FontMetrics fm;
    String[] lines = new String[0];
    static final String TAB = "TAB";
    // static final String TAB = TAB;

    public Breaker(String msg, FontMetrics fm, int wide, boolean indent) {
        this.buf = new Buffer(msg);
        this.wide = wide;
        this.indent = indent;
        this.fm = fm;
        if (fm != null) {
            this.tabWidth = fm.stringWidth(TAB);
            breakText();
        }
    }

    public String getText() {
        return this.buf.toString();
    }

    public FontMetrics getFontMetrics() {
        return this.fm;
    }

    public int getAscent() {
        return this.fm.getAscent();
    }

    public int getHeight() {
        return this.fm.getHeight();
    }

    public void breakText() {
        char c;
        int end;
        boolean paragraph = true;
        if (!(this.wide == 0 || this.fm == null)) {
            while (!this.buf.isDone()) {
                int i = (paragraph || !this.indent) ? 0 : this.tabWidth;
                int pixels = i;
                int markPixels = i;
                int start = this.buf.index();
                while (true) {
                    c = this.buf.getChar();
                    if (c == 0 || c == '\n') {
                        break;
                    } else if (c != '\r') {
                        pixels = c == '\t' ? pixels + this.tabWidth : pixels + this.fm.charWidth(c);
                        if (pixels > this.wide) {
                            if (markPixels > (pixels * 4) / 5) {
                                this.buf.goMark();
                                end = this.buf.index();
                            } else {
                                this.buf.advance(-1);
                                end = this.buf.index();
                            }
                        } else if (c <= ' ') {
                            this.buf.setMark();
                            markPixels = pixels;
                        }
                    }
                }
                end = this.buf.index();
                String msg = (paragraph || !this.indent) ? "" : TAB;
                for (int ix = start; ix < end; ix++) {
                    c = this.buf.getChar(ix);
                    if (c == 0 || c == '\n') {
                        break;
                    }
                    if (c != '\r') {
                        msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(c == '\t' ? TAB : "".concat(String.valueOf(String.valueOf(c))))));
                    }
                }
                paragraph = c == '\n';
                int count = this.lines.length;
                String[] temp = new String[count + 1];
                for (int ix2 = 0; ix2 < count; ix2++) {
                    temp[ix2] = this.lines[ix2];
                }
                temp[count] = msg;
                this.lines = temp;
            }
        }
    }

    public int lineCount() {
        return this.lines.length;
    }

    public String getLine(int index) {
        if (index < 0 || index >= this.lines.length) {
            return null;
        }
        return this.lines[index];
    }
}
