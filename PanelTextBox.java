/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  betterquesting.api.utils.RenderUtils
 *  betterquesting.api2.client.gui.misc.GuiRectangle
 *  betterquesting.api2.client.gui.misc.IGuiRect
 *  betterquesting.api2.client.gui.panels.IGuiPanel
 *  betterquesting.api2.client.gui.resources.colors.GuiColorStatic
 *  betterquesting.api2.client.gui.resources.colors.IGuiColor
 *  javax.annotation.Nonnull
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.math.MathHelper
 */
package betterquesting.api2.client.gui.panels.content;

import betterquesting.api.utils.RenderUtils;
import betterquesting.api2.client.gui.misc.GuiRectangle;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.IGuiPanel;
import betterquesting.api2.client.gui.resources.colors.GuiColorStatic;
import betterquesting.api2.client.gui.resources.colors.IGuiColor;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class PanelTextBox
implements IGuiPanel {
    private final GuiRectText transform;
    private final GuiRectangle refRect = new GuiRectangle(0, 0, 0, 0, 0);
    private boolean enabled = true;
    private String text = "";
    private boolean shadow = false;
    private IGuiColor color = new GuiColorStatic(255, 255, 255, 255);
    private final boolean autoFit;
    private int align = 0;
    private int fontScale = 12;
    private int lines = 1;
    private final float relScale = 12.0f;

    public PanelTextBox(IGuiRect rect, String text) {
        this(rect, text, false);
    }

    public PanelTextBox(IGuiRect rect, String text, boolean autoFit) {
        this.transform = new GuiRectText(rect, autoFit);
        this.setText(text);
        this.autoFit = autoFit;
    }

    public PanelTextBox setText(String text) {
        this.text = text;
        this.refreshText();
        return this;
    }

    public PanelTextBox setColor(IGuiColor color) {
        this.color = color;
        return this;
    }

    public PanelTextBox setAlignment(int align) {
        this.align = MathHelper.func_76125_a((int)align, (int)0, (int)2);
        return this;
    }

    public PanelTextBox setFontSize(int size) {
        this.fontScale = size;
        return this;
    }

    public PanelTextBox enableShadow(boolean enable) {
        this.shadow = enable;
        return this;
    }

    private void refreshText() {
        IGuiRect bounds = this.getTransform();
        FontRenderer fr = Minecraft.func_71410_x().field_71466_p;
        float scale = (float)this.fontScale / 12.0f;
        if (!this.autoFit) {
            this.lines = (int)Math.floor((float)bounds.getHeight() / ((float)fr.field_78288_b * scale)) - 1;
            return;
        }
        int w = (int)Math.floor((float)bounds.getWidth() / scale);
        List sl = w > 8 ? fr.func_78271_c(this.text, w) : Collections.emptyList();
        this.lines = sl.size() - 1;
        this.transform.h = (int)Math.floor((float)(fr.field_78288_b * sl.size()) * scale);
        this.refRect.x = bounds.getX();
        this.refRect.y = bounds.getY();
        this.refRect.w = bounds.getWidth();
        this.refRect.h = bounds.getHeight();
    }

    public IGuiRect getTransform() {
        return this.transform;
    }

    public void initPanel() {
        this.refreshText();
    }

    public void setEnabled(boolean state) {
        this.enabled = state;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void drawPanel(int mx, int my, float partialTick) {
        if (!this.isRectEqual((IGuiRect)this.refRect, this.getTransform())) {
            this.refreshText();
        }
        if (this.lines < 0) {
            return;
        }
        IGuiRect bounds = this.getTransform();
        FontRenderer fr = Minecraft.func_71410_x().field_71466_p;
        double s = (float)this.fontScale / 12.0f;
        int bw = (int)Math.ceil((double)bounds.getWidth() / s);
        double w = Math.min((double)bw, (double)RenderUtils.getStringWidth((String)this.text, (FontRenderer)fr) * s);
        if (bw <= 0) {
            return;
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)bounds.getX(), (float)bounds.getY(), (float)1.0f);
        if (this.align == 1) {
            GlStateManager.func_179137_b((double)((double)bounds.getWidth() / 2.0 - w / 2.0), (double)0.0, (double)0.0);
        }
        if (this.align == 2) {
            GlStateManager.func_179137_b((double)((double)bounds.getWidth() - w), (double)0.0, (double)0.0);
        }
        GlStateManager.func_179139_a((double)s, (double)s, (double)1.0);
        if (this.align == 2) {
            RenderUtils.drawSplitString((FontRenderer)fr, (String)this.text, (int)0, (int)0, (int)bw, (int)this.color.getRGB(), (boolean)this.shadow, (int)0, (int)this.lines);
        } else if (this.align == 1) {
            RenderUtils.drawSplitString((FontRenderer)fr, (String)this.text, (int)0, (int)0, (int)bw, (int)this.color.getRGB(), (boolean)this.shadow, (int)0, (int)this.lines);
        } else {
            RenderUtils.drawSplitString((FontRenderer)fr, (String)this.text, (int)0, (int)0, (int)bw, (int)this.color.getRGB(), (boolean)this.shadow, (int)0, (int)this.lines);
        }
        GlStateManager.func_179121_F();
    }

    public boolean onMouseClick(int mx, int my, int click) {
        return false;
    }

    public boolean onMouseRelease(int mx, int my, int click) {
        return false;
    }

    public boolean onMouseScroll(int mx, int my, int scroll) {
        return false;
    }

    public boolean onKeyTyped(char c, int keycode) {
        return false;
    }

    public List<String> getTooltip(int mx, int my) {
        return null;
    }

    private boolean isRectEqual(IGuiRect r1, IGuiRect r2) {
        return r1.getX() == r2.getX() && r1.getY() == r2.getY() && r1.getWidth() == r2.getWidth() && r1.getHeight() == r2.getHeight();
    }

    private static class GuiRectText
    implements IGuiRect {
        private final IGuiRect proxy;
        private final boolean useH;
        private int h;

        public GuiRectText(IGuiRect proxy, boolean useH) {
            this.proxy = proxy;
            this.useH = useH;
        }

        public int getX() {
            return this.proxy.getX();
        }

        public int getY() {
            return this.proxy.getY();
        }

        public int getWidth() {
            return this.proxy.getWidth();
        }

        public int getHeight() {
            return this.useH ? this.h : this.proxy.getHeight();
        }

        public int getDepth() {
            return this.proxy.getDepth();
        }

        public IGuiRect getParent() {
            return this.proxy.getParent();
        }

        public void setParent(IGuiRect rect) {
            this.proxy.setParent(rect);
        }

        public boolean contains(int x, int y) {
            int x1 = this.getX();
            int x2 = x1 + this.getWidth();
            int y1 = this.getY();
            int y2 = y1 + this.getHeight();
            return x >= x1 && x < x2 && y >= y1 && y < y2;
        }

        public int compareTo(@Nonnull IGuiRect o) {
            return this.proxy.compareTo((Object)o);
        }
    }
}
