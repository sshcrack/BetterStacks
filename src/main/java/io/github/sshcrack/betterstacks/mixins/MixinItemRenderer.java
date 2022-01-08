package io.github.sshcrack.betterstacks.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
    @Shadow public float blitOffset;

    @Inject(
            method = "renderGuiItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;blitOffset:F"),
            cancellable = true)
    public void renderGuiItemDecorations(Font font, ItemStack stack, int x, int y, String display, CallbackInfo ci) {
        ci.cancel();

        double count = stack.getCount();
        if(count > 999 && display == null) {
            double thousands = Math.round(count / 100.0) / 10.0;
            display = String.format("%.1f", thousands);
            display = display.replace(".0", "") + "K";
        }

        String s = display == null ? String.valueOf(stack.getCount()) : display;
        PoseStack posestack = new PoseStack();
        posestack.translate(0.0D, 0.0D, this.blitOffset + 200.0F);
        MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        font.drawInBatch(s, (float)(x + 19 - 2 - font.width(s)), (float)(y + 6 + 3), 16777215, true, posestack.last().pose(), multibuffersource$buffersource, false, 0, 15728880);
        multibuffersource$buffersource.endBatch();
    }
}
