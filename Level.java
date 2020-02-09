
import javax.imageio.ImageIO;
import java.io.File;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Level
{
	private ArrayList<Model> models;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private BufferedImage level;
	private int[][] heights, specials;
	private int width, height;
	private Texture[][] textures; 
	private ArrayList<Texture> floorData = new ArrayList<Texture>();
	private ArrayList<Texture> enemyData = new ArrayList<Texture>();
	private Vector3f playerStartPos = new Vector3f(10, 10, 10), levelEnd;
	private MP3 sound = new MP3("res/game.mp3");
	
	public Level(String file)
	{
		try
		{
			sound.play();
			Renderer.addAction(()->
			{
				if((int)Window.time() % 90 == 0)
				{
					sound.play();
				}
			});
			models = new ArrayList<Model>();
			level = ImageIO.read(new File("levels/" + file + "/level.png"));
			width = level.getWidth();
			height = level.getHeight();
			heights = new int[height][width];
			specials = new int[height][width];
			textures = new Texture[height][width];
			for(String s : new File("levels/" + file).list())
			{
				if(s.indexOf("floor") != -1)
				{
					floorData.add(new Texture("levels/" + file + "/floor" + Integer.parseInt(s.substring(s.length() - 5, s.length() - 4)) + ".png"));
				}
				if(s.indexOf("enemy") != -1)
				{
					enemyData.add(new Texture("levels/" + file + "/enemy" + Integer.parseInt(s.substring(s.length() - 5, s.length() - 4)) + ".png"));
				}
			}
			for(int x = 0; x < width; x++)
			{
				for(int y = 0; y < height; y++)
				{
					Color data = new Color(level.getRGB(x, y));
					if(data.getRed() < 100)
					{
						heights[y][x] = data.getRed();
						textures[y][x] = floorData.get(data.getGreen());
						specials[y][x] = data.getBlue();
						Model m = new Model(VAO.tile, Model.textShader, textures[y][x]);
						m.addTrans(new Matrix4f().translate(x, heights[y][x], y));
						models.add(m);
					}
					else
					{
						heights[y][x] = 0;
					}
				}
			}
			for(int x = 0; x < width; x++)
			{
				for(int y = 0; y < height; y++)
				{
					try
					{
						if(heights[y][x] > heights[y + 1][x] && heights[y + 1][x] != -10)
						{
							for(int i = heights[y][x]; i > heights[y + 1][x]; i--)
							{
								Model m = new Model(VAO.tile, Model.textShader, textures[y][x]);
								m.addTrans(new Matrix4f().translate(x, i, y + 1));
								m.addTrans(new Matrix4f().rotateX((float)Math.PI / 2));
								models.add(m);
							}
						}
						if(heights[y][x] > heights[y - 1][x] && heights[y - 1][x] != -10)
						{
							for(int i = heights[y][x]; i > heights[y - 1][x]; i--)
							{
								Model m = new Model(VAO.tile, Model.textShader, textures[y][x]);
								m.addTrans(new Matrix4f().translate(x, i , y));
								m.addTrans(new Matrix4f().rotateX((float)Math.PI / 2));
								models.add(m);
							}
						}
						if(heights[y][x] > heights[y][x + 1] && heights[y][x + 1] != -10)
						{
							for(int i = heights[y][x]; i > heights[y][x + 1]; i--)
							{
								Model m = new Model(VAO.tile, Model.textShader, textures[y][x]);
								m.addTrans(new Matrix4f().translate(x + 1, i - 1, y));
								m.addTrans(new Matrix4f().rotateZ((float)Math.PI / 2));
								models.add(m);
							}
						}
						if(heights[y][x] > heights[y][x - 1] && heights[y][x - 1] != -10)
						{
							for(int i = heights[y][x]; i > heights[y][x - 1]; i--)
							{
								Model m = new Model(VAO.tile, Model.textShader, textures[y][x]);
								m.addTrans(new Matrix4f().translate(x, i - 1, y));
								m.addTrans(new Matrix4f().rotateZ((float)Math.PI / 2));
								models.add(m);
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setSpecials()
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				int special = specials[y][x];
				if(special == 1)
				{
					playerStartPos = new Vector3f(x, heights[y][x] + 1, y);
				}
				else if(special == 2)
				{
					levelEnd = new Vector3f(x, heights[y][x] + 1, y);
					Entity end = new Entity(new Model("res/diamond.obj", new Color(0, 255, 255), Color.blue), new Vector3f(levelEnd), new Vector3f(), new Vector3f((float)(Math.PI / 2), 0, 0), new Vector3f(0, 0, 1),0.007f, 0, 1);			
					World.add(end);
					Renderer.addAction(()->
					{
						end.setPos(levelEnd.x, (float)(levelEnd.y + 0.5 * Math.sin(Window.time())), levelEnd.z);
						if(World.getPlayer().getHitbox().inside(levelEnd))
						{
							sound.close();
							World.getPlayer().win();
						}
					});
				}
				else if(special == 3)
				{
					enemies.add(new Boomer(enemyData.get(0), new Vector3f(x, heights[y][x], y)));
				}
				else if(special == 4)
				{
					enemies.add(new Ghost(enemyData.get(1), new Vector3f(x, heights[y][x], y)));
				}
				else if(special == 5)
				{
					enemies.add(new Turret(enemyData.get(2), new Vector3f(x, heights[y][x], y)));
				}
			}
		}
		enemies.forEach(World::add);
	}
	
	public ArrayList<Model> getModels()
	{
		return models;
	}
	
	public Vector3f getPSP()
	{
		return playerStartPos;
	}
	
	public int[][] getHeights()
	{
		return heights;
	}
	
	public void endSound()
	{
		sound.close();
	}
	
	public float floorAt(int x, int y)
	{
		try
		{
			return heights[y][x];
		}
		catch(Exception e)
		{
			return -1;
		}
	}
}