package cloud_kitchen.service;

import java.time.LocalDateTime;
 import java.util.List;

 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;

 import cloud_kitchen.entity.Food;
 import cloud_kitchen.entity.Order;
 import cloud_kitchen.entity.OrderItem;
 import cloud_kitchen.entity.User;
 import cloud_kitchen.repository.FoodRepository;
 import cloud_kitchen.repository.OrderItemRepository;
 import cloud_kitchen.repository.OrderRepository;
 import cloud_kitchen.repository.UserRepository;

@Service public class OrderService {
 private static final double DELIVERY_FEE=40.0; private static final double TAX_AMOUNT=10.0;
 private final OrderRepository orderRepository; private final OrderItemRepository orderItemRepository; private final UserRepository userRepository; private final FoodRepository foodRepository;
 public OrderService(OrderRepository a,OrderItemRepository b,UserRepository c,FoodRepository d){orderRepository=a;orderItemRepository=b;userRepository=c;foodRepository=d;}
 @Transactional public Order createOrder(Long userId,List<OrderItemRequest> items,String paymentMethod,String paymentStatus,String phoneNumber,String houseNumber,String street,String area,String village,String district,String pincode,String landmark,Double latitude,Double longitude,Double locationAccuracy){
  User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
  if(items==null||items.isEmpty()) throw new RuntimeException("Order cannot be empty");
  if(phoneNumber==null||!phoneNumber.matches("\\d{10}")) throw new RuntimeException("Please enter a valid 10-digit mobile number");
  if(pincode==null||!pincode.matches("\\d{6}")) throw new RuntimeException("Please enter a valid 6-digit PIN code");
  if(latitude==null||longitude==null) throw new RuntimeException("Please share your current location");
  Order order=new Order(); order.setUser(user); order.setStatus("PLACED"); order.setOrderDate(LocalDateTime.now()); order.setPhoneNumber(phoneNumber);
  order.setHouseNumber(houseNumber);order.setStreet(street);order.setArea(area);order.setVillage(village);order.setDistrict(district);order.setPincode(pincode);order.setLandmark(landmark);order.setLatitude(latitude);order.setLongitude(longitude);order.setLocationAccuracy(locationAccuracy);
  if(paymentMethod==null||paymentMethod.isBlank()) paymentMethod="COD"; if(paymentStatus==null||paymentStatus.isBlank()) paymentStatus=paymentMethod.equalsIgnoreCase("UPI")?"PAID":"PENDING"; order.setPaymentMethod(paymentMethod.toUpperCase()); order.setPaymentStatus(paymentStatus.toUpperCase());
  double subtotal=0; for(OrderItemRequest r:items){Food food=foodRepository.findById(r.getFoodId()).orElseThrow(()->new RuntimeException("Food not found: "+r.getFoodId())); if(food.getAvailable()!=null&&!food.getAvailable()) throw new RuntimeException(food.getName()+" is currently unavailable"); if(r.getQuantity()==null||r.getQuantity()<=0) throw new RuntimeException("Invalid quantity for "+food.getName()); subtotal+=food.getPrice()*r.getQuantity();}
  order.setSubtotal(subtotal);order.setDeliveryFee(DELIVERY_FEE);order.setTaxAmount(TAX_AMOUNT);order.setTotalAmount(subtotal+DELIVERY_FEE+TAX_AMOUNT); Order saved=orderRepository.save(order);
  for(OrderItemRequest r:items){Food food=foodRepository.findById(r.getFoodId()).orElseThrow(()->new RuntimeException("Food not found: "+r.getFoodId())); OrderItem oi=new OrderItem();oi.setOrder(saved);oi.setFood(food);oi.setQuantity(r.getQuantity());oi.setPrice(food.getPrice());orderItemRepository.save(oi);} return orderRepository.save(saved);
 }
 public List<Order> getOrdersByUser(Long id){return orderRepository.findByUserIdOrderByOrderDateDesc(id);} public List<Order> getAllOrders(){return orderRepository.findAllByOrderByOrderDateDesc();} public Order getOrderById(Long id){return orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));}
 public Order updateStatus(Long id,String status){Order o=getOrderById(id);o.setStatus(status.toUpperCase());return orderRepository.save(o);} public Order cancelOrder(Long id){Order o=getOrderById(id);if(!o.getStatus().equalsIgnoreCase("PLACED")) throw new RuntimeException("This order can no longer be cancelled");o.setStatus("CANCELLED");return orderRepository.save(o);}
 public static class OrderItemRequest{private Long foodId;private Integer quantity;public Long getFoodId(){return foodId;}public void setFoodId(Long v){foodId=v;}public Integer getQuantity(){return quantity;}public void setQuantity(Integer v){quantity=v;}}
}
