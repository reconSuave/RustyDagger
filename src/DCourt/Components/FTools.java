package DCourt.Components;

import DCourt.Tools.StaticLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Rectangle;

/* loaded from: DCourt.jar:DCourt/Components/FTools.class */
public class FTools extends Panel {
  static final Font textF = new Font("TimesRoman", 0, 14);
  Color fill = new Color(192, 192, 192);
  Color glow = new Color(224, 224, 224);
  Color dull = new Color(128, 128, 128);
  Color dark = new Color(96, 96, 96);
  FontMetrics fmet = null;

  public FTools() {
    setLayout(new StaticLayout());
    setFont(textF);
    setForeground(Color.black);
  }

  public FTools(Font f) {
    setLayout(new StaticLayout());
    setFont(f);
    setForeground(Color.black);
  }

  public void setFill(Color fc) {
    if (fc != null) {
      this.fill = fc;
      int r = fc.getRed();
      int g = fc.getGreen();
      int b = fc.getBlue();
      this.dull = new Color((r * 2) / 3, (g * 2) / 3, (b * 2) / 3);
      this.dark = new Color(r / 2, g / 2, b / 2);
      this.glow = new Color((256 + r) / 2, (256 + g) / 2, (256 + b) / 2);
    }
  }

  public void update(Graphics g) {
    paint(g);
  }

  public void setFont(Font f) {
    super.setFont(f);
    this.fmet = getFontMetrics(f);
  }

  public boolean handleEvent(Event e) {
    if (e.target != this) {
      return false;
    }
    switch (e.id) {
      case 501:
        return mouseDown(e, e.x, e.y);
      case 502:
        return mouseUp(e, e.x, e.y);
      case 503:
        return false;
      case 504:
      case 505:
      default:
        return false;
      case 506:
        return mouseDrag(e, e.x, e.y);
    }
  }

  public void drawSink(Graphics g) {
    drawSink(g, 0, 0, bounds().width, bounds().height);
  }

  void drawSink(Graphics g, Rectangle r) {
    drawSink(g, r.x, r.y, r.width, r.height);
  }

  void drawSink(Graphics g, int x, int y, int w, int h) {
    g.setColor(this.fill);
    g.fillRect(x, y, w, h);
    drawSinkBorder(g, x, y, w, h);
  }

  public void drawSinkBorder(Graphics g) {
    drawSinkBorder(g, 0, 0, bounds().width, bounds().height);
  }

  void drawSinkBorder(Graphics g, Rectangle r) {
    drawSinkBorder(g, r.x, r.y, r.width, r.height);
  }

  void drawSinkBorder(Graphics g, int x, int y, int w, int h) {
    int right = (x + w) - 1;
    int bottom = (y + h) - 1;
    if (right >= 0 && bottom >= 0) {
      g.setColor(this.fill);
      g.drawRect(x + 0, y + 0, w - 1, h - 1);
      g.drawRect(x + 1, y + 1, w - 3, h - 3);
      g.drawRect(x + 2, y + 2, w - 5, h - 5);
      g.setColor(this.glow);
      g.drawLine(right - 1, y + 1, right - 1, bottom - 1);
      g.drawLine(x + 1, bottom - 1, right - 1, bottom - 1);
      g.setColor(this.dull);
      g.drawLine(x + 1, y + 1, right, y + 1);
      g.drawLine(x + 1, y + 1, x + 1, bottom);
      g.setColor(this.dark);
      g.drawLine(x, y, right, y);
      g.drawLine(x, y, x, bottom);
    }
  }

  void drawBar(Graphics g) {
    drawBar(g, 0, 0, bounds().width, bounds().height);
  }

  public void drawBar(Graphics g, Rectangle r) {
    drawBar(g, r.x, r.y, r.width, r.height);
  }

  void drawBar(Graphics g, int x, int y, int w, int h) {
    g.setColor(this.fill);
    g.fillRect(x, y, w, h);
    drawBarBorder(g, x, y, w, h);
  }

  void drawBarBorder(Graphics g) {
    drawBarBorder(g, 0, 0, bounds().width, bounds().height);
  }

  void drawBarBorder(Graphics g, Rectangle r) {
    drawBarBorder(g, r.x, r.y, r.width, r.height);
  }

