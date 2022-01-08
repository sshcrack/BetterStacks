package me.sshcrack.betterstacks.mixins;

import me.sshcrack.betterstacks.BetterStacks;
import net.minecraft.world.Container;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Container.class)
interface MixinContainer {

    /**
     * @author uh idk
     */
    @Overwrite()
    default int getMaxStackSize() {
        return BetterStacks.MAX_STACK_SIZE;
    }
}
