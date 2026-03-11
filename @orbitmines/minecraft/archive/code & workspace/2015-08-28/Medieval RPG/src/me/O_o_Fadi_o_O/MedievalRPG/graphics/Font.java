package me.O_o_Fadi_o_O.MedievalRPG.graphics;

public class Font {

	private static String chars = 
			".,:;'\"!?%()-+/0123456789          " +
			"abcdefghijklmnopqrstuvwxyz        " +
	        "ABCDEFGHIJKLMNOPQRSTUVWXYZ        ";
	
	public static void render(String msg, UpdateScreen screen, int x, int y, int color){
		for(int i = 0; i < msg.length(); i++){
			int charIndex = chars.indexOf(msg.charAt(i));
			
			if(charIndex >= 0){ screen.render(x + (i + 8), y, charIndex + 1 + 29 * 32, color); }
		}
	}
}
