package com.taahaagul.youtubeclone.service;

import com.taahaagul.youtubeclone.model.User;
import com.taahaagul.youtubeclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String sub = ((Jwt)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");

        return userRepository.findBySub(sub)
                .orElseThrow(()-> new IllegalArgumentException("Cannot find user !"));
    }

    public void addToLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToLikeVideos(videoId);
        userRepository.save(currentUser);
    }

    public boolean ifLikedVideo(String videoId) {
        return getCurrentUser().getLikedVideos().stream().anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public boolean ifDisLikedVideo(String videoId) {
        return getCurrentUser().getDislikedVideos().stream().anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public void removeFromLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public void removeFromDisLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromDisLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    public void addToDislikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToDislikeVideos(videoId);
        userRepository.save(currentUser);
    }

    public void addVideoHistory(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToVideoHistory(videoId);
        userRepository.save(currentUser);
    }

    public void subscribeUser(String userId) {
        User currentUSer = getCurrentUser();
        currentUSer.addToSubscribedToUser(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user!"));
        user.addToSubscribers(currentUSer.getId());

        userRepository.save(currentUSer);
        userRepository.save(user);
    }

    public void unSubscribeUser(String userId) {
        User currentUSer = getCurrentUser();
        currentUSer.removeFromSubscribedToUser(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user!"));
        user.removeFromSubscribers(currentUSer.getId());

        userRepository.save(currentUSer);
        userRepository.save(user);
    }

    public Set<String> userHistory(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user!"));
        return user.getVideoHistory();
    }
}
