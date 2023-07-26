package DCourt.Tools;

/* loaded from: DCourt.jar:DCourt/Tools/Buffer.class */
public class Buffer {
  String text;
  int index;
  int mark;
  int size;
  static final char OPEN_SYMBOL = '{';
  static final char DIVIDE_SYMBOL = '|';
  static final char CLOSE_SYMBOL = '}';

  public Buffer() {
    this.text = "";
    this.size = 0;
    this.mark = 0;
    this.index = 0;
  }

  public Buffer(String s) {
    this.size = s.length();
    this.text = new String(s);
    this.mark = 0;
    this.index = 0;
  }

  public void reset() {
    this.mark = 0;
    this.index = 0;
  }

  public String toString() {
    return this.text;
  }

  public String toString(int sx) {
    return this.text.substring(sx);
  }

  public String toString(int sx, int ex) {
    return this.text.substring(sx, ex);
  }

  public String peek() {
    return toString(this.index);
  }

  public String peek(int num) {
    int max = this.index + num;
    if (max >= this.size) {
      max = this.size;
    }
    return toString(this.index, max);
  }

  private char charAt(int ix) {
    if (ix < 0 || ix >= this.size) {
      return 0;
    }
    return this.text.charAt(ix);
  }

  public int length() {
    return this.size;
  }

  public void length(int len) {
    if (len > 0 && len < this.size) {
      this.size = len;
    }
  }

  public boolean isDone() {
    return this.index >= this.size;
  }

  public int index() {
    return this.index;
  }

  public void advance(int val) {
    this.index += val;
  }

  public int space() {
    return this.size - this.index;
  }

  public void set(int ix) {
    this.index = ix;
  }

  public char getChar(int ix) {
    if (ix < 0 || ix >= this.size) {
      return 0;
    }
    return charAt(ix);
  }

  public char getChar() {
    if (this.index >= this.size) {
      return 0;
    }
    int i = this.index;
    this.index = i + 1;
    return charAt(i);
  }

  public boolean isError() {
    if (this.text.length() < 6) {
      return false;
    }
    return this.text.substring(0, 6).equalsIgnoreCase("ERROR:");
  }

  public boolean begin() {
    return getOpen();
  }

  public boolean end() {
    return getClose();
  }

  public boolean split() {
    return getDivide();
  }

  public void trim() {
    skipWhite();
  }

  public String token() {
    return getToken();
  }

  public int num() {
    return getNumber();
  }

  public String line() {
    return getLine();
  }

  public boolean match(String val) {
    if (val == null) {
      return false;
    }
    setMark();
    if (val.equals(getToken())) {
      return true;
    }
    goMark();
    return false;
  }

  public void skipWhite() {
    while (this.index < this.size && charAt(this.index) <= ' ') {
      this.index++;
    }
  }

  public boolean getOpen() {
    skipWhite();
    if (charAt(this.index) != OPEN_SYMBOL) {
      return false;
    }
    this.index++;
    return true;
  }

  public boolean getDivide() {
    skipWhite();
    if (charAt(this.index) != DIVIDE_SYMBOL) {
      return false;
    }
    this.index++;
    return true;
  }

  public boolean getClose() {
    skipWhite();
    if (charAt(this.index) != CLOSE_SYMBOL) {
      return false;
    }
    this.index++;
    return true;
  }

  public void setMark() {
    this.mark = this.index;
  }

  public void goMark() {
    this.index = this.mark;
  }

  public String getToken() {
    skipWhite();
    if (this.index >= this.size) {
      return "";
    }
    int i = this.index;
    int start = this.index;
    char c;
    while (this.index < this.size
        && (c = charAt(this.index)) != OPEN_SYMBOL
        && c != DIVIDE_SYMBOL
        && c != CLOSE_SYMBOL) {
      this.index++;
    }
    return toString(start, this.index).trim();
  }

  public int getNumber() {
    try {
      return Integer.parseInt(getToken());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public boolean isEmpty() {
    return this.size < 1;
  }

  public boolean startsWith(String s) {
    skipWhite();
    return this.text.startsWith(s, this.index);
  }

  public int indexOf(char c) {
    return this.text.indexOf(this.index, c);
  }

  public String getLine() {
    int ix = indexOf('\n');
    if (ix < 0) {
      ix = this.size;
    }
    String result = toString(this.index, ix);
    this.index = ix;
    return result.trim();
  }
}
