package link.infra.packwiz.companion.cache;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CachedFile(
	String cachedLocation
) {
	public static final Codec<CachedFile> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			Codec.STRING.fieldOf("cachedLocation").forGetter(CachedFile::cachedLocation)
		).apply(instance, CachedFile::new);
	});
}
