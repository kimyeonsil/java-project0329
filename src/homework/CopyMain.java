/*
 * 
 */
package homework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class CopyMain extends JFrame implements ActionListener,Runnable {

	JProgressBar bar;
	JButton bt_open, bt_save, bt_copy;
	JTextField t_open, t_save;
	JFileChooser chooser;
	File file;// 읽어들일 파일, 복사원본
	Thread thread;//복사를 실행할 전용 쓰레드
	//메인 메서드는 우리가 알고 있는 그 실행부라 불리는 어플리케이션의 운영을
	//담당하는 역할을 수행한다. 따라서 절대 무한루프 나 대기상태에 빠지게 해서는 안된다.
	long total;//원본 파일의 전체 용량
	
	public CopyMain() {
		bar = new JProgressBar();
		bt_open = new JButton("열기");
		bt_save = new JButton("저장");
		bt_copy = new JButton("복사실행");

		t_open = new JTextField(35);
		t_save = new JTextField(35);
		chooser = new JFileChooser("C:/Users/sist33");

		bar.setPreferredSize(new Dimension(450, 50));
		//bar.setBackground(Color.PINK);
		bar.setString("0%");

		setLayout(new FlowLayout());

		add(bar);
		add(bt_open);
		add(t_open);
		add(bt_save);
		add(t_save);
		add(bt_copy);

		// 버튼과 리스너 연결
		bt_open.addActionListener(this);
		bt_save.addActionListener(this);
		bt_copy.addActionListener(this);

		setSize(500, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		// 이벤트 일으킨 이벤트소스(이벤트주체)
		Object obj = e.getSource();

		if (obj == bt_open) {
			open();
		} else if (obj == bt_save) {
			save();
		} else if (obj == bt_copy) {
			//메인이 직접 복사를 수행하지 말고 쓰레드 에게 시키자
			//쓰레디 생성자에 runnable 구현객체를 인수로 넣으면 runnable객체 에서 재정의한 run() 메서드 수행
			thread=new Thread(this);//우리 rund꺼
			thread.start();
		}
	}

	public void open() {// 파일열기
		int result = chooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			t_open.setText(file.getAbsolutePath());
			total=file.length();
		}
	}

	public void save() {
		int result = chooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			t_save.setText(file.getAbsolutePath());
		}
	}

	public void copy() {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream(t_save.getText());

			// 생성된 스트림을 통해 데이터 읽기
			int data;
			int count=0;
			
			while (true) {//메인 쓰레드는 무한루프에 빠트리면안된다.
				data = fis.read();// 1byte 읽기
				count++;
				
				if (data == -1)
					break;
				fos.write(data);// 1byte 출력
				int v=(int)getPercent(count);
				//프로세스바에 적용
				bar.setValue(v);
				bar.setString(v+"&");
			}
			JOptionPane.showConfirmDialog(this, "복사완료");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
		private int getPercent(int count) {
		// TODO Auto-generated method stub
		return 0;
	}

		public void run() {
			copy();
		}
		//현재 진행율 구하기 공식
		//진행율=100%현/전체크기
	public long getPrecent(int currentRead){
		return(100*currentRead)/total;
	}
	public static void main(String[] args) {
		new CopyMain();
	}
}
