package vn.com.dsk.demo.feature.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;
import vn.com.dsk.demo.feature.dto.request.CategoryRequest;
import vn.com.dsk.demo.feature.service.CategoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("private/category/all-category")
    public ResponseEntity<?> getAllCategories() {
        return ResponseUtils.ok(categoryService.getAllCategories());
    }

    @PostMapping("admin/category/create-category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseUtils.ok(categoryService.createCategory(categoryRequest));
    }

    @DeleteMapping("admin/category/delete-category")
    public ResponseEntity<?> deleteCategory(@RequestBody Long categoryId) {
        return ResponseUtils.ok(categoryService.deleteCategory(categoryId));
    }
}
