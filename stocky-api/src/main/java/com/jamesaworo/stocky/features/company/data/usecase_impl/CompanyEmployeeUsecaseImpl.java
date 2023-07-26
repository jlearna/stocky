/*
 * @Author: james.junior
 * @Date: 6/18/23 13:54
 *
 * @Project: stocky-api
 */

package com.jamesaworo.stocky.features.company.data.usecase_impl;

import com.jamesaworo.stocky.core.annotations.Usecase;
import com.jamesaworo.stocky.features.company.data.repository.CompanyEmployeeRepository;
import com.jamesaworo.stocky.features.company.domain.entity.CompanyEmployee;
import com.jamesaworo.stocky.features.company.domain.usecase.ICompanyEmployeeUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

@Usecase
@RequiredArgsConstructor
@Slf4j
public class CompanyEmployeeUsecaseImpl implements ICompanyEmployeeUsecase {
    private final CompanyEmployeeRepository repository;

    @Override
    public Optional<CompanyEmployee> findOne(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public CompanyEmployee save(CompanyEmployee customer) {
        return this.repository.save(customer);
    }

    @Override
    public List<CompanyEmployee> findMany(Specification<CompanyEmployee> specification) {
        return this.repository.findAll(specification);
    }

    @Override
    public Page<CompanyEmployee> findMany(Specification<CompanyEmployee> specification, Pageable pageable) {
        return this.repository.findAll(specification, pageable);
    }

    @Override
    public Optional<Boolean> update(CompanyEmployee customer) {
        try {
            Optional<CompanyEmployee> optionalCustomer = this.findOne(customer.getId());
            return optionalCustomer.map(value -> {
                this.save(customer);
                return Boolean.TRUE;
            });

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return of(Boolean.FALSE);
        }
    }

    @Override
    public Optional<Boolean> toggleActiveStatus(Long id) {
        Optional<CompanyEmployee> optionalCustomer = this.findOne(id);
        return optionalCustomer.map(this::updateCustomerIsActiveStatus);

    }

    private Boolean updateCustomerIsActiveStatus(CompanyEmployee customer) {
        int count = this.repository.updateIsActiveStatus(!customer.getIsActiveStatus(), customer.getId());
        return count == 1;
    }
}
