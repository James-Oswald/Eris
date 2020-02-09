
import org.joml.Vector3f;
import org.joml.Matrix4f;
import java.util.function.Supplier;

import static org.lwjgl.opengl.GL20.*;

public class GObject implements RDCol 
{
	private Model model;
	private Supplier<Matrix4f> extra = () -> {return new Matrix4f().identity();};
	protected Shader.Uniforms uniform = Model.genUniforms(() -> {return trans().mul(extra.get());});
	
	public GObject(String file)
	{
		model = new Model(file, uniform);
	}
	
	public GObject(String file, Supplier<Matrix4f> extra)
	{
		this(file);
		this.extra = extra;
	}
	
	protected Matrix4f trans()
	{
		return new Matrix4f().identity();
	}
	
	public void setExtra(Supplier<Matrix4f> extra)
	{
		this.extra = extra;
	}
	
	public void render()
	{
		model.render();
	}
	
	public void delete()
	{
		model.delete();
	}
}