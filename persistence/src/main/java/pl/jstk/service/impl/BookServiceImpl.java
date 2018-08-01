package pl.jstk.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.jstk.entity.BookEntity;
import pl.jstk.mapper.BookMapper;
import pl.jstk.repository.BookRepository;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public List<BookTo> findAllBooks() {
		return BookMapper.map2To(bookRepository.findAll());
	}

	@Override
	public List<BookTo> findBooksByTitle(String title) {
		return BookMapper.map2To(bookRepository.findBookByTitle(title));
	}

	@Override
	public List<BookTo> findBooksByAuthor(String author) {
		return BookMapper.map2To(bookRepository.findBookByAuthor(author));
	}

	@Override
	@Transactional
	public BookTo saveBook(BookTo book) {
		BookEntity entity = BookMapper.map(book);
		entity = bookRepository.save(entity);
		return BookMapper.map(entity);
	}

	@Override
	@Transactional
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}

	@Override
	public BookTo findBookById(Long id) {
		return BookMapper.map(bookRepository.findBookById(id));
	}

	@Override
	public List<BookTo> filterBooks(BookTo params) {
		List<BookTo> filteredBooks = new ArrayList<BookTo>();
		filteredBooks.addAll(BookMapper.map2To(bookRepository.findAll()));
		filteredBooks = filterByAutor(filteredBooks, params.getAuthors());
		filteredBooks = filterByTitle(filteredBooks, params.getTitle());
		return filteredBooks;
	}

	// private methods
	private List<BookTo> filterByAutor(List<BookTo> books, String autor) {
		if (autor != null && autor != "") {
			return books.stream().filter(book -> book.getAuthors().equals(autor)).collect(Collectors.toList());
		} else {
			return books;
		}

	}

	private List<BookTo> filterByTitle(List<BookTo> books, String title) {
		if (title != null && title != "") {
			return books.stream().filter(book -> book.getTitle().equals(title)).collect(Collectors.toList());
		} else {
			return books;
		}
	}
}
