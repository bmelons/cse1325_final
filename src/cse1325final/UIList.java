package cse1325final;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private String[] columns;
//	private boolean isEditing;
//	private String specifiedType;
//	private Object[] elements;
//	private String searchColumn;
	private JTextField searchBar;
	private JTable table;	
	private DefaultTableModel columnModel; // column manager
	
	public UIList(String aTitle) {
		super();
		this.setLayout(new BorderLayout(3,3));
		this.setBorder(new LineBorder(Color.black, 2));
		
		
		this.columnModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevents editing on all cells
            }
        }; // empty columns
		this.table = new JTable(this.columnModel);
		
		this.title = aTitle==null ? "Unconfigured Table" : aTitle;
		this.titleLabel = new JLabel(this.title);
		
		JPanel actionPanel = new UIPlaceholderButtons(2);
		actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JScrollPane tableContainer = new JScrollPane(table);
		
		add(this.titleLabel,BorderLayout.PAGE_START);
		add(tableContainer,BorderLayout.CENTER);
		add(actionPanel,BorderLayout.PAGE_END);
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				if (event.getClickCount() == 2) {
					System.out.println("Double click detected");
				}
			}
		};
		this.table.addMouseListener(mouseAdapter);

	}
	
	public void onSelected() {
		int getSelection = this.table.getSelectedRow();
		
		return;
		
	}
	
	public UIList(String aTitle, String[] columns) {
		this(aTitle);
		this.setColumns(columns);
	}
	
	public UIList() {
		this("--");
	}
	
	
	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] aColumns) {
		this.columns = aColumns;
		this.columnModel.setColumnCount(0);
		for (String column : this.columns) {
			columnModel.addColumn(column);
		}	
	}
	
	public void addRow(Object[] rowData) {
		this.columnModel.addRow(rowData);
		return;
	}

}
