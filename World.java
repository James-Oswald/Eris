
import java.util.ArrayList;
import java.util.Arrays;
public class World
{
	private static ArrayList<RDCol> mods = new ArrayList<RDCol>();
	private static Level level;
	private static Player player;
	
	public static void render()
	{
		//mods.forEach(RDCol::render);
		for(int i = 0; i < mods.size(); i++)
		{
			mods.get(i).render();
		}
	}
	
	public static void add(RDCol m)
	{
		mods.add(m);
	}
	
	public static void remove(RDCol m)
	{
		mods.remove(m);
	}
	
	public static void add(RDCol[] ms)
	{
		for(RDCol m : ms)
		{
			mods.add(m);
		}
	}
	
	public static void setPlayer(Player p)
	{
		player = p;
		add(p);
	}
	
	public static void setLevel(Level l)
	{
		level = l;
		ArrayList<Model> models = l.getModels();
		add(models.toArray(new Model[models.size()]));
	}
	
	public static Level getLevel()
	{
		return level;
	}
	
	public static Player getPlayer()
	{
		return player;
	}
	
	public static void clear()
	{
		mods.forEach(RDCol::delete);
		mods.clear();
	}
	
	public static void delete()
	{
		mods.forEach(RDCol::delete);
		mods.clear();
	}
}
