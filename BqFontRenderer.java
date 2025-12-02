/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.resources.IReloadableResourceManager
 *  net.minecraft.client.resources.IResourceManagerReloadListener
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package betterquesting.api2.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BqFontRenderer
extends FontRenderer {
    public static final BqFontRenderer FONT_UNICODE = new BqFontRenderer(true);
    public static final BqFontRenderer FONT_STANDARD = new BqFontRenderer(false);
    private static final ResourceLocation[] UNICODE_PAGE_LOCATIONS = new ResourceLocation[256];
    private boolean isSmall = false;

    public BqFontRenderer(boolean unicode) {
        super(Minecraft.func_71410_x().field_71474_y, new ResourceLocation("minecraft:textures/font/ascii.png"), Minecraft.func_71410_x().field_71446_o, unicode);
        ((IReloadableResourceManager)Minecraft.func_71410_x().func_110442_L()).func_110542_a((IResourceManagerReloadListener)this);
    }

    public int func_175065_a(String text, float x, float y, int color, boolean dropShadow) {
        return this.drawStringScaled(text, x, y, color, dropShadow, 1.0f);
    }

    public int drawStringScaled(String text, float x, float y, int color, boolean shadow, float scale) {
        if (scale <= 0.0f) {
            return 0;
        }
        Minecraft mc = Minecraft.func_71410_x();
        if (scale == 1.0f && (mc.field_71474_y.field_74335_Z <= 0 || mc.field_71474_y.field_74335_Z >= 3)) {
            this.isSmall = false;
            return super.func_175065_a(text, x, y, color, shadow);
        }
        this.isSmall = mc.field_71474_y.field_74335_Z > 0 && mc.field_71474_y.field_74335_Z < 3 || scale <= 0.5f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)x, (float)y, (float)0.0f);
        GlStateManager.func_179152_a((float)scale, (float)scale, (float)0.0f);
        int r = super.func_175065_a(text, 0.0f, 0.0f, color, shadow);
        GlStateManager.func_179121_F();
        return r;
    }

    protected float func_78277_a(char ch, boolean italic) {
        int i = this.field_78287_e[ch] & 0xFF;
        if (i == 0) {
            return 0.0f;
        }
        int j = ch / 256;
        this.loadGlyphTexture(j);
        int k = i >>> 4;
        int l = i & 0xF;
        double f = k;
        double f1 = l + 1;
        double f2 = (double)(ch % 16 * 16) + f;
        double f3 = (ch & 0xFF) / 16 * 16;
        double f4 = f1 - f - 0.02;
        double f5 = italic ? 1.0 : 0.0;
        double ys = 7.99;
        if (this.isSmall) {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        }
        GlStateManager.func_187447_r((int)5);
        GL11.glTexCoord2d((double)(f2 / 256.0), (double)(f3 / 256.0));
        GL11.glVertex3d((double)((double)this.field_78295_j + f5), (double)this.field_78296_k, (double)0.0);
        GL11.glTexCoord2d((double)(f2 / 256.0), (double)((f3 + 15.98) / 256.0));
        GL11.glVertex3d((double)((double)this.field_78295_j - f5), (double)((double)this.field_78296_k + ys), (double)0.0);
        GL11.glTexCoord2d((double)((f2 + f4) / 256.0), (double)(f3 / 256.0));
        GL11.glVertex3d((double)((double)this.field_78295_j + f4 / 2.0 + f5), (double)this.field_78296_k, (double)0.0);
        GL11.glTexCoord2d((double)((f2 + f4) / 256.0), (double)((f3 + 15.98) / 256.0));
        GL11.glVertex3d((double)((double)this.field_78295_j + f4 / 2.0 - f5), (double)((double)this.field_78296_k + ys), (double)0.0);
        GlStateManager.func_187437_J();
        if (this.isSmall) {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        }
        return (float)((f1 - f) / 2.0 + 1.0);
    }

    protected float func_78266_a(int ch, boolean italic) {
        int i = ch % 16 * 8;
        int j = ch / 16 * 8;
        boolean k = italic;
        this.bindTexture(this.field_111273_g);
        int l = this.field_78286_d[ch];
        float f = (float)l - 0.01f;
        if (this.isSmall) {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        }
        GlStateManager.func_187447_r((int)5);
        GlStateManager.func_187426_b((float)((float)i / 128.0f), (float)((float)j / 128.0f));
        GlStateManager.func_187435_e((float)(this.field_78295_j + (float)k), (float)this.field_78296_k, (float)0.0f);
        GlStateManager.func_187426_b((float)((float)i / 128.0f), (float)(((float)j + 7.99f) / 128.0f));
        GlStateManager.func_187435_e((float)(this.field_78295_j - (float)k), (float)(this.field_78296_k + 7.99f), (float)0.0f);
        GlStateManager.func_187426_b((float)(((float)i + f - 1.0f) / 128.0f), (float)((float)j / 128.0f));
        GlStateManager.func_187435_e((float)(this.field_78295_j + f - 1.0f + (float)k), (float)this.field_78296_k, (float)0.0f);
        GlStateManager.func_187426_b((float)(((float)i + f - 1.0f) / 128.0f), (float)(((float)j + 7.99f) / 128.0f));
        GlStateManager.func_187435_e((float)(this.field_78295_j + f - 1.0f - (float)k), (float)(this.field_78296_k + 7.99f), (float)0.0f);
        GlStateManager.func_187437_J();
        if (this.isSmall) {
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        }
        return l;
    }

    private ResourceLocation getUnicodePageLocation(int page) {
        if (UNICODE_PAGE_LOCATIONS[page] == null) {
            BqFontRenderer.UNICODE_PAGE_LOCATIONS[page] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", page));
        }
        return UNICODE_PAGE_LOCATIONS[page];
    }

    private void loadGlyphTexture(int page) {
        this.bindTexture(this.getUnicodePageLocation(page));
    }

    public String func_78262_a(String text, int width, boolean reverse) {
        StringBuilder stringbuilder = new StringBuilder();
        int i = 0;
        int j = reverse ? text.length() - 1 : 0;
        int k = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag1 = false;
        for (int l = j; l >= 0 && l < text.length() && i < width; l += k) {
            char c0 = text.charAt(l);
            int i1 = this.func_78263_a(c0);
            if (flag) {
                flag = false;
                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R' || BqFontRenderer.isFormatColor(c0)) {
                        flag1 = false;
                    }
                } else {
                    flag1 = true;
                }
            } else if (i1 < 0) {
                flag = true;
            } else {
                i += i1;
                if (flag1) {
                    ++i;
                }
            }
            if (i > width) break;
            if (reverse) {
                stringbuilder.insert(0, c0);
                continue;
            }
            stringbuilder.append(c0);
        }
        return stringbuilder.toString();
    }

    public int func_78256_a(String text) {
        if (text == null) {
            return 0;
        }
        int i = 0;
        boolean flag = false;
        for (int j = 0; j < text.length(); ++j) {
            char c0 = text.charAt(j);
            int k = this.func_78263_a(c0);
            if (k < 0 && j < text.length() - 1) {
                if ((c0 = text.charAt(++j)) != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R' || BqFontRenderer.isFormatColor(c0)) {
                        flag = false;
                    }
                } else {
                    flag = true;
                }
                k = 0;
            }
            i += k;
            if (!flag || k <= 0) continue;
            ++i;
        }
        return i;
    }

    public static boolean isFormatColor(char colorChar) {
        return colorChar >= '0' && colorChar <= '9' || colorChar >= 'a' && colorChar <= 'f' || colorChar >= 'A' && colorChar <= 'F';
    }
}
