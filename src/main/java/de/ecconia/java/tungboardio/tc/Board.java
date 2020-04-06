package de.ecconia.java.tungboardio.tc;

import java.util.ArrayList;
import java.util.List;

import de.ecconia.java.json.JSONArray;
import de.ecconia.java.json.JSONObject;
import de.ecconia.java.tungboardio.stuff.Color;
import de.ecconia.java.tungboardio.stuff.Dir;
import de.ecconia.java.tungboardio.stuff.MagicConverter;
import de.ecconia.java.tungboardio.stuff.Vector;

public class Board extends Component
{
	private int sizeX, sizeZ;
	private Color color = new Color();
	private Dir facing = Dir.PosY;
	
	private MagicConverter mc;
	
	private List<Component> children = new ArrayList<>();
	
	public Board(int sizeX, int sizeZ)
	{
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void setFacing(Dir facing)
	{
		this.facing = facing;
	}
	
	public void add(Component c)
	{
		children.add(c);
		c.setParent(this);
	}
	
	public void resolve()
	{
		//Initial call!
		resolve(new MagicConverter());
	}
	
	@Override
	public void resolve(MagicConverter pmc)
	{
		mc = pmc.spawn(new Vector((double) -sizeX / 2D * square1B1, 0D, (double) -sizeZ / 2D * square1B1), getPosition(), facing);
		
		for(Component child : children)
		{
			child.resolve(mc);
		}
	}
	
	@Override
	public JSONObject asJSON()
	{
		if(mc == null)
		{
			throw new IllegalStateException("Component has not been resolved yet.");
		}
		
		//Create json:
		JSONObject json = new JSONObject();
		json.put("$type", "SavedObjects.SavedCircuitBoard, Assembly-CSharp");
		
		json.put("x", sizeX);
		json.put("z", sizeZ);
		
		json.put("color", color.asJSON());
		json.put("LocalPosition", mc.getLocalPosition().asJSON());
		json.put("LocalEulerAngles", mc.getEulerRotation().asJSON());
		
		JSONArray childrenJSON = new JSONArray();
		for(Component component : children)
		{
			childrenJSON.add(component.asJSON());
		}
		json.put("Children", childrenJSON);
		json.put("CanHaveChildren", true);
		
		return json;
	}
}
