import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class ServiceLightSensingEvent extends JFrame implements LightSensingEventListenerInt, SwingConstants {

	private static final long serialVersionUID = 1L;
	static final int MAX_ROW = 3;
	static final int MAX_COL = 2;
	static final int MAX_HEIGHT = 150;
	static final int MAX_WIDTH = 300;
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JTextField outputText;
	//TextArea notificationText;
	//JTextField inputText;
	//JButton acknowledgeButton;
	
	// Create a GUI Input Output interface to handle user input ad output

	public ServiceLightSensingEvent() {
		super("Simple GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(70,70);
		setPreferredSize(new Dimension(MAX_WIDTH,MAX_HEIGHT));
		setResizable(false);

		// Set menus to be HeavyWeight to prevent the Panel from overlapping it.
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		// Add the menu to support Undo operation
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the File menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("The File menu");
		menuBar.add(menu);

		//Add the Exit option.

		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Exits the application");
		menuItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0); }});

		menu.add(menuItem);


		// Create a GRID panel to hold the user I/O.
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(MAX_ROW,MAX_COL));
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		panel.setBorder(blackBorder);
		//panel.add(new Label("Enter Text:"));
		//inputText = new JTextField("Type here",20);
		//inputText.addActionListener(new MyTextActionListener());
		//panel.add(inputText);
		panel.add(new Label("Light Sensor Value:"));
		outputText = new JTextField("No Value Yet");
		outputText.setEditable(false);
		panel.add(outputText);
		
		/*notificationText = new TextArea("No Events",1,1,TextArea.SCROLLBARS_NONE);
		notificationText.setEditable(false);
		panel.add(notificationText);
		acknowledgeButton = new JButton("Clear Event");
		acknowledgeButton.addActionListener(new MyButtonActionListener());
		panel.add(acknowledgeButton);*/
		
		setContentPane(panel);
		setJMenuBar(menuBar);
		pack();
		setVisible(true);
	}
	

	
	@Override
	public void handleLightSensingEvent(EventObject e) {
		LightSensingEvent lightEvent = (LightSensingEvent) e;
		
		outputText.setText(lightEvent.toString());
		
	}
	
	
	
	//driver... zzoooooommmmmmm
	public static void main (String[] args) {
		
		//start the gui
		ServiceLightSensingEvent service = new ServiceLightSensingEvent();
		
		LightSensingEventSource source = new LightSensingEventSource(100);
		source.addEventListener(service);
		source.run();
		
		
	}

	

	


}
