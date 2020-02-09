import java.nio.file.Paths;
import java.nio.file.Files;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader
{
	private int program, vertex, fragment;
	private Uniforms uniforms;
	
	@FunctionalInterface
	public interface Uniforms
	{
		public void setUniforms(int id);
	}
	
	public Shader(String vsrc, String fsrc) 
	{
		try
		{
			vertex = genShader(vsrc, GL_VERTEX_SHADER);
			fragment = genShader(fsrc, GL_FRAGMENT_SHADER);
			program = glCreateProgram();
			glAttachShader(program, vertex);
			glAttachShader(program, fragment);
			for(int i = 0; i < 4; i++)
			{
				glBindAttribLocation(program, i, "");
			}
			glLinkProgram(program);
			glValidateProgram(program);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void bindUniforms(Uniforms uniforms)
	{
		this.uniforms = uniforms;
	}
	
	public void setUniforms()
	{
		uniforms.setUniforms(program);
	}
	
	public void bind()
	{
		glUseProgram(program);
	}
	
	public void unbind()
	{
		glUseProgram(0);
	}
	
	public void delete()
	{
		glDetachShader(program, vertex);
        glDetachShader(program, fragment);
        glDeleteShader(vertex);
        glDeleteShader(fragment);
        glDeleteProgram(program);
	}
	
	private int genShader(String path, int type) throws Exception
	{
		int shader = glCreateShader(type);
		glShaderSource(shader, new String(Files.readAllBytes(Paths.get(path))));
		glCompileShader(shader);
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			throw new Exception("Failed to compile " + path + "!\n" + glGetShaderInfoLog(shader, 500) + "\n");
		}
		return shader;
	}
	
	public int id()
	{
		return program;
	}
}