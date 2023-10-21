package StoreManagement.itemManagement.category;

import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.itemManagement.category.dto.CategoryReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty())
            throw new ResourceNotFoundException("No Categories found.");

        return categories;
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
    }

    @Override
    public Category createCategory(CategoryReq categoryReq) {
        Category category = new Category();
        category.setCategoryName(categoryReq.getCategoryName());

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Integer categoryId, CategoryReq categoryReq) {
        Category category = getCategoryById(categoryId);
        category.setCategoryName(categoryReq.getCategoryName());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId))
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);

        categoryRepository.deleteById(categoryId);
    }
}
