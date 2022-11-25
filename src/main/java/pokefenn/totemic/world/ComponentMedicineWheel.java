package pokefenn.totemic.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.block.totem.BlockTotemBase;
import pokefenn.totemic.block.totem.BlockTotemPole;
import pokefenn.totemic.configuration.ModConfig;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModVillagers;
import pokefenn.totemic.lib.WoodVariant;
import pokefenn.totemic.tileentity.totem.TileTotemPole;

public class ComponentMedicineWheel extends StructureVillagePieces.Village
{
    private WoodVariant poleWood = WoodVariant.CEDAR;

    public ComponentMedicineWheel()
    {
    }

    public ComponentMedicineWheel(Start start, int type, Random rand, StructureBoundingBox bb, EnumFacing facing)
    {
        super(start, type);
        this.setCoordBaseMode(facing);
        this.boundingBox = bb;

        if(rand.nextBoolean())
        {
            switch(type)
            {
            default:
            case 1: //Desert
                poleWood = WoodVariant.OAK;
                break;
            case 2: //Savanna
                poleWood = WoodVariant.ACACIA;
                break;
            case 3: //Taiga
                poleWood = WoodVariant.SPRUCE;
                break;
            }
        }
        else
            poleWood = WoodVariant.CEDAR;
    }

    public static ComponentMedicineWheel createPiece(StructureVillagePieces.Start startPiece, List<StructureComponent> pieces,
            Random random, int strucMinX, int strucMinY, int strucMinZ, EnumFacing facing, int type)
    {
        StructureBoundingBox bb = StructureBoundingBox.getComponentToAddBoundingBox(strucMinX, strucMinY, strucMinZ, 0, 0, 0,  9, 6, 9, facing);
        if(canVillageGoDeeper(bb) && StructureComponent.findIntersecting(pieces, bb) == null)
            return new ComponentMedicineWheel(startPiece, type, random, bb, facing);
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

        fillWithAir(world, bb, 2, 0, 0,  6, 5, 0);
        fillWithAir(world, bb, 1, 0, 1,  7, 5, 1);
        fillWithAir(world, bb, 0, 0, 2,  8, 5, 6);
        fillWithAir(world, bb, 1, 0, 7,  7, 5, 7);
        fillWithAir(world, bb, 2, 0, 8,  6, 5, 8);

        //Create Totem Pole
        setBlockState(world, ModBlocks.totem_base.getDefaultState().withProperty(BlockTotemBase.FACING, EnumFacing.SOUTH).withProperty(BlockTotemBase.WOOD, poleWood), 4, 0, 4, bb);
        List<TotemEffect> totemList = new ArrayList<>(TotemicRegistries.totemEffects().getValuesCollection());
        for(int i = 0; i < 5; i++)
        {
            int x = 4, y = 1+i, z = 4;
            setBlockState(world, ModBlocks.totem_pole.getDefaultState().withProperty(BlockTotemBase.FACING, EnumFacing.SOUTH).withProperty(BlockTotemPole.WOOD, poleWood), x, y, z, bb);
            BlockPos pos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileTotemPole)
                ((TileTotemPole) tile).setEffect(totemList.get(random.nextInt(totemList.size())));
        }

        //Place music instruments and torches
        setBlockState(world, ModBlocks.drum.getDefaultState(), 4, 0, 0, bb);
        setBlockState(world, ModBlocks.drum.getDefaultState(), 0, 0, 4, bb);
        setBlockState(world, ModBlocks.drum.getDefaultState(), 8, 0, 4, bb);

