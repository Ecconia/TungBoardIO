package de.ecconia.java.tungboardio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import de.ecconia.java.json.JSONObject;
import de.ecconia.java.tungboardio.stuff.Dir;
import de.ecconia.java.tungboardio.stuff.Exportable;
import de.ecconia.java.tungboardio.stuff.MagicConverter;
import de.ecconia.java.tungboardio.stuff.Vector;
import de.ecconia.java.tungboardio.tc.Blotter;
import de.ecconia.java.tungboardio.tc.Board;
import de.ecconia.java.tungboardio.tc.Inverter;
import de.ecconia.java.tungboardio.tc.Peg;
import de.ecconia.java.tungboardio.tc.ThroughBlotter;
import de.ecconia.java.tungboardio.tc.Wire;

public class TungBoardIO
{
	private static final File tmp = new File("tmp.json");
	private static final File rootFile = new File("/home/ecconia/.config/unity3d/Mouse Hat Games/The Ultimate Nerd Game/savedboards/");
	
	public static void main(String[] args)
	{
		createROM(8, 256);
	}
	
	public static final double sq1B1 = 0.3D;
	public static final double sq1B2 = 0.15D;
	public static final double sq3B4 = 0.225D;
	public static final double sq1B4 = 0.075D;
	
	private static void createROM(int bits, int words)
	{
		Board base = new Board(bits * 2, words);
		
		double twoSquares = sq1B1 + sq1B1;
		//Running from the positive half to the negative one:
		double xRunner1 = bits * sq1B1 - sq1B2;
		double xRunner2 = xRunner1 - sq1B1;
		//Offset for Y: (Half cause every field) (Neg cause going from neg to positive)
		double zStart = (double) words / 2D * -sq1B1 + sq1B2;
		
		Vector holderPosition = new Vector(xRunner1, sq1B1 + sq1B2, 0);
		Vector vHolderPosition = new Vector(xRunner1, sq3B4, zStart - sq1B4);
		Vector inverterPos = new Vector(xRunner1, sq1B1 + sq1B4 + sq1B2, zStart);
		Vector pegPos = new Vector(xRunner2, sq1B4, zStart);
		Vector wireStart = new Vector(xRunner2, sq1B4 + sq1B1 * 0.9D, zStart);
		
		for(int bit = 0; bit < bits; bit++)
		{
			Board vHolder = new Board(1, 1);
			vHolder.setFacing(Dir.PosZ);
			vHolder.setPosition(vHolderPosition);
			base.add(vHolder);
			
			Board holder = new Board(1, words);
			holder.setPosition(holderPosition);
			vHolder.add(holder);
			
			vHolderPosition = vHolderPosition.subtract(new Vector(twoSquares, 0, 0));
			holderPosition = holderPosition.subtract(new Vector(twoSquares, 0, 0));
			
			Vector cip = inverterPos;
			Vector last = wireStart;
			Vector cPegPos = pegPos;
			for(int word = 0; word < words; word++)
			{
				Peg p = new Peg();
				p.setPosition(cPegPos);
				base.add(p);
				cPegPos = cPegPos.add(new Vector(0, 0, sq1B1));
				
				if(word != 0)
				{
					Vector next = last.add(new Vector(0, 0, sq1B1));
					Wire wire = new Wire(last, next, true);
					base.add(wire);
					last = next;
				}
				
				double curZ = zStart + (double) word * sq1B1;
				if(bit < bits - 1)
				{
					Wire wire = new Wire(new Vector(xRunner1, sq1B4 + sq1B1 + sq1B2 + sq1B1 + 0.24D * 0.9D, curZ), new Vector(xRunner1 - twoSquares, sq1B4 + sq1B1 + sq1B2 + sq1B1 + 0.24D * 0.9D, curZ), true);
					holder.add(wire);
				}
				
				if(shallSetBit((bits - bit) - 1, word))
				{
					Wire wire = new Wire(new Vector(xRunner2 + sq1B2 - 0.12D * 0.9D, sq1B4 + sq1B1 + sq1B2 + sq1B2, curZ), new Vector(xRunner2, sq1B4 + sq1B1 * 0.9D, curZ), false);
					base.add(wire);
				}
				
				Inverter inverter = new Inverter(true);
				inverter.setPosition(cip);
				inverter.setInputFacing(Dir.PosY);
				inverter.setOutputFacing(Dir.NegX);
				holder.add(inverter);
				cip = cip.add(new Vector(0, 0, sq1B1));
			}
			
			inverterPos = inverterPos.subtract(new Vector(twoSquares, 0, 0));
			pegPos = pegPos.subtract(new Vector(twoSquares, 0, 0));
			wireStart = wireStart.subtract(new Vector(twoSquares, 0, 0));
			
			xRunner1 -= twoSquares;
			xRunner2 -= twoSquares;
		}
		
		Board topHolder = new Board(5, 1);
		topHolder.setFacing(Dir.PosX);
		topHolder.setPosition(new Vector((double) bits * sq1B1 - sq1B2 + sq3B4, 2.5D * sq1B1 - sq1B4, zStart));
		base.add(topHolder);
		
		double topPaneHeight = 4 * sq1B1 + sq3B4;
		
		Board top = new Board(bits * 2, words + 2);
		top.setPosition(new Vector(0, topPaneHeight, -sq1B1));
		topHolder.add(top);
		
		Vector blStart = new Vector(bits * sq1B1 - sq1B2, topPaneHeight, zStart);
		Vector inStart = new Vector(bits * sq1B1 - sq1B2, topPaneHeight + sq1B4, zStart - 2 * sq1B1);
		boolean isNotNegated = false;
		for(int bit = 0; bit < bits * 2; bit++)
		{
			Vector wireLast = blStart.add(new Vector(0, sq1B2 + sq1B1 * 0.9D, 0));
			
			//Place Inputs:
			if(isNotNegated)
			{
				//Blotter:
				Blotter in = new Blotter(false);
				in.setFacing(Dir.PosZ);
				in.setPosition(inStart.add(new Vector(0, sq1B2, 0)));
				top.add(in);
			}
			else
			{
				//Inverter:
				Inverter in = new Inverter(true);
				in.setInputFacing(Dir.PosY);
				in.setOutputFacing(Dir.PosZ);
				in.setPosition(inStart);
				top.add(in);
				
				Wire conWire = new Wire(
						inStart.add(new Vector(0, sq1B1 + 0.24D * 0.9D, 0)),
						inStart.add(new Vector(-sq1B1, sq1B2, -(sq1B2 + 0.24D * 0.9D))), true);
				top.add(conWire);
			}
			
			Wire wire1 = new Wire(wireLast, inStart.add(new Vector(0, sq1B2, sq1B2 + 0.12D * 0.9D)), false);
			top.add(wire1);
			
			//Place and connect blotters
			Vector cBlPos = blStart;
			for(int word = 0; word < words; word++)
			{
				ThroughBlotter bl = new ThroughBlotter(false);
				bl.setFacing(Dir.NegY);
				bl.setPosition(cBlPos);
				top.add(bl);
				
				if(word != 0)
				{
					Vector wireNext = wireLast.add(new Vector(0, 0, sq1B1));
					Wire wire = new Wire(wireLast, wireNext, true);
					top.add(wire);
					wireLast = wireNext;
				}
				
				Vector start = cBlPos.subtract(new Vector(0, sq1B2 + 0.108D, 0));
				Vector end = cBlPos.subtract(new Vector(0, 2 * sq1B1 - 0.24D * 0.9D, 0));
				if(isNotNegated)
				{
					end = end.add(new Vector(sq1B1, 0, 0));
				}
				
				if(((word & (1 << (bits - (bit / 2) - 1))) != 0) != isNotNegated)
				{
					Wire wire = new Wire(start, end, false);
					top.add(wire);
				}
				
				cBlPos = cBlPos.add(new Vector(0, 0, sq1B1));
			}
			inStart = inStart.add(new Vector(-sq1B1, 0, 0));
			blStart = blStart.add(new Vector(-sq1B1, 0, 0));
			isNotNegated = !isNotNegated;
		}
		
		base.resolve(new MagicConverter());
		exportBoard(base);
	}
	
