package com.jamesaworo.stocky.features.product.data.interactor.contract;

import com.jamesaworo.stocky.features.product.data.request.ProductTaxRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IProductTaxInteractor {

	ResponseEntity<List<ProductTaxRequest>> findAll();

	ResponseEntity<Optional<ProductTaxRequest>> save(ProductTaxRequest request);

	ResponseEntity<Optional<Boolean>> remove(Long id);

	ResponseEntity<Optional<ProductTaxRequest>> find(Long id);

	ResponseEntity<Optional<ProductTaxRequest>> update(ProductTaxRequest request);
}