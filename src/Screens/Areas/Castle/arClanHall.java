package DCourt.Screens.Areas.Castle;

import DCourt.Components.FTextField;
import DCourt.Items.Item;
import DCourt.Items.List.itAgent;
import DCourt.Items.List.itHero;
import DCourt.Items.List.itNote;
import DCourt.Items.Token.itValue;
import DCourt.Items.itList;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Indoors;
import DCourt.Screens.Utility.arNotice;
import DCourt.Screens.Utility.arPackage;
import DCourt.Screens.Utility.arPeer;
import DCourt.Static.Constants;
import DCourt.Static.GameStrings;
import DCourt.Tools.Buffer;
import DCourt.Tools.Loader;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Castle/arClanHall.class */
public class arClanHall extends Indoors implements Constants {
    FTextField clantext;
    String current;
    String leader;
    String ability;
    int members;
    int power;
    int petitionID;
    Button enact;
    Button peer;
    Button next;
    Button grant;
    Button deny;
    CheckboxGroup clanAction;
    Checkbox join;
    Checkbox quit;
    Checkbox member;
    Checkbox create;
    Checkbox confirm;
    boolean showPetitions;
    itValue petition;
    int heroStatus;
    int clanStatus;
    static final int JOIN_QUESTS = 1;
    static final int JOIN_COSTS = 2; //JOIN_COSTS;
    // static final int JOIN_COSTS = JOIN_COSTS;
    static final int QUIT_QUESTS = 5;
    static final int QUIT_COSTS = 6; //QUIT_COSTS;
    // static final int QUIT_COSTS = QUIT_COSTS;
    static final int DISBAND_QUESTS = 15;
    static final int DISBAND_COSTS = 16; //DISBAND_COSTS;
    // static final int DISBAND_COSTS = DISBAND_COSTS;
    static final int CREATE_QUESTS = 17; // CREATE_QUESTS;
    // static final int CREATE_QUESTS = CREATE_QUESTS;
    static final int CREATE_COSTS = 18; // CREATE_COSTS;
    // static final int CREATE_COSTS = CREATE_COSTS;
    static final int PRESELECT = 0;
    static final int CLANFOUND = 1;
    static final int CREATECLAN = 2;
    static final int CLANLESS = 1;
    static final int MEMBER = 2;
    static final int LEADER = 3;
    static String[] greeting = {null, "Yes, your Lordship?", "What are your wishes?", "How may I assist you?", "May I help you, sir?", "Leadership is a Burden", "Think carefully my lord", "Deliberation is a Virtue", "Be Wary of Usurpers"};
    static final String leaveClan = "leaveClan";
    // static final String leaveClan = leaveClan;
    static final String petitionMsg = "petitionMsg";
    // static final String petitionMsg = petitionMsg;
    static final String denyHead = "denyHead";
    // static final String denyHead = denyHead;
    static final String denyMsg = "denyMsg";
    // static final String denyMsg = denyMsg;
    static final String createMsg = "createMsg";
    // static final String createMsg = createMsg;
    static final String disbandMsg = "disbandMsg";
    // static final String disbandMsg = disbandMsg;
    static final String grantMsg = "grantMsg";
    // static final String grantMsg = grantMsg;
    static final String reinstateMsg = "reinstateMsg";
    // static final String reinstateMsg = reinstateMsg;
    static final String joinStr = "joinStr";
    // static final String joinStr = joinStr;
    static final String quitStr = "quitStr";
    // static final String quitStr = quitStr;
    static final String createStr = "createStr";
    // static final String createStr = createStr;
    static final String disbandStr = "disbandStr";
    // static final String disbandStr = disbandStr;
    static final String affirmStr = "affirmStr";
    // static final String affirmStr = affirmStr;

    public arClanHall(Screen from) {
        super(from, "Servile Krymps Clan Gathering");
        setBackground(new Color(255, 255, 128));
        setForeground(new Color(96, 96, 0));
        setFont(Tools.courtF);
    }

    @Override // DCourt.Screens.Template.Indoors
    public String getFace() {
        return "Faces/Serville.jpg";
    }

    @Override // DCourt.Screens.Template.Indoors
    public String getGreeting() {
        String msg = Tools.select(greeting);
        return msg == null ? String.valueOf(String.valueOf(Tools.getBest())).concat(" was just here") : msg;
    }

    @Override // DCourt.Screens.Screen
    public void init() {
        this.petitionID = -1;
        findNextPetition();
        this.showPetitions = this.heroStatus == 3 || this.petition != null;
        init();
    }

