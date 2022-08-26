package konyvtar;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;


public class KonyvtarFrame extends JFrame {

    
    private final JTable konyvekTablazat;

    
    private final JTable kolcsonzokTablazat;

    
    private final JPanel felsoResz;

   
    private final JSplitPane vizSzintes;

    
    private final JSplitPane fuggoLeges;

    
    private Konyvtar konyvtar;

    
    private KeresoPanel keresoMezo;

    
    private JTree kolcsonzesekStruktura;

    
    private Dimension ablakMeret;

    
    public KonyvtarFrame() {
        super("Könyvtárkezelõ program");
        this.konyvtar = new Konyvtar();
        this.konyvtar.setSzerializaloUtvonal("konyvtar.libdat");

        this.vizSzintes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.vizSzintes.setOneTouchExpandable(true);
        this.vizSzintes.setContinuousLayout(true);
        this.fuggoLeges = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.fuggoLeges.add(this.vizSzintes);
        this.fuggoLeges.setOneTouchExpandable(true); 
        this.fuggoLeges.setContinuousLayout(true);
        this.konyvekTablazat = new JTable();
        this.kolcsonzokTablazat = new JTable();
        fajlbolBeolvas();
        inditAblak();
        inditMenuPanel();
        inditKonyvTablazat();
        this.felsoResz = new JPanel();
        this.felsoResz.setLayout(new BoxLayout(this.felsoResz, BoxLayout.X_AXIS));
        Dimension dim = new Dimension(1280, 250);
        this.felsoResz.setPreferredSize(dim);
        this.felsoResz.add(this.vizSzintes);
        this.fuggoLeges.add(this.felsoResz);
        this.add(this.fuggoLeges, BorderLayout.CENTER);
        inditKolcsonTablazat();
        this.pack();
    }

    
    public static void main(String[] args) {
        KonyvtarFrame program = new KonyvtarFrame();
        program.setVisible(true);
    }

    
    private void fajlbolBeolvas() {
        this.konyvtar = Konyvtar.fajlbolBeolvas(this.konyvtar);
        this.konyvekTablazat.setModel(this.konyvtar.getKonyvekadatai());
        this.konyvekTablazat.getModel().addTableModelListener(new KonyvTablazatTableModelListener());
        this.kolcsonzokTablazat.setModel(this.konyvtar.getKolcsonzokadatai());
        this.kolcsonzokTablazat.getModel().addTableModelListener(new KolcsonzokTablazatTableModelListener());
        initCellaMod();
    }

