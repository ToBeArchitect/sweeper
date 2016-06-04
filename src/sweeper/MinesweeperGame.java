package sweeper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;

import dao.FindInfo;
import dao.User;

public class MinesweeperGame extends JPanel implements MouseListener, Runnable {
	private static final long serialVersionUID = 1L;
	public Sweeper[] images = new Sweeper[101];// 用于存放100个图标的信息下标从1~100
	public static final int MARGIN = 30; // 边距
	public static final int GRID_SPAN = 62; // 网格间距
	public static final int ROWS = 10;// 行数
	public static final int COLS = 10;// 列数
	public static int num = 24;// 控制雷的个数
	public static int sweepernum = 0;//雷的个数
	public static int level;
	public static int dblevel;
	public static JMenuItem jitemG2 = null;
	public static JMenuItem jitemG3 = null;
	public static JMenuItem jitemG4 = null;
	public static JMenuItem jitemG5 = null;
	public static User u;
	public static String name;
	Image s;
	Image s1;
	Image s2;
	Image s0;
	Image s3;// time
	Image s4;// 灰色背景图片
	Image grey;
	boolean gameStart;// 判断游戏是否开始
	boolean gameOver;// 判断游戏是否结束
	private int countSweeper;// 剩余雷的个数
	private int leftSweeper = 100;// 统计剩余未被点击过的图片,开始时为100；
	private int time;
	private int bestTime;// 时间记录

	public MinesweeperGame() {
		init();
	}

	// 该方法用于数据初始化
	public void init() {
		// 初始化
		level = 1;
		gameOver = false;// 游戏未结束
		gameStart = false;// 游戏未开始
		countSweeper = 100 / num;
		System.out.println("雷的个数"+countSweeper);
		leftSweeper = 100;
		time = 0;
		for (int i = 0; i <= 100; i++)
			images[i] = new Sweeper();

		// 对images[i]中的i进行随机标记，标记以后，放置雷的位置
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int i = 1; i < 100; i++) {
			arr.add(i);
		}
		// 打乱雷的排列顺序
		Collections.shuffle(arr);
		// 默认为images[i + 1].i % num == 0个雷
		for (int i = 0; i < arr.size(); i++) {
			images[i + 1].i = arr.get(i);
			
			if (images[i + 1].i % num == 0) {
				sweepernum++;
				images[i].judge = true;
				System.out.println("作弊:第" + i + "个是雷");
			}
		}
		System.out.println("-------------------------");
		// System.out.println("num:"+num);
		// System.out.println("sweeper:"+countSweeoer);

		// 绘制图标,先将原图置为60*60像素
		try {
			s = ImageIO.read(new FileInputStream("pictures/1.jpg"));
			s1 = ImageIO.read(new FileInputStream("pictures/2.jpg"));
			s2 = ImageIO.read(new FileInputStream("pictures/5.jpg"));
			s0 = ImageIO.read(new FileInputStream("pictures/0.png"));// huise
			s3 = ImageIO.read(new FileInputStream("pictures/time.png"));// time图片
			s4 = ImageIO.read(new FileInputStream("pictures/1.jpg"));// 灰色图片
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = 60; // 假设要缩小到60点像素
		int height = s.getHeight(null) * 60 / s.getWidth(null);// 按比例，将高度缩减
		Image sweeper = s.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		grey = s4.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		int k = 1;
		for (int j = MARGIN; j < GRID_SPAN * ROWS + MARGIN; j = j + GRID_SPAN) {
			for (int i = MARGIN * 4; i < GRID_SPAN * COLS + MARGIN * 4; i = i
					+ GRID_SPAN) {
				// System.out.println(k);
				images[k].images = sweeper;
				images[k].x = i;
				images[k].y = j;
				k++;
			}
		}
		lookNumber();
	}

