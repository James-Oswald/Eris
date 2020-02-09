
import org.lwjgl.opengl.*;
import org.joml.Vector3f;
import org.joml.Matrix4f;
import java.io.PrintStream;
import java.util.Arrays;


public class InheritanceDemo
{
	public static void main(String[] args)
	{
		Renderer.init();
		D3Shape[] shapes = 
		{
			new D3Cube(new Vector3f(2, 2, 2), 3),
			new TransformingCube(new Vector3f(0, 0, 0), 2, new Vector3f(0, 1, 0), 50, 50),
			new TransformingCube(new Vector3f(-10, -10, -10), 5, new Vector3f(0, 1, 0), 0, 50),
			new D3Cube(new Vector3f(10, -1, 10), 2)
		};
		Arrays.asList(shapes).forEach(System.out::println);
		System.out.println("\n0 and 1 Equal?: " + (((D3Cube)shapes[0]).equals((D3Cube)shapes[1]) ? "yes" : "no"));
		System.out.println("\n0 comp 1?: " + ((D3Cube)shapes[0]).compareTo((D3Cube)shapes[1]));
		shapes[0].move(new Vector3f(3, 3, 3));
		((D3Cube)shapes[0]).setSize(2);
		System.out.println(shapes[0]);
		System.out.println("\n0 and 1 Equal?: " + (((D3Cube)shapes[0]).equals((D3Cube)shapes[1]) ? "yes" : "no"));
		System.out.println("\n0 comp 1?: " + ((D3Cube)shapes[0]).compareTo((D3Cube)shapes[1]));
		new Thread(() -> 
		{
			while(!Window.shouldClose())
			{
				for(int i = 0; i < shapes.length; i++)
				{
					System.out.println("Shape " + i + " Volume: " + shapes[i].getVolume());
				}
				try 
				{
					Thread.sleep(5000);
				} 
				catch(Exception e) 
				{
					e.printStackTrace();
				}
			}
		}).start();
		Arrays.asList(shapes).forEach(World::add);
		Renderer.loop();
	}
}