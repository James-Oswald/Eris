
import java.awt.Color;
import org.joml.Vector3f;
import java.util.ArrayList;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class Shotgun extends Pistol
{
	public Shotgun()
	{
		super();
		dmg = 1;
		sound = new MP3("res/shotgun.mp3");
	}

	@Override
	public void action(int act)
	{
		boolean canfire = true;
		if(owner instanceof Player)
		{
			canfire = ((Player)owner).getSammo() > 0;
		}
		if(glfwGetTime() > lastShot + fireRate && canfire)
		{
			if(act == 0)
			{
				int shots = (int)Math.floor(5 * Math.random() + 5);
				for(int i = 0; i < shots; i++)
				{
					Entity bullet = new Entity(bulletModel, new Vector3f(position), changeAcc(new Vector3f(direction).normalize(), 0.1f), new Vector3f(), new Vector3f(1, 0, 1), 0.01f, 5, 10);
					World.add(bullet);
					cons.add(bullet);
				}
				if(owner instanceof Player)
				{
					sound.play();
					((Player)owner).useSammo();
				}
			}
			lastShot = (float)glfwGetTime();
		}
	}
}