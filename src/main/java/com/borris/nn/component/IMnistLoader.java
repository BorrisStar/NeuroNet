package com.borris.nn.component;

import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.IOException;

public interface IMnistLoader {
    DataSetIterator getDoubleCheckedDataSet() throws IOException;
}
