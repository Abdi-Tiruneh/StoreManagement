package StoreManagement.itemManagement.category;

import StoreManagement.itemManagement.category.dto.CategoryReq;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Integer categoryId);

    Category createCategory(CategoryReq categoryReq);

    Category updateCategory(Integer categoryId, CategoryReq categoryReq);

    void deleteCategory(Integer categoryId);
}
