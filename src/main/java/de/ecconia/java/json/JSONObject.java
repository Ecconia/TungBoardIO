package de.ecconia.java.json;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class JSONObject extends JSONNode
{
	private final Map<String, Object> entries = new TreeMap<>();
	
	public void put(String key, Object obj)
	{
		entries.put(key, obj);
	}
	
	public Object get(String key)
	{
		return entries.get(key);
	}
	
	public Map<String, Object> getEntries()
	{
		return entries;
	}
	
	@Override
	public String printJSON()
	{
		String tmp = "";
		Iterator<Entry<String, Object>> it = entries.entrySet().iterator();
		
		if(it.hasNext())
		{
			Entry<String, Object> entry = it.next();
			tmp += '"' + entry.getKey() + "\":" + printJSON(entry.getValue());
			
			while(it.hasNext())
			{
				entry = it.next();
				tmp += ",\"" + entry.getKey() + "\":" + printJSON(entry.getValue());
			}
		}
		
		return '{' + tmp + '}';
	}
	
	@Override
	public void debugTree(String prefix)
	{
		//TODO: If first call, missing prefix.
		System.out.println('{');
		String innerPrefix = prefix + "·   ";
		for(Entry<String, Object> entry : entries.entrySet())
		{
			String key = entry.getKey().replace("\n", "\\n");
			Object obj = entry.getValue();
			
			System.out.print(innerPrefix + "\"" + key + "\": ");
			if(obj == null)
			{
				System.out.println("null");
			}
			else if(obj instanceof Boolean)
			{
				System.out.println(obj);
			}
			else if(obj instanceof Number)
			{
				System.out.println(obj);
			}
			else if(obj instanceof String)
			{
				System.out.println("\"" + ((String) obj).replace("\n", "\\n") + "\"");
			}
			else
			{
				JSONNode node = (JSONNode) obj;
				node.debugTree(innerPrefix);
			}
		}
		System.out.println(prefix + "}");
	}
	
	//Getters:
	
	public JSONObject getObject(String key)
	{
		return asObject(entries.get(key));
	}
	
	public JSONArray getArray(String key)
	{
		return asArray(entries.get(key));
	}
	
	public String getString(String key)
	{
		return asString(entries.get(key));
	}
	
	public boolean getBool(String key)
	{
		return asBool(entries.get(key));
	}
	
	public long getLong(String key)
	{
		return asLong(entries.get(key));
	}
	
	public int getInt(String key)
	{
		return asInt(entries.get(key));
	}
	
	public short getShort(String key)
	{
		return asShort(entries.get(key));
	}
	
	public byte getByte(String key)
	{
		return asByte(entries.get(key));
	}
	
	public double getDouble(String key)
	{
		return asDouble(entries.get(key));
	}
	
	public float getFloat(String key)
	{
		return asFloat(entries.get(key));
	}
	
	//With null:
	
	public JSONObject getObjectOrNull(String key)
	{
		Object object = entries.get(key);
		if(object == null)
		{
			return null;
		}
		return asObject(object);
	}
	
	public JSONArray getArrayOrNull(String key)
	{
		Object object = entries.get(key);
		if(object == null)
		{
			return null;
		}
		return asArray(object);
	}
	
	public String getStringOrNull(String key)
	{
		Object object = entries.get(key);
		if(object == null)
		{
			return null;
		}
		return asString(object);
	}
	
	public Boolean getBoolOrNull(String key)
	{
		Object object = entries.get(key);
		if(object == null)
		{
			return null;
		}
		return asBool(object);
	}
	
	public Long getLongOrNull(String key)
	{
		Object object = entries.get(key);
		if(object == null)
		{
			return null;
		}
		return asLong(object);
	}
	
	public Integer getIntOrNull(String key)
	{
		Object object = entries.get(key);
		if(object == null)
		{
			return null;
		}
		return asInt(object);
	}
	
	public Short getShortOrNull(String key)
	{
		Object object = entries.get(key);
		if(object == null)
		{
			return null;
		}
		return asShort(object);
	}
	
	public Byte getByteOrNull(String key)
	{
		Object object = entries.get(key);
		if(object == null)
		{
			return null;
		}
		return asByte(object);
	}
	
	public Double getDoubleOrNull(String key)
	{
		Object object = entries.get(key);
		if(object == null)
		{
			return null;
		}
		return asDouble(object);
	}
	
	public Float getFloatOrNull(String key)
	{
		Object object = entries.get(key);
		if(object == null)
		{
			return null;
		}
		return asFloat(object);
	}
}
