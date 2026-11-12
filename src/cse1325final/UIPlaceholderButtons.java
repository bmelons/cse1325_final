package cse1325final;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class UIPlaceholderButtons extends JPanel {
	private static final String[] dummyTexts = {"Action", "An Action", "BTTN", "Too Long Button Text"}; // to see how swing handles different lengths of buttons
	
    public UIPlaceholderButtons(int n) { // i need fake buttons to make sure the ui works right with real buttons
        // flow layout arranges components sequentially in a single horizontal row
        setLayout(new FlowLayout(FlowLayout.CENTER));
        Random random = new Random();

        for (int i = 1; i <= n; i++) {
            JButton button = new JButton("");
            button.setText( dummyTexts[random.nextInt(dummyTexts.length)] );
            add(button);
        }

        setBorder(new LineBorder(Color.black, 1));
    }
}