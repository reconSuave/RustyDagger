package DCourt.Screens.Areas.Queen;

import DCourt.Screens.Screen;
import DCourt.Screens.Utility.arNotice;

/* loaded from: DCourt.jar:DCourt/Screens/Areas/Queen/arqStudy.class */
public class arqStudy extends arNotice {
  static final String studyMsg =
      "$TB$You engage in a philosophical dispute with $lordname$ upon $topic$.$CR$";
  static final String[] boastText = {
    "0",
    "1",
    "2",
    "3",
    "$TB$The debate waxes and wanes, other nobles enter the argument to press their points.  "
  };

  public arqStudy(Screen from) {
    super(from, studyMsg);
  }
}
