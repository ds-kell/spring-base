package vn.com.dsk.demo.feature.service;

import vn.com.dsk.demo.feature.dto.CategoryDto;
import vn.com.dsk.demo.feature.dto.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    String createCategory(CategoryRequest categoryRequest);

    String deleteCategory(Integer categoryId);
}
