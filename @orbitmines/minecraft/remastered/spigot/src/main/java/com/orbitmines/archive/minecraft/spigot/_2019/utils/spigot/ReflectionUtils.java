package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

import com.mojang.brigadier.CommandDispatcher;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class ReflectionUtils {

    private static String version;

    static {
        version = SpigotServer.getInstance().getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static void setMaxCapacity(int maxCapacity) {
        try {
            Object server = getOBCClass("CraftServer").getMethod("getHandle").invoke(Bukkit.getServer());
            getDeclaredField(server.getClass().getSuperclass(), "maxPlayers").set(server, maxCapacity);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    public static CommandDispatcher getCommandDispatcher(boolean vanilla) {
        Server bukkitServer = Bukkit.getServer();
        try {
            Object server = bukkitServer.getClass().getDeclaredMethod("getServer").invoke(bukkitServer);
            Object dispatcher = getDeclaredField(getNMSClass("MinecraftServer"), vanilla ? "vanillaCommandDispatcher" : "commandDispatcher").get(server);

            return (CommandDispatcher) getNMSClass("CommandDispatcher").getDeclaredMethod("a").invoke(dispatcher);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String serializeItemStack(ItemStack item) {
        Method asNMSCopyMethod = getDeclaredMethod(getOBCClass("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class);

        Class<?> nbtTagCompoundClass = getNMSClass("NBTTagCompound");
        Method saveNmsItemStackMethod = getDeclaredMethod(getNMSClass("ItemStack"), "save", nbtTagCompoundClass);

        try {
            return saveNmsItemStackMethod.invoke(asNMSCopyMethod.invoke(null, item), nbtTagCompoundClass.newInstance()).toString();
        } catch (Exception ex) {
            return null;
        }
    }

    // TODO:
//    public static GameProfile getGameProfile(String texture) {
//        GameProfile newSkinProfile = new GameProfile(UUID.randomUUID(), null);
//        newSkinProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + texture + "\"}}}")));
//        return newSkinProfile;//https://bukkit.org/threads/how-to-get-the-skull-with-texture-as-itemstack-correctly-alex-head-error.461178/
//    }

    public static Object getDeclaredObject(String fieldName, Class<?> clazz, Object object) {
        Field field;
        Object o = null;

        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return o;
    }

    public static Field getDeclaredField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getDeclaredMethod(Class<?> clazz, String method, Class<?>... types) {
        try {
            Method m = clazz.getDeclaredMethod(method, types);
            m.setAccessible(true);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getNMSClassName(String name) {
        return "net.minecraft.server." + version + "." + name;
    }

    public static Class<?> getOBCClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getCMAClass(String name) {
        try {
            return Class.forName("com.mojang.authlib." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
