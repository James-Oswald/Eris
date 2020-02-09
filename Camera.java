import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import org.joml.Vector3f;
import org.joml.Matrix4f;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import java.util.ArrayList;

public class Camera 
{
	//private static Camera cur;
	private static volatile Vector3f pos, right, up, tar, radTar;
	private static volatile Matrix4f view, projection;
	private static volatile float fov, near, far;
	
	private static double yaw = -90.0, pitch = 0.0, lastX = 0, lastY = 0, xoffset, yoffset;
	private static boolean firstMouse = false;
	private static int x, y;
	private static Vector3f tempFront = new Vector3f();
	
	static
	{
		startCamera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), Settings.FOV, 0.04f, 100f);
		init();
	}
	
	private static void startCamera(Vector3f pos_, Vector3f tar_, float fov_, float near_, float far_)
	{
		up = new Vector3f(0, 1, 0);
		pos = pos_;
		tar = tar_;
		fov = fov_;
		near = near_;
		far = far_;
		view = lookAt(pos, tar.add(pos), up);
		projection = perspective((float)Math.toRadians(fov), 1, near, far);
	}
	
	public static void lookAt(Vector3f tar_)
	{
		tar = tar_.normalize();
	}
	
	public static void moveTo(Vector3f pos_)
	{
		pos = pos_;
	}
	
	public static Vector3f getPos()
	{
		return pos;
	}
	
	public static Vector3f getRight()
	{
		return cross(up, tar);
	}
	
	public static Vector3f getFront()
	{
		return tar;
	}
	
	public static void move(float x, float y, float z)
	{
		pos.x += x;
		pos.y += y;
		pos.z += z;
	}
	
	public static void move(Vector3f v)
	{
		pos.add(v);
	}
	
	public static float[] view()
	{
		float[] rv = new float[16];
		view = lookAt(pos, tar.add(pos), up);
		view.get(rv);
		return rv;
	}
	
	public static float[] projection()
	{
		float[] rv = new float[16];
		projection = perspective((float)Math.toRadians(fov), 1, near, far);
		projection.get(rv);
		return rv;
	}
	
	public static Matrix4f viewM()
	{
		return view;
	}
	
	public static Matrix4f projectionM()
	{
		return projection;
	}
	
	public static double getYaw()
	{
		return yaw;
	}
	
	public static double getPitch()
	{
		return pitch;
	}
	
	public static void init()
	{
		MousePosInput.addAction((Integer x_, Integer y_) ->
		{
			x = x_;
			y = y_;
			if(firstMouse)
			{
				lastX = x;
				lastY = y;
				firstMouse = false;
			}
			xoffset = x - lastX;
			yoffset = lastY - y; 
			lastX = x;
			lastY = y;
			xoffset *= Settings.sensitivity;
			yoffset *= Settings.sensitivity;
			yaw += xoffset;
			pitch += yoffset;
			if(pitch > 89.0f)
			{
				pitch = 89.0f;
			}
			if(pitch < -89.0f)
			{
				pitch = -89.0f;
			}
			tempFront.set
			(
				(float)(Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))),
				(float)Math.sin(Math.toRadians(pitch)),
				(float)(Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)))
			);
			tar = tempFront.normalize();
			//Demo.vectorOut(tar);
		});
	}
	
	private static Vector3f cross(Vector3f p1, Vector3f p2) 
	{
		//System.out.println("\n");
		Vector3f res = new Vector3f();
		res.x = p1.y * p2.z - p2.y * p1.z;
		res.y = p1.z * p2.x - p2.z * p1.x;
		res.z = p1.x * p2.y - p2.x * p1.y;
		return res;
	}
	
	private static Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up)
	{
		Vector3f f = center.sub(eye).normalize();
		Vector3f u = up.normalize();
		Vector3f s = cross(f, u).normalize();
		u = cross(s, f);
		float[][] res = new float[4][4];
		for(int i = 0; i < res.length; i++)
		{
			res[i][i] = 1.0f;
		}
		res[0][0] = s.x;
		res[1][0] = s.y;
		res[2][0] = s.z;
		res[0][1] = u.x;
		res[1][1] = u.y;
		res[2][1] = u.z;
		res[0][2] =- f.x;
		res[1][2] =- f.y;
		res[2][2] =- f.z;
		res[3][0] =- s.dot(eye);
		res[3][1] =- u.dot(eye);
		res[3][2] =  f.dot(eye);
		float[] top = new float[16]; 
		for(int i = 0, k = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++, k++)
			{
				top[k] = res[j][i]; 
			}
		}
		return new Matrix4f((FloatBuffer)(BufferUtils.createFloatBuffer(top.length).put(top).flip()));
	}
	
	private static Matrix4f perspective(float fov, float ar, float near, float far)
	{
		float[] f = new float[16], t = new float[16];
		new Matrix4f().perspective(fov, ar, near, far).get(f);
		for(int i = 0, b = 0, r = 0; r < 16; b += ((i >= 16) ? 1 : 0), i = (i >= 16 ? b : i + 4))
		{
			if(i < 16)
			{
				t[r] = f[i];
				r++;
			}
		}
		return new Matrix4f((FloatBuffer)(BufferUtils.createFloatBuffer(t.length).put(t).flip()));
	}
}