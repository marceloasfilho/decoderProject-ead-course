package com.github.marceloasfilho.shoppingcart.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReserveOutputDTO {
    private BigDecimal amount;
    private Integer invoiceNumber;
    private LocalDateTime dateTime;
    private Long reserveId;
    private String reserveDescription;
}
