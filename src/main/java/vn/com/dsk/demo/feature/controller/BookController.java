package vn.com.dsk.demo.feature.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;
import vn.com.dsk.demo.feature.dto.request.BookRequest;
import vn.com.dsk.demo.feature.service.BookService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/private/book/")
public class BookController {

    private final BookService bookService;

    @GetMapping("books")
    public ResponseEntity<?> getAllBooks() {
        return ResponseUtils.ok(bookService.getAllBooks());
    }

    @DeleteMapping("delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        bookService.deleteProduct(bookId);
        return ResponseUtils.ok("Created");
    }

    @PostMapping("create-book")
    public ResponseEntity<?> createBook(@RequestBody BookRequest bookRequest){
        return ResponseUtils.ok(bookService.createBook(bookRequest));
    }
}
