import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.util.Random;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public strictfp class VAO
{
	private ArrayList<Integer> buffers;
	private int vao, ebo;
	private float[][] props;
	private int[] sizes, indis;
	
	public static final int POSITIONS = 3, COLORS = 4, TEXTCOORDS = 2;
	
	public VAO(String file)
	{
		this(file, null);
	}
	
	public VAO(String file, Color[] col)
	{
		this(fromFile(file, col));
	}
	
	public VAO(VAO v)
	{
		this(v.getIndis(), v.getSizes(), v.getProps());
	}
	
	public VAO(int[] sizes, float[]... prop) 
	{
		this(null, sizes, prop);
	}
	
	public VAO(int[] indis, int[] sizes, float[]... props)
	{
		if(sizes.length != props.length) 
		{
			System.out.println("EXCEPTION in Model.<init>: sizes len != props len");
		}
		this.props = props;
		this.sizes = sizes;
		int vbo;
		FloatBuffer fb;
		buffers = new ArrayList<Integer>();
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		if(indis != null)
		{
			this.indis = indis;
			ebo = glGenBuffers();
			buffers.add(ebo);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
			IntBuffer ib = (IntBuffer)BufferUtils.createIntBuffer(indis.length).put(indis).flip();
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, ib, GL_STATIC_DRAW);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
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
	
	public void delete()
	{
		glDeleteVertexArrays(vao);
		for(int i = 0; i < buffers.size(); i++)
		{
			glDeleteBuffers(buffers.get(i));
		}
	}
	
	public void enableAttributes()
	{
		for(int i = 0; i < props.length; i++)
		{
			glEnableVertexAttribArray(i);
		}
	}
	
	public void disableAttributes()
	{
		for(int i = 0; i < props.length; i++)
		{
			glDisableVertexAttribArray(i);
		}
	}
	
	public int[] getIndis()
	{
		return indis;
	}
	
	public int[] getSizes()
	{
		return sizes;
	}
	
	public float[][] getProps()
	{
		return props;
	}
	
	public int getEbo()
	{
		return ebo;
	}
	
	public int getAttributeSize(int index)
	{
		return props[index].length;
	}
	
	public int getIndiciesCount()
	{
		return indis.length;
	}
	
	public int getNumAttributes()
	{
		return props.length;
	}
	
	public int id()
	{
		return vao;
	}
	
	private static String ln = "";
	private static strictfp VAO fromFile(String file, Color[] col)
	{
		ArrayList<Integer> indis = new ArrayList<Integer>();
		ArrayList<Float> verts = new ArrayList<Float>();
		try(Stream<String> lines = Files.lines(Paths.get(file), Charset.defaultCharset())) 
		{
			lines.forEach(line -> 
			{
				ln = line;
				String temp;
				if(!line.equals("") && line.charAt(0) == 'v')
				{
					line = line.substring(2);
					temp = "";
					for(int i = 0; i < line.length(); i++)
					{
						if(line.charAt(i) == ' ')
						{
							verts.add(Float.parseFloat(temp));
							temp = "";
						}
						else
						{
							temp += line.charAt(i);
						}
					}
					verts.add(Float.parseFloat(temp));
				}
				else if(!line.equals("") && line.charAt(0) == 'f')
				{
					line = line.substring(2);
					temp = "";
					line += " ";
					for(int i = 0; i < line.length(); i++)
					{
						if(line.charAt(i) == '/')
						{
							i = line.indexOf(" ", i);
						}
						if(line.charAt(i) == ' ')
						{
							indis.add(Integer.parseInt(temp) - 1);
							temp = "";
						}
						else
						{
							temp += line.charAt(i);
						}
					}
				}
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(ln);
		}
		int[] pIndies = new int[indis.size()];
		float[] pVerts = new float[verts.size()], colors = new float[(verts.size() / 3) * 4];
		Random r = new Random();
		Color c;
		for(int i = 0; i < (verts.size() / 3) * 4; i += 4)
		{
			if(col != null)
			{
				c = col[r.nextInt(col.length)];
				colors[i] = (float)c.getRed() / 255;
				colors[i + 1] = (float)c.getGreen() / 255;
				colors[i + 2] = (float)c.getBlue() / 255;
			}
			else 
			{
				colors[i] = r.nextFloat();
				colors[i + 1] = r.nextFloat();
				colors[i + 2] = r.nextFloat();
			}
			colors[i + 3] = 1;
		}
		for(int i = 0; i < verts.size(); i++)
		{
			pVerts[i] = verts.get(i);
		}
		for(int i = 0; i < indis.size(); i++)
		{
			pIndies[i] = indis.get(i);
		}
		return new VAO(pIndies, new int[]{VAO.POSITIONS, VAO.COLORS}, pVerts, colors);
	}
	
	private static final int[] indis_ = 
	{
		0, 1, 3,
		0, 2, 3,
	};
	private static final float[] verts_ =
	{
		0, 0, 0,
		1, 0, 0,
		0, 0, 1,
		1, 0, 1,
	};
	private static final float[] verts2_ =
	{
		-0.5f, 0, -0.5f,
		0.5f, 0, -0.5f,
		-0.5f, 0, 0.5f,
		0.5f, 0, 0.5f,
	};
	private static final float[] colors_ =
	{
		0, 1, 0, 1,
		0, 0, 1, 1,
		1, 0, 0, 1,
		0, 1, 0, 1,
	};
	private static final float[] text_ =
	{
		0, 0,
		0, 1,
		1, 0,
		1, 1,
	};
	
	public static final VAO tile = new VAO(indis_, new int[]{VAO.POSITIONS, VAO.COLORS, VAO.TEXTCOORDS}, verts_, colors_, text_);
	public static final VAO cTile = new VAO(indis_, new int[]{VAO.POSITIONS, VAO.COLORS, VAO.TEXTCOORDS}, verts2_, colors_, text_);
}