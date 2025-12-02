package bqfontfix;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;

public class DiscoveryTransformer implements IClassTransformer {

    @Override
        public byte[] transform(String name, String transformedName, byte[] basicClass) {

                if (name == null || basicClass == null) return basicClass;

                        // Look for anything that could be FontRenderer
                                if (name.contains("Font") || name.contains("font")) {
                                            FMLLog.log.info("[BQFontFix] FONT-LIKE: " + name + "  ->  " + transformedName);
                                                    }

                                                            // Also look for GUI classes (font is often inside gui package)
                                                                    if (name.startsWith("net.minecraft.client.gui")) {
                                                                                FMLLog.log.info("[BQFontFix] GUI: " + name + "  ->  " + transformedName);
                                                                                        }

                                                                                                // Look for short obfuscated classes that might be vanilla
                                                                                                        if (name.length() <= 4 && !name.contains("/")) {
                                                                                                                    FMLLog.log.info("[BQFontFix] SMALL CLASS: " + name + "  ->  " + transformedName);
                                                                                                                            }

                                                                                                                                    return basicClass;
                                                                                                                                        }
                                                                                                                                        }
                                                                                                                                        