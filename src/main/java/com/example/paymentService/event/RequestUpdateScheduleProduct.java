package com.example.paymentService.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateScheduleProduct {
    private Long productId;
    private Long orderId;

}
