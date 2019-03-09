package pokefenn.totemic.item.music;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemInfusedFlute extends ItemFlute {
    public ItemInfusedFlute(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if(!world.isRemote && !player.isSneaking())
            temptEntities(world, player.posX, player.posY, player.posZ);

        return super.onItemRightClick(world, player, hand);
    }

    private void temptEntities(World world, double x, double y, double z) {
        //TODO
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
