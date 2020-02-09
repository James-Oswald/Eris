
import java.awt.Color;
import org.joml.Vector3f;
import java.util.ArrayList;

public abstract class Weapon extends Entity
{	
	protected int dmg;
	protected float offset;
	protected Creature owner;
	protected ArrayList<Entity> cons = new ArrayList<Entity>();
	private static ArrayList<Creature> creatures = Creature.getCreatures();
	
	public abstract void action(int act);
	
	public Weapon(Model model_, int dmg_, float size_, float offset_)
	{
		super(model_, new Vector3f(), new Vector3f(), size_);
		dmg = dmg_;
		offset = offset_;
		Renderer.addAction(()->
		{
			Entity curcon;
			Creature curcre;
			for(int i = 0; i < cons.size(); i++)
			{
				curcon = cons.get(i);
				for(int j = 0; j < creatures.size(); j++)
				{
					curcre = creatures.get(j);
					if(curcre.getHitbox().inside(curcon.getPos()))
					{
						if(owner == null || (owner != null && curcre != owner))
						{
							curcre.damage(dmg);
							cons.remove(curcon);
							World.remove(curcon);
						}
					}
				}
				if(curcon.inWall())
				{
					cons.remove(curcon);
					World.remove(curcon);
				}
			}
		});
	}
		
	public Vector3f changeAcc(Vector3f dir, float magnitude)
	{
		return new Vector3f((float)(magnitude * Math.random() + dir.x), (float)(magnitude * Math.random() + dir.y), (float)(magnitude * Math.random() + dir.z));
	}
	
	public float getOffset()
	{
		return offset;
	}
	
	public void setOwner(Creature owner_)
	{
		owner = owner_;
	}
}