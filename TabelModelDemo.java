import java.io.File;
import java.sql.Date;
import java.text.DecimalFormat;
import javax.swing.table.AbstractTableModel;


public class TabelModelDemo extends AbstractTableModel {
    private String header[] = { "Type", "Name", "Size", "Readable", "Writable", "Last Modified" };

    private Class<?>[] types = { String.class, String.class, String.class, Boolean.class, Boolean.class, Date.class

    };
    private Object[][] data;
    private File file;

    public TabelModelDemo(File file) {
        this.file = file;
        processFileInfo();
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int index) {

        // will return each name defined in our headers
        return header[index];
    }

    @Override
    public Class<?> getColumnClass(int index) {
        return types[index];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public void processFileInfo() {

        // get the list of files in the dir
        String[] files = file.list();

        // next, create the data 2d array,
        // rows to be the number os files and
        // coumns to be the number of the headers
        data = new Object[files.length][header.length];

        // now, loop through the file list and obtain each
        // file's info and add this info into the data arrays
        for (int i = 0; i < files.length; i++) {

            // create a new file object to use its
            // methods to get info of the current file item
            String fullPath  = file
            .getAbsolutePath()+
            File.separator
            +files[i];
            File thisFile = new File(fullPath);
            // add file type
            data[i][0] = thisFile.isDirectory() ? "folder" : "File";
            data[i][1] = thisFile.getName();
            data[i][2] = formatSize(thisFile.length());
            data[i][3] = thisFile.canRead();
            data[i][4] = thisFile.canWrite();
            data[i][5] = new Date(thisFile.lastModified());
        } // end for loop

        // make sure to fire tabel structure change event
        // because we will be changing the data when the user
        // chooses a new dir.

        fireTableStructureChanged();

    }


    private String formatSize(long size){
        DecimalFormat df  =  new DecimalFormat("#.##");
        String  kb, mb, gb;
          double m = 1024;
        kb = df.format((double) size / m);
        mb =  df.format(Double.valueOf(kb) / m); 
        gb = df.format(Double.valueOf(mb) / m) ;
        if (size < m) {
            return size + "Bytes";
        } else if (Double.valueOf(kb) < m) {
            return kb+"KB";
        } else if (Double.valueOf(mb)  < m) {
            return mb+"MB";
        } else if (Double.valueOf(gb)   < m) {
            return gb+"GB";
        }
      return String.valueOf(size);
    }
}