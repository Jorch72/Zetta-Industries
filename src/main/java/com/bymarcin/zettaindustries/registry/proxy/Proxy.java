package com.bymarcin.zettaindustries.registry.proxy;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import com.bymarcin.zettaindustries.registry.ZIRegistry;

public class Proxy extends ZIRegistry {

	public void init() {
		for (IProxy p : proxy)
			p.serverSide();
	}

	public World getWorld(int dimensionId) {
		return MinecraftServer.getServer().worldServerForDimension(dimensionId);
	}

	public int getCurrentClientDimension() {
		return -9001;
	}
}
