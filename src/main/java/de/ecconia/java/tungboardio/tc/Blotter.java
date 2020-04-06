package de.ecconia.java.tungboardio.tc;

import de.ecconia.java.json.JSONObject;
import de.ecconia.java.tungboardio.stuff.Dir;
import de.ecconia.java.tungboardio.stuff.MagicConverter;
import de.ecconia.java.tungboardio.stuff.Vector;

public class Blotter extends Component
{
	private MagicConverter pmc;
	private Dir facing = Dir.PosY;
	private boolean outputOn;
	
	private Vector euler;
	private Vector posFix;
	
	public Blotter(boolean outputOn)
	{
		this.outputOn = outputOn;
	}
	
	@Override
	public void resolve(MagicConverter pmc)
	{
		this.pmc = pmc;
		
		Dir localDirection = pmc.convertAbsoluteDirection(facing);
		if(localDirection == Dir.PosX)
		{
			euler = new Vector(0, -90, 0);
			posFix = new Vector(0, -square1B2, 0);
		}
		else if(localDirection == Dir.NegX)
		{
			euler = new Vector(0, 90, 0);
			posFix = new Vector(0, -square1B2, 0);
		}
		else if(localDirection == Dir.PosY)
		{
			euler = new Vector(90, 0, 0);
			posFix = new Vector(0, 0, -square1B2);
		}
		else if(localDirection == Dir.NegY)
		{
			euler = new Vector(-90, 0, 0);
			posFix = new Vector(0, 0, square1B2);
		}
		else if(localDirection == Dir.PosZ)
		{
			euler = new Vector(180, 0, 0);
			posFix = new Vector(0, square1B2, 0);
		}
		else if(localDirection == Dir.NegZ)
		{
			euler = new Vector(0, 0, 0);
			posFix = new Vector(0, -square1B2, 0);
		}
	}
	
	public void setFacing(Dir facing)
	{
		this.facing = facing;
	}
	
	@Override
	public JSONObject asJSON()
	{
		JSONObject json = new JSONObject();
		json.put("$type", "SavedObjects.SavedBlotter, Assembly-CSharp");
		
		json.put("OutputOn", outputOn);
		
		
		
		json.put("LocalPosition", pmc.convertAbsolutePosition(getPosition()).add(posFix).asJSON());
		json.put("LocalEulerAngles", euler.asJSON());
		
		json.put("Children", null);
		json.put("CanHaveChildren", false);
		
		return json;
	}
}
