
import java.util.ArrayList;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.*;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Model
{
	private ArrayList<Integer> buffers;
	private int vao;
	private float[][] props;
	private int[] sizes;
	
	public static final int POSITIONS = 3, COLORS = 4, TEXTCORDS = 2;
	
	public Model(int[] indis, int[] sizes, float[]... props) throws Exception
	{
		if(sizes.length != props.length) 
		{
			throw new Exception("EXCEPTION in Model.<init>: sizes len != props len");
		}
		this.props = props;
		this.sizes = sizes;
		int vbo, ebo;
		FloatBuffer fb;
		buffers = new ArrayList<Integer>();
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		if(indis != null)
		{
			ebo = glGenBuffers();
			buffers.add(ebo);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
			IntBuffer ib = (IntBuffer)BufferUtils.createIntBuffer(indis.length).put(indis).flip();
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, ib, GL_STATIC_DRAW);
		}
		for(int i = 0; i < props.length; i++)
		{
			vbo = glGenBuffers();
			buffers.add(vbo);
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			fb = (FloatBuffer)BufferUtils.createFloatBuffer(props[i].length).put(props[i]).flip();
			glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
			glVertexAttribPointer(i, sizes[i], GL_FLOAT, false, 0, 0);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}
		glBindVertexArray(0);
	}
	
	public Model(int[] sizes, float[]... prop) throws Exception
	{
		this(null, sizes, prop);
	}
	
	public void delete()
	{
		glDeleteVertexArrays(vao);
		for(int i = 0; i < buffers.size(); i++)
		{
			glDeleteBuffers(buffers.get(i));
		}
	}
	
	public void enableAll()
	{
		for(int i = 0; i < props.length; i++)
		{
			glEnableVertexAttribArray(i);
		}
	}
	
	public void disableAll()
	{
		for(int i = 0; i < props.length; i++)
		{
			glDisableVertexAttribArray(i);
		}
	}
	
	public int vao()
	{
		return vao;
	}
	
	public int propcount(int index)
	{
		return props[index].length;
	}
	
	public int numArgs()
	{
		return props.length;
	}
}