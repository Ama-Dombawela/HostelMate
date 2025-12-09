import java.util.Scanner;
import java.time.LocalDate;;

class HostelMate {

    static Scanner input = new Scanner(System.in);
    static final int MaxRows = 100;
    static int NoRooms = 0;
    static String[][] rooms = new String[MaxRows][6];

    static final int MaxStudents = 200;
    static int NoStudents = 0;
    static String[][] students = new String[MaxStudents][5];

    // Allocate bed
    static final int MaxAllocation = 300;
    static int NoAllocations = 0;
    static String[][] allocations = new String[MaxAllocation][5];

    static String[][] occupancy = new String[MaxRows][20];

    public static void main(String[] args) {
        Login();

        int choice;
        do {
            choice = HomePage();

            switch (choice) {
                case 1:
                    ManageRooms();
                    break;
                case 2:
                    ManageStudents();
                    break;
                case 3:
                    AllocateBed();
                    break;
                case 4:
                    VacateBed();
                    break;
                case 5:
                    Transfers();
                    break;
                case 6:
                    ViewReports();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid choice Please enter a coice between (1-7).");

            }
        } while (choice != 7);

    }

    public static void Login() {
        String Username;
        String Password;

        System.out.println("=== HostelMate Login ===");
        while (true) {

            System.out.print("Username: ");
            Username = input.nextLine();
            boolean UserNameCheck = true;

            if (Username.isEmpty() || Username.length() < 3) {
                UserNameCheck = false;
                System.out.println("Invalid Username.Please enter the correct Username.");
            } else {
                for (int i = 0; i < Username.length(); i++) {
                    char n = Username.charAt(i);
                    if (!Character.isLetter(n) && n != ' ') {
                        System.out.println("Invalid Username.Numbers cannot be included");
                        UserNameCheck = false;
                        break;
                    }
                }
            }
            if (UserNameCheck)
                break;
        }

        while (true) {
            System.out.print("Password: ");
            Password = input.nextLine();

            boolean passwordCheck = true;
            boolean hasLetter = false;
            boolean hasNumber = false;

            if (Password.isEmpty() || Password.length() < 4) {
                passwordCheck = false;
                System.out.println("Please enter the correct password");
            } else {
                for (int i = 0; i < Password.length(); i++) {
                    char a = Password.charAt(i);
                    if (Character.isLetter(a))
                        hasLetter = true;
                    if (Character.isDigit(a))
                        hasNumber = true;

                    if (hasLetter && hasNumber)
                        break;
                }
                if (!hasLetter || !hasNumber) {
                    passwordCheck = false;
                    System.out.println("Invalid password.Please enter the correct password");

                }
            }

            if (passwordCheck)
                break;
        }

        System.out.println("Login successful. Welcome," + Username);
    }

    public static int HomePage() {

        System.out.println("""

                === HostelMate ===

                1) Manage Rooms
                2) Manage Students
                3) Allocate Bed
                4) Vacate Bed
                5) Transfers
                6) View Reports
                7) Exit
                         """);

        System.out.print("Choose: ");
        int choice = input.nextInt();

        return choice;
    }

