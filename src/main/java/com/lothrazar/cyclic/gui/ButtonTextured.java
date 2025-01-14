package com.lothrazar.cyclic.gui;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.cyclic.api.IHasTooltip;
import com.lothrazar.cyclic.registry.TextureRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

/**
 * not bound to a tile entity unlike ButtonMachine. Textures bound to TextureEnum
 */
public class ButtonTextured extends ExtendedButton implements IHasTooltip {

  private TextureEnum textureId;
  private List<Component> tooltips;
  public int xOffset = 0;
  public int yOffset = 0;

  public ButtonTextured(int xPos, int yPos, int width, int height, String displayString, OnPress handler) {
    super(xPos, yPos, width, height,
        Component.translatable(displayString), handler);
  }

  public ButtonTextured(int xPos, int yPos, int width, int height, TextureEnum tid, String tooltip, OnPress handler) {
    super(xPos, yPos, width, height, Component.translatable(""), handler);
    this.setTooltip(tooltip);
    this.setTextureId(tid);
  }

  public void setTextureId(TextureEnum id) {
    this.textureId = id;
  }

  @Override
  public void renderWidget(GuiGraphics gg, int mouseX, int mouseY, float partial) {
    super.renderWidget(gg, mouseX, mouseY, partial);
    //    Minecraft minecraft = Minecraft.getInstance();
    //    minecraft.getTextureManager().bind(TextureRegistry.WIDGETS);
    //    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    //    RenderSystem.setShaderTexture(0, TextureRegistry.WIDGETS);
    //    GL11.glColor4f(1.0F, 1.0F, 1.0F, this.alpha);
    //    GL11.glEnable(GL11.GL_BLEND);
    //    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    if (textureId != null) {
      gg.blit(TextureRegistry.WIDGETS,
          this.getX() + textureId.getOffsetX(), this.getY() + textureId.getOffsetY(),
          textureId.getX() + xOffset, textureId.getY() + yOffset,
          textureId.getWidth() - yOffset, textureId.getHeight() - yOffset);
    }
    //    this.renderBg(ms, minecraft, mouseX, mouseY);
  }

  @Override
  public List<Component> getTooltips() {
    return tooltips;
  }

  @Override
  public void setTooltip(String ttIn) {
    tooltips = new ArrayList<>();
    addTooltip(ttIn);
  }

  @Override
  public void addTooltip(String ttIn) {
    tooltips.add(Component.translatable(ttIn));
  }
}
