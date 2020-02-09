
import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.stb.STBImage.*;

import de.matthiasmann.twl.utils.PNGDecoder;

public class Texture
{	
	private int texture;

	public Texture(String path)
	{
		try
		{
			InputStream in = new FileInputStream(path);
			PNGDecoder decoder = new PNGDecoder(in);
			ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			buf.flip();
			in.close();
			texture = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, texture);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
			glGenerateMipmap(GL_TEXTURE_2D);
			glBindTexture(GL_TEXTURE_2D, 0); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int id()
	{
		return texture;
	}	
	
	public void delete()
	{
		glDeleteTextures(texture);
	}
}