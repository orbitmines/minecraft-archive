package om.kitpvp.utils;

import java.util.ArrayList;
import java.util.List;

import om.kitpvp.handlers.KitPvPMap;

public class KitPvPUtils {
	
	public static List<KitPvPMap> asNewMapList(List<KitPvPMap> list){
		List<KitPvPMap> newlist = new ArrayList<KitPvPMap>();
		for(KitPvPMap o : list){
			newlist.add(o);
		}
		return newlist;
	}
}

