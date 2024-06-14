package net.dakotapride.pridemoths.config;

import com.mojang.datafixers.util.Pair;
import net.dakotapride.pridemoths.PrideMothsInitialize;

public class PrideMothsConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static boolean GENERATE_PRIDE_VARIANTS_OUTSIDE_OF_PRIDE_MONTH;
    public static int BASE_RARE_CHANCE;
    public static int BASE_RARE_CHANCE_MOTH_WEEK;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of("pridemoths/common-config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("pridemoths.spawning.outside_of_pride_month", false), "Whether pride variants for moths spawn naturally outside of Pride Month");
        configs.addKeyValuePair(new Pair<>("pridemoths.rare_variant.base_chance", 240), "The default value for the rare, golden moth variant");
        configs.addKeyValuePair(new Pair<>("pridemoths.rare_variant.chance_in_moth_week", 120), "The default value for the rare, golden moth variant during moth week (last week of July)");

    }

    private static void assignConfigs() {
        GENERATE_PRIDE_VARIANTS_OUTSIDE_OF_PRIDE_MONTH = CONFIG.getOrDefault("pridemoths.spawning.outside_of_pride_month", false);
        BASE_RARE_CHANCE = CONFIG.getOrDefault("pridemoths.rare_variant.base_chance", 240);
        BASE_RARE_CHANCE_MOTH_WEEK = CONFIG.getOrDefault("pridemoths.rare_variant.chance_in_moth_week", 120);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
