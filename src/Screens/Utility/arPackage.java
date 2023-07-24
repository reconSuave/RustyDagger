package DCourt.Screens.Utility;

import DCourt.Components.FTextField;
import DCourt.Items.List.itHero;
import DCourt.Items.itList;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Transfer;
import DCourt.Static.GameStrings;
import DCourt.Tools.Buffer;
import DCourt.Tools.Loader;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Utility/arPackage.class */
public class arPackage extends Transfer {
    Button send;
    FTextField name;
    String[] breaksound = {"***KEERASH***", "***SMASHOLA***", "***BANG+CRACK+POP***", "+++SHLORP-bump+++", "***CRASH***...tinkle...", "HEE-HAW! HEE HAW!", "***KABADABOOM***", "...bzzzzzzzzzzzz...", "AIYEEEE!!!!"};
    static final String mailSent = "mailSent";
    // static final String mailSent = mailSent;

    public arPackage(Screen from) {
        super(from, String.valueOf(String.valueOf(from.getTitle())).concat(" Mail Room"));
        setBackground(new Color(0, 0, 128));
        setForeground(Color.white);
        hideStatusBar();
        setValues(0, Screen.getPack(), new itList("stash"));
    }

    @Override // DCourt.Screens.Template.Transfer
    public void goHome() {
        Screen.getPack().merge(getStash());
        clrStash();
        goHome();
    }

    @Override // DCourt.Screens.Screen
    public void localPaint(Graphics g) {
        localPaint(g);
        updateTools();
        g.setFont(Tools.textF);
        g.setColor(getForeground());
        g.drawString("Send To:", 10, 285);
        g.setFont(Tools.statusF);
        g.drawString("Backpack ".concat(String.valueOf(String.valueOf(Screen.packCount()))), 30, 65);
        g.drawString("Mail Package ".concat(String.valueOf(String.valueOf(stashCount()))), 230, 65);
    }

    @Override // DCourt.Screens.Template.Transfer, DCourt.Screens.Screen
    public boolean action(Event e, Object o) {
        if (Tools.movedAway(this)) {
            return true;
        }
        if (e.target == this.send) {
            Tools.setRegion(sendPackage());
        }
        return action(e, o);
    }

    @Override // DCourt.Screens.Template.Transfer, DCourt.Screens.Screen
    public void createTools() {
        createTools();
        this.send = new Button("Send $0");
        this.send.reshape(275, 272, 80, 20);
        this.send.setFont(Tools.textF);
        this.name = new FTextField(15);
        this.name.reshape(65, 270, 200, 22);
        updateTools();
    }

    @Override // DCourt.Screens.Template.Transfer, DCourt.Screens.Screen
    public void addTools() {
        addTools();
        add(this.send);
        add(this.name);
    }

    @Override // DCourt.Screens.Template.Transfer
    public void updateTools() {
        int count = stashCount();
        this.send.setLabel("Send $".concat(String.valueOf(String.valueOf(count * 100))));
        this.send.enable(count > 0 && Screen.getMoney() >= count * 100);
        updateTools();
    }

    Screen sendPackage() {
        itHero h = Screen.getHero();
        String dest = this.name.getText();
        if (dest.length() < 4 || dest.length() > 15) {
            return new arNotice(this, String.valueOf(String.valueOf(new StringBuffer("\tThe name you have selected is malformed:\n<").append(dest).append(">\n\n").append("\tHero names must be at least 4 letters and no ").append("more than 15 letters\n"))));
        }
        if (Screen.getHero().isMatch(dest)) {
            return new arNotice(this, "\tWhat is the point of sending mail to yourself?\n\n\tIt poses a metaphysical conundrum, and lends the suggestion that you are insane.\n\n<<Why'd I go and say a fool thing like that?>>\n");
        }
        int count = stashCount();
        h.subMoney(count * 100);
        if (!Screen.saveHero()) {
            h.addMoney(count * 100);
            return new arNotice(getHome(), GameStrings.SAVE_CANCEL);
        }
        String result = send(String.valueOf(String.valueOf(h.getTitle())).concat(String.valueOf(String.valueOf(h.getName()))), dest, getStash());
        if (result != null) {
            Screen.addMoney(count * 100);
            return new arNotice(this, GameStrings.MAIL_CANCEL.concat(String.valueOf(String.valueOf(result))));
        }
        MadLib sent = new MadLib(mailSent);
        sent.replace("$crash$", Tools.select(this.breaksound));
        return new arNotice(getHome(), sent.getText());
    }

    public static String send(String source, String dest, itList mail) {
        Buffer pkg = new Buffer(mail.toString());
        String msg = Tools.getToday();
        Buffer buf = Loader.cgiBuffer(Loader.SENDMAIL, String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(Screen.getHero().getName()))).append("|").append(Screen.getPlayer().getSessionID()).append("|").append(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(msg.substring(0, msg.lastIndexOf(47)))).concat(String.valueOf(String.valueOf(" Package <== ".concat(String.valueOf(String.valueOf(source)))))))).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("|").append(dest).append("\n")))))))).concat(String.valueOf(String.valueOf(pkg.toString())))))));
        if (buf.isError()) {
            return buf.peek();
        }
        return null;
    }
}
