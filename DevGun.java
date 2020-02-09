
import java.awt.Color;
import org.joml.Vector3f;
import java.util.ArrayList;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class DevGun extends Weapon
{
	//private ArrayList<Entity> cons = new ArrayList<Entity>(); 
	private Model bulletModel = new Model("res/cube.obj", Color.white, Color.green);
	private float lastShot;
	private final float fireRate = 0.2f;
	
	public DevGun()
	{
		super(new Model("res/PortalGun.obj", Color.black, Color.white), 1, 0.2f);
	}
	
	public void action(int act)
	{
		if(glfwGetTime() > lastShot + fireRate)
		{
			if(act == 0)
			{
				Entity bullet = new Entity(bulletModel, new Vector3f(position), new Vector3f(direction).normalize(), new Vector3f(), new Vector3f(1, 0, 1), 0.1f, 5, 5);
				World.add(bullet);
				cons.add(bullet);
			}
			if(act == 1)
			{
				Entity bullet = new Entity(bulletModel, new Vector3f(position), new Vector3f(direction).normalize(), new Vector3f(), new Vector3f(1, 0, 1), 0.1f, 1, 1);
				World.add(bullet);
				cons.add(bullet);
			}
			lastShot = (float)glfwGetTime();
		}
	}
}