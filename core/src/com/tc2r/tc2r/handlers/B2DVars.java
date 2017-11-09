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
	public static final short BIT_PLAYER = 1 << 1;  // == 2
	public static final short BIT_RED = 1    << 2;  // == 4
	public static final short BIT_GREEN = 1  << 3;  // == 4
	public static final short BIT_BLUE = 1   << 4;  // == 4

}
