public class Member {
    int memberId;
    String name;
    String email;
    String phone;
    private Database database;

    public Member() {
        this.database = new Database();
    }

    public void addMember(String name, String email, String phone){

        this.name = name;
        this.email = email;
        this.phone = phone;

        boolean added = database.insertMember(name,email,phone);
        if (added) {
            System.out.println("Member added successfully into database.");
        } else {
            System.out.println("Failed to add Member into database.");
        }
    }
    
    public int searchMember(String name) {
        memberId = database.searchMemberById(name);
        if (memberId <= 0 ) {
            memberId = 0 ;
        }
        return memberId;
    }
}
