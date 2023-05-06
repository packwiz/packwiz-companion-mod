package link.infra.packwiz.companion.cache;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.fabricmc.loader.api.FabricLoader;

public record PackwizCache(
	Map<String, CachedFile> cachedFiles
) {
	private static final String FILE_NAME = "packwiz.json";
	public static final Path PATH = FabricLoader.getInstance().getGameDir().resolve(FILE_NAME);

	public static final Codec<PackwizCache> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			Codec.unboundedMap(Codec.STRING, CachedFile.CODEC).optionalFieldOf("cachedFiles", Collections.emptyMap()).forGetter(PackwizCache::cachedFiles)
		).apply(instance, PackwizCache::new);
	});
}
