package vn.com.dsk.demo.feature.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;
import vn.com.dsk.demo.feature.dto.UpdateBookRequest;
import vn.com.dsk.demo.feature.dto.request.BookRequest;
import vn.com.dsk.demo.feature.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/private/book/")
public class BookController {

    private final BookService bookService;

    @GetMapping("all-book")
    public ResponseEntity<?> getAllBooks() {
        return ResponseUtils.ok(bookService.getAllBooks());
    }

    @GetMapping("all-book-detail")
    public ResponseEntity<?> getAllBookDetails() {
        return ResponseUtils.ok(bookService.getAllBookDetails());
    }

    @DeleteMapping("delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {

        return ResponseUtils.ok(bookService.deleteProduct(bookId));
    }

    @PostMapping("create-book")
    public ResponseEntity<?> createBook(@RequestBody List<BookRequest> bookRequests){
        return ResponseUtils.ok(bookService.createBook(bookRequests));
    }

    @PutMapping("update-book")
    public ResponseEntity<?> updateBook(@RequestBody List<UpdateBookRequest> updateBookRequests){
        return ResponseUtils.ok(bookService.updateBook(updateBookRequests));
    }
}
