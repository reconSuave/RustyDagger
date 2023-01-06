package DCourt.Tools;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.Vector;

/* loaded from: DCourt.jar:DCourt/Tools/StaticLayout.class */
public class StaticLayout implements LayoutManager {
    Vector list = new Vector();

    public void addLayoutComponent(String name, Component cmp) {
        this.list.addElement(cmp);
    }

    public void removeLayoutComponent(Component cmp) {
        this.list.removeElement(cmp);
    }

    private Component getElement(int ix) {
        return (Component) this.list.elementAt(ix);
    }

    public void layoutContainer(Container cont) {
        if (Tools.getJvmVersion() < 2) {
            int num = this.list.size();
            for (int ix = 0; ix < num; ix++) {
                getElement(ix).repaint();
            }
            return;
        }
        int num2 = cont.getComponentCount();
        for (int ix2 = 0; ix2 < num2; ix2++) {
            cont.getComponent(ix2).repaint();
        }
    }

    public Dimension minimumLayoutSize(Container cont) {
        return new Dimension(cont.bounds().width, cont.bounds().height);
    }

    public Dimension preferredLayoutSize(Container cont) {
        return new Dimension(cont.bounds().width, cont.bounds().height);
    }
}
