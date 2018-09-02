package org.inventivetalent.particle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Particle;

import cc.bukkitPlugin.commons.nmsutil.NMSUtil;
import cc.commons.util.reflect.FieldUtil;
import cc.commons.util.reflect.MethodUtil;
import cc.commons.util.reflect.filter.FieldFilter;

public class ParticleNMS {
	
    public static final Class<?> clazz_PacketParticle;
    public static final Class<?> clazz_ParticleParam;
    public static final Class<?> clazz_PlayerConnection;
    public static final Class<?> clazz_CraftParticle;
    
    public static final Method method_PlayerConnection_sendPacket;
    public static final Method method_CraftParticle_toNMS;
    public static final Method method_CraftParticle_toNMSWithData;
    
    public static final Field field_EntityPlayer_PlayerConnection;
    //
    
	//final Class<?>            PacketParticle                    = Reflection.NMS_CLASS_RESOLVER.resolveSilent("PacketPlayOutWorldParticles");
	//final Class<?>            EnumParticle                      = Reflection.NMS_CLASS_RESOLVER.resolveSilent("EnumParticle");
	//final ConstructorResolver PacketParticleConstructorResolver = new ConstructorResolver(PacketParticle);
    static{
    	clazz_PacketParticle = NMSUtil.getNMSClass("PacketPlayOutWorldParticles");
    	clazz_ParticleParam = NMSUtil.getNMSClass("ParticleParam");
    	clazz_PlayerConnection = NMSUtil.getNMSClass("PlayerConnection");
    	clazz_CraftParticle = NMSUtil.getCBTClass("CraftParticle");
    	
    	method_PlayerConnection_sendPacket = MethodUtil.getMethodIgnoreParam(clazz_PlayerConnection,"sendPacket",true).oneGet();
    	method_CraftParticle_toNMS = MethodUtil.getDeclaredMethod(clazz_CraftParticle,"toNMS",Particle.class);
    	method_CraftParticle_toNMSWithData = MethodUtil.getDeclaredMethod(clazz_CraftParticle,"toNMS",Particle.class,Object.class);
    	
    	field_EntityPlayer_PlayerConnection = FieldUtil.getDeclaredField(NMSUtil.clazz_EntityPlayerMP,FieldFilter.t(clazz_PlayerConnection)).oneGet();
    }
    
    @SuppressWarnings("unchecked")
    public static Object getEntityPlayer_PlayerConnection(Object NMSEntityPlayer){
        return FieldUtil.getFieldValue(field_EntityPlayer_PlayerConnection,NMSEntityPlayer);
    }
}
