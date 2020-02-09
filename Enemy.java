
import org.joml.Vector3f;

public abstract class Enemy extends Creature
{
	protected boolean dead;
	protected Entity character;
	protected float height = 0.5f;
	
	public abstract void AI();
	
	public Enemy(Vector3f pos, Entity charater_, Weapon w, Hitbox h, int health, int speed)
	{
		super(pos, w, h, health, speed);
		character = charater_;
		World.add(character);
		dead = false;
		Renderer.addAction(()->
		{
			hitbox.setPos(new Vector3f(position.x, position.y + height, position.z));
			weapon.setPos(new Vector3f(position.x, position.y + height, position.z));
			character.setPos(new Vector3f(position.x, position.y + height, position.z));
			if(!dead)
			{
				AI();
			}
		});
	}
	
	public void die()
	{
		if(!dead)
		{
			dead = true;
			hitbox.setPos(new Vector3f(-10, -10, -10));
			World.getPlayer().incKills();
			new Thread(()->
			{
				while(character.getSize() >= 0)
				{
					character.setSize(character.getSize() - 0.1f);
					Renderer.delayUtil(100);
				}
				World.remove(weapon);
				World.remove(character);
			}).start();
		}
	}
}