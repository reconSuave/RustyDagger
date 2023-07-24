package DCourt.Static;

import DCourt.Items.itList;
import DCourt.Tools.Tools;

/* loaded from: DCourt.jar:DCourt/Static/ArmsTrait.class */
public interface ArmsTrait {
    public static final String HEAD = "Head";
    public static final String BODY = "Body";
    public static final String RIGHT = "Right";
    public static final String LEFT = "Left";
    public static final String FEET = "Feet";
    public static final String SECRET = "Secret";
    public static final String DECAY = "Decay";
    public static final String CURSED = "Cursed";
    public static final String CURSE = "Curse";
    public static final String GLOWS = "Glows";
    public static final String FLAME = "Flame";
    public static final String BLESS = "Bless";
    public static final String LUCKY = "Lucky";
    public static final String DISEASE = "Disease";
    public static final String BLIND = "Blind";
    public static final String PANIC = "Panic";
    public static final String BLAST = "Blast";
    public static final String ENCHANT = "Enchant";
    public static final String[] traitLabel = {HEAD, BODY, FEET, RIGHT, LEFT, DECAY, SECRET, CURSED, CURSE, GLOWS, FLAME, BLESS, LUCKY, "Disease", "Blind", "Panic", BLAST, ENCHANT};
    public static final itList traitList = new itList("Traits", traitLabel);
    public static final int VISIBLE_TRAIT = traitList.firstOf(CURSE);
    public static final int RANDOM_TRAIT = traitList.firstOf(CURSED);
    public static final int VALUED_TRAIT = traitList.firstOf(GLOWS);
    public static final int END_WEAR_TRAIT = traitList.firstOf(LEFT) + 1;
    public static final int ENCHANT_TRAIT = traitList.firstOf(ENCHANT);
    public static final int MAX_TRAITS = traitLabel.length;
    public static final int[] traitValue = {0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 800, Tools.DEFAULT_HEIGHT, 250, 1500, 4000, 3000, 2000, 100};
}
