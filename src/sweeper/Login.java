package sweeper;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	// 定义登录界面的组件
	JButton jb1, jb2 = null;
	JRadioButton jrb1, jrb2 = null;
	JPanel jp1, jp2, jp3 = null;
	JTextField jtf = null;
	JLabel jlb1, jlb2 = null;
	JTextField jpf = null;

	public Login() {
		// 创建组件
		jb1 = new JButton("登录");
		jb2 = new JButton("退出");
		// 设置监听
		jb1.addActionListener(this);
		jb2.addActionListener(this);

		jlb1 = new JLabel("用户名：");
		jlb2 = new JLabel("密    码：");

		jtf = new JTextField(10);
		jpf = new JPasswordField(10);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();

		jp1.add(jlb1);
		jp1.add(jtf);

		jp2.add(jlb2);
		jp2.add(jpf);

		jp3.add(jb1);
		jp3.add(jb2);
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);

		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("注册登录界面");
		this.setLayout(new GridLayout(3, 1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 监听各个按钮
		if (e.getActionCommand() == "退出") {
			this.dispose();
		} else if (e.getActionCommand() == "登录") {
			// 调用登录方法
			this.login();
			/*JOptionPane.showMessageDialog(null, "您已成功登陆！！！开始游戏吧~~~", "nene",
					JOptionPane.WARNING_MESSAGE);*/
		}
	}

	// 登录方法
	@SuppressWarnings("deprecation")
	public void login() {
		Minesweeper.SQLverify(jtf.getText(), jpf.getText());
		// System.out.println(jtf.getText()+jpf.getText());

		this.dispose();
		// new Minesweeper();
	}

	/*
	 * public static void main(String[] args) { Login l = new Login(); }
	 */
}