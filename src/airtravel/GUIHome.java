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
	
	static JButton buttonAirport, buttonAirportRemove, buttonFlightCreate;
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
		JPanel flightButtons = new JPanel(new FlowLayout());
		flightButtons.setBackground(Color.DARK_GRAY);
		JPanel placeholder2 = new JPanel(new FlowLayout());
		placeholder2.setBackground(Color.LIGHT_GRAY);
		
		JPanel airportButtons = new JPanel();
		airportButtons.setBackground(Color.DARK_GRAY);
		
		
		buttonAirport = new JButton("Add Airport");
		buttonAirport.addActionListener(new ButtonAirportActionListener());
		airportButtons.add(buttonAirport);
		
		buttonAirportRemove = new JButton("Remove Airport");
		buttonAirportRemove.addActionListener(new ButtonAirportRemoveActionListener());
		airportButtons.add(buttonAirportRemove);
		
		buttonFlightCreate = new JButton("Create Flight");
		buttonFlightCreate.addActionListener(new ButtonFlightCreateActionListener());
		flightButtons.add(buttonFlightCreate);
		
		//JPanel panelAirports = new JPanel();
		panelAirports.setLayout(new BoxLayout(panelAirports, BoxLayout.Y_AXIS));
		JLabel currAirports = new JLabel("Current Airports: ");
		panelAirports.add(currAirports);
		panelAirports.setBackground(Color.LIGHT_GRAY);
		//panel.add(panelAirports);
		
        
		this.setLayout(new GridLayout(1,4, 0, 0));
        this.add(panelAirports);
        this.add(airportButtons);
        this.add(flightButtons);
        this.add(placeholder2);
        
	}
	
	static void addAirportTextUpdate(Airport airport) {
		panelAirports.add(new JLabel(airport.getCode()));
		panelAirports.revalidate();
		panelAirports.repaint();
	}
	
	static void removeAirportTextUpdate() {
		panelAirports.removeAll();
		panelAirports.add(new JLabel("Current Airports: "));
		for(Airport airport: GUIDataCurrent.airports) {
			panelAirports.add(new JLabel(airport.getCode()));
		}
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
	
	private class ButtonAirportRemoveActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GUIAirportRemove airportRemove = new GUIAirportRemove();
			airportRemove.setVisible(true);
		}	
	}
	
	private class ButtonFlightCreateActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GUICreateFlight flight = new GUICreateFlight();
			flight.setVisible(true);
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
