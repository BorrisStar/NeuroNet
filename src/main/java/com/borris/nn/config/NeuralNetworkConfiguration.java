package com.borris.nn.config;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class NeuralNetworkConfiguration {

    @Bean
    @Qualifier("convolutionNN")
    public MultiLayerConfiguration convolutionNeuralNetConfiguration(Nesterovs nesterovs) {

        return new NeuralNetConfiguration.Builder()
                .seed(ConfigConstants.SEED)
                .weightInit(WeightInit.XAVIER)
                .updater(nesterovs)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .list()
                    .layer(0, firstConvolutionLayer())
                    .layer(1, maxPoolingLayer())
                    .layer(2, secondConvolutionLayer())
                    .layer(3, maxPoolingLayer())
                    .layer(4, firstHiddenLayerCNN())
                    .layer(5, secondHiddenLayerCNN())
                    .layer(6, outputLayerCNN())
                .setInputType(InputType.convolutionalFlat(ConfigConstants.IMAGE_HEIGHT, ConfigConstants.IMAGE_WIDTH, ConfigConstants.CHANNELS)) // see comments above
                .build();

    }

    @Bean
    public Layer firstConvolutionLayer() {
        return new ConvolutionLayer.Builder()
                .nIn(ConfigConstants.CHANNELS)
                .nOut(20)
                .kernelSize(5, 5)
                .stride(1, 1)
                .activation(Activation.IDENTITY)
                .build();
    }

    @Bean
    @Scope("prototype")
    public Layer maxPoolingLayer() {
        return new SubsamplingLayer.Builder()
                .poolingType(SubsamplingLayer.PoolingType.MAX)
                .kernelSize(2, 2)
                .stride(2, 2)
                .build();
    }

    @Bean
    public Layer secondConvolutionLayer() {
        return new ConvolutionLayer.Builder()
                .nIn(20)
                .nOut(50)
                .kernelSize(5, 5)
                .stride(1, 1)
                .activation(Activation.IDENTITY)
                .build();
    }

    @Bean
    public Layer firstHiddenLayerCNN() {
        return new DenseLayer.Builder()
                .activation(Activation.RELU)
                .nIn(800)
                .nOut(128)
                .build();
    }

    @Bean
    public Layer secondHiddenLayerCNN() {
        return new DenseLayer.Builder()
                .activation(Activation.RELU)
                .nIn(128)
                .nOut(64)
                .build();
    }

    @Bean
    public Layer outputLayerCNN() {
        return new OutputLayer.Builder()
                .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .activation(Activation.SOFTMAX)
                .nOut(ConfigConstants.CLASS_COUNT)
                .build();
    }
}
