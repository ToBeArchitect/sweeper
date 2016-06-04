package sweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dao.FindInfo;

public class Minesweeper extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	MinesweeperGame panel;

	public Minesweeper() {
		super("扫雷游戏");
		panel = new MinesweeperGame();
		setSize(1000, 730);
		this.setLocationRelativeTo(null);
		addMenu();
		panel.addMouseListener(panel);
		addMouseListener(panel);
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		Thread thread = new Thread(panel);
		thread.start();
	}

	private void addMenu() {
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);

		JMenu login = new JMenu("登录/注册(L)");
		JMenuItem jitemLogin = new JMenuItem("登录");
		jitemLogin.setActionCommand("login");
		jitemLogin.addActionListener(this);
		JMenuItem jitemReg = new JMenuItem("注册");
		jitemReg.setActionCommand("reg");
		jitemReg.addActionListener(this);
		JMenuItem jitemExitlg = new JMenuItem("退出登录");
		jitemExitlg.setActionCommand("loginexit");
		jitemExitlg.addActionListener(this);
		System.out.println(Begin.isYk);
		if(Begin.isYk){
			jitemExitlg.setEnabled(false);
		}

		JMenu game = new JMenu("菜单(C)");
		JMenuItem jitemNew = new JMenuItem("新游戏");
		jitemNew.setActionCommand("new");
		jitemNew.addActionListener(this);
		JMenuItem jitemPause = new JMenuItem("暂停");
		jitemPause.setActionCommand("pause");
		jitemPause.addActionListener(this);
		JMenuItem jitemExit = new JMenuItem("退出游戏");
		jitemExit.setActionCommand("exit");
		jitemExit.addActionListener(this);

		JMenu guan = new JMenu("选关(G)");
		MinesweeperGame.jitemG2 = new JMenuItem("第二关");
		MinesweeperGame.jitemG2.addActionListener(this);
		// jitemG2.setEnabled(false);

		MinesweeperGame.jitemG2.setActionCommand("guan2");
		MinesweeperGame.jitemG3 = new JMenuItem("第三关");
		MinesweeperGame.jitemG3.addActionListener(this);
		// jitemG3.setEnabled(false);

		MinesweeperGame.jitemG3.setActionCommand("guan3");
		MinesweeperGame.jitemG4 = new JMenuItem("第四关");
		MinesweeperGame.jitemG4.addActionListener(this);
		MinesweeperGame.jitemG4.setActionCommand("guan4");
		// jitemG4.setEnabled(false);

		MinesweeperGame.jitemG5 = new JMenuItem("第五关");
		MinesweeperGame.jitemG5.addActionListener(this);
		MinesweeperGame.jitemG5.setActionCommand("guan5");
		// jitemG5.setEnabled(false);
		/*
		 * JButton button = new JButton("click me");
		 * button.setEnabled(false);//变灰
		 * 
		 * JTextField field = new JTextField("****");
		 * field.setEditable(false);//不能编辑
		 */
		JMenu help = new JMenu("关于游戏(H)");
		JMenuItem jitemHelp = new JMenuItem("帮助");
		jitemHelp.setActionCommand("help");
		jitemHelp.addActionListener(this);
		JMenuItem jitemRule = new JMenuItem("游戏规则");
		jitemRule.setActionCommand("rule");
		jitemRule.addActionListener(this);
		JMenuItem jitemMade = new JMenuItem("版本");
		jitemMade.setActionCommand("made");
		jitemMade.addActionListener(this);

		login.add(jitemLogin);
		login.add(jitemReg);
		login.add(jitemExitlg);
		menubar.add(login);

		game.add(jitemNew);
		game.add(jitemPause);
		game.add(jitemExit);
		menubar.add(game);

		guan.add(MinesweeperGame.jitemG2);
		guan.add(MinesweeperGame.jitemG3);
		guan.add(MinesweeperGame.jitemG4);
		guan.add(MinesweeperGame.jitemG5);
		menubar.add(guan);

		help.add(jitemHelp);
		help.add(jitemMade);
		help.add(jitemRule);
		menubar.add(help);
	}

	public static void SQLverify(String jtf, String jpf) {
		//System.out.println(jtf+":"+jpf);
		MinesweeperGame.u = FindInfo.findUser(jtf);
		//System.out.println(MinesweeperGame.u.getPwd());
		if(MinesweeperGame.u == null || MinesweeperGame.u == null){
			JOptionPane.showMessageDialog(null, "用户名或密码为空", "提示信息",JOptionPane.WARNING_MESSAGE);
		}else if(MinesweeperGame.u.getPwd() == null || MinesweeperGame.u.getName() == null){
			JOptionPane.showMessageDialog(null, "用户名或密码错误", "提示信息",JOptionPane.WARNING_MESSAGE);
		}else if(jpf.equals(MinesweeperGame.u.getPwd())) {	
			JOptionPane.showMessageDialog(null, "登陆成功~~~", "提示信息",JOptionPane.WARNING_MESSAGE);
			new Minesweeper();
		}
		// System.out.println(MinesweeperBorder.u.getInfo());
		
		switch (MinesweeperGame.u.getInfo()) {
		case 1:
			MinesweeperGame.jitemG2.setEnabled(false);
			MinesweeperGame.jitemG3.setEnabled(false);
			MinesweeperGame.jitemG4.setEnabled(false);
			MinesweeperGame.jitemG5.setEnabled(false);
			break;
		case 2:
			MinesweeperGame.jitemG3.setEnabled(false);
			MinesweeperGame.jitemG4.setEnabled(false);
			MinesweeperGame.jitemG5.setEnabled(false);
			break;
		case 3:
			MinesweeperGame.jitemG4.setEnabled(false);
			MinesweeperGame.jitemG5.setEnabled(false);
			break;
		case 4:
			MinesweeperGame.jitemG5.setEnabled(false);
			break;
		}
	}

	/*
	 * public static void main(String[] args)throws IOException { new
	 * Minesweeper(); }
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("login")) {
			this.dispose();
			new Login();
		}
		if (e.getActionCommand().equalsIgnoreCase("reg")) {
			new Register();
		}
		if (e.getActionCommand().equalsIgnoreCase("loginexit")) {
			panel.num = 20;
			panel.level = 1;
			panel.init();
			panel.repaint();
		}
		if (e.getActionCommand().equalsIgnoreCase("new")) {
			JOptionPane.showMessageDialog(this, "new game");
			Thread thread = new Thread(panel);
			thread.start();
		}
		if (e.getActionCommand().equalsIgnoreCase("exit")) {
			System.exit(EXIT_ON_CLOSE);
		}
		if (e.getActionCommand().equalsIgnoreCase("pause")) {
			JOptionPane.showMessageDialog(this, "pause");
		}
		if (e.getActionCommand().equalsIgnoreCase("guan2")) {
			panel.num = 12;
			panel.level = 2;
			panel.init();
			panel.repaint();
		}
		if (e.getActionCommand().equalsIgnoreCase("guan3")) {
			panel.num = 10;
			panel.level = 3;
			panel.init();
			panel.repaint();
		}
		if (e.getActionCommand().equalsIgnoreCase("guan4")) {
			panel.num = 5;
			panel.level = 4;
			panel.init();
			panel.repaint();
		}
		if (e.getActionCommand().equalsIgnoreCase("guan5")) {
			JOptionPane.showMessageDialog(this, "哇!您已经超越程序员开发的速度啦!尽情期待新关卡!");
		}
		if (e.getActionCommand().equalsIgnoreCase("help")) {
			JOptionPane.showMessageDialog(this, "help");
		}
		if (e.getActionCommand().equalsIgnoreCase("made")) {
			JOptionPane.showMessageDialog(this, "made");
		}
		if (e.getActionCommand().equalsIgnoreCase("rule")) {
			JOptionPane.showMessageDialog(this, "rule");
		}

	}

}
