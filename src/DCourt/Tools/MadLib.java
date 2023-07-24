package DCourt.Tools;

import java.util.Hashtable;

/* loaded from: DCourt.jar:DCourt/Tools/MadLib.class */
public class MadLib {
  String[][] gender;
  Hashtable table;
  String text;

  public MadLib(String msg) {
    this.gender =
        new String[][] {
          new String[] {"$HE$", "he", "she"},
          new String[] {"$HIM$", "him", "her"},
          new String[] {"$HIS$", "his", "her"},
          new String[] {"$MAN$", "man", "woman"},
          new String[] {"$BOY$", "boy", "girl"}
        };
    this.table = new Hashtable();
    this.text = "";
    append(msg);
    replace("$CR$", "\n");
    replace("$TB$", "\t");
    replace("$$", "$");
  }

  public MadLib(MadLib what) {
    this.gender =
        new String[][] {
          new String[] {"$HE$", "he", "she"},
          new String[] {"$HIM$", "him", "her"},
          new String[] {"$HIS$", "his", "her"},
          new String[] {"$MAN$", "man", "woman"},
          new String[] {"$BOY$", "boy", "girl"}
        };
    this.table = new Hashtable();
    this.text = "";
    this.table = (Hashtable) what.table.clone();
    this.text = new String(what.text);
  }

  public Object clone() {
    return new MadLib(this);
  }

  public String getReplace(String key) {
    return (String) this.table.get(key);
  }

  public String getText() {
    return update(this.text);
  }

  public String getFinal() {
    this.text = update(this.text);
    return this.text;
  }

  public void replace(String key, String val) {
    this.table.put(key, val);
  }

  public void replace(String key, int num) {
    this.table.put(key, "".concat(String.valueOf(String.valueOf(num))));
  }

  public void append(String val) {
    if (this.text == null) {
      this.text = "";
    }
    this.text =
        String.valueOf(String.valueOf(this.text)).concat(String.valueOf(String.valueOf(val)));
  }

  public void genderize(boolean male) {
    int sex = male ? 1 : 2;
    for (int ix = 0; ix < this.gender.length; ix++) {
      replace(this.gender[ix][0], this.gender[ix][sex]);
    }
  }

  public void capitalize() {
    char[] work = this.text.toCharArray();
    int spaces = 0;
    for (int ix = 0; ix < this.text.length(); ix++) {
      char c = work[ix];
      if (c > ' ') {
        if (spaces > 1 && c >= 'a' && c <= 'z') {
          work[ix] = (char) ((c - 'a') + 65);
        }
        spaces = 0;
      } else {
        spaces++;
      }
    }
    this.text = new String(work);
  }

  String update(String from) {
    String result = "";
    int ix = 0;
    while (true) {
      int dx = from.indexOf(36, ix);
      if (dx < 0) {
        break;
      }
      result =
          String.valueOf(String.valueOf(result))
              .concat(String.valueOf(String.valueOf(from.substring(ix, dx))));
      ix = dx;
      int dx2 = from.indexOf(36, dx + 1);
      if (dx2 < 0) {
        break;
      }
      String sub = from.substring(ix, dx2 + 1);
      ix = dx2 + 1;
      String put = getReplace(sub);
      result =
          String.valueOf(String.valueOf(result))
              .concat(String.valueOf(String.valueOf(put == null ? sub : update(put))));
    }
    return String.valueOf(String.valueOf(result))
        .concat(String.valueOf(String.valueOf(from.substring(ix))));
  }
}
