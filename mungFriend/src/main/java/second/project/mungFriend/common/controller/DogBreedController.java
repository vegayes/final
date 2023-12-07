package second.project.mungFriend.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import second.project.mungFriend.common.dto.DogBreed;
import second.project.mungFriend.common.service.DogBreedService;

@Controller
public class DogBreedController {
             
   @Autowired
    private DogBreedService dogBreedService;

    @GetMapping("/search")
    public String searchDogBreed(String searchInput, Model model) {
    	
        List<DogBreed> breedsList = dogBreedService.findDogBreed(searchInput);
        
        System.out.println("검색 결과 조회 성공");
        
        return "";
    }
}


