package com.lothrazar.cyclic.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

/**
 * Render Types with help from direwolf20 MIT open source project https://github.com/Direwolf20-MC/BuildingGadgets/blob/1.15/LICENSE.md
 */
public class FakeBlockRenderTypes extends RenderType {

  public FakeBlockRenderTypes(String nameIn, VertexFormat formatIn, VertexFormat.Mode drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
    super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
  }

  /**
   * laser rendering from this MIT project https://github.com/Direwolf20-MC/DireGoo2/blob/master/LICENSE.md
   * 1.17 BOOLS are
   * <p>
   * this.affectsCrumbling = p_173182_;
   * this.sortOnUpload = p_173183_;
   */
  public static final RenderType LASER_MAIN_BEAM = create("MiningLaserMainBeam",
      DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256,
      false, false, // affectsCrumbling, sortOnUpload
      RenderType.CompositeState.builder()
          .setLayeringState(VIEW_OFFSET_Z_LAYERING)
          .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
          .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
          .setCullState(NO_CULL)
          .setLightmapState(NO_LIGHTMAP)
          .setWriteMaskState(COLOR_WRITE)
          .createCompositeState(false));
  //
  public static final RenderType FAKE_BLOCK = create("fakeBlock",
      DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 256, false, false, // affectsCrumbling, sortOnUpload
      RenderType.CompositeState.builder()
          //          .setShadeModelState(SMOOTH_SHADE)
          .setLightmapState(LIGHTMAP)
          .setTextureState(BLOCK_SHEET_MIPPED)
          //          .layer(PROJECTION_LAYERING)
          .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setCullState(NO_CULL)
          .setWriteMaskState(COLOR_DEPTH_WRITE)
          .createCompositeState(false));
  public static final RenderType TRANSPARENT_COLOUR = create("transparentColour",
      DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, false, // affectsCrumbling, sortOnUpload
      RenderType.CompositeState.builder()
          //          .layer(PROJECTION_LAYERING)
          .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
          .setTextureState(NO_TEXTURE)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setCullState(CULL)
          .setLightmapState(NO_LIGHTMAP)
          .setWriteMaskState(COLOR_DEPTH_WRITE)
          .createCompositeState(false));
  public static final RenderType SOLID_COLOUR = create("solidColour",
      DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, false, // affectsCrumbling, sortOnUpload
      RenderType.CompositeState.builder()
          //          .layer(PROJECTION_LAYERING)
          .setTransparencyState(ADDITIVE_TRANSPARENCY)
          .setTextureState(NO_TEXTURE)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setCullState(CULL)
          .setLightmapState(NO_LIGHTMAP)
          .setWriteMaskState(COLOR_DEPTH_WRITE)
          .createCompositeState(false));
}
