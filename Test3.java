import org.joml.Vector3f;
import java.awt.Color;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class Test3
{
	public Test3()
	{
		Renderer.init();
		World.setLevel(new Level("test"));
		World.setPlayer(new Player());
		Vector3f op = new Vector3f(5, 10, 5);
		World.add(new DevEnemy("res/lazar.png", new Vector3f(5, 5, 5)));
		//World.add(new DevEnemy(new Vector3f(5, 5, 6)));
		World.add(new DevEnemy("res/melvin.png", new Vector3f(5, 8, 8)));
		//new MP3("res/music.mp3").play();
		/*Weapon w = new DevGun();
		w.setPos(5, 2, 5);
		//System.out.println(w.getDir());
		World.add(w);
		Renderer.addAction(()->
		{
			//if(Math.sin(glfwGetTime()) > 0.8)
			{
				w.setDir(new Vector3f(World.getPlayer().getPos()).sub(w.getPos()).add(0, 0.5f, 0));
				w.action(1);
			}
		});*/
		Renderer.loop();
		Renderer.end();
	}
	
	public static void main(String[] args)
	{
		new Test3();
		//new Test3();
	}
}