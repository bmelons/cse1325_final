package cse1325final;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class UIList extends JPanel {
	// like most ui prefix classes, extends a jcomponent so you can add this as a child of something else
	
	// will be sure to document as this is developed,
	//	we have *not* worked with JTables in class, so i need to make this class workable by whoever ends up writing the pieces of frontend that use it
	
	private String title;
	private JLabel titleLabel;
//	private String[] columns;
//	private boolean isEditing;
//	private String specifiedType;
//	private Object[] elements;
//	private String searchColumn;
	private JTextField searchBar;
	private JTable list;	
	private DefaultTableModel columnModel; // column manager
	
	public UIList(String aTitle) {
		super();
		this.setLayout(new BorderLayout(3,3));
		this.setBorder(new LineBorder(Color.black, 2));
		
		this.columnModel = new DefaultTableModel(); // empty columns
		this.list = new JTable(this.columnModel);
		
		this.title = "Unconfigured Table";
		this.titleLabel = new JLabel(this.title);
		
		JPanel actionPanel = new UIPlaceholderButtons(2);
		actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));;
		
		add(this.titleLabel,BorderLayout.PAGE_START);
		add(this.list,BorderLayout.CENTER);
		add(actionPanel,BorderLayout.PAGE_END);
	}
	
	public UIList() {
		this("--");
	}

}
