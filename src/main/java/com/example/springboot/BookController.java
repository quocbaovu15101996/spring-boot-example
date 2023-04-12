
package com.example.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> list() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable("id") Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable("id") Long id, @RequestBody Book book) {
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook == null) {
            return ResponseEntity.notFound().build();
        }
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        Book savedBook = bookRepository.save(existingBook);
        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Book existingBook = bookRepository.findById(id).orElse(null);
       if (existingBook == null) {
           return ResponseEntity.notFound().build();
       }
       bookRepository.delete(existingBook);
       return ResponseEntity.noContent().build();
   }
}