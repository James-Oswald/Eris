
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import javax.swing.SwingUtilities;

public class Window
{
	private static int width, height;
	private static long window;
	private static String name, icon;
	private static GLFWKeyCallback kcb;
	private static GLFWCursorPosCallback cpcb;
	private static GLFWMouseButtonCallback mbcb;
	private static JFrame infoFrame;
	
	private static final int infoHeight = 200;
	
	public Window(String name, int width, int height, String icon)
	{
		try
		{
			this.name = name;
			this.width = width;
			this.height = height;
			this.icon = icon;
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int)((dimension.getWidth() - width) / 2);
			int y = (int)((dimension.getHeight() - height) / 2);
			infoFrame = new JFrame();
			infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			infoFrame.setUndecorated(true);
			infoFrame.setSize(width, infoHeight);
			infoFrame.setLocation(x, y + height / 2 + infoHeight);
			glfwInit();
			//glfwDefaultWindowHints();
			glfwWindowHint(GLFW_STENCIL_BITS, 4);
			glfwWindowHint(GLFW_SAMPLES, 4);
			glfwWindowHint(GLFW_DECORATED, 0);
			window = glfwCreateWindow(width, height, name, 0, 0);
			if(icon != null) IconLoader.load(window, icon);
			glfwSetWindowPos(window, x, y - infoHeight);
			glfwMakeContextCurrent(window);
			glfwSwapInterval(1);
			glfwShowWindow(window);
			glfwSetKeyCallback(window, kcb = new KeyInput());
			glfwSetCursorPosCallback(window, cpcb = new MousePosInput());
			glfwSetMouseButtonCallback(window, mbcb = new MouseBtnInput());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Window(String name, int width, int height) throws Exception
	{
		this(name, width, height, null);
	}
	
	public static int width()
	{
		return width;
	}
	
	public static int height()
	{
		return height;
	}
	
	public static double time()
	{
		return glfwGetTime();
	}
	
	public static JFrame getInfoFrame()
	{
		return infoFrame;
	}
	
	public static boolean shouldClose()
	{
		return glfwWindowShouldClose(window);
	}
	
	public static void swapBuffers()
	{
		glfwSwapBuffers(window);
	}
	
	public static long id()
	{
		return window;
	}
	
	public static void delete()
	{
		glfwDestroyWindow(window);
		infoFrame.setVisible(false);
		infoFrame.dispose();
	}
	
	public static void curEnabled(boolean b)
	{
		glfwSetInputMode(window, GLFW_CURSOR, b ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED); 
	}
}