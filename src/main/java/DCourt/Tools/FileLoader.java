package DCourt.Tools;

import DCourt.Items.Item;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: DCourt.jar:DCourt/Tools/Loader.class */
public class FileLoader extends Loader {
  static final String HEROFILE = "hero.txt";

  public static Buffer cgiBuffer(String action, String data) {
    return new Buffer(cgi(action, data));
  }

  public static Item cgiItem(String action, String data) {
    return Item.factory(cgi(action, data));
  }

  public static String cgi(String action, String data) {
    switch (action) {
      case FINDHERO:
        return "2023-07-27|0||";
      case SAVEHERO:
        try {
          FileOutputStream output = new FileOutputStream(HEROFILE);
          byte[] strToBytes = data.getBytes();
          output.write(strToBytes);
          output.close();
          return "";
        } catch (IOException e) {
          return "Error: " + e.getMessage();
        }
      case READHERO:
        File f = new File(HEROFILE);
        if (!f.exists()) {
          return "";
        }
        try {
          FileInputStream input = new FileInputStream(HEROFILE);
          byte[] Bytes = new byte[32768];
          input.read(Bytes);
          input.close();
          String s = new String(Bytes, "US-ASCII");
          return s;
        } catch (IOException e) {
          System.out.println(e.getMessage());
          return "Error: " + e.getMessage();
        } catch (Exception e) {
          System.out.println(e.getMessage());
          return "Error: " + e.getMessage();
        }
    }
    return "Error: " + action + " not implemented";
  }
}
