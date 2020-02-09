
import org.joml.Vector3f;
import java.util.ArrayList;

public abstract class Creature extends PolyEntity
{
	protected static ArrayList<Creature> creatures = new ArrayList<Creature>();
	
	public abstract void die();
	
	public static ArrayList<Creature> getCreatures()
	{
		return creatures;
	}
	
	protected Weapon weapon;
	protected int health, speed;
	protected Hitbox hitbox;
	
	public Creature(Vector3f pos, Weapon weapon_, Hitbox hitbox_, int health_, int speed_)
	{
		super(new ArrayList<Entity>(), pos);
		speed = speed_;
		health = health_;
		hitbox = hitbox_;
		weapon = weapon_;
		if(weapon != null)
		{
			setWeapon(weapon);
		}
		creatures.add(this);
		Renderer.addAction(()->
		{
			if(health <= 0 || position.y == 0)
			{
				die();
			}
		});
	}
	
	public void damage(int dmg)
	{
		health -= dmg;
	}
	
	public void heal(int heal)
	{
		health += heal;
	}
	
	public void setWeapon(Weapon w)
	{
		if(weapon != null)
		{
			World.remove(weapon);
		}
		weapon = w;
		World.add(weapon);
		weapon.setOwner(this);
	}
	
	public Hitbox getHitbox()
	{
		return hitbox;
	}
}
