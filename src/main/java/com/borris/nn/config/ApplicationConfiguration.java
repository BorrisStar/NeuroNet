package com.borris.nn.config;

import com.borris.nn.component.ScoreCalculatorImpl;
import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.io.labels.PathLabelGenerator;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration;
import org.deeplearning4j.earlystopping.saver.LocalFileModelSaver;
import org.deeplearning4j.earlystopping.termination.MaxEpochsTerminationCondition;
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.api.TrainingListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Configuration
public class ApplicationConfiguration {

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

    @Bean
    public DataSetIterator mnistTrain() throws IOException {
        return new MnistDataSetIterator(ConfigConstants.MINI_BATCH_SIZE, true, ConfigConstants.SEED);
    }

    @Bean
    public DataSetIterator mnistTest() throws IOException {
        return new MnistDataSetIterator(ConfigConstants.MINI_BATCH_SIZE, false, ConfigConstants.SEED);
    }

    @Bean
    public ScoreIterationListener scoreIterationListener() {
        return new ScoreIterationListener(100);
    }

    @Bean
    public MultiLayerNetwork multiLayerNetwork(
            @Qualifier("convolutionNN") MultiLayerConfiguration configuration,//@Qualifier("simpleNN")
            List<TrainingListener> trainingListenerList) {
        var model = new MultiLayerNetwork(configuration);
        trainingListenerList.forEach(model::addListeners);
        return model;
    }

    @Bean
    public EarlyStoppingConfiguration<MultiLayerNetwork> earlyStoppingConfiguration(ScoreCalculatorImpl scoreCalculator) {
        return new EarlyStoppingConfiguration
                .Builder<MultiLayerNetwork>()
                .epochTerminationConditions(new MaxEpochsTerminationCondition(ConfigConstants.MAX_EPOCHS))
                .scoreCalculator(scoreCalculator)
                .evaluateEveryNEpochs(1)
                .modelSaver(new LocalFileModelSaver(ConfigConstants.PRE_TRAINED_MODEL_PATH.toString()))
                .build();
    }

    @Bean
    public EarlyStoppingTrainer earlyStoppingTrainer(
            EarlyStoppingConfiguration<MultiLayerNetwork> earlyStoppingConfiguration,
            @Qualifier("convolutionNN") MultiLayerConfiguration configuration,
            DataSetIterator mnistTrain) {
        return new EarlyStoppingTrainer(earlyStoppingConfiguration, configuration, mnistTrain);
    }

    //NESTEROVS is referring to gradient descent with momentum
    @Bean
    public Nesterovs nesterovs() {
        return new Nesterovs.Builder()
                .learningRate(ConfigConstants.LEARNING_RATE)
                .build();
    }

    @Bean
    public ImagePreProcessingScaler imagePreProcessingScaler() {
        // Scale pixel values to 0-1
        return new ImagePreProcessingScaler(0, 1);
    }

    @Bean
    public ParentPathLabelGenerator parentPathLabelGenerator() {
        return new ParentPathLabelGenerator();
    }

    @Bean
    public ImageRecordReader imageRecordReader(PathLabelGenerator pathLabelGenerator) {
        return new ImageRecordReader(ConfigConstants.IMAGE_HEIGHT, ConfigConstants.IMAGE_WIDTH, ConfigConstants.CHANNELS, pathLabelGenerator);
    }
}
