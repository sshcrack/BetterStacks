package io.github.sshcrack.betterstacks;

import io.github.sshcrack.betterstacks.config.CommonConfig;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("betterstacks")
public class BetterStacks
{
    private boolean hadPostInit;
    public static final int MAX_STACK_SIZE = 1000;
    public static final String COUNT_ID = "CountBetterStacks";

    public static ForgeConfigSpec COMMON_SPEC;
    public static CommonConfig COMMON;

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public BetterStacks() {
        MinecraftForge.EVENT_BUS.register(this);
        loadConfig();
    }

    public static void loadConfig() {
        LOGGER.debug("Loading common config...");

        Pair<CommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);

        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);

        LOGGER.debug("Loaded config {} %n", StringUtils.join(COMMON.tags.get()));
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent e) {
        LOGGER.debug("all tabs {}", StringUtils.join(CommonConfig.getAllTabsString()));
    }

    public static int getMaxStackSize(CreativeModeTab currTab) {
        boolean contains = false;
        CreativeModeTab[] buildingBlocks = new CreativeModeTab[]{
                CreativeModeTab.TAB_BUILDING_BLOCKS
        };

        for( CreativeModeTab tab : buildingBlocks) {
            if(tab == currTab) {
                contains = true;
                break;
            }
        }

        return contains ? BetterStacks.MAX_STACK_SIZE : 64;
    }

    public static int getMaxStackSize(ItemStack stack) {
        return getMaxStackSize(stack.getItem().getItemCategory());
    }
}
