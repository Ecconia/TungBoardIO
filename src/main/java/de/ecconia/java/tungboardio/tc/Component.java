package de.ecconia.java.tungboardio.tc;

import de.ecconia.java.tungboardio.stuff.Exportable;
import de.ecconia.java.tungboardio.stuff.MagicConverter;
import de.ecconia.java.tungboardio.stuff.Vector;

public abstract class Component implements Exportable
{
	public static final double square1B1 = 0.3D;
	public static final double square1B2 = 0.15D;
	public static final double square3B4 = 0.225D;
	public static final double square1B4 = 0.075D;
	
	private Board parent;
	
	public void setParent(Board parent)
	{
		this.parent = parent;
	}
	
	public Board getParent()
	{
		return parent;
	}
	
	private Vector position = new Vector();
	
	public void setPosition(Vector position)
	{
		this.position = position;
	}
	
	public Vector getPosition()
	{
		return position;
	}
	
	public abstract void resolve(MagicConverter mc);
}
