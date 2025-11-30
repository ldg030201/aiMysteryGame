package sch.ldg.aimysterygame.phone.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sch.ldg.aimysterygame.phone.entity.Memo;
import sch.ldg.aimysterygame.phone.service.MemoService;

import java.util.List;

@Controller
public class MemoController {
    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping(value = "/phone/memo")
    public String phoneMemo(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");
        model.addAttribute("userId", userId);

        //목록 가져오기
        List<Memo> memoList = memoService.findMemoByUserId(userId);
        model.addAttribute("memoList", memoList);



        return "phone/memo";
    }
}
