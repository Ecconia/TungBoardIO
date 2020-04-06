package de.ecconia.java.tungboardio.components;

import de.ecconia.java.json.JSONObject;

public class TungThroughBlotter extends TungComponent
{
	private boolean on;
	private double x, y, z;
	private double xRot, yRot, zRot;
	
	public TungThroughBlotter(boolean on)
	{
		this.on = on;
	}
	
	public TungThroughBlotter setPosition(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public TungThroughBlotter setRotation(double x, double y, double z)
	{
		this.xRot = x;
		this.yRot = y;
		this.zRot = z;
		
		return this;
	}
	
	@Override
	public JSONObject asJSON()
	{
		JSONObject json = new JSONObject();
		json.put("$type", "SavedObjects.SavedThroughBlotter, Assembly-CSharp");
		
		json.put("OutputOn", on);
		
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
		
		json.put("Children", null);
		json.put("CanHaveChildren", false);
		
		return json;
	}
}