    private void fajlImportAblak() {
        JFileChooser valaszt = new JFileChooser("./");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("libdat fájlok", "libdat");
        valaszt.setFileFilter(filter);
        valaszt.setDialogTitle("Import");
        int ertek = valaszt.showOpenDialog(this);
        if (ertek == JFileChooser.APPROVE_OPTION) {
            this.konyvtar.setSzerializaloUtvonal(valaszt.getSelectedFile().getPath());
            fajlbolBeolvas();
            this.kolcsonzesekStruktura.setModel(new Struktura(this.konyvtar.getKolcsonzok()));
            this.keresoMezo.setKonyvek(this.konyvtar.getKonyvek());
        }
    }

    
    private void inditMentesMint() {
        JFileChooser valaszt = new JFileChooser("./");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("libdat fájlok", "libdat");
        valaszt.setFileFilter(filter);
        valaszt.setDialogType(JFileChooser.SAVE_DIALOG);
        valaszt.setDialogTitle("Exportálás mint");
        int ertek = valaszt.showOpenDialog(this);
        if (ertek == JFileChooser.APPROVE_OPTION) {
            konyvtar.mentesMasKepp(valaszt.getSelectedFile().getPath());
        }
    }

    
    private void hozzaadKonyv() {
        KonyvPanel konyvPanel = new KonyvPanel();
        int valasz = JOptionPane.showConfirmDialog(null, konyvPanel.getFoPanel(), "Könyv hozzáadása", JOptionPane.OK_CANCEL_OPTION);
        if (valasz == JOptionPane.OK_OPTION)
            if (!konyvtar.addKonyv(konyvPanel.getSzerzo(), konyvPanel.getCime(), konyvPanel.getMegjelenes_id(),
                    Mufaj.ertek(konyvPanel.getMufaj(), "HU"), konyvPanel.getNyelve(), konyvPanel.getPipa()))
                JOptionPane.showMessageDialog(null, "Nem megfelelõ paraméterek. Írja be újra helyesen õket.", "Helytelen paraméterek.", JOptionPane.WARNING_MESSAGE);
    }

    
    private void torolKonyv() {
        if (konyvekTablazat.getSelectedRow() >= 0) {
            Konyvek konyv = konyvtar.getKonyvek().get(konyvekTablazat.convertRowIndexToModel(konyvekTablazat.getSelectedRow()));
            int valasz = JOptionPane.showConfirmDialog(null,
                    (konyv.getKikolcsonzi() == null) ? "Szeretné törölni a megadott könyvet?" : "Úgy is szeretné törölni, hogy valaki kikölcsönözte?",
                    "Megererõsíti a törlést?", JOptionPane.YES_NO_OPTION);
            if (valasz == JOptionPane.YES_OPTION)
                konyvtar.eltavolit(konyv);
        }
    }

    
    private void modositKolcsonzo() {
        Kolcsonzo kolcsonzo = konyvtar.getKolcsonzok().get(kolcsonzokTablazat.convertRowIndexToModel(kolcsonzokTablazat.getSelectedRow()));
        KolcsonzoPanel kolcsonzoPanel = new KolcsonzoPanel();
        kolcsonzoPanel.setNevErtek(kolcsonzo.getKolnev());
        kolcsonzoPanel.setSzulIdoErtek(kolcsonzo.getSzulIdo().toString());
        kolcsonzoPanel.setTelszamErtek(kolcsonzo.getKoltelszam());
        int valasz = JOptionPane.showConfirmDialog(null, kolcsonzoPanel.getFoPanel(),
                "Tag adatainak szerkesztése", JOptionPane.OK_CANCEL_OPTION);
        if (valasz == JOptionPane.OK_OPTION)
            if (!konyvtar.modositKolcsonzo(kolcsonzo, kolcsonzoPanel.getNevErtek(), kolcsonzoPanel.getSzulIdoErtek(), kolcsonzoPanel.getTelszamErtek()))
                JOptionPane.showMessageDialog(null, "Helytelen paraméterek. Próbálja újra nagyobb odafigyeléssel.", "Helytelen paraméterek.", JOptionPane.WARNING_MESSAGE);
    }

    
    private void inditAblak() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.ablakMeret = new Dimension(1280, 720);
        this.setMinimumSize(ablakMeret);
        this.setPreferredSize(ablakMeret);
        this.setLocationRelativeTo(null); 
        this.setLayout(new BorderLayout());

        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                konyvtar.mentes();
            }
        });

        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int fuggolegesSep = vizSzintes.getDividerLocation();
                int vizszintesSep = fuggoLeges.getDividerLocation();
                
                if (ablakMeret.width == 1280 && ablakMeret.height == 720
                        && (fuggolegesSep == 464 || fuggolegesSep < 0)
                        && (vizszintesSep == 78 || vizszintesSep < 0)) {
                    vizSzintes.setDividerLocation(0.75);
                    fuggoLeges.setDividerLocation(0.35);
                    return;
                }
                int magassag = e.getComponent().getHeight();
                int szelesseg = e.getComponent().getWidth();
                if (fuggolegesSep > 0) {
                    double arany = fuggolegesSep / (double) ablakMeret.width;
                    vizSzintes.setDividerLocation((int) (szelesseg * arany));
                }
                if (vizszintesSep > 0) {
                    double arany = vizszintesSep / (double) ablakMeret.height;
                    fuggoLeges.setDividerLocation((int) (magassag * arany));
                }
                ablakMeret.setSize(szelesseg, magassag);
            }
        });
    }

    
    private void inditMenuPanel() {
        JMenuBar menuPanel = new JMenuBar();

        JMenu fajlokMenu = new JMenu("Fájlok");
        JMenuItem megnyit = new JMenuItem("Importálás");
        megnyit.setAccelerator(KeyStroke.getKeyStroke('O', CTRL_DOWN_MASK));
        
        megnyit.addActionListener(ae -> fajlImportAblak());
        JMenuItem ment = new JMenuItem("Adatok mentése");
        ment.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_DOWN_MASK));
        
        ment.addActionListener(ae -> konyvtar.mentes());
        JMenuItem mentmint = new JMenuItem("Mentés mint");
        mentmint.addActionListener(ae -> inditMentesMint());
        JMenuItem kilepesMentesN = new JMenuItem("Csak bezárás");
        kilepesMentesN.addActionListener(ae -> System.exit(0));
        JMenuItem bezaras = new JMenuItem("Bezárás mentéssel");
        bezaras.setAccelerator(KeyStroke.getKeyStroke('E', CTRL_DOWN_MASK));
        bezaras.addActionListener(ae -> {
            konyvtar.mentes();
            System.exit(0);
        });

        JMenu modositMenu = new JMenu("Módosítás");
        JMenuItem kolcsModosit = new JMenuItem("Kölcsönzõ módosítása");
        kolcsModosit.setAccelerator(KeyStroke.getKeyStroke('U', CTRL_DOWN_MASK));
        kolcsModosit.addActionListener(ae -> {
            if (kolcsonzokTablazat.getSelectedRow() >= 0)
                modositKolcsonzo();
        });

        JMenu konyvekMenu = new JMenu("Összes könyv");
        JMenuItem addNew = new JMenuItem("Könyv felvétele");
        addNew.setAccelerator(KeyStroke.getKeyStroke('L', CTRL_DOWN_MASK));
        addNew.addActionListener(e -> hozzaadKonyv());
        JMenuItem eltavolit = new JMenuItem("Könyv törlése");
        eltavolit.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
        eltavolit.addActionListener(ae -> torolKonyv());

        fajlokMenu.add(megnyit);
        fajlokMenu.addSeparator();
        fajlokMenu.add(ment);
        fajlokMenu.addSeparator();
        fajlokMenu.add(mentmint);
        fajlokMenu.addSeparator();
        fajlokMenu.add(kilepesMentesN);
        fajlokMenu.addSeparator();
        fajlokMenu.add(bezaras);

        modositMenu.add(kolcsModosit);

        konyvekMenu.add(addNew);
        konyvekMenu.addSeparator();
        konyvekMenu.add(eltavolit);

        
        menuPanel.add(fajlokMenu);
        menuPanel.add(modositMenu);
        menuPanel.add(konyvekMenu);

        this.setJMenuBar(menuPanel);
    }

    
    private void inditKonyvTablazat() {
    	Dimension dim = new Dimension(10, 5);
        this.konyvekTablazat.setIntercellSpacing(dim); 
        this.konyvekTablazat.setAutoCreateRowSorter(true);
        
        this.konyvekTablazat.addPropertyChangeListener(propertyChangeEvent -> frissitStruk());
        JScrollPane guriga = new JScrollPane(this.konyvekTablazat);

        
        JPanel konyvPan = new JPanel();
        konyvPan.setBorder(BorderFactory.createTitledBorder("Összes könyv"));
        konyvPan.setLayout(new BorderLayout());

        
        JPanel eszakiPanel = new JPanel();
        BoxLayout northPanelLayout = new BoxLayout(eszakiPanel, BoxLayout.X_AXIS);

        eszakiPanel.setLayout(northPanelLayout);
        JButton hozzadKonyv = new JButton("Új könyv felvétele");
        hozzadKonyv.addActionListener(ae -> hozzaadKonyv());
        eszakiPanel.add(hozzadKonyv);
        eszakiPanel.add(Box.createHorizontalGlue());
        JButton torolKonyv = new JButton("Könyv törlése");
        torolKonyv.addActionListener(ae -> torolKonyv());
        eszakiPanel.add(torolKonyv);
        eszakiPanel.add(Box.createHorizontalGlue());

        JButton visszavesz = new JButton("Visszavétel");
        visszavesz.addActionListener(ae -> {
            if (konyvekTablazat.getSelectedRow() >= 0)
                this.konyvtar.getKonyvekadatai().setValueAt(null, konyvekTablazat.convertRowIndexToModel(konyvekTablazat.getSelectedRow()), 6);
        });
        eszakiPanel.add(visszavesz);
        eszakiPanel.add(Box.createHorizontalGlue());

        
        JCheckBox keresesSzerzois = new JCheckBox("Szerzõ nevében való keresés");
        keresesSzerzois.addActionListener(e -> {
            String kereso = keresoMezo.getText();
            if (!kereso.equals(keresoMezo.getHelyettesitoString())) {
                konyvekTablazat.setRowSorter(konyvtar.kereses(kereso, keresesSzerzois.isSelected()));
            }
        });
        
        JCheckBox kolcsonzottek = new JCheckBox("Kölcsönzöttek szûrése");
        kolcsonzottek.addActionListener(ae -> {
            if (kolcsonzottek.isSelected()) {
                if (!keresoMezo.getText().equals(keresoMezo.getHelyettesitoString())) {
                    keresoMezo.update();
                }
                konyvekTablazat.setRowSorter(konyvtar.kolcsonzottekFilter());
            } else konyvekTablazat.setAutoCreateRowSorter(true);
        });
        eszakiPanel.add(kolcsonzottek);

    
        this.keresoMezo = new KeresoPanel(konyvtar.getKonyvek());
        keresoMezo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String keresoSzoveg = keresoMezo.getText();
                if (!keresoSzoveg.equals(keresoMezo.getHelyettesitoString())) {
                    konyvekTablazat.setRowSorter(konyvtar.kereses(keresoSzoveg, keresesSzerzois.isSelected()));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
                if (kolcsonzottek.isSelected() && !keresoMezo.getText().equals("")) {
                    kolcsonzottek.doClick();
                }
                konyvekTablazat.setRowSorter(konyvtar.kereses(keresoMezo.getText(), keresesSzerzois.isSelected()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                konyvekTablazat.setRowSorter(konyvtar.kereses(keresoMezo.getText(), keresesSzerzois.isSelected()));
            }
        });
        eszakiPanel.add(keresoMezo);
        eszakiPanel.add(keresesSzerzois);

        konyvPan.add(eszakiPanel, BorderLayout.NORTH);
        konyvPan.add(guriga, BorderLayout.CENTER);
        this.fuggoLeges.add(konyvPan);

       
        konyvekTablazat.setDefaultRenderer(String.class, new KonyvTableCellRenderer(konyvekTablazat.getDefaultRenderer(String.class)));
        konyvekTablazat.setDefaultRenderer(Integer.class, new KonyvTableCellRenderer(konyvekTablazat.getDefaultRenderer(Integer.class)));
        konyvekTablazat.setDefaultRenderer(Boolean.class, new KonyvTableCellRenderer(konyvekTablazat.getDefaultRenderer(Boolean.class)));
        konyvekTablazat.setDefaultRenderer(Kolcsonzo.class, new KonyvTableCellRenderer(konyvekTablazat.getDefaultRenderer(Kolcsonzo.class)));

        konyvekTablazat.setRowHeight(20);
    }

    
    private void inditKolcsonTablazat() {
        this.kolcsonzokTablazat.setIntercellSpacing(new Dimension(10, 2)); 
        this.kolcsonzokTablazat.setRowHeight(18);
        this.kolcsonzokTablazat.setAutoCreateRowSorter(true);
        JScrollPane guriga = new JScrollPane(this.kolcsonzokTablazat);

        
        this.kolcsonzokTablazat.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    modositKolcsonzo();
                }
            }
        });

        
        JPanel kolcsonzoPan = new JPanel(new BorderLayout());
        kolcsonzoPan.setBorder(BorderFactory.createTitledBorder("Kölcsönzõink"));
        JPanel kolcsPanel = new JPanel();
        kolcsPanel.setLayout(new BoxLayout(kolcsPanel, BoxLayout.X_AXIS));
        JButton addKolcsonzo = new JButton("Kölcsönzõ felvétele");
        addKolcsonzo.addActionListener(ae -> {
            KolcsonzoPanel kolcsonzoPanel = new KolcsonzoPanel();
            if (JOptionPane.showConfirmDialog(null, kolcsonzoPanel.getFoPanel(),
                    "Kölcsönzõ felvétele", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                String nev = kolcsonzoPanel.getNevErtek();
                String szulIdo = kolcsonzoPanel.getSzulIdoErtek();
                String telefonszam = kolcsonzoPanel.getTelszamErtek();
                if (!konyvtar.addKolcsonzo(nev, szulIdo, telefonszam)) {
                    JOptionPane.showMessageDialog(null, "Helytelen paraméterek. Figyeljen oda az adatok helyes bevitelére.", "Helytelen paraméterek.", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        kolcsPanel.add(addKolcsonzo);
        JButton kolcsonzoTorol = new JButton("Kölcsönzõ törlése");
        kolcsonzoTorol.addActionListener(ae -> {
            if (kolcsonzokTablazat.getSelectedRow() >= 0) {
                int valasz = JOptionPane.showConfirmDialog(null, "Szeretné eltávolítani a megadott kölcsönzõt? A kölcsönzött könyvei is törlõdnek ekkor.", "Megerõsíti az eltávolítását?", JOptionPane.YES_NO_OPTION);
                if (valasz == JOptionPane.YES_OPTION)
                    konyvtar.eltavolit(konyvtar.getKolcsonzok().get(kolcsonzokTablazat.convertRowIndexToModel(kolcsonzokTablazat.getSelectedRow())));
            }
        });
        kolcsPanel.add(kolcsonzoTorol);
        kolcsonzoPan.add(kolcsPanel, BorderLayout.SOUTH);
        kolcsonzoPan.add(guriga, BorderLayout.CENTER);
        this.vizSzintes.add(kolcsonzoPan);
    }

    
    private void frissitStruk() {
        if (this.kolcsonzesekStruktura != null) {
            DefaultTreeModel struk = (DefaultTreeModel) this.kolcsonzesekStruktura.getModel();
            struk.reload();
            for (int h = 0; h < this.kolcsonzesekStruktura.getRowCount(); h++) {
                kolcsonzesekStruktura.expandRow(h);
            }
        }
    }

    
    private void initCellaMod() {
        
        TableColumn kolcson = konyvekTablazat.getColumnModel().getColumn(6);
        JComboBox<Kolcsonzo> kolcsonzoCB = this.konyvtar.getKolcsonzokadatai().getKolcsonzoBox();
        kolcsonzoCB.insertItemAt(null, 0); 
        kolcson.setCellEditor(new DefaultCellEditor(kolcsonzoCB));

        
        TableColumn mufajok = konyvekTablazat.getColumnModel().getColumn(3);
        JComboBox<String> mufajCB = new JComboBox<>();
        for (Mufaj muf : Mufaj.values())
            mufajCB.addItem(muf.getLocN());
        mufajok.setCellEditor(new DefaultCellEditor(mufajCB));
    }

    
    private class KonyvTableCellRenderer implements TableCellRenderer {

        
        private final TableCellRenderer renderelo;

        
        public KonyvTableCellRenderer(TableCellRenderer renderelo) {
            this.renderelo = renderelo;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component komponens = renderelo.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            boolean kolcsonozheto = konyvtar.getKonyvek().get(konyvekTablazat.convertRowIndexToModel(row)).isKolcsstatusz();

            if (kolcsonozheto) {
                if ((konyvtar.getKonyvek().get(konyvekTablazat.convertRowIndexToModel(row)).getKikolcsonzi()) != null) {
                    komponens.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
                }
                else {
                    komponens.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
                }
            }
            else {
                komponens.setFont(new Font(Font.SERIF, Font.PLAIN, 13));
            }
            return komponens;
        }
    }

    
    private class KonyvTablazatTableModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            keresoMezo.update();
            frissitStruk();
        }
    }

    
    private class KolcsonzokTablazatTableModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            konyvtar.getKonyvekadatai().fireTableDataChanged();
            frissitStruk();
        }
    }
}