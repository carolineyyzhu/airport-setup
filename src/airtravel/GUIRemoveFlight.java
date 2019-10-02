package airtravel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUIRemoveFlight extends JFrame {
	
	private JButton buttonSubmit;
	private JComboBox flightOptions;
	private JLabel	LMName;
	
	
	
	GUIRemoveFlight() {
		createView();
		
		this.setSize(new Dimension(700,450));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Airport Manager");
		
	}
	
	private void createView() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.gray);
		this.getContentPane().add(panel);
		
		LMName = new JLabel("Please select the flight to remove.");
		
		List<String> choices = new ArrayList<String>();
		for (Flight flight: GUIDataCurrent.flights) {
			choices.add(flight.getCode()+" From: "+flight.origin().getCode()+" To: "+flight.destination().getCode());
			
		}
        String[] tempType = new String[choices.size()];
		String[] arrayChoices = choices.toArray(tempType);
		flightOptions = new JComboBox<String>(arrayChoices);
		
		
		buttonSubmit = new JButton("Submit");
		buttonSubmit.addActionListener(new ButtonSubmitActionListener());
		
		
		panel.setLayout(new GridLayout(3,1, 1, 1));
		panel.add(LMName);
		panel.add(flightOptions);
		panel.add(buttonSubmit);
		
	}
	
	
	private class ButtonSubmitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int choiceIndex = flightOptions.getSelectedIndex();
			Flight removeFlight = GUIDataCurrent.flights.get(choiceIndex);
			GUIDataCurrent.removeFlight(removeFlight);
			removeFlight.origin().removeFlight(removeFlight);
			
			
			
		}
		
		
	}
}


