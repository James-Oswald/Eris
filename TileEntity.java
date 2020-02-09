
import org.joml.Vector3f;
import org.joml.Matrix4f;

public class TileEntity extends Entity
{	
	public TileEntity(Texture texture, Vector3f position_)
	{
		this(texture, position_, new Vector3f(), 1, 0);
	}
	
	public TileEntity(Texture texture, Vector3f position_, Vector3f direction_, float size_, float velocity_)
	{
		super(new Model(VAO.cTile, Model.textShader, texture), position_, direction_, new Vector3f(), new Vector3f(), size_, velocity_, 0);
		Renderer.addAction(()->
		{
			Vector3f p = World.getPlayer().getPos();
			float xcomp = position.x - p.x, zcomp = position.z - p.z;
			setRot(new Vector3f(0, -(float)(Math.atan(zcomp / xcomp)), -(float)(Math.PI / 2)));
			setPos(position);
		});
	}
}