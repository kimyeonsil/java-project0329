package game.word;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel implements ItemListener {
	GameWindow gameWindow;

	JPanel p_west; // ���� ��Ʈ�� ����
	JPanel p_center; // �ܾ� �׷��� ó�� ����

	JLabel la_user; // ���� �α��� ������
	JLabel la_score; // ���� ����
	Choice choice; // �ܾ� ���� ����ڽ�
	JTextField t_input; // ���� �Է�â
	JButton bt_start, bt_pause; // ���� ���� ��ư
	String res = "C:/java__workspace2/project0329/src/res";

	FileInputStream fis;
	InputStreamReader reader;// ���� ��� ���ڽ�Ʈ��
	BufferedReader buffr;// ���ڱ�� ���۽�Ʈ��
	
	//������ �ܾ ��Ƴ��� ���ӿ� ��Ա� ����
	ArrayList<String> wordList=new ArrayList<String>();

	public GamePanel(GameWindow gameWindow) {
		setLayout(new BorderLayout());

		this.gameWindow = gameWindow;

		p_west = new JPanel();
		
		//�̿����� ���ݺ��� �׸��� �׸� ����
		p_center = new JPanel(){
			public void paint(Graphics g) {
			g.drawString("����", 200, 500);
			
			}
		};
		la_user = new JLabel("�迬�� ��");
		la_score = new JLabel("0��");
		choice = new Choice();
		t_input = new JTextField(10);
		bt_start = new JButton("start");
		bt_pause = new JButton("pause");

		p_west.setPreferredSize(new Dimension(150, 700));
		p_west.setBackground(Color.ORANGE);

		choice.add("�� �ܾ �������ּ���");
		choice.setPreferredSize(new Dimension(150, 40));
		choice.addItemListener(this);

		p_west.add(la_user);
		p_west.add(choice);
		p_west.add(t_input);
		p_west.add(bt_start);
		p_west.add(bt_pause);
		p_west.add(la_score);

		add(p_west, BorderLayout.WEST);

		setVisible(false); // ���ʿ� �������� ����

		//setBackground(Color.PINK);
		setPreferredSize(new Dimension(900, 700));

		getCategory();
	}

	// ���̽� ������Ʈ�� ä���� ���ϸ� �����ϱ�
	public void getCategory() {
		File file = new File(res);

		// ����+���丮 �����ִ� �迭��ȯ
		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {

			if (files[i].isFile()) {
				String name = files[i].getName();// memo.txt
				String[] arr = name.split("\\.");
				if (arr[1].equals("txt")) {// �޸����̶��
					choice.add(name);
				}
			}
		}
	}

	// ���� �о����
	public void getword() {
		int index = choice.getSelectedIndex();

		if (index != 0) {// ù���� ��Ҵ� ����
			String name = choice.getSelectedItem();
			System.out.println(res + name);
			
			try {
				fis=new FileInputStream(res+name);
				try {
					reader = new InputStreamReader(fis,"utf-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				
				//��Ʈ���� ���� ó�� ���ر��� �ø�
				buffr=new BufferedReader(reader);
				String data;
				
				while(true){
					data=buffr.readLine();//����
					if(data==null)break;
						System.out.println(data);
						
						wordList.add(data);
					}
					try {
						buffr.readLine();
					if(fis!=null)break;
					
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}	
			}
	}
	public void itemStateChanged(ItemEvent e) {
		getword();
	}
}