	private static boolean shallSetBit(int bit, int address)
	{
		return (address & (1 << bit)) != 0;
	}
	
	private static void exportBoard(Exportable board)
	{
		JSONObject baseJson = board.asJSON();
		baseJson.getEntries().remove("$type"); //The root component does not have this thing.
		
		System.out.println("##########################################");
		System.out.println("Gen JSON:");
		System.out.println("##########################################");
		
		String jsonText = baseJson.printJSON();
		
		System.out.println("##########################################");
		System.out.println("Got JSON:");
		System.out.println("##########################################");
//		System.out.println("JSON-Debug: " + jsonText);
		try
		{
			FileWriter writer = new FileWriter(tmp, false);
			writer.write(jsonText);
			writer.close();
			
			File exportFile = new File(rootFile, "0");
			ProcessBuilder pb = new ProcessBuilder(new String[]
			{
					"mono", "/home/ecconia/.config/unity3d/Mouse Hat Games/The Ultimate Nerd Game/B2J/BoardToJson/BoardToJson.exe", "-v", "-i", "-o", exportFile.getAbsolutePath() + ".tungboard", tmp.getAbsolutePath()
			});
			pb.directory(rootFile); //Safety.
			System.out.println("Directory: " + pb.directory().getAbsolutePath());
			
			Process p = pb.start();
			
			Thread t = new Thread(() -> {
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String in;
				try
				{
					while((in = reader.readLine()) != null)
					{
						System.out.println("Out: " + in);
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			});
			t.start();
			
			t = new Thread(() -> {
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				String in;
				try
				{
					while((in = reader.readLine()) != null)
					{
						System.out.println("Err: " + in);
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			});
			t.start();
			
			try
			{
				System.out.println("Exit value: " + p.waitFor());
			}
			catch(InterruptedException e)
			{
				System.err.println("Something interrupted the execution listener of the export. No result value can be printed.");
				if(e.getMessage() != null)
				{
					System.err.println("Message: " + e.getMessage());
				}
				System.exit(1);
			}
		}
		catch(IOException e)
		{
			System.err.println("Could not generate the tungboard from json, please check stacktrace:");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void export(Board board, String boardName)
	{
		board.resolve();
		
		JSONObject baseJson = board.asJSON();
		baseJson.getEntries().remove("$type"); //The root component does not have this thing.
		
		String jsonText = baseJson.printJSON();
		try
		{
			FileWriter writer = new FileWriter(tmp, false);
			writer.write(jsonText);
			writer.close();
			
			File exportFile = new File(rootFile, boardName);
			ProcessBuilder pb = new ProcessBuilder(new String[]
			{
					"mono", "/home/ecconia/.config/unity3d/Mouse Hat Games/The Ultimate Nerd Game/B2J/BoardToJson/BoardToJson.exe", "-v", "-i", "-o", exportFile.getAbsolutePath() + ".tungboard", tmp.getAbsolutePath()
			});
			pb.directory(rootFile); //Safety.
			System.out.println("Directory: " + pb.directory().getAbsolutePath());
			
			Process p = pb.start();
			
			Thread t = new Thread(() -> {
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String in;
				try
				{
					while((in = reader.readLine()) != null)
					{
						System.out.println("Out: " + in);
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			});
			t.start();
			
			t = new Thread(() -> {
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				String in;
				try
				{
					while((in = reader.readLine()) != null)
					{
						System.out.println("Err: " + in);
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			});
			t.start();
			
			try
			{
				System.out.println("Exit value: " + p.waitFor());
			}
			catch(InterruptedException e)
			{
				System.err.println("Something interrupted the execution listener of the export. No result value can be printed.");
				if(e.getMessage() != null)
				{
					System.err.println("Message: " + e.getMessage());
				}
				System.exit(1);
			}
		}
		catch(IOException e)
		{
			System.err.println("Could not generate the tungboard from json, please check stacktrace:");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
