import java.awt.Color;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;

public class Test
{	
	public static void main(String[] args)
	{
		World w = new World();
		Renderer.init();
		Renderer.setWorld(w);
		w.add(new Player());
		w.add(new Model("res/cow.obj", Model.genUniforms(() -> {return new Matrix4f().translate(10, 10, 10).rotate((float)Window.time() / 2, 1, 0, 0);}), Color.red, Color.white));
		w.add(new GObject("res/sphere.obj", ()->{return new Matrix4f().rotate((float)Window.time() / 2, 0, 1, 0);}));
		w.add(new Entity("res/cow.obj", new Vector3f(12, 0, 6), new Vector3f(30, 20, 60), new Vector3f(0, 0, 0)));
		w.add(new Model("res/circle.obj", Model.genUniforms(() -> {return new Matrix4f().rotate((float)Math.PI / 2, 1, 0, 0).scale(3000);}), Color.green));
		Renderer.loop();
	}
}