import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int bookId;
        String title;
        String author;
        String publisher;
        int yearOfPublished;
        String memberName;
        String memberEmail;
        String memberPhoneNumber;
        String loanDate;
        String returnDate;


        Book book = new Book();
        Member member = new Member();
        Loan loan = new Loan();

        System.out.println("Welcome to library management system created by Kavinda Lakshan Jayarathna");
        System.out.println("Enter 1 for Add Book");
        System.out.println("Enter 2 for Update Book");
        System.out.println("Enter 3 for Delete Book");
        System.out.println("Enter 4 for Search Book");
        System.out.println("Enter 5 for Add Member");
        System.out.println("Enter 6 for Loan a Book");
        System.out.println("Enter 7 for Return a Book");
        System.out.print("Enter Number : ");

        Scanner object = new Scanner(System.in);
        int userInput = object.nextInt();

        switch (userInput){
            case 1:
                object.nextLine();
                System.out.print("Enter Book Title : ");
                title = object.nextLine();
                System.out.print("Enter Author : ");
                author = object.nextLine();
                System.out.print("Enter Publisher : ");
                publisher = object.nextLine();
                System.out.print("Enter Published Year : ");
                yearOfPublished = object.nextInt();
                book.addBook(title,author,publisher,yearOfPublished);
                break;
            case 2:
                System.out.print("Enter Book ID : ");
                bookId = object.nextInt();
                object.nextLine();
                System.out.print("Enter Book Title : ");
                title = object.nextLine();
                System.out.print("Enter Author : ");
                author = object.nextLine();
                System.out.print("Enter Publisher : ");
                publisher = object.nextLine();
                System.out.print("Enter Published Year : ");
                yearOfPublished = object.nextInt();
                book.updateBook(bookId,title,author,publisher,yearOfPublished);
                break;
            case 3:
                System.out.print("Enter Book ID for Delete : ");
                bookId = object.nextInt();
                book.deleteBook(bookId);
                break;
            case 4:
                object.nextLine();
                System.out.print("Enter t for search by Title, Enter a for search by Author and Enter y for search by Published Year : ");
                String searchInput = object.nextLine();
                if (searchInput.equals("t")){
                    System.out.print("Enter Book Title : ");
                    title = object.nextLine();
                    book.searchBooks(title,null,null);
                }if (searchInput.equals("a")){
                    System.out.print("Enter Book Author : ");
                    author = object.nextLine();
                    book.searchBooks(null,author,null);
                }if (searchInput.equals("y")){
                    System.out.print("Enter Book Published Year : ");
                    yearOfPublished = object.nextInt();
                    book.searchBooks(null,null,yearOfPublished);
                }
                break;
            case 5:
                object.nextLine();
                System.out.print("Enter Name of Member : ");
                memberName = object.nextLine();
                System.out.print("Enter Email of Member : ");
                memberEmail = object.nextLine();
                System.out.print("Enter Phone Number of Member : ");
                memberPhoneNumber = object.nextLine();
                member.addMember(memberName,memberEmail,memberPhoneNumber);
                break;
            case 6:
                object.nextLine();
                System.out.print("Enter Book Title : ");
                title = object.nextLine();
                System.out.print("Enter Member Name : ");
                memberName = object.nextLine();
                System.out.print("Enter Date of Loan : ");
                loanDate = object.nextLine();
                loan.loanBook(title,memberName,loanDate);
                break;
            case 7:
                object.nextLine();
                System.out.print("Enter Book Title : ");
                title = object.nextLine();
                System.out.print("Enter Member Name : ");
                memberName = object.nextLine();
                System.out.print("Enter Date of Return : ");
                returnDate = object.nextLine();
                loan.returnBook(title,memberName,returnDate);
                break;
            default:
                System.out.println("Invalid Input");
        }
    }
}