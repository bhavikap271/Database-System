package Screens;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextArea;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Home extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
					GlobalData.initTableArray();
					GlobalData.initprimaryKey();
					
					//newly added
					GlobalData.initTableJSonArray();

				} catch (Exception e) {
					e.printStackTrace();
				}
				test();

			}
		});
	}

	protected static void test() {
		JSONArray table_a = GlobalData.tableJSonArray.get("A");
		for (int i = 0; i < table_a.size(); i++) {
			System.out.println(table_a.get(i).toString());
		}

		JSONArray table_b = GlobalData.tableJSonArray.get("B");
		for (int i = 0; i < table_b.size(); i++) {
			System.out.println(table_b.get(i).toString());
		}

		try {
			GlobalData.addAttBTreeIndex("A", "m");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONParser parser = new JSONParser();
		BPlusTreeIndexing t = (BPlusTreeIndexing) GlobalData.AttBTreeIndex.get("m");
		t.printbtree();
		JSONObject currJson = (JSONObject) t.search((long) 1);
		System.out.println("Test tree retrival: " + currJson.get("o"));
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setTitle("SQL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 792, 342);
		getContentPane().setLayout(null);

		JLabel lblSqlQuery = new JLabel("SQL Query : ");
		lblSqlQuery.setBounds(10, 44, 116, 24);
		lblSqlQuery.setFont(new Font("Times New Roman", Font.BOLD, 20));
		getContentPane().add(lblSqlQuery);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(135, 30, 631, 174);

		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		getContentPane().add(textArea);
		JButton btnCreate = new JButton("Create Table");
		btnCreate.setBounds(400, 232, 150, 31);
		btnCreate.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {

					CreateTable frame = new CreateTable();
					frame.setTitle("Create Table");
					frame.setVisible(true);

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		getContentPane().add(btnCreate);
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(559, 232, 75, 31);
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String query = textArea.getText();
				if (query.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a Query", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					QValidation.validateQ1(query);
				}
			}
		});
		btnOk.setFont(new Font("Times New Roman", Font.BOLD, 18));
		getContentPane().add(btnOk);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(644, 232, 84, 31);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		btnClear.setFont(new Font("Times New Roman", Font.BOLD, 18));
		getContentPane().add(btnClear);

		JButton btnLoadComplaintdb = new JButton("Load complaint.db");

		btnLoadComplaintdb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				LoadData ld = new LoadData();

				GlobalData.allTables.add("complaints");
				try {
					GlobalData.updateTableFile();
					GlobalData.addTableJSonArray("complaints");
					GlobalData.addAttBTreeIndex("complaints", "id");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnLoadComplaintdb.setBounds(122, 234, 150, 29);
		getContentPane().add(btnLoadComplaintdb);
		contentPane = new JPanel(new GridLayout(5, 5));

	}
}
