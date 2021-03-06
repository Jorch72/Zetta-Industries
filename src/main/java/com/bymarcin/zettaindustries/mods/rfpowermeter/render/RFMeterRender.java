package com.bymarcin.zettaindustries.mods.rfpowermeter.render;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.bymarcin.zettaindustries.ZettaIndustries;
import com.bymarcin.zettaindustries.mods.rfpowermeter.RFMeterTileEntity;

public class RFMeterRender extends TileEntitySpecialRenderer implements IItemRenderer{
	enum SI{
		P(50,40),
		T(44,40),
		G(38,40),
		M(32,40),
		K(26,40),
		none(0,0);
		int x,y,width,height;
		public static final SI[] reverse = valuesReverse();
		SI(int x, int y, int width, int height){
			this.x = x;
			this.y = y;
			this.width = width; 
			this.height = height;
		}
		
		SI(int x, int y){
			this(x,y,5,7);
		}
		
		private static SI[] valuesReverse(){
			int len = SI.values().length;
			SI[] arr = new SI[len];
			for(SI si: values()){
				arr[len-si.ordinal()-1]=si;
			}
			return arr;
		}
		
		public void drawActive(Tessellator tes, float r, float g, float b){
			GL11.glPushMatrix();
			tes.startDrawingQuads();
			tes.setBrightness(0xF << 20 | 0xF << 4);
			tes.setColorRGBA_F(r, g, b, 1);
			tes.setNormal(0, 0, -1);
			tes.addVertexWithUV(0/30D, 0, 0,       (x+width)/64D, (y+height)/64D);
			tes.addVertexWithUV(0/30D, 7/30D, 0,  (x+width)/64D, y/64D);
			tes.addVertexWithUV(5/30D, 7/30D,0,  x/64D, y/64D);
			tes.addVertexWithUV(5/30D, 0,0,       x/64D, (y+height)/64D);
			tes.draw();
			GL11.glPopMatrix();
		}
		
		public void drawInActive(Tessellator tes, float r, float g, float b){
			GL11.glPushMatrix();
			tes.startDrawingQuads();
			tes.setColorRGBA_F(r*0.25F, g*0.25F, b*0.25F, 1);
			tes.setNormal(0, 0, -1);
			tes.addVertexWithUV(0/30D, 0, 0,       (x+width)/64D, (y+height)/64D);
			tes.addVertexWithUV(0/30D, 7/30D, 0,  (x+width)/64D, y/64D);
			tes.addVertexWithUV(5/30D, 7/30D,0,  x/64D, y/64D);
			tes.addVertexWithUV(5/30D, 0,0,       x/64D, (y+height)/64D);
			tes.draw();
			GL11.glPopMatrix();
		}
		
	}
	
	static ResourceLocation cTexture = new ResourceLocation(ZettaIndustries.MODID,"textures/blocks/counter.png");
	Tessellator tes = Tessellator.instance;
    double j=0;
    
	
	public void drawDirection(Tessellator tes, float r, float g, float b, boolean isInverted){
		GL11.glPushMatrix();
		int width = 6;
		int height = 7;
		int x = 56;
		int y =40;
		tes.setTranslation(13.5/30F, -10/30F, 0);
		tes.startDrawingQuads();
		tes.setBrightness(0xF << 20 | 0xF << 4);
		tes.setColorRGBA_F(r, g, b, 1);
		tes.setNormal(0, 0, -1);

		if(isInverted){
			tes.addVertexWithUV(0/30D, 0, 0,       (x+width)/64D, (y+height)/64D);
			tes.addVertexWithUV(0/30D, 7/30D, 0,  (x+width)/64D, y/64D);
			tes.addVertexWithUV(6/30D, 7/30D,0,  x/64D, y/64D);
			tes.addVertexWithUV(6/30D, 0,0,       x/64D, (y+height)/64D);
		}else{
			tes.addVertexWithUV(0/30D, 0, 0,       (x+width)/64D, y/64D);
			tes.addVertexWithUV(0/30D, 7/30D, 0,  (x+width)/64D, (y+height)/64D);
			tes.addVertexWithUV(6/30D, 7/30D,0,  x/64D, (y+height)/64D);
			tes.addVertexWithUV(6/30D, 0,0,       x/64D, y/64D);
		}
		
		tes.draw();
		tes.setTranslation(0, 0, 0);
		GL11.glPopMatrix();
	}
    
