import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;
import java.util.Scanner;

class Gym {
    private String name;

    private String nomatric;
    private String date;

    private double nophone;

    public Book(String name, String nomatric, String date, double nophone) {
        this.name = name;
        this.nomatric = nomatric;
        this.date = date;
        this.nophone = nophone;
    }

    public String getName() {
        return name;
    }

    public String getNomatric() {
        return nomatric;
    }

    public String getDate() {
        return date;
    }

    public double getNophone() {
        return nophone;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", nomatric: " + nomatric + ", Date: " + date + ", nophone: " + nophone;
    }
}

class GymBooking {
    private ArrayList<Gym> Gym;

    public GymBooking() { Gym = new ArrayList<>();}

    public void addGym(Gym gym) { gym.add(gym);}

    public void removeGym(Gym gym) {gym.remove(gym);}

    public ArrayList<Gym> getGym() {return Gym;}

    public void sortByName() {Collections.sort(gym, Comparator.comparing(Gym::getName));}

    public void sortByNomatric() {Collections.sort(gym, Comparator.comparing(Gym::getNomatric));}

    public void sortByDate() {
        Collections.sort(books, Comparator.comparing(Gym::getDate));
    }

    public void sortByNophone() {Collections.sort(books, Comparator.comparingDouble(Gym::getNophone));}

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Gym gym : gym) {
                writer.println(gym.getName());
                writer.println(gym.getIsbn());
                writer.println(gym.getDate());
                writer.println(gym.getPrice());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            books.clear();
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                String nomatric = scanner.nextLine();
                String date = scanner.nextLine();
                double nophone = Double.parseDouble(scanner.nextLine());
                String description = scanner.nextLine();
                Gym gym = new Gym(name, nomatric, date, nophone);
                gym.add(gym);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class GymBooking extends JFrame {
    private JTextField nameField, isbnField, dateField, priceField, descriptionField;
    private JTextArea displayArea;
    private GymBooking gymbooking;

    public GymBooking() {
        gymbooking = new GymBooking();

        setTitle("Gym Booking");
        setSize(900, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        getContentPane().setBackground(new Color(255, 229, 180));

        JLabel nameLabel = new JLabel("Name:");
        add(nameLabel);
        nameField = new JTextField(10);
        add(nameField);

        JLabel nomatricLabel = new JLabel("No Matric:");
        add(nomatricLabel);
        nomatricField = new JTextField(10);
        add(nomatricField);

        JLabel dateLabel = new JLabel("Date:");
        add(dateLabel);
        dateField = new JTextField(10);
        add(dateField);

        JLabel nophoneLabel = new JLabel("No Phone:");
        add(nophoneLabel);
        nophoneField = new JTextField(10);
        add(nophoneField);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String nomatric = nomatricField.getText();
                String date = dateField.getText();
                double nophone = Double.parseDouble(nophoneField.getText());

                Book book = new Book(name, nomatric, date, nophone);
                gymbooking.addGym(gym);
                displayGym();
                clearFields();
            }
        });
        add(addButton);

        JButton removeButton = new JButton("Remove Student");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String isbn = JOptionPane.showInputDialog("Enter the No Matric of the student to remove:");
                Gym gymToRemove = null;
                for (Gym gym : gymbooking.getGym()) {
                    if (gym.getNomatric().equals(nomatric)) {
                        gymToRemove = gym;
                        break;
                    }
                }
                if (gymToRemove != null) {
                    GymBooking.removeBook(bookToRemove);
                    displayGym();
                } else {
                    JOptionPane.showMessageDialog(null, "Student with No Matric " + nomatric + " not found.");
                }
            }
        });
        add(removeButton);

        JButton sortByNameButton = new JButton("Sort by Name");
        sortByNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GymBooking.sortByName();
                displayGym();
            }
        });
        add(sortByNameButton);

        JButton sortBynomatricButton = new JButton("Sort by No Matric");
        sortBynomatricButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GymBooking.sortBynomatric();
                displayGym();
            }
        });
        add(sortBynomatricButton);

        JButton sortByDateButton = new JButton("Sort by Date");
        sortByDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookCollection.sortByDate();
                displayGym();
            }
        });
        add(sortByDateButton);

        JButton sortBynophoneButton = new JButton("Sort by No Phone");
        sortBynophoneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GymBooking.sortBynophone();
                displayGym();
            }
        });
        add(sortByPriceButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename = JOptionPane.showInputDialog("Enter the filename to save:");
                GymBooking.saveToFile(filename);
                JOptionPane.showMessageDialog(null, "Gym Booking saved successfully.");
            }
        });
        add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename = JOptionPane.showInputDialog("Enter the filename to load:");
                GymBooking.loadFromFile(filename);
                displayGym();
                JOptionPane.showMessageDialog(null, "Gym Booking loaded successfully.");
            }
        });
        add(loadButton);

        displayArea = new JTextArea(40, 80);
        add(displayArea);

        setVisible(true);
    }

    private void displayGym() {
        displayArea.setText("");
        for (Gym gym : GymBooking.getGym()) {
            displayArea.append(gym.toString() + "\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
        nomatricField.setText("");
        dateField.setText("");
        nophoneField.setText("");
    }
}