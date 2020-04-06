package de.ecconia.java.tungboardio.components;

import java.util.ArrayList;
import java.util.List;

import de.ecconia.java.json.JSONArray;
import de.ecconia.java.json.JSONObject;

public class TungBoard extends TungComponent
{
	private double r = 0.7647059, g = 0.7647059, b = 0.7647059;
	
	protected int sizeX, sizeZ;
	protected double x, y, z;
	protected double xRot, yRot, zRot;
	
	private List<TungComponent> children = new ArrayList<>();
	
	/**
	 * Has its turn point in the middle one of its corners.
	 * Quater distance to both surfaces.
	 */
	public TungBoard(int sizeX, int sizeZ)
	{
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
	}
	
	public TungBoard setPosition(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public TungBoard setRotation(double x, double y, double z)
	{
		this.xRot = x;
		this.yRot = y;
		this.zRot = z;
		
		return this;
	}
	
	public TungBoard setColor(double r, double g, double b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		
		return this;
	}
	
	public TungBoard add(TungComponent comp)
	{
		children.add(comp);
		comp.setParent(this);
		
		return this;
	}
	
	@Override
	protected void resolve()
	{
		for(TungComponent comp : children)
		{
			comp.resolve();
		}
	}
	
	@Override
	public JSONObject asJSON()
	{
		if(getParent() == null)
		{
			resolve();
		}
		
		JSONObject json = new JSONObject();
		json.put("$type", "SavedObjects.SavedCircuitBoard, Assembly-CSharp");
		
		json.put("x", sizeX);
		json.put("z", sizeZ);
		
		JSONObject color = new JSONObject();
		color.put("r", r);
		color.put("g", g);
		color.put("b", b);
		json.put("color", color);
		
		JSONObject position = new JSONObject();
		position.put("x", x);
		position.put("y", y);
		position.put("z", z);
		json.put("LocalPosition", position);
		
		JSONObject angles = new JSONObject();
		angles.put("x", xRot);
		angles.put("y", yRot);
		angles.put("z", zRot);
		json.put("LocalEulerAngles", angles);
		
		JSONArray childrenJSON = new JSONArray();
		for(TungComponent component : children)
		{
			childrenJSON.add(component.asJSON());
		}
		json.put("Children", childrenJSON);
		json.put("CanHaveChildren", true);
		
		return json;
	}
}
