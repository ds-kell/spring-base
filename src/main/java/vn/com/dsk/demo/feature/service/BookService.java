package vn.com.dsk.demo.feature.service;

import vn.com.dsk.demo.feature.dto.BookDetailDto;
import vn.com.dsk.demo.feature.dto.BookDto;
import vn.com.dsk.demo.feature.dto.UpdateBookRequest;
import vn.com.dsk.demo.feature.dto.request.BookRequest;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    List<BookDetailDto> getAllBookDetails();

    BookDetailDto getBookDetailById(Long bookId);

    List<BookDetailDto> getBookDetailByBranch(Long branchId);

    String deleteProduct(Long bookId);

    String createBook(List<BookRequest> bookRequests);

    List<BookDto> getBooksByCategory(Long categoryId);

    String updateBook(List<UpdateBookRequest> updateBookRequests);
}
