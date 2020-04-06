package de.ecconia.java.tungboardio.stuff;

import de.ecconia.java.json.JSONObject;

public class Vector implements Exportable
{
	private double x, y, z;
	
	public Vector(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector()
	{
		this(0, 0, 0);
	}
	
	@Override
	public String toString()
	{
		return "[" + x + "; " + y + "; " + z + "]";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null || !(obj instanceof Vector))
		{
			return false;
		}
		
		Vector that = (Vector) obj;
		return this.x == that.x && this.y == that.y && this.z == that.z;
	}
	
	@Override
	public JSONObject asJSON()
	{
		JSONObject object = new JSONObject();
		object.put("x", x);
		object.put("y", y);
		object.put("z", z);
		return object;
	}
	
	public Vector subtract(Vector that)
	{
		return new Vector(this.x - that.x, this.y - that.y, this.z - that.z);
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getZ()
	{
		return z;
	}
	
	public Vector add(Vector that)
	{
		return new Vector(this.x + that.x, this.y + that.y, this.z + that.z);
	}
	
	public Vector flip()
	{
		return new Vector(this.x * -1, this.y * -1, this.z * -1);
	}
	
	public Vector normalize()
	{
		double length = Math.sqrt(x * x + y * y + z * z);
		
		return new Vector(this.x / length, this.y / length, this.z / length);
	}
	
	public double length()
	{
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	public Vector divide(int i)
	{
		return new Vector(this.x / i, this.y / i, this.z / i);
	}

	public Vector scale(double i)
	{
		return new Vector(this.x * i, this.y * i, this.z * i);
	}
}
