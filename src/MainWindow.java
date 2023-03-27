import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private WelcomePanel welcomePanel;
    private CreditsPanel creditsPanel;
    private StartPanel startPanel;

    public MainWindow() {
        setTitle("Very Epic Game Title");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel();

        welcomePanel = new WelcomePanel(this);
        creditsPanel = new CreditsPanel(this);
        startPanel = new StartPanel(this);

        cardPanel.setLayout(cardLayout);

        cardPanel.add(welcomePanel, "welcomePanel");
        cardPanel.add(creditsPanel, "creditsPanel");
        cardPanel.add(startPanel, "startPanel");


        add(cardPanel);
        pack();
        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(cardPanel, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
