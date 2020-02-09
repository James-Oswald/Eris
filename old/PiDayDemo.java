import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import java.util.function.Supplier;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import static java.lang.Math.*;
import java.awt.Color;

public class PiDayDemo
{
	static
	{
		Renderer.init();
	}
	
	public Shader.Uniforms genUniforms(Supplier<Matrix4f> trans)
	{
		return (id) ->
		{
			float[] f = new float[16];
			trans.get().get(f);
			glUniformMatrix4fv(glGetUniformLocation(id, "model"), false, f);
			glUniformMatrix4fv(glGetUniformLocation(id, "view"), true, Camera.view());
			glUniformMatrix4fv(glGetUniformLocation(id, "projection"), true, Camera.projection());
		};
	}
	
	public static final int dist = -5;
	
	public void run()
	{
		Model[] m = new Model[]
		{
			//sun
			new Model(Model.createVAOFromFile("sphere.obj", new Color[]
			{
				Color.yellow,
				Color.red,
				Color.orange
			}), 
			new Shader("vertex.glsl", "frag.glsl"), genUniforms(()->
			{
				return new Matrix4f().translate(new Vector3f(0, dist, 0)).scale(2).rotate((float)(glfwGetTime() * 0.5 % (2 * PI)), 0, 1, 0);
			})),
			
			//mecury
			new Model(Model.createVAOFromFile("sphere.obj", new Color[]
			{
				new Color(50, 50, 50), 
				new Color(150, 150, 150), 
				new Color(150, 150, 150)
			}), 
			new Shader("vertex.glsl", "frag.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() % (2 * PI)) * 4;
				float z = (float)sin(glfwGetTime() % (2 * PI)) * 4;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(0.5f);
			})),
			
			//venus
			new Model(Model.createVAOFromFile("sphere.obj", new Color[]
			{
				new Color(225, 225, 160), 
				new Color(150, 150, 110), 
				new Color(130, 130, 60)
			}), 
			new Shader("vertex.glsl", "frag.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() * 0.5 % (2 * PI)) * 6;
				float z = (float)sin(glfwGetTime() * 0.5 % (2 * PI)) * 6;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(0.8f);
			})),
			
			//earth
			new Model(Model.createVAOFromFile("sphere.obj", new Color[]
			{
				Color.blue, 
				Color.blue,
				Color.green, 
				Color.white
			}), 
			new Shader("vertex.glsl", "frag.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() * 0.7 % (2 * PI)) * 9;
				float z = (float)sin(glfwGetTime() * 0.7 % (2 * PI)) * 9;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(0.8f);
			})),
			
			//mars
			new Model(Model.createVAOFromFile("sphere.obj", new Color[]
			{
				Color.red, 
				new Color(135, 60, 60), 
				new Color(200, 40, 40)
			}), 
			new Shader("vertex.glsl", "frag.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() * 0.3 % (2 * PI)) * 11;
				float z = (float)sin(glfwGetTime() * 0.3 % (2 * PI)) * 11;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(0.6f);
			})),
		};
		World.add(m);
		Renderer.loop();
	}
	
	public static void main(String[] args)
	{
		new PiDayDemo().run();
	}
}