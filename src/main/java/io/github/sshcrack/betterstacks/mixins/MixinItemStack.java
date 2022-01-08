package io.github.sshcrack.betterstacks.mixins;

import io.github.sshcrack.betterstacks.BetterStacks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Shadow public abstract Item getItem();

    @Shadow public abstract void setCount(int p_41765_);
    @Shadow public abstract int getCount();

    @Inject(at=@At("TAIL"), method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V")
    public void init(CompoundTag tag, CallbackInfo ci) {
        Set<String> keys = tag.getAllKeys();
        Item item = this.getItem();

        if(!keys.contains(BetterStacks.COUNT_ID))
            return;

        int count = tag.getInt(BetterStacks.COUNT_ID);
        setCount(count);
    }

    @Inject(at=@At("RETURN"), method = "save", cancellable = true)
    public void save(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag returnVal = cir.getReturnValue();
        returnVal.putInt(BetterStacks.COUNT_ID, this.getCount());

        cir.setReturnValue(returnVal);
    }
}
