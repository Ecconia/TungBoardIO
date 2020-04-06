package de.ecconia.java.tungboardio.components;

import de.ecconia.java.json.JSONObject;

public class TungInverter extends TungComponent
{
	private boolean inverting;
	private double x, y, z;
	private double xRot, yRot, zRot;
	
	public TungInverter(boolean inverting)
	{
		this.inverting = inverting;
	}
	
	public TungInverter setPosition(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public TungInverter setRotation(double x, double y, double z)
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
		json.put("$type", "SavedObjects.SavedInverter, Assembly-CSharp");
		
		json.put("OutputOn", inverting);
		
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
