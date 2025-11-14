package com.mtrxxp.backend.service;

import com.mtrxxp.backend.model.OrderItemModel;
import com.mtrxxp.backend.model.OrderModel;
import com.mtrxxp.backend.model.ProductModel;
import com.mtrxxp.backend.model.dto.OrderItemRequest;
import com.mtrxxp.backend.model.dto.OrderItemResponse;
import com.mtrxxp.backend.model.dto.OrderRequest;
import com.mtrxxp.backend.model.dto.OrderResponse;
import com.mtrxxp.backend.repository.OrderRepository;
import com.mtrxxp.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse placeOrder(OrderRequest request) {
        OrderModel orderModel = new OrderModel();
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        orderModel.setOrderId(orderId);
        orderModel.setCustomerName(request.customerName());
        orderModel.setEmail(request.email());
        orderModel.setStatus("ORDER PLACED");
        orderModel.setOrderDate(LocalDate.now());

        List<OrderItemModel> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.items()) {
            ProductModel productModel = productRepository.findById(itemRequest.productId()).orElse(null);
            assert productModel != null;
            productModel.setStockQuantity(productModel.getStockQuantity() - itemRequest.quantity());
            productRepository.save(productModel);

            OrderItemModel orderItemModel = OrderItemModel.builder()
                    .product(productModel)
                    .quantity(itemRequest.quantity())
                    .totalPrice(BigDecimal.valueOf(productModel.getPrice() * itemRequest.quantity()))
                    .order(orderModel)
                    .build();
            orderItems.add(orderItemModel);
        }
        orderModel.setItems(orderItems);
        OrderModel savedOrder = orderRepository.save(orderModel);

        OrderResponse orderResponse = getOrderResponse(orderModel, savedOrder);
        return orderResponse;
    }

    private static OrderResponse getOrderResponse(OrderModel orderModel, OrderModel savedOrder) {
        List<OrderItemResponse> orderResponses = new ArrayList<>();
        for (OrderItemModel item : orderModel.getItems()) {
            OrderItemResponse itemResponses = new OrderItemResponse(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()
            );
            orderResponses.add(itemResponses);
        }

        OrderResponse orderResponse = new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                orderResponses
        );
        return orderResponse;
    }

    public List<OrderResponse> getAllOrderResponses() {
        List<OrderModel> orderModels = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (OrderModel orderModel : orderModels) {
            List<OrderItemResponse> orderItemResponses = new ArrayList<>();
            for (OrderItemModel orderItemModel : orderModel.getItems()) {
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        orderItemModel.getProduct().getName(),
                        orderItemModel.getQuantity(),
                        orderItemModel.getTotalPrice()
                );
                orderItemResponses.add(orderItemResponse);
            }
            OrderResponse orderResponse = new OrderResponse(
                    orderModel.getOrderId(),
                    orderModel.getCustomerName(),
                    orderModel.getEmail(),
                    orderModel.getStatus(),
                    orderModel.getOrderDate(),
                    orderItemResponses
            );
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }
}
