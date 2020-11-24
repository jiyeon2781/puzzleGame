package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuGui extends JFrame implements ActionListener {
	private JButton ThreeButton, FourButton, FiveButton, AIButton, TPlsButton;
	private JLabel mainName, madeName;

	public MenuGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 592);
		Color co = new Color(245,245,245);
		getContentPane().setBackground(co);
		getContentPane().setLayout(null);

		ThreeButton = new JButton("3 * 3");
		ThreeButton.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		ThreeButton.addActionListener(this);
		ThreeButton.setBackground(Color.LIGHT_GRAY);
		ThreeButton.setBounds(12, 150, 178, 32);
		getContentPane().add(ThreeButton);

		FourButton = new JButton("4 * 4");
		FourButton.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		FourButton.addActionListener(this);
		FourButton.setBackground(Color.LIGHT_GRAY);
		FourButton.setBounds(12, 210, 178, 32);
		getContentPane().add(FourButton);

		FiveButton = new JButton("5 * 5");
		FiveButton.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		FiveButton.addActionListener(this);
		FiveButton.setBackground(Color.LIGHT_GRAY);
		FiveButton.setBounds(12, 270, 178, 32);
		getContentPane().add(FiveButton);

		TPlsButton = new JButton("Player VS Player");
		TPlsButton.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		TPlsButton.addActionListener(this);
		TPlsButton.setBackground(Color.LIGHT_GRAY);
		TPlsButton.setBounds(12, 330, 178, 32);
		getContentPane().add(TPlsButton);

		AIButton = new JButton("Computer VS Player");
		AIButton.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		AIButton.addActionListener(this);
		AIButton.setBackground(Color.LIGHT_GRAY);
		AIButton.setBounds(12, 390, 178, 32);
		getContentPane().add(AIButton);

		mainName = new JLabel("Puzzle Game");
		mainName.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 45));
		mainName.setBounds(173, 37, 320, 54);
		getContentPane().add(mainName);

		madeName = new JLabel("Made By Jeong Jiyeon");
		madeName.setFont(new Font("Berlin Sans FB", Font.PLAIN, 12));
		madeName.setBounds(370, 528, 123, 15);
		getContentPane().add(madeName);
		setVisible(true);


	}

	public void actionPerformed(ActionEvent e) {

		Object o = e.getSource();
		if (o == ThreeButton) {
			new BasicPuzzle(3);
		} else if (o == FourButton) {
			new BasicPuzzle(4);
		} else if (o == FiveButton) {
			new BasicPuzzle(5);
		} else if (o == TPlsButton) {
			new TPlsGui();
		} else if (o == AIButton) {
			new ComVsPlGui();
		}
	}
}
