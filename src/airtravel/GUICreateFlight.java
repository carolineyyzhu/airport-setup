package airtravel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUICreateFlight extends JFrame {
	
	private JButton buttonSubmit;
	private JTextField textEntryName, textEntryOrigin, textEntryDestination, textEntryDepartureTime;
	private JTextField textEntryArrivalTime, textEntryBUSINESS, textEntryPREMIUM, textEntryECON;
	private JLabel	LMName, LMOrigin, LMDestination, LMDepartureTime;
	private JLabel	LMArrivalTime, LMBUSINESS, LMPREMIUM, LMECON;
	
	
	
	GUICreateFlight() {
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
		
		LMName = new JLabel("Please enter the flight name.");
		textEntryName = new JTextField();
		textEntryName.setPreferredSize(new Dimension(150, 30));
		
		LMOrigin = new JLabel("Please enter the origin airport name.");
		textEntryOrigin = new JTextField();
		textEntryOrigin.setPreferredSize(new Dimension(150, 30));
		
		LMDestination = new JLabel("Please enter the destination airport name.");
		textEntryDestination = new JTextField();
		textEntryDestination.setPreferredSize(new Dimension(150, 30));
		
		LMDepartureTime = new JLabel("Please enter the departure time (hr, min).");
		textEntryDepartureTime = new JTextField();
		textEntryDepartureTime.setPreferredSize(new Dimension(150, 30));
		
		LMArrivalTime = new JLabel("Please enter the arrival time (hr, min).");
		textEntryArrivalTime = new JTextField();
		textEntryArrivalTime.setPreferredSize(new Dimension(150, 30));
		
		LMBUSINESS = new JLabel("Please enter the number of business seats.");
		textEntryBUSINESS = new JTextField();
		textEntryBUSINESS.setPreferredSize(new Dimension(150, 30));
		
		LMPREMIUM = new JLabel("Please enter the number of premium economy seats.");
		textEntryPREMIUM = new JTextField();
		textEntryPREMIUM.setPreferredSize(new Dimension(150, 30));
		
		LMECON = new JLabel("Please enter the number of economy seats.");
		textEntryECON = new JTextField();
		textEntryECON.setPreferredSize(new Dimension(150, 30));
		
		
		buttonSubmit = new JButton("Submit");
		buttonSubmit.addActionListener(new ButtonSubmitActionListener());
		
		
		panel.setLayout(new GridLayout(9,2, 1, 1));
		panel.add(LMName);
		panel.add(textEntryName);
		panel.add(LMOrigin);
		panel.add(textEntryOrigin);
		panel.add(LMDestination);
		panel.add(textEntryDestination);
		panel.add(LMDepartureTime);
		panel.add(textEntryDepartureTime);
		panel.add(LMArrivalTime);
		panel.add(textEntryArrivalTime);
		panel.add(LMBUSINESS);
		panel.add(textEntryBUSINESS);
		panel.add(LMPREMIUM);
		panel.add(textEntryPREMIUM);
		panel.add(LMECON);
		panel.add(textEntryECON);
		panel.add(buttonSubmit);
		
		
		
	}
	
	private class ButtonSubmitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String flightName = textEntryName.getText();
			String originName = textEntryOrigin.getText();
			String destinationName = textEntryDestination.getText();
			String departureTime = textEntryDepartureTime.getText();
			String arrivalTime = textEntryArrivalTime.getText();
			String seatsBUSINESS = textEntryBUSINESS.getText();
			String seatsPREMIUM = textEntryPREMIUM.getText();
			String seatsECON = textEntryECON.getText();
			
			Airport originAirport = GUIDataCurrent.findAirportByName(originName);
			Airport destinationAirport = GUIDataCurrent.findAirportByName(destinationName);
			Leg leg = Leg.of(originAirport, destinationAirport);
			
			String[] departureTimeArray = departureTime.split(", ");
			LocalTime dTime = LocalTime.of(Integer.parseInt(departureTimeArray[0]), Integer.parseInt(departureTimeArray[1]));
			
			String[] arrivalTimeArray = arrivalTime.split(", ");
			LocalTime aTime = LocalTime.of(Integer.parseInt(arrivalTimeArray[0]), Integer.parseInt(arrivalTimeArray[1]));
			
			FlightSchedule schedlue = FlightSchedule.of(dTime, aTime);
			
			EnumMap<SeatClass, Integer> seatConfigEnumMap = new EnumMap<SeatClass, Integer>(SeatClass.class);
			seatConfigEnumMap.put(SeatClass.BUSINESS, Integer.parseInt(seatsBUSINESS));
			seatConfigEnumMap.put(SeatClass.PREMIUM_ECONOMY, Integer.parseInt(seatsPREMIUM));
			seatConfigEnumMap.put(SeatClass.ECONOMY, Integer.parseInt(seatsECON));
			SeatConfiguration seatConfiguration = SeatConfiguration.of(seatConfigEnumMap);
			
			SimpleFlight newFlight = SimpleFlight.of(flightName, leg, schedlue, seatConfiguration);
			GUIDataCurrent.addFlight(newFlight);
			LMName.setText("The flight has been added. Please enter another flight or close window.");
			textEntryName.setText("");	
			textEntryOrigin.setText("");	
			textEntryDestination.setText("");	
			textEntryDepartureTime.setText("");	
			textEntryArrivalTime.setText("");	
			textEntryBUSINESS.setText("");	
			textEntryPREMIUM.setText("");	
			textEntryECON.setText("");
			}
			
		}
		
		
	}


