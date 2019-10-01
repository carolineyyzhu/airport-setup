package airtravel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


class GUIAirportInput extends JFrame {
	
	private JButton buttonSubmit;
	private JTextField textEntryName, textEntryConnection;
	private JLabel labelMessageName, labelMessageConnection;
	List<Airport> airports = new ArrayList<Airport>();
	
	GUIAirportInput() {
		createView();
		
		setSize(new Dimension(600,150));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Airport Manager");
		
	}
	
	private void createView() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.gray);
		getContentPane().add(panel);
		
		labelMessageName = new JLabel("Please enter the airport name.");
		panel.add(labelMessageName);
		
		textEntryName = new JTextField();
		textEntryName.setPreferredSize(new Dimension(150, 30));
		panel.add(textEntryName);
		
		labelMessageConnection = new JLabel("Please enter a connection time in minutes.");
		panel.add(labelMessageConnection);
		
		textEntryConnection = new JTextField();
		textEntryConnection.setPreferredSize(new Dimension(150, 30));
		panel.add(textEntryConnection);
		
		buttonSubmit = new JButton("Submit");
		buttonSubmit.addActionListener(new ButtonSubmitActionListener());
		panel.add(buttonSubmit);
		
		
	}
	
	private class ButtonSubmitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String airportName = textEntryName.getText();
			String airportConnectionTime = textEntryConnection.getText();
			if(airportName.isEmpty()) {
				labelMessageName.setText("The airport name cannot be nothing.");
			} else if(airportConnectionTime.isEmpty()) {
				labelMessageConnection.setText("The airport connection time cannot be nothing.");
			}	else {
				Airport airport = Airport.of(airportName, Duration.ofMinutes(Integer.parseInt(airportConnectionTime)));
				airports.add(airport);
				textEntryName.setText("");
				textEntryConnection.setText("");
				labelMessageName.setText(airports.get(airports.size() - 1).getCode() + " has been added. Please enter another airport name, or close the window to exit.");
				labelMessageConnection.setText("Please enter another connection time, or close the window to exit.");
			}
			
		}
		
		
	}

}