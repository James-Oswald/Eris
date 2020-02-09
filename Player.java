

import java.util.ArrayList;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class Player extends Creature
{
	private static final Font bold = new Font("SansSerif", Font.BOLD, 30), norm = new Font("SansSerif", Font.PLAIN, 20);
	private int person = 1, mode = 0, weaponIndex, sammo = 50, pammo = 100, lammo = 100, kills = 0;
	private float height = 1, jumpHeight, jumpSpeed, dswap = 0;
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	
	public Player(Vector3f pos)
	{
		super(pos, null, new Hitbox(1, 1, 1, new Vector3f()), 0, 0);
		weapons.add(new Pistol());
		weapons.add(new Shotgun());
		weapons.add(new PlasmaGun());
		weaponIndex = 0;
		setWeapon(weapons.get(weaponIndex));
		try
		{
			if(mode == 0)
			{
				speed = 2;    
				health = 200;
				jumpHeight = 2f;
				grounded = true;
				applyGravity();
				applyWall();
				//hitbox = new Hitbox(1, 1, 1, new Vector3f());
				colbox = new Hitbox(0.3f, 0.3f, 0, position);
				ImageIcon back = new ImageIcon("res/playerBackground.png");
				JPanel infoPanel = new JPanel()
				{
					public void paintComponent(Graphics g)
					{
						super.paintComponent(g);
						for(int i = 0; i < Window.getInfoFrame().getWidth(); i+= back.getIconWidth())
						{
							g.drawImage(back.getImage(), i, 0, null);
						}
						g.setFont(bold);
						g.setColor(Color.yellow);
						g.drawString("Health: " + health + "%", 20, 30);
						g.setFont(norm);
						g.drawString("Bullets: " + pammo, 20, 70);
						g.drawString("Shells:  " + sammo, 20, 100);
						g.drawString("Charges: " + lammo, 20, 130);
						g.setFont(bold);
						g.drawString("Kills: " + kills, 250, 30);
						g.drawString("Time:  " + (int)Window.time(), 250, 60);
					}
				};
				Window.getInfoFrame().add(infoPanel);
				Window.getInfoFrame().setVisible(true);
				Renderer.addAction(()->
				{
					infoPanel.repaint();
					weapon.setDir(Camera.getFront());
					float yaw = (float)-Math.toRadians((Camera.getYaw() + 120) % 360);
					Vector3f r = weapon.getRot();
					weapon.setRot(new Vector3f(0, yaw + weapon.getOffset(), 0));
					weapon.setPos(new Vector3f(position.x, position.y + 0.8f, position.z));
					hitbox.setPos(new Vector3f(position.x, position.y + height, position.z));
					float cameraSpeed = speed * Renderer.getDeltaTime();
					if(KeyInput.isDown(GLFW_KEY_SPACE))
					{
						if(grounded)
						{
							grounded = false;
							new Thread(()->
							{
								double startTime = glfwGetTime();
								float startPos = position.y;
								while(glfwGetTime() - startTime < Math.PI / 4)
								{
									position.y = (float)(startPos + jumpHeight * Math.sin(2 * (glfwGetTime() - startTime)));
								}
								grounded = true;
							}).start();
						}
					}
					if(KeyInput.isDown(GLFW_KEY_W))
					{
						Vector3f mov = new Vector3f(Camera.getFront());
						mov.y = 0;
						position.add(mov.mul(cameraSpeed));
						correctWall();
					}
					if(KeyInput.isDown(GLFW_KEY_S))
					{
						Vector3f mov = new Vector3f(Camera.getFront());
						mov.y = 0;
						position.add(mov.mul(-cameraSpeed));
						correctWall();
					}
					if(KeyInput.isDown(GLFW_KEY_A))
					{
						Vector3f mov = new Vector3f(Camera.getRight());
						mov.y = 0;
						position.add(mov.mul(cameraSpeed));
						correctWall();
					}
					if(KeyInput.isDown(GLFW_KEY_D))
					{
						Vector3f mov = new Vector3f(Camera.getRight());
						mov.y = 0;
						position.add(mov.mul(-cameraSpeed));
						correctWall();
					}
					if(KeyInput.isDown(GLFW_KEY_Q))
					{
						if(Window.time() > dswap + 1)
						{
							weaponIndex++;
							if(weaponIndex > weapons.size() - 1)
							{
								weaponIndex = 0;
							}
							setWeapon(weapons.get(weaponIndex));
							dswap = (float)Window.time();
						}
					}
					if(KeyInput.isDown(GLFW_KEY_E))
					{
						if(Window.time() > dswap + 1)
						{
							weaponIndex--;
							if(weaponIndex < 0)
							{
								weaponIndex = weapons.size() - 1;
							}
							setWeapon(weapons.get(weaponIndex));
							dswap = (float)Window.time();
						}
					}
					finalizePosition();
				});
				MouseBtnInput.addAction((b, a)->
				{
					if(b == GLFW_MOUSE_BUTTON_LEFT)
					{
						weapon.action(0);
					}
					else if(b == GLFW_MOUSE_BUTTON_RIGHT)
					{
						weapon.action(0);
					}
				});
			}
			if(mode == 1)
			{
				speed = 6;
				Renderer.addAction(()->
				{
					float cameraSpeed = speed * Renderer.getDeltaTime();
					if(KeyInput.isDown(GLFW_KEY_LEFT_SHIFT))
					{
						position.add(0, -1, 0);
					}
					if(KeyInput.isDown(GLFW_KEY_SPACE))
					{
						position.add(0, 1, 0);
					}
					if(KeyInput.isDown(GLFW_KEY_W))
					{
						Vector3f mov = new Vector3f(Camera.getFront());
						position.add(mov.mul(cameraSpeed));
					}
					if(KeyInput.isDown(GLFW_KEY_S))
					{
						Vector3f mov = new Vector3f(Camera.getFront());
						position.add(mov.mul(-cameraSpeed));
					}
					if(KeyInput.isDown(GLFW_KEY_A))
					{
						Vector3f mov = new Vector3f(Camera.getRight());
						position.add(mov.mul(cameraSpeed));
					}
					if(KeyInput.isDown(GLFW_KEY_D))
					{
						Vector3f mov = new Vector3f(Camera.getRight());
						position.add(mov.mul(-cameraSpeed));
					}
					finalizePosition();
				});
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private int getScore()
	{
		return (int)(sammo + 0.5 * lammo + 0.3 * pammo + 5 * kills); 
	}
	
	private static final int ew = 500, eh = 500;
	
	public void die()
	{
		World.getLevel().endSound();
		Renderer.end();
		JFrame frame =  new JFrame("You Died D:");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(ew, eh);
		JPanel panel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.black);
				g.setFont(bold);
				g.drawString("You Died", 200, 100);
				g.drawString("Your Score: " + getScore(), 180, 200);
			}
		};
		panel.setBackground(Color.red);
		frame.add(panel);
		TitleScreen.centerJFrame(frame);
		frame.setVisible(true);
	}
	
	public void win()
	{
		Renderer.end();
		JFrame frame =  new JFrame("You Won! :D");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(ew, eh);
		JPanel panel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.black);
				g.setFont(bold);
				g.drawString("You win!", 200, 100);
				g.drawString("Your Score: " + (getScore() + 100), 180, 200);
			}
		};
		panel.setBackground(Color.green);
		frame.add(panel);
		TitleScreen.centerJFrame(frame);
		frame.setVisible(true);
	}
	
	public int getLammo()
	{
		return lammo;
	}
	
	public void useLammo()
	{
		lammo--;
	}
	
	public int getPammo()
	{
		return pammo;
	}
	
	public void usePammo()
	{
		pammo--;
	}
	
	public int getSammo()
	{
		return sammo;
	}
	
	public void useSammo()
	{
		sammo--;
	}
	
	public void setPerson(int p)
	{
		person = p;
	}
	
	public void incKills()
	{
		kills++;
	}
	
	private void finalizePosition()
	{
		if (person == 3)
		{
			Camera.moveTo(new Vector3f(position.x - Camera.getFront().x, position.y + height, position.z - Camera.getFront().z));
		}
		else
		{
			Camera.moveTo(new Vector3f(position.x, position.y + height, position.z));
		}
	}
}