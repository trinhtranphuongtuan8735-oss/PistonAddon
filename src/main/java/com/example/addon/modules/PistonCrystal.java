package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class PistonCrystal extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("Khoang cach tim muc tiêu.")
        .defaultValue(4.5)
        .min(1.0)
        .sliderMax(6.0)
        .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
        .name("rotate")
        .description("Xoay huong nhin khi dat khoi.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay-ticks")
        .description("Delay giua cac buoc.")
        .defaultValue(1)
        .min(0)
        .sliderMax(10)
        .build()
    );

    private enum Stage {
        PLACE_PISTON,
        PLACE_CRYSTAL,
        POWER_PISTON,
        BREAK_CRYSTAL
    }

    private Stage currentStage = Stage.PLACE_PISTON;
    private PlayerEntity target;
    private BlockPos pistonPos;
    private BlockPos crystalPos;
    private BlockPos redstonePos;
    private int timer = 0;

    public PistonCrystal() {
        super(AddonTemplate.CATEGORY, "piston-crystal", "Combo Piston Crystal PvP 1.21.1.");
    }

    @Override
    public void onActivate() {
        currentStage = Stage.PLACE_PISTON;
        target = null;
        timer = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;

        if (timer > 0) {
            timer--;
            return;
        }

        target = mc.world.getPlayers().stream()
            .filter(p -> p != mc.player && p.isAlive() && mc.player.distanceTo(p) <= range.get())
            .findFirst()
            .orElse(null);

        if (target == null) return;

        switch (currentStage) {
            case PLACE_PISTON -> {
                BlockPos targetHead = target.getBlockPos().up();
                for (Direction dir : Direction.Type.HORIZONTAL) {
                    BlockPos possiblePiston = targetHead.offset(dir);
                    if (mc.world.getBlockState(possiblePiston).isReplaceable()) {
                        pistonPos = possiblePiston;
                        crystalPos = targetHead;
                        redstonePos = pistonPos.up();

                        FindItemResult piston = InvUtils.findInHotbar(Items.PISTON);
                        if (!piston.found()) return;

                        if (BlockUtils.place(pistonPos, piston, rotate.get(), 50)) {
                            currentStage = Stage.PLACE_CRYSTAL;
                            timer = delay.get();
                            break;
                        }
                    }
                }
            }
            case PLACE_CRYSTAL -> {
                FindItemResult crystal = InvUtils.findInHotbar(Items.END_CRYSTAL);
                FindItemResult obsidian = InvUtils.findInHotbar(Items.OBSIDIAN);

                if (!crystal.found()) return;

                BlockPos obsidianPos = crystalPos.down();
                if (mc.world.getBlockState(obsidianPos).isReplaceable() && obsidian.found()) {
                    BlockUtils.place(obsidianPos, obsidian, rotate.get(), 50);
                }

                if (BlockUtils.place(crystalPos, crystal, rotate.get(), 50)) {
                    currentStage = Stage.POWER_PISTON;
                    timer = delay.get();
                }
            }
            case POWER_PISTON -> {
                FindItemResult redstone = InvUtils.findInHotbar(Items.REDSTONE_TORCH, Items.REDSTONE_BLOCK);
                if (!redstone.found()) return;

                if (BlockUtils.place(redstonePos, redstone, rotate.get(), 50)) {
                    currentStage = Stage.BREAK_CRYSTAL;
                    timer = delay.get();
                }
            }
            case BREAK_CRYSTAL -> {
                for (var entity : mc.world.getEntities()) {
                    if (entity instanceof EndCrystalEntity crystalEntity && crystalEntity.getBlockPos().equals(crystalPos)) {
                        mc.interactionManager.attackEntity(mc.player, crystalEntity);
                        mc.player.swingHand(Hand.MAIN_HAND);

                        currentStage = Stage.PLACE_PISTON;
                        timer = delay.get();
                        break;
                    }
                }
            }
        }
    }
}
