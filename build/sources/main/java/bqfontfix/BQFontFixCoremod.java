package bqfontfix;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("BQFontFix")
@IFMLLoadingPlugin.MCVersion("1.12.2")
public class BQFontFixCoremod implements IFMLLoadingPlugin {

    @Override
        public String[] getASMTransformerClass() {
                return new String[] {
                            "bqfontfix.BQFontFixTransformer"
                                    };
                                        }

                                            @Override
                                                public String getModContainerClass() {
                                                        return null;
                                                            }

                                                                @Override
                                                                    public String getSetupClass() {
                                                                            return null;
                                                                                }

                                                                                    @Override
                                                                                        public void injectData(Map<String, Object> data) {
                                                                                                // Not needed
                                                                                                    }

                                                                                                        @Override
                                                                                                            public String getAccessTransformerClass() {
                                                                                                                    return null;
                                                                                                                        }
                                                                                                                        }
                                                                                                                        