package de.ecconia.java.tungboardio.tc;

import de.ecconia.java.json.JSONObject;
import de.ecconia.java.tungboardio.stuff.MagicConverter;
import de.ecconia.java.tungboardio.stuff.Vector;

public class DefaultPeg extends Component
{
	MagicConverter pmc;
	
	public DefaultPeg()
	{
	}
	
	@Override
	public void resolve(MagicConverter pmc)
	{
		this.pmc = pmc;
	}
	
	@Override
	public JSONObject asJSON()
	{
		JSONObject json = new JSONObject();
		json.put("$type", "SavedObjects.SavedPeg, Assembly-CSharp");
		
		json.put("LocalPosition", getPosition().asJSON());
		json.put("LocalEulerAngles", new Vector().asJSON());
		
		json.put("Children", null);
		json.put("CanHaveChildren", false);
		
		return json;
	}
}
