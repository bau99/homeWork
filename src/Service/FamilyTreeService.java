package Service;

import Model.FamilyTree;
import Model.Person;
import Util.FileManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class FamilyTreeService {
    private final FamilyTree<Person> familyTree;
    private final FileManager fileManager;

    public FamilyTreeService(FamilyTree<Person> familyTree, FileManager fileManager) {
        this.familyTree = familyTree;
        this.fileManager = fileManager;
    }

    public void addPerson(String firstName, String lastName, LocalDate birthDate, LocalDate deathDate, Person.Gender gender) {
        Person person = new Person(firstName, lastName, birthDate, deathDate, gender);
        familyTree.addPerson(person);
    }

    public LocalDate validateAndParseDate(String prompt) {
        Scanner scanner = new Scanner(System.in);
        LocalDate date = null;
        while (date == null) {
            System.out.print(prompt);
            String dateStr = scanner.nextLine();
            if (dateStr.isEmpty()) {
                break;
            }
            try {
                date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Неправильный формат даты. Пожалуйста, используйте формат ГГГГ-ММ-ДД.");
            }
        }
        return date;
    }

    public Person.Gender validateAndParseGender(String prompt) {
        Scanner scanner = new Scanner(System.in);
        Person.Gender gender = null;
        while (gender == null) {
            System.out.print(prompt);
            String genderStr = scanner.nextLine();
            if (genderStr.isEmpty()) {
                break;
            }
            try {
                gender = Person.Gender.valueOf(genderStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильный формат гендера. Пожалуйста, используйте МУЖЧИНА или ЖЕНЩИНА.");
            }
        }
        return gender;
    }

    public Person findByNameOrId(String searchInput) {
        if (searchInput.matches("\\d+")) {
            int searchId = Integer.parseInt(searchInput);
            return findById(searchId);
        } else {
            String[] nameParts = searchInput.split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";
            return findByName(firstName, lastName);
        }
    }

    public Person findById(int id) {
        for (Person person : familyTree.getAllPeople()) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public Person findByName(String firstName, String lastName) {
        for (Person person : familyTree.getAllPeople()) {
            if (person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) {
                return person;
            }
        }
        return null;
    }

    public void saveTree(String fileName) {
        fileManager.saveFamilyTree(familyTree, fileName);
    }

    public void loadTree(String fileName) {
        FamilyTree<Person> loadedTree = (FamilyTree<Person>) fileManager.loadFamilyTree(fileName);
        if (loadedTree != null) {
            familyTree.getAllPeople().clear();
            familyTree.getAllPeople().addAll(loadedTree.getAllPeople());
        }
    }

    public void setParents(int childId, int motherId, int fatherId) {
        familyTree.setParents(childId, motherId, fatherId);
    }

    public FamilyTree<Person> getFamilyTree() {
        return familyTree;
    }
}

//package Service;
//
//import Model.FamilyTree;
//import Model.Person;
//import Util.FileManager;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class FamilyTreeService {
//    private final FamilyTree<Person> familyTree;
//    private final FileManager fileManager;
//
//    public FamilyTreeService(FamilyTree<Person> familyTree, FileManager fileManager) {
//        this.familyTree = familyTree;
//        this.fileManager = fileManager;
//    }
//
//    public void addPerson(String firstName, String lastName, LocalDate birthDate, LocalDate deathDate, Person.Gender gender) {
//        Person person = new Person(firstName, lastName, birthDate, deathDate, gender);
//        familyTree.addPerson(person);
//    }
//
//    public void setParents(int childId, int motherId, int fatherId) {
//        familyTree.setParents(childId, motherId, fatherId);
//    }
//    public List<Person> getAllPeople(int sortChoice) {
//        if (sortChoice == 1) {
//            familyTree.sort();
//        } else if (sortChoice == 2) {
//            familyTree.sortByBirthDate();
//        }
//        return familyTree.getAllPeople();
//    }
//
//    public Person findById(int id) {
//        return familyTree.getPerson(id);
//    }
//
//    public List<Person> findByName(String firstName, String lastName) {
//        return familyTree.findByName(firstName, lastName);
//    }
//
//    public void saveTree(String filename) {
//        fileManager.saveFamilyTree(familyTree, filename);
//    }
//
//    public void loadTree(String filename) {
//        FamilyTree<Person> loadedTree = (FamilyTree<Person>) fileManager.loadFamilyTree(filename);
//        if (loadedTree != null) {
//            familyTree.getAllPeople().clear();
//            familyTree.getAllPeople().addAll(loadedTree.getAllPeople());
//        }
//    }
//
//    public FamilyTree<Person> getFamilyTree() {
//        return familyTree;
//    }
//    public void getChildren(int personId) {
//        familyTree.getChildren(personId);
//    }
//
//    public void getParents(int personId) {
//        familyTree.getParents(personId);
//    }
//}