  void drawBarBorder(Graphics g, int x, int y, int w, int h) {
    int right = (x + w) - 1;
    int bottom = (y + h) - 1;
    if (right >= 0 && bottom >= 0) {
      g.setColor(this.glow);
      g.drawLine(x, y, right - 1, y);
      g.drawLine(x, y, x, bottom - 1);
      g.setColor(this.fill);
      g.drawLine(right - 1, y + 1, right - 1, bottom - 1);
      g.drawLine(x + 1, bottom - 1, right - 1, bottom - 1);
      g.setColor(this.dark);
      g.drawLine(right, y, right, bottom);
      g.drawLine(x, bottom, right, bottom);
    }
  }

  void drawUpArrow(Graphics g) {
    drawUpArrow(g, 0, 0, bounds().width, bounds().height);
  }

  public void drawUpArrow(Graphics g, Rectangle r) {
    drawUpArrow(g, r.x, r.y, r.width, r.height);
  }

  void drawUpArrow(Graphics g, int x, int y, int width, int height) {
    g.setColor(this.dark);
    int high8 = height / 8;
    int high4 = (height * 5) / 8;
    if (high4 != 0) {
      int wide3 = (width * 5) / 8;
      for (int v = 0; v < height; v++) {
        int val = v - high8;
        if (val > 0 && val <= high4) {
          int val2 = (wide3 * val) / high4;
          int wide2 = (width - val2) / 2;
          g.drawLine(x + wide2, y + v, ((x + wide2) + val2) - 1, y + v);
        }
      }
    }
  }

  void drawDownArrow(Graphics g) {
    drawDownArrow(g, 0, 0, bounds().width, bounds().height);
  }

  public void drawDownArrow(Graphics g, Rectangle r) {
    drawDownArrow(g, r.x, r.y, r.width, r.height);
  }

  void drawDownArrow(Graphics g, int x, int y, int width, int height) {
    int bottom = (y + height) - 1;
    g.setColor(this.dark);
    int high8 = height / 8;
    int high4 = (height * 5) / 8;
    if (high4 != 0) {
      int wide3 = (width * 5) / 8;
      for (int v = 0; v < height; v++) {
        int val = v - high8;
        if (val > 0 && val <= high4) {
          int val2 = (wide3 * val) / high4;
          int wide2 = (width - val2) / 2;
          g.drawLine(x + wide2, bottom - v, ((x + wide2) + val2) - 1, bottom - v);
        }
      }
    }
  }

  void drawLeftArrow(Graphics g) {
    drawLeftArrow(g, 0, 0, bounds().width, bounds().height);
  }

  public void drawLeftArrow(Graphics g, Rectangle r) {
    drawLeftArrow(g, r.x, r.y, r.width, r.height);
  }

  void drawLeftArrow(Graphics g, int x, int y, int width, int height) {
    g.setColor(this.dark);
    int wide8 = width / 8;
    int wide4 = (width * 5) / 8;
    if (wide4 != 0) {
      int high3 = (height * 5) / 8;
      for (int h = 0; h < width; h++) {
        int val = h - wide8;
        if (val > 0 && val <= wide4) {
          int val2 = (high3 * val) / wide4;
          int high2 = (height - val2) / 2;
          g.drawLine(x + h, y + high2, x + h, ((y + high2) + val2) - 1);
        }
      }
    }
  }

  void drawRightArrow(Graphics g) {
    drawRightArrow(g, 0, 0, bounds().width, bounds().height);
  }

  public void drawRightArrow(Graphics g, Rectangle r) {
    drawRightArrow(g, r.x, r.y, r.width, r.height);
  }

  void drawRightArrow(Graphics g, int x, int y, int width, int height) {
    int right = x + width;
    g.setColor(this.dark);
    int wide8 = width / 8;
    int wide4 = (width * 5) / 8;
    if (wide4 != 0) {
      int high3 = (height * 5) / 8;
      for (int h = 0; h < width; h++) {
        int val = h - wide8;
        if (val > 0 && val <= wide4) {
          int val2 = (high3 * val) / wide4;
          int high2 = (height - val2) / 2;
          g.drawLine(right - h, y + high2, right - h, ((y + high2) + val2) - 1);
        }
      }
    }
  }
}
