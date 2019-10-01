package airtravel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.accessibility.AccessibleContext;
import javax.swing.*;

public class GUIHome extends JFrame {
	
	static JButton buttonAirport;
	static JLabel currAirports;
	static JPanel panelAirports = new JPanel();
	
	 GUIHome(){
		createView();
		
		this.setSize(new Dimension(1300,800));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Airport Manager");
		
	}
	
	private void createView() {
		JPanel placeholder = new JPanel(new FlowLayout());
		placeholder.setBackground(Color.DARK_GRAY);
		JPanel placeholder2 = new JPanel(new FlowLayout());
		placeholder2.setBackground(Color.DARK_GRAY);
		
		JPanel main = new JPanel();
		main.setBackground(Color.DARK_GRAY);
		
		
		buttonAirport = new JButton("Add Airport");
		
		buttonAirport.addActionListener(
				new ButtonAirportActionListener()
				);
		main.add(buttonAirport);
		
		//JPanel panelAirports = new JPanel();
		panelAirports.setLayout(new BoxLayout(panelAirports, BoxLayout.Y_AXIS));
		JLabel currAirports = new JLabel("Current Airports: ");
		panelAirports.add(currAirports);
		panelAirports.setBackground(Color.LIGHT_GRAY);
		//panel.add(panelAirports);
		
        
		this.setLayout(new GridLayout(1,4, 0, 0));
        this.add(panelAirports);
        this.add(main);
        this.add(placeholder);
        this.add(placeholder2);
        
	}
	
	static void addAirportTextUpdate(Airport airport) {
		panelAirports.add(new JLabel(airport.getCode()));
		panelAirports.revalidate();
		panelAirports.repaint();
	}
	
	
	private class ButtonAirportActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			GUIAirportInput airportInput = new GUIAirportInput();
			airportInput.setVisible(true);
		}
		
	}


	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUIHome().setVisible(true);
			}
		});

		
		
	}

}
