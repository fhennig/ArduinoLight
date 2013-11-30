package arduino;

/**
 * Diese Klasse dient dazu, die konkreten byte-Werte der Flags zu kapseln.
 * Enthält Konstanten zu den Flags.
 */
public class AmbloneFlags //enum als bytearray
{
	public static final byte STARTFLAG1 = 1;
	public static final byte STARTFLAG2 = 2;
	public static final byte STARTFLAG3 = 3;
	public static final byte STARTFLAG4 = 4;
	public static final byte ENDFLAG = 3;
	public static final byte ESCFLAG = 6;
}
