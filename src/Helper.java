import javax.swing.*;
import java.awt.*;

public class Helper {

    public static GridBagConstraints createCenteredGridConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        return gbc;
    }
}