    @Override // DCourt.Screens.Screen
    public void localPaint(Graphics g) {
        itHero h = Screen.getHero();
        Checkbox act = this.clanAction.getCurrent();
        localPaint(g);
        g.setFont(Tools.textF);
        g.drawString(String.valueOf(String.valueOf(new StringBuffer(">>> ").append(h.getTitle()).append(h.getName()))), 180, 60);
        g.setFont(getFont());
        if (this.clanStatus == 0) {
            g.drawString("Enter a Clan Name", 180, 120);
        } else if (this.clanStatus == 2) {
            g.drawString("No Clan Found", 180, 120);
        } else {
            if (this.heroStatus != 1) {
                g.drawString("Clan: ".concat(String.valueOf(String.valueOf(this.current))), 180, 80);
            }
            g.drawString("Leader: ".concat(String.valueOf(String.valueOf(this.leader))), 180, 100);
            g.drawString(String.valueOf(String.valueOf(new StringBuffer("Men: ").append(this.members).append("  Power: ").append(this.power))), 180, 120);
            g.drawString("Abilities: ".concat(String.valueOf(String.valueOf(this.ability))), 180, 140);
        }
        if (act == this.create && h.getSocial() < 2) {
            g.drawString("Must be a Baron", 180, 200);
            g.drawString("to Create a Clan", 200, 220);
        }
        if (act == this.member) {
            g.setColor(new Color(255, 196, 196));
            g.fillRect(170, 150, 225, 50);
            g.setColor(getForeground());
            if (this.petition == null) {
                g.drawString("No Petitions Outstanding", 180, 170);
            } else {
                g.drawString("Petition from ".concat(String.valueOf(String.valueOf(this.petition.getValue()))), 180, 170);
            }
        }
    }

    @Override // DCourt.Screens.Template.Indoors, DCourt.Screens.Screen
    public boolean action(Event e, Object o) {
        if (Tools.movedAway(this)) {
            return true;
        }
        if (e.target == this.enact) {
            Checkbox act = this.clanAction.getCurrent();
            if (act == this.join) {
                Tools.setRegion(petitionClan());
            }
            if (act == this.create) {
                Tools.setRegion(createClan());
            }
            if (act == this.quit) {
                if (this.heroStatus == 3) {
                    Tools.setRegion(disbandClan());
                } else {
                    Tools.setRegion(quitClan());
                }
            }
        }
        if (e.target != this.confirm) {
            this.confirm.setState(false);
        }
        if (e.target == this.clantext) {
            findClanInfo(this.clantext.getText());
        }
        if (e.target == getPic(0)) {
            Tools.setRegion(getHome());
        }
        if (this.petition != null) {
            if (e.target == this.peer) {
                Tools.setRegion(new arPeer(this, 4, this.petition.getValue()));
            }
            if (e.target == this.next) {
                findNextPetition();
            }
            if (e.target == this.grant) {
                Tools.setRegion(grantPetition());
            }
            if (e.target == this.deny) {
                Tools.setRegion(denyPetition());
            }
        }
        updateTools();
        repaint();
        return action(e, o);
    }

