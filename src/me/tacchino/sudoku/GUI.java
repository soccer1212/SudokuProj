package me.tacchino.sudoku;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GUI {

	private SudokuTable sudokuTable = new SudokuTable();
	private JFrame frame = new JFrame("Sudoku Solver");
	private JTextField textField[][] = new JTextField[9][9];
	private GridPanel gridPanel = new GridPanel(new GridLayout(9,9,1,1));
	private static final String stop = "Stop";
    private static final String start = "Start";
    private final ClockListener clock = new ClockListener();
    private final Timer timer = new Timer(53, (ActionListener) clock);
    private final JTextField tf = new JTextField(9);
    private final SimpleDateFormat date = new SimpleDateFormat("mm.ss.SSS");
    private long startTime;
	
	GUI(){
		for (int y = 0; y < 9; y++){
			for (int x = 0; x < 9; x++){
				textField[x][y] = new JTextField();
				textField[x][y].setForeground(Color.RED);
				gridPanel.add(textField[x][y]);
			}
		}
	}
	
	boolean checkText(){
		for (int y = 0; y < 9; y++){
			for (int x = 0; x < 9; x++){
				if (!textField[x][y].getText().equals("")){
					try {
						int digit = Integer.parseInt(textField[x][y].getText());
						if (digit <= 0 || digit >= 10)
							return false;
					}
					catch (NumberFormatException e){
						return false;
					}
				}
			}
		}
		return true;
	}

	public void GUIToSudokuTable(){
		for (int y = 0; y < 9; y++){
			for (int x = 0; x < 9; x++){
				if (!textField[x][y].getText().equals("")){
					sudokuTable.getDigit(x,y).setAnswer(Integer.parseInt(textField[x][y].getText()));
					sudokuTable.getDigit(x,y).setSafe(true);
					textField[x][y].setForeground(Color.RED);
				}
				else {
					sudokuTable.getDigit(x,y).setAnswer(0);
					textField[x][y].setForeground(Color.BLACK);
				}
			}
		}
	}
	
	public void sudokuTableToGUI(){
		for (int y = 0; y < 9; y++){
			for (int x = 0; x < 9; x++){		
				if (sudokuTable.getDigit(x,y).isSolved())
					textField[x][y].setText(String.valueOf(sudokuTable.getDigit(x,y).getAnswer()));			
			}
		}
	}
	
	public void clearGrid(){
		for (int y = 0; y < 9; y++){
			for (int x = 0; x < 9; x++){
				textField[x][y].setText("");
				textField[x][y].setForeground(Color.RED);
			}
		}
	}
	
	public void createGUI(){
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		
		gridBagConstraints.weighty = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTH;
		
		//add label to top of main panel
		/*JLabel topLeftLabel = new JLabel("Score", JLabel.CENTER);
		
		topLeftLabel.setOpaque(true);
		topLeftLabel.setBackground(Color.BLACK);
		topLeftLabel.setForeground(Color.WHITE);
		topLeftLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weighty = 0.05;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		mainPanel.add(topLeftLabel, gridBagConstraints);*/
		
		JLabel topCenterLabel = new JLabel("Sudoku", JLabel.CENTER);
		
		topCenterLabel.setOpaque(true);
		topCenterLabel.setBackground(Color.BLUE);
		topCenterLabel.setForeground(Color.WHITE);
		topCenterLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weighty = 0.05;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		mainPanel.add(topCenterLabel, gridBagConstraints);
		
		JPanel topRightPanel = new JPanel(new FlowLayout(2));
		JLabel topRightLabel = new JLabel("Timer:", JLabel.RIGHT);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weighty = 0.05;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		topRightLabel.setOpaque(true);
		topRightLabel.setBackground(Color.RED);
		topRightLabel.setForeground(Color.WHITE);
		topRightLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
		mainPanel.add(topRightPanel, gridBagConstraints);
		topRightPanel.add(topRightLabel);
		topRightPanel.add(tf);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.anchor = GridBagConstraints.NORTH;
		mainPanel.add(gridPanel, gridBagConstraints); 
		
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.weighty = 0.1;
		
	  JButton exampleButton = new JButton("Generate Example");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.ipadx = 40;
		mainPanel.add(exampleButton, gridBagConstraints);
		
		exampleButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
                    startTime = System.currentTimeMillis();
                    timer.start();
                    
                
               
                	
          
				ExamplePuzzles example = new ExamplePuzzles();
				sudokuTable = example.createExamplePuzzle();
				clearGrid();
				sudokuTableToGUI();
			}
		});
		
		JButton clearButton = new JButton("Clear Table");
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx = 0;
		mainPanel.add(clearButton, gridBagConstraints);
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sudokuTable = new SudokuTable();
				clearGrid();
			}
		});
		
		JButton solveButton = new JButton("Solve");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		mainPanel.add(solveButton, gridBagConstraints);
		solveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				updateClock();
				timer.stop();
				if (!checkText())
					JOptionPane.showMessageDialog(frame,"Invalid input. Values must be integers from 1 to 9","Error",JOptionPane.ERROR_MESSAGE);
				else {
					GUIToSudokuTable();
					if (!sudokuTable.solve(1)) {
						JOptionPane.showMessageDialog(frame,"This puzzle cannot be solved.","Error",JOptionPane.ERROR_MESSAGE);
						timer.start();
					}
					else
						sudokuTableToGUI();
				}
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.getContentPane().add(mainPanel);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(300,300));
		frame.setVisible(true);
	}
	
	public class GridPanel extends JPanel{
		private static final long serialVersionUID = -6157041650150998205L;

		GridPanel(GridLayout layout){
			super(layout);
		}
		
		public void paintComponent(Graphics g){
			g.fillRect(getWidth()/3 - 1,0,3,getHeight());
			g.fillRect(2*getWidth()/3 - 1,0,3,getHeight());
			g.fillRect(0,getHeight()/3 - 1,getWidth(),3);
			g.fillRect(0,2*getHeight()/3 - 2,getWidth(),3);
		}
	}
	private void updateClock() {
        Date elapsed = new Date(System.currentTimeMillis() - startTime);
        tf.setText(date.format(elapsed));
    }
	private class ClockListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateClock();
        }
    }
	
}
