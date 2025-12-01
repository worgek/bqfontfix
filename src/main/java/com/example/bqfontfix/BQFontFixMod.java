package com.example.bqfontfix;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import java.lang.reflect.Field;

@Mod(modid = BQFontFixMod.MODID, name = BQFontFixMod.NAME, version = BQFontFixMod.VERSION, clientSideOnly = true, acceptableRemoteVersions="*")
public class BQFontFixMod {
    public static final String MODID = "bqfontfix";
    public static final String NAME = "BetterQuesting Vanilla Font Fix";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        try {
            applyFix();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void applyFix() {
        try {
            Class<?> themeRegistryCls = Class.forName("betterquesting.client.themes.ThemeRegistry");

            Field[] fields = themeRegistryCls.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getType().getName().equals("net.minecraft.client.gui.FontRenderer")) {
                    f.set(null, Minecraft.getMinecraft().fontRenderer);
                }
            }

        } catch (Throwable ignored) {}
    }
}
