package DCourt.Screens.Areas.Queen;

import DCourt.Items.List.itHero;
import DCourt.Screens.Areas.arQueen;
import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;
import DCourt.Tools.MadLib;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Queen/arqGame.class */
public class arqGame extends arNotice {
    static final String gameMsg = gameMsg;
    static final String gameMsg = gameMsg;
    static final String[] game = {"Wickets.  You whack a wooden ball with a mallet and try to knock down a standing stick called a wicket while a team of nine men try to stop you.", "Nine Pins. You set up ten pegs in a triangle formation. then take turns throwing a ball to bowl them down.", "Croquette.  You knock a wooden ball through little hoops and try to hit a standing stick.", "Golfing. You knock a small ball with a stick and try to drop it into a hole with the fewest swings.", "Badmitton.  Two players enter a court, then knock a feathered ball over a net and off the walls.", "Poloball.  You ride a horse around whacking a wooden ball with a mallet.  You score by shooting into a goal box.", "Scrumming. Two teams face off over an inflated pigs bladder. They charge at each other and try to push the balloon across an end line.", "Fisticuffs. Two to six men face off in a ring wielding nothing more than their bare knuckles.  Points are scored by striking the face and shoulders."};
    static final String[] accident = {"accidently smash your hand", "run headfirst into a wall", "trip and wrench your ankle", "jump and bang your head", "turn and twist your knee", "throw out your back"};
    static final String[] country = {"Hie Brasil", "Shangala", "Orehwon", "the Troll Dominion", "Alfheim", "Raynoma", "Farstael", "Ter Winache"};
    static final String[] result = {"$TB$You bumble about clumsily, fowling your opponents and tripping up repeatedly. When you $accident$ your mates kick you out of the game.$CR$$TB$Later that the day, everyone is whispering and giggling whenever you aren't looking.$CR$$CR$$TB$$TB$*** you take -$hurt$ wounds ***", "$TB$You play as hard as you can, but for some reason you just can't quite get the hang of things. You remain scoreless for the day.$CR$$TB$After the game, your friends try to cheer you up. They pretend that noone likes this game, but you know that's a lie.$CR$", "$TB$You charge about with strength and enthusiam. You score once and so do some of your mates.$CR$$TB$When all the scores are tallied, things are about even.Everyone declares the game to be great good fun and vow to play it daily.$CR$", "$TB$You charge about with verve and force.  You score several times, impressing the crowds.$CR$$TB$When the scores are tallied, you are found to be ahead of everyone.  Your mates congratulate and express their admiration.$CR$", "$TB$You play with an unmatched skill that devestates the opposition. You stop all their points, and score with complete impunity.$CR$$TB$At the end of play, the crowds charge forward and bear you on their shoulders while loudly chanting your name.$CR$"};
    public static final String SMASHED = SMASHED;
    public static final String SMASHED = SMASHED;

    public arqGame(Screen from) {
        super(from, prepareText());
        itHero h = Screen.getHero();
        if (h.getWounds() >= h.getGuts()) {
            setHome(h.killedScreen(this, SMASHED, false));
        }
    }

    static String prepareText() {
        itHero h = Screen.getHero();
        int rank = Tools.roll(6) + Tools.roll(3);
        int level = h.getLevel();
        int skill = h.getGuts();
        int favor = h.getFavor();
        h.addFatigue(1);
        int index = Tools.fourTest(skill, (1 + rank) * 20);
        MadLib msg = new MadLib(gameMsg.concat(String.valueOf(String.valueOf(result[index]))));
        switch (index) {
            case 0:
                int num = ((1 + Tools.twice(3)) * h.getGuts()) / 10;
                h.addWounds(num);
                h.subFavor(favor / (12 - rank));
                msg.replace("$hurt$", "".concat(String.valueOf(String.valueOf(num))));
                break;
            case 1:
                h.subFavor(favor / (14 - rank));
                break;
            case 2:
                msg.append(h.gainExp(rank + level));
                msg.append(h.gainGuts(4));
                break;
            case 3:
                msg.append(h.gainExp((rank * 2) + level));
                msg.append(h.gainGuts(7));
                h.addFavor(favor / (14 - rank));
                break;
            case 4:
                msg.append(h.gainExp((rank * 5) + level));
                msg.append(h.gainGuts(10));
                h.addFavor(favor / (12 - rank));
                break;
        }
        msg.append(arQueen.Recommend());
        msg.replace("$country$", Tools.select(country));
        msg.replace("$game$", game[rank]);
        msg.replace("$accident$", Tools.select(accident));
        return msg.getText();
    }
}
