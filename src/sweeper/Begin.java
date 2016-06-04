package sweeper;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Begin extends JFrame implements ActionListener {
	// 定义登录界面的组件
	JButton jb1, jb2, jb3 = null;
	JPanel jp1, jp2 = null;
	JLabel j1,j2= null;
	//定义是否是游客身份登录
	static boolean isYk = false;

	public Begin() {
		// 创建组件
		jb1 = new JButton("游客登录");
		jb2 = new JButton("账号登录");
		jb3 = new JButton("去注册~~");
		j2 = new JLabel("还没有账号先去");
		j1 = new JLabel("以游客方式登录数据不会保存,请选择账号登录哦~~");

		jp1 = new JPanel();
		jp2 = new JPanel();
		// 设置监听
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);

		jp1.add(j1);
		jp1.add(jb1);
		jp1.add(jb2);
		jp2.add(j2);
		jp2.add(jb3);
		this.add(jp1);
		this.add(jp2);

		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("选择界面");
		this.setLayout(new GridLayout(0, 1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 150);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 监听各个按钮
		if (e.getActionCommand() == "游客登录") {
			Begin.isYk = true;
			this.dispose();
			new Minesweeper();
			
		} else if (e.getActionCommand() == "账号登录") {
			this.dispose();
			//new Minesweeper();
			new Login();
		} else if (e.getActionCommand() == "去注册~~") {
			this.dispose();
			//new Minesweeper();
			new Register();
		}
	}

	public static void main(String[] args) {
		new Begin();
	}
}