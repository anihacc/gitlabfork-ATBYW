package net.azagwen.atbyw.item;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.azagwen.atbyw.dev_tools.AutoJsonWriter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AtbywDebugStick extends Item {
    public final List<BlockState> invalidStates = Lists.newArrayList();
    public final List<BlockState> missingStates = Lists.newArrayList();
    private DebugMode currentMode = DebugMode.LIST_INVALID_STATES;

    public AtbywDebugStick(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (!world.isClient) {
            if (miner.getStackInHand(Hand.MAIN_HAND).isOf(this)) {
                var stack = miner.getStackInHand(Hand.MAIN_HAND);
                this.currentMode = switch (this.currentMode) {
                    case LIST_INVALID_STATES -> DebugMode.LIST_MISSING_STATES;
                    case LIST_MISSING_STATES -> DebugMode.EXPORT_LISTINGS;
                    case EXPORT_LISTINGS -> DebugMode.LIST_INVALID_STATES;
                };
                miner.sendMessage(new LiteralText(this.currentMode.asString()), true);
                var nbtCompound = stack.getOrCreateSubNbt("DebugProperty");
                nbtCompound.putString("debugMode", this.currentMode.asString());
            }
        }

        return false;
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        var pos = context.getBlockPos();
        var player = context.getPlayer();

        if (!player.isCreativeLevelTwoOp()) {
            return ActionResult.FAIL;
        } else {
            System.out.println(world.getBlockState(pos).toString());
            switch (this.currentMode) {
                case LIST_INVALID_STATES -> this.updateList(this.invalidStates, player, world.getBlockState(pos));
                case LIST_MISSING_STATES -> this.updateList(this.missingStates, player, world.getBlockState(pos));
                case EXPORT_LISTINGS -> this.writeFiles();
            }
            return ActionResult.success(world.isClient);
        }
    }

    private void updateList(List<BlockState> list, PlayerEntity player, BlockState state) {
        if (!player.isSneaking()) {
            list.add(state);
            player.sendMessage(new LiteralText(String.format("§aadded %s's current state to %s", state.getBlock().toString(), this.currentMode.asString())), true);
        } else {
            list.remove(state);
            player.sendMessage(new LiteralText(String.format("§cremoved %s's current state from %s", state.getBlock().toString(), this.currentMode.asString())), true);
        }
    }

    private void writeFiles() {
        var writer = new AutoJsonWriter();
        var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        var now = LocalDateTime.now();

        var invalidStatesArray = new JsonArray();
        this.invalidStates.forEach((state) -> invalidStatesArray.add(state.toString()));
        var invalidStates = new JsonObject();
        invalidStates.add("invalid_states", invalidStatesArray);
        writer.write(String.format("invalid_states_%s", dtf.format(now)), invalidStates);

        var missingStatesArray = new JsonArray();
        this.missingStates.forEach((state) -> missingStatesArray.add(state.toString()));
        var missingStates = new JsonObject();
        missingStates.add("missing_states", invalidStatesArray);
        writer.write(String.format("missing_states_%s", dtf.format(now)), missingStates);

    }

    public enum DebugMode implements StringIdentifiable {
        LIST_INVALID_STATES("invalid_state_listing"),
        LIST_MISSING_STATES("missing_state_listing"),
        EXPORT_LISTINGS("export_listings");

        private final String name;
        DebugMode(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
