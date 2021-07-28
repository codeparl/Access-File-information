import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class FileInfo extends JPanel {

    private JTextField pathField;
    private JButton browseButton;
    private JButton viewInfoButton;
    private JTable infoTable;
    private String dir  ; 

    public FileInfo() {

        dir  =  System.getProperty("user.dir");
        // divide this panel into two vertical
        // sections to lay out the items
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        // add paddings on the content
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // create two horizontal boxes using factory method
        // of Box
        Box sectionOne = Box.createHorizontalBox();
        sectionOne.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        Box sectionTwo = Box.createHorizontalBox();

        addCompTopSection(sectionOne);
        addTabel(sectionTwo);
        // add the two sections on our main panel
        add(sectionOne);
        add(sectionTwo);
    }

    public void addCompTopSection(Box section) {

        section.add(new JLabel("Path "));
        pathField = new JTextField();

        section.add(pathField);

        browseButton = new JButton("Browse...");
        browseButton.addActionListener((event)->{
            JFileChooser  fileChooser= new JFileChooser(System.getProperty("user.dir"));
            
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
             int approved =  fileChooser
             .showOpenDialog(FileInfo.this);
             if(approved == JFileChooser.APPROVE_OPTION){
                pathField.setText(fileChooser.getSelectedFile().toString());
             }
        });

        section.add(browseButton);

        viewInfoButton = new JButton("View Info");
        viewInfoButton.addActionListener((event)->{
            File dir  = new File(pathField.getText().trim());
            updateTabelModel(dir);
        });
        section.add(viewInfoButton);

    }

    private void addTabel(Box section) {

        infoTable = new JTable();
        //the dot . means the current folder
        //from which this app is executed
        updateTabelModel(new File(dir));
        pathField.setText((new File(dir)).getAbsolutePath());
        infoTable.setAutoCreateColumnsFromModel(true);
        section.add(new JScrollPane(infoTable));
    }


    private void updateTabelModel(File dir){
        //make sure the dir path exists
        if(dir.exists()){
            //display info 
        TabelModelDemo dataModel  =  new TabelModelDemo(dir);
        infoTable.setModel(dataModel);
        }//end if
    }


}