    public static void ManageRooms() {

        System.out.println("""

                === ManageRooms ===

                1) Add Room
                2) Update Room
                3) Delete Room
                4) Search Room
                5) View All Rooms
                """);

        System.out.print("Enter your choice(1-5): ");
        int manageChoice = input.nextInt();
        input.nextLine();

        if (manageChoice == 1) {
            System.out.println("\nAdd Room\n");

            System.out.print("Room ID : ");
            String RoomId = input.nextLine();

            for (int i = 0; i < NoRooms; i++) {
                if (rooms[i][0].equals(RoomId)) {
                    System.out.println("Room Id already exists");
                    return;
                }
            }

            rooms[NoRooms][0] = RoomId;

            System.out.print("Floor : ");
            int Floor = Integer.parseInt(input.nextLine());
            rooms[NoRooms][1] = Integer.toString(Floor);

            System.out.print("Room No : ");
            int RoomNo = Integer.parseInt(input.nextLine());
            rooms[NoRooms][2] = Integer.toString(RoomNo);

            System.out.print("Capacity : ");
            int Capacity = Integer.parseInt(input.nextLine());
            rooms[NoRooms][3] = Integer.toString(Capacity);

            System.out.print("Fee/Day(LKR) : ");
            double fee = Double.parseDouble(input.nextLine());
            rooms[NoRooms][4] = Double.toString(fee);

            rooms[NoRooms][5] = Integer.toString(Capacity);

            // Initialize occupancy grid for this room (all the beds empty initialy)
            for (int i = 0; i < Capacity; i++) {
                occupancy[NoRooms][i] = "EMPTY";
            }

            System.out.println("Room added. Available beds: " + rooms[NoRooms][5]);

            NoRooms++;

        } else if (manageChoice == 2) {

            System.out.println("\nUpdate Room\n");

            System.out.print("Room ID: ");
            String RoomId = input.nextLine();

            int index = -1;
            for (int i = 0; i < NoRooms; i++) {
                if (rooms[i][0].equals(RoomId)) {
                    index = i;
                    break;
                }

            }

            if (index == -1) {
                System.out.println("Room not Found.Please enter a correct RoomId");
                return;
            }

            int CurrentOcup = Integer.parseInt(rooms[index][3]) - Integer.parseInt(rooms[index][5]);

            System.out.print("New Capacity (or - to skip): ");
            String CapInput = input.nextLine();

            if (!CapInput.equals("-")) {
                int NewCap = Integer.parseInt(CapInput);
                if (NewCap < CurrentOcup) {
                    System.out.println("Rejected");

                } else {
                    rooms[index][3] = Integer.toString(NewCap);
                    int NewAvailable = NewCap - CurrentOcup;
                    rooms[index][5] = Integer.toString(NewAvailable);
                }
            }

            System.out.print("New Fee/Day (or - to skip): ");
            String FeeInput = input.nextLine();

            if (!FeeInput.equals("-")) {
                double NewFee = Double.parseDouble(FeeInput);
                rooms[index][4] = Double.toString(NewFee);
            }

            System.out.println("Updated: " + rooms[index][0] + " | Floor=" + rooms[index][1] + " | RoomNo="
                    + rooms[index][2] + " | Capacity=" + rooms[index][3] + " | Fee/Day=" + rooms[index][4] + " | Avail="
                    + rooms[index][5]);

        } else if (manageChoice == 3) {

            System.out.println("\nDelete Room\n");

            System.out.print("Room ID: ");
            String RoomId = input.nextLine();

            int index = -1;
            for (int i = 0; i < NoRooms; i++) {
                if (rooms[i][0].equals(RoomId)) {
                    index = i;
                    break;
                }

            }

            if (index == -1) {
                System.out.println("Room not Found.Please enter a correct RoomId");
                return;
            }

            int FullCapacity = Integer.parseInt(rooms[index][3]);
            int available = Integer.parseInt(rooms[index][5]);
            if (available != FullCapacity) {
                System.out.println("Cannot Delete! There are ACTIVE ALLOCATIONS for this room");
                return;
            } else {
                for (int i = index; i < NoRooms - 1; i++) {
                    rooms[i] = rooms[i + 1];
                }
                rooms[NoRooms - 1] = new String[6];

            }
            NoRooms--;
            System.out.println("Deleted successfully.");

        } else if (manageChoice == 4) {

            System.out.println("\nSearch Room\n");

            System.out.print("Room ID: ");
            String RoomId = input.nextLine();

            int index = -1;
            for (int i = 0; i < NoRooms; i++) {
                if (rooms[i][0].equals(RoomId)) {
                    index = i;
                    System.out.println("Found\n");

                    System.out.println("ID Floor No Cap Avail Fee/Day\n");
                    System.out.println("--------------------------------------\n");

                    for (int x = 0; x < rooms[index].length; x++) {
                        System.out.print(rooms[index][x] + "");
                    }
                    System.out.println();
                    break;
                }

            }

            if (index == -1) {
                System.out.println("Room not Found.Please enter a correct RoomId");
                return;
            }

        } else if (manageChoice == 5) {
            System.out.println("\nAll Rooms\n");
            System.out.println("ID\tFloor\t No\t Cap\t Avail\t Fee/Day\n");
            System.out.println("---------------------------------------------\n");

            for (int i = 0; i < NoRooms; i++) {
                System.out.print(rooms[i][0] + "\t" + rooms[i][1] + "\t" + rooms[i][2] + "\t" + rooms[i][3] + "\t"
                        + rooms[i][4] + "\t" + rooms[i][5] + "\n");
            }

        } else {
            System.out.println("Invalid input.Please enter a choice(1-5)");

        }

    }

