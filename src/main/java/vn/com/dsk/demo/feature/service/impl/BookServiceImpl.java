package vn.com.dsk.demo.feature.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.exception.EntityNotFoundException;
import vn.com.dsk.demo.feature.dto.BookDetailDto;
import vn.com.dsk.demo.feature.dto.BookDto;
import vn.com.dsk.demo.feature.dto.CategoryDto;
import vn.com.dsk.demo.feature.dto.UpdateBookRequest;
import vn.com.dsk.demo.feature.dto.request.BookDetailRequest;
import vn.com.dsk.demo.feature.dto.request.BookRequest;
import vn.com.dsk.demo.feature.model.Book;
import vn.com.dsk.demo.feature.model.BookDetail;
import vn.com.dsk.demo.feature.model.Branch;
import vn.com.dsk.demo.feature.model.Category;
import vn.com.dsk.demo.feature.repository.BookDetailRepository;
import vn.com.dsk.demo.feature.repository.BookRepository;
import vn.com.dsk.demo.feature.repository.BranchRepository;
import vn.com.dsk.demo.feature.repository.CategoryRepository;
import vn.com.dsk.demo.feature.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookDetailRepository bookDetailRepository;

    private final BranchRepository branchRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;



    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAllByIsActive(true).stream().map(e -> modelMapper.map(e, BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public String deleteBook(Integer bookId) {
        var book = bookRepository.findById(bookId).orElseThrow();
        book.setIsActive(false);
        bookRepository.save(book);
        return "Book have been deleted";
    }

    @Override
    @Transactional
    public String createBook(List<BookRequest> bookRequests) {
        List<Book> books = bookRequests.stream().map(
                bookRequest -> {
                    var book = modelMapper.map(bookRequest, Book.class);
                    var category = categoryRepository.findById(bookRequest.getCategoryId()).orElseThrow();
                    book.setCategory(category);
                    return book;
                }
        ).collect(Collectors.toList());
        bookRepository.saveAll(books);
        bookRepository.flush();
        return "Create book success";
    }

    @Override
    public List<BookDto> getBooksByCategory(Integer categoryId) {
        var category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new EntityNotFoundException(Category.class.getName(), categoryId.toString()));
        return bookRepository.findAllByCategory(category).stream().map(
                e -> modelMapper.map(e, BookDto.class)
        ).collect(Collectors.toList());
    }

    @Override
    public String updateBook(List<UpdateBookRequest> updateBookRequests) {
        List<Book> listBooks = updateBookRequests.stream().map(
                updateBookRequest -> {
                    var book = modelMapper.map(updateBookRequest, Book.class);
                    var category = categoryRepository.findById(updateBookRequest.getCategoryId())
                            .orElseThrow(() -> new EntityNotFoundException(Category.class.getName(), updateBookRequest.getCategoryId().toString()));
                    book.setCategory(category);
                    return book;
                }
        ).toList();
        bookRepository.saveAll(listBooks);
        return "Books have been updated";
    }

    @Override
    public List<BookDetailDto> getAllBookDetails() {
        return bookDetailRepository.findAll().stream().map(
                bookDetail -> {
                    if (bookDetail.getBook().getIsActive()) {
                        var bookDetailDto = modelMapper.map(bookDetail, BookDetailDto.class);
                        var bookDto = modelMapper.map(bookDetail.getBook(), BookDto.class);
                        bookDto.setCategoryDto(modelMapper.map(bookDetail.getBook().getCategory(), CategoryDto.class));
                        bookDetailDto.setBookDto(bookDto);
                        return bookDetailDto;
                    }
                    return null;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public BookDetailDto getBookDetailById(String bookId) {
        BookDetail bookDetail = bookDetailRepository.findBookDetailById(bookId);
        BookDetailDto bookDetailDto = modelMapper.map(bookDetail, BookDetailDto.class);
        if (bookDetail.getBook().getIsActive()) {
            var bookDto = modelMapper.map(bookDetail.getBook(), BookDto.class);
            bookDto.setCategoryDto(modelMapper.map(bookDetail.getBook().getCategory(), CategoryDto.class));
            bookDetailDto.setBookDto(bookDto);
        }
        return bookDetailDto;
    }

    @Override
    public BookDto getBookById(Integer bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(Book.class.getName(), bookId.toString()));
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        bookDto.setCategoryDto(modelMapper.map(book.getCategory(), CategoryDto.class));
        return bookDto;
    }

    @Override
    public List<BookDetailDto> getBookDetailByBranch(Integer branchId) {
        return null;
    }

    @Override
    public String createBookDetail(List<BookDetailRequest> bookDetailRequests) {
        List<BookDetail> bookDetails = new ArrayList<>();
        bookDetailRequests.forEach(
                bookDetailRequest -> {
                    var bookDetail = bookDetailRepository.findBookDetailByBookIdAndBranchId(bookDetailRequest.getBookId(), bookDetailRequest.getBranchId());
                    if (bookDetail == null) {
                        bookDetail = new BookDetail();
                        bookDetail.setQuantity(bookDetailRequest.getQuantity());
                        bookDetail.setBook(bookRepository.findById(bookDetailRequest.getBookId()).orElseThrow(() -> new EntityNotFoundException(Book.class.getName(), bookDetailRequest.getBookId().toString())));
                        bookDetail.setBranch(branchRepository.findById(bookDetailRequest.getBranchId()).orElseThrow(() -> new EntityNotFoundException(Branch.class.getName(), bookDetailRequest.getBranchId().toString())));
                    } else {
                        bookDetail.setQuantity(bookDetail.getQuantity() + bookDetailRequest.getQuantity());
                    }
                    bookDetails.add(bookDetail);
                }
        );
        bookDetailRepository.saveAll(bookDetails);
        return "Book detail have been created";
    }
}
