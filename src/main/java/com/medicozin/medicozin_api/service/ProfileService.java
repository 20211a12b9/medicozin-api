package com.medicozin.medicozin_api.service;

import com.medicozin.medicozin_api.entity.Profile;
import com.medicozin.medicozin_api.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    private static final String UPLOADED_FOLDER = "C:/Users/venka/medicozin-api/uploads/";

    public Profile createPost(Long studentId, MultipartFile image) {
        Profile post =new Profile();
        post.setStudentId(studentId);


        if (image != null && !image.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, image.getBytes());
                // Store the image URL
                post.setImageUrl("/uploads/" + fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return profileRepository.save(post);
    }
    public List<Object[]> getAllPostsbyId(Long userid) {
        return profileRepository.findAllByuserId(userid);
    }
}