    public static void ManageStudents() {

        String studentId;
        int index;
        String contactNo;
        String emailAddress;
        String status = "ACTIVE";

        System.out.println("""
                === ManageStudents ===

                1) Add Student
                2) Update Student
                3) Delete Student
                4) Search Student
                5) View All Student
                """);

        System.out.print("Enter your choice(1-5): ");
        int manageChoice = input.nextInt();
        input.nextLine();

        switch (manageChoice) {
            case 1:
                System.out.println("\nAdd Student\n");

                System.out.print("Student ID : ");
                studentId = input.next();
                input.nextLine();

                // Validating studentId
                // Each row represent a student record
                // Condition checks until the NoStudents rather than the MaxStudents
                for (int i = 0; i < NoStudents; i++) {
                    // In inner arrays, 0th element represents the studentId
                    if (students[i][0].equals(studentId)) {
                        System.out.println("Student Id already exists");
                        return;
                    }
                }

                // Getting user input and validating

                System.out.print("Name       : ");
                String studentName = input.nextLine();

                System.out.print("Contact    : ");
                contactNo = input.next();
                input.nextLine();
                // If the number is invalid then redirect to the main menu
                if (invalidContact(contactNo)) {
                    System.out.println();
                    return;
                }

                System.out.print("Email      : ");
                emailAddress = input.nextLine();
                if (invalidEmail(emailAddress)) {
                    return;

                }

                System.out.println("Status     : " + status);

                // Adding information to the array
                students[NoStudents][0] = studentId;
                students[NoStudents][1] = studentName;
                students[NoStudents][2] = contactNo;
                students[NoStudents][3] = emailAddress;
                students[NoStudents][4] = status;

                System.out.println("Student Added.");
                NoStudents++;

                System.out.println();
                break;

            case 2:

                System.out.println("\nUpdate Student\n");

                System.out.print("Student ID to update      : ");
                studentId = input.nextLine();

                index = -1;
                for (int i = 0; i < NoStudents; i++) {
                    if (students[i][0].equals(studentId)) {
                        index = i;
                        break;
                    }

                }

                if (index == -1) {
                    System.out.println("Student not Found.");
                    return;
                }

                System.out.print("New Contact (or - to skip): ");
                contactNo = input.next();
                input.nextLine();
                if (!contactNo.equals("-")) {
                    // If the number is invalid then redirect to the main menu
                    if (invalidContact(contactNo)) {
                        System.out.println();
                        return;
                    } else {
                        students[index][2] = contactNo;
                    }
                }

                System.out.print("New Email (or - to skip)  : ");
                emailAddress = input.nextLine();
                if (!emailAddress.equals("-")) {
                    if (invalidEmail(emailAddress)) {
                        return;
                    }
                } else {
                    students[index][3] = emailAddress;
                }

                System.out.println("Updated: " +
                        students[index][0] + " | " +
                        students[index][1] + " | " +
                        students[index][2] + " | " +
                        students[index][3] + " | " +
                        students[index][4] + " | ");

                break;

            case 3:

                System.out.println("\nDelete Student\n");

                System.out.print("Student ID: ");
                studentId = input.nextLine();

                index = -1;
                for (int i = 0; i < NoStudents; i++) {
                    if (students[i][0].equals(studentId)) {
                        index = i;
                        break;
                    }

                }

                if (index == -1) {
                    System.out.println("Student not Found.");
                    return;
                }

                // If the student is active then cannot remove him
                if (students[index][4].equals("ACTIVE")) {
                    System.out.println("Cannot Delete! Student have ACTIVE ALLOCATIONS");
                    return;
                } else {
                    // Shifting records to the left. Current I is equal to I+1(next student). This
                    // goes until no of NoStudent - 1
                    // length - 1 because there's nothing to replace last one with
                    for (int i = index; i < NoStudents - 1; i++) {
                        students[i] = students[i + 1];
                    }

                    // Last room is now empty and a new array
                    students[NoStudents - 1] = new String[5];

                }
                NoStudents--;
                System.out.println("Deleted successfully.");

                break;

            case 4:

                System.out.println("\nSearch Student\n");

                System.out.print("Student ID: ");
                studentId = input.nextLine();

                index = -1;
                for (int i = 0; i < NoStudents; i++) {
                    if (students[i][0].equals(studentId)) {
                        index = i;
                        System.out.println("Found\n");

                        System.out.printf("%-10s %-10s %-10s %-20s %-10s\n", "ID", "Name", "Contact", "Email",
                                "Status");
                        System.out.println("----------------------------------------------------------------\n");

                        // 0 - 5, indexes of the inner array elements
                        System.out.printf(
                                "%-10s %-10s %-10s %-20s %-10s\n",
                                students[index][0],
                                students[index][1],
                                students[index][2],
                                students[index][3],
                                students[index][4]);

                        System.out.println();
                        break;
                    }

                }

                if (index == -1) {
                    System.out.println("Student not Found.");
                    return;
                }

                break;

            case 5:

                System.out.println("All Students");
                System.out.printf("%-10s %-10s %-10s %-20s %-10s\n", "ID", "Name", "Contact", "Email", "Status");
                System.out.println("----------------------------------------------------------------\n");

                for (int i = 0; i < NoStudents; i++) {
                    System.out.printf(
                            "%-10s %-10s %-10s %-20s %-10s\n",
                            students[i][0],
                            students[i][1],
                            students[i][2],
                            students[i][3],
                            students[i][4]);
                }

                System.out.println();
                break;

            default:
                System.out.println("Invalid input. Please enter a choice(1-5)");

        }

    }

