/*
 * Copyright 2015-2016 inventivetalent. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and contributors and should not be interpreted as representing official policies,
 *  either expressed or implied, of anybody else.
 */

package org.inventivetalent.particle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.inventivetalent.particle.ParticlePacket.ParticleColor;

import cc.bukkitPlugin.commons.nmsutil.Version;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static cc.bukkitPlugin.commons.nmsutil.Version.*;

public enum ParticleEffect {
	
    EXPLOSION_NORMAL( 0, "poof" ), 
    EXPLOSION_LARGE( 1, "explosion"), 
    EXPLOSION_HUGE( 2, "explosion_emitter"), 
    FIREWORKS_SPARK( 3, "firework" ), 
    WATER_BUBBLE( 4, "bubble" , ParticleProperty.REQUIRES_WATER), 
    WATER_SPLASH( 5, "splash" ), //need -offsetY must equal 0 offsetX and offsetZ must not equal 0
    WATER_WAKE( 6, "fishing" ), 
    SUSPENDED( 7, "underwater", ParticleProperty.REQUIRES_WATER), 
    SUSPENDED_DEPTH( 8, "underwater", ParticleProperty.REQUIRES_WATER), 
    CRIT( 9, "crit" ), 
    CRIT_MAGIC( 10, "enchanted_hit" ), 
    SMOKE_NORMAL( 11, "smoke" ), 
    SMOKE_LARGE( 12, "large_smoke" ), 
    SPELL( 13, "effect" ), //need -offsetX must equal 0 offsetZ must equal 0
    SPELL_INSTANT( 14, "instant_effect" ),  //need -offsetX must equal 0 offsetZ must equal 0
    SPELL_MOB( 15, "entity_effect", ParticleProperty.COLORABLE ), //Feature.COLOR
    SPELL_MOB_AMBIENT( 16, "ambient_entity_effect", ParticleProperty.COLORABLE ), //Feature.COLOR
    SPELL_WITCH( 17, "witch" ), 
    DRIP_WATER( 18, "dripping_water"), 
    DRIP_LAVA( 19, "dripping_lava"), 
    VILLAGER_ANGRY( 20, "angry_villager"), 
    VILLAGER_HAPPY( 21, "happy_villager" ), 
    TOWN_AURA( 22, "mycelium" ), 
    NOTE( 23, "note"), //Feature.COLOR ParticleProperty.COLORABLE seed>0 have color
    PORTAL( 24, "portal" ), 
    ENCHANTMENT_TABLE( 25, "enchant" ), 
    FLAME( 26, "flame" ), 
    LAVA( 27, "lava"), 
    CLOUD( 28, "cloud" ), 
    REDSTONE( 29, "dust", ParticleProperty.NEED_DATA), //Feature.COLOR8888888888888888888888888
    SNOWBALL( 30, "item_snowball"), 
    SNOW_SHOVEL( 31, "item_snowball"), 
    SLIME( 32, "item_slime"), 
    HEART( 33, "heart"), 
    BARRIER( 34, "barrier"), 
    ITEM_CRACK( 35, "item" , ParticleProperty.NEED_DATA),//Feature.DATA8888888888888888888888888
    BLOCK_CRACK( 36, "block" , ParticleProperty.NEED_DATA),//, Feature.DATA8888888888888888888888888
    BLOCK_DUST( 37, "block" , ParticleProperty.NEED_DATA),//Feature.DATA8888888888888888888888888
    WATER_DROP( 38, "rain"), 
    MOB_APPEARANCE( 39, "elder_guardian"), 
    DRAGON_BREATH( 40, "dragon_breath"), 
    END_ROD( 41, "end_rod"), 
    DAMAGE_INDICATOR( 42, "damage_indicator"), 
    SWEEP_ATTACK( 43, "sweep_attack"), 
    FALLING_DUST( 44, "falling_dust"), 
    TOTEM( 45, "totem_of_undying"), 
    SPIT( 46, "spit"), 
    SQUID_INK( 47, "squid_ink"), 
    BUBBLE_POP( 48, "bubble_pop"), 
    CURRENT_DOWN( 49, "current_down", ParticleProperty.REQUIRES_WATER), 
    BUBBLE_COLUMN_UP( 50, "bubble_column_up", ParticleProperty.REQUIRES_WATER), 
    NAUTILUS( 51, "nautilus"), 
    DOLPHIN( 52, "dolphin");
    //LEGACY_BLOCK_CRACK( 53, "block"), 
    //LEGACY_BLOCK_DUST( 54, "block"), 
    //LEGACY_FALLING_DUST( 55, "falling_dust");

	
	private static final Map<String, ParticleEffect> NAME_MAP = new HashMap<String, ParticleEffect>();
	private static final Map<Integer, ParticleEffect> ID_MAP = new HashMap<Integer, ParticleEffect>();
	private final String name;
	private final int id;
	private final Version minVersion;
	private final List<ParticleProperty> properties;
	//protected Particle          particle;

