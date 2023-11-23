package second.project.mungFriend.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/centerInfo")
public class MapController {

    @GetMapping("/map")
    public String mapPage() {
        return "centerInfo/map"; // map.html을 반환
    }
}