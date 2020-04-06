package de.ecconia.java.tungboardio.stuff;

import de.ecconia.java.json.JSONObject;

public class Color implements Exportable
{
	private int r = 195;
	private int g = 195;
	private int b = 195;
	
	public Color()
	{
		this(195, 195, 195);
	}
	
	public Color(int r, int g, int b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	@Override
	public JSONObject asJSON()
	{
		JSONObject obj = new JSONObject();
		obj.put("r", (float) r / 255F);
		obj.put("g", (float) g / 255F);
		obj.put("b", (float) b / 255F);
		return obj;
	}
}
