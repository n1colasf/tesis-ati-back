package com.pi.ati.ort.back.controllers;

import com.pi.ati.ort.back.classes.BimClient;
import com.pi.ati.ort.back.classes.ModelRequest;
import com.pi.ati.ort.back.entities.Model;
import com.pi.ati.ort.back.services.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.bimserver.shared.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class ModelController {

    private final ModelService modelService;
    private final BimClient bimClient;

    @Autowired
    public ModelController(ModelService modelService, BimClient bimClient) {
        this.modelService = modelService;
        this.bimClient = bimClient;
    }

    // UPLOAD MODEL -----------------------------------------------------
    // En proceso!
    @Operation(summary = "Upload a model to a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Model uploaded Ok",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Model.class))}),
    })
    @PostMapping("/projects/id/{id}/model")
    public ResponseEntity<Model> uploadModel(@RequestBody ModelRequest modelRequest) throws ServiceException {
        Model model = new Model();
        model.setProjectId(modelRequest.getModelId());
        model.setName(modelRequest.getName());
        modelService.createModel(model);
        //bimClient.uploadModel(modelRequest.getFile(), modelRequest.getPath());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
