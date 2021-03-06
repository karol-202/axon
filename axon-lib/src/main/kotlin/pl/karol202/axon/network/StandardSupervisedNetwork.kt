package pl.karol202.axon.network

import pl.karol202.axon.layer.StandardSupervisedLayer
import pl.karol202.axon.neuron.StandardSupervisedNeuron
import pl.karol202.axon.specification.NetworkSpecification
import pl.karol202.axon.specification.networkBuilder

fun standardSupervisedNetwork(inputs: Int, init: StandardSupervisedNetwork.Specification.() -> Unit) =
		networkBuilder(StandardSupervisedNetwork.Specification(inputs), init)

fun StandardSupervisedNetwork.Specification.standardSupervisedLayer(init: StandardSupervisedLayer.Specification.() -> Unit) =
		addLayer(StandardSupervisedLayer.Specification(), init)

open class StandardSupervisedNetwork(inputs: Int,
                                     layers: List<StandardSupervisedLayer>) :
		AbstractNetwork<StandardSupervisedLayer, StandardSupervisedNeuron>(inputs, layers)
{
	class Specification(inputs: Int) :
			NetworkSpecification<StandardSupervisedNetwork, StandardSupervisedLayer, StandardSupervisedNeuron>(inputs)
	{
		override fun createNetwork(networkData: NetworkData) = StandardSupervisedNetwork(inputs, networkData.createLayers())
	}

	suspend fun learn(error: FloatArray, learnRate: Float)
	{
		checkOutputSize(error)

		var currentError = error
		layers.reversed().forEachIndexed { layerIndex, layer ->
			val tempError = currentError
			if(layerIndex > 0) currentError = layer.backpropagateError(tempError, layers[layerIndex - 1].size)
			layer.learn(error = tempError, learnRate = learnRate)
		}
	}
}