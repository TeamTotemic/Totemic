/*package totemic_commons.pokefenn.item.equipment.music;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.music.IMusic;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

import java.util.List;

public class ItemInfusedFlute extends ItemTotemic implements IMusic
{
    public int time = 0;

    public ItemInfusedFlute()
    {
        super();
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.SHAMAN_FLUTE_NAME);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("Like the Pied Piper of old");
        list.add("Is Imbued: " + (stack.getItemDamage() == 0 ? "False" : "True"));
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity player, int par4, boolean par5)
    {
        if(!world.isRemote)
        {
            if(player instanceof EntityPlayer)
                if(((EntityPlayer) player).getHeldItem() != null && ((EntityPlayer) player).getHeldItem().getItem() == ModItems.shamanFlute)
                {
                    ((EntityPlayer) player).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 30, 1));
                }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    {
        for(int meta = 0; meta < 2; ++meta)
            list.add(new ItemStack(id, 1, meta));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            time++;
            if(time >= 15 && !player.isSneaking())
            {
                time = 0;
                TotemUtil.playMusicFromItem(world, player, this.musicEnum(), (int) player.posX, (int) player.posY, (int) player.posZ, this.getRange(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getMaximumMusic(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getMusicOutput(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player));
                particlesAllAround(world, player.posX, player.posY, player.posZ);
                return stack;
            }
            if(time >= 15 && player.isSneaking())
            {
                time = 0;
                TotemUtil.playMusicFromItemForCeremonySelector(stack, player, (int) player.posX, (int) player.posY, (int) player.posZ, musicEnum(), this.getRange(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player));
                particlesAllAround(world, player.posX, player.posY, player.posZ);
            }
            if(stack.getItemDamage() == 1)
                if(EntityUtil.getEntitiesInRange(world, player.posX, player.posY, player.posZ, 2, 2) != null)
                {
                    for(Entity entity : EntityUtil.getEntitiesInRange(world, player.posX, player.posY, player.posZ, 2, 2))
                    {
                        if(entity instanceof EntityAnimal || entity instanceof EntityVillager)
                        {
                            if(entity instanceof EntityAnimal)
                                ((EntityAnimal) entity).targetTasks.addTask(5, new EntityAITempt((EntityCreature) entity, 1, ModItems.shamanFlute, false));
                            if(entity instanceof EntityVillager)
                                ((EntityVillager) entity).targetTasks.addTask(5, new EntityAITempt((EntityCreature) entity, 0.5, ModItems.shamanFlute, false));
                        }

                    }
                }

        }
        return stack;
    }

    public void particlesAllAround(World world, double x, double y, double z)
    {
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x + 0.5D, (double) y + 1.2D, (double) z + 0.5D, 2, 0.5D, 0.0D, 0.5D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x, (double) y + 1.2D, (double) z, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x + 0.5D, (double) y + 1.2D, (double) z, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x, (double) y + 1.2D, (double) z + 0.5D, 2, 0.0D, 0.0D, 0.0D, 0.0D);
    }


    @Override
    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 60;
    }

    @Override
    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return player.getHeldItem() != null && player.getHeldItem().getItemDamage() == 0 ? 3 : 7;
    }

    @Override
    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 7;
    }
}
*/