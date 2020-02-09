
import java.awt.Color;

import org.lwjgl.opengl.*;

import org.joml.Vector3f;
import org.joml.Matrix4f;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class TransformingCube extends D3Cube
{
	private int rotationSpeed, dialationSpeed;
	private Vector3f rotationAxis;
	
	public TransformingCube(Vector3f pos, int size, Vector3f rotationAxis, int rotationSpeed, int dialationSpeed)
	{
		super(pos, size);
		this.rotationAxis = rotationAxis;
		this.rotationSpeed = rotationSpeed;
		this.dialationSpeed = dialationSpeed;
	}
	
	public int getRotationSpeed()
	{
		return rotationSpeed;
	}
	
	public int getDialationSpeed()
	{
		return dialationSpeed;
	}
	
	public Matrix4f transform()
	{
		return new Matrix4f().translate(new Vector3f(0, 0, 0)).rotate((float)Math.toRadians(glfwGetTime() * rotationSpeed), rotationAxis.x(), rotationAxis.y(), rotationAxis.z()).scale(.5f * (float)Math.cos(glfwGetTime()) + 1.5f);
	}
	
	public double getVolume()
	{
		return super.getVolume() * .5f * (float)Math.cos(glfwGetTime()) + 1.5f;
	}
	
	public int compareTo(TransformingCube c)
	{
		return super.compareTo(c) == 0 && rotationSpeed == c.getRotationSpeed() && dialationSpeed == c.getDialationSpeed() ? 0 : super.compareTo(c);
	}
	
	public String toString()
	{
		return super.toString() + "\nRotSpeed: " + rotationSpeed + "\nDiSpeed: " + dialationSpeed;
	}
}