	// Initialize map for quick name and id lookup
	static {
		for (ParticleEffect effect : values()) {
			NAME_MAP.put(effect.name, effect);
			ID_MAP.put(effect.id, effect);
		}
	}
	
	/**
	 * Construct a new particle effect
	 * 
	 * @param name Name of this particle effect
	 * @param id Id of this particle effect
	 * @param requiredVersion Version which is required (1.x)
	 * @param properties Properties of this particle effect
	 */
	
	ParticleEffect(int id ,String name, Version minVersion, ParticleProperty... properties) {
		this.id = id;
		this.name = name;
		this.minVersion = minVersion;
		this.properties = Arrays.asList(properties);
	}

	ParticleEffect(int id ,String name, ParticleProperty... properties) {
		this(id, name, v1_13_R1, properties);
	}

	ParticleEffect(int id, String name) {
		this(id,name, v1_13_R1);
	}

	/**
	 * Returns the id of this particle effect
	 * 
	 * @return The id
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the id of this particle effect
	 * 
	 * @return The id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the minimum {@link org.inventivetalent.reflection.minecraft.Minecraft.Version} required for this particle
	 */
	public Version getMinVersion() {
		return minVersion;
	}

	/**
	 * @return <code>true</code> if this particle is compatible with the server version
	 */
	public boolean isCompatible() {
		return Version.getCurrentVersion().isNewerOrSame(getMinVersion());
	}

	/**
	 * Determine if this particle effect has a specific property
	 * 
	 * @return Whether it has the property or not
	 */
	public boolean hasProperty(ParticleProperty property) {
		return properties.contains(property);
	}

	/**
	 * Check if this particle has no {@link org.inventivetalent.particle.ParticleEffect.Feature}s - Particles without features cannot use special sendColor or sendData methods - Particles with features cannot use the default send() methods
	 *
	 * @return <code>true</code> if this particle has no special features
	 * @see #hasFeature(Feature)
	 */
	public boolean hasNoPropertys() {
		return this.properties.size() == 0;
	}

