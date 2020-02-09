import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import java.util.function.BiConsumer;
import java.util.ArrayList;

public class KeyInput extends GLFWKeyCallback
{
	private final static boolean[] keys = new boolean[65536];
	private static ArrayList<BiConsumer<Integer, Integer>> actions = new ArrayList<BiConsumer<Integer, Integer>>();
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) 
	{
		keys[key] = action != GLFW_RELEASE;
		for(int i = 0; i < actions.size(); i++)
		{
			actions.get(i).accept(key, action);
		}
	}
	
	public static boolean isDown(int key)
	{
		return keys[key];
	}
	
	public static void addAction(BiConsumer<Integer, Integer> f)
	{
		actions.add(f);
	}
	
	public static void addRemoveAction(BiConsumer<Integer, Integer> f)
	{
		actions.remove(f);
	}
}