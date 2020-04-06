package de.ecconia.java.tungboardio.components;

import de.ecconia.java.tungboardio.stuff.Exportable;

public abstract class TungComponent implements Exportable
{
	public static final double square1B1 = 0.3D;
	public static final double square1B2 = 0.15D;
	public static final double square3B4 = 0.225D;
	public static final double square1B4 = 0.075D;
	
	private TungBoard parent;
	
	protected void setParent(TungBoard parent)
	{
		if(this.parent != null)
		{
			throw new IllegalStateException("This component is already used somewhere else. Please clone it.");
		}
		
		this.parent = parent;
	}
	
	protected TungBoard getParent()
	{
		return parent;
	}
	
	protected void resolve()
	{
	}
}
