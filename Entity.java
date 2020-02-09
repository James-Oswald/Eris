
import org.joml.Vector3f;
import org.joml.Matrix4f;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class Entity implements RDCol
{
	protected volatile Vector3f position, rotation, direction, rotationalDirection;
	protected volatile float size, velocity, rotationalVelocity;
	protected volatile Model model;
	protected volatile Hitbox colbox;
	protected volatile Level level = World.getLevel();
	
	protected volatile boolean grounded = true;
	private Runnable gravity = ()->
	{
		try
		{
			boolean anyOn = true;
			for(Vector3f p : colbox.getCorners())
			{
				anyOn = anyOn && (level.floorAt((int)Math.floor(p.x), (int)Math.floor(p.z)) < position.y && grounded);
			}
			if(anyOn)
			{
				grounded = false;
				new Thread(()->
				{
					double startTime = glfwGetTime();
					float startPos = position.y;
					boolean anyOn2 = false;
					while(!grounded)
					{
						for(Vector3f p : colbox.getCorners())
						{
							if(level.floorAt((int)Math.floor(p.x), (int)Math.floor(p.z)) > position.y)
							{
								anyOn2 = true;
								break;
							}
						}
						if(anyOn2)
						{
							grounded = true;
						}
						else
						{
							position.y = (float)(startPos - (3 * Math.pow(glfwGetTime() - startTime, 2)));
						}
						//correctWall();
					}
					if(level.floorAt((int)position.x, (int)position.z) > position.y)
					position.y = level.floorAt((int)position.x, (int)position.z);
				}).start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	};
	
	protected volatile Vector3f lastPos;
	protected volatile boolean inWall = false;
	public boolean inWall()
	{
		//boolean anyIn = false;
		for(Vector3f p : colbox.getCorners())
		{
			if(level.floorAt((int)Math.floor(p.x), (int)Math.floor(p.z)) > position.y + 0.1 || level.floorAt((int)Math.floor(p.x), (int)Math.floor(p.z)) == -1)
			{
				return true;
			}
		}
		return false;
	}
	
	public void correctWall()
	{
		if(inWall())
		{
			position.x = lastPos.x;
			position.y = position.y;
			position.z = lastPos.z;
		}
		else
		{
			lastPos = new Vector3f(position);
		}
	}
	
	private Runnable wallBlock = ()->
	{
		correctWall();
	};
	
	public void applyGravity()
	{
		Renderer.addAction(gravity);
	}
	
	public void applyWall()
	{
		Renderer.addAction(wallBlock);
	}
	
	public Entity()
	{
		this(new Model());
	}
	
	public Entity(Model model_)
	{
		this(model_, new Vector3f());
	}
	public Entity(Model model_, Vector3f position_)
	{
		this(model_, position_, new Vector3f(), 1);
	}
	
	public Entity(Model model_, Vector3f position_, Vector3f rotation_, float size_)
	{
		this(model_, position_, new Vector3f(), rotation_, new Vector3f(), size_, 0, 0);
	}
	
	public Entity(Model model_, Vector3f position_, Vector3f direction_, Vector3f rotation_, Vector3f rotationalDirection_, float size_, float velocity_, float rotationalVelocity_)
	{
		model = model_;
		position = position_;
		direction = direction_;
		rotation = rotation_;
		rotationalDirection = rotationalDirection_;
		size = size_;
		velocity = velocity_;
		rotationalVelocity = rotationalVelocity_;
		colbox = new Hitbox(0, 0, 0, position);
		lastPos = new Vector3f(position);
	}
	
	public void setPos(float x, float y, float z)
	{
		position = new Vector3f(x, y, z);
	}
	
	public void setPos(Vector3f p)
	{
		position = p;
	}
	
	public Vector3f getPos()
	{
		return position;
	}
	
	public void setDir(Vector3f d)
	{
		direction = d;
	}
	
	public Vector3f getDir()
	{
		return direction;
	}
	
	public void setVel(float v)
	{
		velocity = v;
	}
	
	public float getVel()
	{
		return velocity;
	}
	
	public void setRot(Vector3f r)
	{
		rotation = r;
	}
	
	public Vector3f getRot()
	{
		return rotation;
	}
	
	public void setSize(float s)
	{
		size = s;
	}
	
	public float getSize()
	{
		return size;
	}
	
	public boolean isGrounded()
	{
		return grounded;
	}
	
	protected void finalizeRender()
	{
		model.addTrans(new Matrix4f().translate(position));
		model.addTrans(new Matrix4f().scale(size));
		model.addTrans(new Matrix4f().rotateXYZ(rotation));
	}
	
	public void render()
	{
		position.add(new Vector3f(direction).mul(velocity * Renderer.getDeltaTime()));
		rotation.add(new Vector3f(rotationalDirection).mul(rotationalVelocity * Renderer.getDeltaTime()));
		if(model != null)
		{
			model.getTrans().clear();
			finalizeRender();
			model.render();
		}
	}
	
	public void delete()
	{
		if (model != null)model.delete();
	}
}