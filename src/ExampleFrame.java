import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExampleFrame implements ActionListener {

    private final Frame frame;
    private final Button button;
    private final TextField clicks_text_field;
    private int clicks;

    public ExampleFrame() {
        frame = new Frame("Example Frame");
        Panel panel = new Panel();
        button = new Button("Click Me");
        button.addActionListener(this);
        clicks_text_field = new TextField("You clicked the Button 0 times");

        panel.add(button);
        panel.add(clicks_text_field);

        // closes the window when close button gets pressed
        frame.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                frame.dispose();
            }
        });
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var source = e.getSource();
        if (source == button) {
            ++clicks;
            String msg = "You clicked the button " + clicks + " time";

            if (clicks != 1)
                msg += "s";

            clicks_text_field.setText(msg);
        }
    }
}
