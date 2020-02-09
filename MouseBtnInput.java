
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class MouseBtnInput extends GLFWMouseButtonCallback
{
	private static int button, action;
	private static ArrayList<BiConsumer<Integer, Integer>> actions = new ArrayList<BiConsumer<Integer, Integer>>();
	private static ArrayList<String> names = new ArrayList<String>();
	
	public void invoke(long window, int button_, int action_, int mods_)
	{
		button = button_;
		action = action_;
		for(int i = 0; i < actions.size(); i++)
		{
			actions.get(i).accept(button, action);
		}
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