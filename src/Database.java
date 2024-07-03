import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection conn;

    public Database() {
        // Initialize database connection
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/library_db", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertBook(String title, String author, String publisher, int yearPublished) {
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO books (title, author, publisher, year_published) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, publisher);
            stmt.setInt(4, yearPublished);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deleteBook(int bookId) {
        PreparedStatement stmt = null;

        try {
            String sql = "DELETE FROM books WHERE book_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateBook(int bookId, String title, String author, String publisher, int yearPublished) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, year_published = ? WHERE book_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, publisher);
            stmt.setInt(4, yearPublished);
            stmt.setInt(5, bookId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Book> searchBooks(String title, String author, Integer yearPublished) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM books WHERE 1=1");
            if (title != null && !title.isEmpty()) {
                sql.append(" AND title LIKE ?");
            }
            if (author != null && !author.isEmpty()) {
                sql.append(" AND author LIKE ?");
            }
            if (yearPublished != null) {
                sql.append(" AND year_published = ?");
            }

            stmt = conn.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (title != null && !title.isEmpty()) {
                stmt.setString(paramIndex++, "%" + title + "%");
            }
            if (author != null && !author.isEmpty()) {
                stmt.setString(paramIndex++, "%" + author + "%");
            }
            if (yearPublished != null) {
                stmt.setInt(paramIndex++, yearPublished);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.bookId = rs.getInt("book_id");
                book.title = rs.getString("title");
                book.author = rs.getString("author");
                book.publisher = rs.getString("publisher");
                book.yearPublished = rs.getInt("year_published");
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return books;
    }

    public boolean insertMember(String name, String email, String phone) {
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO members (name, email, phone) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int searchMemberById(String name) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int memberId = -1; // Return -1 if not found

        try {
            String sql = "SELECT member_id FROM members WHERE name LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");

            rs = stmt.executeQuery();

            if (rs.next()) {
                memberId = rs.getInt("member_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return memberId;
    }


    public boolean addLoan(int bookId,int memberId,String loanDate) {
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO loans (book_id ,member_id , loan_date) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            stmt.setInt(2, memberId);
            stmt.setString(3, loanDate);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int searchLoan(String bookTitle, String memberName) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int loanId = -1; // Return -1 if not found

        try {
            String sql = "SELECT l.loan_id " +
                    "FROM loans l " +
                    "JOIN books b ON l.book_id = b.book_id " +
                    "JOIN members m ON l.member_id = m.member_id " +
                    "WHERE b.title LIKE ? AND m.name LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + bookTitle + "%");
            stmt.setString(2, "%" + memberName + "%");

            rs = stmt.executeQuery();

            if (rs.next()) {
                loanId = rs.getInt("loan_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return loanId;
    }


    public boolean updateLoan(int loanId, String returnDate) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE loans SET return_date = ? WHERE loan_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, returnDate);
            stmt.setInt(2, loanId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
