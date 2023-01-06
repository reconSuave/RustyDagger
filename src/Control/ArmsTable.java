package DCourt.Control;

import DCourt.Items.Item;
import DCourt.Items.List.itArms;
import java.util.Hashtable;

/* loaded from: DCourt.jar:DCourt/Control/ArmsTable.class */
public class ArmsTable {
    static Hashtable table = null;

    public ArmsTable() {
        buildTable();
    }

    void add(String info) {
        Item it = Item.factory(info);
        if (it == null || !(it instanceof itArms)) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("ArmsTable bad item = [").append(info).append("]"))));
        } else if (table.get(it.getName()) != null) {
            System.err.println(String.valueOf(String.valueOf(new StringBuffer("ArmsTable duplicate key = [").append(it.getName()).append("]"))));
        } else {
            table.put(it.getName(), it);
        }
    }

    public static itArms get(itArms it) {
        return get(it.getName());
    }

    public static itArms get(String key) {
        itArms what = (itArms) table.get(key);
        if (what != null) {
            return what;
        }
        System.err.println(String.valueOf(String.valueOf(new StringBuffer("ArmsTable could not find key=[").append(key).append("]"))));
        return null;
    }

    public static boolean find(itArms it) {
        return find(it.getName());
    }

    public static boolean find(String key) {
        if (table.get(key) != null) {
            return true;
        }
        System.err.println(String.valueOf(String.valueOf(new StringBuffer("ArmsTable could not find item=[").append(key).append("]"))));
        return false;
    }

    public static itArms shopItem(String key) {
        itArms what = get(key);
        if (what == null) {
            return null;
        }
        return (itArms) what.copy();
    }

    synchronized void buildTable() {
        if (table == null) {
            table = new Hashtable();
            add("{itArms|Knife|2|0|-1|right}");
            add("{itArms|Hatchet|4|0|-1|right}");
            add("{itArms|Short Sword|5|0|0|right}");
            add("{itArms|Long Sword|7|0|0|right}");
            add("{itArms|Spear|9|0|1|right}");
            add("{itArms|Broad Sword|11|0|1|right}");
            add("{itArms|Battle Axe|13|0|1|right|left}");
            add("{itArms|Pike|15|0|2|right|left}");
            add("{itArms|Silver Pike|30|0|2|right}");
            add("{itArms|Sling|4|0|4|right}");
            add("{itArms|Short Bow|8|0|6|right|left}");
            add("{itArms|Long Bow|12|0|8|right|left}");
            add("{itArms|Spike Helm|3|5|-2|head}");
            add("{itArms|Main Gauche|2|2|1|left}");
            add("{itArms|Clothes|0|2|0|body}");
            add("{itArms|Leather Jacket|0|4|0|body}");
            add("{itArms|Brigandine|0|6|-1|body}");
            add("{itArms|Chain Suit|0|8|-2|body}");
            add("{itArms|Scale Suit|0|10|-2|body}");
            add("{itArms|Buckler|0|2|-1|left}");
            add("{itArms|Targe|0|4|-1|left}");
            add("{itArms|Shield|0|6|-2|left}");
            add("{itArms|Spike Shield|3|5|-3|left}");
            add("{itArms|Sandals|0|0|3|feet}");
            add("{itArms|Shoes|0|2|5|feet}");
            add("{itArms|Boots|0|4|7|feet}");
            add("{itArms|Leather Cap|0|2|-1|head}");
            add("{itArms|Pot Helm|0|4|-2|head}");
            add("{itArms|Chain Coif|0|6|-3|head}");
            add("{itArms|Steel Sword|15|0|-3|right}");
            add("{itArms|Bill Hook|17|0|5|right|left}");
            add("{itArms|Sword Breaker|6|6|3|left}");
            add("{itArms|Shakrum|13|0|7|right}");
            add("{itArms|Recurve Bow|16|0|9|right|left}");
            add("{itArms|Half Plate|0|12|-6|body}");
            add("{itArms|Full Plate|0|15|-9|body}");
            add("{itArms|Steel Buckler|0|5|-1|left}");
            add("{itArms|Roman Helm|0|7|-3|head}");
            add("{itArms|Doc Martins|4|6|5|feet}");
            add("{itArms|Mercury Sandals|0|0|20|feet}");
            add("{itArms|Rusty Dagger|3|0|-1|right|decay}");
            add("{itArms|Throwing Knife|5|0|5|right}");
            add("{itArms|Silver Throwing Knife|10|0|10|right|panic}");
            add("{itArms|Staff|5|0|3|right|left}");
            add("{itArms|War Tusk|7|6|-3|left}");
            add("{itArms|Elf Bow|21|0|10|right|left}");
            add("{itArms|Silver Elf Bow|42|0|10|right|left|blast}");
            add("{itArms|Mithril Mail|0|13|-2|body}");
            add("{itArms|Dwarf Axe|13|2|5|right|lucky}");
            add("{itArms|Unicorn Horn|17|0|8|right|bless}");
            add("{itArms|Cross Bow|20|0|20|right|left}");
            add("{itArms|Miners Cap|0|12|-5|head|glows}");
            add("{itArms|Goblin Pick|18|0|0|right}");
            add("{itArms|Weird Knife|12|8|12}");
            add("{itArms|Silver Weird Knife|25|8|12|left|disease}");
            add("{itArms|Goblin Shield|0|13|-5|left}");
            add("{itArms|War Boots|7|8|10|feet}");
            add("{itArms|Goblin Pike|22|0|5|right}");
            add("{itArms|Magic Staff|15|10|12|right|left|bless|lucky}");
            add("{itArms|Silver Staff|30|10|12|right|bless|lucky}");
            add("{itArms|Magic Robes|0|10|3|head|bless|lucky}");
            add("{itArms|Goblin Mithril|0|15|-5}");
            add("{itArms|Flaming Sword|15|0|5|right|flames}");
            add("{itArms|Goblin Plate|0|18|-5|bless}");
            add("{itArms|Terror Rod|17|0|8|right|glows|panic}");
            add("{itArms|Rams Horn|7|13|-8|left}");
            add("{itArms|Giant Maul|45|0|-20|right|left}");
            add("{itArms|Silver Giant Maul|90|0|-20|right}");
            add("{itArms|Rat Tail Whip|25|6|25|right|disease}");
            add("{itArms|Silver Tail Whip|50|12|50|right|disease}");
            add("{itArms|Dragon Shield|0|25|18|left}");
            add("{itArms|Great Pike|54|0|15|right|left}");
            add("{itArms|Mystic Staff|25|25|25|right|left|bless|lucky}");
            add("{itArms|Mystic Robes|0|25|12|body|bless|lucky}");
            add("{itArms|Great Bow|50|0|35|right|left}");
            add("{itArms|Serpent Scale|0|20|25|left}");
            add("{itArms|Sea Slippers|0|10|100|feet}");
            add("{itArms|Silver Sea Slippers|0|20|200|feet}");
            add("{itArms|Gladius|50|0|20|right}");
            add("{itArms|Silver Gladius|100|0|20|right|blind}");
            add("{itArms|Great Targe|0|35|10|left}");
            add("{itArms|Snake Scale Suit|0|50|-15|body}");
            add("{itArms|Matchlock Rifle|80|0|80|right|left|blast}");
            add("{itArms|Foul Axe|30|0|15|right|disease}");
            add("{itArms|Asaura|0|20|12|feet}");
            add("{itArms|Nunchaku|25|0|30|right}");
            add("{itArms|Shuriken|30|0|50|left}");
            add("{itArms|Spirit Katana|50|0|80|right|panic}");
            add("{itArms|Masamune|60|0|40|right}");
            add("{itArms|Silver Masamune|120|0|40|right|panic}");
            add("{itArms|Koutetsu|10|60|-20|body}");
        }
    }
}
