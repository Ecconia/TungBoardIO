package de.ecconia.java.tungboardio.tc;

import de.ecconia.java.json.JSONObject;
import de.ecconia.java.tungboardio.stuff.MagicConverter;
import de.ecconia.java.tungboardio.stuff.Vector;

public class Wire extends Component
{
	private Vector direction;
	private double length;
	private boolean input2x = true;
	
	private Vector start;
	private Vector end;
	
	public Wire(Vector start, Vector end, boolean input2x)
	{
		this.start = start;
		this.end = end;
		this.input2x = input2x;
	}
	
	@Override
	public void resolve(MagicConverter mc)
	{
		System.out.println("\nResolving wire:");
		direction = end.subtract(start);
		length = direction.length(); //If this is 0, then someone connected garbage.
		if(length == 0)
		{
			throw new IllegalStateException("Wire with length zero. Please connect a wire between two different Points.");
		}
		
		Vector half = direction.divide(2);
		start = start.add(half);
		start = mc.convertAbsolutePosition(start);
		
		setPosition(start);
		
		Vector rotatedDirection = mc.rotateAbsolutePosition(direction);
		
		double eulerX = -Math.toDegrees(Math.asin(rotatedDirection.getY() / length));
		double len2 = Math.sqrt(rotatedDirection.getX() * rotatedDirection.getX() + rotatedDirection.getZ() * rotatedDirection.getZ());
		double eulerY = len2 == 0 ? 0D : Math.toDegrees(Math.asin(rotatedDirection.getX() / len2));
		
		if(direction.getZ() < 0)
		{
			eulerX = -eulerX;
			eulerY = -eulerY;
		}
		
		direction = new Vector(eulerX, eulerY, 0);
	}
	
	@Override
	public JSONObject asJSON()
	{
		JSONObject json = new JSONObject();
		json.put("$type", "SavedObjects.SavedWire, Assembly-CSharp");
		
		json.put("InputInput", input2x);
		json.put("length", length);
		
		json.put("LocalPosition", getPosition().asJSON());
		json.put("LocalEulerAngles", direction.asJSON());
		
		json.put("Children", null);
		json.put("CanHaveChildren", false);
		
		return json;
	}
}
