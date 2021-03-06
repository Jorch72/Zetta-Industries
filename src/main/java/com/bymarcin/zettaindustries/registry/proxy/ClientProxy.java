package com.bymarcin.zettaindustries.registry.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ClientProxy extends Proxy {

	@Override
	public void init() {
		for (IProxy p : proxy)
			p.clientSide();
	}
	
	public void loadComplete(){
		for (IProxy p : proxy){
			if(p instanceof INeedLoadCompleteEvent){
				((INeedLoadCompleteEvent)p).clientLoadComplete();
			}
		}
	}


	@Override
	public World getWorld(int dimensionId) {
		if (getCurrentClientDimension() != dimensionId) {
			return null;
		} else
			return Minecraft.getMinecraft().theWorld;
	}

	@Override
	public int getCurrentClientDimension() {
		return Minecraft.getMinecraft().theWorld.provider.dimensionId;
	}
}