        IBlockState log = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
        IBlockState log_z = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, EnumAxis.Z));
        for(int i = 0; i < 3; i++)
            setBlockState(world, log, 4, i, 8, bb);
        setBlockState(world, log_z, 4, 3, 8, bb);
        setBlockState(world, log_z, 4, 3, 7, bb);
        setBlockState(world, ModBlocks.wind_chime.getDefaultState(), 4, 2, 7, bb);

        if(!isZombieInfested)
        {
            setBlockState(world, ModBlocks.totem_torch.getDefaultState(), 1, 0, 1, bb);
            setBlockState(world, ModBlocks.totem_torch.getDefaultState(), 7, 0, 1, bb);
            setBlockState(world, ModBlocks.totem_torch.getDefaultState(), 7, 0, 7, bb);
            setBlockState(world, ModBlocks.totem_torch.getDefaultState(), 1, 0, 7, bb);
        }

        //Clear above and make ground level
        IBlockState ground = (structureType != 1) ? Blocks.DIRT.getDefaultState() : Blocks.SAND.getDefaultState();
        IBlockState grass = getBiomeSpecificBlockState(Blocks.GRASS.getDefaultState());
        for(int z = 0; z < 9; z++)
            for(int x = 0; x < 9; x++)
            {
                //Only replace inside the circle
                if((x == 0 || x == 8) && (z <= 1 || z >= 7)  ||  (x == 1 || x == 7) && (z == 0 || z == 8))
                    continue;
                clearCurrentPositionBlocksUpwards(world, x, 6, z, bb);
                replaceAirAndLiquidDownwards(world, ground, x, -1, z, bb);
                if(getBlockStateFromPos(world, x, -1, z, bb).getBlock() == Blocks.DIRT)
                    setBlockState(world, grass, x, -1, z, bb);
            }

        //Create Medicine Wheel
        IBlockState cobble = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
        if(cobble.getBlock() == Blocks.SANDSTONE)
            cobble = Blocks.RED_SANDSTONE.getDefaultState();
        fillWithBlocks(world, bb, 4, -1, 0,  4, -1, 8, cobble, cobble, false);
        fillWithBlocks(world, bb, 0, -1, 4,  8, -1, 4, cobble, cobble, false);

        setBlockState(world, cobble, 5, -1, 0, bb);
        setBlockState(world, cobble, 6, -1, 0, bb);
        setBlockState(world, cobble, 7, -1, 1, bb);
        setBlockState(world, cobble, 8, -1, 2, bb);
        setBlockState(world, cobble, 8, -1, 3, bb);

        setBlockState(world, cobble, 8, -1, 5, bb);
        setBlockState(world, cobble, 8, -1, 6, bb);
        setBlockState(world, cobble, 7, -1, 7, bb);
        setBlockState(world, cobble, 6, -1, 8, bb);
        setBlockState(world, cobble, 5, -1, 8, bb);

        setBlockState(world, cobble, 3, -1, 8, bb);
        setBlockState(world, cobble, 2, -1, 8, bb);
        setBlockState(world, cobble, 1, -1, 7, bb);
        setBlockState(world, cobble, 0, -1, 6, bb);
        setBlockState(world, cobble, 0, -1, 5, bb);

        setBlockState(world, cobble, 0, -1, 3, bb);
        setBlockState(world, cobble, 0, -1, 2, bb);
        setBlockState(world, cobble, 1, -1, 1, bb);
        setBlockState(world, cobble, 2, -1, 0, bb);
        setBlockState(world, cobble, 3, -1, 0, bb);

        spawnVillagers(world, bb, 4, 1, 2,  1);
        return true;
    }

    @Override
    protected VillagerProfession chooseForgeProfession(int count, VillagerProfession prof)
    {
        if(ModConfig.general.enableMedicineMen)
            return ModVillagers.profTotemist;
        else
            return super.chooseForgeProfession(count, prof);
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tag)
    {
        super.writeStructureToNBT(tag);
        tag.setByte("PoleWood", (byte) poleWood.getID());
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tag, TemplateManager templateMgr)
    {
        super.readStructureFromNBT(tag, templateMgr);
        poleWood = WoodVariant.fromID(tag.getInteger("PoleWood"));
    }

    public static class CreationHandler implements IVillageCreationHandler
    {
        @Override
        public PieceWeight getVillagePieceWeight(Random random, int size)
        {
            return new PieceWeight(ComponentMedicineWheel.class, 15, MathHelper.getInt(random, 0 + size, 1 + size));
        }

        @Override
        public Class<?> getComponentClass()
        {
            return ComponentMedicineWheel.class;
        }

        @Override
        public Village buildComponent(PieceWeight villagePiece, Start startPiece, List<StructureComponent> pieces,
                Random random, int strucMinX, int strucMinY, int strucMinZ, EnumFacing facing, int type)
        {
            return ComponentMedicineWheel.createPiece(startPiece, pieces, random, strucMinX, strucMinY, strucMinZ, facing, type);
        }
    }
}
