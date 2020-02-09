
public class Game
{
	public Game(String level)
	{
		Renderer.init();
		World.setLevel(new Level(level));
		World.getLevel().setSpecials();
		World.setPlayer(new Player(World.getLevel().getPSP()));
		Renderer.loop();
		Renderer.end();
	}
	
	public static void main(String[] args)
	{
		new Game("solar sanctum");
	}
}
