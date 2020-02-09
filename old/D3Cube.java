
import org.lwjgl.opengl.*;
import org.joml.Vector3f;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;

import java.util.Random;

public class D3Cube extends D3Shape implements Comparable <D3Cube>
{
	private static final Random r = new Random();
	
	private static final float[] CC = 
	{
		0.5f,  0.5f, 0.5f,   
		0.5f, -0.5f, 0.5f,   
		-0.5f, -0.5f, 0.5f,   
		-0.5f,  0.5f, 0.5f, 
		0.5f,  0.5f, -0.5f,   
		0.5f, -0.5f, -0.5f,   
		-0.5f, -0.5f, -0.5f,   
		-0.5f,  0.5f, -0.5f 
	};
	
	private static final int[] CI = 
	{
		0, 1, 3, 
        1, 2, 3,
		4, 5, 6,
		4, 6, 7,
		4, 0, 1,
		4, 5, 1,
		7, 4, 0,
		7, 3, 0,
		7, 6, 2,
		7, 3, 2,
		6, 2, 1,
		6, 5, 1
	};
	
	private static float[] adjustCC(int s)
	{
		float[] rv = new float[CC.length];
		for(int i = 0; i < CC.length; i++)
		{
			rv[i] = CC[i] * s;
		}
		return rv;
	}
	
	private static float[] randomizeColors()
	{
		float[] rv = new float[(CC.length / 3) * 4];
		for(int i = 0; i < (CC.length / 3) * 4; i++)
		{
			rv[i] = (i + 1) % 4 == 0 ? 1.0f : r.nextFloat();
		}
		return rv;
	}
	
	protected int size;
	
	public D3Cube(Vector3f pos, int size)
	{
		super(new VAO(CI, new int[]{VAO.POSITIONS, VAO.COLORS}, adjustCC(size), randomizeColors()), pos);
		this.size = size;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize(int i)
	{
		size = i;
	}
	
	public double getVolume()
	{
		return Math.pow(size, 3);
	}
	
	public Matrix4f transform()
	{
		return new Matrix4f().identity();
	}
	
	public int compareTo(D3Cube c)
	{
		return size == c.getSize() ? 0 : size > c.getVolume() ? 1 : -1;
	}
	
	public boolean equals(D3Cube c)
	{
		return size == c.getSize();
	}
	
	public String toString()
	{
		return "\nPosition: " + pos.toString() + "\nSize: " + size + "\nVolume: " + getVolume();
	}
	
}