package DCourt;

import DCourt.Tools.Tools;
/* old Event system is broken */
import java.awt.Event;
import java.awt.Frame;
import java.awt.Insets;

/* new Event system */
import java.awt.AWTEvent;
import java.awt.event.WindowEvent;
/* https://docs.oracle.com/javase/8/docs/api/constant-values.html */




/* loaded from: DCourt.jar:DCourt/DCourtFrame.class */
public class DCourtFrame extends Frame {
    static final String config = "config";
    // static final String config = config;
    static final String cgibin = "cgibin";
    //  static final String cgibin = cgibin;
    static final String artpath = "artpath";
    // static final String artpath = artpath;

    public DCourtFrame(String msg) {
        super(msg);
    }

    public static void main(String[] args) {
        DCourtApplet app = new DCourtApplet();
        app.setInBrowser(false);
        DCourtFrame win = new DCourtFrame("FFI Presents: Dragon Court");
        win.show();
        Insets edge = win.insets();
        win.add("Center", app);
        win.resize(edge.left + edge.right + Tools.DEFAULT_WIDTH, edge.top + edge.bottom + Tools.DEFAULT_HEIGHT);
        app.reshape(edge.left, edge.top, (int) Tools.DEFAULT_WIDTH, (int) Tools.DEFAULT_HEIGHT);
        app.init();
        app.repaint();
    }

    public void processEvent(AWTEvent e) {
        // System.out.println("processEvent " + e.getID() + " " + e.toString());
        switch (e.getID()) {
            case WindowEvent.WINDOW_CLOSING:
                dispose();
                System.exit(0);
                return; // true;
            case WindowEvent.WINDOW_CLOSED:
            case WindowEvent.WINDOW_ICONIFIED:
            case WindowEvent.WINDOW_DEICONIFIED:
            case WindowEvent.WINDOW_ACTIVATED:
                repaint();
                break;
        }
        super.processEvent(e);
    }

    public boolean handleEvent(Event e) {
        switch (e.id) {
            case 201:
                dispose();
                System.exit(0);
                return true;
            case 202:
            case 203:
            case 204:
            case 205:
                repaint();
                break;
        }
        return super.handleEvent(e);
    }
}
