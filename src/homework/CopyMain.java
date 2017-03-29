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
	File file;// �о���� ����, �������
	Thread thread;//���縦 ������ ���� ������
	//���� �޼���� �츮�� �˰� �ִ� �� ����ζ� �Ҹ��� ���ø����̼��� ���
	//����ϴ� ������ �����Ѵ�. ���� ���� ���ѷ��� �� �����¿� ������ �ؼ��� �ȵȴ�.
	long total;//���� ������ ��ü �뷮
	
	public CopyMain() {
		bar = new JProgressBar();
		bt_open = new JButton("����");
		bt_save = new JButton("����");
		bt_copy = new JButton("�������");

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

		// ��ư�� ������ ����
		bt_open.addActionListener(this);
		bt_save.addActionListener(this);
		bt_copy.addActionListener(this);

		setSize(500, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		// �̺�Ʈ ����Ų �̺�Ʈ�ҽ�(�̺�Ʈ��ü)
		Object obj = e.getSource();

		if (obj == bt_open) {
			open();
		} else if (obj == bt_save) {
			save();
		} else if (obj == bt_copy) {
			//������ ���� ���縦 �������� ���� ������ ���� ��Ű��
			//������ �����ڿ� runnable ������ü�� �μ��� ������ runnable��ü ���� �������� run() �޼��� ����
			thread=new Thread(this);//�츮 rund��
			thread.start();
		}
	}

	public void open() {// ���Ͽ���
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

			// ������ ��Ʈ���� ���� ������ �б�
			int data;
			int count=0;
			
			while (true) {//���� ������� ���ѷ����� ��Ʈ����ȵȴ�.
				data = fis.read();// 1byte �б�
				count++;
				
				if (data == -1)
					break;
				fos.write(data);// 1byte ���
				int v=(int)getPercent(count);
				//���μ����ٿ� ����
				bar.setValue(v);
				bar.setString(v+"&");
			}
			JOptionPane.showConfirmDialog(this, "����Ϸ�");
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
		//���� ������ ���ϱ� ����
		//������=100%��/��üũ��
	public long getPrecent(int currentRead){
		return(100*currentRead)/total;
	}
	public static void main(String[] args) {
		new CopyMain();
	}
}
