import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import java.util.function.Supplier;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import static java.lang.Math.*;
import java.awt.Color;
import java.util.Random;

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
	public static final Random r = new Random();
	
	public void run()
	{
		World w = new World();
		Renderer.setWorld(w);
		Model[] m = new Model[]
		{
			//sun
			new Model("res/sphere.obj", new Color[]
			{
				Color.yellow,
				Color.red,
				Color.orange
			}, 
			new Shader("defVertex.glsl", "defFragment.glsl"), genUniforms(()->
			{
				return new Matrix4f().translate(new Vector3f(0, dist, 0)).scale(3).rotate((float)(glfwGetTime() * 0.5 % (2 * PI)), 0, 1, 0);
			})),
			
			//mecury
			new Model("res/sphere.obj", new Color[]
			{
				new Color(50, 50, 50), 
				new Color(150, 150, 150), 
				new Color(150, 150, 150)
			}, 
			new Shader("defVertex.glsl", "defFragment.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() % (2 * PI)) * 4;
				float z = (float)sin(glfwGetTime() % (2 * PI)) * 4;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(0.4f);
			})),
			
			//venus
			new Model("res/sphere.obj", new Color[]
			{
				new Color(225, 225, 160), 
				new Color(150, 150, 110), 
				new Color(130, 130, 60)
			}, 
			new Shader("defVertex.glsl", "defFragment.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() * 0.5 % (2 * PI)) * 6;
				float z = (float)sin(glfwGetTime() * 0.5 % (2 * PI)) * 6;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(0.8f);
			})),
			
			//earth
			new Model("res/sphere.obj", new Color[]
			{
				Color.blue, 
				Color.blue,
				Color.green, 
				Color.white
			}, 
			new Shader("defVertex.glsl", "defFragment.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() * 0.7 % (2 * PI)) * 9;
				float z = (float)sin(glfwGetTime() * 0.7 % (2 * PI)) * 9;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(0.8f);
			})),
			
			//mars
			new Model("res/sphere.obj", new Color[]
			{
				Color.red, 
				new Color(135, 60, 60), 
				new Color(200, 40, 40)
			}, 
			new Shader("defVertex.glsl", "defFragment.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() * 0.3 % (2 * PI)) * 11;
				float z = (float)sin(glfwGetTime() * 0.3 % (2 * PI)) * 11;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(0.6f);
			})),
		};
		w.add(m);
		for(int i = 0; i < 100; i++) //astroid belt
		{
			float speed = r.nextFloat() + 0.1f, orbit = r.nextInt(3) + 13, y = r.nextFloat() + dist;
			w.add(new Model("res/sphere.obj", new Color[]
			{
				new Color(50, 50, 50), 
				new Color(150, 150, 150), 
				new Color(150, 150, 150)
			}, 
			new Shader("defVertex.glsl", "defFragment.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() * speed % (2 * PI)) * orbit;
				float z = (float)sin(glfwGetTime() * speed % (2 * PI)) * orbit;
				return new Matrix4f().translate(new Vector3f(x, y, z)).scale(0.1f);
			})));
		}
		Model[] m2 = new Model[]
		{
			//Jupiter
			new Model("res/sphere.obj", new Color[]
			{
				new Color(150, 66, 40), 
				new Color(150, 102, 40), 
				new Color(150, 66, 40)
			}, 
			new Shader("defVertex.glsl", "defFragment.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() * 0.4 % (2 * PI)) * 22;
				float z = (float)sin(glfwGetTime() * 0.4 % (2 * PI)) * 22;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(2);
			})),
			
			//Saturn
			new Model("res/sphere.obj", new Color[]
			{
				new Color(240, 160, 60), 
				new Color(215, 175, 122), 
				new Color(165, 132, 89)
			}, 
			new Shader("defVertex.glsl", "defFragment.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() * 0.3 % (2 * PI)) * 26;
				float z = (float)sin(glfwGetTime() * 0.3 % (2 * PI)) * 26;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(2);
			})),
			
			//rings
			new Model("circle.obj", new Color[]
			{
				new Color(240, 160, 60), 
				new Color(132, 114, 72), 
				new Color(165, 132, 89),
				new Color(132, 114, 72)
			}, 
			new Shader("defVertex.glsl", "defFragment.glsl"), genUniforms(()->
			{
				float x = (float)cos(glfwGetTime() * 0.3 % (2 * PI)) * 26;
				float z = (float)sin(glfwGetTime() * 0.3 % (2 * PI)) * 26;
				return new Matrix4f().translate(new Vector3f(x, dist, z)).scale(150).rotate(90f, 1, 0, 0);
			})),
		};
		w.add(m2);
		Renderer.loop();
	}
	
	public static void main(String[] args)
	{
		new PiDayDemo().run();
	}
}