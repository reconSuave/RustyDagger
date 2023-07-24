package DCourt.Screens;

import DCourt.Components.Portrait;
import DCourt.Control.GearTable;
import DCourt.Control.MonsterTable;
import DCourt.Control.Player;
import DCourt.Items.Item;
import DCourt.Items.List.itArms;
import DCourt.Items.List.itHero;
import DCourt.Items.List.itMonster;
import DCourt.Items.itList;
import DCourt.Screens.Utility.arStatus;
import DCourt.Static.ArmsTrait;
import DCourt.Static.GameStrings;
import DCourt.Tools.StaticLayout;
import DCourt.Tools.Tools;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.util.Vector;

/* loaded from: DCourt.jar:DCourt/Screens/Screen.class */
public abstract class Screen extends Panel implements GameStrings, ArmsTrait {
    private static Image offscreen = null;
    private String title;
    private boolean status;
    private Screen home;
    private Vector pics;

    public Screen() {
        this(null, "Default Screen");
    }

    public Screen(Screen from) {
        this(from, "Default Screen");
    }

    public Screen(String name) {
        this(null, name);
    }

    public Screen(Screen from, String name) {
        setLayout(new StaticLayout());
        this.home = from;
        this.status = true;
        this.title = name;
        this.pics = null;
        createTools();
        reshape(0, 0, (int) Tools.DEFAULT_WIDTH, (int) Tools.DEFAULT_HEIGHT);
        setFont(Tools.courtF);
    }

    public String toString() {
        return this.title;
    }

    public void init() {
        removeAll();
        addTools();
        if (this.pics != null) {
            for (int ix = 0; ix < this.pics.size(); ix++) {
                add((Portrait) this.pics.elementAt(ix));
            }
        }
        if (this.status) {
            add(Tools.statusPic);
        }
    }

    public void questInit() {
        getHero().tryToLevel(this);
    }

    public void repaint() {
        super.repaint();
        if (this.status) {
            Tools.statusPic.repaint();
        }
        if (Tools.getJvmVersion() >= 2) {
            validate();
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        if (offscreen == null) {
            offscreen = createImage((int) Tools.DEFAULT_WIDTH, (int) Tools.DEFAULT_HEIGHT);
        }
        Graphics og = offscreen.getGraphics();
        og.setColor(getBackground());
        og.fillRect(0, 0, bounds().width, bounds().height);
        localPaint(og);
        g.drawImage(offscreen, 0, 0, this);
        paintComponents(g);
    }

    public void localPaint(Graphics g) {
        g.setFont(Tools.courtF);
        g.setColor(getForeground());
        g.drawString(this.title, 10, 20);
    }

    public static Player getPlayer() {
        return Tools.getPlayer();
    }

    public static Screen tryToExit(Screen where, String loc, int cost) {
        return getPlayer().tryToExit(where, loc, cost);
    }

    public static int getSessionID() {
        return getPlayer().getSessionID();
    }

    public static String getBest() {
        return getPlayer().getBest();
    }

    public static String getLeader() {
        return getPlayer().getLeader();
    }

    public static String getState() {
        return getHero().getState();
    }

    public static void setState(String val) {
        getHero().setState(val);
    }

    public static boolean saveHero() {
        return getPlayer().saveHero();
    }

    public static itHero getHero() {
        return Tools.getHero();
    }

    public static int getGuts() {
        return getHero().getGuts();
    }

    public static int getWits() {
        return getHero().getWits();
    }

    public static int getCharm() {
        return getHero().getCharm();
    }

    public static int getQuests() {
        return getHero().getQuests();
    }

    public static int getLevel() {
        return getHero().getLevel();
    }

    public static int getSocial() {
        return getHero().getSocial();
    }

    public static String getPlace() {
        return getHero().getPlace();
    }

    public static void setPlace(String val) {
        getHero().setPlace(val);
    }

    public static itList getActions() {
        return getHero().getActions();
    }

    public static itList getGear() {
        return getHero().getGear();
    }

    public static itList getStatus() {
        return getHero().getStatus();
    }

    public static itList getPack() {
        return getHero().getPack();
    }

    public static itList getStore() {
        return getHero().getStore();
    }

    public static int packCount() {
        return getHero().packCount();
    }

    public static int packCount(Item it) {
        return packCount(it.getName());
    }

    public static int packCount(String id) {
        return getHero().packCount(id);
    }

    public static boolean hasTrait(String val) {
        return getHero().hasTrait(val);
    }

    public static itArms findGearTrait(String val) {
        return getHero().findGearTrait(val);
    }

    public static int getMoney() {
        return getHero().getMoney();
    }

    public static int addMoney(int val) {
        return getHero().addMoney(val);
    }

    public static int subMoney(int val) {
        return getHero().subMoney(val);
    }

    public static int addFatigue(int val) {
        return getHero().addFatigue(val);
    }

    public static int subFatigue(int val) {
        return getHero().subFatigue(val);
    }

    public static int addPack(String id, int num) {
        if (!GearTable.find(id)) {
            return 0;
        }
        return getHero().addPack(id, num);
    }

    public static void addPack(Item it) {
        if (GearTable.find(it)) {
            getHero().addPack(it);
        }
    }

    public static void putPack(Item it) {
        if (GearTable.find(it)) {
            getHero().putPack(it);
        }
    }

    public static int subPack(String id, int num) {
        return getHero().subPack(id, num);
    }

    public static void subPack(Item it) {
        getHero().subPack(it);
    }

    public static Item selectPack(int ix) {
        return getHero().selectPack(ix);
    }

    public static int firstPack(String id) {
        return getHero().firstPack(id);
    }

    public static int indexPack(Item it) {
        return getHero().indexPack(it);
    }

    public void hideStatusBar() {
        this.status = false;
    }

    public static itMonster findBeast(String key) {
        return MonsterTable.find(key);
    }

    public String getTitle() {
        return this.title;
    }

    public Screen getHome() {
        return this.home;
    }

    public void setHome(Screen next) {
        this.home = next;
    }

    public void addPic(Portrait pic) {
        if (this.pics == null) {
            this.pics = new Vector();
        }
        this.pics.addElement(pic);
    }

    public Portrait getPic(int ix) {
        if (this.pics == null || ix < 0 || ix >= this.pics.size()) {
            return null;
        }
        return (Portrait) this.pics.elementAt(ix);
    }

    public int getPic(Object obj) {
        for (int ix = 0; ix < this.pics.size(); ix++) {
            if (getPic(ix) == obj) {
                return ix;
            }
        }
        return -1;
    }

    public void createTools() {
    }

    public void addTools() {
    }

    public boolean action(Event e, Object o) {
        System.out.println("Screen::action");
        if (e.target != Tools.statusPic) {
            return true;
        }
        Tools.setRegion(new arStatus(this));
        return true;
    }

    public boolean mouseDown(Event e, int x, int y) {
        Screen next = down(x, y);
        if (next == null) {
            postEvent(new Event(this, 1001, (Object) null));
            return true;
        }
        Tools.setRegion(next);
        return true;
    }

    public Screen down(int x, int y) {
        return null;
    }

    public void updatePack() {
    }

    public static String packString(String entry, itList list) {
        String msg = String.valueOf(String.valueOf(entry)).concat("\n");
        if (list.getCount() < 1) {
            return "";
        }
        for (int ix = 0; ix < list.getCount(); ix++) {
            msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("     ").append(list.select(ix).toLoot()).append("\n"))))));
        }
        return msg;
    }
}
