import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CreditsPanel extends JPanel implements ActionListener {

    private JLabel creditsLabel;
    private JButton backButton;
    private MainWindow mainWindow;
    private String creditsMessage = "<html>Developed by:<br>Finn<br>Chris<Br>David<Br>Mark";

    public CreditsPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        creditsLabel = new JLabel(creditsMessage);
        backButton = new JButton("Back");
        backButton.addActionListener(this);

        GridBagConstraints gbc = Helper.createCenteredGridConstraints();

        JPanel creditsPanel = new JPanel(new GridBagLayout());
        creditsPanel.add(creditsLabel, gbc);

        setLayout(new GridBagLayout());

        add(creditsPanel, gbc);
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
