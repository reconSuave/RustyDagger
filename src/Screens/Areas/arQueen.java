package DCourt.Screens.Areas;

import DCourt.Items.List.itHero;
import DCourt.Items.List.itNote;
import DCourt.Items.Token.itCount;
import DCourt.Items.itList;
import DCourt.Screens.Areas.Queen.arqBoast;
import DCourt.Screens.Areas.Queen.arqDice;
import DCourt.Screens.Areas.Queen.arqGame;
import DCourt.Screens.Areas.Queen.arqMingle;
import DCourt.Screens.Screen;
import DCourt.Screens.Template.Indoors;
import DCourt.Screens.Utility.arNotice;
import DCourt.Screens.Utility.arPackage;
import DCourt.Static.Constants;
import DCourt.Static.GameStrings;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/arQueen.class */
public class arQueen extends Indoors {
    itHero hero = Screen.getHero();
    Button[] tools;
    Button petition;
    Button invest;
    static final int MAXIMUM_RANK = 9;
    static final int INVEST_DANGER = 9;
    static final int PETITION_COST = 10; // PETITION_COST;
    // static final int PETITION_COST = PETITION_COST;
    static final int PETITION_QUEST = 3;
    static final int INVEST_COST = 4; // INVEST_COST;
    // static final int INVEST_COST = INVEST_COST;
    static final int INVEST_QUEST = 5;
    static final String[] text = {"{1}Dice", "{1}Mingle", "{1}Boast", "{1}Game"};
    static final String[] greeting = {null, "What a clever little man.", "I'm getting bored.", "Tell me a story.", "Where have you been?", "Give me a good reason.", "OFF WITH HIS HEAD!", "Show me something special.", "You are boring me.", "Why should I listen?", "Give me a good jape."};
    public static final String investMsg = "investMsg";
    // public static final String investMsg = investMsg;
    public static final String feelGood = "feelGood";
    // public static final String feelGood = feelGood;
    public static final String feelBad = "feelBad";
    // public static final String feelBad = feelBad;
    public static final String[] investFeel = {feelGood, feelGood, "", feelBad, feelBad};
    public static final String investNote = "investNote";
    // public static final String investNote = investNote;
    public static final String[] investText = {"$today$$CR$This letter is to inform you that the $lordname$ was beaten senseless and robbed utterly while engaged in business. $CR$Sincerely,$CR$$official$", "Things went very poorly. I am deeply ashamed to report substantial losses. Pray accept this small sum as my sole apology.$CR$Deepest Regrets, $CR$$lordname$ ", "There were several setbacks to the venture we have planned.  I am able to return your original investment, but no more. $CR$Regretfully, $CR$$lordname$", "Business has progressed exactly as expected. Your share of the proceeds accompany this letter. $CR$Ever yours, $CR$$lordname$", "As the purse accompanying this missive indicates, things went extremely well. You will find an extra $bonus$ marks above the amount I promised you. $CR$Thank you for your trust.$CR$$lordname$"};
    public static final String[][] investJobs = {new String[]{"the Oat Harvest", "the Wheat Harvest", "the Barley Harvest"}, new String[]{"the Grape Harvest", "Hog Futures", "Wool Futures"}, new String[]{"a Cargo of Wine", "a Cargo of Beer", "a Cargo of Dried Meat"}, new String[]{"the Leather Works", "the Iron Works", "the Lumber Yard"}, new String[]{"a Flower Shop", "a Pastry Shop", "a Fine Theatre"}, new String[]{"a Cargo of Weapons", "a Cargo of Farm Tools", "A Cargo of Glassware"}, new String[]{"a Cargo of Artwork", "a Cargo of Fine Wines", "a Cargo of Jewelry"}, new String[]{"bribes to the Chief Counselor", "bribes to the Guard Captain", "bribes to the Guild Master"}};
    public static final String[] investRisk = {"marginal", "minimal", "small", "moderate", "large", "substantial", "massive", "incredible"};
    public static final String[] officialTitle = {"Captain ", "Doctor ", "Mayor ", "Abbot ", "Lieutenant ", "Officer ", "Father ", "Brother ", "Sherrif", "Alderman", "Sultan", "Major"};
    public static final String[] petitionText = {"$TB$The queen laughs coldly at your ambitions. $CR$$TB$\"The rank of $newrank$ is at such far remove that you may find the stars to be a closer companion.  Do not even dare to speak to me again until you have a gift worthy of my attentions.\" $CR$$TB$She signals her guards who drag you out and throw you from the castle. $CR$", "$TB$The queen stares at you as if you were a stain on the carpet. $CR$$TB$\"The rank of $newrank$ is far from your grasp at this moment.  Go forth, do good works to curry favor with this court.\" $CR$$TB$She turns her gaze away from you in a clear gesture of dismissal. $CR$", "$TB$Her majesty smiles shyly. $CR$$TB$\"You have made great inroads with this court.  Many nobles find you a pleasing companion and speak favorably of you.  The rank of $newrank$ is not an impossible dream, but still requires more work.\" $CR$$TB$The queen acknowledges your parting bow, and turns to whisper giggling comments to her ladies in waiting. $CR$", "$TB$The queen smiles on you benignly. $CR$$TB$\"The rank of $newrank$ is not far from your grasp.  If you could gain the greatest approval of my counselors and nobles, I could be persuaded to grant you a new title.\" $CR$$TB$The queen turns her attention to matters of state, but you suspect your case is on her mind. $CR$", "$TB$$TB$*** Social Standing Raised *** $CR$$TB$Proclamations are sent accross the land: $CR$ $CR$$TB$\"Her majesty Queen Beth is pleased to declare that one of her most faithful and loyal of subjects, the honorable $newrank$ $name$ ;  has earned her deepest gratitude and the respect of the Dragon Court as a whole. $CR$ $CR$$TB$\"In Recognition of his long standing good works, we are delighted to declare that $name$ shall henceforth be known as:  $CR$ $CR$$TB$$TB$$TB$$TB$\" $newrank$ $name$ \"!!"};
    static final String recommendMsg = "recommendMsg";
    // static final String recommendMsg = recommendMsg;

