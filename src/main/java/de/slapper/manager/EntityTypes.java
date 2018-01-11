package de.slapper.manager;

public enum  EntityTypes {

    //Monster
    BLAZE("Blaze"),
    CAVE_SPIDER("CaveSpider"),
    CREEPER("Creeper"),
    ELDER_GUARDIAN("ElderGuardian"),
    ENDER_DRAGON("EnderDragon"),
    ENDERMAN("Enderman"),
    ENDERMITE("Endermite"),
    GHAST("Ghast"),
    GUARDIAN("Guardian"),
    HUSK("Husk"),
    MAGMA_CUBE("MagmaCube"),
    POLAR_BEAR("PolarBear"),
    SHULKER("Shulker"),
    SILVERFISH("Silverfish"),
    SKELETON("Skeleton"),
    SLIME("Slime"),
    SPIDER("Spider"),
    STRAY("Stray"),
    VEX("Vex"),
    WITCH("Witch"),
    WITHER("Wither"),
    WITHER_SKELETON("WitherSkeleton"),
    ZOMBIE("Zombie"),
    ZOMBIE_PIGMAN("ZombiePigman"),
    ZOMBIE_VILLAGER("ZombieVillager"),

    //Passive

    BAT("Bat"),
    CHICKEN("Chicken"),
    COW("Cow"),
    DONKEY("Donkey"),
    HORSE("Horse"),
    HUMAN("Human"),
    LAMA("Lama"),
    MOOSHROOM("Mooshroom"),
    MULE("Mule"),
    OCELOT("Ocelot"),
    PIG("Pig"),
    RABBIT("Rabbit"),
    SHEEP("Sheep"),
    SKELETON_HORSE("SkeletonHorse"),
    SQUID("Squid"),
    VILLAGER("Villager"),
    WOLF("Wolf"),
    ZOMBIE_HOSE("ZombieHorse");

    private String name;

    EntityTypes( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
