package second.project.mungFriend.common.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import second.project.mungFriend.common.dto.DogBreed;
import second.project.mungFriend.common.service.DogBreedService;

@Controller
public class DogBreedController {
             
   @Autowired
    private DogBreedService dogBreedService;

    @PostMapping("/search")
    public String searchDogBreed(String searchInput, Model model,
    		@RequestParam(value="cp", required= false, defaultValue="1") int cp) {
    	
        List<DogBreed> breedsList = dogBreedService.findDogBreed(searchInput);
        
		Map<String, Object> map = dogBreedService.selectDogList1(searchInput,cp);

		model.addAttribute("map", map);
		
        
        
        System.out.println("검색 결과 조회 성공");
        
        return "adopt/dogList";
    }
}