    public arQueen(Screen from) {
        super(from, "Queen Beth reigns over Dragon Court");
        setBackground(new Color(255, 128, 128));
        setForeground(new Color(128, 0, 0));
    }

    @Override // DCourt.Screens.Template.Indoors
    public String getFace() {
        return "Faces/Ruler.jpg";
    }

    @Override // DCourt.Screens.Template.Indoors
    public String getGreeting() {
        String msg = Tools.select(greeting);
        return msg == null ? String.valueOf(String.valueOf(new StringBuffer("Have you seen ").append(Screen.getBest()).append("?"))) : msg;
    }

    @Override // DCourt.Screens.Screen
    public void update(Graphics g) {
        paint(g);
        updateTools();
    }

    @Override // DCourt.Screens.Screen
    public void localPaint(Graphics g) {
        int rank = this.hero.getSocial();
        int sex = this.hero.getGender();
        localPaint(g);
        if (rank < 9) {
            g.drawString(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(Constants.rankName[sex][rank]))).append(" to ").append(Constants.rankName[sex][rank + 1]))), 180, 190);
        }
    }

    @Override // DCourt.Screens.Template.Indoors, DCourt.Screens.Screen
    public boolean action(Event e, Object o) {
        if (Tools.movedAway(this)) {
            return true;
        }
        if (Screen.getQuests() > 0) {
            if (e.target == this.tools[0]) {
                Tools.setRegion(new arqDice(this));
            } else if (e.target == this.tools[1]) {
                Tools.setRegion(new arqMingle(this));
            } else if (e.target == this.tools[2]) {
                Tools.setRegion(new arqBoast(this));
            } else if (e.target == this.tools[3]) {
                Tools.setRegion(new arqGame(this));
            }
        }
        if (e.target == this.petition) {
            petition();
        } else if (e.target == this.invest) {
            invest();
        } else if (e.target == getPic(0)) {
            Tools.setRegion(getHome());
        }
        return action(e, o);
    }

    @Override // DCourt.Screens.Screen
    public void createTools() {
        this.tools = new Button[text.length];
        for (int i = 0; i < this.tools.length; i++) {
            this.tools[i] = new Button(text[i]);
            this.tools[i].reshape(160 + ((i / 2) * 100), 50 + ((i % 2) * 40), 90, 25);
            this.tools[i].setFont(Tools.statusF);
        }
        this.petition = new Button("{3} Petition $5000");
        this.petition.reshape(170, 200, 170, 25);
        this.petition.setFont(Tools.statusF);
        this.petition.show(Screen.getSocial() < 9);
        this.invest = new Button("{5}Invest $100k");
        this.invest.reshape(170, 130, 170, 25);
        this.invest.setFont(Tools.statusF);
        updateTools();
    }

    @Override // DCourt.Screens.Screen
    public void addTools() {
        for (int i = 0; i < this.tools.length; i++) {
            add(this.tools[i]);
        }
        add(this.petition);
        add(this.invest);
    }

    public void updateTools() {
        int rank = Screen.getSocial();
        int i = Constants.rankCost[rank];
        int cash = Screen.getMoney();
        int quests = Screen.getQuests();
        for (int i2 = 0; i2 < this.tools.length; i2++) {
            this.tools[i2].enable(quests >= 1);
        }
        if (cash < 1000) {
            this.tools[0].enable(false);
        }
        this.petition.enable(quests >= 3 && cash >= PETITION_COST);
        this.petition.show(rank < 9);
        this.invest.enable(cash >= INVEST_COST && quests >= 5);
    }

    synchronized void invest() {
        int rank;
        if (Screen.getQuests() >= 5 && Screen.getMoney() >= INVEST_COST && (rank = (Screen.getSocial() + Tools.roll(4)) - 1) >= 0 && rank <= 11) {
            int risk = (rank + Tools.roll(rank + 2)) / 3;
            int gain = 0;
            int index = Tools.fourTest(this.hero.getWits(), 20 + (risk * 40));
            int reward = (INVEST_COST * (23 + (risk * 3))) / 20;
            MadLib msg = new MadLib(investMsg.concat(String.valueOf(String.valueOf(investFeel[index]))));
            MadLib note = new MadLib(investNote.concat(String.valueOf(String.valueOf(investText[index]))));
            switch (index) {
                case 0:
                    note = new MadLib(investText[0]);
                    note.replace("$official$", String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(Tools.select(officialTitle)))).append(" ").append(Tools.select(GameStrings.Names)))));
                    gain = 0;
                    break;
                case 1:
                    gain = INVEST_COST / 2;
                    break;
                case 2:
                    msg.append(this.hero.gainExp((risk * 5) + rank));
                    msg.append(this.hero.gainWits(5));
                    gain = INVEST_COST;
                    break;
                case 3:
                    msg.append(this.hero.gainExp((risk * 10) + rank));
                    msg.append(this.hero.gainWits(10));
                    gain = reward;
                    break;
                case 4:
                    gain = (reward * 3) / 2;
                    msg.replace("$bonus$", "".concat(String.valueOf(String.valueOf(gain - reward))));
                    msg.append(this.hero.gainExp((risk * 15) + rank));
                    msg.append(this.hero.gainWits(15));
                    break;
            }
            String name = String.valueOf(String.valueOf(Constants.rankTitle[rank])).concat(String.valueOf(String.valueOf(Tools.select(GameStrings.Names))));
            int sex = Tools.roll(2);
            note.replace("$lordname$", name);
            note.replace("$rank$", this.hero.getRankTitle());
            note.replace("$name$", this.hero.getName());
            note.replace("$today$", Tools.getToday());
            msg.replace("$lordname$", name);
            msg.replace("$lordrank$", Constants.rankName[sex][rank]);
            msg.replace("$jobname$", Tools.select(investJobs[risk]));
            msg.replace("$cost$", "".concat(String.valueOf(String.valueOf((int) INVEST_COST))));
            msg.replace("$risk$", investRisk[risk]);
            msg.replace("$reward$", "".concat(String.valueOf(String.valueOf(reward))));
            msg.genderize(sex == 0);
            itList mail = new itList(Constants.MAIL);
            mail.append(new itNote("Letter", name, note.getText()));
            if (gain > 0) {
                mail.add(new itCount("Marks", gain));
            }
            Screen.subMoney(INVEST_COST);
            this.hero.addFatigue(5);
            if (!Screen.saveHero()) {
                Screen.addMoney(INVEST_COST);
                this.hero.subFatigue(5);
                Tools.setRegion(new arNotice(getHome(), GameStrings.SAVE_CANCEL));
                return;
            }
            String result = arPackage.send(name, this.hero.getName(), mail);
            if (result != null) {
                Screen.addMoney(INVEST_COST);
                this.hero.subFatigue(5);
                Tools.setRegion(new arNotice(this, GameStrings.MAIL_CANCEL.concat(String.valueOf(String.valueOf(result)))));
                return;
            }
            Tools.setRegion(new arNotice(this, msg.getText()));
        }
    }

    synchronized void petition() {
        Screen next;
        int rank = Screen.getSocial();
        int cost = Constants.rankCost[rank];
        int sex = this.hero.getGender();
        if (Screen.getQuests() >= 3 && Screen.getMoney() >= PETITION_COST) {
            this.hero.addFatigue(3);
            Screen.subMoney(PETITION_COST);
            this.hero.addFavor(PETITION_COST);
            int index = ((this.hero.hasTrait(Constants.POPULAR) ? this.hero.getFavor() / 700 : this.hero.getFavor() / 1000) * 4) / cost;
            if (index > 4) {
                index = 4;
            }
            MadLib msg = new MadLib(petitionText[index]);
            msg.replace("$newrank$", Constants.rankName[sex][rank + 1]);
            msg.replace("$name$", this.hero.getName());
            switch (index) {
                case 0:
                    next = new arNotice(new arTown(), msg.getText());
                    break;
                case 1:
                case 2:
                case 3:
                    next = new arNotice(this, msg.getText());
                    break;
                default:
                    this.hero.getStatus().zero(Constants.FAVOR);
                    this.hero.addRank(Constants.SOCIAL, 1);
                    this.hero.addStatus(Constants.FAME, this.hero.getSocial() * 100);
                    next = new arNotice(this, msg.getText());
                    break;
            }
            Tools.setRegion(next);
        }
    }

    public static String Recommend() {
        itHero hero = Screen.getHero();
        return ((hero.hasTrait(Constants.POPULAR) ? hero.getFavor() / 700 : hero.getFavor() / 900) < Constants.rankCost[hero.getSocial()] || Tools.roll(2) > 0) ? "" : recommendMsg;
    }
}
