/*
 * @Author: james.junior
 * @Date: 6/26/23 17:35
 *
 * @Project: stocky-api
 */

package com.jamesaworo.stocky.features.stock.data.interactor.implementation;

import com.jamesaworo.stocky.core.annotations.Interactor;
import com.jamesaworo.stocky.features.company.domain.entity.CompanySupplier;
import com.jamesaworo.stocky.features.company.domain.usecase.ICompanySupplierUsecase;
import com.jamesaworo.stocky.features.product.domain.entity.Product;
import com.jamesaworo.stocky.features.product.domain.usecase.IProductUsecase;
import com.jamesaworo.stocky.features.stock.data.interactor.contract.IStockExpensesInteractor;
import com.jamesaworo.stocky.features.stock.data.interactor.contract.IStockItemInteractor;
import com.jamesaworo.stocky.features.stock.data.interactor.contract.IStockPriceInteractor;
import com.jamesaworo.stocky.features.stock.data.interactor.contract.IStockSettlementInteractor;
import com.jamesaworo.stocky.features.stock.data.request.StockExpensesRequest;
import com.jamesaworo.stocky.features.stock.data.request.StockItemRequest;
import com.jamesaworo.stocky.features.stock.domain.entity.*;
import com.jamesaworo.stocky.features.stock.domain.usecase.IStockItemUsecase;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;

@Interactor
@RequiredArgsConstructor
public class StockItemInteractor implements IStockItemInteractor {
    private final IStockItemUsecase usecase;
    private final ModelMapper mapper;
    private final IProductUsecase productUsecase;
    private final ICompanySupplierUsecase supplierUsecase;
    private final IStockPriceInteractor stockPriceInteractor;
    private final IStockSettlementInteractor settlementInteractor;
    private final IStockExpensesInteractor expensesInteractor;


    /**
     * Saves multiple StockItemRequest objects to the database and associates them with the given Stock.
     * <p>
     * <p>
     * Steps:
     * - Checks if the requests list is not empty.
     * - If not empty, iterates over the requests and performs the following actions:
     * - Creates a new StockItem object.
     * - Sets the basic details of the StockItem using the setStockItemBasicDetails method.
     * - Sets the supplier of the StockItem using the setItemSupplier method.
     * - Sets the product of the StockItem using the setItemProduct method.
     * - Sets the settlement of the StockItem using the setItemSettlement method.
     * - Sets the price of the StockItem using the setItemPrice method.
     * - Associates the Stock object with the StockItem.
     * - Saves the StockItem using the usecase's save method.
     * - Updates the expenses of the StockItem using the updateStockItemExpenses method.
     *
     * @param requests The list of StockItemRequest objects to be saved.
     * @param stock    The Stock object to associate the saved StockItems with.
     */
    @Override
    public void saveMany(List<StockItemRequest> requests, Stock stock) {
        if (!isEmpty(requests)) {
            for (StockItemRequest request : requests) {
                StockItem model = new StockItem();
                this.setStockItemBasicDetails(model, request);
                this.setItemSupplier(model, request);
                this.setItemProduct(model, request);
                this.setItemSettlement(model, request);
                this.setItemPrice(model, request);
                model.setStock(stock);
                StockItem newStockItem = this.usecase.save(model);
                this.updateStockItemExpenses(newStockItem, request.getExpenses());
            }
        }
    }

    @Override
    public StockItem save(StockItem item) {
        return this.usecase.save(item);
    }

    private void setStockItemBasicDetails(StockItem model, StockItemRequest request) {
        model.setRecordedDate(LocalDate.now());
        model.setProductQuantity(request.getProductQuantity());
        model.setProductQuantitySold(0);
    }

    private void setItemSupplier(StockItem model, StockItemRequest request) {
        Optional<CompanySupplier> optionalSupplier = this.supplierUsecase.findOne(request.getSupplier().getId());
        optionalSupplier.ifPresent(model::setSupplier);
    }

    private void setItemProduct(StockItem model, StockItemRequest request) {
        Optional<Product> optionalProduct = this.productUsecase.findById(request.getProduct().getId());
        optionalProduct.ifPresent(model::setProduct);
    }

    private void setItemSettlement(StockItem model, StockItemRequest request) {
        if (!isEmpty(request.getSettlement())) {
            StockSettlement settlement = this.settlementInteractor.save(request.getSettlement());
            model.setSettlement(settlement);
        }
    }

    private void setItemPrice(StockItem model, StockItemRequest request) {
        if (!isEmpty(request) && !isEmpty(request.getPrice())) {
            StockPrice price = this.stockPriceInteractor.save(request.getPrice());
            model.setStockPrice(price);
        }
    }

    private void updateStockItemExpenses(StockItem stockItem, List<StockExpensesRequest> expensesRequest) {
        if (!isEmpty(expensesRequest)) {
            stockItem.setExpenses(saveExpensesList(expensesRequest));
            this.usecase.save(stockItem);
        }
    }

    private List<StockExpenses> saveExpensesList(List<StockExpensesRequest> expensesRequest) {
        Set<StockExpenses> expensesList = new HashSet<>();
        for (StockExpensesRequest request : expensesRequest) {
            if (request != null) {
                StockExpenses stockExpenses = expensesInteractor.saveOne(request);
                if (stockExpenses.getId() != null) {
                    expensesList.add(stockExpenses);
                }
            }
        }
        return new ArrayList<>(expensesList);
    }

    @Override
    public StockItemRequest toRequest(StockItem model) {
        return this.mapper.map(model, StockItemRequest.class);
    }

    @Override
    public StockItem toModel(StockItemRequest request) {
        return this.mapper.map(request, StockItem.class);
    }


}
