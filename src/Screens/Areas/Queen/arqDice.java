package DCourt.Screens.Areas.Queen;

import DCourt.Items.List.itHero;
import DCourt.Screens.Areas.arQueen;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Static.GameStrings;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Queen/arqDice.class */
public class arqDice extends arNotice {
    static final String swapMsg = "swapMsg";
    // static final String swapMsg = swapMsg;
    static final String guildSign = "guildSign";
    // static final String guildSign = guildSign;
    static final String diceMsg = "diceMsg";
    // static final String diceMsg = diceMsg;
    static final String[] diceText = {"$TB$A short time later, you are handing over a full purse of money to the gloating $lordrank$.  $HE$ slaps you on the back and shakes your hand. You may have lost, but you have gained a friend.$CR$", "$TB$You dice with great care, but lose steadily. Soon, you shake your head and cut your losses at $cost$ marks.  $lordname$ smiles broadly and offers to play again in the future.$CR$", "$TB$You dice with gay abandon.  At the end, you and the $lordrank$ break even.  $HE$ thanks you for the friendly game.$CR$", "$TB$You dice with good success. $lordname$ smiles wanly as you take half $HIS$ money.$CR$", "$TB$A short time later, you are adding a fat purse to your possessions.  It is the $lordrank$'s turn to smile bravely.$CR$"};

    public arqDice(Screen from) {
        super(from, prepareText());
    }

    static String prepareText() {
        itHero h = Screen.getHero();
        int rank = 1 + (h.getMoney() / 25000) + Tools.roll(3);
        if (rank > 10) {
            rank = 10;
        }
        int level = (rank * 2) + Tools.roll(rank * 2);
        int thief = Tools.roll(2) == 0 ? 0 : Tools.roll(level);
        int dskill = level * 8;
        int cost = 2500 * (rank + 1);
        if (cost > h.getMoney()) {
            cost = h.getMoney();
        }
        int pskill = h.getWits();
        int favor = h.getFavor();
        h.addFatigue(1);
        MadLib msg = new MadLib(diceMsg);
        boolean swap = Tools.contest(h.thief(), thief);
        if (!swap) {
            dskill += thief * 5;
        } else {
            msg.append(swapMsg);
            pskill += h.thief() * 5;
        }
        int index = Tools.fourTest(pskill, dskill);
        msg.append(diceText[index]);
        switch (index) {
            case 0:
                h.subMoney(cost);
                h.addFavor(favor / (20 - rank));
                break;
            case 1:
                msg.replace("$cost$", cost / 2);
                h.subMoney(cost / 2);
                h.addFavor(favor / (30 - rank));
                break;
            case 2:
                msg.append(h.gainExp(rank + thief + level));
                msg.append(h.gainWits(4));
                h.addFavor(favor / (40 - rank));
                break;
            case 3:
                h.addMoney(cost / 2);
                msg.append(h.gainExp(((rank + thief) * 2) + level));
                msg.append(h.gainWits(7));
                h.subFavor(favor / (30 - rank));
                break;
            case 4:
                h.addMoney(cost);
                msg.append(h.gainExp(((rank + thief) * 5) + level));
                msg.append(h.gainWits(10));
                h.subFavor(favor / (20 - rank));
                break;
        }
        if (!swap && h.thief() > 0 && thief > 0) {
            msg.append(guildSign);
        }
        msg.append(arQueen.Recommend());
        msg.replace("$lordname$", String.valueOf(String.valueOf(Constants.rankTitle[rank])).concat(String.valueOf(String.valueOf(Tools.select(GameStrings.Names)))));
        String[][] strArr = Constants.rankName;
        int sex = Tools.roll(2);
        msg.replace("$lordrank$", strArr[sex][rank]);
        msg.replace("$bet$", cost);
        msg.genderize(sex == 0);
        return msg.getText();
    }
}
