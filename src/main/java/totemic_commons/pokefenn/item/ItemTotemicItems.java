package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
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
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.music.IMusic;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/12/13
 * Time: 19:19
 */
public class ItemTotemicItems extends ItemTotemic implements IMusic
{

    private static final String[] ITEMS_NAMES = new String[]{"leaf", "cedarStick", "cedarMaker", "flute", "fluteInfused", "cedarBark", "barkStickCedar"};

    public int time = 0;

    public static int leaf = 0;
    public static int cedarStick = 1;
    public static int cedarMaker = 2;
    public static int flute = 3;
    public static int fluteInfused = 4;
    public static int cedarBark = 5;
    public static int barkStickCedar = 6;

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemTotemicItems()
    {
        super();
        setHasSubtypes(true);
        maxStackSize = 64;
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity player, int par4, boolean par5)
    {
        if(!world.isRemote)
        {
            if(player instanceof EntityPlayer)
            {
                if(((EntityPlayer) player).getHeldItem() != null && ((EntityPlayer) player).getHeldItem().getItem() == this)
                if(itemStack.getItemDamage() == flute || itemStack.getItemDamage() == fluteInfused)
                {
                    fluteUpdate((EntityPlayer) player);
                }
            }
        }
    }

    public void fluteUpdate(EntityPlayer player)
    {
        if(player.getHeldItem() != null && player.getHeldItem().getItem() == this)
        {
            player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 30, 1));
        }
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if(!world.isRemote)
        {
            if(itemStack.getItemDamage() == cedarMaker)
                cedarCreatorEffect(itemStack, player, world);

        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            if(itemStack.getItemDamage() == fluteInfused || itemStack.getItemDamage() == flute)
                fluteEffect(itemStack, player, world);
        }
        return itemStack;
    }


    public void fluteEffect(ItemStack stack, EntityPlayer player, World world)
    {
        time++;
        if(time >= 15 && !player.isSneaking())
        {
            time = 0;
            TotemUtil.playMusicFromItem(world, player, this.musicEnum(stack, world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), (int) player.posX, (int) player.posY, (int) player.posZ, this.getRange(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getMaximumMusic(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getMusicOutput(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player));
            particlesAllAround(world, player.posX, player.posY, player.posZ);
            return;
        }
        if(time >= 15 && player.isSneaking())
        {
            time = 0;
            TotemUtil.playMusicFromItemForCeremonySelector(stack, player, (int) player.posX, (int) player.posY, (int) player.posZ, musicEnum(stack, world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player), this.getRange(world, (int) player.posX, (int) player.posY, (int) player.posZ, true, player));
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
                            ((EntityAnimal) entity).targetTasks.addTask(5, new EntityAITempt((EntityCreature) entity, 1, this, false));
                        if(entity instanceof EntityVillager)
                            ((EntityVillager) entity).targetTasks.addTask(5, new EntityAITempt((EntityCreature) entity, 0.5, this, false));
                    }

                }
            }
    }

    public void particlesAllAround(World world, double x, double y, double z)
    {
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x + 0.5D, (double) y + 1.2D, (double) z + 0.5D, 2, 0.5D, 0.0D, 0.5D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x, (double) y + 1.2D, (double) z, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x + 0.5D, (double) y + 1.2D, (double) z, 2, 0.0D, 0.0D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x, (double) y + 1.2D, (double) z + 0.5D, 2, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public void cedarCreatorEffect(ItemStack itemStack, EntityPlayer player, World world)
    {
        MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);

        if(block != null && !player.isSneaking())
        {
            Block blockQuery = world.getBlock(block.blockX, block.blockY, block.blockZ);

            if(blockQuery instanceof BlockSapling && blockQuery != ModBlocks.totemSapling)
            {
                world.setBlock(block.blockX, block.blockY, block.blockZ, ModBlocks.totemSapling);
                itemStack.stackSize--;
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {

        StringBuilder unlocalizedName = new StringBuilder();
        int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, ITEMS_NAMES.length - 1);

        unlocalizedName.append("item.");
        unlocalizedName.append(Strings.RESOURCE_PREFIX);
        unlocalizedName.append(ITEMS_NAMES[meta]);

        return unlocalizedName.toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        int j = MathHelper.clamp_int(meta, 0, ITEMS_NAMES.length - 1);
        return icons[j];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[ITEMS_NAMES.length];

        for(int i = 0; i < ITEMS_NAMES.length; ++i)
            icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + ITEMS_NAMES[i]);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    {
        for(int meta = 0; meta < ITEMS_NAMES.length; ++meta)
            list.add(new ItemStack(id, 1, meta));
    }

    @Override
    public MusicEnum musicEnum(ItemStack itemStack, World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return itemStack != null && (itemStack.getItemDamage() == flute || itemStack.getItemDamage() == fluteInfused) ? MusicEnum.FLUTE_MUSIC : null;
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
