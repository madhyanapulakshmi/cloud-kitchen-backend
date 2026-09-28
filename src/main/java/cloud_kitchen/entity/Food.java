package cloud_kitchen.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private Double price;

    private String category;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean available = true;

    /*
     * Actual uploaded image.
     * Stored directly inside MySQL.
     */
    @Lob
    @Column(
            name = "image_data",
            columnDefinition = "LONGBLOB"
    )
    @JsonIgnore
    private byte[] imageData;

    /*
     * Example:
     * image/jpeg
     * image/png
     * image/webp
     */
    @Column(
            name = "image_content_type",
            length = 100
    )
    @JsonIgnore
    private String imageContentType;

    public Food() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description
    ) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(
            String imageUrl
    ) {
        this.imageUrl = imageUrl;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(
            Boolean available
    ) {
        this.available = available;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(
            byte[] imageData
    ) {
        this.imageData = imageData;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(
            String imageContentType
    ) {
        this.imageContentType =
                imageContentType;
    }
}