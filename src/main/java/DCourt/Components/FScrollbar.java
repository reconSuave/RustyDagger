package DCourt.Components;

import java.awt.Event;
import java.awt.Graphics;
import java.awt.Rectangle;

/* loaded from: DCourt.jar:DCourt/Components/FScrollbar.class */
public class FScrollbar extends FTools {
  static boolean HORIZONTAL = true;
  static boolean VERTICAL = false;
  Rectangle head;
  Rectangle tail;
  Rectangle body;
  Rectangle thumb;
  int iMax;
  int iVal;
  int iStep;
  int iJump;
  boolean horz;
  boolean drag;

  public FScrollbar(int max, int val, int step, int jump) {
    this.horz = false;
    this.drag = false;
    reshape(0, 0, 10, 30);
    setAll(max, val, step, jump);
  }

  public FScrollbar() {
    this(0, 0, 0, 0);
  }

  public FScrollbar(int max) {
    this(max, 0, 1, (max + 9) / 10);
  }

  public FScrollbar(int max, int jump) {
    this(max, 0, 1, jump);
  }

  @Override // DCourt.Components.FTools
  public void update(Graphics g) {
    paint(g);
  }

  public void paint(Graphics g) {
    drawSink(g);
    drawBar(g, this.head);
    drawBar(g, this.tail);
    drawBar(g, this.thumb);
    if (this.horz) {
      drawLeftArrow(g, this.head);
      drawRightArrow(g, this.tail);
      return;
    }
    drawUpArrow(g, this.head);
    drawDownArrow(g, this.tail);
  }

  public boolean mouseDown(Event e, int x, int y) {
    if (this.head.inside(x, y)) {
      setVal(this.iVal - this.iStep);
    } else if (this.tail.inside(x, y)) {
      setVal(this.iVal + this.iStep);
    } else if (this.thumb.inside(x, y)) {
      this.drag = true;
    } else if (!this.horz && y < this.thumb.y) {
      setVal(this.iVal - this.iJump);
    } else if (!this.horz || x >= this.thumb.x) {
      setVal(this.iVal + this.iJump);
    } else {
      setVal(this.iVal - this.iJump);
    }
    repaint();
    return false;
  }

  public boolean mouseUp(Event e, int x, int y) {
    mouseDrag(e, x, y);
    this.drag = false;
    repaint();
    return false;
  }

  public boolean mouseDrag(Event e, int x, int y) {
    int size;
    int pos;
    int num;
    if (!this.drag) {
      return false;
    }
    if (this.horz) {
      size = this.body.width - this.thumb.width;
      pos = (x - this.body.x) - (this.thumb.width / 2);
    } else {
      size = this.body.height - this.thumb.height;
      pos = (y - this.body.y) - (this.thumb.height / 2);
    }
    if (pos < 0) {
      pos = 0;
      num = 0;
    } else if (pos >= size) {
      num = this.iMax;
      pos = size;
    } else {
      num = ((this.iMax * pos) + (size / 2)) / size;
    }
    this.iVal = num;
    if (this.horz) {
      this.thumb.x = this.body.x + pos;
    } else {
      this.thumb.y = this.body.y + pos;
    }
    postEvent(new Event(this, 1001, (Object) null));
    repaint();
    return false;
  }

  public void reshape(Rectangle r) {
    super.reshape(r.x, r.y, r.width, r.height);
  }

  public void reshape(int x, int y, int w, int h) {
    super.reshape(x, y, w, h);
    fixBody();
    repaint();
  }

  void fixBody() {
    int sizeHead;
    int sizeHead2;
    Rectangle r = bounds();
    this.horz = r.width > r.height;
    if (this.horz) {
      int sizeThumb = r.width - (2 * r.height);
      if (sizeThumb < 5) {
        sizeThumb = 0;
        sizeHead2 = r.width / 2;
      } else {
        sizeHead2 = r.height;
        if (sizeThumb > r.height) {
          sizeThumb = r.height;
        }
      }
      this.head = new Rectangle(0, 0, sizeHead2, r.height);
      this.tail = new Rectangle(r.width - sizeHead2, 0, sizeHead2, r.height);
      this.body = new Rectangle(sizeHead2, 0, r.width - (2 * sizeHead2), r.height);
      this.thumb = new Rectangle(0, 0, sizeThumb, r.height);
      setVal(this.iVal);
      return;
    }
    int sizeThumb2 = r.height - (2 * r.width);
    if (sizeThumb2 < 5) {
      sizeThumb2 = 0;
      sizeHead = r.height / 2;
    } else {
      sizeHead = r.width;
      if (sizeThumb2 > r.width) {
        sizeThumb2 = r.width;
      }
    }
    this.head = new Rectangle(0, 0, r.width, sizeHead);
    this.tail = new Rectangle(0, r.height - sizeHead, r.width, sizeHead);
    this.body = new Rectangle(0, sizeHead, r.width, r.height - (2 * sizeHead));
    this.thumb = new Rectangle(0, 0, r.width, sizeThumb2);
    setVal(this.iVal);
  }

  public void setAll(int max, int val, int step, int jump) {
    this.iMax = max;
    this.iJump = jump > max ? max : jump;
    this.iStep = step > jump ? jump : step;
    setVal(val);
  }

  public void setMax(int newMax) {
    this.iMax = newMax;
    if (this.iMax < 0) {
      this.iMax = 0;
    }
    setJump((this.iMax + 9) / 10);
    setStep((this.iMax + 99) / 100);
    setVal(this.iVal);
  }

  public void setVal(int newVal) {
    Rectangle r = bounds();
    this.iVal = newVal;
    if (this.iVal < 0) {
      this.iVal = 0;
    }
    if (this.iVal > this.iMax) {
      this.iVal = this.iMax;
    }
    if (this.horz) {
      this.thumb.x =
          this.head.x
              + this.head.width
              + (this.iMax < 1
                  ? 0
                  : (this.iVal * ((r.width - (2 * this.head.width)) - this.thumb.width))
                      / this.iMax);
    } else {
      this.thumb.y =
          this.head.y
              + this.head.height
              + (this.iMax < 1
                  ? 0
                  : (this.iVal * ((r.height - (2 * this.head.height)) - this.thumb.height))
                      / this.iMax);
    }
  }

  public void setJump(int newJump) {
    this.iJump = newJump;
    if (this.iJump > this.iMax) {
      this.iJump = this.iMax;
    }
  }

  public void setStep(int newStep) {
    this.iStep = newStep;
    if (this.iStep > this.iMax) {
      this.iStep = this.iMax;
    }
    if (this.iStep > this.iJump) {
      this.iStep = this.iJump;
    }
  }

  public int getMax() {
    return this.iMax;
  }

  public int getVal() {
    return this.iVal;
  }

  public int getJump() {
    return this.iJump;
  }

  public int getStep() {
    return this.iStep;
  }

  public boolean getHorz() {
    return this.horz;
  }
}
