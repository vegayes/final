package second.project.mungFriend.common.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import second.project.mungFriend.common.service.DogBreedService;

@Controller
public class DogBreedController {
             
   @Autowired
    private DogBreedService dogBreedService;

    @PostMapping("/search")
    public String searchDogBreed(String searchInput, Model model,
    		@RequestParam(value="cp", required= false, defaultValue="1") int cp) {
    	
    	System.out.println("검색 :"  + searchInput);
    	
		Map<String, Object> map = dogBreedService.selectDogList(cp, searchInput);

		model.addAttribute("map", map);
		
        System.out.println("검색 결과 조회 성공");
        
        return "adopt/dogList";
    }
    
    
    
    
}


