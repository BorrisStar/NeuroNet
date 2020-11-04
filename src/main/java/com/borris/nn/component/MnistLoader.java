package com.borris.nn.component;

import com.borris.nn.config.ConfigConstants;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

@Component
public class MnistLoader implements IMnistLoader {
    String workDir = "src/main/resources/pictures/black_background/";
    private final ImageRecordReader imageRecordReader;
    private final DataNormalization dataNormalization;
    private final Random randNumGen = new Random(ConfigConstants.SEED);

    @Autowired
    public MnistLoader(
            ImageRecordReader imageRecordReader,
            DataNormalization dataNormalization) {
        this.imageRecordReader = imageRecordReader;
        this.dataNormalization = dataNormalization;
    }

    @Override
    public DataSetIterator getDoubleCheckedDataSet() throws IOException {
        File files = Paths.get(workDir).toFile();
        FileSplit fileSplit = new FileSplit(files, NativeImageLoader.ALLOWED_FORMATS, randNumGen);
        imageRecordReader.initialize(fileSplit);

        // Test the Loaded Model with the fileSplit data
        DataSetIterator dataSetIterator = new RecordReaderDataSetIterator(imageRecordReader, 128, 1, ConfigConstants.CLASS_COUNT);

        // Scale pixel values to 0-1
        dataNormalization.fit(dataSetIterator);
        dataSetIterator.setPreProcessor(dataNormalization);
        return dataSetIterator;
    }
}
