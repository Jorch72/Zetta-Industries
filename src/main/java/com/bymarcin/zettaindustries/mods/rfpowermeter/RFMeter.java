package com.bymarcin.zettaindustries.mods.rfpowermeter;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.MinecraftForgeClient;

import com.bymarcin.zettaindustries.ZettaIndustries;
import com.bymarcin.zettaindustries.modmanager.IMod;
import com.bymarcin.zettaindustries.mods.rfpowermeter.render.RFMeterRender;
import com.bymarcin.zettaindustries.registry.ZIRegistry;
import com.bymarcin.zettaindustries.registry.proxy.IProxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ItemStackHolder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RFMeter implements IMod, IProxy{
	RFMeterBlock  meter;
	public static boolean isOCAllowed = true;
	
	@ItemStackHolder(value="ThermalExpansion:material", meta=1)
	public static final ItemStack receptionCoil = null;
	@ItemStackHolder(value="ThermalExpansion:material", meta=2)
	public static final ItemStack transmissionCoil = null;
	@ItemStackHolder(value="ThermalExpansion:meter")
	public static final ItemStack rfmeter = null;
	
	@Override
	public void init() {
		ZettaIndustries.instance.config.get("rfmeter", "oc.methods.allowed", true).getBoolean(true);
		meter = new RFMeterBlock();
		GameRegistry.registerBlock(meter, RFMeterItem.class, "rfmeterblock");
		GameRegistry.registerTileEntity(RFMeterTileEntity.class, "rfmeterblock");
		GameRegistry.registerTileEntity(RFMeterTileEntityOC.class, "rfmeterblockoc");
		ZIRegistry.registerProxy(this);
		ZIRegistry.registerPacket(4, RFMeterUpdatePacket.class, Side.CLIENT);
	}

	@Override
	public void postInit() {
		if(rfmeter!=null && transmissionCoil !=null && receptionCoil!=null)
			GameRegistry.addRecipe(new ItemStack(meter),"IRI","IYI","IXI", 'X', transmissionCoil, 'Y', rfmeter, 'I', Items.iron_ingot,'R',receptionCoil);
		else
			GameRegistry.addRecipe(new ItemStack(meter),"IXI","IYI","IXI", 'X', Items.comparator, 'Y', Blocks.redstone_block, 'I', Items.iron_ingot);
		GameRegistry.addShapelessRecipe(new ItemStack(meter),meter);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void clientSide() {
		RFMeterRender render = new RFMeterRender();
		ClientRegistry.bindTileEntitySpecialRenderer(RFMeterTileEntity.class, render);
		ClientRegistry.bindTileEntitySpecialRenderer(RFMeterTileEntityOC.class, render);

		MinecraftForgeClient.registerItemRenderer(new ItemStack(meter).getItem(), render);
	}

	@Override
	public void serverSide() {

	}
	
	@Override
	public void preInit() {

	}

}
