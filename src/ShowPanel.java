
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import java.util.Vector;
public class ShowPanel extends JFrame  {
	//Deklaracja obiektow
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	public ShowPanel() {
		
	//Obsluga JTabel
		super("Showing");
		Vector<String> label=new Vector<String>();
		label.add("Index");
		label.add("Name");
		label.add("Password");
		label.add("Address");
		label.add("Email");
		label.add("Pesel");
		model=new DefaultTableModel(label,0);
		table=new JTable(model);
		table.setBounds(100,50,200,450);
		JScrollPane sp=new JScrollPane(table);  
		this.add(sp);
		this.setSize(300,400); }
		
	//Dodawanie rekordow do wyswietlenia
	public void upgradeJTable(Vector<Vector<String>> vector,int counter) {
		
		
			
model.addRow(vector.get(counter));

		
		
		
		
		
		
		
	}



}


