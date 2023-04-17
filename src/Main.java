
import com.jdbc.view.ProductCategoryFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
        ProductCategoryFrame productCategoryFrame = new ProductCategoryFrame();
        productCategoryFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}