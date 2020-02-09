
import org.joml.Vector3f;

public class Boomer extends Enemy
{		
	public Boomer(Texture text, Vector3f pos)
	{
		super(pos, new TileEntity(text, new Vector3f()), new Shotgun(), new Hitbox(1, 1, 1, new Vector3f()), 10, 2);
		applyGravity();
		applyWall();
	}
	
	public void AI()
	{
		if(new Vector3f(World.getPlayer().getPos()).distance(getPos()) < 10)
		{
			if(new Vector3f(World.getPlayer().getPos()).distance(getPos()) > 3)
			{
				position.add(new Vector3f(World.getPlayer().getPos()).sub(getPos()).normalize().mul(0.01f));
			}
			weapon.setDir(new Vector3f(World.getPlayer().getPos()).sub(weapon.getPos()).add(0, 0.5f, 0));
			weapon.action(0);
		}
	}
}