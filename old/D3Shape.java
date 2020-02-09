import org.lwjgl.opengl.*;
import org.joml.Vector3f;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;


public abstract class D3Shape
{
	protected Model mod;
	protected Vector3f pos;

	public abstract double getVolume();
	public abstract Matrix4f transform();
	
	public D3Shape(VAO points, Vector3f pos)
	{
		this.pos = pos;
		mod = new Model(points, new Shader("vertex.glsl", "frag.glsl"), (id, args) ->
		{
			float[] f = new float[16];
			transform().translate(pos).get(f);
			glUniformMatrix4fv(glGetUniformLocation(id, "model"), false, f);
			glUniformMatrix4fv(glGetUniformLocation(id, "view"), true, Camera.view());
			glUniformMatrix4fv(glGetUniformLocation(id, "projection"), true, Camera.projection());
		});
	}
	
	public void move(Vector3f v)
	{
		pos = v;
	}
	
	public void move(float x, float y, float z)
	{
		pos = new Vector3f(x, y, z);
	}
	
	public void render()
	{
		mod.render();
	}
	
	public void delete()
	{
		mod.delete();
	}
}