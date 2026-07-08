package totemic_commons.pokefenn.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class ItemCeremonyCheat extends ItemTotemic
{
    public ItemCeremonyCheat()
    {
        super(Strings.CEREMONY_CHEAT_NAME);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("item.totemic:ceremonyCheat.tooltip1"));
        list.add(StatCollector.translateToLocal("item.totemic:ceremonyCheat.tooltip2"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.epic;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof TileTotemBase)
        {
            if(world.isRemote)
                return true;

            TileTotemBase totem = (TileTotemBase)tile;
            if(totem.isDoingStartup())
            {
                totem.startCeremony();
                // Make sure that Ceremonies with non-instant effects which drain music don't stop immediately
                Ceremony cer = totem.currentCeremony;
                if(cer.getMusicPer5() > 0)
                    totem.totalCeremonyMelody = cer.getMusicNeeded();
                
                return true;
            }
        }
        return false;
    }
}
