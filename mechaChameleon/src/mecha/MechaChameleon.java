package mecha;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * 메카 카멜레온
 * - 방향키: 이동
 * - 스페이스: 혀(레이저 텅) 발사로 파리 잡기
 * - C: 위장 모드 ON/OFF (켜면 서 있는 타일 색으로 서서히 변함, 에너지 소모)
 */
public class MechaChameleon extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int TILE = 60;
	private static final int COLS = 13;
	private static final int ROWS = 9;
	private static final int WIDTH = TILE * COLS;
	private static final int HEIGHT = TILE * ROWS;
	private static final Color[] PALETTE = {
			new Color(46, 139, 87), new Color(210, 180, 140), new Color(70, 130, 180),
			new Color(178, 34, 34), new Color(255, 215, 0), new Color(105, 105, 105) };

	private final Color[][] map = new Color[ROWS][COLS];
	private final List<int[]> flies = new ArrayList<>();
	private final Random random = new Random();
	private final boolean[] keys = new boolean[256];

	// 카멜레온 상태
	private double x = WIDTH / 2.0, y = HEIGHT / 2.0;
	private int dir = 1; // 1: 오른쪽, -1: 왼쪽
	private Color bodyColor = new Color(120, 200, 120);
	private boolean camouflage = false;
	private double energy = 100;
	private int tongue = 0; // 혀 길이 (0이면 집어넣은 상태)
	private boolean tongueOut = false;
	private int score = 0;
	private int tick = 0;

	public MechaChameleon() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				map[r][c] = PALETTE[random.nextInt(PALETTE.length)];

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (code < keys.length) keys[code] = true;
				if (code == KeyEvent.VK_SPACE && tongue == 0) tongueOut = true;
				if (code == KeyEvent.VK_C) camouflage = !camouflage;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
				if (code < keys.length) keys[code] = false;
			}
		});

		new Timer(16, e -> {
			update();
			repaint();
		}).start();
	}

	private void update() {
		tick++;
		double speed = camouflage ? 1.5 : 3.5; // 위장 중엔 느리게
		if (keys[KeyEvent.VK_LEFT]) { x -= speed; dir = -1; }
		if (keys[KeyEvent.VK_RIGHT]) { x += speed; dir = 1; }
		if (keys[KeyEvent.VK_UP]) y -= speed;
		if (keys[KeyEvent.VK_DOWN]) y += speed;
		x = Math.max(40, Math.min(WIDTH - 40, x));
		y = Math.max(30, Math.min(HEIGHT - 30, y));

		// 위장: 발밑 타일 색으로 보간
		Color target = camouflage && energy > 0 ? tileAt(x, y) : new Color(120, 200, 120);
		bodyColor = lerp(bodyColor, target, 0.08);
		energy = camouflage ? Math.max(0, energy - 0.15) : Math.min(100, energy + 0.1);
		if (energy == 0) camouflage = false;

		// 혀 발사/회수
		if (tongueOut) {
			tongue += 12;
			if (tongue >= 160) tongueOut = false;
		} else if (tongue > 0) {
			tongue = Math.max(0, tongue - 16);
		}

		// 파리 생성/이동/포획
		if (flies.size() < 6 && random.nextInt(60) == 0)
			flies.add(new int[] { random.nextInt(WIDTH), random.nextInt(HEIGHT) });
		double tipX = x + dir * (35 + tongue), tipY = y - 5;
		for (Iterator<int[]> it = flies.iterator(); it.hasNext();) {
			int[] f = it.next();
			f[0] = Math.floorMod(f[0] + random.nextInt(7) - 3, WIDTH);
			f[1] = Math.floorMod(f[1] + random.nextInt(7) - 3, HEIGHT);
			if (tongue > 0 && Math.hypot(f[0] - tipX, f[1] - tipY) < 18) {
				it.remove();
				score++;
				energy = Math.min(100, energy + 15);
			}
		}
	}

	private Color tileAt(double px, double py) {
		int c = Math.min(COLS - 1, (int) (px / TILE));
		int r = Math.min(ROWS - 1, (int) (py / TILE));
		return map[r][c];
	}

	private static Color lerp(Color a, Color b, double t) {
		return new Color(
				(int) (a.getRed() + (b.getRed() - a.getRed()) * t),
				(int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t),
				(int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t));
	}

	@Override
	protected void paintComponent(Graphics g0) {
		super.paintComponent(g0);
		Graphics2D g = (Graphics2D) g0;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++) {
				g.setColor(map[r][c]);
				g.fillRect(c * TILE, r * TILE, TILE, TILE);
			}

		g.setColor(Color.BLACK);
		for (int[] f : flies) {
			g.fillOval(f[0] - 4, f[1] - 4, 8, 8);
			g.drawLine(f[0] - 6, f[1] - (tick % 10 < 5 ? 6 : 2), f[0] + 6, f[1] - (tick % 10 < 5 ? 2 : 6));
		}

		drawChameleon(g);

		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		g.drawString("SCORE " + score + "   ENERGY " + (int) energy + "%   CAMO " + (camouflage ? "ON" : "OFF"), 10, 20);
	}

	private void drawChameleon(Graphics2D g) {
		int cx = (int) x, cy = (int) y;
		Color armor = bodyColor.darker();

		// 혀 (레이저 텅)
		if (tongue > 0) {
			g.setStroke(new BasicStroke(4));
			g.setColor(new Color(255, 60, 120));
			g.drawLine(cx + dir * 35, cy - 5, cx + dir * (35 + tongue), cy - 5);
			g.fillOval(cx + dir * (35 + tongue) - 6, cy - 11, 12, 12);
		}

		// 말린 꼬리
		g.setStroke(new BasicStroke(6));
		g.setColor(armor);
		g.drawArc(cx - dir * 45 - 15, cy - 5, 30, 30, dir > 0 ? 90 : -90, 270);

		// 다리 (피스톤 관절)
		g.setStroke(new BasicStroke(4));
		int step = (int) (Math.sin(tick * 0.3) * 4);
		for (int i = -1; i <= 1; i += 2) {
			g.drawLine(cx + i * 15, cy + 10, cx + i * 18 + step, cy + 25);
			g.fillRect(cx + i * 18 + step - 4, cy + 24, 8, 4);
		}

		// 몸통 + 장갑판
		g.setColor(bodyColor);
		g.fillRoundRect(cx - 30, cy - 15, 60, 28, 20, 20);
		g.setColor(armor);
		g.setStroke(new BasicStroke(2));
		g.drawRoundRect(cx - 30, cy - 15, 60, 28, 20, 20);
		for (int i = -15; i <= 15; i += 10) g.drawLine(cx + i, cy - 15, cx + i, cy + 13);

		// 머리
		g.setColor(bodyColor);
		int hx = cx + dir * 30;
		g.fillOval(hx - 14, cy - 20, 28, 24);
		g.setColor(armor);
		g.drawOval(hx - 14, cy - 20, 28, 24);

		// 로봇 눈 (회전하는 렌즈)
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(hx - 7, cy - 22, 14, 14);
		g.setColor(Color.RED);
		int ex = (int) (Math.cos(tick * 0.1) * 3), ey = (int) (Math.sin(tick * 0.1) * 3);
		g.fillOval(hx - 3 + ex, cy - 18 + ey, 6, 6);

		// 등 안테나
		g.setColor(Color.DARK_GRAY);
		g.drawLine(cx, cy - 15, cx, cy - 28);
		g.setColor(tick % 30 < 15 ? Color.CYAN : Color.BLUE);
		g.fillOval(cx - 3, cy - 32, 6, 6);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Mecha Chameleon");
			MechaChameleon panel = new MechaChameleon();
			frame.add(panel);
			frame.pack();
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			panel.requestFocusInWindow();
		});
	}
}
