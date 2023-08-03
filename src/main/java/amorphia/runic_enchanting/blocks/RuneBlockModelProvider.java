package amorphia.runic_enchanting.blocks;

import amorphia.runic_enchanting.RunicEnchanting;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class RuneBlockModelProvider implements ModelResourceProvider
{
	@Override
	public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) throws ModelProviderException
	{
		if(RuneBlockModelBuilder.getModelSupplierForIdentifier(resourceId).isPresent())
		{
			RunicEnchanting.LOGGER.info(resourceId);
			String jsonString = RuneBlockModelBuilder.getModelSupplierForIdentifier(resourceId).get().getValue().get();
			JsonUnbakedModel model = JsonUnbakedModel.deserialize(jsonString);
			model.id = resourceId.toString();
			return model;
		}
		return null;
	}
}
