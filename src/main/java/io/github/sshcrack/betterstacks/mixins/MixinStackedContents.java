package io.github.sshcrack.betterstacks.mixins;

import io.github.sshcrack.betterstacks.BetterStacks;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StackedContents.class)
public abstract class MixinStackedContents {
    @Redirect(
            at=@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/StackedContents;accountStack(Lnet/minecraft/world/item/ItemStack;I)V"
            ),
            method = "accountStack(Lnet/minecraft/world/item/ItemStack;)V")
    public void accountStackRedirect(StackedContents contents, ItemStack p_36469_, int p_36470_) {
        contents.accountStack(p_36469_, BetterStacks.MAX_STACK_SIZE);
    }
}
