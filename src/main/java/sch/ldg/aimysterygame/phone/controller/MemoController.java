package sch.ldg.aimysterygame.phone.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @ResponseBody
    @PostMapping(value = "/memo/save")
    public ResponseEntity<String> saveMemo(HttpServletRequest request) {
        Memo memo = new Memo();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(memo);
        binder.bind(request);

        memoService.saveMemo(memo);

        return ResponseEntity.ok("success");
    }
}
