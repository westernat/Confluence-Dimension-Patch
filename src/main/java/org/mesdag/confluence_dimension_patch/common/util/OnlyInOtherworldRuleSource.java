package org.mesdag.confluence_dimension_patch.common.util;

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
        if (((IDimensionAccessor) context.system).confluence_dimension_patch$allows()) {
            return otherworld.apply(context);
        }
        return (x, y, z) -> null;
    }
}
