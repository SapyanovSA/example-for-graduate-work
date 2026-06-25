package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    @GetMapping
    public ResponseEntity<Ads> getAllAds() {
        log.info("Request to get all ads");
        return ResponseEntity.ok(new Ads());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAd(@RequestPart("properties") CreateOrUpdateAd properties,
                                       @RequestPart("image") MultipartFile image) {
        log.info("Request to add new ad");
        return ResponseEntity.status(HttpStatus.CREATED).body(new AdDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable Integer id) {
        log.info("Request to get ad by id: {}", id);
        return ResponseEntity.ok(new ExtendedAd());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id) {
        log.info("Request to delete ad by id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAds(@PathVariable Integer id,
                                           @RequestBody CreateOrUpdateAd properties) {
        log.info("Request to update ad by id: {}", id);
        return ResponseEntity.ok(new AdDto());
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getAdsMe() {
        log.info("Request to get current user ads");
        return ResponseEntity.ok(new Ads());
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,
                                              @RequestParam MultipartFile image) {
        log.info("Request to update ad image by id: {}", id);
        return ResponseEntity.ok(new byte[0]);
    }

    // COMMENT ENDPOINTS

    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable Integer id) {
        log.info("Request to get comments for ad id: {}", id);
        return ResponseEntity.ok(new Comments());
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id,
                                                 @RequestBody CreateOrUpdateComment properties) {
        log.info("Request to add comment to ad id: {}", id);
        return ResponseEntity.ok(new CommentDto());
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId,
                                              @PathVariable Integer commentId) {
        log.info("Request to delete comment id: {} from ad id: {}", commentId, adId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    @RequestBody CreateOrUpdateComment properties) {
        log.info("Request to update comment id: {} from ad id: {}", commentId, adId);
        return ResponseEntity.ok(new CommentDto());
    }
}
