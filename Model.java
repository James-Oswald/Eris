
import java.awt.Color;
import java.util.ArrayList;
import org.joml.Vector3f;
import org.joml.Matrix4f;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;

public class Model implements RDCol 
{
	private Shader shader;
	private Texture texture;
	private VAO vao;
	private ArrayList<Matrix4f> trans = new ArrayList<Matrix4f>();
	
	public static final Shader defShader = new Shader("shaders/defVertex.glsl", "shaders/defFragment.glsl");
	public static final Shader textShader = new Shader("shaders/textVertex.glsl", "shaders/textFragment.glsl");
	private static final Texture defTexture = null;
	private Shader.Uniforms uniforms = (id) ->
	{
		float[] fa = new float[16];
		Matrix4f temp = new Matrix4f().identity();
		for(Matrix4f m : trans)
		{
			temp.mul(m);
		}
		temp.get(fa);
		glUniformMatrix4fv(glGetUniformLocation(id, "model"), false, fa);
		glUniformMatrix4fv(glGetUniformLocation(id, "view"), true, Camera.view());
		glUniformMatrix4fv(glGetUniformLocation(id, "projection"), true, Camera.projection());
	};
	
	public Model()
	{
		this(new VAO("res/sphere.obj"), defShader);
	}
	
	public Model(String file)
	{
		this(new VAO(file), defShader);
	}
	
	public Model(String file, Color... colors)
	{
		this(new VAO(file, colors), defShader);
	}
	
	public Model(VAO v)
	{
		this(v, defShader, null);
	}
	
	public Model(VAO v, Texture t)
	{
		this(v, defShader, t);
	}
	
	public Model(VAO v, Shader s)
	{
		this(v, s, null);
	}
	
	public Model(VAO v, Shader s, Texture t)
	{
		shader = s;
		texture = t;
		vao = v;
	}
	
	public void addTrans(Matrix4f t)
	{
		trans.add(t);
	}
	
	public ArrayList<Matrix4f> getTrans()
	{
		return trans;
	}
	
	public Shader getShader()
	{
		return shader;
	}
	
	public void setShader(Shader s)
	{
		shader = s;
	}
	
	public void render()
	{
		if(vao != null)
		{
			shader.bind();
			if(texture != null) glBindTexture(GL_TEXTURE_2D, texture.id());
			glBindVertexArray(vao.id());
			vao.enableAttributes();
			shader.bindUniforms(uniforms);
			shader.setUniforms();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vao.getEbo());
			glDrawElements(GL_TRIANGLES, vao.getIndiciesCount(), GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			vao.disableAttributes();
			if(texture != null) glBindTexture(GL_TEXTURE_2D, 0); 
			glBindVertexArray(0);
			shader.unbind();
		}
	}
	
	public void delete()
	{
		vao.delete();
		shader.delete();
		if(texture != null) texture.delete();
	}
}