    @Override // DCourt.Screens.Screen
    public void createTools() {
        itHero h = Screen.getHero();
        Color backC = new Color(255, 255, 128);
        findClanInfo(h.getClan());
        if (this.current == null) {
            this.heroStatus = 1;
        } else if (!h.isMatch(this.leader)) {
            this.heroStatus = 2;
        } else {
            this.heroStatus = 3;
        }
        this.enact = new Button();
        this.enact.reshape(170, 160, 220, 25);
        this.enact.setFont(Tools.statusF);
        this.enact.show(false);
        this.confirm = new Checkbox();
        this.confirm.reshape(180, 240, 200, 20);
        this.confirm.setFont(Tools.textF);
        this.confirm.show(false);
        if (this.heroStatus != 3) {
            this.clantext = new FTextField(h.getClan() == null ? Constants.NONE : h.getClan(), 40);
            this.clantext.reshape(180, 63, 200, 20);
            this.clantext.setFont(Tools.textF);
        }
        this.clanAction = new CheckboxGroup();
        this.member = null;
        this.create = null;
        this.quit = null;
        this.join = null;
        if (this.heroStatus == 3) {
            this.member = new Checkbox("Members", this.clanAction, true);
            this.member.reshape(200, 205, 80, 25);
            this.quit = new Checkbox("Quit", this.clanAction, false);
            this.quit.reshape(280, 205, 80, 25);
        }
        if (this.heroStatus == 2) {
            this.quit = new Checkbox("Quit", this.clanAction, true);
            this.quit.reshape(200, 205, 80, 25);
            this.join = new Checkbox("Join", this.clanAction, false);
            this.join.reshape(280, 205, 80, 25);
        }
        if (this.heroStatus == 1) {
            this.join = new Checkbox("Join", this.clanAction, true);
            this.join.reshape(200, 205, 80, 25);
            this.create = new Checkbox(itAgent.CREATE, this.clanAction, false);
            this.create.reshape(280, 205, 80, 25);
        }
        if (this.join != null) {
            this.join.setFont(Tools.textF);
            this.join.setBackground(backC);
            this.join.setForeground(Color.black);
        }
        if (this.quit != null) {
            this.quit.setFont(Tools.textF);
            this.quit.setBackground(backC);
            this.quit.setForeground(Color.black);
        }
        if (this.create != null) {
            this.create.setFont(Tools.textF);
            this.create.setBackground(backC);
            this.create.setForeground(Color.black);
        }
        if (this.member != null) {
            this.member.setFont(Tools.textF);
            this.member.setBackground(backC);
            this.member.setForeground(Color.black);
        }
        if (this.heroStatus == 3) {
            this.peer = new Button("Peer");
            this.peer.setFont(Tools.textF);
            this.peer.reshape(175, 175, 50, 20);
            this.peer.show(false);
            this.next = new Button("Next");
            this.next.setFont(Tools.textF);
            this.next.reshape(230, 175, 50, 20);
            this.next.show(false);
            this.grant = new Button("Grant");
            this.grant.setFont(Tools.textF);
            this.grant.reshape(285, 175, 50, 20);
            this.grant.show(false);
            this.deny = new Button("Deny");
            this.deny.setFont(Tools.textF);
            this.deny.reshape(340, 175, 50, 20);
            this.deny.show(false);
        }
    }

    @Override // DCourt.Screens.Screen
    public void addTools() {
        add(this.enact);
        add(this.confirm);
        if (this.clantext != null) {
            add(this.clantext);
        }
        if (this.join != null) {
            add(this.join);
        }
        if (this.quit != null) {
            add(this.quit);
        }
        if (this.create != null) {
            add(this.create);
        }
        if (this.member != null) {
            add(this.member);
        }
        if (this.heroStatus == 3) {
            add(this.peer);
            add(this.next);
            add(this.grant);
            add(this.deny);
        }
        updateTools();
    }

    void updateTools() {
        itHero h = Screen.getHero();
        this.enact.show(false);
        this.confirm.show(false);
        Checkbox act = this.clanAction.getCurrent();
        if (act != null) {
            if (act == this.join && this.clanStatus == 1) {
                this.enact.setLabel(joinStr);
                this.confirm.setLabel("Make It So - JOIN");
                this.enact.enable(this.confirm.getState() && h.getQuests() >= 1 && h.getMoney() >= JOIN_COSTS);
                this.enact.show(true);
                this.confirm.show(true);
            }
            if (act == this.quit) {
                if (this.heroStatus == 3) {
                    this.enact.setLabel(disbandStr);
                    this.confirm.setLabel("Make It So - DISBAND");
                    this.enact.enable(this.confirm.getState() && h.getQuests() >= 15 && h.getMoney() >= DISBAND_COSTS);
                    this.enact.show(true);
                    this.confirm.show(true);
                } else if (this.heroStatus == 2) {
                    this.enact.setLabel(quitStr);
                    this.confirm.setLabel("Make It So - QUIT");
                    this.enact.enable(this.confirm.getState() && h.getQuests() >= 5 && h.getMoney() >= QUIT_COSTS);
                    this.enact.show(true);
                    this.confirm.show(true);
                }
            }
            if (act == this.create && this.clanStatus == 2) {
                this.enact.setLabel(createStr);
                this.confirm.setLabel("Make It So - CREATE");
                this.enact.enable(this.confirm.getState() && h.getQuests() >= CREATE_QUESTS && h.getMoney() >= CREATE_COSTS);
                this.enact.show(true);
                this.confirm.show(true);
            }
            if (this.heroStatus != 3) {
                return;
            }
            if (act == this.member) {
                this.peer.show(true);
                this.next.show(true);
                this.grant.show(true);
                this.deny.show(true);
                this.enact.show(false);
                this.confirm.show(false);
                return;
            }
            this.peer.show(false);
            this.next.show(false);
            this.grant.show(false);
            this.deny.show(false);
        }
    }

