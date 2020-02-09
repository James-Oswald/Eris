
import java.util.ArrayList;
import org.joml.Vector3f;
import org.joml.Matrix4f;

public class PolyEntity extends Entity
{
	protected ArrayList<Entity> parts;
	
	public PolyEntity()
	{
		super(null, new Vector3f());
		parts = new ArrayList<Entity>();
	}
	
	public PolyEntity(ArrayList<Entity> parts_, Vector3f position_)
	{
		super(null, position_);
		parts = parts_;
	}
	
	public PolyEntity(ArrayList<Entity> parts_, Vector3f position_, Vector3f rotation_, float size_)
	{
		super(null, position_, rotation_, size_);
		parts = parts_;
	}
	
	public PolyEntity(ArrayList<Entity> parts_, Vector3f position_, Vector3f direction_, Vector3f rotation_, Vector3f rotationalDirection_, float size_, float velocity_, float rotationalVelocity_)
	{
		super(null, position_, direction_, rotation_, rotationalDirection_, size_, velocity_, rotationalVelocity_);
		parts = parts_;
	}
	
	public void render()
	{
		super.render();
		Vector3f tempPos;
		float tempSize;
		for(Entity e : parts)
		{
			tempSize = e.getSize();
			tempPos = new Vector3f(e.getPos());
			e.setSize(super.size * tempSize);
			e.getPos().add(super.position);
			e.render();
			e.setPos(new Vector3f(tempPos));
			e.setSize(tempSize);
		}
	}

	public void add(Entity e)
	{
		parts.add(e);
	}
}