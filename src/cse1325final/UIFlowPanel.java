package cse1325final;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class UIFlowPanel extends JPanel {
	// subclass to quickly define horizontally flowing panels
	public UIFlowPanel() { 
		super();
    	// flow layout arranges components sequentially in a single horizontal row
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBorder(new LineBorder(Color.black, 1));
    }

}
