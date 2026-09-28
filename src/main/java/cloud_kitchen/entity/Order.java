package cloud_kitchen.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity @Table(name="orders")
public class Order {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne @JoinColumn(name="user_id") @JsonIgnoreProperties({"password"}) private User user;
 private Double subtotal; private Double deliveryFee; private Double taxAmount; private Double totalAmount;
 private String status; private String paymentMethod; private String paymentStatus; private LocalDateTime orderDate;
 private String phoneNumber;
 private String houseNumber; private String street; private String area; private String village; private String district; private String pincode; private String landmark;
 private Double latitude; private Double longitude; private Double locationAccuracy;
 @OneToMany(mappedBy="order",cascade=CascadeType.ALL) private List<OrderItem> items=new ArrayList<>();
 public Order(){}
 public Long getId(){return id;} public void setId(Long v){id=v;} public User getUser(){return user;} public void setUser(User v){user=v;}
 public Double getSubtotal(){return subtotal;} public void setSubtotal(Double v){subtotal=v;} public Double getDeliveryFee(){return deliveryFee;} public void setDeliveryFee(Double v){deliveryFee=v;} public Double getTaxAmount(){return taxAmount;} public void setTaxAmount(Double v){taxAmount=v;} public Double getTotalAmount(){return totalAmount;} public void setTotalAmount(Double v){totalAmount=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;} public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;} public String getPaymentStatus(){return paymentStatus;} public void setPaymentStatus(String v){paymentStatus=v;} public LocalDateTime getOrderDate(){return orderDate;} public void setOrderDate(LocalDateTime v){orderDate=v;}
 public String getPhoneNumber(){return phoneNumber;} public void setPhoneNumber(String v){phoneNumber=v;}
 public String getHouseNumber(){return houseNumber;} public void setHouseNumber(String v){houseNumber=v;} public String getStreet(){return street;} public void setStreet(String v){street=v;} public String getArea(){return area;} public void setArea(String v){area=v;} public String getVillage(){return village;} public void setVillage(String v){village=v;} public String getDistrict(){return district;} public void setDistrict(String v){district=v;} public String getPincode(){return pincode;} public void setPincode(String v){pincode=v;} public String getLandmark(){return landmark;} public void setLandmark(String v){landmark=v;}
 public Double getLatitude(){return latitude;} public void setLatitude(Double v){latitude=v;} public Double getLongitude(){return longitude;} public void setLongitude(Double v){longitude=v;} public Double getLocationAccuracy(){return locationAccuracy;} public void setLocationAccuracy(Double v){locationAccuracy=v;} public List<OrderItem> getItems(){return items;} public void setItems(List<OrderItem> v){items=v;}
}
