package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

import com.mojang.brigadier.CommandDispatcher;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class ReflectionUtils {

    private static String version;

    static {
        String[] parts = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
        version = parts.length > 3 ? parts[3] : null;
    }

    public static void setMaxCapacity(int maxCapacity) {
        Bukkit.getServer().setMaxPlayers(maxCapacity);
    }

    public static CommandDispatcher getCommandDispatcher(boolean vanilla) {
        Server bukkitServer = Bukkit.getServer();
        try {
            Object server = bukkitServer.getClass().getDeclaredMethod("getServer").invoke(bukkitServer);
            Class<?> commandsClass = Class.forName("net.minecraft.commands.Commands");

            Object commands;
            if (vanilla) {
                commands = getDeclaredField(server.getClass(), "vanillaCommandDispatcher").get(server);
            } else {
                commands = server.getClass().getMethod("getCommands").invoke(server);
            }

            return (CommandDispatcher) commandsClass.getMethod("getDispatcher").invoke(commands);
        } catch (Exception ex) {
            ex.printStackTrace();
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
            return Class.forName(getNMSClassName(name));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getNMSClassName(String name) {
        if (version == null)
            return "net.minecraft.server." + name;
        return "net.minecraft.server." + version + "." + name;
    }

    public static Class<?> getOBCClass(String name) {
        try {
            String className = version == null
                    ? "org.bukkit.craftbukkit." + name
                    : "org.bukkit.craftbukkit." + version + "." + name;
            return Class.forName(className);
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
