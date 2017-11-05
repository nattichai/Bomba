package bomba;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Map extends GridPane{
	
	public Map() {
		setAlignment(Pos.CENTER);
		setGridLinesVisible(true);
		for (int i = 0; i < 9; i++) {
	         getColumnConstraints().addAll(new ColumnConstraints(50));
	         getRowConstraints().add(new RowConstraints(50));
	     }
	}

}
