package cloud_kitchen.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cloud_kitchen.entity.Food;
import cloud_kitchen.service.FoodService;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = {"http://localhost:5173", "https://cloud-kitchen-frontend-chi.vercel.app"})
public class FoodController {

    private final FoodService foodService;

    public FoodController(
            FoodService foodService
    ) {
        this.foodService =
                foodService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> createFood(

            @RequestParam String name,

            @RequestParam(required = false)
            String description,

            @RequestParam Double price,

            @RequestParam(required = false)
            String category,

            @RequestParam(
                    defaultValue = "true"
            )
            Boolean available,

            @RequestParam("image")
            MultipartFile image
    ) {

        try {

            return ResponseEntity.ok(
                    foodService.createFood(
                            name,
                            description,
                            price,
                            category,
                            available,
                            image
                    )
            );

        } catch (RuntimeException error) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    error.getMessage()
                            )
                    );
        }
    }

    @GetMapping
    public ResponseEntity<List<Food>>
    getAllFoods() {

        return ResponseEntity.ok(
                foodService.getAllFoods()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food>
    getFood(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                foodService.getFoodById(id)
        );
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]>
    getFoodImage(
            @PathVariable Long id
    ) {

        try {

            byte[] image =
                    foodService.getImage(id);

            String contentType =
                    foodService
                            .getImageContentType(id);

            MediaType mediaType;

            try {

                mediaType =
                        MediaType.parseMediaType(
                                contentType
                        );

            } catch (Exception error) {

                mediaType =
                        MediaType.IMAGE_JPEG;
            }

            return ResponseEntity
                    .ok()
                    .contentType(mediaType)
                    .body(image);

        } catch (RuntimeException error) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PutMapping(
            value = "/{id}",
            consumes =
                    MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> updateFood(

            @PathVariable Long id,

            @RequestParam String name,

            @RequestParam(required = false)
            String description,

            @RequestParam Double price,

            @RequestParam(required = false)
            String category,

            @RequestParam(
                    defaultValue = "true"
            )
            Boolean available,

            @RequestParam(
                    value = "image",
                    required = false
            )
            MultipartFile image
    ) {

        try {

            return ResponseEntity.ok(
                    foodService.updateFood(
                            id,
                            name,
                            description,
                            price,
                            category,
                            available,
                            image
                    )
            );

        } catch (RuntimeException error) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    error.getMessage()
                            )
                    );
        }
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<?> updateAvailability(

            @PathVariable Long id,

            @RequestParam Boolean available
    ) {

        try {

            return ResponseEntity.ok(
                    foodService
                            .updateAvailability(
                                    id,
                                    available
                            )
            );

        } catch (RuntimeException error) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    error.getMessage()
                            )
                    );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(
            @PathVariable Long id
    ) {

        try {

            foodService.deleteFood(id);

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Food deleted successfully"
                    )
            );

        } catch (RuntimeException error) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    error.getMessage()
                            )
                    );
        }
    }

    public static class ErrorResponse {

        private String message;

        public ErrorResponse(
                String message
        ) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(
                String message
        ) {
            this.message = message;
        }
    }

    public static class MessageResponse {

        private String message;

        public MessageResponse(
                String message
        ) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(
                String message
        ) {
            this.message = message;
        }
    }
}