package de.ecconia.java.tungboardio.components;

import de.ecconia.java.json.JSONObject;

public class TungWire extends TungComponent
{
	private double x, y, z;
	private double xRot, yRot, zRot;
	private double length;
	
	public TungWire()
	{
	}
	
	public TungWire(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		setPosition(x1, y1, z1);
		
		double dx = x1 - x2;
		double dy = y1 - y2;
		double dz = z1 - z2;
		length = Math.sqrt(dx * dx + dy * dy + dz * dz);
		
		System.out.println("Sanity test: " + length + " == " + 0.334389359D);
	}
	
	public void setPosition(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setRotation(double x, double y, double z)
	{
		this.xRot = x;
		this.yRot = y;
		this.zRot = z;
	}
	
	@Override
	public JSONObject asJSON()
	{
		JSONObject json = new JSONObject();
		json.put("$type", "SavedObjects.SavedWire, Assembly-CSharp");
		
		json.put("InputInput", false);
		json.put("length", length);
		
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
