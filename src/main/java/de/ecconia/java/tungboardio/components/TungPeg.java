package de.ecconia.java.tungboardio.components;

import de.ecconia.java.json.JSONObject;

public class TungPeg extends TungComponent
{
	private double x, y, z;
	private double xRot, yRot, zRot;
	
	public TungPeg()
	{
	}
	
	public TungPeg setPosition(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public TungPeg setRotation(double x, double y, double z)
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
		json.put("$type", "SavedObjects.SavedPeg, Assembly-CSharp");
		
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
