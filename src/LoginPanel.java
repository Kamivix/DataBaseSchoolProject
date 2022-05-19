
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


public class LoginPanel extends JFrame {
	//deklaracja obiektów potrzebnych do aplikacji
	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
	private JTextField textField,password,searchNameField,searchIdField,emailField,addressField,peselField;
	private JLabel labelToLogin, labelToPassword,tittleLabel,registerLabel,idLabel,nameLabel,emailLabel,addressLabel,peselLabel;
	private JButton buttonToLogin,buttonToFind,buttonToDelete,buttonToUpdate;
	public  static Connection connection;
	private ShowPanel search=null;
	int counter=0;
	
	
	public static void main(String args[]) {
		//Polaczenie z baza danych o okreœlonej nazwie, jako okreslony uzytkownik z za³o¿onym has³em
	    try {
             connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo",
                "root", "PBDProjekt123!");
            }
		catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
	
	
//Swing dziala w osobnym w¹tku w stosunku do funkcji main dlatego wsyzstkie zmiany w aplikacji musz¹ byæ wykonywane w tym w¹tku
		EventQueue.invokeLater( new Runnable(){
			public void run() {
				try {
					//Konstruktor tworzacy glowny panel aplikacji
					LoginPanel frame=new LoginPanel(connection);
					frame.setVisible(true);
				
					
				}
				catch (Exception e) {
                    e.printStackTrace();
                }
				
			}
		});
	
		}
	
	
	public LoginPanel(Connection connect){
		//Wywolanie konstruktora klasy nadrzednej w celu nadania nazwy oknu
		super("Main Window");
		//Macierz do obslugi JTabel
		Vector<Vector<String>> findedData = new Vector<Vector<String>>(); 
		
		//Ustawienie rozmiarów przycisków, etykiet, rozmieszczenie wszystkich elementów
		contentPane=new JPanel();
		setBounds(190, 190, 1014, 597);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        labelToLogin=new JLabel("Name:");
        labelToLogin.setBounds(100,100,50,20);
        contentPane.add(labelToLogin);
        
        addressLabel=new JLabel("Address:");
        addressLabel.setBounds(100,250,50,20);
        contentPane.add(addressLabel);
        
        emailLabel=new JLabel("Email");
        emailLabel.setBounds(100,200,50,20);
        contentPane.add(emailLabel);
        
        peselLabel=new JLabel("Pesel:");
        peselLabel.setBounds(100,300,50,20);
        contentPane.add(peselLabel);
        
        tittleLabel=new JLabel("Find");
        tittleLabel.setBounds(700,50,50,20);
        contentPane.add(tittleLabel);
        
        registerLabel=new JLabel("Add");
        registerLabel.setBounds(150,50,50,20);
        contentPane.add(registerLabel);
        
        nameLabel=new JLabel("Name:");
        nameLabel.setBounds(650,100,100,20);
        contentPane.add(nameLabel);
        
        idLabel= new JLabel("Id:");
        idLabel.setBounds(650,150,100,20);
        contentPane.add(idLabel);
        
        emailField=new JTextField();
        emailField.setBounds(200,200,100,20);
        contentPane.add(emailField);
        
        addressField=new JTextField();
        addressField.setBounds(200,250,100,20);
        contentPane.add(addressField);
        
        peselField=new JTextField();
        peselField.setBounds(200,300,100,20);
        contentPane.add(peselField);
        
        searchNameField=new JTextField();
        searchNameField.setBounds(700,100,100,20);
        contentPane.add(searchNameField);
        
        searchIdField=new JTextField();
        searchIdField.setBounds(700,150,100,20);
        contentPane.add(searchIdField);
        
        textField=new JTextField();
        textField.setBounds(200,100,100,20);
        contentPane.add(textField);
        
       labelToPassword=new JLabel("Password:");
       labelToPassword.setBounds(100,150,100,20);
       contentPane.add(labelToPassword);
       
        password=new JPasswordField();
        password.setBounds(200,150,100,20);
        contentPane.add(password);
        
  
        buttonToLogin=new JButton("Add");
        buttonToLogin.setBounds(200,400,100,50);
        contentPane.add(buttonToLogin);
        //Listener nas³uchuj¹cy czy przycisk zosta³ wcisniety
        buttonToLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String name,passwordText,emailText,addressText,peselText;
        		//Pobranie danych z pól tekstowych
        		name=textField.getText();
  
        		passwordText=password.getText();
        		emailText=emailField.getText();
        		addressText=addressField.getText();
        		peselText=peselField.getText();
        		
        		try {
        			//Stworzenie instrukcji dla bazy MYSQL
        		PreparedStatement st = (PreparedStatement) connect
                        .prepareStatement("INSERT INTO student (name, password,address,Email,Pesel) VALUES (?,?,?,?,?)");
        			//W miejsce pytajników wstawiane s¹ poni¿sze zmienne 
                    st.setString(1, name);
                    st.setString(2, passwordText);
                    st.setString(3,addressText);
                    st.setString(4, emailText);
                    st.setString(5, peselText);
                    //Wykonanie instrukcji 
                   st.executeUpdate();
                   //Wyœwietlenie okna dialogowego
                   JOptionPane.showMessageDialog(buttonToLogin,"Added");
                   
        		}
        		catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
        		
        	}
        });
        
        buttonToDelete=new JButton("Delete");
        buttonToDelete.setBounds(700,450,100,50);
        contentPane.add(buttonToDelete);
        buttonToDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//automatyczne wnioskowanie typu 
        		var nameToDelete=searchNameField.getText();
        		
        		try {
            		PreparedStatement st = (PreparedStatement) connect
                            .prepareStatement("DELETE from student where name=?");
                        st.setString(1, nameToDelete);
                        st.executeUpdate();
                        //Po usunieciu osoby instrukcja ponumeruje id na nowo 
                       String tabel[]= {"SET  @num := 0;","UPDATE student SET id = @num := (@num+1);","ALTER TABLE student AUTO_INCREMENT =1;"};
                       for(int i=0;i<tabel.length;i++) {
                       st = (PreparedStatement) connect.prepareStatement(tabel[i]);
                       st.executeUpdate();
                       }

                       JOptionPane.showMessageDialog(buttonToDelete,"Deleted");
                       st.executeUpdate();
            		}
            		catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
        		
        	}
        });
        //Dodanie listenera do pola tekstowego aby wyszukiwaæ dynamiczne po Id
        searchIdField.getDocument().addDocumentListener(new DocumentListener() {
      
        	  public void changedUpdate(DocumentEvent e) {
        		  System.out.println("do");
        	    
        	  }
        	  //Ignorowanie kasowania danych z pola tekstowego
       	  public void removeUpdate(DocumentEvent e) {
       	    System.out.println("DO nothing");
       	 
       	  }
       	  //Reaguje na wprowadzanie danych do pola tekstowego
   	  public void insertUpdate(DocumentEvent e) {
   		String dat;
   		//Vektor pól tekstowych
   		Vector<JTextField> vect=new Vector<JTextField>();
   		vect.add(textField);
   		vect.add(password);
   		vect.add(addressField);
   		vect.add(emailField);
   		vect.add(peselField);
   		
			  dat=searchIdField.getText();
   		  try {
   			  
   			  int idd=Integer.parseInt(dat);
   			
   		PreparedStatement statement = (PreparedStatement) connect
                .prepareStatement("SELECT * FROM student WHERE id=?");
   		statement.setInt(1,idd);
   		ResultSet RS= statement.executeQuery();
   		if (RS.next()) {
   			for (int count=0;count<=4;count++){
   				vect.get(count).setText(RS.getString(count+2));
   			}
   		}
   			
   		
   		  }
   		  catch(SQLException except) {
   			except.printStackTrace();
   		  }
   	
   	
      	  }
         	
        	});
 
        buttonToFind=new JButton("Find");
        buttonToFind.setBounds(700,400,100,50);
        contentPane.add(buttonToFind);
        
        buttonToFind.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String nameToFind,idToFind;
        		int idFind;
        		
        		nameToFind=searchNameField.getText();
        		if(nameToFind.isEmpty()) {
        			nameToFind="0";
        		}
        		idToFind=searchIdField.getText();
        		if(idToFind.isEmpty()) {
        		
        	
        			idFind=0;
        		}
        		else {
        		idFind=Integer.parseInt(idToFind); }
        		try {
            		PreparedStatement statement = (PreparedStatement) connect
                            .prepareStatement("SELECT * FROM student WHERE id=? OR name=?");
                        statement.setString(2, nameToFind);
                        statement.setInt(1, idFind);
                       ResultSet RS= statement.executeQuery();
                    //Jesli nie zostala otwarta tabela z osobam, otworz ja 
                    if(search==null) {
                     search = new ShowPanel();  
              	 search.setVisible(true);
              	 //Dodanie listenera do przycisku zamniecia w celu reagowania na zamkniecie okna 
                	 search.addWindowListener(new WindowAdapter(){
                		 public void windowClosing(WindowEvent e) {
                		 search=null;
                	 }});
               
                    }
                       while (RS.next()) {
                    	   counter++;
                    	   findedData.add(new Vector<String>());
                    	  
                    	 for (int z=1;z<=6;z++) {
                    	   String datax = RS.getString(z);
                    	  
                    	   findedData.get(counter-1).add(datax);  
                    	  }

                   	 search.upgradeJTable(findedData,counter-1);  
                    	 }
            		}
            		catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
        	}	
        });
        
        
        buttonToUpdate=new JButton("Update");
        buttonToUpdate.setBounds(200, 450, 100, 50);
        contentPane.add(buttonToUpdate);
        buttonToUpdate.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		String name,pass,address,email,pesel,id;
        		name=textField.getText();
        		pass=password.getText();
        		address=addressField.getText();
        		email=emailField.getText();
        		pesel=peselField.getText();
        		id=searchIdField.getText();
        		//zmiana ze string na int 
        		
        		try {
        		PreparedStatement st = (PreparedStatement) connect.prepareStatement("UPDATE student set name=?,password=?,address=?,Email=?,Pesel=? WHERE id=?");
        		st.setString(1, name);
        		st.setString(2, pass);
        		st.setString(4, address);
        		st.setString(3, email);
        		st.setString(5, pesel);
        		st.setString(6, id);
        		st.executeUpdate();
        		JOptionPane.showMessageDialog(buttonToUpdate,"Updated");
        	}
        		
        		
        	
        	catch(SQLException except) {
        		
        	
        	}
        	}
        });
       
	}
	
	
	

	
	
	

}
