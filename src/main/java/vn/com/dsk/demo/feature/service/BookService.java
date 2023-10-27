package vn.com.dsk.demo.feature.service;

import vn.com.dsk.demo.feature.dto.BookDto;
import vn.com.dsk.demo.feature.dto.request.BookRequest;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    void deleteProduct(Long bookId);

    String createBook(BookRequest bookRequest);
}
