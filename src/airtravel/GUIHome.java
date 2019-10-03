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

@SuppressWarnings("serial")
public class GUIHome extends JFrame {
	
	static JButton buttonAirport, buttonAirportRemove, buttonFlightCreate, buttonFlightRemove;
	static JLabel currAirports;
	static JPanel panelAirports = new JPanel();
	static JPanel flightList = new JPanel();
	
	 GUIHome(){
		createView();
		
		this.setSize(new Dimension(1300,800));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Airport Manager");
		
	}
	
	private void createView() {
		panelAirports.setBackground(Color.LIGHT_GRAY);
		
		JPanel airportButton1 = new JPanel();
		airportButton1.setBackground(Color.DARK_GRAY);
		
		
		JPanel airportButton2 = new JPanel();
		airportButton2.setBackground(Color.DARK_GRAY);
		
		JPanel yellowStrip = new JPanel(new FlowLayout());
		Color runwayYellow = new Color(250,233,30);
		yellowStrip.setBackground(runwayYellow);
		
		JPanel flightButton1 = new JPanel(new FlowLayout());
		flightButton1.setBackground(Color.DARK_GRAY);
		
		JPanel flightButton2 = new JPanel(new FlowLayout());
		flightButton2.setBackground(Color.DARK_GRAY);
		
		flightList.setBackground(Color.LIGHT_GRAY);
		
		
		
		
		buttonAirport = new JButton("Add Airport");
		buttonAirport.addActionListener(new ButtonAirportActionListener());
		airportButton1.add(buttonAirport);
		
		buttonAirportRemove = new JButton("Remove Airport");
		buttonAirportRemove.addActionListener(new ButtonAirportRemoveActionListener());
		airportButton2.add(buttonAirportRemove);
		
		buttonFlightCreate = new JButton("Create Flight");
		buttonFlightCreate.addActionListener(new ButtonFlightCreateActionListener());
		flightButton1.add(buttonFlightCreate);
		
		buttonFlightRemove = new JButton("Remove Flight");
		buttonFlightRemove.addActionListener(new ButtonFlightRemoveActionListener());
		flightButton2.add(buttonFlightRemove);
		
		//JPanel panelAirports = new JPanel();
		panelAirports.setLayout(new BoxLayout(panelAirports, BoxLayout.Y_AXIS));
		JLabel currAirports = new JLabel("Current Airports: ");
		panelAirports.add(currAirports);
		panelAirports.setBackground(Color.LIGHT_GRAY);
		
		flightList.setLayout(new BoxLayout(flightList, BoxLayout.Y_AXIS));
		JLabel currFlights = new JLabel("Current Flights: ");
		flightList.add(currFlights);
		flightList.setBackground(Color.LIGHT_GRAY);
		
        
		this.setLayout(new GridLayout(1,7, 0, 0));
        this.add(panelAirports);
        this.add(airportButton1);
        this.add(airportButton2);
        this.add(yellowStrip);
        this.add(flightButton1);
        this.add(flightButton2);
        this.add(flightList);
        
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
	
	static void addFlightTextUpdate(Flight flight) {
		flightList.add(new JLabel(flight.getCode()+" From: "+flight.origin().getCode()+" To: "+flight.destination().getCode()));
		panelAirports.revalidate();
		panelAirports.repaint();
	}
	
	static void removeFlightTextUpdate() {
		flightList.removeAll();
		flightList.add(new JLabel("Current Flights: "));
		for(Flight flight: GUIDataCurrent.flights) {
			flightList.add(new JLabel(flight.getCode()+" From: "+flight.origin().getCode()+" To: "+flight.destination().getCode()));
		}
		flightList.revalidate();
		flightList.repaint();
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
	
	private class ButtonFlightRemoveActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GUIRemoveFlight flightRemove = new GUIRemoveFlight();
			flightRemove.setVisible(true);
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
