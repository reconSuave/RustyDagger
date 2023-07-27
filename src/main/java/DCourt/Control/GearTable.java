package DCourt.Control;

import DCourt.Items.Item;
import DCourt.Items.Token.itCount;
import DCourt.Items.itList;
import DCourt.Static.GearTypes;
import DCourt.Tools.Tools;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: DCourt.jar:DCourt/Control/GearTable.class */
public class GearTable implements GearTypes {
  static Hashtable table = null;
  static GearRecord unknown = null;

  public GearTable() {
    buildTable();
  }

  public static boolean find(Item it) {
    if (it == null) {
      return false;
    }
    return find(it.getName());
  }

  public static boolean find(String key) {
    if (key == null) {
      return false;
    }
    if (table.get(key) != null || ArmsTable.find(key)) {
      return true;
    }
    System.err.println(
        String.valueOf(
            String.valueOf(
                new StringBuffer("GearTable could not find item=[").append(key).append("]"))));
    return false;
  }

  void add(String name, int type, int cost) {
    add(new GearRecord(this, name, type, cost, 0));
  }

  void add(String name, int type, int cost, int effect) {
    add(new GearRecord(this, name, type, cost, effect));
  }

  void add(GearRecord rec) {
    if (table.get(rec.getName()) != null) {
      System.err.println(
          String.valueOf(
              String.valueOf(
                  new StringBuffer("GearTable duplicate key=[")
                      .append(rec.getName())
                      .append("]"))));
    } else {
      table.put(rec.getName(), rec);
    }
  }

  static GearRecord get(String key) {
    GearRecord rec = (GearRecord) table.get(key);
    return rec == null ? unknown : rec;
  }

  public static itList findList(String name, int type) {
    Enumeration keys = table.keys();
    itList list = new itList(name);
    while (keys.hasMoreElements()) {
      GearRecord rec = get((String) keys.nextElement());
      if (rec.getType() == type) {
        list.fix(rec.getName(), rec.getCost());
      }
    }
    return list;
  }

  public static Vector findVector(int type) {
    Enumeration keys = table.keys();
    Vector list = new Vector();
    while (keys.hasMoreElements()) {
      GearRecord rec = get((String) keys.nextElement());
      if (rec.getType() == type) {
        list.addElement(rec.getName());
      }
    }
    return list;
  }

  public static Item shopItem(Item what) {
    return shopItem(what.getName());
  }

  public static Item shopItem(String key) {
    GearRecord rec = get(key);
    return rec == unknown ? ArmsTable.shopItem(key) : new itCount(key, rec.getCost());
  }

  public static int getType(Item what) {
    return get(what.getName()).getType();
  }

  public static int getCost(Item what) {
    return get(what.getName()).getCost();
  }

  public static int getEffect(Item what) {
    return get(what.getName()).getEffect();
  }

  public static boolean isScroll(Item what) {
    return getType(what) == 7;
  }

  public static boolean canHeroUse(Item what) {
    return getEffect(what) != 0;
  }

  public static boolean canMageUse(Item what) {
    return canMageUse(what.getName());
  }

  public static String effectLabel(Item what) {
    return GearTypes.effectLabel[getEffect(what)];
  }

  public static int getType(String key) {
    return get(key).getType();
  }

  public static int getCost(String key) {
    return get(key).getCost();
  }

  public static int getEffect(String key) {
    return get(key).getEffect();
  }

  public static boolean canMageUse(String key) {
    int skill = Tools.getHero().magicRank();
    return getType(key) == 5 && (skill * skill) * 25 >= getCost(key);
  }

  synchronized void buildTable() {
    if (table == null) {
      table = new Hashtable();
      unknown = new GearRecord(this, "Unknown", 0, 0, 0);
      add("Marks", 9, 1);
      add("Map to Warrens", 1, 500);
      add("Map to Treasury", 1, 2000);
      add("Map to Throne Room", 1, 5000);
      add("Map to Vortex", 1, 10000);
      add("Rutter for Hie Brasil", 1, 6000);
      add("Rutter for Shangala", 1, 12000);
      add("Time Crystal", 1, 18000);
      add("Castle Permit", 1, 5000);
      add("Sleeping Bag", 2, 25);
      add("Cooking Gear", 2, 75);
      add("Camp Tent", 2, 150);
      add(GearTypes.FOOD, 3, 2, 21);
      add(GearTypes.FISH, 3, 2, 21);
      add("Torch", 3, 5);
      add("Rope", 3, 8);
      add("Pen & Paper", 3, 12, 14);
      add("Teeth", 4, 2);
      add("Tusk", 4, 200);
      add("Gold Nugget", 4, Tools.DEFAULT_WIDTH);
      add("Horn", 4, 600);
      add("Crystal Crown", 4, 2500);
      add("Platinum Ring", 4, 8000);
      add("Dragon Scales", 4, 5000);
      add("Quartz", 5, 100, 1);
      add("Opal", 5, 250, 2);
      add("Garnet", 5, 650, 3);
      add("Emerald", 5, 1250, 5);
      add("Ruby", 5, 2400, 4);
      add("Turquoise", 5, 3500, 6);
      add("Diamond", 5, 5000, 10);
      add(GearTypes.SALVE, 6, 150, 2);
      add(GearTypes.SELTZER, 6, 200, 3);
      add("Gold Apple", 6, 500, 7);
      add(GearTypes.GINSENG, 6, 1000, 8);
      add(GearTypes.MANDRAKE, 6, 2000, 9);
      add("Cookie", 6, 1, 10);
      add(GearTypes.BLIND_DUST, 6, Tools.DEFAULT_WIDTH, 4);
      add(GearTypes.PANIC_DUST, 6, 800, 5);
      add(GearTypes.BLAST_DUST, 6, 2000, 6);
      add("Youth Elixir", 6, 8000, 11);
      add("Aging Elixir", 6, 1000, 12);
      add("Faceless Potion", 6, 1500, 13);
      add("Identify Scroll", 7, 60, 1);
      add("Glow Scroll", 7, Tools.DEFAULT_HEIGHT, 15);
      add("Bless Scroll", 7, 1000, 16);
      add("Luck Scroll", 7, 1500, 17);
      add("Flame Scroll", 7, 3500, 18);
      add("Enchant Scroll", 7, 2500, 19);
      add("Bottled Faery", 8, 800);
      add(GearTypes.INSURANCE, 8, 1000);
      add(GearTypes.TOKEN, 8, 0);
      add("Postcard", 8, 0);
      add("Letter", 8, 0);
      add("Petition", 8, 0);
      add("Grant", 8, 0, 20);
      add("Denial", 8, 0);
      add("Gobble Inn Postcard", 0, 50, 14);
      add("Gobble Inn T-Shirt", 0, 100);
      add("Troll Wart", 0, 0);
      add("Turnip", 0, 0);
      add("Rock", 0, 0);
    }
  }

  /* loaded from: DCourt.jar:DCourt/Control/GearTable$GearRecord.class */
  public class GearRecord {
    String name;
    int type;
    int cost;
    int effect;

    GearRecord(GearTable this$0, String name, int type, int cost, int effect) {
      this.name = name;
      this.type = type;
      this.cost = cost;
      this.effect = effect;
    }

    String getName() {
      return this.name;
    }

    int getType() {
      return this.type;
    }

    int getCost() {
      return this.cost;
    }

    int getEffect() {
      return this.effect;
    }
  }
}
