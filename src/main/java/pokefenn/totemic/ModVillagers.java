package pokefenn.totemic;

import java.util.Random;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import pokefenn.totemic.lib.Resources;

@EventBusSubscriber(modid = Totemic.MOD_ID)
public final class ModVillagers
{
    @SubscribeEvent
    public static void init(RegistryEvent.Register<VillagerProfession> event)
    {
        VillagerProfession totemicProf = new VillagerProfession(Resources.PREFIX_MOD + "totemist", "", ""); //TODO: Textures
        event.getRegistry().register(totemicProf);

        new VillagerCareer(totemicProf, "totemist")
            .addTrade(1, new EmeraldForItems(Item.getItemFromBlock(ModBlocks.cedar_log), new PriceInfo(20, 26)),
                         new EmeraldForItems(Item.getItemFromBlock(ModBlocks.stripped_cedar_log), new PriceInfo(12, 16)),
                         new EmeraldsForItemsWithMeta(new ItemStack(ModItems.buffalo_items, 1, 0), new PriceInfo(9, 12)),
                         new EmeraldsForItemsWithMeta(new ItemStack(ModItems.buffalo_items, 1, 1), new PriceInfo(6, 8)),
                         new EmeraldForItems(ModItems.medicine_bag, new PriceInfo(1, 1)),
                         new EmeraldsForItemsWithMeta(new ItemStack(ModItems.baykok_bow), new PriceInfo(-3, -2)),

                         new ListItemForEmeralds(ModItems.flute, new PriceInfo(1, 1)),
                         new ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.drum), new PriceInfo(1, 1)),
                         new ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.wind_chime), new PriceInfo(3, 4)),
                         new ListItemForEmeralds(Item.getItemFromBlock(ModBlocks.tipi), new PriceInfo(2, 3)),
                         new ListItemForEmeralds(ModItems.totemic_staff, new PriceInfo(1, 1)),

                         new ItemAndEmeraldToItem(Item.getItemFromBlock(ModBlocks.cedar_log), new PriceInfo(10, 10), Item.getItemFromBlock(ModBlocks.stripped_cedar_log), new PriceInfo(10, 10)));
    }

    static class EmeraldsForItemsWithMeta implements ITradeList
    {
        ItemStack stack;
        PriceInfo price;

        EmeraldsForItemsWithMeta(ItemStack stack, PriceInfo price)
        {
            this.stack = stack;
            this.price = price;
        }

        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            int p = 1;
            if(price != null)
                p = price.getPrice(random);

            ItemStack buying, selling;
            if(p > 0)
            {
                buying = new ItemStack(stack.getItem(), p, stack.getMetadata());
                selling = new ItemStack(Items.EMERALD);
            }
            else
            {
                buying = new ItemStack(stack.getItem(), 1, stack.getMetadata());
                selling = new ItemStack(Items.EMERALD, -p);
            }

            recipeList.add(new MerchantRecipe(buying, selling));
        }
    }
}
