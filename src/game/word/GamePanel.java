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

	JPanel p_west; // 왼쪽 컨트롤 영역
	JPanel p_center; // 단어 그래픽 처리 영역

	JLabel la_user; // 게임 로그인 유저명
	JLabel la_score; // 게임 점수
	Choice choice; // 단어 선택 드랍박스
	JTextField t_input; // 게임 입력창
	JButton bt_start, bt_pause; // 게임 시작 버튼
	String res = "C:/java__workspace2/project0329/src/res";

	FileInputStream fis;
	InputStreamReader reader;// 파일 대상 문자스트림
	BufferedReader buffr;// 문자기반 버퍼스트림
	
	//조사한 단어를 담아놓자 게임에 써먹기 위해
	ArrayList<String> wordList=new ArrayList<String>();

	public GamePanel(GameWindow gameWindow) {
		setLayout(new BorderLayout());

		this.gameWindow = gameWindow;

		p_west = new JPanel();
		
		//이영역은 지금부터 그림을 그릴 영역
		p_center = new JPanel(){
			public void paint(Graphics g) {
			g.drawString("고등어", 200, 500);
			
			}
		};
		la_user = new JLabel("김연실 님");
		la_score = new JLabel("0점");
		choice = new Choice();
		t_input = new JTextField(10);
		bt_start = new JButton("start");
		bt_pause = new JButton("pause");

		p_west.setPreferredSize(new Dimension(150, 700));
		p_west.setBackground(Color.ORANGE);

		choice.add("▼ 단어를 선택해주세요");
		choice.setPreferredSize(new Dimension(150, 40));
		choice.addItemListener(this);

		p_west.add(la_user);
		p_west.add(choice);
		p_west.add(t_input);
		p_west.add(bt_start);
		p_west.add(bt_pause);
		p_west.add(la_score);

		add(p_west, BorderLayout.WEST);

		setVisible(false); // 최초에 등장하지 않음

		//setBackground(Color.PINK);
		setPreferredSize(new Dimension(900, 700));

		getCategory();
	}

	// 초이스 컴포넌트에 채워질 파일명 조사하기
	public void getCategory() {
		File file = new File(res);

		// 파일+디렉토리 섞여있는 배열반환
		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {

			if (files[i].isFile()) {
				String name = files[i].getName();// memo.txt
				String[] arr = name.split("\\.");
				if (arr[1].equals("txt")) {// 메모장이라면
					choice.add(name);
				}
			}
		}
	}

	// 파일 읽어오기
	public void getword() {
		int index = choice.getSelectedIndex();

		if (index != 0) {// 첫번재 요소는 빼고
			String name = choice.getSelectedItem();
			System.out.println(res + name);
			
			try {
				fis=new FileInputStream(res+name);
				try {
					reader = new InputStreamReader(fis,"utf-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				
				//스트림을 버퍼 처리 수준까지 올림
				buffr=new BufferedReader(reader);
				String data;
				
				while(true){
					data=buffr.readLine();//한줄
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