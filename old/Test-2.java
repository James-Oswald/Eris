import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Test
{
	public static Vector3f Ccross(Vector3f p1, Vector3f p2) 
	{
		//System.out.println("begin");
		//System.out.println(p1);
		//System.out.println(p2);
		//System.out.println("\n");
		Vector3f result = new Vector3f();
		result.x = p1.y * p2.z - p2.y * p1.z;
		result.y = p1.z * p2.x - p2.z * p1.x;
		result.z = p1.x * p2.y - p2.x * p1.y;
		return result;
	}
	
	public static Matrix4f ClookAt(Vector3f eye, Vector3f center, Vector3f up)
	{
		Vector3f f = center.sub(eye).normalize();
		Vector3f u = up.normalize();
		Vector3f s = Ccross(f, u).normalize();
		//System.out.println(f);
		//System.out.println(u);
		//System.out.println(s);
		u = Ccross(s, f);
		//System.out.println(u);
		float[][] Result = new float[4][4];
		for(int i = 0; i < Result.length; i++)
		{
			Result[i][i] = 1.0f;
		}
		Result[0][0] = s.x;
		Result[1][0] = s.y;
		Result[2][0] = s.z;
		Result[0][1] = u.x;
		Result[1][1] = u.y;
		Result[2][1] = u.z;
		Result[0][2] =- f.x;
		Result[1][2] =- f.y;
		Result[2][2] =- f.z;
		Result[3][0] =- s.dot(eye);
		Result[3][1] =- u.dot(eye);
		Result[3][2] =  f.dot(eye);
		float[] top = new float[16]; 
		for(int i = 0, k = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++, k++)
			{
				//System.out.println(k);
				//System.out.println(Result[i][j]);
				top[k] = Result[j][i]; 
			}
		}
		FloatBuffer buf = BufferUtils.createFloatBuffer(top.length);
		buf.put(top);
		buf.flip();
		return new Matrix4f(buf);
	}
	
	public static Matrix4f Cperspective(float fov, float ar, float near, float far)
	{
		float[] f = new float[16], t = new float[16];
		new Matrix4f().perspective(fov, ar, near, far).get(f);
		for(int i = 0, b = 0, r = 0; r < 16; b += ((i >= 16) ? 1 : 0), i = (i >= 16 ? b : i + 4))
		{
			if(i < 16)
			{
				System.out.println(i);
				t[r] = f[i];
				r++;
			}
		}
		return new Matrix4f((FloatBuffer)(BufferUtils.createFloatBuffer(t.length).put(t).flip()));
	}
	
	public static void out(float[] f)
	{
		for(int i = 0; i < f.length; i += 4)
		{
			System.out.println(f[i] + " " + f[i + 1] + " " + f[i + 2] + " " + f[i + 3] + "\n");
		}
	}
	
	public static void main(String[] args)
	{
		Vector3f position = new Vector3f(4, 4, 4);
		Vector3f target = new Vector3f(0, 0, 0);
		Vector3f up = new Vector3f(0, 1, 0);
		Matrix4f veiw = ClookAt(position, target, up);
		//System.out.println(veiw);
		float[] f = new float[16];
		veiw.get(f);
		//out(f);
		
		float fov = 45, near = 0.1f, far = 100.0f;
		Matrix4f per = Cperspective((float)Math.toRadians(fov), 1, near, far);
		System.out.println(per);
		per.get(f);
		//out(f);
		
		Matrix4f model = new Matrix4f().identity();
		//System.out.println(model);
		model.get(f);
		//out(f);
		
		Vector3f pos = new Vector3f(-0.5f, -0.5f, 0);
		
		Vector4f fin = new Vector4f(pos, 1.0f).mul(model.mul(veiw.mul(per)));
		
		//System.out.println(fin);
	}
}