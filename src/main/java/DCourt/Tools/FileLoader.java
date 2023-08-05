package DCourt.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* loaded from: DCourt.jar:DCourt/Tools/Loader.class */
public class FileLoader extends Loader {

  public static Buffer cgiBuffer(String action, String data) {
    return new Buffer(cgi(action, data));
  }

  private static String getToday() {
    LocalDate d = LocalDate.now();
    return d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  public static Buffer loadHero(String name) {
    File f = new File(name);
    if (!f.exists()) {
      System.out.println("Hero " + name + " file not found");
      return new Buffer("");
    }
    try {
      FileInputStream input = new FileInputStream(name);
      byte[] Bytes = new byte[input.available()];
      input.read(Bytes);
      input.close();
      String s = new String(Bytes, "US-ASCII");
      return new Buffer(s);

    } catch (IOException e) {
      System.out.println("Failed to load Hero " + name + ": " + e.getMessage());
      return new Buffer("Error: " + e.getMessage());

    } catch (Exception e) {
      System.out.println("Failed to load Hero " + name + ": " + e.getMessage());
      return new Buffer("Error: " + e.getMessage());
    }
  }

  public static Buffer saveHero(String name, String data) {
    try {
      FileOutputStream output = new FileOutputStream(name);
      output.write(data.getBytes());
      output.close();
      return new Buffer("");
    } catch (IOException e) {
      System.out.println("Failed to save Hero " + name + ": " + e.getMessage());
      return new Buffer("Error: " + e.getMessage());
    }
  }

  public static String cgi(String action, String data) {
    switch (action) {
      case FINDHERO:
        System.out.println("FIND HERO");
        return getToday() + "|0||";
    }
    System.out.println(action + " not implemented");
    return "Error: " + action + " not implemented";
  }
}
