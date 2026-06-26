package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Объявления", description = "Управление объявлениями и комментариями к ним")
public class AdsController {

    @Operation(summary = "Получение всех объявлений", description = "Публичный эндпоинт для просмотра всех товаров на площадке")
    @ApiResponse(responseCode = "200", description = "Список объявлений успешно получен")
    @GetMapping
    public ResponseEntity<Ads> getAllAds() {
        log.info("Request to get all ads");
        return ResponseEntity.ok(new Ads());
    }

    @Operation(summary = "Добавление объявления", description = "Позволяет авторизованному пользователю создать новое объявление с картинкой")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Объявление успешно создано"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAd(@RequestPart("properties") CreateOrUpdateAd properties,
                                       @RequestPart("image") MultipartFile image) {
        log.info("Request to add new ad");
        return ResponseEntity.status(HttpStatus.CREATED).body(new AdDto());
    }

    @Operation(summary = "Получение информации об объявлении", description = "Возвращает расширенную информацию о товаре и контактах продавца")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация успешно получена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable Integer id) {
        log.info("Request to get ad by id: {}", id);
        return ResponseEntity.ok(new ExtendedAd());
    }

    @Operation(summary = "Удаление объявления", description = "Позволяет автору или администратору удалить объявление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Объявление успешно удалено"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id) {
        log.info("Request to delete ad by id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновление информации об объявлении", description = "Позволяет изменить заголовок, цену и описание товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Объявление успешно обновлено"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAds(@PathVariable Integer id,
                                           @RequestBody CreateOrUpdateAd properties) {
        log.info("Request to update ad by id: {}", id);
        return ResponseEntity.ok(new AdDto());
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя", description = "Возвращает список только тех объявлений, которые создал текущий пользователь")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список личных объявлений получен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    @GetMapping("/me")
    public ResponseEntity<Ads> getAdsMe() {
        log.info("Request to get current user ads");
        return ResponseEntity.ok(new Ads());
    }

    @Operation(summary = "Обновление картинки объявления", description = "Позволяет загрузить новое фото для существующего товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Картинка успешно обновлена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,
                                              @RequestParam MultipartFile image) {
        log.info("Request to update ad image by id: {}", id);
        return ResponseEntity.ok(new byte[0]);
    }

    // ================= COMMENT ENDPOINTS =================

    @Operation(summary = "Получение комментариев объявления", description = "Возвращает список всех отзывов к выбранному товару")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарии успешно получены"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable Integer id) {
        log.info("Request to get comments for ad id: {}", id);
        return ResponseEntity.ok(new Comments());
    }

    @Operation(summary = "Добавление комментария к объявлению", description = "Позволяет оставить отзыв под карточкой товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id,
                                                 @RequestBody CreateOrUpdateComment properties) {
        log.info("Request to add comment to ad id: {}", id);
        return ResponseEntity.ok(new CommentDto());
    }

    @Operation(summary = "Удаление комментария", description = "Позволяет автору отзыва или администратору удалить комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно удален"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Комментарий или объявление не найдено")
    })
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId,
                                              @PathVariable Integer commentId) {
        log.info("Request to delete comment id: {} from ad id: {}", commentId, adId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление комментария", description = "Позволяет отредактировать текст своего отзыва")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно обновлен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Комментарий или объявление не найдено")
    })
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    @RequestBody CreateOrUpdateComment properties) {
        log.info("Request to update comment id: {} from ad id: {}", commentId, adId);
        return ResponseEntity.ok(new CommentDto());
    }
}