package io.github.sshcrack.betterstacks.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import io.github.sshcrack.betterstacks.BetterStacks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.Tags;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class CommonConfig {
    public final HashMap<String, Integer> defaultTabMap = new HashMap<>();
    public final HashMap<String, Integer> defaultBlockMap = new HashMap<>();
    public final HashMap<String, Integer> defaultTagMap = new HashMap<>();

    public final ForgeConfigSpec.ConfigValue<Map<String, Integer>> creativeTabs;
    public final ForgeConfigSpec.ConfigValue<Map<String, Integer>> blocks;
    public final ForgeConfigSpec.ConfigValue<Map<String, Integer>> tags;
    public CommonConfig(ForgeConfigSpec.Builder builder) {
        defaultTabMap.put(getTabName(CreativeModeTab.TAB_BUILDING_BLOCKS), BetterStacks.MAX_STACK_SIZE);
        defaultBlockMap.put("minecraft:dirt", BetterStacks.MAX_STACK_SIZE);
        defaultTabMap.put("#forge:stone", BetterStacks.MAX_STACK_SIZE);

        builder
                .comment("This is a list of all available tabs you can use, names should be self explanatory")
                .define("tabs_example", CommonConfig.getAllTabsString());

        this.creativeTabs = mapToBuilder(
                "tabs",
                defaultTabMap,
                builder
                .comment("Key = Creative Tab, Value = Max Items")
        )

        this.blocks = builder
                .comment("Define specific blocks that you want to be able to stack to the given number")
                .define("blocks", defaultBlockMap);

        this.tags = builder
                .comment("Define specific tags that you want to be able to stack to the given number")
                .define("tags", defaultTagMap);
    }

    public static String getTabName(CreativeModeTab tab) {
        return tab.getDisplayName().getContents();
    }

    public static String getTagName(Tags.IOptionalNamedTag<Item> tag) {
        return tag.getName().toString();
    }

    public static List<String> getAllTabsString() {
        return Arrays.stream(CreativeModeTab.TABS)
                .map(CommonConfig::getTabName)
                .collect(Collectors.toList());
    }

    public static List<String> getAllTagsString() {
        return getAllTags()
                .stream()
                .map(CommonConfig::getTagName)
                .collect(Collectors.toList());
    }

    public static List<Tags.IOptionalNamedTag<Item>> getAllTags() {
        return getStatics(Tags.Items.class)
                .stream()
                .map(e -> {
                    try {
                        return e.get(null);
                    } catch (IllegalAccessException ex) {
                        return null;
                    }
                })
                .filter(e -> e instanceof Tags.IOptionalNamedTag)
                .map(e -> (Tags.IOptionalNamedTag<Item>) e)
                .collect(Collectors.toList());
    }

    public static List<Field> getStatics(Class<?> clazz) {
        List<Field> result;

        result = Arrays.stream(clazz.getDeclaredFields())
                // filter out the non-static fields
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                // collect to list
                .collect(Collectors.toList());

        return result;
    }

    public static Optional<CreativeModeTab> fromName(String name) {
        return Arrays
                .stream(CreativeModeTab.TABS)
                .filter(e -> getTabName(e).equals(name))
                .findFirst();
    }

    @Nullable
    public static String getRegistryName(@NonNull Item item) {
        ResourceLocation loc = item.getRegistryName();
        if(loc == null)
            return null;

        return loc.toString();
    }

    public static <T> ForgeConfigSpec.Builder mapToBuilder(String name, Map<String, T> map, ForgeConfigSpec.Builder builder) {
        builder.push(name);

        for (String key : map.keySet()) {
            T value = map.get(key);

            builder.define(key, value);
        }

        builder.pop();
        return builder;
    }

}
