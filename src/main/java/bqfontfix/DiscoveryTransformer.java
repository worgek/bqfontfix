package bqfontfix;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class DiscoveryTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {

        if (name == null || basicClass == null) return basicClass;

        String n = name.toLowerCase();

        // Filter 1: BetterQuesting namespace
        if (n.contains("betterquesting")) {
            FMLLog.log.info("[BQFind] BQ: " + name + " -> " + transformedName);
        }

        // Filter 2: panel-related classes
        if (n.contains("panel")) {
            FMLLog.log.info("[BQFind] PANEL: " + name + " -> " + transformedName);
        }

        // Filter 3: text-related classes
        if (n.contains("text")) {
            FMLLog.log.info("[BQFind] TEXT: " + name + " -> " + transformedName);
        }

        return basicClass;
    }
}
