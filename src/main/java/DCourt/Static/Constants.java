package DCourt.Static;

import DCourt.Items.itList;

/* loaded from: DCourt.jar:DCourt/Static/Constants.class */
public interface Constants {
  public static final int PLACE_MAX = 11;
  public static final String SUITE = "suite";
  public static final String ROOM = "room";
  public static final String FLOOR = "floor";
  public static final String COT = "cot";
  public static final String TOWN = "town";
  public static final String FIELDS = "fields";
  public static final String FOREST = "forest";
  public static final String HILLS = "hills";
  public static final String MOUND = "mound";
  public static final String DOCKS = "docks";
  public static final String DUNJEON = "dunjeon";
  public static final String[] PLACE_ARRAY = {
    SUITE, ROOM, FLOOR, COT, TOWN, FIELDS, FOREST, HILLS, MOUND, DOCKS, DUNJEON
  };
  public static final itList PLACE_LIST = new itList("Place", PLACE_ARRAY);
  public static final int[] placeCamp = {0, 0, 1, 3, 0, 3, 7, 3, 1, 0, 0};
  public static final String FIGHT = "fight";
  public static final String MAGIC = "magic";
  public static final String THIEF = "thief";
  public static final String IEATSU = "Ieatsu";
  public static final String ATTACK = "Attack";
  public static final String DEFEND = "Defend";
  public static final String SKILL = "Skill";
  public static final String SPELLS = "Magic Assault";
  public static final String VICTORY = "Victory";
  public static final String RUNAWAY = "Runs Aways";
  public static final String BERZERK = "Berzek";
  public static final String CONTROL = "Control";
  public static final String SWINDLE = "Swindle";
  public static final String BACKSTAB = "Backstab";
  public static final String WORM = "worm";
  public static final String GOAT = "goat";
  public static final String EXP = "Exp";
  public static final String AGE = "Age";
  public static final String FAME = "Fame";
  public static final String HIGH = "High";
  public static final String PEAK = "Peak";
  public static final String LEVEL = "Level";
  public static final String MONEY = "Marks";
  public static final String SOCIAL = "Social";
  public static final String ACTIONS = "Action";
  public static final String WOUNDS = "Wounds";
  public static final String FATIGUE = "Fatigue";
  public static final String DISEASE = "Disease";
  public static final String STIPEND = "Stipend";
  public static final String BLIND_STR = "*BLIND*";
  public static final String PANIC_STR = "+PANIC+";
  public static final String FAVOR = "Favor";
  public static final String VERSION = "Version";
  public static final String MAIL = "Mail";
  public static final String TITLE = "Title";
  public static final String MALE = "Male";
  public static final String FEMALE = "Female";
  public static final String BOTH = "Both";
  public static final String NONE = "None";
  public static final String[] sexs = {MALE, FEMALE, BOTH, NONE};
  public static final String GENDER = "Gender";
  public static final String DRESS = "Dress";
  public static final String BEHAVE = "Behave";
  public static final String RACE = "Race";
  public static final String BUILD = "Build";
  public static final String SIGN = "Sign";
  public static final String SKIN = "Skin";
  public static final String EYES = "Eyes";
  public static final String HAIR = "Hair";
  public static final String HABIT = "Habit";
  public static final String CLAN = "Clan";
  public static final String MARKS = "Marks";
  public static final String PHRASE = "Phrase";
  public static final String RANK = "Rank";
  public static final String NAME = "Name";
  public static final String GUTS = "Guts";
  public static final String WITS = "Wits";
  public static final String CHARM = "Charm";
  public static final int MAXRANK = 10;
  public static final String[] rankTitle = {
    "",
    "Sr. ",
    "Brn. ",
    "Cnt. ",
    "Vct. ",
    "Mqs. ",
    "Earl ",
    "Duke ",
    "Prc. ",
    "Regent ",
    "Ruler ",
    "Emperor "
  };
  public static final String[][] rankName = {
    new String[] {
      "Peasant",
      "Knight",
      "Baron",
      "Count",
      "Viscount",
      "Marquis",
      "Earl",
      "Duke",
      "Prince",
      "Regent",
      "King",
      "Emperor"
    },
    new String[] {
      "Peasant",
      "Dame",
      "Baroness",
      "Countess",
      "Comtessa",
      "Marquessa",
      "Earl",
      "Duchess",
      "Princess",
      "Regent",
      "Queen",
      "Empress"
    }
  };
  public static final int[] rankCost = {5, 20, 80, 320, 1250, 5000, 20000, 80000, 320000, 0, 0, 0};
  public static final String GUILD = "Guild";
  public static final String MYSTIC = "Mystic";
  public static final String TRADER = "Trader";
  public static final String ILLUMINATI = "Illuminati";
  public static final String STRONG = "Strong";
  public static final String STURDY = "Sturdy";
  public static final String AGILE = "Agile";
  public static final String UNAGING = "Unaging";
  public static final String CATSEYES = "CatsEyes";
  public static final String HILLFOLK = "HillFolk";
  public static final String SWIFT = "Swift";
  public static final String SINCERE = "Sincere";
  public static final String TRICKY = "Tricky";
  public static final String EMPATHIC = "Empathic";
  public static final String SEXY = "Sexy";
  public static final String FENCER = "Fencer";
  public static final String STUBBORN = "Stubborn";
  public static final String CLEVER = "Clever";
  public static final String DRAGON = "Dragon";
  public static final String POPULAR = "Popular";
  public static final String RANGER = "Ranger";
  public static final String GYPSY = "Gypsy";
  public static final String MERCHANT = "Merchant";
  public static final String BANDIT = "Bandit";
  public static final String MEDIC = "Medic";
  public static final String SMITH = "Smith";
  public static final String ARMOR = "Armor";
  public static final String QUICK = "Quick";
  public static final String HOTEL = "Hotel";
  public static final String HARDY = "Hardy";
  public static final String ALERT = "Alert";
  public static final String REFLEX = "Reflex";
  public static final String[] TraitList = {
    ARMOR,
    AGILE,
    ALERT,
    BANDIT,
    BERZERK,
    CATSEYES,
    CLEVER,
    DRAGON,
    EMPATHIC,
    FENCER,
    GUILD,
    GYPSY,
    HARDY,
    HOTEL,
    HILLFOLK,
    ILLUMINATI,
    MEDIC,
    MERCHANT,
    MYSTIC,
    POPULAR,
    RANGER,
    REFLEX,
    SINCERE,
    SEXY,
    SMITH,
    STRONG,
    STUBBORN,
    STURDY,
    SWIFT,
    TRADER,
    TRICKY,
    UNAGING,
    QUICK
  };
  public static final String[] TraitStub = {
    "AR", "AG", "AL", "BA", "BE", "CA", "CL", "DR", "EM", "FE", "GU", "GY", "HA", "HO", "HI", "IL",
    "MM", "MR", "MY", "PO", "RA", "RE", "SI", "SE", "SM", "ST", "SB", "SR", "SW", "TA", "TK", "UN",
    "QU"
  };
}
