import java.awt.Color;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;

public class Test2
{	
	private static final Vector3f COFFSET = new Vector3f(0, 1, 0.5f);
	private static final Vector3f position = new Vector3f(0, 0, 0);
	private static float speed = 100;

	public static void main(String[] args)
	{
		World w = new World();
		Renderer.init();
		Renderer.setWorld(w);
		new Camera(new Vector3f(0, 0, 0).add(COFFSET));
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
		Model m = new Model("res/cow.obj", Color.blue);
		w.add(m);
		Renderer.loop();
	}
}