import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;

public class Demo
{	
	private Model m;
	
	public void init() throws Exception
	{
		new Window("Meme", 800, 800, "res/lex.png");
		GL.createCapabilities();
		glViewport(0,0, Window.width(), Window.height());
	}
	
	public void load() throws Exception
	{
		float[] vertices = 
		{            
			0.5f,  0.5f, 0.0f,   
			0.5f, -0.5f, 0.0f,   
			-0.5f, -0.5f, 0.0f,   
			-0.5f,  0.5f, 0.0f   
        };
		
		float[] colors = 
		{
			1, 0, 0, 1,
			0, 1, 0, 1,
			1, 0, 1, 1,
			1, 1, 0, 1
		};
		int[] indices = 
		{
            0, 1, 3, 
            1, 2, 3   
        };
		float[] text =
		{
			0, 0,
			0, 1,
			1, 1,
			1, 0
		};
		new Camera(0, 0, 5);
		Camera.setMode(Camera.FPS);
		Window.curEnabled(false);
		//m1 = new Model(new VAO(indices, new int[]{VAO.POSITIONS, VAO.COLORS, VAO.TEXTCOORDS}, vertices, colors, text), new Shader("vertex.glsl", "frag.glsl"),
		m = new Model(Model.createVAOFromFile("sphere.obj"), new Shader("vertex.glsl", "frag.glsl"),
		(id) -> 
		{
			Matrix4f mat = new Matrix4f();
			float[] f = new float[16];
			mat.rotate((float)Math.toRadians(glfwGetTime() * 50), 0, 1, 0).get(f);
			//mat.identity().get(f);
			glUniformMatrix4fv(glGetUniformLocation(id, "model"), true, f);
			glUniformMatrix4fv(glGetUniformLocation(id, "view"), true, Camera.view());
			glUniformMatrix4fv(glGetUniformLocation(id, "projection"), true, Camera.projection());
		});//, new Texture("hope.png"));
		//m2 = new Model(new VAO(indices, new int[]{VAO.POSITIONS, VAO.COLORS, VAO.TEXTCOORDS}, vertices, colors, text), new Shader("vertex.glsl", "frag.glsl"),
		/*//m = new Model(Model.createVAOFromFile("teapot.obj"), new Shader("vertex.glsl", "frag.glsl"),
		(id) -> 
		{
			Matrix4f mat = new Matrix4f();
			float[] f = new float[16];
			mat.rotate((float)Math.toRadians(glfwGetTime() * 50), 1, 0, 0).get(f);
			//mat.identity().get(f);
			glUniformMatrix4fv(glGetUniformLocation(id, "model"), false, f);
			glUniformMatrix4fv(glGetUniformLocation(id, "view"), true, Camera.view());
			glUniformMatrix4fv(glGetUniformLocation(id, "projection"), true, Camera.projection());
		}, new Texture("hope.png"));*/
		KeyInput.addAction((key, action) ->
		{
			if(key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
			{
				glfwSetWindowShouldClose(Window.id(), true);
			}
			if(key == GLFW_KEY_W && action == GLFW_PRESS)
			{
				Camera.move(0, 0, 1);
			}
			if(key == GLFW_KEY_S && action == GLFW_PRESS)
			{
				Camera.move(0, 0, -1);
			}
			if(key == GLFW_KEY_A && action == GLFW_PRESS)
			{
				Camera.move(1, 0, 0);
			}
			if(key == GLFW_KEY_D && action == GLFW_PRESS)
			{
				Camera.move(-1, 0, 0);
			}
		});
		glEnable(GL_DEPTH_TEST);
		glClearColor(0.156f, 0.431f, 0.466f, 1.0f);
	}
	
	public void loop() throws Exception
	{
		while(!Window.shouldClose())
		{
			glfwPollEvents();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			m.render();
			//m2.render();
			Window.swapBuffers();
		}
	}
	
	public void exit()
	{
		m.delete();
		//m2.delete();
		Window.delete();
		glfwTerminate();
	}
	
	public static void main(String[] args)
	{
		try
		{
			Demo d = new Demo();
			d.init();
			DEBUG("Initialazation Compleate");
			d.load();
			DEBUG("Model Loaded");
			d.loop();
			DEBUG("Loop ended");
			d.exit();
			DEBUG("Program Exiting");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage()); 
			e.printStackTrace();
		}
	}
	
	public static void DEBUG(String s)
	{
		System.out.println(s);
	}
	
	public static void matrixOut(Matrix4f m)
	{
		float[] f = new float[16];
		m.get(f);
		for(int i = 0; i < f.length; i += 4)
		{
			System.out.println(f[i] + " " + f[i + 1] + " " + f[i + 2] + " " + f[i + 3]);
		}
	}
	
	public static void matrixOut(float[] f)
	{
		for(int i = 0; i < f.length; i += 4)
		{
			System.out.println(f[i] + " " + f[i + 1] + " " + f[i + 2] + " " + f[i + 3] + "\n");
		}
	}
	
	public static void vectorOut(Vector3f v)
	{
		System.out.println(v.x() + " " + v.y() + " " + v.z() + "\n");
	}
}