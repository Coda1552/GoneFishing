package coda.gonefishing;

import coda.gonefishing.common.WinterRaid;
import coda.gonefishing.common.WinterRaidSavedData;
import coda.gonefishing.common.entity.Nutcracker;
import coda.gonefishing.common.entity.NutcrackerGeneral;
import coda.gonefishing.common.entity.util.WinterRaider;
import coda.gonefishing.common.entity.WoodenHorse;
import coda.gonefishing.registry.NLEntities;
import coda.gonefishing.registry.NLItems;
import coda.gonefishing.registry.NLSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;

@Mod(NaughtyList.MOD_ID)
public class NaughtyList {
    public static final String MOD_ID = "naughtylist";

    // todo - check if naughty raids AND normal raids work at the same time
    public NaughtyList() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(this::blockBroken);
        forgeBus.addListener(this::levelTick);
        forgeBus.addListener(this::addGoals);
        bus.addListener(this::createAttributes);

        NLEntities.ENTITIES.register(bus);
        NLItems.ITEMS.register(bus);
        NLSounds.SOUNDS.register(bus);
    }

    private void createAttributes(EntityAttributeCreationEvent e) {
        e.put(NLEntities.NUTCRACKER.get(), Nutcracker.createAttributes().build());
        e.put(NLEntities.WOODEN_HORSE.get(), WoodenHorse.createAttributes().build());
        e.put(NLEntities.NUTCRACKER_GENERAL.get(), NutcrackerGeneral.createAttributes().build());
    }

    private void levelTick(TickEvent.LevelTickEvent e) {
        Level eLevel = e.level;

        if (eLevel instanceof ServerLevel level) {
            WinterRaidSavedData raids = level.getDataStorage().computeIfAbsent(tag -> WinterRaidSavedData.load(level, tag), () -> new WinterRaidSavedData(level), WinterRaidSavedData.getFileId(level.dimensionTypeRegistration()));;

            raids.tick();
        }
    }

    // todo - remove
    private void blockBroken(BlockEvent.BreakEvent e) {
        if (e.getState().is(Blocks.CRYING_OBSIDIAN) && e.getLevel() instanceof ServerLevel level && e.getPlayer() instanceof ServerPlayer player) {
            WinterRaidSavedData raids = level.getDataStorage().computeIfAbsent(tag -> WinterRaidSavedData.load(level, tag), () -> new WinterRaidSavedData(level), WinterRaidSavedData.getFileId(level.dimensionTypeRegistration()));;

            raids.createOrExtendRaid(player);
        }
    }

    @Nullable
    public static WinterRaid getRaidAt(ServerLevel level, BlockPos pos) {
        WinterRaidSavedData raids = level.getDataStorage().computeIfAbsent(tag -> WinterRaidSavedData.load(level, tag), () -> new WinterRaidSavedData(level), WinterRaidSavedData.getFileId(level.dimensionTypeRegistration()));

        return raids.getNearbyRaid(pos, 9216);
    }

    private void addGoals(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof AbstractVillager villager) {
            villager.goalSelector.addGoal(0, new AvoidEntityGoal<>(villager, WinterRaider.class, 1.0F, 1.05D, 1.15D));
        }
    }
}
