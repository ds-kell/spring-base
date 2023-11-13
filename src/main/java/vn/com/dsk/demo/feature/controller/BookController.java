package vn.com.dsk.demo.feature.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;
import vn.com.dsk.demo.feature.dto.UpdateBookRequest;
import vn.com.dsk.demo.feature.dto.request.BookDetailRequest;
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

    @GetMapping("all-detail-by-all-book")
    public ResponseEntity<?> getAllBooDetailByAllBook() {
        return ResponseUtils.ok(bookService.getAllBooDetailByAllBook());
    }

    @GetMapping("all-detail-by-book-id/{bookId}")
    public ResponseEntity<?> getAllBooDetailByBook(@PathVariable Integer bookId) {
        return ResponseUtils.ok(bookService.getAllBookDetailByBookId(bookId));
    }

    @GetMapping("all-book-detail")
    public ResponseEntity<?> getAllBookDetails() {
        return ResponseUtils.ok(bookService.getAllBookDetails());
    }

    @GetMapping("detail/{bookDetailId}")
    public ResponseEntity<?> getBookDetailById(@PathVariable String bookDetailId) {
        return ResponseUtils.ok(bookService.getBookDetailById(bookDetailId));
    }

    @GetMapping("{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Integer bookId) {
        return ResponseUtils.ok(bookService.getBookById(bookId));
    }

    @DeleteMapping("delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer bookId) {
        return ResponseUtils.ok(bookService.deleteBook(bookId));
    }

    @PostMapping("create-book")
    public ResponseEntity<?> createBook(@RequestBody List<BookRequest> bookRequests) {
        return ResponseUtils.ok(bookService.createBook(bookRequests));
    }

    @PostMapping("create-book-detail")
    public ResponseEntity<?> createBookDetail(@RequestBody List<BookDetailRequest> bookDetailRequests) {
        return ResponseUtils.ok(bookService.createBookDetail(bookDetailRequests));
    }


    @PutMapping("update-book")
    public ResponseEntity<?> updateBook(@RequestBody List<UpdateBookRequest> updateBookRequests) {
        return ResponseUtils.ok(bookService.updateBook(updateBookRequests));
    }

}
