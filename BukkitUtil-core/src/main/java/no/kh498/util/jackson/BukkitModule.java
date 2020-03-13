package no.kh498.util.jackson;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import no.kh498.util.jackson.deserializers.BukkitDeserializers;
import no.kh498.util.jackson.deserializers.BukkitKeyDeserializers;
import no.kh498.util.jackson.mixin.*;
import no.kh498.util.jackson.serializers.BukkitSerializers;
import org.bukkit.Color;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

/**
 * @author Elg
 */
public class BukkitModule extends SimpleModule {

    public BukkitModule() {
        VersionUtil.parseVersion("4.1.0-beta1", "no.kh498.util", "BukkitUtil");
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);

        context.addSerializers(new BukkitSerializers());

        context.addDeserializers(new BukkitDeserializers());
        context.addKeyDeserializers(new BukkitKeyDeserializers());

        context.setMixInAnnotations(ConfigurationSerializable.class, ConfigurationSerializableMixIn.class);
        context.setMixInAnnotations(Enchantment.class, EnchantmentMixIn.class);
        context.setMixInAnnotations(MaterialData.class, MaterialDataMixIn.class);
        context.setMixInAnnotations(Vector.class, VectorMixIn.class);
        context.setMixInAnnotations(Color.class, ColorMixIn.class);
    }
}
