package pokefenn.totemic.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import pokefenn.totemic.ModBlocks;
import pokefenn.totemic.ModVillagers;
import pokefenn.totemic.block.tipi.BlockTipi;

public class ComponentTipi extends StructureVillagePieces.Village
{
    public ComponentTipi()
    {
    }

    public ComponentTipi(Start start, int type, Random rand, StructureBoundingBox bb, EnumFacing facing)
    {
        super(start, type);
        this.setCoordBaseMode(facing);
        this.boundingBox = bb;
    }

    public static ComponentTipi createPiece(StructureVillagePieces.Start startPiece, List<StructureComponent> pieces,
            Random random, int strucMinX, int strucMinY, int strucMinZ, EnumFacing facing, int type)
    {
        StructureBoundingBox bb = StructureBoundingBox.getComponentToAddBoundingBox(strucMinX, strucMinY, strucMinZ, 0, 0, 0,  5, 6, 5, facing);
        if(canVillageGoDeeper(bb) && StructureComponent.findIntersecting(pieces, bb) == null)
            return new ComponentTipi(startPiece, type, random, bb, facing);
        else
            return null;
    }

    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox bb)
    {
        if(averageGroundLvl < 0)
        {
            averageGroundLvl = getAverageGroundLevel(world, bb);
            if(averageGroundLvl < 0)
                return true;
            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 6 - 1, 0);
        }

        fillWithAir(world, bb, 0, 0, 0,  4, 5, 4);

        placeTipi(world, 2, 0, 2, EnumFacing.NORTH, bb);
        setBlockState(world, ModBlocks.totem_torch.getDefaultState(), 0, 0, 0, bb);
        setBlockState(world, ModBlocks.totem_torch.getDefaultState(), 4, 0, 0, bb);

        IBlockState ground = (structureType != 1) ? Blocks.DIRT.getDefaultState() : Blocks.SAND.getDefaultState();
        IBlockState grass = getBiomeSpecificBlockState(Blocks.GRASS.getDefaultState());
        for(int z = 0; z < 5; z++)
            for(int x = 0; x < 5; x++)
            {
                clearCurrentPositionBlocksUpwards(world, x, 6, z, bb);
                replaceAirAndLiquidDownwards(world, ground, x, -1, z, bb);
                if(getBlockStateFromPos(world, x, -1, z, bb).getBlock() == Blocks.DIRT)
                    setBlockState(world, grass, x, -1, z, bb);
            }

        spawnVillagers(world, bb, 2, 1, 1,  1);
        return true;
    }

    private void placeTipi(World world, int x, int y, int z, EnumFacing dir, StructureBoundingBox bb)
    {
        //Place dummy blocks
        for(int i = 0; i < 2; i++)
        {
            for(EnumFacing blockDir : EnumFacing.HORIZONTALS)
            {
                if(blockDir == dir)
                    continue;
                Vec3i dirVec = blockDir.getDirectionVec();
                setBlockState(world, ModBlocks.dummy_tipi.getDefaultState(), x + dirVec.getX(), y + i, z + dirVec.getZ(), bb);
            }
        }
        setBlockState(world, ModBlocks.dummy_tipi.getDefaultState(), x, y + 3, z, bb);
        setBlockState(world, ModBlocks.dummy_tipi.getDefaultState(), x, y + 4, z, bb);
        setBlockState(world, ModBlocks.dummy_tipi.getDefaultState(), x, y + 5, z, bb);

        //Place Tipi block itself
        setBlockState(world, ModBlocks.tipi.getDefaultState().withProperty(BlockTipi.FACING, dir), x, y, z, bb);
    }

    @Override
    protected VillagerProfession chooseForgeProfession(int count, VillagerProfession prof)
    {
        return ModVillagers.profTotemist;
    }

    public static class CreationHandler implements IVillageCreationHandler
    {
        @Override
        public PieceWeight getVillagePieceWeight(Random random, int size)
        {
            return new PieceWeight(ComponentTipi.class, 15, MathHelper.getInt(random, 1 + size, 2 + size));
        }

        @Override
        public Class<?> getComponentClass()
        {
            return ComponentTipi.class;
        }

        @Override
        public Village buildComponent(PieceWeight villagePiece, Start startPiece, List<StructureComponent> pieces,
                Random random, int strucMinX, int strucMinY, int strucMinZ, EnumFacing facing, int type)
        {
            return ComponentTipi.createPiece(startPiece, pieces, random, strucMinX, strucMinY, strucMinZ, facing, type);
        }
    }
}
