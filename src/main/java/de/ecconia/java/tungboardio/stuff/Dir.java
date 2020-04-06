package de.ecconia.java.tungboardio.stuff;

public enum Dir
{
	PosY(new Vector(0, 1, 0), true, "+Y"),
	NegY(new Vector(0, -1, 0), false, "-Y"),
	PosX(new Vector(1, 0, 0), true, "+X"),
	NegX(new Vector(-1, 0, 0), false, "-X"),
	PosZ(new Vector(0, 0, 1), true, "+Z"),
	NegZ(new Vector(0, 0, -1), false, "-Z");
	
	private final Vector v;
	private final boolean p;
	private final String s;
	
	private Dir(Vector v, boolean p, String s)
	{
		this.v = v;
		this.p = p;
		this.s = s;
	}
	
	public boolean isPositive()
	{
		return p;
	}
	
	public Vector getVector()
	{
		return v;
	}
	
	@Override
	public String toString()
	{
		return s;
	}
	
	public static Dir fromVector(Vector vector)
	{
		if(PosX.v.equals(vector))
		{
			return PosX;
		}
		else if(NegX.v.equals(vector))
		{
			return NegX;
		}
		else if(PosY.v.equals(vector))
		{
			return PosY;
		}
		else if(NegY.v.equals(vector))
		{
			return NegY;
		}
		else if(PosZ.v.equals(vector))
		{
			return PosZ;
		}
		else if(PosX.v.equals(vector))
		{
			return PosX;
		}
		else if(NegZ.v.equals(vector))
		{
			return NegZ;
		}
		else
		{
			throw new IllegalArgumentException("Non-gridbased unit vector: " + vector);
		}
	}
}
