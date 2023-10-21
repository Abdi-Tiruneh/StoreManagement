package StoreManagement.purchaseOrderManagement.supplier;

import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.purchaseOrderManagement.supplier.dto.SupplierRegReq;
import StoreManagement.purchaseOrderManagement.supplier.dto.SupplierUpdateReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> getAllSupplier() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with ID: " + supplierId));
    }

    @Override
    public Supplier createSupplier(SupplierRegReq supplierRegReq) {
        Supplier supplier = new Supplier();
        supplier.setSupplierName(supplierRegReq.getSupplierName());
        supplier.setSupplierAddress(supplierRegReq.getSupplierAddress());

        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Long supplierId, SupplierUpdateReq updateReq) {
        Supplier existingSupplier = getSupplierById(supplierId);

        if (updateReq.getSupplierName() != null)
            existingSupplier.setSupplierName(updateReq.getSupplierName());

        if (updateReq.getSupplierAddress() != null)
            existingSupplier.setSupplierAddress(updateReq.getSupplierAddress());

        return supplierRepository.save(existingSupplier);
    }

    @Override
    public void deleteSupplier(Long supplierId) {
        Supplier existingSupplier = getSupplierById(supplierId);
        supplierRepository.delete(existingSupplier);
    }
}