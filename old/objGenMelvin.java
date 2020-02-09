
import java.util.stream.Stream;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.io.File;
import java.io.PrintWriter;


public class objGenMelvin
{
	private static String newFile = "";
	
	public static void main(String[] melvin)
	{
		try(Stream<String> lines = Files.lines(Paths.get("pi.obj"), Charset.defaultCharset())) 
		{
			lines.forEach(line -> 
			{
				if(line.charAt(1) == ' ' && line.charAt(0) == 'v' || line.charAt(0) == 'f')
				{
					System.out.println(line);
					newFile += line + '\n';
				}
			});
			new PrintWriter("pi2.obj").println(newFile);
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
	}
}
