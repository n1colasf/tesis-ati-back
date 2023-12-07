package com.pi.ati.ort.back.controllers;

import com.pi.ati.ort.back.classes.BimClient;
import com.pi.ati.ort.back.classes.ProjectRequest;
import com.pi.ati.ort.back.entities.Project;
import com.pi.ati.ort.back.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.bimserver.shared.exceptions.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {
    private final ProjectService projectService;
    private final BimClient bimClient;

    public ProjectController(ProjectService projectService, BimClient bimClient) {
        this.projectService = projectService;
        this.bimClient = bimClient;
    }

    //Docs GET ALL PROJECTS
    @Operation(summary = "Get all the projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))}),
    })
    @GetMapping("/projects")
    public List<Project> getAllUsers() throws ServiceException {
        //List<SProject> projects = bimClient.getAllProjectsByUser(true, true);
        return projectService.findAllProjects();
    }

    //Docs GET PROJECT BY ID
    @Operation(summary = "Get the project by poid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Project.class))}),
    })
    @GetMapping("/projects/id/{id}")
    public Optional<Project> getProjectById(@Parameter(description="The Project's poid") @PathVariable long id) {
        return projectService.findProjectById(id);
    }

    //Docs GET PROJECT BY NAME
    @Operation(summary = "Get the project by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Project.class))}),
    })
    @GetMapping("/projects/{name}")
    public Project getProjectByName(@Parameter(description="The Project's name") @PathVariable String name) {
        return projectService.findProjectByName(name);
    }

    //Docs CREATE PROJECT
    @Operation(summary = "Create a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created Ok",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectRequest.class))}),
    })
    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@Valid @RequestBody ProjectRequest projectRequest) throws ServiceException {
        Project project = new Project();
        String name = projectRequest.getName();
        System.out.println(name); // POR QUE SALE NULL??
        project.setName(name);
        String schema = projectRequest.getSchema();
        System.out.println(schema); // POR QUE SALE NULL??
        project.setSchema(schema);
        //project.setName("TestProject");
        //project.setPoid(726496904729L);
        //project.setSchema("ifc4");

        //bimClient.createProject(project.getName(), project.getSchema());
        Project createdProject = projectService.createProject(project);

        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }
}
