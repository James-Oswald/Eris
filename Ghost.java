

import org.joml.Vector3f;

public class Ghost extends Enemy
{		
	public Ghost(Texture text, Vector3f pos)
	{
		super(pos, new TileEntity(text, new Vector3f()), new Pistol(), new Hitbox(1, 1, 1, new Vector3f()), 10, 2);
	}
	
	public void AI()
	{
		if(new Vector3f(World.getPlayer().getPos()).distance(getPos()) < 15)
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