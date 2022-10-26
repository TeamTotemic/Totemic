package pokefenn.totemic.item.music;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import pokefenn.totemic.init.ModItems;

public class JingleDressItem extends ArmorItem {
    public static final ArmorMaterial MATERIAL = new ArmorMaterial() {
        @Override
        public float getToughness() { return 0.0F; }

        @Override
        public Ingredient getRepairIngredient() { return Ingredient.of(ModItems.buffalo_hide.get()); }

        @Override
        public String getName() { return "totemic:jingle_dress"; }

        @Override
        public float getKnockbackResistance() { return 0.0F; }

        @Override
        public SoundEvent getEquipSound() { return SoundEvents.ARMOR_EQUIP_LEATHER; }

        @Override
        public int getEnchantmentValue() { return 15; }

        @Override
        public int getDurabilityForSlot(EquipmentSlot pSlot) { return 5 * 15; }

        @Override
        public int getDefenseForSlot(EquipmentSlot pSlot) { return 1; }
    };

    public JingleDressItem(Properties pProperties) {
        super(MATERIAL, EquipmentSlot.LEGS, pProperties);
    }
}
