package org.inventivetalent.particle;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import cc.bukkitPlugin.commons.nmsutil.NMSUtil;
import cc.commons.util.reflect.ClassUtil;
import cc.commons.util.reflect.MethodUtil;

public class ParticlePacket {
	
	private final ParticleEffect effect;
	private Location center;
	private float offsetX;
	private final float offsetY;
	private final float offsetZ;
	private final float speed;
	private final int amount;
	private Object Data = null; 
	/*private ItemStack item = null;
	private BlockData blockdata = null;
	private DustOptions Dust = null;*/
	private Object packet = null;
	//private boolean longDistance = false;
	
	public ParticlePacket(ParticleEffect effect, Location center ,float offsetX, float offsetY, float offsetZ, float speed, int amount) throws IllegalArgumentException {
		if (speed < 0) {
			throw new IllegalArgumentException("The speed is lower than 0");
		}
		if (amount < 0) {
			throw new IllegalArgumentException("The amount is lower than 0");
		}
		this.effect = effect;
		this.center = center;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.speed = speed;
		this.amount = amount;
	}
	
	public <T> ParticlePacket(ParticleEffect effect, T DATA, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount) throws IllegalArgumentException {
		if (speed < 0) {
			throw new IllegalArgumentException("The speed is lower than 0");
		}
		if (amount < 0) {
			throw new IllegalArgumentException("The amount is lower than 0");
		}
		this.effect = effect;
		this.center = center;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.speed = speed;
		this.amount = amount;
		this.Data = DATA;
	/*	switch(effect)
		{
			case ITEM_CRACK:
				this.item = (ItemStack)DATA;
				break;
			case BLOCK_CRACK:
			case BLOCK_DUST:
				this.blockdata = (BlockData)DATA;
				break;
			case REDSTONE:
				this.Dust = (DustOptions)DATA;
				break;
			default:
			    break;
		}*/
	}
	
	public Object initializePacket(boolean flag)
	{
		if (packet != null) {
			return packet;
		}
		try {
			Class<?>[] pParamTypes = new Class[] {
					ParticleNMS.clazz_ParticleParam,
					boolean.class,
					float.class,
					float.class,
					float.class,
					float.class,
					float.class,
					float.class,
					float.class,
					int.class};
			Object ParticleParam = null; 
			switch(effect)
			{
				case ITEM_CRACK:
					ParticleParam = MethodUtil.invokeStaticMethod(ParticleNMS.method_CraftParticle_toNMSWithData,Particle.ITEM_CRACK,(ItemStack)Data);
					break;
				case BLOCK_CRACK:
					ParticleParam = MethodUtil.invokeStaticMethod(ParticleNMS.method_CraftParticle_toNMSWithData,Particle.BLOCK_CRACK,(BlockData)Data);
					break;
				case BLOCK_DUST:
					ParticleParam = MethodUtil.invokeStaticMethod(ParticleNMS.method_CraftParticle_toNMSWithData,Particle.BLOCK_DUST,(BlockData)Data);
					break;
				case REDSTONE:
					ParticleParam = MethodUtil.invokeStaticMethod(ParticleNMS.method_CraftParticle_toNMSWithData,Particle.REDSTONE,(DustOptions)Data);
					//ParticleParam = (DustOptions)DATA;
					break;
				default:
					ParticleParam = MethodUtil.invokeStaticMethod(ParticleNMS.method_CraftParticle_toNMS,Particle.valueOf(effect.toString()));
				    break;
			}
			Object[] pParams = new Object[]{
					ParticleParam,
					flag,
					(float) center.getX(),//X
					(float) center.getY(),//Y
					(float) center.getZ(),//Z
					offsetX,//X-offset
					offsetY,//Y-offset
					offsetZ,//Z-offset
					speed,//Speed
					amount,//Count
					};
			packet = ClassUtil.newInstance(ParticleNMS.clazz_PacketParticle,pParamTypes,pParams);
			return packet;	
		} catch (Exception exception) {
			throw new ParticleException("Packet instantiation failed", exception);
		}
	}
	
	
	/**
	 * Sends the packet to a single player and caches it
	 * 
	 * @param center Center location of the effect
	 * @param player Receiver of the packet
	 * @throws PacketInstantiationException If instantion fails due to an unknown error
	 * @throws PacketSendingException If sending fails due to an unknown error
	 * @see #initializePacket(Location)
	 */
	public void sendTo(Player player, boolean longDistance) {
		try {
			Object PlayerConnection = ParticleNMS.getEntityPlayer_PlayerConnection(NMSUtil.getNMSPlayer(player));
			MethodUtil.invokeMethod(ParticleNMS.method_PlayerConnection_sendPacket, PlayerConnection, this.initializePacket(longDistance));
		} catch (Exception exception) {
			throw new ParticleException("Failed to send the packet to player '" + player.getName() + "'", exception);
		}
	}

