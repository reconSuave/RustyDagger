package DCourt.Screens.Areas.Queen;

import DCourt.Items.List.itHero;
import DCourt.Screens.Areas.arQueen;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Static.Constants;
import DCourt.Static.GameStrings;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Queen/arqMingle.class */
public class arqMingle extends arNotice {
    static int MINGLERISK = 5;
    public static final String mingleMsg = "mingleMsg";
    // public static final String mingleMsg = mingleMsg;
    public static final String[] mingleText = {"$TB$Five minutes pass while you describe your bladder problems to $lordname$.  $HE$ smiles in a strained fashion and takes $his$ leave as soon as possible.$CR$$TB$Perhaps you could have been a little more attentive.$CR$", "$TB$The $lordrank$ is a terrific conversationalist. $HE$ smiles and nods for several minutes while you speak at some length about your adventures and aspirations.$CR$$TB$You part company thinking that you have made great success with ... uh ... what was $his$ name again? $CR$", "$TB$You exchange friendly banter with the noble for several minutes.  You fail to find any topic of common interest and thus part ways a short time later.$CR$$TB$Well, at least you have offended noone.$CR$", "$TB$You listen attentively while $lordname$ describes $HIS$ pursuits.  You nod and smile at all the appropriate points during $HIS$ stories.$CR$$TB$The $lordrank$ is favorably impressed by your intelligence and acumen.  $HE$ invites you to come visit at $HIS$ estates sometime.$CR$$TB$You have won another friend in the Dragon Court.$CR$", "$TB$You listen with grave attention as the $lordrank$ describes $his$ pursuits.  You wax enthusiastic and make knowledgable comments on the topic.$CR$$TB$ $lordname$ thanks you for the delightful conversation and expresses interest in your future career. You have earned yourself a staunch ally in the Dragon Court.$CR$"};

    public arqMingle(Screen from) {
        super(from, prepareText());
    }

    static String prepareText() {
        itHero h = Screen.getHero();
        int rank = 1 + Tools.roll(2 + h.getSocial());
        if (rank > 10) {
            rank = 10;
        }
        int level = (rank * 2) + Tools.roll(rank * 2);
        int skill = h.getCharm() + (h.magic() * 5);
        int favor = h.getFavor();
        h.addFatigue(1);
        MadLib msg = new MadLib(mingleMsg.concat(String.valueOf(String.valueOf(mingleText[Tools.fourTest(skill, level * MINGLERISK)]))));
        switch (Tools.fourTest(skill, level * MINGLERISK)) {
            case 0:
                h.subFavor(favor / (14 - rank));
                break;
            case 1:
                h.subFavor(favor / (17 - rank));
                break;
            case 2:
                msg.append(h.gainExp(rank + level));
                msg.append(h.gainCharm(4));
                break;
            case 3:
                msg.append(h.gainExp((rank * 2) + level));
                msg.append(h.gainCharm(7));
                h.addFavor(favor / (17 - rank));
                break;
            case 4:
                msg.append(h.gainExp((rank * 5) + level));
                msg.append(h.gainCharm(10));
                h.addFavor(favor / (14 - rank));
                break;
        }
        msg.append(arQueen.Recommend());
        msg.replace("$lordname$", String.valueOf(String.valueOf(Constants.rankTitle[rank])).concat(String.valueOf(String.valueOf(Tools.select(GameStrings.Names)))));
        String[][] strArr = Constants.rankName;
        int sex = Tools.roll(2);
        msg.replace("$lordrank$", strArr[sex][rank]);
        msg.replace("$interests$", Tools.select(GameStrings.interests));
        msg.genderize(sex == 0);
        return msg.getText();
    }
}
