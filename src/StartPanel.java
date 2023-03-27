import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel implements ActionListener {

    private MainWindow mainWindow;
    private JButton backButton;
    private JButton ticTacToeButton;

    public StartPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        backButton = new JButton("Back");
        backButton.addActionListener(this);

        ticTacToeButton = new JButton("Tic Tac Toe");

        JPanel gameButtons = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = Helper.createCenteredGridConstraints();

        gameButtons.add(ticTacToeButton, gbc);

        setLayout(new GridBagLayout());

        add(gameButtons, gbc);
        add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sender = e.getSource();
        if (sender == backButton) {
            mainWindow.showPanel("welcomePanel");
        }
    }
}
