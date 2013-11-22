package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TilePlantShredder;

/**
 * Created with IntelliJ IDEA.
 * User: ${Pokefenn}
 * Date: 17/11/13
 * Time: 20:00
 */
public class BlockPlantShredder extends BlockTotemic {

    public BlockPlantShredder(int id){

        super(id, Material.iron);
        this.setUnlocalizedName(Strings.PLANT_SHREDDER_NAME);
        this.setHardness(1F);
        this.setCreativeTab(Totemic.tabsTotem);



    }


    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TilePlantShredder();
    }


    @Override
    @SideOnly(Side.CLIENT)
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){

        if (!player.isSneaking()){
            return false;
        }

        else {
            if (!world.isRemote) {
                TilePlantShredder tilePlantShredder = (TilePlantShredder) world.getBlockTileEntity(x, y, z);

                if(tilePlantShredder != null){




                }

            }

            return true;
        }


            }
}