	/**
	 * Returns the particle effect with the given name
	 * 
	 * @param name Name of the particle effect
	 * @return The particle effect
	 */
	public static ParticleEffect fromName(String name) {
		for (Entry<String, ParticleEffect> entry : NAME_MAP.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase(name)) {
				continue;
			}
			return entry.getValue();
		}
		return null;
	}
	
	/**
	 * Returns the particle effect with the given id
	 * 
	 * @param id Id of the particle effect
	 * @return The particle effect
	 */
	public static ParticleEffect fromId(int id) {
		for (Entry<Integer, ParticleEffect> entry : ID_MAP.entrySet()) {
			if (entry.getKey() != id) {
				continue;
			}
			return entry.getValue();
		}
		return null;
	}

	/**
	 * Determine if water is at a certain location
	 * 
	 * @param location Location to check
	 * @return Whether water is at this location or not
	 */
	private static boolean isWater(Location location) {
		Material material = location.getBlock().getType();
		return material == Material.WATER ;
	}
	
	/**
	 * Displays a particle effect which is only visible for all players within a certain range in the world of @param center
	 * 
	 * @param offsetX Maximum distance particles can fly away from the center on the x-axis
	 * @param offsetY Maximum distance particles can fly away from the center on the y-axis
	 * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
	 * @param speed Display speed of the particles
	 * @param amount Amount of particles
	 * @param center Center location of the effect
	 * @param range Range of the visibility
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect requires additional data
	 * @throws IllegalArgumentException If the particle effect requires water and none is at the center location
	 * @see ParticlePacket
	 * @see ParticlePacket#sendTo(Location, double)
	 */
	public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws IllegalArgumentException {	
		if (!isCompatible()) {
			throw new ParticleException("This particle effect is not supported by your server version");
		}
		if (hasProperty(ParticleProperty.NEED_DATA)) {
			throw new ParticleException("This particle effect requires additional data");
		}
		if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
			throw new IllegalArgumentException("There is no water at the center location");
		}
		new ParticlePacket(this, center, offsetX, offsetY, offsetZ, speed, amount).sendTo(range);
		//new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256, null).sendTo(center, range);
	
	}

	/**
	 * Displays a particle effect which is only visible for the specified players
	 * 
	 * @param offsetX Maximum distance particles can fly away from the center on the x-axis
	 * @param offsetY Maximum distance particles can fly away from the center on the y-axis
	 * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
	 * @param speed Display speed of the particles
	 * @param amount Amount of particles
	 * @param center Center location of the effect
	 * @param players Receivers of the effect
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect requires additional data
	 * @throws IllegalArgumentException If the particle effect requires water and none is at the center location
	 * @see ParticlePacket
	 * @see ParticlePacket#sendTo(Location, List)
	 */
	public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws  IllegalArgumentException {
		if (!isCompatible()) {
			throw new ParticleException("This particle effect is not supported by your server version");
		}
		if (hasProperty(ParticleProperty.NEED_DATA)) {
			throw new ParticleException("This particle effect requires additional data");
		}
		if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
			throw new IllegalArgumentException("There is no water at the center location");
		}
		new ParticlePacket(this, center, offsetX, offsetY, offsetZ, speed, amount).sendTo(players);
	}

	/**
	 * Displays a particle effect which is only visible for the specified players
	 * 
	 * @param offsetX Maximum distance particles can fly away from the center on the x-axis
	 * @param offsetY Maximum distance particles can fly away from the center on the y-axis
	 * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
	 * @param speed Display speed of the particles
	 * @param amount Amount of particles
	 * @param center Center location of the effect
	 * @param players Receivers of the effect
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect requires additional data
	 * @throws IllegalArgumentException If the particle effect requires water and none is at the center location
	 * @see #display(float, float, float, float, int, Location, List)
	 */
	public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws IllegalArgumentException {
		display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
	}

	
	
	
	/**
	 * Displays a single particle which flies into a determined direction and is only visible for all players within a certain range in the world of @param center
	 * 
	 * @param direction Direction of the particle
	 * @param speed Display speed of the particle
	 * @param center Center location of the effect
	 * @param range Range of the visibility
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect requires additional data
	 * @throws IllegalArgumentException If the particle effect is not directional or if it requires water and none is at the center location
	 * @see ParticlePacket#ParticlePacket(ParticleEffect, Vector, float, boolean, ParticleData)
	 * @see ParticlePacket#sendTo(Location, double)
	 */
	public void display(Vector direction, float speed, int amount, Location center, double range) throws  IllegalArgumentException {
		display((float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), speed, amount, center, range) ;
	}

	/**
	 * Displays a single particle which flies into a determined direction and is only visible for the specified players
	 * 
	 * @param direction Direction of the particle
	 * @param speed Display speed of the particle
	 * @param center Center location of the effect
	 * @param players Receivers of the effect
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect requires additional data
	 * @throws IllegalArgumentException If the particle effect is not directional or if it requires water and none is at the center location
	 * @see ParticlePacket#ParticlePacket(ParticleEffect, Vector, float, boolean, ParticleData)
	 * @see ParticlePacket#sendTo(Location, List)
	 */
	public void display(Vector direction, float speed, int amount, Location center, List<Player> players) throws IllegalArgumentException {
		display((float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), speed, amount, center, players);
	}

	/**
	 * Displays a single particle which flies into a determined direction and is only visible for the specified players
	 * 
	 * @param direction Direction of the particle
	 * @param speed Display speed of the particle
	 * @param center Center location of the effect
	 * @param players Receivers of the effect
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect requires additional data
	 * @throws IllegalArgumentException If the particle effect is not directional or if it requires water and none is at the center location
	 * @see #display(Vector, float, Location, List)
	 */
	public void display(Vector direction, float speed, int amount, Location center, Player... players) throws IllegalArgumentException {
		display(direction, speed, amount, center, Arrays.asList(players));
	}

	/**
	 * Displays a single particle which is colored and only visible for all players within a certain range in the world of @param center
	 * 
	 * @param color Color of the particle
	 * @param center Center location of the effect
	 * @param range Range of the visibility
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleColorException If the particle effect is not colorable or the color type is incorrect
	 * @see ParticlePacket#ParticlePacket(ParticleEffect, ParticleColor, boolean)
	 * @see ParticlePacket#sendTo(Location, double)
	 */
	public void display(ParticleColor color, Location center, double range) {
		if (!hasProperty(ParticleProperty.COLORABLE)) {
			throw new ParticleException("This particle effect is not colorable");
		}
		display(color.getValueX(), color.getValueY(), color.getValueZ(), 1, 0, center, range) ;
	}

	/**
	 * Displays a single particle which is colored and only visible for the specified players
	 * 
	 * @param color Color of the particle
	 * @param center Center location of the effect
	 * @param players Receivers of the effect
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleColorException If the particle effect is not colorable or the color type is incorrect
	 * @see ParticlePacket#ParticlePacket(ParticleEffect, ParticleColor, boolean)
	 * @see ParticlePacket#sendTo(Location, List)
	 */
	public void display(ParticleColor color, Location center, List<Player> players) {
		if (!hasProperty(ParticleProperty.COLORABLE)) {
			throw new ParticleException("This particle effect is not colorable");
		}
		display(color.getValueX(), color.getValueY(), color.getValueZ(), 1, 0, center, players);
	}

	/**
	 * Displays a single particle which is colored and only visible for the specified players
	 * 
	 * @param color Color of the particle
	 * @param center Center location of the effect
	 * @param players Receivers of the effect
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleColorException If the particle effect is not colorable or the color type is incorrect
	 * @see #display(ParticleColor, Location, List)
	 */
	public void display(ParticleColor color, Location center, Player... players) {
		display(color, center, Arrays.asList(players));
	}

	/**
	 * Displays a particle effect which requires additional data and is only visible for all players within a certain range in the world of @param center
	 * 
	 * @param data Data of the effect
	 * @param offsetX Maximum distance particles can fly away from the center on the x-axis
	 * @param offsetY Maximum distance particles can fly away from the center on the y-axis
	 * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
	 * @param speed Display speed of the particles
	 * @param amount Amount of particles
	 * @param center Center location of the effect
	 * @param range Range of the visibility
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
	 * @see ParticlePacket
	 * @see ParticlePacket#sendTo(Location, double)
	 */
	public <T> void display(T data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) {
		if (!isCompatible()) {
			throw new ParticleException("This particle effect is not supported by your server version");
		}
		if (!hasProperty(ParticleProperty.NEED_DATA)){ 
			throw new ParticleException("This particle cannot have block/item data"); 
		}
		new ParticlePacket(this, data, center, offsetX, offsetY, offsetZ, speed, amount).sendTo(range);
	}

	/**
	 * Displays a particle effect which requires additional data and is only visible for the specified players
	 * 
	 * @param data Data of the effect
	 * @param offsetX Maximum distance particles can fly away from the center on the x-axis
	 * @param offsetY Maximum distance particles can fly away from the center on the y-axis
	 * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
	 * @param speed Display speed of the particles
	 * @param amount Amount of particles
	 * @param center Center location of the effect
	 * @param players Receivers of the effect
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
	 * @see ParticlePacket
	 * @see ParticlePacket#sendTo(Location, List)
	 */
	public <T> void display(T data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) {
		if (!isCompatible()) {
			throw new ParticleException("This particle effect is not supported by your server version");
		}
		if (!hasProperty(ParticleProperty.NEED_DATA)){ 
			throw new ParticleException("This particle cannot have block/item data"); 
		}
		new ParticlePacket(this, data, center, offsetX, offsetY, offsetZ, speed, amount).sendTo(players);
	}

	/**
	 * Displays a particle effect which requires additional data and is only visible for the specified players
	 * 
	 * @param data Data of the effect
	 * @param offsetX Maximum distance particles can fly away from the center on the x-axis
	 * @param offsetY Maximum distance particles can fly away from the center on the y-axis
	 * @param offsetZ Maximum distance particles can fly away from the center on the z-axis
	 * @param speed Display speed of the particles
	 * @param amount Amount of particles
	 * @param center Center location of the effect
	 * @param players Receivers of the effect
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
	 * @see #display(ParticleData, float, float, float, float, int, Location, List)
	 */
	public <T> void display(T data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) {
		display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
	}

	/**
	 * Displays a single particle which requires additional data that flies into a determined direction and is only visible for all players within a certain range in the world of @param center
	 * 
	 * @param data Data of the effect
	 * @param direction Direction of the particle
	 * @param speed Display speed of the particles
	 * @param center Center location of the effect
	 * @param range Range of the visibility
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
	 * @see ParticlePacket
	 * @see ParticlePacket#sendTo(Location, double)
	 */
	public <T> void display(T data, Vector direction, float speed, int amount, Location center, double range) {
		display(data, (float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), speed, amount, center, range);
	}

	/**
	 * Displays a single particle which requires additional data that flies into a determined direction and is only visible for the specified players
	 * 
	 * @param data Data of the effect
	 * @param direction Direction of the particle
	 * @param speed Display speed of the particles
	 * @param center Center location of the effect
	 * @param players Receivers of the effect
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
	 * @see ParticlePacket
	 * @see ParticlePacket#sendTo(Location, List)
	 */
	public <T>  void display(T data, Vector direction, float speed, int amount,Location center, List<Player> players) {
		display(data, (float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), speed, amount, center, players);
	}

	/**
	 * Displays a single particle which requires additional data that flies into a determined direction and is only visible for the specified players
	 * 
	 * @param data Data of the effect
	 * @param direction Direction of the particle
	 * @param speed Display speed of the particles
	 * @param center Center location of the effect
	 * @param players Receivers of the effect
	 * @throws ParticleVersionException If the particle effect is not supported by the server version
	 * @throws ParticleDataException If the particle effect does not require additional data or if the data type is incorrect
	 * @see #display(ParticleData, Vector, float, Location, List)
	 */
	public <T> void display(T data, Vector direction, float speed, int amount,Location center, Player... players) {
		display(data, direction, speed, amount, center, Arrays.asList(players));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	public static enum ParticleProperty {
		/**
		 * The particle effect requires water to be displayed
		 */
		REQUIRES_WATER,
		/**
		 * The particle effect requires block or item data to be displayed
		 */
		NEED_DATA,
		/**
		 * The particle effect uses the offsets as color values
		 */
		COLORABLE;
	}

}
