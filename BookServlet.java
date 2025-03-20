//	package pages;
//	
//	import jakarta.servlet.RequestDispatcher;
//	import jakarta.servlet.ServletException;
//	import jakarta.servlet.annotation.WebServlet;
//	import jakarta.servlet.http.HttpServlet;
//	import jakarta.servlet.http.HttpServletRequest;
//	import jakarta.servlet.http.HttpServletResponse;
//	import java.io.IOException;
//	import java.sql.SQLException;
//	import java.util.List;
//	
//	
//	@WebServlet( "/books/*")
//	public class BookServlet extends HttpServlet {
//	    /**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//		private BookDAO bookDAO;
//	
//	    public void init() {
//	        bookDAO = new BookDAO();
//	    }
//	
//	    @Override
//	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    	String action = request.getPathInfo();
//	
//	        if (action == null) {
//	            action = "/list";
//	        }
//	
//	        switch (action) {
//	            case "/new":
//	                showNewForm(request, response);
//	                break;
//	            case "/insert":
//	                insertBook(request, response);
//	                break;
//	            case "/delete":
//	                deleteBook(request, response);
//	                break;
//	            case "/edit":
//	                showEditForm(request, response);
//	                break;
//	            case "/update":
//	                updateBook(request, response);
//	                break;
//	            default:
//	                listBooks(request, response);
//	                break;
//	        }
//	    }
//	
//	    @Override
//	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        doGet(request, response);
//	    }
//	
//	    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        List<Book> books = bookDAO.selectAllBooks();
//	        request.setAttribute("books", books);
//	        RequestDispatcher dispatcher = request.getRequestDispatcher("/book-list.jsp");
//	        dispatcher.forward(request, response);
//	    }
//	
//	    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        RequestDispatcher dispatcher = request.getRequestDispatcher("/book-form.jsp");
//	        dispatcher.forward(request, response);
//	    }
//	
//	    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        int bookId = Integer.parseInt(request.getParameter("id"));
//	        Book existingBook = bookDAO.selectBook(bookId);
//	        RequestDispatcher dispatcher = request.getRequestDispatcher("/book-form.jsp");
//	        request.setAttribute("book", existingBook);
//	        dispatcher.forward(request, response);
//	    }
//	
//	    private void insertBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        String title = request.getParameter("title");
//	        String author = request.getParameter("author");
//	        String genre = request.getParameter("genre");
//	        String isbn = request.getParameter("isbn");
//	        int publicationYear = Integer.parseInt(request.getParameter("publicationYear"));
//	        String publisher = request.getParameter("publisher");
//	        String language = request.getParameter("language");
//	        int numCopies = Integer.parseInt(request.getParameter("numCopies"));
//	        String shelfLocation = request.getParameter("shelfLocation");
//	        String bookCoverUrl = request.getParameter("bookCoverUrl");
//	
//	        Book newBook = new Book(0, title, author, genre, isbn, publicationYear, publisher, language, numCopies, shelfLocation, bookCoverUrl);
//	        try {
//	            bookDAO.insertBook(newBook);
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
//	        response.sendRedirect(request.getContextPath() + "/books/list");
//	    }
//	
//	    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        int bookId = Integer.parseInt(request.getParameter("bookId"));
//	        String title = request.getParameter("title");
//	        String author = request.getParameter("author");
//	        String genre = request.getParameter("genre");
//	        String isbn = request.getParameter("isbn");
//	        int publicationYear = Integer.parseInt(request.getParameter("publicationYear"));
//	        String publisher = request.getParameter("publisher");
//	        String language = request.getParameter("language");
//	        int numCopies = Integer.parseInt(request.getParameter("numCopies"));
//	        String shelfLocation = request.getParameter("shelfLocation");
//	        String bookCoverUrl = request.getParameter("bookCoverUrl");
//	
//	        Book book = new Book(bookId, title, author, genre, isbn, publicationYear, publisher, language, numCopies, shelfLocation, bookCoverUrl);
//	        try {
//	            bookDAO.updateBook(book);
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
//	        response.sendRedirect(request.getContextPath() + "/books/list");
//	        }
//	
//	    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        int bookId = Integer.parseInt(request.getParameter("id"));
//	        try {
//	            bookDAO.deleteBook(bookId);
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
//	        response.sendRedirect(request.getContextPath() + "/books/list");
//	        }
//	}
