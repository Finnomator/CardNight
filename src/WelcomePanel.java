import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel implements ActionListener {
    private JButton startButton;
    private JButton creditsButton;
    private JButton exitButton;
    private MainWindow mainWindow;

    public WelcomePanel(MainWindow mainWindow) {

        this.mainWindow = mainWindow;

        startButton = new JButton("Start");
        creditsButton = new JButton("Credits");
        exitButton = new JButton("Exit");

        startButton.addActionListener(this);
        creditsButton.addActionListener(this);
        exitButton.addActionListener(this);

        GridBagConstraints gbc = Helper.createCenteredGridConstraints();

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.add(startButton, gbc);
        buttonsPanel.add(creditsButton, gbc);

        setPreferredSize(new Dimension(1280, 720));
        setLayout(new GridBagLayout());

        add(buttonsPanel, gbc);
        add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sender = e.getSource();
        if (sender == startButton) {
            mainWindow.showPanel("startPanel");
        } else if (sender == creditsButton) {
            mainWindow.showPanel("creditsPanel");
        } else if (sender == exitButton) {
            System.exit(0);
        }
    }
}
