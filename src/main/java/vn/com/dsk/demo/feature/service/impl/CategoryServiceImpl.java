package vn.com.dsk.demo.feature.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.exception.EntityNotFoundException;
import vn.com.dsk.demo.feature.dto.CategoryDto;
import vn.com.dsk.demo.feature.dto.request.CategoryRequest;
import vn.com.dsk.demo.feature.model.Category;
import vn.com.dsk.demo.feature.repository.BookRepository;
import vn.com.dsk.demo.feature.repository.CategoryRepository;
import vn.com.dsk.demo.feature.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(e -> modelMapper.map(e, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public String createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.findByName(categoryRequest.getName()).isPresent()) {
            return "Category already exist in system";
        } else {
            Category category = new Category();
            category.setName(categoryRequest.getName());
            category.setDescription(categoryRequest.getDescription());
            categoryRepository.save(category);
            return "Category have been created";
        }
    }

    @Override
    public String deleteCategory(String categoryId) {
        var category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new EntityNotFoundException(Category.class.getName(), categoryId.toString()));
        if (!bookRepository.findAllByCategory(category).isEmpty()) {
            return "Cannot delete category, there are books in this category";
        }
        categoryRepository.delete(category);
        return "Category have been deleted";
    }
}
