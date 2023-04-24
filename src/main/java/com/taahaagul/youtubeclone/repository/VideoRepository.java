package com.taahaagul.youtubeclone.repository;

import com.taahaagul.youtubeclone.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
