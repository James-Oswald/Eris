
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.GLFW.*;

public class IconLoader
{
	public static void load(long window, String path) throws Exception
	{
		IntBuffer w = memAllocInt(1);
		IntBuffer h = memAllocInt(1);
		IntBuffer comp = memAllocInt(1);
		ByteBuffer icon16;
		ByteBuffer icon32;
		try 
		{
			icon16 = ioResourceToByteBuffer(path, 2048);
			icon32 = ioResourceToByteBuffer(path, 4096);
		} 
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
		try (GLFWImage.Buffer icons = GLFWImage.malloc(2)) 
		{
			ByteBuffer pixels16 = STBImage.stbi_load_from_memory(icon16, w, h, comp, 4);
			icons
				.position(0)
				.width(w.get(0))
				.height(h.get(0))
				.pixels(pixels16);

			ByteBuffer pixels32 = STBImage.stbi_load_from_memory(icon32, w, h, comp, 4);
			icons
				.position(1)
				.width(w.get(0))
				.height(h.get(0))
				.pixels(pixels32);

			icons.position(0);
			glfwSetWindowIcon(window, icons);

			STBImage.stbi_image_free(pixels32);
			STBImage.stbi_image_free(pixels16);
		}
		memFree(comp);
		memFree(h);
		memFree(w);
	}
	
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) 
	{
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}
	
	private static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws Exception 
	{
		ByteBuffer buffer;
		Path path = Paths.get(resource);
		if (Files.isReadable(path)) 
		{
			try (SeekableByteChannel fc = Files.newByteChannel(path)) 
			{
				buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
				while(fc.read(buffer) != -1 );
			}
		} 
		else 
		{
			try
			(
				InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
				ReadableByteChannel rbc = Channels.newChannel(source)
			) 
			{
				buffer = BufferUtils.createByteBuffer(bufferSize);
				while (true) 
				{
					int bytes = rbc.read(buffer);
					if (bytes == -1)
					{
						break;
					}
					if (buffer.remaining() == 0)
					{
						buffer = resizeBuffer(buffer, buffer.capacity() * 2);
					}
				}
			}
		}
		buffer.flip();
		return buffer;
	}
}