    public static void AllocateBed() {

        System.out.println("\nAllocate Bed\n");

        // input student id
        System.out.print("\tStudent ID : ");
        String studentId = input.next();
        input.nextLine();

        // checking student exist
        int index = -1;
        for (int i = 0; i < NoStudents; i++) {
            if (students[i][0].equals(studentId)) {
                index = i;
                break;
            }

        }

        if (index == -1) {
            System.out.println("Student not Found.");
            return;
        }

        // checking the student is ACTIVE
        if (!students[index][4].equals("ACTIVE")) {
            System.out.println("Student is not ACTIVE");
            return;
        }

        // input room Id
        System.out.print("\n\tRoom ID : ");
        String roomId = input.next();
        input.nextLine();

        // checking if the room exists
        int roomindex = -1;
        for (int i = 0; i < NoRooms; i++) {
            if (rooms[i][0].equals(roomId)) {
                roomindex = i;
                break;
            }

        }

        if (roomindex == -1) {
            System.out.println("Room not Found.");
            return;
        }

        // Checking the availablity of beds if its greather than zero
        int availableBeds = Integer.parseInt(rooms[roomindex][5]);
        if (availableBeds <= 0) {
            System.out.println("No available beds in this room");
            return;
        }

        // Checking if studen is already allocated
        for (int i = 0; i < NoAllocations; i++) {
            if (allocations[i][0].equals(studentId)) {
                System.out.println("Student already has an active allocation");
                return;
            }
        }

        // Input due date
        System.out.print("\n\tDue Date(YYYY -MM-DD) : ");
        String DueDate = input.next();
        input.nextLine();

        LocalDate checkDate = LocalDate.now(); // using current data as check in date
        LocalDate due = LocalDate.parse(DueDate);

        // Validating by checking if DueDate >= checkInDate
        if (due.isBefore(checkDate)) {
            System.out.println("Invalid due date.Please enter again");
            return;
        }

        // choosing the lowest available bedIndex within the room.
        int bedIndex = -1;
        int roomCapacity = Integer.parseInt(rooms[roomindex][3]);

        // iterating through the rooms and selecting the first emty amoung them
        for (int i = 0; i < roomCapacity; i++) {
            if (occupancy[roomindex][i].equals("EMPTY")) {
                bedIndex = i;
                break;
            }
        }

        if (bedIndex == -1) {
            System.out.println("No empty bed found");
            return;
        }

        // Recording the allocations
        allocations[NoAllocations][0] = studentId;
        allocations[NoAllocations][1] = roomId;
        allocations[NoAllocations][2] = Integer.toString(bedIndex);
        allocations[NoAllocations][3] = checkDate.toString();
        allocations[NoAllocations][4] = due.toString();
        NoAllocations++;

        // updating Occupancy
        occupancy[roomindex][bedIndex] = studentId;

        // updating available beds
        rooms[roomindex][5] = Integer.toString(Integer.parseInt(rooms[roomindex][5]) - 1);

        // displaying the allocated room and the available beds
        System.out.println("\n\tAllocated: " + studentId + " -> Room " + roomId + "Bed " + bedIndex);
        System.out.println("\nAvailable beds: (" + roomId + "): " + rooms[roomindex][5]);
    }

