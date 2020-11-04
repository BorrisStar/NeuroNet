package com.borris.nn.config;

import java.nio.file.Path;

public class ConfigConstants {
    /**
     * Number prediction classes.
     */
    public static final int CLASS_COUNT = 10;

    /**
     * Mini batch gradient descent size or number of matrices processed in parallel.
     */
    public static final int MINI_BATCH_SIZE = 16;// Number of training epochs

    /**
     * Number of total traverses through data.
     * with 5 epochs we will have 5/@MINI_BATCH_SIZE iterations or weights updates
     */
    public static final int EPOCHS = 5;

    /**
     * Number of total traverses through data. In this case it is used as the maximum epochs we allow
     * with 5 epochs we will have 5/@MINI_BATCH_SIZE iterations or weights updates
     */
    public static final int MAX_EPOCHS = 20;

    /**
     * The alpha learning rate defining the size of step towards the minimum
     */
    public static final double LEARNING_RATE = 0.01;

    public static final int SEED = 123;
    public static final int IMAGE_WIDTH = 28;
    public static final int IMAGE_HEIGHT = 28;
    public static final int CHANNELS = 1;
    public static final Path PRE_TRAINED_MODEL_PATH = Path.of("models", "training");

}
