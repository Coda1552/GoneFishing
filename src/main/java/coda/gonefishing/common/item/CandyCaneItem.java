package coda.gonefishing.common.item;

import coda.gonefishing.common.entity.ThrownCandyCane;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CandyCaneItem extends Item implements Vanishable {
   private final Multimap<Attribute, AttributeModifier> defaultModifiers;

   public CandyCaneItem(Item.Properties p_43381_) {
      super(p_43381_);
      ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 4.0D, AttributeModifier.Operation.ADDITION));
      builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.9D, AttributeModifier.Operation.ADDITION));
      this.defaultModifiers = builder.build();
   }

   public boolean canAttackBlock(BlockState p_43409_, Level p_43410_, BlockPos p_43411_, Player p_43412_) {
      return !p_43412_.isCreative();
   }

   public UseAnim getUseAnimation(ItemStack p_43417_) {
      return UseAnim.SPEAR;
   }

   public int getUseDuration(ItemStack p_43419_) {
      return 72000;
   }

   public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
      if (p_43396_ instanceof Player player) {
         int i = this.getUseDuration(p_43394_) - p_43397_;
         if (i >= 10) {
            ThrownCandyCane thrownCandyCane = new ThrownCandyCane(p_43395_, player, p_43394_);
            thrownCandyCane.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);

            if (player.getAbilities().instabuild) {
               thrownCandyCane.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            p_43395_.addFreshEntity(thrownCandyCane);
            p_43395_.playSound(null, thrownCandyCane, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);

            if (!player.getAbilities().instabuild) {
               player.getInventory().removeItem(p_43394_);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
         }
      }
   }

   public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
      ItemStack itemstack = p_43406_.getItemInHand(p_43407_);
      if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
         return InteractionResultHolder.fail(itemstack);
      } else if (EnchantmentHelper.getRiptide(itemstack) > 0 && !p_43406_.isInWaterOrRain()) {
         return InteractionResultHolder.fail(itemstack);
      } else {
         p_43406_.startUsingItem(p_43407_);
         return InteractionResultHolder.consume(itemstack);
      }
   }

   @Override
   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment != Enchantments.CHANNELING && enchantment != Enchantments.RIPTIDE && enchantment != Enchantments.IMPALING && super.canApplyAtEnchantingTable(stack, enchantment);
   }

   public boolean hurtEnemy(ItemStack p_43390_, LivingEntity p_43391_, LivingEntity p_43392_) {
      p_43390_.hurtAndBreak(1, p_43392_, (p_43414_) -> {
         p_43414_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
      });
      return true;
   }

   public boolean mineBlock(ItemStack p_43399_, Level p_43400_, BlockState p_43401_, BlockPos p_43402_, LivingEntity p_43403_) {
      if ((double)p_43401_.getDestroySpeed(p_43400_, p_43402_) != 0.0D) {
         p_43399_.hurtAndBreak(2, p_43403_, (p_43385_) -> {
            p_43385_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
         });
      }

      return true;
   }

   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43383_) {
      return p_43383_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_43383_);
   }

   public int getEnchantmentValue() {
      return 1;
   }
}