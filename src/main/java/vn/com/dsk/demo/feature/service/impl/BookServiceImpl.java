package vn.com.dsk.demo.feature.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.exception.EntityNotFoundException;
import vn.com.dsk.demo.feature.dto.BookDto;
import vn.com.dsk.demo.feature.dto.request.BookRequest;
import vn.com.dsk.demo.feature.model.Book;
import vn.com.dsk.demo.feature.model.Category;
import vn.com.dsk.demo.feature.repository.BookRepository;
import vn.com.dsk.demo.feature.repository.CategoryRepository;
import vn.com.dsk.demo.feature.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAllByIsActive(true).stream().map(e -> modelMapper.map(e, BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public String deleteProduct(Long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(Book.class.getName(), bookId.toString()));
        book.setIsActive(false);
        bookRepository.save(book);
        return "Book have been deleted";
    }

    @Override
    public String createBook(BookRequest bookRequest) {
        Book book = modelMapper.map(bookRequest, Book.class);
        var category = categoryRepository.findById(bookRequest.getCategoryId());
        category.ifPresent(book::setCategory);
        bookRepository.save(book);
        return "Create book success";
    }

    @Override
    public List<BookDto> getBooksByCategory(Long categoryId) {
        var category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new EntityNotFoundException(Category.class.getName(), categoryId.toString()));
        return bookRepository.findAllByCategory(category).stream().map(
                e -> modelMapper.map(e, BookDto.class)
        ).collect(Collectors.toList());
    }
}
