
import org.joml.Vector3f;
import org.joml.Matrix4f;

public class Entity extends GObject
{
	protected Vector3f position;
	protected Vector3f rotation;
	protected Vector3f velocity;
	
	@Override
	protected Matrix4f trans()
	{
		return new Matrix4f()
			.translate(position)
			.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
			.rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
			.rotate((float)Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
	}
	
	public Entity(String model, Vector3f pos, Vector3f rot, Vector3f vel)
	{
		super(model);
		position = pos;
		rotation = rot;
		velocity = vel;
	}
	
	public Entity(String model)
	{
		this(model, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
	}
	
	public Vector3f getPos()
	{
		return position;
	}
	
	public Vector3f getRot()
	{
		return rotation;
	}
	
	public Vector3f getVel()
	{
		return velocity;
	}
}