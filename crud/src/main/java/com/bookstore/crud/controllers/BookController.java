package com.bookstore.crud.controllers;

import com.bookstore.crud.entities.Book;
import com.bookstore.crud.services.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
@CrossOrigin("http://localhost:4200/")
public class BookController {
    @Autowired
    BookServiceImpl bookService;
    @Autowired
    private BookServiceImpl bookServiceImpl;

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestPart("book") Book book, @RequestPart("file")MultipartFile file) {
        System.out.println("test miguel"+file);
        try {
            Book savedBook = bookServiceImpl.saveBook(book, file);
            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping ("/{id}/image")
    public ResponseEntity<Book> updateImage(@PathVariable Long id, @RequestPart MultipartFile file) throws IOException{
        Optional<Book> book = bookServiceImpl.getBookById(id);
        if(book.isPresent()){
            Book updateBook = bookServiceImpl.updateBookImage(file,book.get());
            return new ResponseEntity<>(updateBook, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        try{
            Book updatedBook = bookService.updateBook(book);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(bookServiceImpl.getBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> book = bookServiceImpl.getBookById(id);
       return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
               new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) throws IOException{
        Optional<Book> book = bookServiceImpl.getBookById(id);
        if(book.isPresent()){
            bookServiceImpl.deleteBook(book.get());
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
