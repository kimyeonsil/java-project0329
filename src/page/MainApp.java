package page;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApp extends JFrame implements ActionListener {
	JButton[] menu = new JButton[3];
	JButton bt_login, bt_content, bt_etc;
	JPanel p_north;
	URL[] url = new URL[3];
	String[] path = { "/login.png", "/content.png", "/etc.png" };

	// 페이지들을 보유한다
	JPanel p_center;// 페이지들이 붙을곳
	LoginForm loginForm;
	Content content;
	Etc etc;

	JPanel[] page = new JPanel[3];

	public MainApp() {
		p_center = new JPanel();
		p_north = new JPanel();

		page[0] = new LoginForm();
		page[1] = new Content();
		page[2] = new Etc();

		for (int i = 0; i < path.length; i++) {
			url[i] = this.getClass().getResource(path[i]);
			menu[i] = new JButton(new ImageIcon(url[i]));
			p_north.add(menu[i]);
			menu[i].addActionListener(this);
		}
		add(p_north, BorderLayout.NORTH);

		p_center = new JPanel();

		p_center.add(page[0]);// 로그인 폼 생성
		p_center.add(page[1]);// 컨텐츠 생성
		p_center.add(page[2]);

		add(p_center);

		setSize(700, 600);
		setVisible(true);
		setLocationRelativeTo(null);// 화면중앙
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// url=this.getClass().getResource("/login.png");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		for (int i = 0; i < page.length; i++) {
			if (obj == menu[i]) {
				page[i].setVisible(true);// 로그인폼 보이기
			}else{
				page[i].setVisible(false);
				
				
				
			}
		}

	}

	public static void main(String[] args) {
		new MainApp();

	}

}
