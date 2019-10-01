package airtravel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUIHome extends JFrame {
	
	static JButton buttonAirport;
	
	 GUIHome(){
		createView();
		
		this.setSize(new Dimension(1300,800));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Airport Manager");
		
	}
	
	private void createView() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.darkGray);
		getContentPane().add(panel);
		
		buttonAirport = new JButton("Add Airport");
		buttonAirport.addActionListener(
				new ButtonAirportActionListener()
				);
		panel.add(buttonAirport);
	}
	
	private class ButtonAirportActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			GUIAirportInput airportInput = new GUIAirportInput();
			airportInput.setVisible(true);
//			SwingUtilities.invokeLater(new Runnable() {
//				@Override
//				public void run() {
//					new GUIAirportInput().setVisible(true);
//				}
//			});
			
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
