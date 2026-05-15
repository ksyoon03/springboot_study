package com.study.koreait.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddProductReqDto {
    @NotBlank(message = "상품명은 비울 수 없습니다.")
    @Size(max = 20, message = "상품명은 20자 이내여야 합니다.")
    private String ProductName;

    @Min(value = 1000, message = "최저가는 1000원입니다.")
    private int price;

    @PositiveOrZero(message = "재고량은 음수가 될 수 없습니다.")
    private int stock;
}
