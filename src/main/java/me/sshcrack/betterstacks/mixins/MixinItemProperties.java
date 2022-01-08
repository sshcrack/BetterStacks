package me.sshcrack.betterstacks.mixins;

import me.sshcrack.betterstacks.BetterStacks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
            method = "<init>",
            at = @At("RETURN")
    )
    public void init(CallbackInfo ci) {
        this.maxStackSize = BetterStacks.MAX_STACK_SIZE;
    }

    @Inject(
            method = "tab",
            at = @At("RETURN")
    )
    public void tab(CreativeModeTab currTab, CallbackInfoReturnable<Item.Properties> cir) {
        CreativeModeTab[] buildingBlocks = new CreativeModeTab[]{
                CreativeModeTab.TAB_BUILDING_BLOCKS
        };

        boolean contains = false;
        this.maxStackSize = BetterStacks.MAX_STACK_SIZE;
        /*
        for( CreativeModeTab tab : buildingBlocks) {
            if(tab == currTab) {
                contains = true;
                break;
            }
        }
        if (!contains)
            return;

        this.maxStackSize = BetterStacks.MAX_STACK_SIZE;
        */
    }

    @Inject(
            method = "stacksTo",
            at = @At("RETURN")
    )
    public void stacksTo(int maxStackSize, CallbackInfoReturnable<Item.Properties> cir) {
        this.maxStackSize = BetterStacks.MAX_STACK_SIZE;
    }
}
