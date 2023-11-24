package second.project.mungFriend.admissionApply.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import second.project.mungFriend.admissionApply.model.dto.Admission;

public interface FreeAdmissionService {

	int admissionInsert(Admission admission, MultipartFile admissionProfile)throws IllegalStateException, IOException;

}