	/**
	 * Sends the packet to all players in the list
	 * 
	 * @param center Center location of the effect
	 * @param players Receivers of the packet
	 * @throws IllegalArgumentException If the player list is empty
	 * @see #sendTo(Location center, Player player)
	 */
	public void sendTo(List<Player> players) throws IllegalArgumentException {
		if (players.isEmpty()) {
			throw new IllegalArgumentException("The player list is empty");
		}
		for (Player player : players) {
			sendTo(player, true);
		}
	}

	/**
	 * Sends the packet to all players in a certain range
	 * 
	 * @param center Center location of the effect
	 * @param range Range in which players will receive the packet (Maximum range for particles is usually 16, but it can differ for some types)
	 * @throws IllegalArgumentException If the range is lower than 1
	 * @see #sendTo(Location center, Player player)
	 */
	public void sendTo(double range) throws IllegalArgumentException {
		if (range < 1) {
			throw new IllegalArgumentException("The range is lower than 1");
		}
		String worldName = center.getWorld().getName();
		double squared = range * range;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.getWorld().getName().equals(worldName) || player.getLocation().distanceSquared(center) > squared) {
				continue;
			}
			sendTo(player, range > 256);
		}
	}
	
	
	public static final class ParticleColor {
		private final int red;
		private final int green;
		private final int blue;

		/**
		 * Construct a new ordinary color
		 * 
		 * @param red Red value of the RGB format
		 * @param green Green value of the RGB format
		 * @param blue Blue value of the RGB format
		 * @throws IllegalArgumentException If one of the values is lower than 0 or higher than 255
		 */
		public ParticleColor(int red, int green, int blue) throws IllegalArgumentException {
			if (red < 0) {
				throw new IllegalArgumentException("The red value is lower than 0");
			}
			if (red > 255) {
				throw new IllegalArgumentException("The red value is higher than 255");
			}
			this.red = red;
			if (green < 0) {
				throw new IllegalArgumentException("The green value is lower than 0");
			}
			if (green > 255) {
				throw new IllegalArgumentException("The green value is higher than 255");
			}
			this.green = green;
			if (blue < 0) {
				throw new IllegalArgumentException("The blue value is lower than 0");
			}
			if (blue > 255) {
				throw new IllegalArgumentException("The blue value is higher than 255");
			}
			this.blue = blue;
		}

		/**
		 * Construct a new ordinary color
		 * 
		 * @param color Bukkit color
		 */
		public ParticleColor(Color color) {
			this(color.getRed(), color.getGreen(), color.getBlue());
		}

		/**
		 * Returns the red value of the RGB format
		 * 
		 * @return The red value
		 */
		public int getRed() {
			return red;
		}

		/**
		 * Returns the green value of the RGB format
		 * 
		 * @return The green value
		 */
		public int getGreen() {
			return green;
		}

		/**
		 * Returns the blue value of the RGB format
		 * 
		 * @return The blue value
		 */
		public int getBlue() {
			return blue;
		}

		/**
		 * Returns the red value divided by 255
		 * 
		 * @return The offsetX value
		 */
		public float getValueX() {
			if(red == 0)
			{
				return Float.MIN_VALUE;
			}
			return (float) red / 255F;
		}

		/**
		 * Returns the green value divided by 255
		 * 
		 * @return The offsetY value
		 */
		public float getValueY() {
			if(green == 0)
			{
				return Float.MIN_VALUE;
			}
			return (float) green / 255F;
		}

		/**
		 * Returns the blue value divided by 255
		 * 
		 * @return The offsetZ value
		 */
		public float getValueZ() {
			if(blue == 0)
			{
				return Float.MIN_VALUE;
			}
			return (float) blue / 255F;
		}
	}
}
