package coda.gonefishing.registry;

import coda.gonefishing.NaughtyList;
import coda.gonefishing.common.entity.Nutcracker;
import coda.gonefishing.common.entity.NutcrackerGeneral;
import coda.gonefishing.common.entity.ThrownCandyCane;
import coda.gonefishing.common.entity.WoodenHorse;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NLEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NaughtyList.MOD_ID);

    public static final RegistryObject<EntityType<Nutcracker>> NUTCRACKER = ENTITIES.register("nutcracker", () -> EntityType.Builder.of(Nutcracker::new, MobCategory.MONSTER).sized(1.0F, 1.4F).setTrackingRange(64).build("nutcracker"));
    public static final RegistryObject<EntityType<WoodenHorse>> WOODEN_HORSE = ENTITIES.register("wooden_horse", () -> EntityType.Builder.of(WoodenHorse::new, MobCategory.MONSTER).sized(1.2F, 1.2F).setTrackingRange(64).build("wooden_horse"));
    public static final RegistryObject<EntityType<ThrownCandyCane>> CANDY_CANE = ENTITIES.register("candy_cane", () -> EntityType.Builder.<ThrownCandyCane>of(ThrownCandyCane::new, MobCategory.MISC).sized(0.25F, 0.25F).build("candy_cane"));
    public static final RegistryObject<EntityType<NutcrackerGeneral>> NUTCRACKER_GENERAL = ENTITIES.register("nutcracker_general", () -> EntityType.Builder.of(NutcrackerGeneral::new, MobCategory.MONSTER).sized(1.0F, 1.55F).setTrackingRange(64).build("nutcracker_general"));

}
