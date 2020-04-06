package de.ecconia.java.tungboardio.stuff;

public class MagicConverter
{
	private final MagicConverter parent;
	
	private Dir xFace;
	private Dir yFace;
	private Dir zFace;
	
	private Tunnel xTunnelIn;
	private Tunnel yTunnelIn;
	private Tunnel zTunnelIn;
	
	private Tunnel xTunnelOut;
	private Tunnel yTunnelOut;
	private Tunnel zTunnelOut;
	
	private Vector globalPos;
	private Vector localPosition;
	private Vector eulerRotation;
	
	public Vector getEulerRotation()
	{
		return eulerRotation;
	}
	
	public Vector getLocalPosition()
	{
		return localPosition;
	}
	
	public MagicConverter()
	{
		parent = null;
		globalPos = new Vector();
		localPosition = null;
		eulerRotation = new Vector();
		xFace = Dir.PosX;
		yFace = Dir.PosY;
		zFace = Dir.PosZ;
		setupTunnels();
	}
	
	private MagicConverter(MagicConverter parent, Vector globalPos, Vector localPoint, Vector eulerRotation, Dir xFace, Dir yFace, Dir zFace)
	{
		this.parent = parent;
		
		this.globalPos = globalPos;
		this.localPosition = localPoint;
		this.eulerRotation = eulerRotation;
		
		this.xFace = xFace;
		this.yFace = yFace;
		this.zFace = zFace;
		
		setupTunnels();
	}
	
	public MagicConverter spawn(Vector classicFixPoint, Vector globalPosition, Dir globalPosYFace)
	{
		Dir x = null;
		Dir y = null;
		Dir z = null;
		
		Dir localPosYFace = convertAbsoluteDirection(globalPosYFace);
		
		Vector rotatedFixPoint = null;
		Vector eulerRotation = null;
		if(localPosYFace == Dir.PosY || localPosYFace == Dir.NegY)
		{
			//No rotation required. Also if it was flipped, ignore same effect - the user doesn't need to care.
			rotatedFixPoint = new Vector(classicFixPoint.getX(), 0, classicFixPoint.getZ());
			eulerRotation = new Vector(0, 0, 0);
			
			x = convertAbsoluteDirection(Dir.PosX);
			y = convertAbsoluteDirection(Dir.PosY);
			z = convertAbsoluteDirection(Dir.PosZ);
		}
		else if(localPosYFace == Dir.PosX || localPosYFace == Dir.NegX)
		{
			//Rotate around Z axis. If flipped, ignore.
			rotatedFixPoint = new Vector(0, -classicFixPoint.getX(), classicFixPoint.getZ());
			eulerRotation = new Vector(0, 0, -90);
			x = convertAbsoluteDirection(Dir.PosY);
			y = convertAbsoluteDirection(Dir.NegX);
			z = convertAbsoluteDirection(Dir.PosZ);
		}
		else if(localPosYFace == Dir.PosZ || localPosYFace == Dir.NegZ)
		{
			//Rotate around X axis. If flipped, ignore.
			rotatedFixPoint = new Vector(classicFixPoint.getX(), -classicFixPoint.getZ(), 0);
			eulerRotation = new Vector(90, 0, 0);
			x = convertAbsoluteDirection(Dir.PosX);
			y = convertAbsoluteDirection(Dir.NegZ);
			z = convertAbsoluteDirection(Dir.PosY);
		}
		
		Vector absoluteRotatedFixPoint = rotateLocalPosition(rotatedFixPoint);
		Vector globalPos = globalPosition.add(absoluteRotatedFixPoint);
		
		Vector localPos = globalPos;
		if(parent != null)
		{
			localPos = convertAbsolutePosition(localPos);
		}
		
		return new MagicConverter(this, globalPos, localPos, eulerRotation, x, y, z);
	}
	
	private void setupTunnels()
	{
		xTunnelIn = xFace.isPositive() ? new PosTunnel() : new NegTunnel();
		yTunnelIn = yFace.isPositive() ? new PosTunnel() : new NegTunnel();
		zTunnelIn = zFace.isPositive() ? new PosTunnel() : new NegTunnel();
		
		if(xFace == Dir.PosX || xFace == Dir.NegX)
		{
			xTunnelOut = xTunnelIn;
		}
		else if(xFace == Dir.PosY || xFace == Dir.NegY)
		{
			xTunnelOut = yTunnelIn;
		}
		else
		{
			xTunnelOut = zTunnelIn;
		}
		
		if(yFace == Dir.PosX || yFace == Dir.NegX)
		{
			yTunnelOut = xTunnelIn;
		}
		else if(yFace == Dir.PosY || yFace == Dir.NegY)
		{
			yTunnelOut = yTunnelIn;
		}
		else
		{
			yTunnelOut = zTunnelIn;
		}
		
		if(zFace == Dir.PosX || zFace == Dir.NegX)
		{
			zTunnelOut = xTunnelIn;
		}
		else if(zFace == Dir.PosY || zFace == Dir.NegY)
		{
			zTunnelOut = yTunnelIn;
		}
		else
		{
			zTunnelOut = zTunnelIn;
		}
	}
	
	public Vector rotateLocalPosition(Vector in)
	{
		xTunnelOut.in(in.getX());
		yTunnelOut.in(in.getY());
		zTunnelOut.in(in.getZ());
		
		return new Vector(xTunnelIn.out(), yTunnelIn.out(), zTunnelIn.out());
	}
	
	public Vector convertAbsolutePosition(Vector absIn)
	{
		Vector diff = absIn.subtract(globalPos);
		xTunnelIn.in(diff.getX());
		yTunnelIn.in(diff.getY());
		zTunnelIn.in(diff.getZ());
		
		return new Vector(xTunnelOut.out(), yTunnelOut.out(), zTunnelOut.out());
	}
	
	public Vector rotateAbsolutePosition(Vector direction)
	{
		xTunnelIn.in(direction.getX());
		yTunnelIn.in(direction.getY());
		zTunnelIn.in(direction.getZ());
		
		return new Vector(xTunnelOut.out(), yTunnelOut.out(), zTunnelOut.out());
	}
	
	public Dir convertAbsoluteDirection(Dir direction)
	{
		Vector v = direction.getVector();
		xTunnelIn.in(v.getX());
		yTunnelIn.in(v.getY());
		zTunnelIn.in(v.getZ());
		
		return Dir.fromVector(new Vector(xTunnelOut.out(), yTunnelOut.out(), zTunnelOut.out()));
	}
	
	private static abstract class Tunnel
	{
		private double value;
		
		public void in(double value)
		{
			this.value = value;
		}
		
		public double out()
		{
			return value;
		}
	}
	
	private static class PosTunnel extends Tunnel
	{
	}
	
	private static class NegTunnel extends Tunnel
	{
		@Override
		public void in(double value)
		{
			super.in(-value);
		}
	}
}
