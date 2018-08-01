package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.impl.BookServiceImpl;
import pl.jstk.to.BookTo;

@Controller
@EnableGlobalMethodSecurity(securedEnabled=true)
public class BooksController {

	@Autowired
	BookServiceImpl bookServiceImpl;

	@GetMapping(value = "/books")
	public String showAllBooks(Model model) {
		model.addAttribute("bookList", bookServiceImpl.findAllBooks());
		String info = "Number of the books in database: " + bookServiceImpl.findAllBooks().size();
		model.addAttribute(ModelConstants.INFO, info);
		return ViewNames.BOOKS;
	}

	@GetMapping(value = "/books/book")
	public String showSelectedBook(@RequestParam("id") Long id, Model model) {
		model.addAttribute("book", bookServiceImpl.findBookById(id));
		String info = "Selected book id: " + id;
		model.addAttribute(ModelConstants.INFO, info);
		return ViewNames.BOOK;
	}

	@RequestMapping(value = "/books/add", method = RequestMethod.GET)
	public String addBookWindow(Model model) {
		model.addAttribute("newBook", new BookTo());
		return ViewNames.ADDBOOK;
	}

	@RequestMapping(value = "/greeting", method = RequestMethod.POST)
	public String addNewBook(@ModelAttribute("newBook") BookTo newBook, Model model) {
		newBook = bookServiceImpl.saveBook(newBook);
		model.addAttribute("bookList", bookServiceImpl.findAllBooks());
		return ViewNames.BOOKS;
	}

	@RequestMapping(value = "/books/remove", method = RequestMethod.GET)
	@Secured({ "ROLE_ADMIN" })
	public String deleteSelectedBook(@RequestParam("id") Long id, Model model) {
		bookServiceImpl.deleteBook(id);
		model.addAttribute("bookList", bookServiceImpl.findAllBooks());
		String info = "Number of the books in database: " + bookServiceImpl.findAllBooks().size();
		model.addAttribute(ModelConstants.INFO, info);
		String remove = "You've been delete book with id: " + id;
		model.addAttribute("remove_info", remove);
		return ViewNames.BOOKS;
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String returnErrorForUser(Model model) {
		String message = "Your profile dosn't have a permission to use this option";
		model.addAttribute("message", message);
		return ViewNames.USER_ERROR;
	}

	@RequestMapping(value = "/books/search", method = RequestMethod.GET)
	public String searchBooksWindow(Model model) {
		model.addAttribute("filterParams", new BookTo());
		return ViewNames.SEARCH;
	}

	@RequestMapping(value = "/books/filter", method = RequestMethod.POST)
	public String filterBooks(@ModelAttribute("filterParams") BookTo params, Model model) {
		model.addAttribute("bookList", bookServiceImpl.filterBooks(params));
		String info = "Found: " + bookServiceImpl.filterBooks(params).size() + " books in database";
		model.addAttribute(ModelConstants.INFO, info);
		return ViewNames.BOOKS;
	}

}
