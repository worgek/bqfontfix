/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  betterquesting.api2.client.gui.misc.GuiRectangle
 *  betterquesting.api2.client.gui.misc.IGuiRect
 *  betterquesting.api2.client.gui.resources.colors.GuiColorStatic
 *  betterquesting.api2.client.gui.resources.colors.IGuiColor
 *  betterquesting.api2.client.gui.themes.presets.PresetTexture
 *  betterquesting.core.BetterQuesting
 *  javax.annotation.Nonnull
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$LogicOp
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.event.RenderTooltipEvent$PostText
 *  net.minecraftforge.client.event.RenderTooltipEvent$Pre
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.opengl.GL11
 */
package betterquesting.api.utils;

import betterquesting.api2.client.gui.misc.GuiRectangle;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.resources.colors.GuiColorStatic;
import betterquesting.api2.client.gui.resources.colors.IGuiColor;
import betterquesting.api2.client.gui.themes.presets.PresetTexture;
import betterquesting.core.BetterQuesting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RenderUtils {
    public static final String REGEX_NUMBER = "[^\\.0123456789-]";
    private static final IGuiColor STENCIL_COLOR = new GuiColorStatic(0, 0, 0, 255);
    private static int stencilDepth = 0;

    public static void RenderItemStack(Minecraft mc, ItemStack stack, int x, int y, String text) {
        RenderUtils.RenderItemStack(mc, stack, x, y, text, Color.WHITE.getRGB());
    }

    public static void RenderItemStack(Minecraft mc, ItemStack stack, int x, int y, String text, Color color) {
        RenderUtils.RenderItemStack(mc, stack, x, y, text, color.getRGB());
    }

    public static void RenderItemStack(Minecraft mc, ItemStack stack, int x, int y, String text, int color) {
        RenderUtils.RenderItemStack(mc, stack, x, y, 16.0f, text, color);
    }

    public static void RenderItemStack(Minecraft mc, ItemStack stack, int x, int y, float z, String text, int color) {
        if (stack == null || stack.func_190926_b()) {
            return;
        }
        GlStateManager.func_179094_E();
        RenderItem itemRender = mc.func_175599_af();
        float preZ = itemRender.field_77023_b;
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        GlStateManager.func_179124_c((float)r, (float)g, (float)b);
        RenderHelper.func_74520_c();
        GlStateManager.func_179091_B();
        GlStateManager.func_179126_j();
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)z);
        itemRender.field_77023_b = -150.0f;
        FontRenderer font = stack.func_77973_b().getFontRenderer(stack);
        if (font == null) {
            font = mc.field_71466_p;
        }
        try {
            itemRender.func_180450_b(stack, x, y);
            if (stack.func_190916_E() != 1 || text != null) {
                float ty;
                float tx;
                GlStateManager.func_179094_E();
                int w = RenderUtils.getStringWidth(text, font);
                float s = 1.0f;
                if (w > 17) {
                    s = 17.0f / (float)w;
                    tx = 0.0f;
                    ty = 17.0f - (float)font.field_78288_b * s;
                } else {
                    tx = 17 - w;
                    ty = 18 - font.field_78288_b;
                }
                GlStateManager.func_179109_b((float)((float)x + tx), (float)((float)y + ty), (float)0.0f);
                GlStateManager.func_179152_a((float)s, (float)s, (float)1.0f);
                GlStateManager.func_179140_f();
                GlStateManager.func_179097_i();
                GlStateManager.func_179084_k();
                font.func_175065_a(text, 0.0f, 0.0f, 0xFFFFFF, true);
                GlStateManager.func_179145_e();
                GlStateManager.func_179126_j();
                GlStateManager.func_179147_l();
                GlStateManager.func_179121_F();
            }
            itemRender.func_180453_a(font, stack, x, y, "");
        }
        catch (Exception e) {
            BetterQuesting.logger.warn("Unabled to render item " + stack, (Throwable)e);
        }
        GlStateManager.func_179097_i();
        RenderHelper.func_74518_a();
        itemRender.field_77023_b = preZ;
        GlStateManager.func_179121_F();
    }

    public static void RenderEntity(int posX, int posY, int scale, float rotation, float pitch, Entity entity) {
        RenderUtils.RenderEntity(posX, posY, 64.0f, scale, rotation, pitch, entity);
    }

    public static void RenderEntity(float posX, float posY, float posZ, int scale, float rotation, float pitch, Entity entity) {
        try {
            float f9;
            GlStateManager.func_179142_g();
            GlStateManager.func_179094_E();
            GlStateManager.func_179126_j();
            GlStateManager.func_179109_b((float)posX, (float)posY, (float)posZ);
            GlStateManager.func_179152_a((float)(-scale), (float)scale, (float)scale);
            GlStateManager.func_179114_b((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.func_179114_b((float)pitch, (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.func_179114_b((float)rotation, (float)0.0f, (float)1.0f, (float)0.0f);
            float f3 = entity.field_70177_z;
            float f4 = entity.field_70125_A;
            float f5 = entity.field_70126_B;
            float f6 = entity.field_70127_C;
            entity.field_70177_z = 0.0f;
            entity.field_70125_A = 0.0f;
            entity.field_70126_B = 0.0f;
            entity.field_70127_C = 0.0f;
            EntityLivingBase livingBase = entity instanceof EntityLivingBase ? (EntityLivingBase)entity : null;
            float f7 = livingBase == null ? 0.0f : livingBase.field_70761_aq;
            float f8 = livingBase == null ? 0.0f : livingBase.field_70759_as;
            float f = f9 = livingBase == null ? 0.0f : livingBase.field_70758_at;
            if (livingBase != null) {
                livingBase.field_70761_aq = 0.0f;
                livingBase.field_70759_as = 0.0f;
                livingBase.field_70758_at = 0.0f;
            }
            RenderHelper.func_74519_b();
            RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
            rendermanager.func_178631_a(180.0f);
            rendermanager.func_188391_a(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
            entity.field_70177_z = f3;
            entity.field_70125_A = f4;
            entity.field_70126_B = f5;
            entity.field_70127_C = f6;
            if (livingBase != null) {
                livingBase.field_70761_aq = f7;
                livingBase.field_70759_as = f8;
                livingBase.field_70758_at = f9;
            }
            GlStateManager.func_179097_i();
            GlStateManager.func_179121_F();
            RenderHelper.func_74518_a();
            GlStateManager.func_179101_C();
            OpenGlHelper.func_77473_a((int)OpenGlHelper.field_77476_b);
            GlStateManager.func_179090_x();
            OpenGlHelper.func_77473_a((int)OpenGlHelper.field_77478_a);
            GlStateManager.func_179098_w();
            GlStateManager.func_179119_h();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void DrawLine(int x1, int y1, int x2, int y2, float width, int color) {
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179090_x();
        GlStateManager.func_179131_c((float)r, (float)g, (float)b, (float)1.0f);
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glEnd();
        GlStateManager.func_179098_w();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179121_F();
    }

    public static void drawSplitString(FontRenderer renderer, String string, int x, int y, int width, int color, boolean shadow) {
        RenderUtils.drawSplitString(renderer, string, x, y, width, color, shadow, 0, RenderUtils.splitString(string, width, renderer).size() - 1);
    }

    public static void drawSplitString(FontRenderer renderer, String string, int x, int y, int width, int color, boolean shadow, int start, int end) {
        RenderUtils.drawHighlightedSplitString(renderer, string, x, y, width, color, shadow, start, end, 0, 0, 0);
    }

    public static void drawHighlightedSplitString(FontRenderer renderer, String string, int x, int y, int width, int color, boolean shadow, int highlightColor, int highlightStart, int highlightEnd) {
        RenderUtils.drawHighlightedSplitString(renderer, string, x, y, width, color, shadow, 0, RenderUtils.splitString(string, width, renderer).size() - 1, highlightColor, highlightStart, highlightEnd);
    }

    public static void drawHighlightedSplitString(FontRenderer renderer, String string, int x, int y, int width, int color, boolean shadow, int start, int end, int highlightColor, int highlightStart, int highlightEnd) {
        int i;
        if (renderer == null || string == null || string.length() <= 0 || start > end) {
            return;
        }
        string = string.replaceAll("\r", "");
        List<String> list = RenderUtils.splitString(string, width, renderer);
        List<String> noFormat = RenderUtils.splitStringWithoutFormat(string, width, renderer);
        if (list.size() != noFormat.size()) {
            return;
        }
        int hlStart = Math.min(highlightStart, highlightEnd);
        int hlEnd = Math.max(highlightStart, highlightEnd);
        int idxStart = 0;
        for (i = 0; i < start && i < noFormat.size(); ++i) {
            idxStart += noFormat.get(i).length();
        }
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        for (i = start; i <= end; ++i) {
            if (i < 0 || i >= list.size()) continue;
            renderer.func_175065_a(list.get(i), (float)x, (float)(y + renderer.field_78288_b * (i - start)), color, shadow);
            int lineSize = noFormat.get(i).length();
            int idxEnd = idxStart + lineSize;
            int i1 = Math.max(idxStart, hlStart) - idxStart;
            int i2 = Math.min(idxEnd, hlEnd) - idxStart;
            if (i1 != i2 && i1 >= 0 && i2 >= 0 && i1 <= lineSize && i2 <= lineSize) {
                String lastFormat = FontRenderer.func_78282_e((String)list.get(i));
                int x1 = RenderUtils.getStringWidth(lastFormat + noFormat.get(i).substring(0, i1), renderer);
                int x2 = RenderUtils.getStringWidth(lastFormat + noFormat.get(i).substring(0, i2), renderer);
                RenderUtils.drawHighlightBox(x + x1, y + renderer.field_78288_b * (i - start), x + x2, y + renderer.field_78288_b * (i - start) + renderer.field_78288_b, highlightColor);
            }
            idxStart = idxEnd;
        }
    }

    public static void drawHighlightedString(FontRenderer renderer, String string, int x, int y, int color, boolean shadow, int highlightColor, int highlightStart, int highlightEnd) {
        if (renderer == null || string == null || string.length() <= 0) {
            return;
        }
        renderer.func_175065_a(string, (float)x, (float)y, color, shadow);
        int hlStart = Math.min(highlightStart, highlightEnd);
        int hlEnd = Math.max(highlightStart, highlightEnd);
        int size = string.length();
        int i1 = MathHelper.func_76125_a((int)hlStart, (int)0, (int)size);
        int i2 = MathHelper.func_76125_a((int)hlEnd, (int)0, (int)size);
        if (i1 != i2) {
            int x1 = RenderUtils.getStringWidth(string.substring(0, i1), renderer);
            int x2 = RenderUtils.getStringWidth(string.substring(0, i2), renderer);
            RenderUtils.drawHighlightBox(x + x1, y, x + x2, y + renderer.field_78288_b, highlightColor);
        }
    }

    public static void drawHighlightBox(IGuiRect rect, IGuiColor color) {
        RenderUtils.drawHighlightBox(rect.getX(), rect.getY(), rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), color.getRGB());
    }

    public static void drawHighlightBox(int left, int top, int right, int bottom, int color) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        GlStateManager.func_179094_E();
        GL11.glDisable((int)3553);
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179131_c((float)f, (float)f1, (float)f2, (float)f3);
        GlStateManager.func_179090_x();
        GlStateManager.func_179115_u();
        GlStateManager.func_187422_a((GlStateManager.LogicOp)GlStateManager.LogicOp.OR_REVERSE);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        bufferbuilder.func_181662_b((double)left, (double)bottom, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)right, (double)bottom, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)right, (double)top, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)left, (double)top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179134_v();
        GlStateManager.func_179098_w();
        GL11.glEnable((int)3553);
        GlStateManager.func_179121_F();
    }

    public static void drawColoredRect(IGuiRect rect, IGuiColor color) {
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder vertexbuffer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_187428_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        color.applyGlColor();
        vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        vertexbuffer.func_181662_b((double)rect.getX(), (double)rect.getY() + (double)rect.getHeight(), 0.0).func_181675_d();
        vertexbuffer.func_181662_b((double)rect.getX() + (double)rect.getWidth(), (double)rect.getY() + (double)rect.getHeight(), 0.0).func_181675_d();
        vertexbuffer.func_181662_b((double)rect.getX() + (double)rect.getWidth(), (double)rect.getY(), 0.0).func_181675_d();
        vertexbuffer.func_181662_b((double)rect.getX(), (double)rect.getY(), 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void startScissor(IGuiRect rect) {
        if (stencilDepth >= 255) {
            throw new IndexOutOfBoundsException("Exceeded the maximum number of nested stencils (255)");
        }
        if (stencilDepth == 0) {
            GL11.glEnable((int)2960);
            GL11.glStencilMask((int)255);
            GL11.glClear((int)1024);
        }
        GL11.glStencilFunc((int)513, (int)stencilDepth, (int)255);
        GL11.glStencilOp((int)7682, (int)7680, (int)7680);
        GL11.glStencilMask((int)255);
        GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
        GL11.glDepthMask((boolean)false);
        RenderUtils.drawColoredRect(rect, STENCIL_COLOR);
        GL11.glStencilMask((int)0);
        GL11.glStencilFunc((int)514, (int)(stencilDepth + 1), (int)255);
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glDepthMask((boolean)true);
        ++stencilDepth;
    }

    private static void fillScreen() {
        int w = Minecraft.func_71410_x().field_71443_c;
        int h = Minecraft.func_71410_x().field_71440_d;
        GL11.glPushAttrib((int)265073);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)2896);
        GL11.glMatrixMode((int)5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)w, (double)h, (double)0.0, (double)-1.0, (double)1.0);
        GL11.glMatrixMode((int)5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        RenderUtils.drawColoredRect((IGuiRect)new GuiRectangle(0, 0, w, h), STENCIL_COLOR);
        GL11.glMatrixMode((int)5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode((int)5888);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

    public static void endScissor() {
        if (--stencilDepth < 0) {
            throw new IndexOutOfBoundsException("No stencil to end");
        }
        if (stencilDepth == 0) {
            GL11.glStencilMask((int)255);
            GL11.glClear((int)1024);
            GL11.glStencilFunc((int)519, (int)1, (int)255);
            GL11.glStencilOp((int)7680, (int)7680, (int)7680);
            GL11.glStencilMask((int)0);
            GL11.glDisable((int)2960);
        } else {
            GL11.glStencilFunc((int)515, (int)stencilDepth, (int)255);
            GL11.glStencilOp((int)7683, (int)7680, (int)7680);
            GL11.glStencilMask((int)255);
            GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
            GL11.glDepthMask((boolean)false);
            RenderUtils.fillScreen();
            GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
            GL11.glDepthMask((boolean)true);
            GL11.glStencilFunc((int)514, (int)stencilDepth, (int)255);
            GL11.glStencilMask((int)0);
        }
    }

    public static List<String> splitStringWithoutFormat(String str, int wrapWidth, FontRenderer font) {
        boolean flag;
        ArrayList<String> list = new ArrayList<String>();
        String lastFormat = "";
        String temp = str;
        do {
            int i = RenderUtils.sizeStringToWidth(lastFormat + temp, wrapWidth, font);
            if (temp.length() <= (i -= lastFormat.length())) {
                list.add(temp);
                break;
            }
            String s = temp.substring(0, i);
            char c0 = temp.charAt(i);
            flag = c0 == ' ' || c0 == '\n';
            lastFormat = FontRenderer.func_78282_e((String)(lastFormat + s));
            temp = temp.substring(i + (flag ? 1 : 0));
            list.add(s + (flag ? "\n" : ""));
        } while (temp.length() > 0 || flag);
        return list;
    }

    public static List<String> splitString(String str, int wrapWidth, FontRenderer font) {
        boolean flag;
        ArrayList<String> list = new ArrayList<String>();
        String temp = str;
        do {
            int i = RenderUtils.sizeStringToWidth(temp, wrapWidth, font);
            if (temp.length() <= i) {
                list.add(temp);
                break;
            }
            String s = temp.substring(0, i);
            char c0 = temp.charAt(i);
            flag = c0 == ' ' || c0 == '\n';
            temp = FontRenderer.func_78282_e((String)s) + temp.substring(i + (flag ? 1 : 0));
            list.add(s);
        } while (temp.length() > 0 || flag);
        return list;
    }

    public static int getCursorPos(String text, int x, FontRenderer font) {
        int i;
        if (text.length() <= 0) {
            return 0;
        }
        for (i = 0; i < text.length() && RenderUtils.getStringWidth(text.substring(0, i + 1), font) <= x; ++i) {
        }
        if (i - 1 >= 0 && text.charAt(i - 1) == '\n') {
            return i - 1;
        }
        return i;
    }

    public static int getCursorPos(String text, int x, int y, int width, FontRenderer font) {
        List<String> tLines = RenderUtils.splitStringWithoutFormat(text, width, font);
        if (tLines.size() <= 0) {
            return 0;
        }
        int row = MathHelper.func_76125_a((int)(y / font.field_78288_b), (int)0, (int)(tLines.size() - 1));
        String lastFormat = "";
        int idx = 0;
        for (int i = 0; i < row; ++i) {
            String line = tLines.get(i);
            idx += line.length();
            lastFormat = FontRenderer.func_78282_e((String)(lastFormat + line));
        }
        return idx + RenderUtils.getCursorPos(lastFormat + tLines.get(row), x, font) - lastFormat.length();
    }

    private static int sizeStringToWidth(String str, int wrapWidth, FontRenderer font) {
        int k;
        int i = str.length();
        int j = 0;
        int l = -1;
        boolean flag = false;
        for (k = 0; k < i; ++k) {
            char c0 = str.charAt(k);
            switch (c0) {
                case '\n': {
                    --k;
                    break;
                }
                case ' ': {
                    l = k;
                }
                default: {
                    j += font.func_78263_a(c0);
                    if (!flag) break;
                    ++j;
                    break;
                }
                case '\u00a7': {
                    char c1;
                    if (k >= i - 1) break;
                    if ((c1 = str.charAt(++k)) != 'l' && c1 != 'L') {
                        if (c1 != 'r' && c1 != 'R' && !RenderUtils.isFormatColor(c1)) break;
                        flag = false;
                        break;
                    }
                    flag = true;
                }
            }
            if (c0 == '\n') {
                l = ++k;
                break;
            }
            if (j > wrapWidth) break;
        }
        return k != i && l != -1 && l < k ? l : k;
    }

    private static boolean isFormatColor(char colorChar) {
        return colorChar >= '0' && colorChar <= '9' || colorChar >= 'a' && colorChar <= 'f' || colorChar >= 'A' && colorChar <= 'F';
    }

    public static float lerpFloat(float f1, float f2, float blend) {
        return f2 * blend + f1 * (1.0f - blend);
    }

    public static double lerpDouble(double d1, double d2, double blend) {
        return d2 * blend + d1 * (1.0 - blend);
    }

    public static int lerpRGB(int c1, int c2, float blend) {
        float a1 = c1 >> 24 & 0xFF;
        float r1 = c1 >> 16 & 0xFF;
        float g1 = c1 >> 8 & 0xFF;
        float b1 = c1 & 0xFF;
        float a2 = c2 >> 24 & 0xFF;
        float r2 = c2 >> 16 & 0xFF;
        float g2 = c2 >> 8 & 0xFF;
        float b2 = c2 & 0xFF;
        int a3 = (int)RenderUtils.lerpFloat(a1, a2, blend);
        int r3 = (int)RenderUtils.lerpFloat(r1, r2, blend);
        int g3 = (int)RenderUtils.lerpFloat(g1, g2, blend);
        int b3 = (int)RenderUtils.lerpFloat(b1, b2, blend);
        return (a3 << 24) + (r3 << 16) + (g3 << 8) + b3;
    }

    public static void drawHoveringText(List<String> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, FontRenderer font) {
        RenderUtils.drawHoveringText(ItemStack.field_190927_a, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font);
    }

    public static void drawHoveringText(@Nonnull ItemStack stack, List<String> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, FontRenderer font) {
        if (textLines == null || textLines.isEmpty()) {
            return;
        }
        RenderTooltipEvent.Pre event = new RenderTooltipEvent.Pre(stack, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return;
        }
        mouseX = event.getX();
        mouseY = event.getY();
        screenWidth = event.getScreenWidth();
        screenHeight = event.getScreenHeight();
        maxTextWidth = event.getMaxWidth();
        font = event.getFontRenderer();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)32.0f);
        GlStateManager.func_179101_C();
        RenderHelper.func_74518_a();
        GlStateManager.func_179140_f();
        GlStateManager.func_179097_i();
        int tooltipTextWidth = 0;
        for (String textLine : textLines) {
            int textLineWidth = RenderUtils.getStringWidth(textLine, font);
            if (textLineWidth <= tooltipTextWidth) continue;
            tooltipTextWidth = textLineWidth;
        }
        boolean needsWrap = false;
        int titleLinesCount = 1;
        int tooltipX = mouseX + 12;
        if (tooltipX + tooltipTextWidth + 4 > screenWidth && (tooltipX = mouseX - 16 - tooltipTextWidth) < 4) {
            tooltipTextWidth = mouseX > screenWidth / 2 ? mouseX - 12 - 8 : screenWidth - 16 - mouseX;
            needsWrap = true;
        }
        if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth) {
            tooltipTextWidth = maxTextWidth;
            needsWrap = true;
        }
        if (needsWrap) {
            int wrappedTooltipWidth = 0;
            ArrayList<String> wrappedTextLines = new ArrayList<String>();
            for (int i = 0; i < textLines.size(); ++i) {
                String textLine = textLines.get(i);
                List wrappedLine = font.func_78271_c(textLine, tooltipTextWidth);
                if (i == 0) {
                    titleLinesCount = wrappedLine.size();
                }
                for (String line : wrappedLine) {
                    int lineWidth = RenderUtils.getStringWidth(line, font);
                    if (lineWidth > wrappedTooltipWidth) {
                        wrappedTooltipWidth = lineWidth;
                    }
                    wrappedTextLines.add(line);
                }
            }
            tooltipTextWidth = wrappedTooltipWidth;
            textLines = wrappedTextLines;
            tooltipX = mouseX > screenWidth / 2 ? mouseX - 16 - tooltipTextWidth : mouseX + 12;
        }
        int tooltipY = mouseY - 12;
        int tooltipHeight = 8;
        if (textLines.size() > 1) {
            tooltipHeight += (textLines.size() - 1) * 10;
            if (textLines.size() > titleLinesCount) {
                tooltipHeight += 2;
            }
        }
        if (tooltipY < 4) {
            tooltipY = 4;
        } else if (tooltipY + tooltipHeight + 4 > screenHeight) {
            tooltipY = screenHeight - tooltipHeight - 4;
        }
        PresetTexture.TOOLTIP_BG.getTexture().drawTexture(tooltipX - 4, tooltipY - 4, tooltipTextWidth + 8, tooltipHeight + 8, 0.0f, 1.0f);
        int tooltipTop = tooltipY;
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)0.1f);
        for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber) {
            String line = textLines.get(lineNumber);
            font.func_175063_a(line, (float)tooltipX, (float)tooltipY, -1);
            if (lineNumber + 1 == titleLinesCount) {
                tooltipY += 2;
            }
            tooltipY += 10;
        }
        MinecraftForge.EVENT_BUS.post((Event)new RenderTooltipEvent.PostText(stack, textLines, tooltipX, tooltipTop, font, tooltipTextWidth, tooltipHeight));
        GlStateManager.func_179145_e();
        RenderHelper.func_74519_b();
        GlStateManager.func_179091_B();
        GlStateManager.func_179121_F();
    }

    public static int getStringWidth(String text, FontRenderer font) {
        if (text == null || text.length() == 0) {
            return 0;
        }
        int maxWidth = 0;
        int curLineWidth = 0;
        boolean bold = false;
        for (int j = 0; j < text.length(); ++j) {
            char c0 = text.charAt(j);
            int k = font.func_78263_a(c0);
            if (k < 0 && j < text.length() - 1) {
                if ((c0 = text.charAt(++j)) != 'l' && c0 != 'L') {
                    int ci = "0123456789abcdefklmnor".indexOf(String.valueOf(c0).toLowerCase(Locale.ROOT).charAt(0));
                    if (ci < 16 || ci == 21) {
                        bold = false;
                    }
                } else {
                    bold = true;
                }
                k = 0;
            }
            curLineWidth += k;
            if (bold && k > 0) {
                ++curLineWidth;
            }
            if (c0 != '\n') continue;
            maxWidth = Math.max(maxWidth, curLineWidth);
            curLineWidth = 0;
        }
        return Math.max(maxWidth, curLineWidth);
    }
}
