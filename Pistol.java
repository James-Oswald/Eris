
import java.awt.Color;
import org.joml.Vector3f;
import java.util.ArrayList;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class Pistol extends Weapon
{
	protected Model bulletModel = new Model("res/sphere.obj", Color.black, Color.gray);
	protected float lastShot;
	protected final float fireRate = 0.5f;
	protected MP3 sound = new MP3("res/pistol.mp3");
	
	public Pistol()
	{
		super(new Model("res/pistol.obj", Color.black, Color.white), 2, 0.01f, (float)(7 * Math.PI / 6));
	}
	
	public void action(int act)
	{
		boolean canfire = true;
		if(owner instanceof Player)
		{
			canfire = ((Player)owner).getPammo() > 0;
		}
		if(glfwGetTime() > lastShot + fireRate && canfire)
		{
			if(act == 0)
			{
				Entity bullet = new Entity(bulletModel, new Vector3f(position), changeAcc(new Vector3f(direction).normalize(), 0.05f), new Vector3f(), new Vector3f(1, 0, 1), 0.03f, 5, 10);
				World.add(bullet);
				cons.add(bullet);
				if(owner instanceof Player)
				{
					sound.play();
					((Player)owner).usePammo();
				}
			}
			lastShot = (float)glfwGetTime();
		}
	}
}