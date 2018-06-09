package engine.core.system;

import engine.render.advancedTerrainSystem.AdvancedTerrainSystem;
import engine.render.entitysystem.EntitySystem;
import engine.render.instancedsystem.InstancedEntitySystem;
import engine.render.normalentitysystem.NormalEntitySystem;
import engine.render.overlaysystem.OverlaySystem;
import engine.render.particlesystem.ParticleSystem;
import engine.render.shadowsystem.ShadowSystem;
import engine.render.skydomesystem.SkydomeSystem;
import engine.render.terrainsystem.TerrainSystem;

/**
 * Created by Luecx on 04.01.2017.
 */
public abstract class Sys {

    public static final EntitySystem ENTITY_SYSTEM;
    public static final ShadowSystem SHADOW_SYSTEM;
    public static final NormalEntitySystem NORMAL_ENTITY_SYSTEM;
    public static final ParticleSystem PARTICLE_SYSTEM;
    public static final OverlaySystem OVERLAY_SYSTEM;
    public static final SkydomeSystem SKYDOME_SYSTEM;
    public static final InstancedEntitySystem INSTANCED_ENTITY_SYSTEM;
    public static final AdvancedTerrainSystem ADVANCED_TERRAIN_SYSTEM;
    public static final TerrainSystem TERRAIN_SYSTEM;


    static{
        ENTITY_SYSTEM = new EntitySystem();
        SHADOW_SYSTEM = new ShadowSystem();
        NORMAL_ENTITY_SYSTEM = new NormalEntitySystem();
        PARTICLE_SYSTEM = new ParticleSystem();
        OVERLAY_SYSTEM = new OverlaySystem();
        SKYDOME_SYSTEM = new SkydomeSystem();
        INSTANCED_ENTITY_SYSTEM = new InstancedEntitySystem();
        ADVANCED_TERRAIN_SYSTEM = new AdvancedTerrainSystem();
        TERRAIN_SYSTEM = new TerrainSystem();
    }



    public static void enableAll() {
        OVERLAY_SYSTEM.createSystem();
        ENTITY_SYSTEM.createSystem();
        SHADOW_SYSTEM.createSystem();
        NORMAL_ENTITY_SYSTEM.createSystem();
        PARTICLE_SYSTEM.createSystem();
        SKYDOME_SYSTEM.createSystem();
        INSTANCED_ENTITY_SYSTEM.createSystem();
        ADVANCED_TERRAIN_SYSTEM.createSystem();
        TERRAIN_SYSTEM.createSystem();
    }

    public static void disableAll() {
        OVERLAY_SYSTEM.closeSystem();
        ENTITY_SYSTEM.closeSystem();
        SHADOW_SYSTEM.closeSystem();
        NORMAL_ENTITY_SYSTEM.closeSystem();
        PARTICLE_SYSTEM.closeSystem();
        SKYDOME_SYSTEM.closeSystem();
        INSTANCED_ENTITY_SYSTEM.closeSystem();
        ADVANCED_TERRAIN_SYSTEM.closeSystem();
        TERRAIN_SYSTEM.closeSystem();
    }

}
