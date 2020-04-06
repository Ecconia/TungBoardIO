package de.ecconia.java.tungboardio.tc;

import de.ecconia.java.json.JSONObject;
import de.ecconia.java.tungboardio.stuff.Dir;
import de.ecconia.java.tungboardio.stuff.MagicConverter;
import de.ecconia.java.tungboardio.stuff.Vector;

public class Peg extends Component
{
	private MagicConverter pmc;
	private Dir facing = Dir.PosY;
	
	public Peg()
	{
	}
	
	@Override
	public void resolve(MagicConverter pmc)
	{
		this.pmc = pmc;
	}
	
	public void setFacing(Dir facing)
	{
		this.facing = facing;
	}
	
	public static Vector getWirePoint()
	{
		return new Vector(0, square1B1 * 0.9D, 0);
	}
	
	@Override
	public JSONObject asJSON()
	{
		System.out.println();
		System.out.println("Exporting Peg:");
		
		JSONObject json = new JSONObject();
		json.put("$type", "SavedObjects.SavedPeg, Assembly-CSharp");
		
		Vector euler = null;
		Dir localDirection = pmc.convertAbsoluteDirection(facing);
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
		
		json.put("LocalPosition", pmc.convertAbsolutePosition(getPosition()).asJSON());
		json.put("LocalEulerAngles", euler.asJSON());
		
		json.put("Children", null);
		json.put("CanHaveChildren", false);
		
		return json;
	}
}
