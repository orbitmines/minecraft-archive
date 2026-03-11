public class ServerSelectorInv {
 
  public static Inventory serverSelector = Bukkit.createInventory(null, 27, "&0&lServer Selector");
  
  public static int i = 0;
  
  public void setAllServerSelectorItems(){
    
    setServerSelectorItem("kitpvp", "&c&lKitPvP &7- &61.7", Material.IRON_SWORD, 10);
    setServerSelectorItem("prison", "&4&lPrison &7- &61.7"
    
  }
  
  public void setServerSelectorItem(String server, String displayName, Material m, int slot){
    int Players = getPlayersOnline(server);
    
  	ItemStack item = new ItemStack(m, 1);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(displayName);
    List<String> lore = new ArrayList<String>();
    lore.add("")
    if(isServerOnline(Players)){
    	lore.add(" &7Status: &a&lOnline ");j
         ?
        lore.add(" &7Players: &a&l" + Players + "&7/&a&l100 ");
    }
    else{
    	lore.add(" &7Status: &c&lOffline ");
    }
    lore.add("");
    if(isServerOnline(Players)){
    	if(i == 0){
    		lore.add(" &7&oClick Here to Connect");
    	}
    	else{
       		lore.add(" &7&o&nClick Here to Connect");
    	}
    	lore.add("");
    }
    meta.setLore(lore);
    item.setItemMeta(meta);
      
      inv.setItem(slot, item);
    
  }
  
  public boolean isServerOnline(int PlayersOnline){
    
  	if(PlayersOnline == -1){
      return false;
    }
    else{
      return true;
    }
  }
             
  public int getPlayersOnline(String server){
    
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(b);
    
    out.writeUTF("PlayerCount");
    out.writeUTF(server);
    
    try{
    	return b.toByteArray();
    }catch(Exception ex){
        return -1;
    }
  }
}