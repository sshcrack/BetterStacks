package io.github.sshcrack.betterstacks.mixins;

import io.github.sshcrack.betterstacks.BetterStacks;
import net.minecraft.world.Container;
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
