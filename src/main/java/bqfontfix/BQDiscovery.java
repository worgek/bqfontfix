package bqfontfix;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class BQDiscovery implements IClassTransformer {

    @Override
        public byte[] transform(String name, String transformedName, byte[] basicClass) {

                if (name == null || basicClass == null)
                            return basicClass;

                                    // Only scan BetterQuesting classes
                                            if (name.startsWith("betterquesting") || name.contains("betterquesting")) {
                                                        FMLLog.log.info("[BQFontFix][DISCOVER] BQ CLASS: " + name + " -> " + transformedName);
                                                                }

                                                                        return basicClass;
                                                                            }
                                                                            }
                                                                            