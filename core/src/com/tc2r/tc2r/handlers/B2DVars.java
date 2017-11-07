package com.tc2r.tc2r.handlers;

/**
 * Created by Tc2r on 11/7/2017.
 * <p>
 * Description:
 */
public class B2DVars {

	// Pixels per meter ratio
	public static final float PPM = 100;

	// category bits
	public static final short BIT_NOTHING = 0;       // collides with nothing.
	public static final short BIT_DEFAULT = 1;       // automatic default if unset
	public static final short BIT_GROUND = 1  << 1;  // == 2
	public static final short BIT_BOX = 1     << 2;  // == 4
	public static final short BIT_BALL = 1    << 3;  // == 8
	public static final short BIT_VEGGIES = 1 << 4;  // == 16
}