    void findClanInfo(String clan) {
        this.clanStatus = 0;
        this.current = clan == null ? null : Tools.detokenize(clan.trim());
        this.power = 0;
        this.members = 0;
        this.ability = Constants.NONE;
        this.leader = Constants.NONE;
        if (this.current != null && this.current.length() >= 1 && !Constants.NONE.equalsIgnoreCase(this.current)) {
            repaint();
            Buffer buf = Loader.cgiBuffer(Loader.PEEKCLAN, this.current);
            if (buf == null || buf.isEmpty() || buf.isError()) {
                this.clanStatus = 2;
                return;
            }
            this.clanStatus = 1;
            this.leader = buf.token();
            if (this.leader == null) {
                this.leader = Constants.NONE;
            }
            if (buf.split()) {
                this.members = buf.num();
            }
            if (buf.split()) {
                this.power = buf.num();
            }
            if (buf.split()) {
                this.ability = buf.token();
            }
            if (this.heroStatus == 1 && Screen.getHero().isMatch(this.leader)) {
                Screen.getHero().setClan(this.current);
                MadLib sent = new MadLib(reinstateMsg);
                sent.replace("$leader$", this.leader);
                sent.replace("$clan$", this.current);
                sent.replace("$ruler$", "Queen Beth");
                Tools.setRegion(new arNotice(getHome(), sent.getText()));
            }
        }
    }

