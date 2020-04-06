package de.ecconia.java.tungboardio.tc;

import de.ecconia.java.json.JSONObject;
import de.ecconia.java.tungboardio.stuff.Dir;
import de.ecconia.java.tungboardio.stuff.MagicConverter;
import de.ecconia.java.tungboardio.stuff.Vector;

public class Inverter extends Component
{
	private boolean isInverting;
	private MagicConverter mc;
	private Dir facingInput = Dir.PosY;
	private Dir facingOutput = Dir.NegX;
	
	public Inverter(boolean isInverting)
	{
		this.isInverting = isInverting;
	}
	
	public void setInputFacing(Dir in)
	{
		facingInput = in;
	}
	
	public void setOutputFacing(Dir out)
	{
		facingOutput = out;
	}
	
	@Override
	public void resolve(MagicConverter mc)
	{
		this.mc = mc;
	}
	
	@Override
	public JSONObject asJSON()
	{
		if(mc == null)
		{
			throw new IllegalStateException("Please resolve the parent board first.");
		}
		
		JSONObject json = new JSONObject();
		json.put("$type", "SavedObjects.SavedInverter, Assembly-CSharp");
		
		json.put("OutputOn", isInverting);
		
		json.put("LocalPosition", mc.convertAbsolutePosition(getPosition()).asJSON());
		
		Vector euler = null;
		Dir rotatedInputDirection = mc.convertAbsoluteDirection(facingInput);
		Dir rotatedOutputDirection = mc.convertAbsoluteDirection(facingOutput);
		if(rotatedInputDirection == Dir.PosX)
		{
			if(rotatedOutputDirection == Dir.PosY)
			{
				euler = new Vector(90, 90, 0);
			}
			else if(rotatedOutputDirection == Dir.NegY)
			{
				euler = new Vector(-90, -90, 0);
			}
			else if(rotatedOutputDirection == Dir.PosZ)
			{
				euler = new Vector(0, 180, 90);
			}
			else if(rotatedOutputDirection == Dir.NegZ)
			{
				euler = new Vector(0, 0, -90);
			}
		}
		else if(rotatedInputDirection == Dir.NegX)
		{
			if(rotatedOutputDirection == Dir.PosY)
			{
				euler = new Vector(90, -90, 0);
			}
			else if(rotatedOutputDirection == Dir.NegY)
			{
				euler = new Vector(-90, 90, 0);
			}
			else if(rotatedOutputDirection == Dir.PosZ)
			{
				euler = new Vector(0, 180, -90);
			}
			else if(rotatedOutputDirection == Dir.NegZ)
			{
				euler = new Vector(0, 0, 90);
			}
		}
		else if(rotatedInputDirection == Dir.PosY)
		{
			if(rotatedOutputDirection == Dir.PosX)
			{
				euler = new Vector(0, -90, 0);
			}
			else if(rotatedOutputDirection == Dir.NegX)
			{
				euler = new Vector(0, 90, 0);
			}
			else if(rotatedOutputDirection == Dir.PosZ)
			{
				euler = new Vector(0, 180, 0);
			}
			else if(rotatedOutputDirection == Dir.NegZ)
			{
				euler = new Vector(0, 0, 0);
			}
		}
		else if(rotatedInputDirection == Dir.NegY)
		{
			if(rotatedOutputDirection == Dir.PosX)
			{
				euler = new Vector(180, 90, 0);
			}
			else if(rotatedOutputDirection == Dir.NegX)
			{
				euler = new Vector(180, -90, 0);
			}
			else if(rotatedOutputDirection == Dir.PosZ)
			{
				euler = new Vector(180, 0, 0);
			}
			else if(rotatedOutputDirection == Dir.NegZ)
			{
				euler = new Vector(180, 180, 0);
			}
		}
		else if(rotatedInputDirection == Dir.PosZ)
		{
			if(rotatedOutputDirection == Dir.PosX)
			{
				euler = new Vector(0, -90, -90);
			}
			else if(rotatedOutputDirection == Dir.NegX)
			{
				euler = new Vector(0, 90, 90);
			}
			else if(rotatedOutputDirection == Dir.PosY)
			{
				euler = new Vector(90, 0, 0);
			}
			else if(rotatedOutputDirection == Dir.NegY)
			{
				euler = new Vector(-90, 180, 0);
			}
		}
		else if(rotatedInputDirection == Dir.NegZ)
		{
			if(rotatedOutputDirection == Dir.PosX)
			{
				euler = new Vector(0, -90, 90);
			}
			else if(rotatedOutputDirection == Dir.NegX)
			{
				euler = new Vector(0, 90, -90);
			}
			else if(rotatedOutputDirection == Dir.PosY)
			{
				euler = new Vector(90, 180, 0);
			}
			else if(rotatedOutputDirection == Dir.NegY)
			{
				euler = new Vector(-90, 0, 0);
			}
		}
		json.put("LocalEulerAngles", euler.asJSON());
		
		json.put("Children", null);
		json.put("CanHaveChildren", false);
		
		return json;
	}
}
