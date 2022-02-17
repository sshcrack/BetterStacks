package io.github.sshcrack.betterstacks.mixins;

import io.github.sshcrack.betterstacks.BetterStacks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.Properties.class)
public class MixinItemProperties {
    @Shadow
    int maxStackSize;

    @Inject(
            method = "tab",
            at = @At("RETURN")
    )
    public void tab(CreativeModeTab currTab, CallbackInfoReturnable<Item.Properties> cir) {
        this.maxStackSize = BetterStacks.getMaxStackSize(currTab);
    }
}