	// 图片绘制
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 背景图片绘制
		Image imageBG = new ImageIcon("pictures/background.jpg").getImage();
		g.drawImage(imageBG, 0, 0, null);
		int width = 60; // 假设要缩小到60点像素
		int height = s.getHeight(null) * 60 / s.getWidth(null);// 按比例，将高度缩减
		Image sweeper0 = s0
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);// 灰色图片
		Image sweeper1 = s1
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);// 红旗标记图片
		Image sweeper2 = s2
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);// 地雷图片
		Image timePicture = s3.getScaledInstance(width, height,
				Image.SCALE_SMOOTH);// 时间图片
		g.setColor(Color.white);
		Font font = new Font("", Font.BOLD, 60);
		g.setFont(font);
		// 画图标 10*10
		// 最低层为灰色图片
		for (int i = 1; i <= 100; i++)
			g.drawImage(sweeper0, images[i].x, images[i].y, null);
		for (int i = 1; i <= 100; i++) {
			if (images[i].isClicked) {// 当该图片被单击过，则有两种情况：1.该图片既不为空且又不是雷；2.该图片为雷
				if (images[i].i != 0 && !images[i].judge)// 该图片不为空且不是雷
					g.drawString("" + images[i].i, images[i].x + 13,
							images[i].y + GRID_SPAN / 2 + 20);
				else if (images[i].judge) {
					g.drawImage(sweeper2, images[i].x, images[i].y, null);
					gameOver = true;
				} else
					continue;
			} else if (images[i].isMetaDown)
				g.drawImage(sweeper1, images[i].x, images[i].y, null);
			else if (!images[i].isMetaDown && !images[i].isClicked)
				g.drawImage(images[i].images, images[i].x, images[i].y, null);
		}
		// 时间计时
		g.drawString("" + time, 885, 255);// 白色
		g.drawString("RD:" + bestTime, 800, 350);// 白色
		g.drawImage(timePicture, 800, 200, null);
		// 当前用户
		System.out.println(name);
		g.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		g.setColor(Color.yellow);
		g.drawString("当前用户："+name, 800, 50);
		// 剩余雷的标志
		g.drawImage(sweeper2, 800, 100, null);
		g.setColor(Color.red);// 红色
		font = new Font("", Font.BOLD, 70);
		g.setFont(font);

		// 剩余雷的个数
		g.drawString("" + countSweeper, 870, 160);

		if (gameOver || leftSweeper == sweepernum) {
			g.setColor(Color.white);
			for (int i = 1; i <= 100; i++)
				g.drawImage(sweeper0, images[i].x, images[i].y, null);
			for (int i = 1; i <= 100; i++) {
				if (images[i].judge) {
					g.drawImage(sweeper2, images[i].x, images[i].y, null);
				} else if (images[i].i != 0)
					g.drawString("" + images[i].i, images[i].x + 13,
							images[i].y + GRID_SPAN / 2 + 20);
				else if (images[i].i == 0)
					g.drawImage(sweeper0, images[i].x, images[i].y, null);

			}

		}
	}

	// 该方法用于查找每张图片周围的地雷数，即单击时所出现的数字
	private void lookNumber() {
		int[] loca = new int[8];
		for (int i = 1; i <= 100; i++) {
			if (images[i].judge)
				continue;
			// 左、左上、左下
			if ((i - 1) % 10 == 0) {// 处于第一列
				loca[0] = -1;
				loca[4] = -1;
				loca[6] = -1;
			} else {
				loca[0] = i - 1;
				loca[4] = i - 11;
				loca[6] = i + 9;
			}
			// 右、右上、右下
			if (i % 10 == 0) {// 处于最后一列
				loca[1] = -1;
				loca[5] = -1;
				loca[7] = -1;
			} else {
				loca[1] = i + 1;
				loca[5] = i - 9;
				loca[7] = i + 11;
			}

			// 上
			loca[2] = i - 10;
			// 下
			loca[3] = i + 10;
			int count = 0;
			for (int j = 0; j < 8; j++) {
				if (loca[j] < 0 || loca[j] > 100)
					continue;
				if (images[loca[j]].judge)
					count++;
			}
			images[i].i = count;
		}
	}

	// 该方法用于点击的图片为空白时，找出周围空白的图片
	private void isBlank(int k) {
		if (images[k].i != 0)
			return;
		int[] loca = new int[8];
		// 左、左上、左下
		if ((k - 1) % 10 == 0) {// 处于第一列
			loca[0] = -1;
			loca[4] = -1;
			loca[6] = -1;
		} else {
			loca[0] = k - 1;
			loca[4] = k - 11;
			loca[6] = k + 9;
		}
		// 右、右上、右下
		if (k % 10 == 0) {// 处于最后一列
			loca[1] = -1;
			loca[5] = -1;
			loca[7] = -1;
		} else {
			loca[1] = k + 1;
			loca[5] = k - 9;
			loca[7] = k + 11;
		}

		// 上
		loca[2] = k - 10;
		// 下
		loca[3] = k + 10;
		for (int j = 0; j < 8; j++) {
			if (loca[j] < 0 || loca[j] > 100 || images[loca[j]].isClicked)
				continue;
			images[loca[j]].isClicked = true;
			leftSweeper--;
			if (images[loca[j]].i == 0) {
				isBlank(loca[j]);// 递归
			}

		}
	}

	// Lose/Win
	public void isGameOver() {
		if (gameOver) {
			gameStart = false;// 计时器停止
		
			int choice = JOptionPane.showConfirmDialog(MinesweeperGame.this,
					"再试一局吗,少年？", null, JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				init();
				repaint();
			} else
				System.exit(0);
		}
		if (leftSweeper == sweepernum) {
			gameStart = false;// 计时器停止

			JOptionPane.showMessageDialog(null, "哇,您已经成功解锁下一关喽，加油!", "提示信息",
					JOptionPane.WARNING_MESSAGE);
			System.out.println(Begin.isYk);
			if(!Begin.isYk){
				switch (level+1) {
				
				case 2:
					if (level > (dblevel = FindInfo.findLevel(u.getName()))) {
						dblevel++;
						FindInfo.addLevel(u, dblevel);
					}
					num = 12;
					this.init();
					this.repaint();
					break;
					
				case 3:
					if (level > (dblevel = FindInfo.findLevel(u.getName()))) {
						dblevel++;
						FindInfo.addLevel(u, dblevel);
					}
					num = 10;
					this.init();
					this.repaint();
					
				case 4:
					if (level > (dblevel = FindInfo.findLevel(u.getName()))) {
						dblevel++;
						FindInfo.addLevel(u, dblevel);
					}
					num = 5;
					this.init();
					this.repaint();
				default:
					break;
				}
			}
		}
	}

	// 鼠标点击事件
	@Override
	public void mouseClicked(MouseEvent e) {
		// 鼠标右击事件
		if (e.isMetaDown()) {
			for (int j = 1; j < 101; j++) {
				int x1 = images[j].x + GRID_SPAN - 2;// 右
				int x2 = images[j].x + 2;// 左
				int y1 = images[j].y + GRID_SPAN - 2;// 下
				int y2 = images[j].y + 2;// 上
				if ((e.getX() < x1) && (e.getX() > x2) && (e.getY() < y1)
						&& (e.getY() > y2) && !images[j].isClicked) {
					if (images[j].isMetaDown) {// 如果之前已经标记，则再次右键点击取消该标记
						countSweeper++;
						images[j].isMetaDown = false;
						repaint();
					} else {
						countSweeper--;
						images[j].isMetaDown = true;
						repaint();
					}
				}
			}
		}
		// 中间键点击无效
		else if (e.isAltDown())
			;
		// 左键点击
		else {
			for (int j = 1; j < 101; j++) {
				int x1 = images[j].x + GRID_SPAN - 2;// 右
				int x2 = images[j].x + 2;// 左
				int y1 = images[j].y + GRID_SPAN - 2;// 下
				int y2 = images[j].y + 2;// 上
				if ((e.getX() < x1) && (e.getX() > x2) && (e.getY() < y1)
						&& (e.getY() > y2) && !images[j].isClicked) {
					if (images[j].isMetaDown) {// 该图片已被标记，再次点击则取消该标记
						countSweeper++;
						images[j].isMetaDown = false;
						repaint();
					} else if (images[j].judge) {
						images[j].isClicked = true;
						leftSweeper--;
						repaint();
						gameOver = true;
						isGameOver();

					}// 未被点击过，此时单击
					else {
						resume();// 线程开始
						leftSweeper--;
						images[j].isClicked = true;
						isBlank(j);
						repaint();
						isGameOver();
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	// 游戏开始
	@Override
	public void run() {
		while (true) {// gameStart=true时，游戏开始，开始计时
			try {
				Thread.sleep(1000);
				synchronized (this) {
					while (!gameStart) {
						wait();
					}
					time++;
					repaint();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 线程继续
	synchronized void resume() {
		gameStart = true;
		notify();// 唤醒线程
	}
}
