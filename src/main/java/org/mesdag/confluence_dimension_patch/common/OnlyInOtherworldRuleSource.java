package org.mesdag.confluence_dimension_patch.common;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;

public record OnlyInOtherworldRuleSource(SurfaceRules.RuleSource otherworld) implements SurfaceRules.RuleSource {
    public static final KeyDispatchDataCodec<OnlyInOtherworldRuleSource> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(instance -> instance.group(
            SurfaceRules.RuleSource.CODEC.fieldOf("otherworld").forGetter(OnlyInOtherworldRuleSource::otherworld)
    ).apply(instance, OnlyInOtherworldRuleSource::new)));

    @Override
    public KeyDispatchDataCodec<OnlyInOtherworldRuleSource> codec() {
        return CODEC;
    }

    @Override
    public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
        if (IDimensionAccessor.of(context.system).cdp$isNotOverworld()) {
            return otherworld.apply(context);
        }
        return (x, y, z) -> null;
    }
}
