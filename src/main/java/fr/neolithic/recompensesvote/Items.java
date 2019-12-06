package fr.neolithic.recompensesvote;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public enum Items {
    TOTEM_OF_UNDYING("totem_of_undying", 0.05f, false),
    SEA_LANTERN("sea_lantern", 0.05f, false),
    SHULKER_SHELL("shulker_shell", 0.25f, false),
    SHULKER_BOX("shulker_box", 0.1f, false),
    BIRCH_LOG("birch_log", 0.08f, false),
    SPRUCE_LOG("spruce_log", 0.08f, false),
    OAK_LOG("oak_log", 0.08f, false),
    ELYTRA("elytra", 0.05f, false),
    NETHER_QUARTZ_ORE("nether_quartz_ore", 0.2f, false),
    EXPERIENCE_BOTTLE("experience_bottle", 0.1f, false),
    FIREWORK_ROCKET("firework_rocket", 0.1f, false),
    SILK_TOUCH_BOOK("silk_touch_book", 0.09f, false),
    FORTUNE_BOOK("fortune_book", 0.07f, false),
    MENDING_BOOK("mending_book", 0.04f, false),
    FLY_1("fly_1", 0.22f, true),
    FLY_2("fly_2", 0.15f, true),
    FLY_3("fly_3", 0.1f, true),
    FLY_4("fly_4", 0.05f, true),
    PHANTOM_POTION("phantom_potion", 0.17f, true),
    CREEPER_POTION("creeper_potion", 0.16f, true),
    MINING_POTION("mining_potion", 0.20f, true),
    SWIMING_POTION("swiming_potion", 0.12f, true),
    ULU("ulu", 0.18f, true),
    INDIAN_SPEAR("indian_spear", 0.21f, true),
    BASEBALL_BAT("baseball_bat", 0.05f, true),
    GOBLIN_PICKAXE("goblin_pickaxe", 0.1f, true),
    GIANT_BOOTS("giant_boots", 0.12f, true),
    COOKED_HORSE("cooked_horse", 0f, true),
    COOKED_BEAR("cooked_bear", 0f, true),
    RAW_HORSE("raw_horse", 0f, true),
    RAW_BEAR("raw_bear", 0f, true),
    CANDY_CANE("candy_cane", 0.001f, true);

    private final String name;
    private final ItemStack item;
    private final Float rewardChance;
    private final Boolean isCustom;

    private Items(String name, Float rewardChance, Boolean isCustom) {
        this.name = name;
        this.rewardChance = rewardChance;
        this.isCustom = isCustom;

        ItemStack item;

        switch (name) {
            case "totem_of_undying":
                item = new ItemStack(Material.TOTEM_OF_UNDYING);
                break;

            case "sea_lantern":
                item = new ItemStack(Material.SEA_LANTERN, 32);
                break;

            case "shulker_shell":
                item = new ItemStack(Material.SHULKER_SHELL);
                break;

            case "shulker_box":
                item = new ItemStack(Material.SHULKER_BOX);
                break;

            case "birch_log":
                item = new ItemStack(Material.BIRCH_LOG, 64);
                break;

            case "spruce_log":
                item = new ItemStack(Material.SPRUCE_LOG, 64);
                break;

            case "oak_log":
                item = new ItemStack(Material.OAK_LOG, 64);
                break;

            case "elytra":
                item = new ItemStack(Material.ELYTRA);
                break;

            case "nether_quartz_ore":
                item = new ItemStack(Material.NETHER_QUARTZ_ORE, 64);
                break;

            case "experience_bottle":
                item = new ItemStack(Material.EXPERIENCE_BOTTLE, 32);
                break;

            case "firework_rocket":
                item = createFireworkRocket();
                break;

            case "silk_touch_book":
                item = createSilkTouchBook();
                break;

            case "fortune_book":
                item = createFortuneBook();
                break;

            case "mending_book":
                item = createMendingBook();
                break;

            case "fly_1":
                item = createFly1();
                break;

            case "fly_2":
                item = createFly2();
                break;

            case "fly_3":
                item = createFly3();
                break;

            case "fly_4":
                item = createFly4();
                break;

            case "phantom_potion":
                item = createPhantomPotion();
                break;

            case "creeper_potion":
                item = createCreeperPotion();
                break;

            case "mining_potion":
                item = createMiningPotion();
                break;

            case "swiming_potion":
                item = createSwimingPotion();
                break;

            case "ulu":
                item = createUlu();
                break;

            case "indian_spear":
                item = createIndianSpear();
                break;

            case "baseball_bat":
                item = createBaseballBat();
                break;

            case "goblin_pickaxe":
                item = createGoblinPickaxe();
                break;

            case "giant_boots":
                item = createGiantBoots();
                break;

            case "cooked_horse":
                item = createCoockedHorse();
                break;

            case "cooked_bear":
                item = createCookedBear();
                break;

            case "raw_horse":
                item = createRawHorse();
                break;

            case "raw_bear":
                item = createRawBear();
                break;

            case "candy_cane":
                item = createCandyCane();
                break;

            default:
                item = new ItemStack(Material.AIR);
                break;
        }

        this.item = item;
    }

    private ItemStack createMendingBook() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) item.getItemMeta();

        itemMeta.addEnchant(Enchantment.MENDING, 1, false);

        item.setItemMeta(itemMeta);
        return item;
    }

    private ItemStack createFortuneBook() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) item.getItemMeta();

        itemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, false);

        item.setItemMeta(itemMeta);
        return item;
    }

    private ItemStack createSilkTouchBook() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) item.getItemMeta();

        itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);

        item.setItemMeta(itemMeta);
        return item;
    }

    private ItemStack createFireworkRocket() {
        ItemStack item = new ItemStack(Material.FIREWORK_ROCKET, 64);
        FireworkMeta itemMeta = (FireworkMeta) item.getItemMeta();

        itemMeta.setPower(5);

        item.setItemMeta(itemMeta);
        return item;
    }

    private ItemStack createCandyCane() {
        ItemStack item = new ItemStack(Material.BAMBOO);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName("§cS§fu§cc§fr§ce §fd§c'§fO§cr§fg§ce");
		itemMeta.setLore(Arrays.asList("§eNoël 2019"));
        itemMeta.setCustomModelData(1);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createRawBear() {
        ItemStack item = new ItemStack(Material.BEEF);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName("§fOurs Cru");
        itemMeta.setCustomModelData(2);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createRawHorse() {
        ItemStack item = new ItemStack(Material.BEEF);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName("§fCheval Cru");
        itemMeta.setCustomModelData(1);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createCookedBear() {
        ItemStack item = new ItemStack(Material.COOKED_BEEF);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName("§fSteak d'Ours");
        itemMeta.setCustomModelData(2);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createCoockedHorse() {
        ItemStack item = new ItemStack(Material.COOKED_BEEF);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName("§fSteak de Cheval");
        itemMeta.setCustomModelData(1);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createGiantBoots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.fromString("ec3474c3-b57c-4fbf-ba1f-a7c5ac9292c5"), "generic.armor", 1, Operation.ADD_NUMBER, EquipmentSlot.FEET));
		itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("dcc66b53-a22c-48cc-afbd-274a02967392"), "generic.movementSpeed", 0.75, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.FEET));
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName("§bBottes de Géant");
		itemMeta.setLore(Arrays.asList("§eCes bottes semblent émettre", "§eune certaine forme de magie", "§eet vous permettraient", "§ed'aller plus vite", "", "§cAttention la durabilité", "§cbaisse avec la distance", "§cparcourue"));
		itemMeta.setCustomModelData(1);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createGoblinPickaxe() {
        ItemStack item = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName("§bPioche de Gobelin");
		itemMeta.setLore(Arrays.asList("§eCette pioche vous confère", "§eune précision accrue et", "§evous permet d'extraire", "§edes minéraux précieux", "§edes roches"));
		itemMeta.setCustomModelData(1);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createBaseballBat() {
        ItemStack item = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.addEnchant(Enchantment.KNOCKBACK, 15, true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName("§bBatte de Baseball");
		itemMeta.setLore(Arrays.asList("§7Recul XV", "§c /!\\ Attention /!\\", "§c  Cet item est à", "§c  usage unique"));
		itemMeta.setCustomModelData(1);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createIndianSpear() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("b3fb96db-e490-4233-b618-292f965c974c"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("63590bf7-46ea-4ca4-b8c7-fc6c99942af3"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("d0b9f488-95ac-4028-8706-762e5aadfb00"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("fe3ba83f-76a6-44ba-9cfe-02fd12dc306c"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.OFF_HAND));
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName("§bLance Indienne");
		itemMeta.setLore(Arrays.asList("§ePermet d'obtenir du", "§esteak de cheval"));
		itemMeta.setCustomModelData(1);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createUlu() {
        ItemStack item = new ItemStack(Material.STONE_AXE);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("1de4ca9e-fe66-4222-966d-73f226e8fecd"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("c26bf64f-1a1a-4425-bc4e-77dc56845f8b"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("10cea9ed-2627-4a6e-b904-52228bbff57b"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("48c680ff-6d3a-4bff-b62c-2badd65cf045"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.OFF_HAND));
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName("§bUlu");
		itemMeta.setLore(Arrays.asList("§eUn couteau inuit", "§epermettant de", "§edépecer un ours"));
		itemMeta.setCustomModelData(1);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createSwimingPotion() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();

        itemMeta.setColor(Color.fromRGB(7, 162, 186));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setDisplayName("§bPotion d'Homme Poisson");
        itemMeta.setLore(Arrays.asList("§eBoire cette potion vous", "§epermettra de devenir", "§eaussi à l'aise sous l'eau", "§eque n'importe quel être", "§esous-marin"));
        itemMeta.setCustomModelData(8);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createMiningPotion() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();

        itemMeta.setColor(Color.fromRGB(254, 194, 0));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setDisplayName("§bBreuvage Nain");
        itemMeta.setLore(Arrays.asList("§eBoire ce breuvage vous", "§epermettra de miner", "§eaussi vite qu'un nain", "§eau détriment de votre", "§edéfense"));
        itemMeta.setCustomModelData(7);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createCreeperPotion() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();

        itemMeta.setColor(Color.fromRGB(0, 135, 6));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setDisplayName("§bSang de Creeper");
        itemMeta.setLore(Arrays.asList("§eBoire cette potion vous", "§epermet de faire fuir les", "§ecreeper autour de vous", "§ependant §620min"));
        itemMeta.setCustomModelData(6);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createPhantomPotion() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();

        itemMeta.setColor(Color.fromRGB(0, 43, 112));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setDisplayName("§bPotion Anti Phantom");
        itemMeta.setLore(Arrays.asList("§eBoire cette potion vous", "§epermet d'éliminer les", "§ephantom autour de vous", "§ependant §620min"));
        itemMeta.setCustomModelData(5);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createFly1() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();

        itemMeta.setColor(Color.fromRGB(0, 255, 255));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setDisplayName("§bPotion de Vol 2min30s");
        itemMeta.setLore(Arrays.asList("§eBoire cette potion", "§evous permet de voler", "§ependant §62min30s"));
        itemMeta.setCustomModelData(1);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createFly2() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();

        itemMeta.setColor(Color.fromRGB(0, 255, 255));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setDisplayName("§bPotion de Vol 5min");
        itemMeta.setLore(Arrays.asList("§eBoire cette potion", "§evous permet de voler", "§ependant §65min"));
        itemMeta.setCustomModelData(2);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createFly3() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();

        itemMeta.setColor(Color.fromRGB(0, 255, 255));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setDisplayName("§bPotion de Vol 10min");
        itemMeta.setLore(Arrays.asList("§eBoire cette potion", "§evous permet de voler", "§ependant §610min"));
        itemMeta.setCustomModelData(3);

        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createFly4() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta itemMeta = (PotionMeta) item.getItemMeta();

        itemMeta.setColor(Color.fromRGB(0, 255, 255));
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setDisplayName("§bPotion de Vol 20min");
        itemMeta.setLore(Arrays.asList("§eBoire cette potion", "§evous permet de voler", "§ependant §620min"));
        itemMeta.setCustomModelData(4);

        item.setItemMeta(itemMeta);

        return item;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getItem() {
        return this.item.clone();
    }

    public Float getRewardChance() {
        return this.rewardChance;
    }

    public Boolean isCustom() {
        return this.isCustom;
    }

    public Boolean compareTo(ItemStack item) {
        item = item.clone();

        if (item.getItemMeta() instanceof Damageable) {
            Damageable itemMeta = (Damageable) item.getItemMeta();
            itemMeta.setDamage(0);
            item.setItemMeta((ItemMeta) itemMeta);
        }

        return this.item.isSimilar(item);
    }

    public static Boolean contains(String test) {
        for (Items item : Items.values()) {
            if (item.getName().equals(test)) {
                return true;
            }
        }

        return false;
    }

    public static Boolean blockedItemsContains(ItemStack testItem) {
        testItem = testItem.clone();

        if (testItem.getItemMeta() instanceof Damageable) {
            Damageable testItemMeta = (Damageable) testItem.getItemMeta();
            testItemMeta.setDamage(0);
            testItem.setItemMeta((ItemMeta) testItemMeta);
        }

        for (Items item : Items.values()) {
            if (item.isCustom && item.getItem().isSimilar(testItem)) {
                return true;
            }
        }

        return false;
    }

    public static Boolean contains(ItemStack testItem) {
        testItem = testItem.clone();

        if (testItem.getItemMeta() instanceof Damageable) {
            Damageable testItemMeta = (Damageable) testItem.getItemMeta();
            testItemMeta.setDamage(0);
            testItem.setItemMeta((ItemMeta) testItemMeta);
        }

        for (Items item : Items.values()) {
            if (item.getItem().isSimilar(testItem)) {
                return true;
            }
        }

        return false;
    }

    public static Items getItemNamed(String name) {
        for (Items item : Items.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }

        throw new IllegalArgumentException();
    }
}