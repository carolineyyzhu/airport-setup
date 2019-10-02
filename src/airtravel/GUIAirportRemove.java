package airtravel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUIAirportRemove extends JFrame {
	
	private JButton buttonSubmit;
	private JTextField textEntryName, textEntryConnection;
	private JLabel labelMessageName, labelMessageConnection;
	
	
	GUIAirportRemove() {
		createView();
		
		this.setSize(new Dimension(600,150));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Airport Manager");
		
	}
	
	private void createView() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.gray);
		this.getContentPane().add(panel);
		
		labelMessageName = new JLabel("Please enter the airport name to remove.");
		panel.add(labelMessageName);
		
		textEntryName = new JTextField();
		textEntryName.setPreferredSize(new Dimension(150, 30));
		panel.add(textEntryName);
		
		buttonSubmit = new JButton("Submit");
		buttonSubmit.addActionListener(new ButtonSubmitActionListener());
		panel.add(buttonSubmit);
		
		
	}
	
	private class ButtonSubmitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String airportName = textEntryName.getText();
			Airport airport = Airport.of(airportName, Duration.ofMinutes(1));
			if(airportName.isEmpty()) {
				labelMessageName.setText("The airport name cannot be nothing.");
			} else if(!GUIDataCurrent.containsAirport(airport)) {
				labelMessageName.setText("The airport does not exist.");
			}else {
				GUIDataCurrent.removeAirport(airport);
				textEntryName.setText("");
				labelMessageName.setText(airport.getCode() + " has been removed. Please enter another airport name, or close the window to exit.");
				
				
				
			}
			
		}
		
		
	}

}
