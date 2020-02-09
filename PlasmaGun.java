
import java.awt.Color;
import org.joml.Vector3f;
import java.util.ArrayList;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class PlasmaGun extends Weapon
{
	//private ArrayList<Entity> cons = new ArrayList<Entity>(); 
	private Model bulletModel = new Model("res/sphere.obj", new Color(0, 255, 255), new Color(0, 255, 255), Color.blue);
	private float lastShot;
	private final float fireRate = 0.1f;
	private final MP3 sound = new MP3("res/laser.mp3");
	
	public PlasmaGun()
	{
		super(new Model("res/PortalGun.obj", Color.black, Color.white), 3, 0.2f, 0);
	}
	
	public void action(int act)
	{
		boolean canfire = true;
		if(owner instanceof Player)
		{
			canfire = ((Player)owner).getLammo() > 0;
		}
		if(glfwGetTime() > lastShot + fireRate && canfire)
		{
			if(act == 0)
			{
				Entity bullet = new Entity(bulletModel, new Vector3f(position), new Vector3f(direction).normalize(), new Vector3f(), new Vector3f(1, 0, 1), 0.1f, 5, 10);
				World.add(bullet);
				cons.add(bullet);
				if(owner instanceof Player)
				{
					sound.play();
					((Player)owner).useLammo();
				}
			}
			lastShot = (float)glfwGetTime();
		}
	}
}