package DCourt.Control;

import DCourt.Items.Item;
import DCourt.Items.List.itMonster;
import DCourt.Screens.Quest.Quests;
import DCourt.Screens.Quest.VQuests;
import DCourt.Tools.Tools;
import java.util.Hashtable;

/* loaded from: DCourt.jar:DCourt/Control/MonsterTable.class */
public class MonsterTable implements Quests, VQuests {
  static Hashtable table = null;
  static boolean loading = false;

  public MonsterTable() {
    buildTable();
  }

  public static boolean isLoading() {
    return loading;
  }

  public static itMonster find(String key) {
    itMonster what = (itMonster) table.get(key);
    if (what != null) {
      return what;
    }
    System.err.println(
        String.valueOf(
            String.valueOf(
                new StringBuffer("MonsterTable could not find key=[").append(key).append("]"))));
    return null;
  }

  void add(String key, String info) {
    Item it = Item.factory(info);
    if (it == null || !(it instanceof itMonster)) {
      System.err.println(
          String.valueOf(
              String.valueOf(
                  new StringBuffer("MonsterTable bad item = [").append(info).append("]"))));
    } else if (table.get(key) != null) {
      System.err.println(
          String.valueOf(
              String.valueOf(
                  new StringBuffer("MonsterTable duplicate key=[").append(key).append("]"))));
    } else {
      ((itMonster) it).testGear();
      table.put(key, it);
    }
  }

  synchronized void buildTable() {
    if (table != null) {
      loading = false;
      Tools.repaint();
      return;
    }
    table = new Hashtable();
    add("Town:Guard", Quests.townGuard);
    add("Castle:Guard", Quests.castleGuard);
    add("Vortex:Guard", Quests.vortexGuard);
    add("Faery", Quests.faeryRing);
    add("Fields:Rodent", Quests.fieldRodent);
    add("Fields:Goblin", Quests.fieldGoblin);
    add("Fields:Gypsy", Quests.fieldGypsy);
    add("Fields:Centaur", Quests.fieldCentaur);
    add("Fields:Merchant", Quests.fieldMerchant);
    add("Fields:Wizard", Quests.fieldWizard);
    add("Fields:Soldier", Quests.fieldSoldier);
    Tools.repaint();
    add("Forest:Boar", Quests.forestBoar);
    add("Forest:Orc", Quests.forestOrc);
    add("Forest:Elf", Quests.forestElf);
    add("Forest:Gryphon", Quests.forestGryphon);
    add("Forest:Snot", Quests.forestSnot);
    add("Forest:Unicorn", Quests.forestUnicorn);
    add("Mound:Gate", Quests.moundGate);
    add("Mound:Gang", Quests.moundGang);
    add("Mound:Rager", Quests.moundRager);
    add("Mound:Thief", Quests.moundThief);
    add("Mound:Worm", Quests.moundWorm);
    add("Mound:Mage", Quests.moundMage);
    add("Mound:Guard", Quests.moundGuard);
    add("Mound:Vault", Quests.moundVault);
    add("Mound:Champ", Quests.moundChamp);
    add("Mound:Queen", Quests.moundQueen);
    Tools.repaint();
    add("Hills:Goat", Quests.hillsGoat);
    add("Hills:Basilisk", Quests.hillsBasilisk);
    add("Hills:Wyvern", Quests.hillsWyvern);
    add("Hills:Troll", Quests.hillsTroll);
    add("Hills:Sphinx", Quests.hillsSphinx);
    add("Hills:Giant", Quests.hillsGiant);
    add("Hills:Dragon", Quests.hillsDragon);
    add("Dunjeon:Rodent", VQuests.dungRodent);
    add("Dunjeon:Snot", VQuests.dungSnot);
    add("Dunjeon:Rager", VQuests.dungRager);
    add("Dunjeon:Gang", VQuests.dungGang);
    add("Dunjeon:Troll", VQuests.dungTroll);
    add("Dunjeon:Mage", VQuests.dungMage);
    Tools.repaint();
    add("Ocean:Traders", VQuests.seaTraders);
    add("Ocean:Serpent", VQuests.seaSerpent);
    add("Ocean:Mermaid", VQuests.seaMermaid);
    add("Brasil:Harpy", VQuests.braHarpy);
    add("Brasil:Fighter", VQuests.braFighter);
    add("Brasil:Golem", VQuests.braGolem);
    add("Brasil:Medusa", VQuests.braMedusa);
    add("Brasil:Hero", VQuests.braHero);
    Tools.repaint();
    add("Shang:Gunner", VQuests.shaGunner);
    add("Shang:Plague", VQuests.shaPlague);
    add("Shang:Peasant", VQuests.shaPeasant);
    add("Shang:Ninja", VQuests.shaNinja);
    add("Shang:Shogun", VQuests.shaShogun);
    add("Shang:Panda", VQuests.shaPanda);
    add("Shang:Samurai", VQuests.shaSamurai);
    loading = false;
    Tools.repaint();
  }
}
