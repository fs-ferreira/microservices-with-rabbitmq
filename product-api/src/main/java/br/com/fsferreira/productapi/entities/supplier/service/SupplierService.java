package br.com.fsferreira.productapi.entities.supplier.service;

import br.com.fsferreira.productapi.config.exception.GenericNotFoundException;
import br.com.fsferreira.productapi.config.exception.GenericUserException;
import br.com.fsferreira.productapi.config.validators.TypeValidator;
import br.com.fsferreira.productapi.entities.supplier.dto.SupplierRequestInput;
import br.com.fsferreira.productapi.entities.supplier.dto.SupplierResponseOutput;
import br.com.fsferreira.productapi.entities.supplier.model.Supplier;
import br.com.fsferreira.productapi.entities.supplier.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository repository;

    public List<SupplierResponseOutput> findAll() {
        return repository.findAll().stream().map(SupplierResponseOutput::of).toList();
    }

    public SupplierResponseOutput findById(String id) {
        var uuid = TypeValidator.validateUUID(id, "Id is not valid!");
        var supplier = repository.findById(uuid)
                .orElseThrow(() ->new GenericNotFoundException("Supplier not found!"));
        return SupplierResponseOutput.of(supplier);
    }

    @Transactional
    public SupplierResponseOutput save(SupplierRequestInput input){
        validateSupplierName(input);
        var category = repository.save(Supplier.of(input));
        return SupplierResponseOutput.of(category);
    }

    private void validateSupplierName(SupplierRequestInput input) {
        if(input.getName() == null || input.getName().isEmpty()){
            throw new GenericUserException("Supplier's name not informed!");
        }
    }
}
