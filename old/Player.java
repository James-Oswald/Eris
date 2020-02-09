
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity
{
	private static final Vector3f COFFSET = new Vector3f(0, 1, 0.5f);
	
	private float speed = 100;
	
	public Player()
	{
		this(0, 0, 0);
	}
	
	public Player(int x, int y, int z)
	{
		new Camera(new Vector3f(x, y, z).add(COFFSET));
		Camera.setMode(Camera.FPS);
		Renderer.addAction(()->
		{
			float cameraSpeed = speed * Renderer.getDeltaTime();
			if(KeyInput.isDown(GLFW_KEY_W))
			{
				position.add(new Vector3f(Camera.getFront()).mul(cameraSpeed));
			}
			if(KeyInput.isDown(GLFW_KEY_S))
			{
				position.add(new Vector3f(Camera.getFront()).mul(-cameraSpeed));
			}
			if(KeyInput.isDown(GLFW_KEY_A))
			{
				position.add(new Vector3f(Camera.getRight()).mul(cameraSpeed));
			}
			if(KeyInput.isDown(GLFW_KEY_D))
			{
				position.add(new Vector3f(Camera.getRight()).mul(-cameraSpeed));
			}
			if(KeyInput.isDown(GLFW_KEY_LEFT_SHIFT))
			{
				position.add(0, -1, 0);
			}
			if(KeyInput.isDown(GLFW_KEY_SPACE))
			{
				position.add(0, 1, 0);
			}
			//position = new Vector3f(position.x, 0, position.z);
			Camera.moveTo(new Vector3f(position).add(COFFSET));
		});
	}
	
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
}