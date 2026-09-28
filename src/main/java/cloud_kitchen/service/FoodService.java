package cloud_kitchen.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cloud_kitchen.entity.Food;
import cloud_kitchen.repository.FoodRepository;

@Service
public class FoodService {

    private static final String BACKEND_URL =
        "https://cloud-kitchen-backend-production.up.railway.app";

    private final FoodRepository foodRepository;

    public FoodService(
            FoodRepository foodRepository
    ) {
        this.foodRepository =
                foodRepository;
    }

    public Food createFood(
            String name,
            String description,
            Double price,
            String category,
            Boolean available,
            MultipartFile image
    ) {

        validateFood(
                name,
                price,
                image
        );

        Food food = new Food();

        food.setName(name);
        food.setDescription(description);
        food.setPrice(price);
        food.setCategory(category);

        food.setAvailable(
                available == null
                        ? true
                        : available
        );

        if (image != null &&
                !image.isEmpty()) {

            saveImage(
                    food,
                    image
            );
        }

        Food saved =
                foodRepository.save(food);

        /*
         * Each food receives its own image URL.
         */
        if (food.getImageData() != null) {

            saved.setImageUrl(
                    BACKEND_URL +
                    "/api/foods/" +
                    saved.getId() +
                    "/image"
            );

            saved =
                    foodRepository.save(saved);
        }

        return saved;
    }

    public List<Food> getAllFoods() {

        List<Food> foods =
                foodRepository.findAll();

        /*
         * Make sure every uploaded image
         * has its correct URL.
         */
        for (Food food : foods) {

            if (food.getImageData() != null) {

                food.setImageUrl(
                        BACKEND_URL +
                        "/api/foods/" +
                        food.getId() +
                        "/image"
                );
            }
        }

        return foods;
    }

    public Food getFoodById(
            Long id
    ) {

        Food food =
                foodRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Food not found"
                                )
                        );

        if (food.getImageData() != null) {

            food.setImageUrl(
                    BACKEND_URL +
                    "/api/foods/" +
                    food.getId() +
                    "/image"
            );
        }

        return food;
    }

    public Food updateFood(
            Long id,
            String name,
            String description,
            Double price,
            String category,
            Boolean available,
            MultipartFile image
    ) {

        Food food =
                foodRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Food not found"
                                )
                        );

        if (name == null ||
                name.trim().isEmpty()) {

            throw new RuntimeException(
                    "Food name is required"
            );
        }

        if (price == null ||
                price < 0) {

            throw new RuntimeException(
                    "Valid price is required"
            );
        }

        food.setName(name);
        food.setDescription(description);
        food.setPrice(price);
        food.setCategory(category);

        if (available != null) {
            food.setAvailable(
                    available
            );
        }

        /*
         * Image is optional while editing.
         * If owner doesn't select a new image,
         * old image remains.
         */
        if (image != null &&
                !image.isEmpty()) {

            saveImage(
                    food,
                    image
            );
        }

        Food saved =
                foodRepository.save(food);

        if (saved.getImageData() != null) {

            saved.setImageUrl(
                    BACKEND_URL +
                    "/api/foods/" +
                    saved.getId() +
                    "/image"
            );

            saved =
                    foodRepository.save(saved);
        }

        return saved;
    }

    public Food updateAvailability(
            Long id,
            Boolean available
    ) {

        Food food =
                foodRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Food not found"
                                )
                        );

        food.setAvailable(
                available
        );

        return foodRepository.save(food);
    }

    public void deleteFood(
            Long id
    ) {

        if (!foodRepository
                .existsById(id)) {

            throw new RuntimeException(
                    "Food not found"
            );
        }

        foodRepository.deleteById(id);
    }

    public byte[] getImage(
            Long id
    ) {

        Food food =
                foodRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Food not found"
                                )
                        );

        if (food.getImageData() == null) {

            throw new RuntimeException(
                    "Image not found"
            );
        }

        return food.getImageData();
    }

    public String getImageContentType(
            Long id
    ) {

        Food food =
                foodRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Food not found"
                                )
                        );

        return food.getImageContentType();
    }

    private void saveImage(
            Food food,
            MultipartFile image
    ) {

        if (image.getContentType() == null ||
                !image.getContentType()
                        .startsWith("image/")) {

            throw new RuntimeException(
                    "Only image files are allowed"
            );
        }

        /*
         * Keep uploads reasonably small.
         */
        if (image.getSize() >
                5 * 1024 * 1024) {

            throw new RuntimeException(
                    "Image must be smaller than 5 MB"
            );
        }

        try {

            food.setImageData(
                    image.getBytes()
            );

            food.setImageContentType(
                    image.getContentType()
            );

        } catch (IOException error) {

            throw new RuntimeException(
                    "Failed to save image",
                    error
            );
        }
    }

    private void validateFood(
            String name,
            Double price,
            MultipartFile image
    ) {

        if (name == null ||
                name.trim().isEmpty()) {

            throw new RuntimeException(
                    "Food name is required"
            );
        }

        if (price == null ||
                price < 0) {

            throw new RuntimeException(
                    "Valid price is required"
            );
        }

        if (image == null ||
                image.isEmpty()) {

            throw new RuntimeException(
                    "Please upload a food image"
            );
        }
    }
}