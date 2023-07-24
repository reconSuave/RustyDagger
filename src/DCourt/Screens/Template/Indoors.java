package DCourt.Screens.Template;

import DCourt.Components.Portrait;
import DCourt.Screens.Screen;
import DCourt.Tools.Tools;
import java.awt.Color;
import java.awt.Event;

/* loaded from: DCourt.jar:DCourt/Screens/Template/Indoors.class */
public abstract class Indoors extends Screen {
    public abstract String getGreeting();

    public abstract String getFace();

    public Indoors(Screen from, String name) {
        super(from, name);
        setBackground(new Color(128, 255, 129));
        setForeground(new Color(0, 128, 0));
        addPic(new Portrait("Exit.jpg", 320, 10, 64, 32));
        addPic(new Portrait(getFace(), getGreeting(), 10, 30, 144, 192));
    }

    @Override // DCourt.Screens.Screen
    public boolean action(Event e, Object o) {
        if (Tools.movedAway(this)) {
            return true;
        }
        if (e.target == getPic(0)) {
            Tools.setRegion(getHome());
        }
        return action(e, o);
    }
}