    Screen quitClan() {
        itHero h = Screen.getHero();
        String oldClan = h.getClan();
        if (oldClan == null || h.getQuests() < 5 || h.getMoney() < QUIT_COSTS) {
            return null;
        }
        h.setClan(null);
        h.subMoney(QUIT_COSTS);
        h.addFatigue(5);
        if (!Screen.saveHero()) {
            h.setClan(oldClan);
            h.addMoney(QUIT_COSTS);
            h.subFatigue(5);
            return new arNotice(this, GameStrings.SAVE_CANCEL);
        }
        String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(leaveClan)).concat(String.valueOf(String.valueOf(h.gainExp(h.getLevel())))))).concat(String.valueOf(String.valueOf(h.gainWits(25)))))).concat(String.valueOf(String.valueOf(h.gainCharm(25))));
        return new arNotice(getHome(), leaveClan);
    }

    Screen createClan() {
        itHero h = Screen.getHero();
        if (this.current == null || h.getQuests() < CREATE_QUESTS || h.getMoney() < CREATE_COSTS) {
            return null;
        }
        String oldClan = h.getClan();
        h.setClan(this.current);
        h.subMoney(CREATE_COSTS);
        h.addFatigue(CREATE_QUESTS);
        if (!Screen.saveHero()) {
            h.subFatigue(CREATE_QUESTS);
            h.addMoney(CREATE_COSTS);
            h.setClan(oldClan);
            return new arNotice(this, GameStrings.SAVE_CANCEL);
        }
        Buffer buf = Loader.cgiBuffer(Loader.MAKECLAN, String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(h.getName()))).append("|").append(Screen.getPlayer().getSessionID()).append("|").append(this.current))));
        if (buf == null || buf.isError()) {
            h.subFatigue(CREATE_QUESTS);
            h.addMoney(CREATE_COSTS);
            h.setClan(oldClan);
            return new arNotice(this, GameStrings.SAVE_CANCEL);
        }
        MadLib sent = new MadLib(createMsg);
        sent.replace("$leader$", this.leader);
        sent.replace("$clan$", this.current);
        sent.replace("$ruler$", "Queen Beth");
        return new arNotice(getHome(), String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(sent.getText())).concat(String.valueOf(String.valueOf(h.gainExp(h.getLevel() * h.getLevel() * 10)))))).concat(String.valueOf(String.valueOf(h.gainWits(500)))))).concat(String.valueOf(String.valueOf(h.gainCharm(500)))));
    }

    Screen disbandClan() {
        itHero h = Screen.getHero();
        if (this.current == null || h.getQuests() < 15 || h.getMoney() < DISBAND_COSTS) {
            return null;
        }
        String oldClan = h.getClan();
        h.setClan(this.current);
        h.subMoney(DISBAND_COSTS);
        h.addFatigue(15);
        if (!Screen.saveHero()) {
            h.subFatigue(15);
            h.addMoney(DISBAND_COSTS);
            h.setClan(oldClan);
            return new arNotice(this, GameStrings.SAVE_CANCEL);
        }
        Buffer buf = Loader.cgiBuffer(Loader.KILLCLAN, String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(h.getName()))).append("|").append(Screen.getPlayer().getSessionID()).append("|").append(this.current))));
        if (buf == null || buf.isError()) {
            h.subFatigue(15);
            h.addMoney(DISBAND_COSTS);
            h.setClan(oldClan);
            return new arNotice(this, GameStrings.SAVE_CANCEL);
        }
        MadLib sent = new MadLib(disbandMsg);
        sent.replace("$leader$", this.leader);
        sent.replace("$clan$", this.current);
        sent.replace("$ruler$", "Queen Beth");
        return new arNotice(getHome(), String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(sent.getText())).concat(String.valueOf(String.valueOf(h.gainExp((h.getLevel() * h.getLevel()) + 10)))))).concat(String.valueOf(String.valueOf(h.gainWits(100)))))).concat(String.valueOf(String.valueOf(h.gainCharm(100)))));
    }

    Screen petitionClan() {
        itHero h = Screen.getHero();
        if (this.current == null || this.clanStatus != 1 || h.getQuests() < 1 || h.getMoney() < JOIN_COSTS) {
            return null;
        }
        h.subMoney(JOIN_COSTS);
        h.addFatigue(1);
        if (!Screen.saveHero()) {
            h.addMoney(JOIN_COSTS);
            h.subFatigue(1);
            return new arNotice(this, GameStrings.SAVE_CANCEL);
        }
        itList mail = new itList(Constants.MAIL);
        mail.append(new itNote("Petition", h.getName(), "Sire,\nI wish to join thy guild.\nThankee"));
        String result = arPackage.send(String.valueOf(String.valueOf(h.getTitle())).concat(String.valueOf(String.valueOf(h.getName()))), this.leader, mail);
        if (result != null) {
            h.addMoney(JOIN_COSTS);
            h.subFatigue(1);
            return new arNotice(this, GameStrings.MAIL_CANCEL.concat(String.valueOf(String.valueOf(result))));
        }
        MadLib sent = new MadLib(petitionMsg);
        sent.replace("$leader$", this.leader);
        sent.replace("$clan$", this.current);
        return new arNotice(getHome(), sent.getText());
    }

    void findNextPetition() {
        Item it = Screen.getPack().select("Petition", this.petitionID + 1);
        if (it != null && (it instanceof itValue)) {
            this.petitionID++;
            this.petition = (itValue) it;
        } else if (this.petitionID < 0) {
            this.petition = null;
        } else {
            Item it2 = Screen.getPack().select("Petition", 0);
            if (it2 instanceof itValue) {
                this.petitionID = 0;
                this.petition = (itValue) it2;
                return;
            }
            this.petition = null;
        }
    }

    Screen grantPetition() {
        itHero hero = Screen.getHero();
        Screen.subPack(this.petition);
        if (!Screen.saveHero()) {
            Screen.putPack(this.petition);
            return new arNotice(this, GameStrings.SAVE_CANCEL);
        }
        itList mail = new itList(Constants.MAIL);
        mail.append(new itNote("Grant", hero.getClan(), "Greetings,\nIt is my pleasure to welcome you to our guild.\nGuildmaster"));
        String result = arPackage.send(String.valueOf(String.valueOf(hero.getTitle())).concat(String.valueOf(String.valueOf(hero.getName()))), this.petition.getValue(), mail);
        if (result != null) {
            Screen.putPack(this.petition);
            return new arNotice(this, GameStrings.MAIL_CANCEL.concat(String.valueOf(String.valueOf(result))));
        }
        MadLib sent = new MadLib(grantMsg);
        sent.replace("$name$", this.petition.getValue());
        sent.replace("$clan$", hero.getClan());
        return new arNotice(this, sent.getText());
    }

    Screen denyPetition() {
        itHero h = Tools.getHero();
        Screen.subPack(this.petition);
        if (!Screen.saveHero()) {
            Screen.putPack(this.petition);
            return new arNotice(this, GameStrings.SAVE_CANCEL);
        }
        MadLib msg = new MadLib(denyMsg);
        msg.replace("$today$", Tools.getToday());
        msg.replace("$name$", this.petition.getValue());
        msg.replace("$clan$", h.getClan());
        msg.replace("$leader$", String.valueOf(String.valueOf(h.getTitle())).concat(String.valueOf(String.valueOf(h.getName()))));
        itList mail = new itList(Constants.MAIL);
        mail.append(new itNote("Denial", h.getName(), msg.getText()));
        String result = arPackage.send(String.valueOf(String.valueOf(h.getTitle())).concat(String.valueOf(String.valueOf(h.getName()))), this.petition.getValue(), mail);
        if (result == null) {
            return new arNotice(this, denyHead.concat(String.valueOf(String.valueOf(msg.getText()))));
        }
        Screen.putPack(this.petition);
        return new arNotice(this, GameStrings.MAIL_CANCEL.concat(String.valueOf(String.valueOf(result))));
    }
}
