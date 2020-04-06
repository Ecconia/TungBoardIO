package de.ecconia.java;

import de.ecconia.java.tungboardio.S;
import de.ecconia.java.tungboardio.TungBoardIO;
import de.ecconia.java.tungboardio.stuff.Dir;
import de.ecconia.java.tungboardio.stuff.Vector;
import de.ecconia.java.tungboardio.tc.Blotter;
import de.ecconia.java.tungboardio.tc.Board;
import de.ecconia.java.tungboardio.tc.Inverter;

public class Incrementer16b
{
	public static final int bits = 16;
	public static final int width = bits * 2;
	public static final int depth = 6;
	
	public static void main(String[] args)
	{
		Board board = new Board(width, depth);
		
		for(int bit = 0; bit < bits; bit++)
		{
			Inverter inverter = new Inverter(false);
			inverter.setPosition(new Vector(undoPositioningX(bit, false), S.QUAD, undoPositioningZ(3)));
			inverter.setInputFacing(Dir.PosY);
			inverter.setOutputFacing(Dir.PosZ);
			board.add(inverter);
			
			Blotter blotter = new Blotter(true);
			blotter.setPosition(new Vector(undoPositioningX(bit, false), S.QUAD + S.HALF, undoPositioningZ(7)));
			blotter.setFacing(Dir.PosZ);
			board.add(blotter);
		}
		
		TungBoardIO.export(board, "AutoIncrementer16bit");
	}
	
	public static double undoPositioningZ(int z)
	{
		double halfDepth = -((double) depth / 2D * S.FULL);
		return halfDepth + S.HALF + (double) z * S.FULL;
	}

	public static double undoPositioningX(int x, boolean secondary)
	{
		double halfWidth = -((double) width / 2D * S.FULL);
		if(secondary)
		{
			halfWidth += S.FULL;
		}
		return halfWidth + S.HALF + (double) x * 2D * S.FULL;
	}
}