    public void render(double x, double y, double z, int metadata, int mixedBrightnessForBlock, long currentValue, int transfer, float r, float g, float b, boolean isInverted){
		GL11.glPushMatrix();
		GL11.glColor4f(1, 1, 1, 1);
		tes.setColorRGBA_F(1F, 1F, 1F, 1);
		GL11.glTranslated(x, y, z);
		GL11.glTranslated(0.5D, 0.5D, 0.5D);
		switch(metadata){
			case 0: GL11.glRotated(180, 0, 1, 0); break;
			case 1: GL11.glRotated(90, 0, 1, 0); break;
			case 2: break;
			case 3: GL11.glRotated(270, 0, 1, 0); break;
		}
		GL11.glTranslated(-0.5D, -0.5D, -0.5D);
		
		
		
		bindTexture(cTexture);
		//drawShape(mixedBrightnessForBlock, isInverted);
		float s= 11/43F;	

		GL11.glPushMatrix();
		GL11.glScaled(8/16D, 8/16D, 1);
		GL11.glTranslated(8/16F, 28/16F-s, 3/16F-0.002F);
		drawNumber(transfer,r,g,b,false);
		GL11.glTranslated(0, -1.85/16F-s, 0);

		long total = currentValue;
		int unit=0;
		total *=10;
		while(Math.ceil(Math.log10(total))>6){
			unit++;
			total /=1000;
		}

		drawNumber(total,r,g,b,true);
		
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(19/64D, 12/32D, 3/16D-0.001);
		GL11.glScaled(16/44D, 16/44D, 1);
		drawSI(SI.reverse[unit], r,g,b);
		drawDirection(tes, r, g, b, isInverted);
		GL11.glPopMatrix();

		GL11.glPopMatrix();

    }
    
    
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float delta) {
		if(!(te instanceof RFMeterTileEntity)) return;
		RFMeterTileEntity tile = (RFMeterTileEntity) te;
		render(x,y,z,tile.getBlockMetadata(), te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord).getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord),tile.getCurrentValue(), tile.getTransfer(), tile.r, tile.g, tile.b, tile.isInverted());
	//	render(x,y,z,tile.getBlockMetadata(), 0xF << 20 | 0xF << 4,tile.getCurrentValue(), tile.getTransfer(), tile.r, tile.g, tile.b);

	}
	
	private void drawSI(SI value, float r, float g, float b){
		GL11.glPushMatrix();
		for(SI si: SI.values()){
			if(si==SI.none) continue;
			if(si == value)
				si.drawActive(tes, r, g, b);
			else
				si.drawInActive(tes,  r, g, b);
			tes.addTranslation(7/30F, 0, 0);
		}
		tes.setTranslation(0, 0, 0);

		GL11.glPopMatrix();
	}
	

	
	
	
	private void drawNumber(long number,float r, float g, float b, boolean dot) {
		GL11.glPushMatrix();
		tes.startDrawingQuads();
		tes.setNormal(0, 0, -1);
		tes.setColorRGBA_F(r*0.5F, g*0.5F, b*0.5F, 1);
		double s= 11/43D;
		tes.addVertexWithUV(0, 0, 0.001, 43/64D, 22/64D);//bottom right texture
		tes.addVertexWithUV(0, s, 0.001, 43/64D, 11/64D);//top right
		tes.addVertexWithUV(1, s, 0.001, 0, 11/64D);//top left
		tes.addVertexWithUV(1, 0, 0.001, 0, 22/64D);//bottom left

		tes.draw();	
	
		int i=0;
		tes.startDrawingQuads();
		tes.setColorRGBA_F(r, g, b, 1);
		tes.addTranslation(-1/43F,0,-0.001F);
		tes.setBrightness(0xF << 20 | 0xF << 4);
		for(;number != 0 || i<(dot?2:1);number = number/10,i++){
			long dig = number%10;
			long x = dig *6;
			tes.setNormal(0, 0, -1);
			tes.addVertexWithUV(0, 0, 0, (x+6)/64D, 11/64D);//bottom right texture
			tes.addVertexWithUV(0, s, 0, (x+6)/64D, 0);//top right
			tes.addVertexWithUV(6/43D, s, 0, x/64D, 0);//top left
			tes.addVertexWithUV(6/43D, 0, 0, x/64D, 11/64D);//bottom left
			tes.addTranslation(6/43F,0,0);
			if(i==0){
				if(dot){
					tes.setNormal(0, 0, -1);
					tes.addVertexWithUV(0, 0, 0, (62)/64D, 11/64D);//bottom right texture
					tes.addVertexWithUV(0, s, 0, (62)/64D, 0);//top right
					tes.addVertexWithUV(2/43D, s, 0, 60/64D, 0);//top left
					tes.addVertexWithUV(2/43D, 0, 0, 60/64D, 11/64D);//bottom left
				}
				tes.addTranslation(2/43F,0,0);
			}	
		}
		tes.draw();
		tes.setTranslation(0, 0, 0);
		GL11.glPopMatrix();
	
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		RenderHelper.enableStandardItemLighting();
		if(ItemRenderType.ENTITY == type)
			render(-0.5, -0.5, -0.5, 0, 0xF << 20 | 0xF << 4, 0, 0, 0, 1, 0, false);
		else
			render(0, 0, 0, 0, 0xF << 20 | 0xF << 4, 0, 0, 0, 1, 0, false);
		
	}

}
