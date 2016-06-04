package sweeper;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.FindInfo;
import dao.User;

class Register extends JFrame implements ActionListener {
	private static final long serialVersionUID = 4184860936168836591L;
	// 定义组件
	JFrame jf;
	JPanel jp;
	JLabel jl1, jl2;
	JTextField jtf1, jtf2;
	JButton jb1, jb2;

	public Register() {
		// 初始化组件
		jf = new JFrame();
		jp = new JPanel();
		jl1 = new JLabel("请输入用户名：");
		jtf1 = new JTextField(10);
		jtf1.setToolTipText("用户名必须为3-6位字母_或者数字");
		jl2 = new JLabel("请输入密码：");
		jtf2 = new JTextField(10);
		jtf2.setToolTipText("密码必须为6位字母_或者数字");

		jb1 = new JButton("返回");
		jb1.setToolTipText("点我返回登录界面哦");
		jb2 = new JButton("注册");
		jb1.addActionListener(this);
		jb2.addActionListener(this);

		jp.setLayout(new GridLayout(3, 0));
		jp.add(jl1);
		jp.add(jtf1);
		jp.add(jl2);
		jp.add(jtf2);
		jp.add(jb1);
		jp.add(jb2);

		this.add(jp);
		this.setTitle("注册界面");
		this.setSize(300, 150);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "返回") {
			this.dispose();
		} else if (e.getActionCommand() == "注册") {
			// 调用注册方法
			this.zhuce();
		}
	}

	public void zhuce() {
		String regex1 = "\\w{3,6}"; // 用户名必须是3-6位
		boolean flag1 = jtf1.getText().matches(regex1);

		String regex2 = "\\w{6}"; // 密码必须是6位
		boolean flag2 = jtf2.getText().matches(regex2);

		if (flag1 == false) {
			JOptionPane.showMessageDialog(null, "用户名填写错误,必须为3-6位字母_或者数字",
					"提示信息", JOptionPane.WARNING_MESSAGE);
		} else if (flag2 == false) {
			JOptionPane.showMessageDialog(null, "密码填写错误,必须为6位字母_或者数字", "提示信息",
					JOptionPane.WARNING_MESSAGE);
		} else {
			this.dispose();
			FindInfo.addUser(new User(jtf1.getText(), jtf2.getText()));
			JOptionPane.showMessageDialog(null, "注册成功！", "提示信息",JOptionPane.WARNING_MESSAGE);
			new Login();
		}
	}

	/*
	 * public static void main(String[] args) { new Register(); }
	 */
}