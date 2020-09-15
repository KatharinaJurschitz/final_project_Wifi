package syd.view;

import syd.model.daycare.Daycare;
import syd.model.daycareentry.DaycareEntry;
import syd.model.daycareentry.DaycareEntryRepository;
import syd.model.guest.Guest;
import syd.model.guest.GuestRepository;
import syd.model.person.Person;
import syd.model.person.host.Host;
import syd.model.person.host.HostRepository;
import syd.model.person.owner.Owner;
import syd.model.person.owner.OwnerRepository;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Consumer;
import static java.time.temporal.ChronoUnit.DAYS;

public class Rahmen extends JFrame {
    private Daycare daycare;
    private DaycareEntryRepository daycareEntryRepository;
    private GuestRepository guestRepository;
    private OwnerRepository ownerRepository;
    private HostRepository hostRepository;
    private JComboBox resChooseGuestDropdown;
    private JTextField usernameTextfield;

    public Rahmen(GuestRepository guestRepository, OwnerRepository ownerRepository, HostRepository hostRepository, DaycareEntryRepository daycareEntryRepository) {
        daycare = new Daycare(daycareEntryRepository, guestRepository, ownerRepository, hostRepository);
        this.daycareEntryRepository = daycareEntryRepository;
        this.guestRepository = guestRepository;
        this.ownerRepository = ownerRepository;
        this.hostRepository = hostRepository;

        // ---------------------------------------- GENERAL -------------------------------------
        // --------------------------------- components ------------
        JTabbedPane tab = new JTabbedPane();
        JPanel tabMasterData = new JPanel();
        JPanel tabBookReservation = new JPanel();
        JPanel tabManage = new JPanel();
        JPanel tabSearch = new JPanel();
        JPanel mdNorthpanel = new JPanel();
        JPanel mdCenterpanel = new JPanel();
        JPanel mdSouthpanel = new JPanel();
        JPanel resNorthpanel = new JPanel();
        JPanel resCenterpanel = new JPanel();
        JPanel resSouthpanel = new JPanel();
        JPanel manageNorthpanel = new JPanel();
        JPanel manageCenterpanel = new JPanel();
        JPanel manageSouthpanel = new JPanel();
        JPanel searchNorthpanel = new JPanel();
        JPanel searchCenterpanel = new JPanel();
        JPanel searchSouthpanel = new JPanel();

        // --------------------------------- layout -----------------------
        this.add(tab);
        tab.add("master data",tabMasterData);
        tabMasterData.setLayout(new BorderLayout());
        tab.add("book reservation",tabBookReservation);
        tabBookReservation.setLayout(new BorderLayout());
        tab.add("manage reservations", tabManage);
        tabManage.setLayout(new BorderLayout());
        tab.add("search", tabSearch);
        tabSearch.setLayout(new BorderLayout());

        // ---------------------------------------- MASTER DATA  ------------------------------
        // --------------------------------- components ------------

        JLabel ownerMDLabel = new JLabel("Your information  (Owner):");
        ownerMDLabel.setFont(new Font("Verdana",Font.BOLD, 16));
        JLabel usernameLabel = new JLabel("Username: *");
        JLabel nameLabel = new JLabel("Name: *");
        JLabel addressLabel = new JLabel("Address:");
        JLabel telephoneLabel = new JLabel("Telephone:");
        usernameTextfield = new JTextField();
        JTextField nameTextfield = new JTextField();
        JTextField addressTextfield = new JTextField();
        JTextField telephoneTextfield = new JTextField();
        JButton registerOwnerButton = new JButton("register Owner");
        JButton loadOwnerButton = new JButton("load Owner");
        JButton saveOwnerButton = new JButton("save Owner");
        JLabel guestMDLabel = new JLabel("Your pet's information (Guest):");
        guestMDLabel.setFont(new Font("Verdana",Font.BOLD, 16));
        JButton mdEditGuestButton = new JButton("edit Guest");
        JButton mdRemoveGuestButton = new JButton("remove Guest");
        List mdGuestList = new List();
        JButton mdAddGuestButton = new JButton("add Guest");
        JButton mdSaveGuestButton = new JButton("save Guest");

        JLabel guesttypeLabel = new JLabel(" Guest Type *: ");
        String typeGuest[] = {"please choose a type","cat", "dog", "rabbit", "guinea pig", "lizard", "bird", "rat/mouse", "snake"};
        JComboBox guesttypeDropdown = new JComboBox(typeGuest);
        JLabel guestnameLabel = new JLabel(" Guest Name *: ");
        JTextField guestnameTextfield = new JTextField();
        JLabel guestcareLabel = new JLabel(" Care Options: ");

        JComboBox guestcareDropdown = new JComboBox();
        ComboBoxModel[] guestcareModel = new ComboBoxModel[9];
        guestcareModel[0] = new DefaultComboBoxModel(new String[]{"please choose a type"});
        guestcareModel[1] = new DefaultComboBoxModel(new String[]{"dry food", "wet food", "daily treats"});
        guestcareModel[2] = new DefaultComboBoxModel(new String[]{"fresh raw meat", "canned dog food", "daily treats"});
        guestcareModel[3] = new DefaultComboBoxModel(new String[]{"veggies", "salt sticks", "nuts and seeds"});
        guestcareModel[4] = new DefaultComboBoxModel(new String[]{"veggies", "salt sticks", "nuts and seeds"});
        guestcareModel[5] = new DefaultComboBoxModel(new String[]{"live crickets", "live worms", "live larvae"});
        guestcareModel[6] = new DefaultComboBoxModel(new String[]{"nuts and seeds", "live worms", "veggies"});
        guestcareModel[7] = new DefaultComboBoxModel(new String[]{"nuts and seeds", "live worms", "veggies"});
        guestcareModel[8] = new DefaultComboBoxModel(new String[]{"mice", "rats", "gerbils"});
        guestcareDropdown.setModel(guestcareModel[0]);
        guesttypeDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = guesttypeDropdown.getSelectedIndex();
                guestcareDropdown.setModel(guestcareModel[i]);
            }
        });

        JButton mdNextButton = new JButton("next >");
        JButton mdCloseButton = new JButton("close");

        // --------------------------------- layout ------------------

        tabMasterData.add(mdCenterpanel, BorderLayout.CENTER);
        mdCenterpanel.setLayout (new BoxLayout (mdCenterpanel, BoxLayout.Y_AXIS));
        mdCenterpanel.add(mdGuestList);

        tabMasterData.add(mdNorthpanel, BorderLayout.NORTH);
        mdNorthpanel.setLayout(new GridLayout(0,2));
        mdNorthpanel.add(new JLabel()); mdNorthpanel.add(new JLabel());
        mdNorthpanel.add(ownerMDLabel); mdNorthpanel.add(new JLabel());
        mdNorthpanel.add(new JLabel()); mdNorthpanel.add(new JLabel());
        mdNorthpanel.add(usernameLabel); mdNorthpanel.add(usernameTextfield);
        mdNorthpanel.add(nameLabel); mdNorthpanel.add(nameTextfield);
        mdNorthpanel.add(addressLabel); mdNorthpanel.add(addressTextfield);
        mdNorthpanel.add(telephoneLabel); mdNorthpanel.add(telephoneTextfield);
        mdNorthpanel.add(new JLabel()); mdNorthpanel.add(new JLabel());
        mdNorthpanel.add(registerOwnerButton); mdNorthpanel.add(loadOwnerButton);
        mdNorthpanel.add(new JLabel()); mdNorthpanel.add(saveOwnerButton);
        mdNorthpanel.add(new JLabel()); mdNorthpanel.add(new JLabel());
        mdNorthpanel.add(guestMDLabel); mdNorthpanel.add(new JLabel());
        mdNorthpanel.add(new JLabel()); mdNorthpanel.add(new JLabel());
        mdNorthpanel.add(guestnameLabel); mdNorthpanel.add(guestnameTextfield);
        mdNorthpanel.add(guesttypeLabel); mdNorthpanel.add(guesttypeDropdown);
        mdNorthpanel.add(guestcareLabel); mdNorthpanel.add(guestcareDropdown);
        mdNorthpanel.add(new JLabel()); mdNorthpanel.add(new JLabel());
        mdNorthpanel.add(mdAddGuestButton); mdNorthpanel.add(mdSaveGuestButton);
        mdNorthpanel.add(new JLabel()); mdNorthpanel.add(new JLabel());

        tabMasterData.add(mdSouthpanel, BorderLayout.SOUTH);
        mdSouthpanel.setLayout(new GridLayout(0,2));
        mdSouthpanel.add(mdEditGuestButton); mdSouthpanel.add(mdRemoveGuestButton);
        mdSouthpanel.add(new JLabel()); mdSouthpanel.add(new JLabel());
        mdSouthpanel.add(mdCloseButton); mdSouthpanel.add(mdNextButton);
        mdSouthpanel.add(new JLabel()); mdSouthpanel.add(new JLabel());

        // ---------------------------------------- RES TAB  ------------------------------
        // --------------------------------- components ------------

        JLabel resChooseGuestLabel = new JLabel("Choose a guest *:");
        resChooseGuestDropdown = new JComboBox();
        JLabel resFromLabel = new JLabel("Reservation from *: ");
        JLabel resToLabel = new JLabel("Reservation to *: ");
        JLabel fromGridDateDescLabel = new JLabel("");
        JLabel fromGridDayMonYearLabel = new JLabel("");
        JLabel fromGridDayMonLabel = new JLabel("");
        JLabel fromDescLabel = new JLabel("(DD MM YYYY)");
        JLabel toGridDateDescLabel = new JLabel("");
        JLabel toGridDayMonYearLabel = new JLabel("");
        JLabel toGridDayMonLabel = new JLabel("");
        JLabel toDescLabel = new JLabel("(DD MM YYYY)");
        JTextField fromDayTextfield = new JTextField();
        JTextField fromMonthTextfield = new JTextField();
        JTextField fromYearTextfield = new JTextField();
        JTextField toDayTextfield = new JTextField();
        JTextField toMonthTextfield = new JTextField();
        JTextField toYearTextfield = new JTextField();
        JButton resShowHostsButton = new JButton("show available Hosts");
        JLabel resChooseHostLabel = new JLabel("Choose a Host:");
        List resHostsList = new List();
        JButton resCloseButton = new JButton("close");
        JButton resSaveButton = new JButton("book reservation");

        // --------------------------------- layout -----------------------
        tabBookReservation.add(resNorthpanel, BorderLayout.NORTH);
        resNorthpanel.setLayout(new GridLayout(0,2));

        resNorthpanel.add(new JLabel()); resNorthpanel.add(new JLabel());
        resNorthpanel.add(resChooseGuestLabel); resNorthpanel.add(resChooseGuestDropdown);
        resNorthpanel.add(new JLabel()); resNorthpanel.add(new JLabel());

        resNorthpanel.add(resFromLabel); resNorthpanel.add(fromGridDateDescLabel);
        fromGridDateDescLabel.setLayout(new GridLayout(0,2));
        fromGridDateDescLabel.add(fromGridDayMonYearLabel); fromGridDateDescLabel.add(fromDescLabel);
        fromGridDayMonYearLabel.setLayout(new GridLayout(0,2));
        fromGridDayMonYearLabel.add(fromGridDayMonLabel); fromGridDayMonYearLabel.add(fromYearTextfield);
        fromGridDayMonLabel.setLayout(new GridLayout(0,2));
        fromGridDayMonLabel.add(fromDayTextfield); fromGridDayMonLabel.add(fromMonthTextfield);

        resNorthpanel.add(resToLabel); resNorthpanel.add(toGridDateDescLabel);
        toGridDateDescLabel.setLayout(new GridLayout(0,2));
        toGridDateDescLabel.add(toGridDayMonYearLabel); toGridDateDescLabel.add(toDescLabel);
        toGridDayMonYearLabel.setLayout(new GridLayout(0,2));
        toGridDayMonYearLabel.add(toGridDayMonLabel); toGridDayMonYearLabel.add(toYearTextfield);
        toGridDayMonLabel.setLayout(new GridLayout(0,2));
        toGridDayMonLabel.add(toDayTextfield); toGridDayMonLabel.add(toMonthTextfield);

        resNorthpanel.add(new JLabel()); resNorthpanel.add(new JLabel());
        resNorthpanel.add(new JLabel()); resNorthpanel.add(resShowHostsButton);
        resNorthpanel.add(new JLabel()); resNorthpanel.add(new JLabel());
        resNorthpanel.add(resChooseHostLabel); resNorthpanel.add(new JLabel());

        tabBookReservation.add(resCenterpanel, BorderLayout.CENTER);
        resCenterpanel.setLayout (new BoxLayout (resCenterpanel, BoxLayout.Y_AXIS));
        resCenterpanel.add(resHostsList);

        tabBookReservation.add(resSouthpanel, BorderLayout.SOUTH);
        resSouthpanel.setLayout(new GridLayout(0,2));
        resSouthpanel.add(new JLabel()); resSouthpanel.add(new JLabel());
        resSouthpanel.add(new JLabel()); resSouthpanel.add(new JLabel());
        resSouthpanel.add(new JLabel()); resSouthpanel.add(new JLabel());
        resSouthpanel.add(new JLabel()); resSouthpanel.add(new JLabel());
        resSouthpanel.add(new JLabel()); resSouthpanel.add(new JLabel());
        resSouthpanel.add(resCloseButton); resSouthpanel.add(resSaveButton);
        resSouthpanel.add(new JLabel()); resSouthpanel.add(new JLabel());

        // ---------------------------------------- MANAGE TAB  ------------------------------
        // --------------------------------- components ------------
        JLabel manageRegNoLabel = new JLabel("   Reservation No.: ", SwingConstants.LEFT);
        JTextField manageRegNoTextfield = new JTextField();
        JButton manageShowButton = new JButton("show");
        JButton bringInButton = new JButton("bring in");
        JButton pickUpButton = new JButton("pick up");
        JButton manageCancelResButton = new JButton("cancel reservation");
        JButton manageCloseButton = new JButton("close");

        JTextArea manageTextArea = new JTextArea();
        manageTextArea.setRows(9);
        manageTextArea.setPreferredSize(new Dimension(500, 700));

        // --------------------------------- layout -----------------------
        tabManage.add(manageNorthpanel, BorderLayout.NORTH);
        manageNorthpanel.setLayout(new GridLayout(0,3));
        manageNorthpanel.add(new JLabel("")); manageNorthpanel.add(new JLabel("")); manageNorthpanel.add(new JLabel(""));
        manageNorthpanel.add(manageRegNoLabel); manageNorthpanel.add(manageRegNoTextfield); manageNorthpanel.add(manageShowButton);
        manageNorthpanel.add(new JLabel("")); manageNorthpanel.add(new JLabel("")); manageNorthpanel.add(new JLabel(""));

        tabManage.add(manageCenterpanel, BorderLayout.CENTER);
        manageCenterpanel.add(manageTextArea);

        tabManage.add(manageSouthpanel, BorderLayout.SOUTH);
        manageSouthpanel.setLayout(new GridLayout(0, 2));
        manageSouthpanel.add(new JLabel("")); manageSouthpanel.add(new JLabel(""));
        manageSouthpanel.add(bringInButton); manageSouthpanel.add(pickUpButton);
        manageSouthpanel.add(manageCancelResButton); manageSouthpanel.add(manageCloseButton);
        manageSouthpanel.add(new JLabel("")); manageSouthpanel.add(new JLabel(""));
        manageSouthpanel.add(new JLabel("")); manageSouthpanel.add(new JLabel(""));

        // ---------------------------------------- SEARCH TAB  ------------------------------
        // --------------------------------- components ------------
        JButton showAllButton = new JButton("show all reservations");
        JButton searchByButton = new JButton("search");
        JTextField searchbyGuestTextfield = new JTextField();
        JTextField searchbyOwnerTextfield = new JTextField();
        JTextField searchbyHostTextfield = new JTextField();
        JTextField searchbyStatusTextfield = new JTextField();
        JRadioButton searchbyGuestLabel = new JRadioButton("  search by Guest: ");
        JRadioButton searchbyOwnerLabel = new JRadioButton("  search by Owner Username: ");
        JRadioButton searchbyHostLabel = new JRadioButton("  search by Host: ");
        JRadioButton searchbyStatusLabel = new JRadioButton("  search by Status: ");
        searchbyGuestTextfield.setEnabled(false);
        searchbyOwnerTextfield.setEnabled(false);
        searchbyHostTextfield.setEnabled(false);
        searchbyStatusTextfield.setEnabled(false);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(searchbyGuestLabel); buttonGroup.add(searchbyHostLabel); buttonGroup.add(searchbyOwnerLabel); buttonGroup.add(searchbyStatusLabel);
        JTextArea searchTextarea = new JTextArea();
        JScrollPane searchScrollPane = new JScrollPane(searchTextarea);

        searchTextarea.setRows(33);
        searchTextarea.setPreferredSize(new Dimension(500,700));
        JButton searchCloseButton = new JButton("close");

        // --------------------------------- layout -----------------------
        tabSearch.add(searchNorthpanel, BorderLayout.NORTH);
        searchNorthpanel.setLayout(new GridLayout(0,2));
        searchNorthpanel.add(new JLabel("")); searchNorthpanel.add(new JLabel(""));
        searchNorthpanel.add(searchbyGuestLabel); searchNorthpanel.add(searchbyGuestTextfield);
        searchNorthpanel.add(searchbyOwnerLabel); searchNorthpanel.add(searchbyOwnerTextfield);
        searchNorthpanel.add(searchbyHostLabel); searchNorthpanel.add(searchbyHostTextfield);
        searchNorthpanel.add(searchbyStatusLabel); searchNorthpanel.add(searchbyStatusTextfield);
        searchNorthpanel.add(new JLabel("")); searchNorthpanel.add(new JLabel(""));
        searchNorthpanel.add(searchByButton); searchNorthpanel.add(showAllButton);
        searchNorthpanel.add(new JLabel("")); searchNorthpanel.add(new JLabel(""));

        tabSearch.add(searchCenterpanel,BorderLayout.CENTER);
        searchCenterpanel.add(searchScrollPane);

        tabSearch.add(searchSouthpanel, BorderLayout.SOUTH);
        searchSouthpanel.setLayout(new GridLayout(0, 3));
        searchSouthpanel.add(new JLabel("")); searchSouthpanel.add(new JLabel("")); searchSouthpanel.add(new JLabel(""));
        searchSouthpanel.add(new JLabel("")); searchSouthpanel.add(searchCloseButton); searchSouthpanel.add(new JLabel(""));
        searchSouthpanel.add(new JLabel("")); searchSouthpanel.add(new JLabel("")); searchSouthpanel.add(new JLabel(""));





        // --------------------------------------- EVENT HANDLING ------------------------------------







        registerOwnerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Owner pOwner;
                try {
                    pOwner = new Owner(usernameTextfield.getText(),nameTextfield.getText(),addressTextfield.getText(),telephoneTextfield.getText());
                } catch (Exception ex){
                    JFrame mandatoryErrorFrame = new JFrame("Error");
                    JLabel mandatoryErrorLabel = new JLabel(ex.getMessage(), SwingConstants.CENTER);
                    mandatoryErrorFrame.add(mandatoryErrorLabel);
                    mandatoryErrorFrame.setSize(300,100);
                    mandatoryErrorFrame.setResizable(false);
                    mandatoryErrorFrame.setLocationRelativeTo(null);
                    mandatoryErrorFrame.dispose();

                    mandatoryErrorFrame.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 27) {
                                mandatoryErrorFrame.dispose();
                            }
                        }
                    });
                    mandatoryErrorFrame.setVisible(true);
                    return;
                }
                ownerRepository.save(pOwner);
                JFrame registerOwnerOKFrame = new JFrame("Owner registered");
                JLabel registerOwnerOKLabel = new JLabel("The Owner was registered successfully", SwingConstants.CENTER);
                registerOwnerOKFrame.add(registerOwnerOKLabel);
                registerOwnerOKFrame.setSize(300,100);
                registerOwnerOKFrame.setResizable(false);
                registerOwnerOKFrame.setLocationRelativeTo(null);
                registerOwnerOKFrame.dispose();

                registerOwnerOKFrame.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == 27) {
                            registerOwnerOKFrame.dispose();
                        }
                    }
                });
                registerOwnerOKFrame.setVisible(true);
            }
        });

        saveOwnerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Owner pOwner = ownerRepository.findByUsername(usernameTextfield.getText());
                pOwner.setName(nameTextfield.getText());
                pOwner.setAddress(addressTextfield.getText());
                pOwner.setTelephone(telephoneTextfield.getText());
                ownerRepository.save(pOwner);
                JFrame savedOwnerFrame = new JFrame("Owner saved");
                JLabel savedOwnerLabel = new JLabel("The Owner's information was saved successfully", SwingConstants.CENTER);
                savedOwnerFrame.add(savedOwnerLabel);
                savedOwnerFrame.setSize(300,100);
                savedOwnerFrame.setResizable(false);
                savedOwnerFrame.setLocationRelativeTo(null);
                savedOwnerFrame.dispose();

                savedOwnerFrame.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == 27) {
                            savedOwnerFrame.dispose();
                        }
                    }
                });
                savedOwnerFrame.setVisible(true);
            }
        });

        loadOwnerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Owner pOwner = ownerRepository.findByUsername(usernameTextfield.getText());
                nameTextfield.setText(pOwner.getName());
                addressTextfield.setText(pOwner.getAddress());
                telephoneTextfield.setText(pOwner.getTelephone());
                mdGuestList.removeAll();
                pOwner.getGuests().forEach(new Consumer<Guest>() {
                    @Override
                    public void accept(Guest guest) {
                        mdGuestList.add(guest.toString());
                    }
                });
                refreshChooseGuest();
            }
        });

        mdAddGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Guest pGuest;
                try {
                    pGuest = new Guest(guestnameTextfield.getText(), guesttypeDropdown.getSelectedItem().toString(), guestcareDropdown.getSelectedItem().toString());
                } catch (Exception ex){
                    JFrame mandatoryErrorFrame = new JFrame("Error");
                    JLabel mandatoryErrorLabel = new JLabel(ex.getMessage(), SwingConstants.CENTER);
                    mandatoryErrorFrame.add(mandatoryErrorLabel);
                    mandatoryErrorFrame.setSize(300,100);
                    mandatoryErrorFrame.setResizable(false);
                    mandatoryErrorFrame.setLocationRelativeTo(null);
                    mandatoryErrorFrame.dispose();

                    mandatoryErrorFrame.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 27) {
                                mandatoryErrorFrame.dispose();
                            }
                        }
                    });
                    mandatoryErrorFrame.setVisible(true);
                    return;
                }
                guestRepository.save(pGuest);
                Owner pOwner = ownerRepository.findByUsername(usernameTextfield.getText());
                pOwner.addGuest(pGuest);
                ownerRepository.save(pOwner);
                mdGuestList.removeAll();
                pOwner.getGuests().forEach(new Consumer<Guest>() {
                    @Override
                    public void accept(Guest guest) {
                        mdGuestList.add(guest.toString());
                    }
                });
                refreshChooseGuest();
            }
        });

        mdEditGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suchergeb = (mdGuestList.getSelectedItem());
                suchergeb = suchergeb.substring(suchergeb.indexOf("[ID ")+4,suchergeb.indexOf(']'));
                Guest pGuest = guestRepository.findByid(Long.valueOf(suchergeb));
                guestnameTextfield.setText(pGuest.getName());
                guesttypeDropdown.setSelectedItem(pGuest.getType());
                guestcareDropdown.setSelectedItem(pGuest.getCareoption());
            }
        });

        mdSaveGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suchergeb = (mdGuestList.getSelectedItem());
                suchergeb = suchergeb.substring(suchergeb.indexOf("[ID ")+4,suchergeb.indexOf(']'));
                Guest pGuest = guestRepository.findByid(Long.valueOf(suchergeb));
                pGuest.setName(guestnameTextfield.getText());
                pGuest.setType(guesttypeDropdown.getSelectedItem().toString());
                pGuest.setCareoption(guestcareDropdown.getSelectedItem().toString());
                guestRepository.save(pGuest);
                JFrame savedGuestFrame = new JFrame("Guest saved");
                JLabel savedGuestLabel = new JLabel("The Guest's information was saved successfully", SwingConstants.CENTER);
                savedGuestFrame.add(savedGuestLabel);
                savedGuestFrame.setSize(300,100);
                savedGuestFrame.setResizable(false);
                savedGuestFrame.setLocationRelativeTo(null);
                savedGuestFrame.dispose();

                savedGuestFrame.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == 27) {
                            savedGuestFrame.dispose();
                        }
                    }
                });
                savedGuestFrame.setVisible(true);
                refreshChooseGuest();
            }
        });

        mdRemoveGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String suchergeb = (mdGuestList.getSelectedItem());
                suchergeb = suchergeb.substring(suchergeb.indexOf("[ID ")+4,suchergeb.indexOf(']'));
                Owner pOwner = ownerRepository.findByUsername(usernameTextfield.getText());
                //remove all reservations for this particular guest
                java.util.List <DaycareEntry> entryList = daycareEntryRepository.findByGuest(pOwner.getGuestById(Long.valueOf(suchergeb)));
                entryList.forEach(new Consumer<DaycareEntry>() {
                    @Override
                    public void accept(DaycareEntry daycareEntry) {
                        daycareEntryRepository.delete(daycareEntry);
                    }
                });
                pOwner.removeGuestById(Long.valueOf(suchergeb));
                ownerRepository.save(pOwner);

                guestRepository.deleteById(Long.valueOf(suchergeb));
                mdGuestList.removeAll();
                pOwner.getGuests().forEach(new Consumer<Guest>() {
                    @Override
                    public void accept(Guest guest) {
                        mdGuestList.add(guest.toString());
                    }
                });
                refreshChooseGuest();
            }
        });

        mdNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab.setSelectedIndex(1);
            }
        });

        mdCloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        resShowHostsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate pFrom;
                try {
                    pFrom = LocalDate.of(Integer.parseInt(fromYearTextfield.getText()), Integer.parseInt(fromMonthTextfield.getText()), Integer.parseInt(fromDayTextfield.getText()));
                } catch (Exception ex){
                    JFrame incorrectDateErrorFrame = new JFrame("Error");
                    JLabel incorrectDateErrorLabel = new JLabel("Please enter a valid date.", SwingConstants.CENTER);
                    incorrectDateErrorFrame.add(incorrectDateErrorLabel);
                    incorrectDateErrorFrame.setSize(300,100);
                    incorrectDateErrorFrame.setResizable(false);
                    incorrectDateErrorFrame.setLocationRelativeTo(null);
                    incorrectDateErrorFrame.dispose();

                    incorrectDateErrorFrame.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 27) {
                                incorrectDateErrorFrame.dispose();
                            }
                        }
                    });
                    incorrectDateErrorFrame.setVisible(true);
                    return;
                }

                LocalDate pTo;
                try {
                    pTo = LocalDate.of(Integer.parseInt(toYearTextfield.getText()), Integer.parseInt(toMonthTextfield.getText()), Integer.parseInt(toDayTextfield.getText()));
                } catch (Exception ex){
                    JFrame incorrectDateErrorFrame = new JFrame("Error");
                    JLabel incorrectDateErrorLabel = new JLabel("Please enter a valid date.", SwingConstants.CENTER);
                    incorrectDateErrorFrame.add(incorrectDateErrorLabel);
                    incorrectDateErrorFrame.setSize(300,100);
                    incorrectDateErrorFrame.setResizable(false);
                    incorrectDateErrorFrame.setLocationRelativeTo(null);
                    incorrectDateErrorFrame.dispose();

                    incorrectDateErrorFrame.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 27) {
                                incorrectDateErrorFrame.dispose();
                            }
                        }
                    });
                    incorrectDateErrorFrame.setVisible(true);
                    return;
                }

                try {
                    checkDate(pFrom,pTo);
                } catch (Exception ex) {
                    JFrame errorFromGreaterToFrame = new JFrame("Error");
                    JLabel errorFromGreaterToLabel = new JLabel(ex.getMessage(), SwingConstants.CENTER);
                    errorFromGreaterToFrame.add(errorFromGreaterToLabel);
                    errorFromGreaterToFrame.setSize(300,100);
                    errorFromGreaterToFrame.setResizable(false);
                    errorFromGreaterToFrame.setLocationRelativeTo(null);
                    errorFromGreaterToFrame.dispose();

                    errorFromGreaterToFrame.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 27) {
                                errorFromGreaterToFrame.dispose();
                            }
                        }
                    });
                    errorFromGreaterToFrame.setVisible(true);
                    return;
                }

                resHostsList.removeAll();
                hostRepository.findAll().forEach(new Consumer<Host>() {
                    @Override
                    public void accept(Host host) {
                        String suchergeb = resChooseGuestDropdown.getSelectedItem().toString();
                        suchergeb = suchergeb.substring(suchergeb.indexOf("[")+1,suchergeb.indexOf("]"));
                        if (host.getFreeCapacity(LocalDate.of(Integer.parseInt(fromYearTextfield.getText()),Integer.parseInt(fromMonthTextfield.getText()),Integer.parseInt(fromDayTextfield.getText())),
                                LocalDate.of(Integer.parseInt(toYearTextfield.getText()),Integer.parseInt(toMonthTextfield.getText()),Integer.parseInt(toDayTextfield.getText())),
                                suchergeb)>0){
                            resHostsList.add(host.toString());
                        }
                    }
                });
            }
        });

        resSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate pFrom;
                try {
                    pFrom = LocalDate.of(Integer.parseInt(fromYearTextfield.getText()), Integer.parseInt(fromMonthTextfield.getText()), Integer.parseInt(fromDayTextfield.getText()));
                } catch (Exception ex){
                    JFrame incorrectDateErrorFrame = new JFrame("Error");
                    JLabel incorrectDateErrorLabel = new JLabel("Please enter a valid date.", SwingConstants.CENTER);
                    incorrectDateErrorFrame.add(incorrectDateErrorLabel);
                    incorrectDateErrorFrame.setSize(300,100);
                    incorrectDateErrorFrame.setResizable(false);
                    incorrectDateErrorFrame.setLocationRelativeTo(null);
                    incorrectDateErrorFrame.dispose();

                    incorrectDateErrorFrame.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 27) {
                                incorrectDateErrorFrame.dispose();
                            }
                        }
                    });
                    incorrectDateErrorFrame.setVisible(true);
                    return;
                }
                LocalDate pTo;
                try {
                    pTo = LocalDate.of(Integer.parseInt(toYearTextfield.getText()), Integer.parseInt(toMonthTextfield.getText()), Integer.parseInt(toDayTextfield.getText()));
                } catch (Exception ex){
                    JFrame incorrectDateErrorFrame = new JFrame("Error");
                    JLabel incorrectDateErrorLabel = new JLabel("Please enter a valid date.", SwingConstants.CENTER);
                    incorrectDateErrorFrame.add(incorrectDateErrorLabel);
                    incorrectDateErrorFrame.setSize(300,100);
                    incorrectDateErrorFrame.setResizable(false);
                    incorrectDateErrorFrame.setLocationRelativeTo(null);
                    incorrectDateErrorFrame.dispose();

                    incorrectDateErrorFrame.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 27) {
                                incorrectDateErrorFrame.dispose();
                            }
                        }
                    });
                    incorrectDateErrorFrame.setVisible(true);
                    return;
                }

                try {
                    checkDate(pFrom,pTo);
                } catch (Exception ex) {
                    JFrame errorFromGreaterToFrame = new JFrame("Error");
                    JLabel errorFromGreaterToLabel = new JLabel(ex.getMessage(), SwingConstants.CENTER);
                    errorFromGreaterToFrame.add(errorFromGreaterToLabel);
                    errorFromGreaterToFrame.setSize(300,100);
                    errorFromGreaterToFrame.setResizable(false);
                    errorFromGreaterToFrame.setLocationRelativeTo(null);
                    errorFromGreaterToFrame.dispose();

                    errorFromGreaterToFrame.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 27) {
                                errorFromGreaterToFrame.dispose();
                            }
                        }
                    });
                    errorFromGreaterToFrame.setVisible(true);
                    return;
                }

                Host pHost;
                String suchergeb = (resHostsList.getSelectedItem());
                suchergeb = suchergeb.substring(suchergeb.indexOf("[ID ")+4,suchergeb.indexOf(']'));
                pHost = hostRepository.findByid(Long.valueOf(suchergeb));

                Owner pOwner = ownerRepository.findByUsername(usernameTextfield.getText());

                String guestnamesubstring = resChooseGuestDropdown.getSelectedItem().toString();
                guestnamesubstring = guestnamesubstring.substring(guestnamesubstring.indexOf("]")+2);
                String guesttypesubstring = resChooseGuestDropdown.getSelectedItem().toString();
                guesttypesubstring = guesttypesubstring.substring(guesttypesubstring.indexOf("[")+1, guesttypesubstring.indexOf("]"));
                Guest pGuest = pOwner.getGuest(guestnamesubstring, guesttypesubstring);

                JFrame priceOKFrame = new JFrame();
                JOptionPane priceOKLabel = new JOptionPane();
                DaycareEntry daycareEntry = null;
                try {
                    int okReply = priceOKLabel.showConfirmDialog(priceOKFrame, "The hosting for these "
                            + (DAYS.between(pFrom, pTo)+1) + " days will cost " + daycare.getPriceForBooking(pFrom, pTo, pHost)
                            + "EUR. Do you agree to these price conditions? (Y/N)", "Are you sure?", JOptionPane.YES_NO_OPTION);
                    if (okReply == JOptionPane.YES_OPTION){
                        try {
                            daycareEntry = daycare.bookHosting(pGuest, pOwner, pFrom, pTo, pHost);
                            JFrame confirmRegNoFrame = new JFrame();
                            JOptionPane confirmRegNoLabel = new JOptionPane();
                            confirmRegNoLabel.showMessageDialog(confirmRegNoFrame,daycare.getConfirmMsg(daycareEntry.getId()));
                            daycareEntryRepository.save(daycareEntry);
                        } catch (Exception exception){
                            JFrame errorFromGreaterToFrame = new JFrame("Error");
                            JLabel errorFromGreaterToLabel = new JLabel(exception.getMessage(), SwingConstants.CENTER);
                            errorFromGreaterToFrame.add(errorFromGreaterToLabel);
                            errorFromGreaterToFrame.setSize(300,100);
                            errorFromGreaterToFrame.setResizable(false);
                            errorFromGreaterToFrame.setLocationRelativeTo(null);
                            errorFromGreaterToFrame.dispose();

                            errorFromGreaterToFrame.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyPressed(KeyEvent e) {
                                    if (e.getKeyCode() == 27) {
                                        errorFromGreaterToFrame.dispose();
                                    }
                                }
                            });
                            errorFromGreaterToFrame.setVisible(true);
                        }
                    } else {
                        JFrame cancelledFrame = new JFrame();
                        JOptionPane cancelledLabel = new JOptionPane();
                        cancelledLabel.showMessageDialog(cancelledFrame, "Your booking has been cancelled.");
                        cancelledFrame.dispose();
                    }
                } catch (Exception exception){
                    JFrame errorFromGreaterToFrame = new JFrame("Error");
                    JLabel errorFromGreaterToLabel = new JLabel(exception.getMessage(), SwingConstants.CENTER);
                    errorFromGreaterToFrame.add(errorFromGreaterToLabel);
                    errorFromGreaterToFrame.setSize(300,100);
                    errorFromGreaterToFrame.setResizable(false);
                    errorFromGreaterToFrame.setLocationRelativeTo(null);
                    errorFromGreaterToFrame.dispose();

                    errorFromGreaterToFrame.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 27) {
                                errorFromGreaterToFrame.dispose();
                            }
                        }
                    });
                    errorFromGreaterToFrame.setVisible(true);
                }
            }
        });

        resCloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {System.exit(0); }
        });

        manageShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageTextArea.setText(daycare.searchByRegNo(Long.valueOf(manageRegNoTextfield.getText())));
            }
        });

        bringInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame confirmBringInFrame = new JFrame();
                JOptionPane confirmBringInLabel = new JOptionPane();
                confirmBringInLabel.showMessageDialog(confirmBringInFrame,
                        daycare.bringIn(Long.valueOf(manageRegNoTextfield.getText())));
            }
        });

        pickUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame confirmPickUpFrame = new JFrame();
                JOptionPane confirmPickUpLabel = new JOptionPane();
                confirmPickUpLabel.showMessageDialog(confirmPickUpFrame, daycare.pickUp(Long.valueOf(manageRegNoTextfield.getText())));
            }
        });

        manageCancelResButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame confirmCancelFrame = new JFrame();
                JOptionPane confirmCancelLabel = new JOptionPane();
                int cancelReply = confirmCancelLabel.showConfirmDialog(confirmCancelFrame,"Are " +
                        "you sure you want to cancel this reservation?", "Are you sure?", JOptionPane.YES_NO_OPTION);
                if (cancelReply== JOptionPane.YES_OPTION){
                    JFrame resCancelledFrame = new JFrame();
                    JOptionPane resCancelledLabel = new JOptionPane();
                    resCancelledLabel.showMessageDialog(resCancelledFrame,daycare.cancelRes(Long.valueOf(manageRegNoTextfield.getText())));
                } else {
                    confirmCancelFrame.dispose();
                }
            }
        });

        manageCloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        searchbyGuestLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchbyGuestTextfield.setEnabled(true);
                searchbyHostTextfield.setEnabled(false);
                searchbyOwnerTextfield.setEnabled(false);
                searchbyStatusTextfield.setEnabled(false);
            }
        });

        searchbyHostLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchbyGuestTextfield.setEnabled(false);
                searchbyHostTextfield.setEnabled(true);
                searchbyOwnerTextfield.setEnabled(false);
                searchbyStatusTextfield.setEnabled(false);
            }
        });

        searchbyOwnerLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchbyGuestTextfield.setEnabled(false);
                searchbyHostTextfield.setEnabled(false);
                searchbyOwnerTextfield.setEnabled(true);
                searchbyStatusTextfield.setEnabled(false);
            }
        });

        searchbyStatusLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchbyGuestTextfield.setEnabled(false);
                searchbyHostTextfield.setEnabled(false);
                searchbyOwnerTextfield.setEnabled(false);
                searchbyStatusTextfield.setEnabled(true);
            }
        });

        searchByButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchbyGuestLabel.isSelected()){
                    searchTextarea.setText(daycare.searchByGuest(searchbyGuestTextfield.getText()));
                } else if (searchbyHostLabel.isSelected()){
                    searchTextarea.setText(daycare.searchByHost(searchbyHostTextfield.getText()));
                } else if (searchbyOwnerLabel.isSelected()){
                    searchTextarea.setText(daycare.searchByOwner(searchbyOwnerTextfield.getText()));
                } else if (searchbyStatusLabel.isSelected()){
                    searchTextarea.setText(daycare.searchByStatus(searchbyStatusTextfield.getText()));
                }
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTextarea.setText(daycare.currentDate());
            }
        });

        searchCloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // --------------------------------------- FRAME ------------------------------------
        this.setSize(600,900);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setTitle("PetBnB");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void refreshChooseGuest(){
        Owner pOwner = ownerRepository.findByUsername(usernameTextfield.getText());
        ArrayList <String> chooseGuest = new ArrayList<String>();
        pOwner.getGuests().forEach(new Consumer<Guest>() {
            @Override
            public void accept(Guest guest) {
                chooseGuest.add("[" + guest.getType() +
                        "] " + guest.getName());
            }
        });
        ComboBoxModel model = new DefaultComboBoxModel(chooseGuest.toArray());
        resChooseGuestDropdown.setModel(model);
    }

    public void checkDate(LocalDate pFrom, LocalDate pTo) throws Exception{
        if (pTo.compareTo(pFrom)<0){
            throw new Exception("From-Date must be smaller than To-Date.");
        }
    }
}
