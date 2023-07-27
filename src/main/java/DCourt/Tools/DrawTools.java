package DCourt.Tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/* loaded from: DCourt.jar:DCourt/Tools/DrawTools.class */
public class DrawTools {
  public static Color white = Color.white;
  public static Color fill = new Color(192, 192, 192);
  public static Color glow = new Color(224, 224, 224);
  public static Color dull = new Color(128, 128, 128);
  public static Color dark = new Color(96, 96, 96);
  public static Color black = Color.black;

  public static void center(Graphics g, String msg, int x, int y) {
    g.drawString(msg, x - (g.getFontMetrics(g.getFont()).stringWidth(msg) / 2), y);
  }

  public static void right(Graphics g, String msg, int x, int y) {
    g.drawString(msg, x - g.getFontMetrics(g.getFont()).stringWidth(msg), y);
  }

  public static void sink(Graphics g, Color back, Rectangle r) {
    sink(g, back, r.x, r.y, r.width, r.height);
  }

  public static void sink(Graphics g, Color back, int x, int y, int w, int h) {
    g.setColor(back);
    g.fillRect(x, y, w, h);
    sinkBorder(g, back, x, y, w, h);
  }

  public static void sinkBorder(Graphics g, Color back, Rectangle r) {
    sinkBorder(g, back, r.x, r.y, r.width, r.height);
  }

  public static void sinkBorder(Graphics g, Color back, int x, int y, int w, int h) {
    int right = (x + w) - 1;
    int bottom = (y + h) - 1;
    if (right >= 0 && bottom >= 0) {
      g.setColor(back);
      g.drawRect(x + 0, y + 0, w - 1, h - 1);
      g.drawRect(x + 1, y + 1, w - 3, h - 3);
      g.drawRect(x + 2, y + 2, w - 5, h - 5);
      g.setColor(glow);
      g.drawLine(right - 1, y + 1, right - 1, bottom - 1);
      g.drawLine(x + 1, bottom - 1, right - 1, bottom - 1);
      g.setColor(fill);
      g.drawLine(right, y, right, bottom);
      g.drawLine(x, bottom, right, bottom);
      g.setColor(dull);
      g.drawLine(x + 1, y + 1, right, y + 1);
      g.drawLine(x + 1, y + 1, x + 1, bottom);
      g.setColor(dark);
      g.drawLine(x, y, right, y);
      g.drawLine(x, y, x, bottom);
    }
  }

  public static void bar(Graphics g, Color back, Rectangle r) {
    bar(g, back, r.x, r.y, r.width, r.height);
  }

  public static void bar(Graphics g, Color back, int x, int y, int w, int h) {
    g.setColor(back);
    g.fillRect(x, y, w, h);
    barBorder(g, back, x, y, w, h);
  }

  public static void barBorder(Graphics g, Color back, Rectangle r) {
    barBorder(g, back, r.x, r.y, r.width, r.height);
  }

  public static void barBorder(Graphics g, Color back, int x, int y, int w, int h) {
    int right = (x + w) - 1;
    int bottom = (y + h) - 1;
    if (right >= 0 && bottom >= 0) {
      g.setColor(glow);
      g.drawLine(x, y, right - 1, y);
      g.drawLine(x, y, x, bottom - 1);
      g.setColor(back);
      g.drawLine(right - 1, y + 1, right - 1, bottom - 1);
      g.drawLine(x + 1, bottom - 1, right - 1, bottom - 1);
      g.setColor(dark);
      g.drawLine(right, y, right, bottom);
      g.drawLine(x, bottom, right, bottom);
    }
  }

  public static void upArrow(Graphics g, Rectangle r) {
    upArrow(g, r.x, r.y, r.width, r.height);
  }

  public static void upArrow(Graphics g, int x, int y, int width, int height) {
    g.setColor(dark);
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

  public static void downArrow(Graphics g, Rectangle r) {
    downArrow(g, r.x, r.y, r.width, r.height);
  }

  public static void downArrow(Graphics g, int x, int y, int width, int height) {
    int bottom = (y + height) - 1;
    g.setColor(dark);
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

  public static void leftArrow(Graphics g, Rectangle r) {
    leftArrow(g, r.x, r.y, r.width, r.height);
  }

  public static void leftArrow(Graphics g, int x, int y, int width, int height) {
    g.setColor(dark);
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

  public static void rightArrow(Graphics g, Rectangle r) {
    rightArrow(g, r.x, r.y, r.width, r.height);
  }

  public static void rightArrow(Graphics g, int x, int y, int width, int height) {
    int right = x + width;
    g.setColor(dark);
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
