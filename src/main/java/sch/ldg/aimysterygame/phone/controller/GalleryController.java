package sch.ldg.aimysterygame.phone.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sch.ldg.aimysterygame.phone.entity.Clue;
import sch.ldg.aimysterygame.phone.service.ClueService;

import java.util.List;

@Controller
public class GalleryController {
    private final ClueService clueService;

    public GalleryController(ClueService clueService) {
        this.clueService = clueService;
    }

    @GetMapping(value = "/phone/gallery")
    public String phoneGallery(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");
        model.addAttribute("userId", userId);

        List<Clue> clueList = clueService.findClueByUserId(userId);
        model.addAttribute("clueList", clueList);

        return "phone/gallery";
    }

    @ResponseBody
    @PostMapping(value = "/gallery/get-clue")
    public void getClue(@RequestBody Clue clue) {
        clueService.saveClue(clue);
    }
}
