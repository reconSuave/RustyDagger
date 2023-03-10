package DCourt.Tools;

import DCourt.Items.Item;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: DCourt.jar:DCourt/Tools/Loader.class */
public class Loader {
    String text = new String();
    static final String EXECFILE = EXECFILE;
    static final String EXECFILE = EXECFILE;
    public static final String FINDHERO = FINDHERO;
    public static final String FINDHERO = FINDHERO;
    public static final String SAVEHERO = SAVEHERO;
    public static final String SAVEHERO = SAVEHERO;
    public static final String READHERO = READHERO;
    public static final String READHERO = READHERO;
    public static final String READRANK = READRANK;
    public static final String READRANK = READRANK;
    public static final String SAVESCORE = SAVESCORE;
    public static final String SAVESCORE = SAVESCORE;
    public static final String SENDMAIL = SENDMAIL;
    public static final String SENDMAIL = SENDMAIL;
    public static final String TAKEMAIL = TAKEMAIL;
    public static final String TAKEMAIL = TAKEMAIL;
    public static final String LISTMAIL = LISTMAIL;
    public static final String LISTMAIL = LISTMAIL;
    public static final String MESSAGE = MESSAGE;
    public static final String MESSAGE = MESSAGE;
    public static final String PEEKCLAN = PEEKCLAN;
    public static final String PEEKCLAN = PEEKCLAN;
    public static final String MAKECLAN = MAKECLAN;
    public static final String MAKECLAN = MAKECLAN;
    public static final String KILLCLAN = KILLCLAN;
    public static final String KILLCLAN = KILLCLAN;

    public static Buffer cgiBuffer(String action, String data) {
        return new Buffer(cgi(action, data));
    }

    public static Item cgiItem(String action, String data) {
        return Item.factory(cgi(action, data));
    }

    public static String cgi(String action, String data) {
        try {
            return operate(new URL(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(Tools.getCgibin()))).append("/").append(EXECFILE).append("?").append("cfg=").append(Tools.getConfig()).append("&act=").append(action)))), data).trim();
        } catch (MalformedURLException ex) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("Loader Exception: [").append(ex).append("]"))));
            return "";
        }
    }

    public String getText() {
        return this.text;
    }

    public boolean isError() {
        return this.text.startsWith("Error:");
    }

    static String operate(URL path, String send) {
        try {
            URLConnection con = path.openConnection();
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("content-type", "text/plain");
            con.setDoOutput(true);
            byte[] buf = getBytes(send);
            con.setRequestProperty("content-length", "".concat(String.valueOf(String.valueOf(buf.length))));
            try {
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.write(buf);
                out.flush();
                out.close();
                int size = con.getContentLength();
                if (size < 0 && Tools.getJvmVersion() > 0) {
                    return "";
                }
                try {
                    DataInputStream in = new DataInputStream(con.getInputStream());
                    return decrypt(size >= 0 ? newLoad(in, size) : oldLoad(in));
                } catch (Exception ex) {
                    System.err.println(String.valueOf(String.valueOf(new StringBuffer("Loader Exception [").append(ex).append("]"))));
                    ex.printStackTrace();
                    return "Loader.read() Exception: ".concat(String.valueOf(String.valueOf(ex)));
                }
            } catch (Exception ex2) {
                System.err.println(String.valueOf(String.valueOf(new StringBuffer("Loader Exception [").append(ex2).append("]"))));
                ex2.printStackTrace();
                return "Loader.send() Exception: ".concat(String.valueOf(String.valueOf(ex2)));
            }
        } catch (Exception ex3) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("Loader Exception [").append(ex3).append("]"))));
            ex3.printStackTrace();
            return "Loader.open() Exception: ".concat(String.valueOf(String.valueOf(ex3)));
        }
    }

    static byte[] getBytes(String msg) {
        if (msg == null) {
            return new byte[0];
        }
        String msg2 = encrypt(msg);
        if (Tools.getJvmVersion() > 0) {
            return msg2.getBytes();
        }
        byte[] buf = new byte[msg2.length()];
        msg2.getBytes(0, buf.length, buf, 0);
        return buf;
    }

    static String newLoad(DataInputStream in, int size) throws IOException {
        byte[] buf = new byte[size];
        in.readFully(buf);
        in.close();
        if (Tools.getJvmVersion() > 0) {
            return new String(buf);
        }
        String msg = "";
        for (byte b : buf) {
            msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf((char) b)));
        }
        return msg;
    }

    static String oldLoad(DataInputStream in) throws IOException {
        int cut;
        String msg = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                break;
            }
            msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(line)).concat("\n"))));
        }
        if (Tools.getJvmVersion() < 1 && (cut = msg.indexOf("\n\n")) >= 0) {
            msg = msg.substring(cut + 2);
        }
        return msg;
    }

    static String encrypt(String from) {
        String result = "";
        int size = from.length();
        int dx = 0;
        for (int ix = 0; ix < size; ix++) {
            int temp = (from.charAt(ix) + 128) & 127;
            if (temp < 32) {
                result = String.valueOf(String.valueOf(result)).concat(String.valueOf(String.valueOf((char) temp)));
            } else {
                result = String.valueOf(String.valueOf(result)).concat(String.valueOf(String.valueOf((char) ((((temp - 32) + ((size + dx) % 96)) % 96) + 32))));
                dx++;
            }
        }
        return result;
    }

    static String decrypt(String from) {
        String result = "";
        int size = from.length();
        int dx = 0;
        for (int ix = 0; ix < size; ix++) {
            int temp = (from.charAt(ix) + 128) & 127;
            if (temp < 32) {
                result = String.valueOf(String.valueOf(result)).concat(String.valueOf(String.valueOf((char) temp)));
            } else {
                result = String.valueOf(String.valueOf(result)).concat(String.valueOf(String.valueOf((char) (((((temp - 32) + 96) - ((size + dx) % 96)) % 96) + 32))));
                dx++;
            }
        }
        return result;
    }
}