    public static void VacateBed() {

        System.out.println("\nVacate Bed\n");

        // input student id
        System.out.print("\tStudent ID : ");
        String studentId = input.next();
        input.nextLine();

        // input room Id
        System.out.print("\tRoom ID : ");
        String roomId = input.next();
        input.nextLine();

        int roomindex = -1;
        for (int i = 0; i < NoRooms; i++) {
            if (rooms[i][0].equals(roomId)) {
                roomindex = i;
                break;
            }

        }

        if (roomindex == -1) {
            System.out.println("Room not Found.Please enter a correct RoomId");
            return;
        }
        // Check if this student is allocated to this room
        int allocateIndex = -1; // will store the row number if found

        for (int x = 0; x < NoAllocations; x++) {
            if (allocations[x][0].equals(studentId) && allocations[x][1].equals(roomId)) {
                allocateIndex = x;
                break;
            }
        }

        // if not found this ,cannot vacate and goes to the main menue
        if (allocateIndex == -1) {
            System.out.println("No ACTIVE allocations found for this student in this room");
            return;
        }

        String StrDueDate = allocations[allocateIndex][4];
        int Overduedays = OverdueDaysCalculate(StrDueDate);// calling the Overduedays calculation method

        // Calculating the fine
        double OneDayFee = Double.parseDouble(rooms[roomindex][4]);
        double fine = Overduedays * OneDayFee;

        // Remove allocation row, set occupancy cell to "EMPTY", increase availableBeds
        // by 1.
        int bedIndex = Integer.parseInt(allocations[allocateIndex][2]);
        occupancy[roomindex][bedIndex] = "EMPTY";

        for (int i = allocateIndex; i < NoAllocations - 1; i++) {
            allocations[i] = allocations[i + 1];
        }
        allocations[NoAllocations - 1] = new String[5];
        NoAllocations--;

        int availableBeds = Integer.parseInt(rooms[roomindex][5]) + 1;
        rooms[roomindex][5] = Integer.toString(availableBeds);

        // Printing the result
        System.out.println("\nOverdue days: " + Overduedays + "| Fee/Day: " + OneDayFee + "| Fine: " + fine);
        System.out.println("\nCheckout completed. Bed freed. Available beds (" + roomId + ") : " + availableBeds);

    }

    public static void Transfers() {

    }

    public static void ViewReports() {

    }

    // Supporting methods
    public static boolean invalidContact(String contactNo) {
        boolean validNumber = true;

        // Validating contact information
        // Check whether the length is 10
        if (contactNo.length() == 10) {
            // Check whether the number has all digits
            for (int i = 0; i < contactNo.length(); i++) {
                char digit = contactNo.charAt(i);
                if (!Character.isDigit(digit)) {
                    System.out.println("Contact number should in digits!");
                    validNumber = false;
                    break;
                }

            }

        } else {
            System.out.println("Contact number should contain 10 digits" +
                    "(eg: 07xxxxxxxx)");
            validNumber = false;
        }

        // If the number is invalid then return true
        if (!validNumber) {
            System.out.println();
            return true;
        }
        return false;

    }

    public static boolean invalidEmail(String emailAddress) {
        boolean hasAt = false;
        boolean hasDot = false;

        // Validating the email to check whether there is @ and .
        for (int i = 0; i < emailAddress.length(); i++) {
            char a = emailAddress.charAt(i);
            if (a == '@')
                hasAt = true;
            if (a == '.')
                hasDot = true;

            if (hasAt && hasDot)
                break;
        }
        if (!hasAt || !hasDot) {
            System.out.println("Invalid email address. Email must have @ and .\n");
            return true;

        }

        return false;
    }

    // Overduedays calculation method
    public static int OverdueDaysCalculate(String StrDueDate) {

        String[] parts = StrDueDate.split("-");
        int dueYear = Integer.parseInt(parts[0]);
        int dueMonth = Integer.parseInt(parts[1]);
        int dueDay = Integer.parseInt(parts[2]);

        LocalDate TODAY = LocalDate.now();
        int CURRENT_YEAR = TODAY.getYear();
        int CURRENT_MONTH = TODAY.getMonthValue();
        int CURRENT_DAY = TODAY.getDayOfMonth();

        int dueTotal = (dueYear * 365) + (dueMonth * 30) + dueDay;
        int CurrentTotal = (CURRENT_YEAR * 365) + (CURRENT_MONTH * 30) + CURRENT_DAY;

        // calculating the overdue days by
        int Overduedays = CurrentTotal - dueTotal;
        if (Overduedays < 0)
            Overduedays = 0;
        return Overduedays;

    }
}
