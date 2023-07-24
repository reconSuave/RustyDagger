package DCourt.Screens.Utility;

import DCourt.Control.GearTable;
import DCourt.Items.Item;
import DCourt.Items.List.itArms;
import DCourt.Items.List.itNote;
import DCourt.Items.Token.itCount;
import DCourt.Screens.Screen;
import DCourt.Static.ArmsTrait;
import DCourt.Static.Constants;

/* loaded from: DCourt.jar:DCourt/Screens/Utility/arDetail.class */
public class arDetail extends arNotice {
    static final String[] typeString = {"Junk\n\tDitch it\n", "Map\n\tAllows Access to Region\n", "Camp Gear\n\tImproves Outdoor Camping\n", "Gear\n\tCommon Adventure Supplies\n", "Treasure\n\tSell it for money", "Treasure\n\tMay be used by Mages\n", "Magic\n\tAffect Hero + Monsters\n", "Magic\n\tAffects Armaments\n", "Special\n\tAdvanced Adventuring Supplies\n", "Money\n\tThis is what you spend in shops"};

    public arDetail(Screen from, Item it) {
        super(from, "Equipment Detail");
        setMessage(detail(it));
    }

    String detail(Item it) {
        return it instanceof itArms ? armsDetail((itArms) it) : it instanceof itCount ? countDetail((itCount) it) : it instanceof itNote ? noteDetail((itNote) it) : "No Information";
    }

    String countDetail(itCount it) {
        return String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(it.getName()))).append("[").append(it.getCount()).append("]\n\n\t"))))).concat(String.valueOf(String.valueOf(typeString[GearTable.getType(it)])));
    }

    String noteDetail(itNote it) {
        return String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(it.getName()))).append(": ").append(it.getFrom()).append("\nSent: ").append(it.getDate()).append("\n====================\n").append(it.getBody())));
    }

    String armsDetail(itArms a) {
        if (a.hasTrait(ArmsTrait.SECRET)) {
            if (Screen.hasTrait(Constants.SMITH) && (a.hasTrait(ArmsTrait.RIGHT) || a.hasTrait(ArmsTrait.LEFT))) {
                a.clrTrait(ArmsTrait.SECRET);
            }
            if (Screen.hasTrait(Constants.ARMOR) && (a.hasTrait(ArmsTrait.HEAD) || a.hasTrait(ArmsTrait.BODY) || a.hasTrait(ArmsTrait.FEET))) {
                a.clrTrait(ArmsTrait.SECRET);
            }
        }
        String msg = String.valueOf(String.valueOf(a.toShow())).concat("\n\n\tArmament\n\tLoc:");
        boolean none = true;
        for (int ix = 0; ix < ArmsTrait.END_WEAR_TRAIT; ix++) {
            if (a.hasTrait(ArmsTrait.traitLabel[ix])) {
                msg = String.valueOf(String.valueOf(msg)).concat(String.valueOf(String.valueOf(" ".concat(String.valueOf(String.valueOf(ArmsTrait.traitLabel[ix]))))));
                none = false;
            }
        }
        if (none) {
            msg = String.valueOf(String.valueOf(msg)).concat(" NONE");
        }
        if (a.hasTrait(ArmsTrait.SECRET)) {
            return String.valueOf(String.valueOf(msg)).concat("\n\n\tNot Identified\n");
        }
        String msg2 = String.valueOf(String.valueOf(msg)).concat("\n");
        int num = a.getAttack();
        if (num != 0) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("\t").append(num < 1 ? "" : "+").append(num).append(" Attack\n"))))));
        }
        int num2 = a.getDefend();
        if (num2 != 0) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("\t").append(num2 < 1 ? "" : "+").append(num2).append(" Defense\n"))))));
        }
        int num3 = a.getSkill();
        if (num3 != 0) {
            msg2 = String.valueOf(String.valueOf(msg2)).concat(String.valueOf(String.valueOf(String.valueOf(String.valueOf(new StringBuffer("\t").append(num3 < 1 ? "" : "+").append(num3).append(" Skill\n"))))));
        }
        String msg3 = String.valueOf(String.valueOf(msg2)).concat("\n\tOther:");
        boolean none2 = true;
        for (int ix2 = ArmsTrait.VISIBLE_TRAIT; ix2 < ArmsTrait.traitLabel.length; ix2++) {
            if (a.hasTrait(ArmsTrait.traitLabel[ix2])) {
                msg3 = String.valueOf(String.valueOf(msg3)).concat(String.valueOf(String.valueOf("\n\t\t".concat(String.valueOf(String.valueOf(ArmsTrait.traitLabel[ix2]))))));
                if (ix2 == ArmsTrait.ENCHANT_TRAIT) {
                    msg3 = String.valueOf(String.valueOf(msg3)).concat(String.valueOf(String.valueOf(enchantStrength(a.getPower(), a.getCount(ArmsTrait.ENCHANT)))));
                }
                none2 = false;
            }
        }
        if (none2) {
            msg3 = String.valueOf(String.valueOf(msg3)).concat("\n\t\tNONE\n");
        }
        return msg3;
    }

    String enchantStrength(int power, int spell) {
        return spell < power / 2 ? " Weak\n" : spell < power ? " Good\n" : " Strong\n";
    }
}
