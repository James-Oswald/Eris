import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.util.Random;
import java.io.File;

public class TitleScreen
{
	private JFrame frame;
	private JPanel mainMenu, playMenu, editorMenu;
	private JButton play, quit;
	private JList<String> levels;
	private JLabel eris;
	private MP3 titleMusic = new MP3("res/titleScreen.mp3");
	private Random rand = new Random();
	private boolean start = false;
	private String selected;
	
	private Image ico;
	private final int bwidth = 200, bheight = 50, width = 515, height = 600, swidth = 430, sheight = 100;
	public TitleScreen()
	{
		titleMusic.play();
		introSequence();
		frame = new JFrame("Eris");
		frame.setSize(width, height);
		frame.setIconImage(new ImageIcon("res/favicon.png").getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centerJFrame(frame);
		ico = new ImageIcon("res/mainMenu.gif").getImage();
		mainMenu = new JPanel(null)
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawImage((Image)ico, 0, 0, null);
				//delay(34);
				repaint();
			}
		};
		File[] listOfFiles = new File("levels").listFiles();
		String[] namesOfFiles = new String[listOfFiles.length];
		for(int i = 0; i < namesOfFiles.length; i++)
		{
			namesOfFiles[i] = listOfFiles[i].getName();
		}
		levels = new JList<String>(namesOfFiles);
		JScrollPane scrollPane = new JScrollPane(levels);
		scrollPane.setBounds(width / 2 - swidth / 2 , 230, swidth, sheight);
		mainMenu.add(scrollPane);
		play = new JButton("Play");
		quit = new JButton("Quit");
		eris = new JLabel();
		ImageIcon logo = new ImageIcon("res/eris2.png");
		eris.setIcon(logo);
		styleButton(play);
		styleButton(quit);
		eris.setBounds(width / 2 - logo.getIconWidth() / 2, -20, logo.getIconWidth(), logo.getIconHeight());
		play.setBounds(width / 2 + 10, 350, bwidth, bheight);
		quit.setBounds(width / 2 - bwidth - 10, 350, bwidth, bheight);
		play.addActionListener((e)->
		{
			selected = levels.getSelectedValue();
			if(selected != null)
			{
				titleMusic.close();
				frame.setVisible(false);
				start = true;
			}
		});
		quit.addActionListener((e)->
		{
			System.exit(0);
		});
		mainMenu.add(eris);
		mainMenu.add(play);
		mainMenu.add(quit);
		frame.add(mainMenu);
		frame.setVisible(true);
		while(!start)
		{
			delay(300);
		}
		new Game(selected);
	}

	
	private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static void centerJFrame(JFrame f)
	{
		f.setLocation(dim.width / 2 - f.getSize().width / 2, dim.height / 2 - f.getSize().height / 2);
	}
	
	private void styleButton(JButton b)
	{
		b.setBackground(Color.blue);
		b.setForeground(Color.white);
		b.setBorderPainted(false);
		b.setFocusPainted(false);
	}
	
	private void delay(long ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void introSequence()
	{
		JFrame intro = new JFrame();
		intro.setSize(0, 210);
		intro.setUndecorated(true);
		centerJFrame(intro);
		JPanel intoPanel = new JPanel();
		intoPanel.setBackground(Color.black);
		intoPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255)));
		JLabel logo = new JLabel(), name = new JLabel();
		logo.setIcon(new ImageIcon("res/logo.gif"));
		name.setIcon(new ImageIcon("res/name.png"));
		name.setVisible(false);
		intoPanel.add(logo);
		intoPanel.add(name);
		intro.add(intoPanel);
		intro.setVisible(true);
		for(int i = 0; i < 450; i += 2)
		{
			intro.setSize(i, 210);
			centerJFrame(intro);
			delay(10);
		}
		name.setVisible(true);
		delay(3000);
		name.setVisible(false);
		for(int i = 450; i >= 0; i -= 2)
		{
			intro.setSize(i, 210);
			centerJFrame(intro);
			delay(10);
		}
		intro.dispose();
	}
	
	public static void main(String[] args)
	{
		new TitleScreen();
	}
}