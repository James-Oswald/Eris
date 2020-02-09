
import org.joml.Vector3f;

public class Turret extends Enemy 
{
	public Turret(Texture texture, Vector3f pos)
	{
		super(pos, new TileEntity(texture, new Vector3f()), new PlasmaGun(), new Hitbox(1, 1, 1, new Vector3f()), 5, 2);
	}
	
	public void AI()
	{
		if(new Vector3f(World.getPlayer().getPos()).distance(getPos()) < 10)
		{
			weapon.setDir(new Vector3f(World.getPlayer().getPos()).sub(weapon.getPos()).add(0, 0.5f, 0));
			weapon.action(0);
		}
	}
}