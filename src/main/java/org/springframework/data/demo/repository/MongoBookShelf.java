package org.springframework.data.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.demo.domain.Author;
import org.springframework.data.demo.domain.Book;
import org.springframework.stereotype.Repository;

@Repository
public class MongoBookShelf implements BookShelf {

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	BookRepository bookRepository;

	@Override
	public void add(Book book) {
		save(book);
	}
	
	@Override
	public void save(Book book) {
		checkForAuthor(book);
		bookRepository.save(book);
	}
	
	@Override
	public Book find(String isbn) {
		return bookRepository.findOne(isbn);
	}
	
	@Override
	public void remove(String isbn) {
		bookRepository.delete(isbn);
	}
	
	@Override
	public List<Book> findAll() {
		 return bookRepository.findAll();
	}

	private void checkForAuthor(Book book) {
		Author existing = authorRepository.findOne(book.getAuthor().getName());
		if (existing != null) {
			book.setAuthor(existing);
		}
		else {
			authorRepository.save(book.getAuthor());
		}
	}

}
