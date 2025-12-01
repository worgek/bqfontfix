package bqfontfix;

import net.minecraft.launchwrapper.IClassTransformer;

public class BQFontFixTransformer implements IClassTransformer {

    @Override
        public byte[] transform(String name, String transformedName, byte[] basicClass) {

                // We will add the real font patch later.
                        // For now we just return the class unchanged.
                                return basicClass;
                                    }
                                    }
                                    