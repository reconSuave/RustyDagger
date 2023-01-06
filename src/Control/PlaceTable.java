package DCourt.Control;

import DCourt.Screens.Screen;
import DCourt.Static.Constants;
import java.util.Hashtable;

/* loaded from: DCourt.jar:DCourt/Control/PlaceTable.class */
public class PlaceTable {
    Hashtable table = null;
    PlaceRecord pick;

    public PlaceTable() {
        buildTable();
        select(Constants.FIELDS);
    }

    void addTable(PlaceRecord place) {
        this.table.put(place.getName(), place);
    }

    public void select(String key) {
        this.pick = key == null ? null : (PlaceRecord) this.table.get(key);
        if (this.pick == null) {
            this.pick = (PlaceRecord) this.table.get("limbo");
        }
    }

    public String getName() {
        return this.pick.getName();
    }

    public String getUse() {
        return this.pick.getUse();
    }

    public String getAwake() {
        return this.pick.getAwake();
    }

    public String getSleep() {
        return this.pick.getSleep();
    }

    public int getDecay() {
        return this.pick.getDecay();
    }

    public Screen getLaunch() {
        if (this.pick == null || this.pick.getLaunch() == null) {
            return null;
        }
        try {
            return (Screen) Class.forName("DCourt.Screens.".concat(String.valueOf(String.valueOf(this.pick.getLaunch())))).newInstance();
        } catch (Exception ex) {
            System.err.println("PlaceTable.getLaunch(): ".concat(String.valueOf(String.valueOf(ex))));
            ex.printStackTrace();
            return null;
        }
    }

    synchronized void buildTable() {
        if (this.table == null) {
            this.table = new Hashtable();
            addTable(new PlaceRecord(this, Constants.SUITE, "Areas.arTown", 6, "s", "\tYou awaken in a tavern suite, enjoy a leisurely bath, then saunter down to the common room to break your fast.\n", "\tAfter a sumptuous meal, you enjoy a lazy bath, flirting with the attendant, then lay yourself down on the down filled mattresses.  You fall asleep instantly."));
            addTable(new PlaceRecord(this, Constants.ROOM, "Areas.arTown", 5, "s", "\tYou awaken in a small room, listen for a moment to the sounds of the morning crowd tromping around, then get up.\n", "\tYou eat dinner, down a pint, wash your hands and face, then lay down on the hay filled mattress and close your eyes.\n"));
            addTable(new PlaceRecord(this, Constants.FLOOR, "Areas.arTown", 4, "b", "\tYou awaken on the tavern floor with the taste of smoke in your mouth.  You have to scramble to evade being trampled by the morning crowd, just arriving for breakfast.\n", "\tYou bundle up your cloak and find a relatively unsoiled section of floor to sleep on.\n"));
            addTable(new PlaceRecord(this, Constants.TOWN, "Areas.arTown", 3, "", "\tYou crawl from the alley where your body has been laying all night, stretch your newly revived limbs, and go about your business.\n", "\tYou drop dead in the town.\n"));
            addTable(new PlaceRecord(this, Constants.FIELDS, "Wilds.arField", 3, "cb", "\tYou awaken in a grassy meadow, soaked head to toe in morning dew.\n", "\tYou set camp on a grassy knoll, then watch the the sun set in radiant red and gold.\n"));
            addTable(new PlaceRecord(this, Constants.FOREST, "Wilds.arForest", 2, "cb", "\tYou awaken under a bush, itching horribly from insect bites.\n\n\tWhy in the name of heaven did you decide to come to this wretched place?\n", "\tYou find a hollow tree, drive out the spiders and squirrels, then hole up in a cramped position.\n"));
            addTable(new PlaceRecord(this, Constants.MOUND, "Wilds.arMound", 2, "b", "\tYou awaken under a bench in the Gobble Inn.  You itch horribly from numerous insect bites.\n\n\tWhy in the name of heaven did you decide to come to this wretched place?\n", "\tYou sink to the bottom of degradation.  Being utterly broke and exhausted you slump into a corner of the Gobble Inn.  You have to squabble with a tame berzerker for a spot near the fire.\n"));
            addTable(new PlaceRecord(this, Constants.COT, "Wilds.arMound", 3, "bc", "\tYou awaken on a cramped and smelly cot. Smidgen Crumb is eyeing your purse with a calculated stare.\n", "\tYou shell out a small fortune for a small and suspiciously odiferous cot.  You down your meal of uncooked radishes, then pull the thin covers over your shivering form\n"));
            addTable(new PlaceRecord(this, Constants.HILLS, "Wilds.arHills", 2, "bc", "\tYou roll over and nearly fall off a cliff before snapping to your senses in a heady rush of adrenalin.\n", "\tYou find a drafty cave that stinks of large predators.  You huddle next to a make-shift fire and descend into a nightmarish stupor.\n"));
            addTable(new PlaceRecord(this, Constants.DOCKS, "Wilds.arCastle", 2, "", "\tYou come to consciousness on a rain swept beach.  After walking for several hours, you arrive back at the docks.\n", "Sleep Docks"));
            addTable(new PlaceRecord(this, Constants.DUNJEON, "Wilds.arCastle", 2, "b", "\tYou come to awareness in darkness with a foul stench. You crawl towards a distant light, finally emerging from a sewer into the castle.\n", "Sleep Dunjeon"));
            addTable(new PlaceRecord(this, "limbo", null, 3, "bc", "\tYour character has failed to load properly. Please report this error to the System Operator. \n\tWe RECOMMEND that you reload the game (hit refresh/reload in your browser), then wait five minutes before trying to load your hero again.\n\t(You Have Awakened In Limbo)", "\tYou have fallen into LIMBO, that strange region that can only be entered via programming errors. You go to sleep with a strong urge to report your problem to the world builders...\n"));
        }
    }

    /* loaded from: DCourt.jar:DCourt/Control/PlaceTable$PlaceRecord.class */
    public class PlaceRecord {
        String name;
        String launch;
        String use;
        String awake;
        String sleep;
        int decay;

        PlaceRecord(PlaceTable this$0, String name, String launch, int decay, String use, String awake, String sleep) {
            this.name = name;
            this.launch = launch;
            this.decay = decay;
            this.use = use;
            this.awake = awake;
            this.sleep = sleep;
        }

        String getName() {
            return this.name;
        }

        String getLaunch() {
            return this.launch;
        }

        String getUse() {
            return this.use;
        }

        String getAwake() {
            return this.awake;
        }

        String getSleep() {
            return this.sleep;
        }

        int getDecay() {
            return this.decay;
        }
    }
}
