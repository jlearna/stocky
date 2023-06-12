package com.jamesaworo.stocky.features.product.data.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Aworo James
 * @since 5/10/23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCategoryRequest {
    private Long id;

    @NotNull(message = "Title cannot be empty")
    private String title;
    private String description;
    private ProductCategoryRequest parent;

}