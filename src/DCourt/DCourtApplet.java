package DCourt;

import DCourt.Screens.Command.arLoading;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Tools.StaticLayout;
import DCourt.Tools.Tools;
import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.net.URL;

/* loaded from: DCourt.jar:DCourt/DCourtApplet.class */
public class DCourtApplet extends Applet {
    private String artpath;
    private String config;
    private String cgibin;
    private String today;
    private Tools tools;
    static final String badAccess = "badAccess";
    private boolean inBrowser = true;
    private boolean playtest = false;
    Screen region = null;

    public Dimension prefferedSize() {
        return new Dimension((int) Tools.DEFAULT_WIDTH, (int) Tools.DEFAULT_HEIGHT);
    }

    public void init() {
        System.out.println("Dragon Court version 1.2");
        setLayout(new StaticLayout());
        if (this.inBrowser) {
            String host = getCodeBase().getHost();
            this.config = getParameter("CONFIG");
            this.cgibin = String.valueOf(String.valueOf(new StringBuffer("http://").append(host).append(getParameter("CGIBIN"))));
            this.artpath = String.valueOf(String.valueOf(new StringBuffer("http://").append(host).append(getParameter("ARTPATH"))));
        } else {
            this.config = "DCourt";
            this.cgibin = "http://205.238.11.118/cgibin";
            this.artpath = "Images";
        }
        this.playtest = this.config.equalsIgnoreCase("DCourtWork");
        this.tools = new Tools(this);
        if (pirateTest()) {
            setRegion(new arNotice(null, badAccess));
        } else {
            setRegion(new arLoading(this, this.tools));
        }
    }

    public String getArtpath() {
        return this.artpath;
    }

    public String getConfig() {
        return this.config;
    }

    public String getCgibin() {
        return this.cgibin;
    }

    public String getToday() {
        return this.today;
    }

    public boolean isInBrowser() {
        return this.inBrowser;
    }

    public void setInBrowser(boolean val) {
        this.inBrowser = val;
    }

    public boolean isPlaytest() {
        return this.playtest;
    }

    public void setPlaytest(boolean val) {
        this.playtest = val;
    }

    public Screen getRegion() {
        return this.region;
    }

    public void setRegion(Screen next) {
        if (next != null) {
            enable(false);
            removeAll();
            this.region = next;
            this.region.init();
            add(this.region);
            enable(true);
            this.region.repaint();
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    boolean pirateTest() {
        if (!this.inBrowser) {
            return false;
        }
        URL doc = getDocumentBase();
        URL code = getCodeBase();
        if (doc == null || code == null) {
            return true;
        }
        String chost = code.getHost();
        String dhost = doc.getHost();
        return chost == null || dhost == null || !dhost.equalsIgnoreCase(chost);
    }
}
