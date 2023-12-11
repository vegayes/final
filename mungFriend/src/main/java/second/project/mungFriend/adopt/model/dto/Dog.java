package second.project.mungFriend.adopt.model.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Dog {

	private int dogNo;
	private int breedNo;
	private String dogName;
	private String dogBirthDay;
	private String dogGender;
	private String dogSize;
	private String dogPersonality;
	private String dogNeutering;
	private String dogVaccine;
	private String dogLike;
	private String dogHate;
	private String dogSpecialNote;
	private String dogAdmissionDate;
	private String dogAdoptFl;
	private String dogAdoptDate;
	
	// DOG_BREED 테이블 join
	private String breedName;

	// DOG_IMG 테이블 join
	private String thumbnail;
	
	// 이미지목록
	private List<DogImage> imageList;
	
	// 입소 신청 내역에서 가져온 이미지 
	private String imgPath;
	private String imgRename;
	
	private String selectedFilters;
	
	private LocalDate toDate;
	private LocalDate fromDate;
	
	
	
}
