package cloud_kitchen.controller;
import java.util.List;

 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.CrossOrigin;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cloud_kitchen.entity.Order;
import cloud_kitchen.service.OrderService;
import cloud_kitchen.service.OrderService.OrderItemRequest;
@RestController @RequestMapping("/api/orders") @CrossOrigin(origins={"http://localhost:5173","https://cloud-kitchen-frontend-chi.vercel.app"}) public class OrderController {
 private final OrderService orderService; public OrderController(OrderService s){orderService=s;}
 @PostMapping public ResponseEntity<?> createOrder(@RequestParam Long userId,@RequestParam(defaultValue="COD") String paymentMethod,@RequestParam(defaultValue="PENDING") String paymentStatus,@RequestParam String phoneNumber,@RequestParam(required=false) String houseNumber,@RequestParam(required=false) String street,@RequestParam(required=false) String area,@RequestParam(required=false) String village,@RequestParam(required=false) String district,@RequestParam(required=false) String pincode,@RequestParam(required=false) String landmark,@RequestParam(required=false) Double latitude,@RequestParam(required=false) Double longitude,@RequestParam(required=false) Double locationAccuracy,@RequestBody List<OrderItemRequest> items){try{return ResponseEntity.ok(orderService.createOrder(userId,items,paymentMethod,paymentStatus,phoneNumber,houseNumber,street,area,village,district,pincode,landmark,latitude,longitude,locationAccuracy));}catch(RuntimeException e){return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));}}
 @GetMapping("/user/{userId}") public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId){return ResponseEntity.ok(orderService.getOrdersByUser(userId));}
 @GetMapping public ResponseEntity<List<Order>> getAllOrders(){return ResponseEntity.ok(orderService.getAllOrders());}
 @GetMapping("/{id}") public ResponseEntity<Order> getOrder(@PathVariable Long id){return ResponseEntity.ok(orderService.getOrderById(id));}
 @PutMapping("/{id}/status") public ResponseEntity<Order> updateStatus(@PathVariable Long id,@RequestBody StatusRequest r){return ResponseEntity.ok(orderService.updateStatus(id,r.getStatus()));}
 @PutMapping("/{id}/cancel") public ResponseEntity<?> cancelOrder(@PathVariable Long id){try{return ResponseEntity.ok(orderService.cancelOrder(id));}catch(RuntimeException e){return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));}}
 public static class StatusRequest{private String status;public String getStatus(){return status;}public void setStatus(String v){status=v;}} public static class ErrorResponse{private String message;public ErrorResponse(String m){message=m;}public String getMessage(){return message;}public void setMessage(String v){message=v;}}
}
