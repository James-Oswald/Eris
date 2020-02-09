
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound
{
	public static void play(String file)
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(file));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}