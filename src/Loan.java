public class Loan {
    int loanId;
    int bookId;
    int memberId;
    String loanDate;
    String returnDate;
    private Database database;

    public void loanBook(String title,String name,String loanDate){

        Book book = new Book();
        bookId = book.searchBooks(title, null, null);

        Member member = new Member();
        memberId = member.searchMember(name);

        if (bookId == 0){
            System.out.println("Can't find book named " + title);
        }
        if (memberId == 0) {
            System.out.println("Cant find member named " + name);
        }
        if ( bookId > 0 && memberId > 0 ){
            this.loanDate = loanDate;
            Database database = new Database();
            boolean loanAdded = database.addLoan(bookId,memberId,loanDate);
            if (loanAdded){
                System.out.println("Book loan added successfully to " + name);
            } else {
                System.out.println("Book loan unsuccessful");
            }
        }
    }

    public void returnBook(String bookTitle, String memberName,String returnDate) {
        this.returnDate = returnDate;
        this.database = new Database();
        loanId = database.searchLoan(bookTitle, memberName);
        if (loanId != -1) {
            boolean markReturn = database.updateLoan(loanId, returnDate);
            if (markReturn){
                System.out.println("return Book successfully");
            }else {
                System.out.println("Book return unsuccessfully");
            }
        } else {
            System.out.println("Loan not found.");
        }
    }
}
