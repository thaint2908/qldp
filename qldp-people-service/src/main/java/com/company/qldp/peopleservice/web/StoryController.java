package com.company.qldp.peopleservice.web;

import com.company.qldp.domain.Story;
import com.company.qldp.peopleservice.domain.assembler.StoryRepresentationModelAssembler;
import com.company.qldp.peopleservice.domain.dto.StoryDto;
import com.company.qldp.peopleservice.domain.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
    path = "/people",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class StoryController {
    
    private StoryService storyService;
    
    private StoryRepresentationModelAssembler assembler;
    
    @Autowired
    public StoryController(
        StoryService storyService,
        StoryRepresentationModelAssembler assembler
    ) {
        this.storyService = storyService;
        this.assembler = assembler;
    }
    
    @PostMapping(
        path = "/{id}/stories",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Story>>> addPeopleStory(
        @PathVariable("id") Integer id,
        @Valid StoryDto storyDto,
        ServerWebExchange exchange
    ) {
        Story story = storyService.createStory(id, storyDto);
        
        return assembler.toModel(story, exchange)
            .map(storyModel -> ResponseEntity
                .created(storyModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(storyModel)
            );
    }
    
    @GetMapping(path = "/{id}/stories")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<Story>>> getStoriesByPeopleId(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        List<Story> stories = storyService.getStoriesByPeopleId(id);
        
        return assembler.toCollectionModel(Flux.fromIterable(stories), exchange);
    }
    
    @GetMapping(path = "/{id}/stories/{storyId}")
    public Mono<EntityModel<Story>> getStoryById(
        @PathVariable("id") Integer peopleId,
        @PathVariable("storyId") Integer storyId,
        ServerWebExchange exchange
    ) {
        Story story = storyService.getStoryById(peopleId, storyId);
        
        return assembler.toModel(story, exchange);
    }
}
