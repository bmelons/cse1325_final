package cse1325final;

import java.awt.BorderLayout;
import java.awt.Color;

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
	public UIList() {
		super();
		this.setLayout(new BorderLayout(3,3));
		this.setBorder(new LineBorder(Color.black, 2));
		
		this.columnModel = new DefaultTableModel(); // empty columns
		this.list = new JTable(this.columnModel);
		
		this.title = "Unconfigured Table";
		this.titleLabel = new JLabel(this.title);
		
		// intending to come back and add actions to PAGE_END before commiting, may not be able to get to it before a class
		
		add(this.titleLabel,BorderLayout.PAGE_START);
		add(this.list,BorderLayout.CENTER);
		
		
	}

}
