package amorphia.runic_enchanting.mixins;

import amorphia.runic_enchanting.blocks.RuneBlockModelBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Mixin(ModelLoader.class)
public class RuneBlockModelLoaderMixin
{
	@Shadow @Final private ResourceManager resourceManager;

	@Environment(EnvType.CLIENT)
	@Inject(method = "loadModelFromJson", at = @At("HEAD"), cancellable = true)
	private void re_loadModelFromSupplier(Identifier identifier, CallbackInfoReturnable<JsonUnbakedModel> cir)
	{
		RuneBlockModelBuilder.getModelSupplierForIdentifier(identifier).ifPresent(supplier -> {
			Resource resource = null;
			Reader reader = null;
			JsonUnbakedModel model = null;

			try
			{
				resource = resourceManager.getResource(new Identifier(identifier.getNamespace(), "models/" + identifier.getPath() + ".json")).orElse(null);
				if (resource != null)
				{
					reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
					model = JsonUnbakedModel.deserialize(reader);
				}
			}
			catch (IOException thrown)
			{
				//silent
			}
			finally
			{
				IOUtils.closeQuietly(reader);
			}

			if (model == null)
			{
				model = JsonUnbakedModel.deserialize(supplier.getValue().get());
			}

			if (model != null)
			{
				model.id = identifier.toString();
				cir.setReturnValue(model);
				cir.cancel();
			}
		});
	}
}
