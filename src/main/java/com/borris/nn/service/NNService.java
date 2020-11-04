package com.borris.nn.service;

import com.borris.nn.component.MnistLoader;
import com.borris.nn.component.MnistNN;
import com.borris.nn.config.ConfigConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NNService {

    private final MnistLoader mnistLoader;
    private final MnistNN mnistNN;

    /*
     * Picture's requirement:
     *   - background - black color
     *   - foreground (digit) - white color
     */
    public List<String> processingHandWrittenDigits() throws IOException {
        var doubleCheckedDataSet = mnistLoader.getDoubleCheckedDataSet();

        // Create Eval object with 10 possible classes
        Evaluation eval = new Evaluation(ConfigConstants.CLASS_COUNT);

        while (doubleCheckedDataSet.hasNext()) {
            DataSet next = doubleCheckedDataSet.next();
            INDArray output = mnistNN.output(next);
            eval.eval(next.getLabels(), output);
        }

        log.info(eval.stats());
        return List.of(eval.stats().split("\n"));
    }

    public void preProcessHandWrittenDigit(String script) {
        try {
            Process process = Runtime.getRuntime().exec(script);
            InputStream stdout = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    System.out.println("stdout: " + line);
                }
            } catch (IOException e) {
                System.out.println("Exception in reading output" + e.toString());
            }
        } catch (Exception e) {
            System.out.println("Exception Raised" + e.toString());
        }
    }
}
