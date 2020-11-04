package com.borris.nn.rest;

import com.borris.nn.model.Pic;
import com.borris.nn.model.PicsResponseBody;
import com.borris.nn.model.ResultMatrix;
import com.borris.nn.model.ResultMatrixResponseBody;
import com.borris.nn.service.NNService;
import com.borris.nn.service.PicsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("pics")
public class PicsProcessController {

    private final PicsService picsService;
    private final NNService nnService;

    @GetMapping("/all")
    public ResponseEntity<?> getPicsAll() {
        PicsResponseBody result = new PicsResponseBody();

        List<Pic> pics = picsService.getPics();
        if (pics.isEmpty()) {
            result.setMsg("no pics found!");
        } else {
            result.setMsg("success!");
        }

        result.setPics(pics);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/processing")
    public ResponseEntity<?> processingHandWrittenDigits() throws IOException {
        ResultMatrixResponseBody result = new ResultMatrixResponseBody();
        ResultMatrix resultMatrix = new ResultMatrix();
        resultMatrix.setStatistcs(nnService.processingHandWrittenDigits());

        result.setMatrix(resultMatrix);
        if (result.getMatrix().getStatistcs().isEmpty()) {
            result.setMsg("no pics found!");
        } else {
            result.setMsg("success!");
        }
        return ResponseEntity.ok(result);
    }
}
