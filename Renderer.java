import java.util.ArrayList;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;

public class Renderer
{
	private static float deltaTime, lastFrame, currentFrame;
	private static boolean loopShouldStop;
	private static ArrayList<Runnable> actions, ndActions;
	
	public static void addAction(Runnable r)
	{
		actions.add(r);
	}
	
	public static void addNdAction(Runnable r)
	{
		ndActions.add(r);
	}
	
	public static float getDeltaTime()
	{
		return deltaTime;
	}
	
	public static void init()
	{
		actions = new ArrayList<Runnable>();
		ndActions = new ArrayList<Runnable>();
		//new Window("Test", 1920, 1080, "res/lex.png");
		new Window("Test", 800, 800, "res/lex.png");
		GL.createCapabilities();
		glViewport(0,0, Window.width(), Window.height());
		Window.curEnabled(false);
		addAction(() ->
		{
			if(KeyInput.isDown(GLFW_KEY_ESCAPE))
			{
				//System.exit(0);
				loopShouldStop = true;
			}
			if(KeyInput.isDown(GLFW_KEY_RIGHT_SHIFT))
			{
				Window.curEnabled(true);
			}
			else
			{
				Window.curEnabled(false);
			}
		});
		glClearDepth(1);
		glEnable(GL_DEPTH_TEST);
		glClearColor(0, 0, 0, 0);
	}
	
	public static void loop()
	{
		try
		{
			loopShouldStop = false;
			new Thread(()->
			{
				while(!Window.shouldClose() && !loopShouldStop)
				{
					ndActions.forEach(Runnable::run);
				}
			}).start();
			while(!Window.shouldClose() && !loopShouldStop)
			{
				glfwPollEvents();
				currentFrame = (float)glfwGetTime();
				deltaTime = currentFrame - lastFrame;
				lastFrame = currentFrame;
				for(int i = 0; i < actions.size(); i++)
				{
					actions.get(i).run();
				}
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				World.render();
				Window.swapBuffers();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void endLoop()
	{
		loopShouldStop = true;
	}
	
	public static void delayUtil(int ms)
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
	
	public static void end()
	{
		endLoop();
		World.delete();
		Window.delete();
		glfwTerminate();
	}
}