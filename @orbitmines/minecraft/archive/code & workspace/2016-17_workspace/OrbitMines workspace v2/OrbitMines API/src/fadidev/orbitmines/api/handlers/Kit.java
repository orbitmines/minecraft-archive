package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class Kit {

	private static OrbitMinesAPI api;
	private String name;
	private ItemStack[] armorContents;
	private ItemStack[] contents;
	private ItemStack itemOffHand;
	private List<List<ItemStack>> randomItems;
	
	private PotionEffect potionEffect;
	
	public Kit(String name){
		api = OrbitMinesAPI.getApi();
		this.name = name;
		this.armorContents = new ItemStack[4];
		this.contents = new ItemStack[36];
		this.randomItems = new ArrayList<>();
		for(int i = 0; i < 36; i++){
			this.randomItems.add(new ArrayList<ItemStack>());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemStack getHelmet() {
		return this.armorContents[3];
	}

	public void setHelmet(ItemStack helmet) {
		this.armorContents[3] = helmet;
	}

	public ItemStack getChestplate() {
		return this.armorContents[2];
	}

	public void setChestplate(ItemStack chestplate) {
		this.armorContents[2] = chestplate;
	}

	public ItemStack getLeggings() {
		return this.armorContents[1];
	}

	public void setLeggings(ItemStack leggings) {
		this.armorContents[1] = leggings;
	}

	public ItemStack getBoots() {
		return this.armorContents[0];
	}

	public void setBoots(ItemStack boots) {
		this.armorContents[0] = boots;
	}
	
	public ItemStack[] getArmorContents() {
		return armorContents;
	}

	public void setArmorContents(ItemStack[] armorContents) {
		this.armorContents = armorContents;
	}

	public ItemStack[] getContents() {
		return contents;
	}

	public void setContents(ItemStack[] contents) {
		this.contents = contents;
	}
	
	public void setItem(int index, ItemStack content) {
		this.contents[index] = content;
	}

	public ItemStack getItem(int index) {
		return this.contents[index];
	}

    public ItemStack getItemOffHand() {
        return itemOffHand;
    }

    public void setItemOffHand(ItemStack itemOffHand) {
        this.itemOffHand = itemOffHand;
    }

    public ItemStack getFirstItem(){
		for(ItemStack item : this.contents){
			if(item != null)
				return item;
		}
		return null;
	}

	public List<List<ItemStack>> getRandomItems() {
		return randomItems;
	}

	public void setRandomItems(List<List<ItemStack>> randomItems) {
		this.randomItems = randomItems;
	}
	
	public void setRandomItem(int index, List<ItemStack> content) {
		this.randomItems.set(index, content);
	}

	public List<ItemStack> getRandomItem(int index) {
		return this.randomItems.get(index);
	}
	
	public int contentItems(){
		int amount = 0;
		for(ItemStack item : getContents()){
			if(item != null)
				amount++;
		}
		return amount;
	}

	public PotionEffect getPotionEffect() {
		return potionEffect;
	}

	public void setPotionEffect(PotionEffect potionEffect) {
		this.potionEffect = potionEffect;
	}
	
	public void setItems(Player p){
		for(ItemStack item : getContents()){
			if(item != null && item.getType() == Material.SKULL_ITEM && item.getDurability() == (short) 3){
				SkullMeta meta = (SkullMeta) item.getItemMeta();
				meta.setOwner(p.getName());
				item.setItemMeta(meta);
			}
		}

        ItemStack[] armorContents = new ItemStack[4];
        int index = 0;
        for(ItemStack item : getArmorContents()){
            if(item != null)
                armorContents[index] = item;
            else
                armorContents[index] = p.getInventory().getArmorContents()[index];

            index++;
        }
        p.getInventory().setArmorContents(armorContents);

        index = 0;
		for(ItemStack item : getContents()){
            if(item != null)
                p.getInventory().setItem(index, item);

            index++;
        }

        p.getInventory().setItemInOffHand(getItemOffHand());
		
		index = 0;
		for(List<ItemStack> items : getRandomItems()){
			if(items != null && items.size() > 0)
				p.getInventory().setItem(index, items.get(Utils.RANDOM.nextInt(items.size())));

			index++;
		}

		if(getPotionEffect() != null)
			p.addPotionEffect(getPotionEffect());
	}

    public void replaceItems(Player p){
        for(ItemStack item : getContents()){
            if(item != null && item.getType() == Material.SKULL_ITEM && item.getDurability() == (short) 3){
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                meta.setOwner(p.getName());
                item.setItemMeta(meta);
            }
        }

        ItemStack[] armorContents = new ItemStack[4];
        int index = 0;
        for(ItemStack item : getArmorContents()){
            if(item != null && setItem(p.getInventory().getArmorContents(), index, item))
                armorContents[index] = item;
            else
                armorContents[index] = p.getInventory().getArmorContents()[index];

            index++;
        }
        p.getInventory().setArmorContents(armorContents);

        index = 0;
        for(ItemStack item : getContents()){
            if(item != null && setItem(p.getInventory().getContents(), index, item))
                p.getInventory().setItem(index, item);

            index++;
        }

        index = 0;
        for(List<ItemStack> items : getRandomItems()){
            if(items != null && items.size() > 0)
                p.getInventory().setItem(index, items.get(Utils.RANDOM.nextInt(items.size())));

            index++;
        }

        if(getItemOffHand() != null && setItem(getItemOffHand(), p.getInventory().getItemInOffHand()))
            p.getInventory().setItemInOffHand(getItemOffHand());

        if(getPotionEffect() != null)
            p.addPotionEffect(getPotionEffect());
    }

    private boolean setItem(ItemStack item, ItemStack item2){
        if(item2 == null)
            return true;

        return item.getType() != item2.getType() || item.getAmount() != item2.getAmount() || !item.getItemMeta().getDisplayName().equals(item2.getItemMeta().getDisplayName());
    }

	private boolean setItem(ItemStack[] contents, int index, ItemStack item){
        ItemStack item2 = contents[index];

        if(item2 == null)
            return true;

        return item.getType() != item2.getType() || item.getAmount() != item2.getAmount() || !item.getItemMeta().getDisplayName().equals(item2.getItemMeta().getDisplayName());
    }
	
	public void addItems(Player p){
		for(ItemStack item : getContents()){
			if(item != null)
				p.getInventory().addItem(item);
		}
		int index = 0;
		for(ItemStack item : getArmorContents()){
			ItemStack item2 = p.getInventory().getArmorContents()[index];
			
			if(item2 != null)
				p.getInventory().addItem(item2);
			
			if(index == 0)
				p.getInventory().setBoots(item);
			else if(index == 1)
				p.getInventory().setLeggings(item);
			else if(index == 2)
				p.getInventory().setChestplate(item);
			else
				p.getInventory().setHelmet(item);
			
			index++;
		}

		if(p.getInventory().getItemInOffHand() == null)
		    p.getInventory().setItemInOffHand(getItemOffHand());
        else
            p.getInventory().addItem(getItemOffHand());
		
		if(getPotionEffect() != null)
			p.addPotionEffect(getPotionEffect());
	}
	
	public static Kit getKit(String name){
		for(Kit kit : api.getKits()){
			if(kit.getName().equals(name)){
				return kit;
			}
		}
		return null;
	}
	
	public void saveToConfig(FileConfiguration file, String path){
		int index = 0;
		for(ItemStack item : getArmorContents()){
			if(item != null){
				file.set(path + ".ArmorContents." + index, ConfigUtils.parseString(item));
			}
			index++;
		}
		index = 0;
		for(ItemStack item : getContents()){
			if(item != null){
				file.set(path + ".Contents." + index, ConfigUtils.parseString(item));
			}
			index++;
		}
	}
	
	public void saveToConfig(FileConfiguration file, String path, Material... bannedMaterials){
		int index = 0;
		for(ItemStack item : getArmorContents()){
			boolean clear = false;
			
			if(item != null){
				for(Material bannedMaterial : bannedMaterials){
					if(item.getType() == bannedMaterial){
						clear = true;
					}
				}
				
				if(!clear){
					file.set(path + ".ArmorContents." + index, ConfigUtils.parseString(item));
				}
			}
			else{
				clear = true;
			}
			
			if(clear){
				file.set(path + ".ArmorContents." + index, null);
			}
			index++;
		}
		index = 0;
		for(ItemStack item : getContents()){
			boolean clear = false;
			
			if(item != null){
				for(Material bannedMaterial : bannedMaterials){
					if(item.getType() == bannedMaterial){
						clear = true;
					}
				}
				
				if(!clear){
					file.set(path + ".Contents." + index, ConfigUtils.parseString(item));
				}
			}
			else{
				clear = true;
			}
			
			if(clear){
				file.set(path + ".Contents." + index, null);
			}
			index++;
		}
	}
	
	public static Kit getKitFromConfig(String name, FileConfiguration file, String path){
		Kit kit = new Kit(name);
		for(String stringIndex : file.getConfigurationSection(path + ".ArmorContents").getKeys(false)){
			kit.getArmorContents()[Integer.parseInt(stringIndex)] = ConfigUtils.parseItemStack(file.getString(path + ".ArmorContents." + stringIndex));
		}
		for(String stringIndex : file.getConfigurationSection(path + ".Contents").getKeys(false)){
			kit.getContents()[Integer.parseInt(stringIndex)] = ConfigUtils.parseItemStack(file.getString(path + ".Contents." + stringIndex));
		}
		
		return kit;
	}
}
