package de.ecconia.java.tungboardio.tc;

import de.ecconia.java.json.JSONObject;
import de.ecconia.java.tungboardio.stuff.Dir;
import de.ecconia.java.tungboardio.stuff.MagicConverter;
import de.ecconia.java.tungboardio.stuff.Vector;

public class ThroughBlotter extends Component
{
	private MagicConverter mc;
	private Dir facing;
	private boolean outputOn;
	
	public ThroughBlotter(boolean outputOn)
	{
		this.outputOn = outputOn;
	}
	
	public void setFacing(Dir facing)
	{
		this.facing = facing;
	}
	
	@Override
	public void resolve(MagicConverter mc)
	{
		this.mc = mc;
		
		Vector offset = facing.getVector().scale(0.075D);
		setPosition(getPosition().add(offset));
	}
	
	@Override
	public JSONObject asJSON()
	{
		JSONObject json = new JSONObject();
		json.put("$type", "SavedObjects.SavedThroughBlotter, Assembly-CSharp");
		
		json.put("OutputOn", outputOn);
		
		Vector euler = null;
		Dir localDirection = mc.convertAbsoluteDirection(facing);
		if(localDirection == Dir.PosX)
		{
			euler = new Vector(0, 0, 90);
		}
		else if(localDirection == Dir.NegX)
		{
			euler = new Vector(0, 0, -90);
		}
		else if(localDirection == Dir.PosY)
		{
			euler = new Vector(0, 0, 0);
		}
		else if(localDirection == Dir.NegY)
		{
			euler = new Vector(0, 0, 180);
		}
		else if(localDirection == Dir.PosZ)
		{
			euler = new Vector(90, 0, 0);
		}
		else if(localDirection == Dir.NegZ)
		{
			euler = new Vector(-90, 0, 0);
		}
		
		json.put("LocalPosition", mc.convertAbsolutePosition(getPosition()).asJSON());
		json.put("LocalEulerAngles", euler.asJSON());
		
		json.put("Children", null);
		json.put("CanHaveChildren", false);
		
		return json;
	}
}
