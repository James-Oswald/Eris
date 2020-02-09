import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class MousePosInput extends GLFWCursorPosCallback
{
	private static int x, y;
	private static ArrayList<BiConsumer<Integer, Integer>> actions = new ArrayList<BiConsumer<Integer, Integer>>();
	private static ArrayList<String> names = new ArrayList<String>();
	
	public void invoke(long window, double xpos, double ypos) 
	{
		x = (int)xpos;
		y = (int)ypos;
		for(int i = 0; i < actions.size(); i++)
		{
			actions.get(i).accept(x, y);
		}
	}
	
	public static int x()
	{
		return x;
	}
	
	public static int y()
	{
		return y;
	}
	
	public static void addAction(BiConsumer<Integer, Integer> f)
	{
		addAction("" + names.size() + 1, f);
	}	
	
	public static void addAction(String n, BiConsumer<Integer, Integer> f)
	{
		names.add(n);
		actions.add(f);
	}
	
	public static void removeAction(String n)
	{
		actions.remove(names.indexOf(n));
		names.remove(n);
	}
}