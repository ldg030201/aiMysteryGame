package sch.ldg.aimysterygame.phone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sch.ldg.aimysterygame.phone.entity.Clue;
import sch.ldg.aimysterygame.phone.service.ClueService;

@Controller
@RequestMapping(value = "/gallery")
public class GalleryController {
    private final ClueService clueService;

    public GalleryController(ClueService clueService) {
        this.clueService = clueService;
    }

    @ResponseBody
    @PostMapping(value = "/get-clue")
    public void getClue(@RequestBody Clue clue) {
        clueService.saveClue(clue);
    }
}
