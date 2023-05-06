package link.infra.packwiz.companion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.terraformersmc.modmenu.api.ModMenuApi;

import link.infra.packwiz.companion.cache.PackwizCache;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModOrigin.Kind;

public class PackwizCompanionModMenuCompatibility implements ModMenuApi {
	public static final Logger LOGGER = LoggerFactory.getLogger("Packwiz Companion");
	private static final FabricLoader LOADER = FabricLoader.getInstance();

	private static boolean isModLoadedFromPath(ModContainer mod, Path path) {
		while (mod.getContainingMod().isPresent()) {
			mod = mod.getContainingMod().get();
		}

		if (mod.getOrigin().getKind() == Kind.PATH) {
			for (var origin : mod.getOrigin().getPaths()) {
				if (origin.equals(path)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void attachModpackBadges(Consumer<String> consumer) {
		var gameDir = LOADER.getGameDir();
		var gson = new Gson();

		try (var reader = Files.newBufferedReader(PackwizCache.PATH)) {
			var jsonReader = gson.newJsonReader(reader);
			var element = JsonParser.parseReader(jsonReader);

			var result = PackwizCache.CODEC.parse(JsonOps.INSTANCE, element);
			var cache = result.getOrThrow(false, message -> {});

			cache.cachedFiles().forEach((key, cachedFile) -> {
				var cachedLocationPath = gameDir.resolve(cachedFile.cachedLocation());

				for (var mod : LOADER.getAllMods()) {
					if (isModLoadedFromPath(mod, cachedLocationPath)) {
						consumer.accept(mod.getMetadata().getId());
					}
				}
			});
		} catch (NoSuchFileException e) {
			// Ignore
		} catch (IOException e) {
			LOGGER.warn("Failed to read Packwiz cache", e);
		}
	}
}
