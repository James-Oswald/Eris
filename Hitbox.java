
import org.joml.Vector3f;

public class Hitbox
{
	private Vector3f position;
	private float width, length, height;
	
	public Hitbox(float width, float length, float height, Vector3f position_)
	{
		this.width = width;
		this.height = height;
		this.length = length;
		this.position = position_;
		if(Settings.displayHitBoxes && width > 0)
		{
			Entity e = new Entity(new Model("res/cube.obj"), new Vector3f(), new Vector3f(), width);
			World.add(e);
			Renderer.addAction(()->
			{
				e.setPos(new Vector3f(position.x - 0.5f, position.y - 0.5f, position.z - 0.5f));
			});
		}
	}
	
	public boolean inside(Vector3f v)
	{
		double maxH = position.y + height / 2;
		double minH = position.y + -height / 2;
		double maxW = position.x + width / 2;
		double minW = position.x + -width / 2;
		double maxL = position.z + length / 2;
		double minL = position.z + -length / 2;
		return v.x <= maxW && v.x >= minW && v.y <= maxH && v.y >= minH && v.z <= maxL && v.z >= minL;
	}
	
	public Vector3f[] getCorners()
	{
		Vector3f[] rv = new Vector3f[]
		{
			new Vector3f(position.x + width / 2, position.y + height / 2, position.z + length / 2),
			new Vector3f(position.x + -width / 2, position.y + height / 2, position.z + length / 2),
			new Vector3f(position.x + width / 2, position.y + height / 2, position.z + -length / 2),
			new Vector3f(position.x + -width / 2, position.y + height / 2, position.z + -length / 2),
			new Vector3f(position.x + width / 2, position.y + -height / 2, position.z + length / 2),
			new Vector3f(position.x + -width / 2, position.y + -height / 2, position.z + length / 2),
			new Vector3f(position.x + width / 2, position.y + -height / 2, position.z + -length / 2),
			new Vector3f(position.x + -width / 2, position.y + -height / 2, position.z + -length / 2),
		};
		return rv;
	}
	
	public void setPos(Vector3f p)
	{
		position = p;
	}
	
	public Vector3f getPos()
	{
		return position;
	}
}