
import org.joml.Vector3f;

public class DevEnemy extends Enemy
{	
	//private static Texture melvin = new Texture("res/melvin.png");
	
	public DevEnemy(String png, Vector3f pos)
	{
		super(pos, new TileEntity(new Texture(png), new Vector3f()), new DevGun(), new Hitbox(1, 1, 1, new Vector3f()), 10, 2);
	}
	
	public void AI()
	{
		position.add(new Vector3f(World.getPlayer().getPos()).sub(getPos()).normalize().mul(0.01f));
		weapon.setDir(new Vector3f(World.getPlayer().getPos()).sub(weapon.getPos()).add(0, 0.5f, 0));
		weapon.action(0);
	}
}